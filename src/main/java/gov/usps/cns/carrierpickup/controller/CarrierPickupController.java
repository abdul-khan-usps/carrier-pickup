package gov.usps.cns.carrierpickup.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.usps.cns.carrierpickup.dto.PickupAvailabilityRequest;
import gov.usps.cns.carrierpickup.dto.PickupAvailabilityResponse;
import gov.usps.cns.carrierpickup.exception.CarrierPickupException;
import gov.usps.cns.carrierpickup.service.CarrierPickupService;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/carrier-pickup")
@Validated
public class CarrierPickupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarrierPickupController.class);

    private final CarrierPickupService carrierPickupService;

    public CarrierPickupController(CarrierPickupService carrierPickupService) {
        this.carrierPickupService = carrierPickupService;
    }

    @PostMapping("/availability")
    public ResponseEntity<PickupAvailabilityResponse> checkPickupAvailability(
            @Valid @RequestBody PickupAvailabilityRequest request) {
        
        try {
            LOGGER.info("Received pickup availability request for client: {}", request.getClientId());
            
            PickupAvailabilityResponse response = carrierPickupService.checkPickupAvailability(request);
            
            LOGGER.info("Pickup availability check completed successfully for client: {}", request.getClientId());
            
            return ResponseEntity.ok(response);
            
        } catch (CarrierPickupException e) {
            LOGGER.error("Carrier pickup service error: {}", e.getMessage(), e);
            
            PickupAvailabilityResponse errorResponse = new PickupAvailabilityResponse();
            errorResponse.setSuccess(false);
            PickupAvailabilityResponse.ErrorInfo error = new PickupAvailabilityResponse.ErrorInfo();
            error.setCode(e.getErrorCode());
            error.setMessage(e.getMessage());
            errorResponse.setError(error);
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            
        } catch (Exception e) {
            LOGGER.error("Unexpected error in carrier pickup controller: {}", e.getMessage(), e);
            
            PickupAvailabilityResponse errorResponse = new PickupAvailabilityResponse();
            errorResponse.setSuccess(false);
            PickupAvailabilityResponse.ErrorInfo error = new PickupAvailabilityResponse.ErrorInfo();
            error.setCode("INTERNAL_ERROR");
            error.setMessage("Internal server error");
            errorResponse.setError(error);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
