package gov.usps.cns.carrierpickup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class Address {
    
    @NotBlank(message = "Address line one is required")
    @Size(max = 100, message = "Address line one must not exceed 100 characters")
    private String addressLineOne;
    
    @Size(max = 100, message = "Address line two must not exceed 100 characters")
    private String addressLineTwo;
    
    @Size(max = 10, message = "Carrier route must not exceed 10 characters")
    private String carrierRoute;
    
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;
    
    @Size(max = 10, message = "Delivery point must not exceed 10 characters")
    private String deliveryPoint;
    
    @NotBlank(message = "State is required")
    @Pattern(regexp = "^[A-Z]{2}$", message = "State must be a 2-letter code")
    private String state;
    
    @Size(max = 10, message = "Urbanization code must not exceed 10 characters")
    private String urbanizationCode;
    
    @Size(max = 4, message = "ZIP4 must not exceed 4 characters")
    private String zip4;
    
    @NotBlank(message = "ZIP5 is required")
    @Pattern(regexp = "^\\d{5}$", message = "ZIP5 must be exactly 5 digits")
    private String zip5;
}
