package gov.usps.cns.carrierpickup.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gov.usps.cns.carrierpickup.dto.Address;
import gov.usps.cns.carrierpickup.dto.PickupAvailabilityRequest;
import gov.usps.carrierpickup.dto.PickupAvailabilityResponse;
import gov.usps.cns.carrierpickup.exception.CarrierPickupException;

@Service
public class CarrierPickupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarrierPickupService.class);

    private final UspsPickupService uspsPickupService;

    public CarrierPickupService(UspsPickupService uspsPickupService) {
        this.uspsPickupService = uspsPickupService;
    }

    public PickupAvailabilityResponse checkPickupAvailability(PickupAvailabilityRequest request) {
        try {
            LOGGER.info("Processing pickup availability request for client: {}", request.getClientId());
            
            // Validate request
            validateRequest(request);
            
            // Call USPS service
            PickupAvailabilityResponse response = uspsPickupService.checkPickupAvailability(request.getAddress());
            
            LOGGER.info("Pickup availability check completed. Success: {}, Pickup Available: {}", 
                response.isSuccess(), response.isPickupAvailable());
            
            return response;
            
        } catch (CarrierPickupException e) {
            LOGGER.error("Carrier pickup service error: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error in carrier pickup service: {}", e.getMessage(), e);
            throw new CarrierPickupException("Internal service error", "INTERNAL_ERROR", e);
        }
    }

    private void validateRequest(PickupAvailabilityRequest request) {
        if (request == null) {
            throw new CarrierPickupException("Request cannot be null", "INVALID_REQUEST");
        }
        
        if (request.getAddress() == null) {
            throw new CarrierPickupException("Address is required", "INVALID_REQUEST");
        }
        
        if (request.getClientId() == null || request.getClientId().trim().isEmpty()) {
            throw new CarrierPickupException("Client ID is required", "INVALID_REQUEST");
        }
        
        Address address = request.getAddress();
        if (address.getAddressLineOne() == null || address.getAddressLineOne().trim().isEmpty()) {
            throw new CarrierPickupException("Address line one is required", "INVALID_REQUEST");
        }
        
        if (address.getCity() == null || address.getCity().trim().isEmpty()) {
            throw new CarrierPickupException("City is required", "INVALID_REQUEST");
        }
        
        if (address.getState() == null || address.getState().trim().isEmpty()) {
            throw new CarrierPickupException("State is required", "INVALID_REQUEST");
        }
        
        if (address.getZip5() == null || address.getZip5().trim().isEmpty()) {
            throw new CarrierPickupException("ZIP5 is required", "INVALID_REQUEST");
        }
    }
}
