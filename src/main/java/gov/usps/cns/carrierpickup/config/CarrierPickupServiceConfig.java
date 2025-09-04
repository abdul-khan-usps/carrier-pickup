package gov.usps.cns.carrierpickup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import gov.usps.cns.carrierpickup.service.UspsPickupService;

@Configuration
public class CarrierPickupServiceConfig {

    private final CarrierPickupServiceProperties properties;

    public CarrierPickupServiceConfig(CarrierPickupServiceProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("gov.usps.cns.carrierpickup.soap");
        return marshaller;
    }

    @Bean
    public HttpComponentsMessageSender messageSender() {
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        messageSender.setConnectionTimeout(properties.getUspsPickupTimeout());
        messageSender.setReadTimeout(properties.getUspsPickupTimeout());
        return messageSender;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller, HttpComponentsMessageSender messageSender) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        webServiceTemplate.setMessageSender(messageSender);
        webServiceTemplate.setDefaultUri(properties.getUspsPickupServiceUrl());
        return webServiceTemplate;
    }

    @Bean
    public UspsPickupService uspsPickupService(WebServiceTemplate webServiceTemplate) {
        UspsPickupService service = new UspsPickupService(webServiceTemplate, properties);
        return service;
    }
}
