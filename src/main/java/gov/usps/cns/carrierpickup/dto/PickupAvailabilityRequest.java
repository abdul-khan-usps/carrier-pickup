package gov.usps.cns.carrierpickup.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PickupAvailabilityRequest {
    
    @NotNull(message = "Address is required")
    @Valid
    private Address address;
    
    @NotBlank(message = "Client ID is required")
    private String clientId;
}
