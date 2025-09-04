package gov.usps.cns.carrierpickup;

import java.util.TimeZone;

import jakarta.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"gov.usps.cns.carrierpickup", "gov.usps.cns.common", "gov.usps.cns.shipping"})
public class CarrierPickupServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrierPickupServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // Setting Spring Boot default tz
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
