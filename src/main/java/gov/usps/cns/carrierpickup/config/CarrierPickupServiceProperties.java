package gov.usps.cns.carrierpickup.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "carrier-pickup")
public class CarrierPickupServiceProperties {
    
    private String uspsPickupServiceUrl = "https://sit-cccservices.usps.gov/cpws/PackagePickupV5Service";
    private int uspsPickupTimeout = 30000; // 30 seconds
    private String uspsClientId = "CNS";
    private int cacheTtl = 3600; // 1 hour in seconds
    private boolean enableCaching = true;
    private int maxRetries = 3;
    private int retryDelayMs = 1000;
}
