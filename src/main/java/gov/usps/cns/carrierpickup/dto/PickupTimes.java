package gov.usps.cns.carrierpickup.dto;

import lombok.Data;

@Data
public class PickupTimes {
    private String startPickupTime;
    private String endPickupTime;
    private String estimatedPickupWindow;
}
