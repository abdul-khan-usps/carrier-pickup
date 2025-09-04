package gov.usps.cns.carrierpickup.exception;

public class CarrierPickupException extends RuntimeException {
    
    private final String errorCode;
    
    public CarrierPickupException(String message) {
        super(message);
        this.errorCode = "CARRIER_PICKUP_ERROR";
    }
    
    public CarrierPickupException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "CARRIER_PICKUP_ERROR";
    }
    
    public CarrierPickupException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public CarrierPickupException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
