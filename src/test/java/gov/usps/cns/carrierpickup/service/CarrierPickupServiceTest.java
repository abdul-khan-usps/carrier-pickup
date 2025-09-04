package gov.usps.cns.carrierpickup.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gov.usps.cns.carrierpickup.dto.Address;
import gov.usps.cns.carrierpickup.dto.PickupAvailabilityRequest;
import gov.usps.cns.carrierpickup.dto.PickupAvailabilityResponse;
import gov.usps.cns.carrierpickup.exception.CarrierPickupException;

@ExtendWith(MockitoExtension.class)
class CarrierPickupServiceTest {

    @Mock
    private UspsPickupService uspsPickupService;

    private CarrierPickupService carrierPickupService;

    @BeforeEach
    void setUp() {
        carrierPickupService = new CarrierPickupService(uspsPickupService);
    }

    @Test
    void testCheckPickupAvailability_Success() {
        // Given
        PickupAvailabilityRequest request = createValidRequest();
        PickupAvailabilityResponse expectedResponse = createSuccessResponse();
        
        when(uspsPickupService.checkPickupAvailability(any(Address.class)))
            .thenReturn(expectedResponse);

        // When
        PickupAvailabilityResponse response = carrierPickupService.checkPickupAvailability(request);

        // Then
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertTrue(response.isPickupAvailable());
        assertEquals("43101 SHADOW TER", response.getAddress().getAddressLineOne());
        assertEquals("LEESBURG", response.getAddress().getCity());
        assertEquals("VA", response.getAddress().getState());
        assertEquals("20176", response.getAddress().getZip5());
        
        verify(uspsPickupService).checkPickupAvailability(request.getAddress());
    }

    @Test
    void testCheckPickupAvailability_NullRequest() {
        // When & Then
        CarrierPickupException exception = assertThrows(CarrierPickupException.class,
            () -> carrierPickupService.checkPickupAvailability(null));
        
        assertEquals("Request cannot be null", exception.getMessage());
        assertEquals("INVALID_REQUEST", exception.getErrorCode());
    }

    @Test
    void testCheckPickupAvailability_NullAddress() {
        // Given
        PickupAvailabilityRequest request = new PickupAvailabilityRequest();
        request.setAddress(null);
        request.setClientId("CNS");

        // When & Then
        CarrierPickupException exception = assertThrows(CarrierPickupException.class,
            () -> carrierPickupService.checkPickupAvailability(request));
        
        assertEquals("Address is required", exception.getMessage());
        assertEquals("INVALID_REQUEST", exception.getErrorCode());
    }

    @Test
    void testCheckPickupAvailability_EmptyClientId() {
        // Given
        PickupAvailabilityRequest request = createValidRequest();
        request.setClientId("");

        // When & Then
        CarrierPickupException exception = assertThrows(CarrierPickupException.class,
            () -> carrierPickupService.checkPickupAvailability(request));
        
        assertEquals("Client ID is required", exception.getMessage());
        assertEquals("INVALID_REQUEST", exception.getErrorCode());
    }

    @Test
    void testCheckPickupAvailability_EmptyAddressLineOne() {
        // Given
        PickupAvailabilityRequest request = createValidRequest();
        request.getAddress().setAddressLineOne("");

        // When & Then
        CarrierPickupException exception = assertThrows(CarrierPickupException.class,
            () -> carrierPickupService.checkPickupAvailability(request));
        
        assertEquals("Address line one is required", exception.getMessage());
        assertEquals("INVALID_REQUEST", exception.getErrorCode());
    }

    @Test
    void testCheckPickupAvailability_EmptyCity() {
        // Given
        PickupAvailabilityRequest request = createValidRequest();
        request.getAddress().setCity("");

        // When & Then
        CarrierPickupException exception = assertThrows(CarrierPickupException.class,
            () -> carrierPickupService.checkPickupAvailability(request));
        
        assertEquals("City is required", exception.getMessage());
        assertEquals("INVALID_REQUEST", exception.getErrorCode());
    }

    @Test
    void testCheckPickupAvailability_EmptyState() {
        // Given
        PickupAvailabilityRequest request = createValidRequest();
        request.getAddress().setState("");

        // When & Then
        CarrierPickupException exception = assertThrows(CarrierPickupException.class,
            () -> carrierPickupService.checkPickupAvailability(request));
        
        assertEquals("State is required", exception.getMessage());
        assertEquals("INVALID_REQUEST", exception.getErrorCode());
    }

    @Test
    void testCheckPickupAvailability_EmptyZip5() {
        // Given
        PickupAvailabilityRequest request = createValidRequest();
        request.getAddress().setZip5("");

        // When & Then
        CarrierPickupException exception = assertThrows(CarrierPickupException.class,
            () -> carrierPickupService.checkPickupAvailability(request));
        
        assertEquals("ZIP5 is required", exception.getMessage());
        assertEquals("INVALID_REQUEST", exception.getErrorCode());
    }

    private PickupAvailabilityRequest createValidRequest() {
        PickupAvailabilityRequest request = new PickupAvailabilityRequest();
        request.setClientId("CNS");
        
        Address address = new Address();
        address.setAddressLineOne("43101 shadow terr");
        address.setAddressLineTwo("");
        address.setCity("leesburg");
        address.setState("VA");
        address.setZip5("20176");
        address.setZip4("");
        
        request.setAddress(address);
        return request;
    }

    private PickupAvailabilityResponse createSuccessResponse() {
        PickupAvailabilityResponse response = new PickupAvailabilityResponse();
        response.setSuccess(true);
        response.setPickupAvailable(true);
        
        Address address = new Address();
        address.setAddressLineOne("43101 SHADOW TER");
        address.setCity("LEESBURG");
        address.setState("VA");
        address.setZip5("20176");
        address.setZip4("3655");
        address.setCarrierRoute("R031");
        address.setDeliveryPoint("01");
        
        response.setAddress(address);
        response.setDogStatus("UNK");
        
        return response;
    }
}
