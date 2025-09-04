package gov.usps.cns.carrierpickup.dto;

import lombok.Data;

@Data
public class PickupResult {
    private String message;
    private boolean result;
    private String resultCode;
}
