package gov.usps.cns.carrierpickup.dto;

import lombok.Data;

@Data
public class PickupAvailabilityResponse {
    private boolean success;
    private Address address;
    private boolean pickupAvailable;
    private PickupTimes pickupTimes;
    private String dogStatus;
    private PickupResult result;
    private ErrorInfo error;
    
    @Data
    public static class ErrorInfo {
        private String code;
        private String message;
    }
}
