package gov.usps.cns.carrierpickup.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import gov.usps.cns.carrierpickup.config.CarrierPickupServiceProperties;
import gov.usps.cns.carrierpickup.dto.Address;
import gov.usps.cns.carrierpickup.dto.PickupAvailabilityResponse;
import gov.usps.cns.carrierpickup.dto.PickupResult;
import gov.usps.cns.carrierpickup.dto.PickupTimes;
import gov.usps.cns.carrierpickup.exception.CarrierPickupException;
import gov.usps.cns.carrierpickup.soap.AvailabilityForAddress;
import gov.usps.cns.carrierpickup.soap.AvailabilityForAddressResponse;

@Service
public class UspsPickupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UspsPickupService.class);
    private static final String SOAP_ACTION = "http://v5.soap.ws.pp.ccc.usps.com/availabilityForAddress";

    private final WebServiceTemplate webServiceTemplate;
    private final CarrierPickupServiceProperties properties;

    public UspsPickupService(WebServiceTemplate webServiceTemplate, CarrierPickupServiceProperties properties) {
        this.webServiceTemplate = webServiceTemplate;
        this.properties = properties;
    }

    public PickupAvailabilityResponse checkPickupAvailability(Address address) {
        try {
            LOGGER.info("Checking pickup availability for address: {} {}, {} {}", 
                address.getAddressLineOne(), address.getAddressLineTwo(), address.getCity(), address.getState());

            AvailabilityForAddress request = createSoapRequest(address);
            
            AvailabilityForAddressResponse response = (AvailabilityForAddressResponse) webServiceTemplate
                .marshalSendAndReceive(request, new SoapActionCallback(SOAP_ACTION));

            return mapResponse(response);
            
        } catch (Exception e) {
            LOGGER.error("Error checking pickup availability for address: {} {}, {} {}", 
                address.getAddressLineOne(), address.getAddressLineTwo(), address.getCity(), address.getState(), e);
            throw new CarrierPickupException("Failed to check pickup availability", "USPS_SERVICE_ERROR", e);
        }
    }

    private AvailabilityForAddress createSoapRequest(Address address) {
        AvailabilityForAddress request = new AvailabilityForAddress();
        request.setClientId(properties.getUspsClientId());
        
        AvailabilityForAddress.Address soapAddress = new AvailabilityForAddress.Address();
        soapAddress.setAddressLineOne(address.getAddressLineOne());
        soapAddress.setAddressLineTwo(address.getAddressLineTwo());
        soapAddress.setCarrierRoute(address.getCarrierRoute());
        soapAddress.setCity(address.getCity());
        soapAddress.setDeliveryPoint(address.getDeliveryPoint());
        soapAddress.setState(address.getState());
        soapAddress.setUrbanizationCode(address.getUrbanizationCode());
        soapAddress.setZip4(address.getZip4());
        soapAddress.setZip5(address.getZip5());
        
        request.setAddress(soapAddress);
        return request;
    }

    private PickupAvailabilityResponse mapResponse(AvailabilityForAddressResponse soapResponse) {
        PickupAvailabilityResponse response = new PickupAvailabilityResponse();
        
        if (soapResponse == null || soapResponse.getAddressResult() == null) {
            response.setSuccess(false);
            PickupAvailabilityResponse.ErrorInfo error = new PickupAvailabilityResponse.ErrorInfo();
            error.setCode("NO_RESPONSE");
            error.setMessage("No response received from USPS service");
            response.setError(error);
            return response;
        }

        AvailabilityForAddressResponse.AddressResult addressResult = soapResponse.getAddressResult();
        
        // Map address
        if (addressResult.getAddress() != null) {
            Address address = new Address();
            address.setAddressLineOne(addressResult.getAddress().getAddressLineOne());
            address.setAddressLineTwo(addressResult.getAddress().getAddressLineTwo());
            address.setCarrierRoute(addressResult.getAddress().getCarrierRoute());
            address.setCity(addressResult.getAddress().getCity());
            address.setDeliveryPoint(addressResult.getAddress().getDeliveryPoint());
            address.setState(addressResult.getAddress().getState());
            address.setUrbanizationCode(addressResult.getAddress().getUrbanizationCode());
            address.setZip4(addressResult.getAddress().getZip4());
            address.setZip5(addressResult.getAddress().getZip5());
            response.setAddress(address);
        }

        // Map pickup times
        if (addressResult.getPickupTimes() != null) {
            PickupTimes pickupTimes = new PickupTimes();
            pickupTimes.setStartPickupTime(addressResult.getPickupTimes().getStartPickupTime());
            pickupTimes.setEndPickupTime(addressResult.getPickupTimes().getEndPickupTime());
            pickupTimes.setEstimatedPickupWindow(addressResult.getPickupTimes().getEstimatedPickupWindow());
            response.setPickupTimes(pickupTimes);
        }

        // Map result
        if (addressResult.getResult() != null) {
            PickupResult result = new PickupResult();
            result.setMessage(addressResult.getResult().getMessage());
            result.setResult(addressResult.getResult().isResult());
            result.setResultCode(addressResult.getResult().getResultCode());
            response.setResult(result);
            
            // Set success based on result
            response.setSuccess(addressResult.getResult().isResult());
            response.setPickupAvailable(addressResult.getResult().isResult());
            
            // If not successful, create error info
            if (!addressResult.getResult().isResult()) {
                PickupAvailabilityResponse.ErrorInfo error = new PickupAvailabilityResponse.ErrorInfo();
                error.setCode(addressResult.getResult().getResultCode());
                error.setMessage(addressResult.getResult().getMessage());
                response.setError(error);
            }
        }

        response.setDogStatus(addressResult.getDogStatus());

        return response;
    }
}
