# CNS Carrier Pickup Service

A Spring Boot microservice for checking USPS carrier pickup availability for addresses.

## Overview

The Carrier Pickup Service provides an API to check if USPS carrier pickup is available for a given address. It integrates with the USPS Package Pickup V5 Service via SOAP to determine pickup availability.

## Features

- REST API for checking pickup availability
- SOAP integration with USPS Package Pickup V5 Service
- Input validation and error handling
- Health checks and monitoring
- Docker containerization
- Kubernetes deployment support

## API Endpoints

### Check Pickup Availability

**POST** `/v1/carrier-pickup/availability`

Check if carrier pickup is available for a given address.

#### Request Body

```json
{
  "address": {
    "addressLineOne": "43101 shadow terr",
    "addressLineTwo": "",
    "city": "leesburg",
    "state": "VA",
    "zip5": "20176",
    "zip4": ""
  },
  "clientId": "CNS"
}
```

#### Success Response (200)

```json
{
  "success": true,
  "address": {
    "addressLineOne": "43101 SHADOW TER",
    "city": "LEESBURG",
    "state": "VA",
    "zip5": "20176",
    "zip4": "3655",
    "carrierRoute": "R031",
    "deliveryPoint": "01"
  },
  "pickupAvailable": true,
  "pickupTimes": {
    "startPickupTime": "",
    "endPickupTime": "",
    "estimatedPickupWindow": ""
  },
  "dogStatus": "UNK"
}
```

#### Error Response (400)

```json
{
  "success": false,
  "error": {
    "code": "1007",
    "message": "An invalid address was entered. Please verify address, including apartment, suite number"
  }
}
```

## Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `USPS_PICKUP_SERVICE_URL` | USPS SOAP service URL | `https://sit-cccservices.usps.gov/cpws/PackagePickupV5Service` |
| `USPS_PICKUP_TIMEOUT` | SOAP request timeout (ms) | `30000` |
| `USPS_CLIENT_ID` | Client identifier | `CNS` |
| `CACHE_TTL` | Response cache TTL (seconds) | `3600` |
| `ENABLE_CACHING` | Enable response caching | `true` |
| `MAX_RETRIES` | Maximum retry attempts | `3` |
| `RETRY_DELAY_MS` | Retry delay (ms) | `1000` |

### Application Properties

The service uses Spring Boot configuration properties with the `carrier-pickup` prefix:

```yaml
carrier-pickup:
  usps-pickup-service-url: ${USPS_PICKUP_SERVICE_URL}
  usps-pickup-timeout: ${USPS_PICKUP_TIMEOUT}
  usps-client-id: ${USPS_CLIENT_ID}
  cache-ttl: ${CACHE_TTL}
  enable-caching: ${ENABLE_CACHING}
  max-retries: ${MAX_RETRIES}
  retry-delay-ms: ${RETRY_DELAY_MS}
```

## Development

### Prerequisites

- Java 21
- Maven 3.8+
- Docker

### Building

```bash
mvn clean package
```

### Running Locally

```bash
java -jar target/carrier-pickup.jar
```

### Docker

```bash
docker build -t carrier-pickup .
docker run -p 8080:8080 carrier-pickup
```

## Deployment

### Kubernetes

The service includes Helm charts for Kubernetes deployment:

```bash
# Deploy to SIT
helm install carrier-pickup ./helm -f ./helm/values-sit.yaml

# Deploy to Production
helm install carrier-pickup ./helm -f ./helm/values-prod.yaml
```

### Health Checks

The service exposes health check endpoints:

- **Health Check**: `GET /actuator/health`
- **Info**: `GET /actuator/info`
- **Metrics**: `GET /actuator/metrics`

## Architecture

### Components

- **CarrierPickupController**: REST API controller
- **CarrierPickupService**: Main business logic service
- **UspsPickupService**: SOAP client for USPS integration
- **CarrierPickupServiceConfig**: Spring configuration
- **CarrierPickupServiceProperties**: Configuration properties

### Dependencies

- Spring Boot Web Starter
- Spring Boot Web Services Starter (SOAP)
- Spring Boot Actuator (health checks)
- Spring Boot Validation
- JAXB API (XML processing)
- Lombok (code reduction)

### Integration Points

- **USPS Package Pickup V5 Service**: SOAP endpoint for pickup availability
- **Spring Boot Actuator**: Health checks and monitoring
- **Kubernetes**: Container orchestration

## Error Handling

The service implements comprehensive error handling:

- **Validation Errors**: Input validation with detailed error messages
- **SOAP Errors**: USPS service error handling and mapping
- **Network Errors**: Connection timeout and retry logic
- **System Errors**: Internal error handling with logging

## Monitoring

### Logging

The service uses structured logging with the following levels:

- **INFO**: Normal operation logs
- **WARN**: Warning conditions
- **ERROR**: Error conditions with stack traces
- **DEBUG**: Detailed debugging information (SIT only)

### Metrics

Spring Boot Actuator provides metrics for:

- HTTP request counts and response times
- JVM metrics (memory, GC, threads)
- Application-specific metrics

## Security

- Non-root container user
- Input validation and sanitization
- Secure logging (no PII in logs)
- HTTPS for external communications
- Kubernetes security contexts

## Testing

### Unit Tests

```bash
mvn test
```

### Integration Tests

```bash
mvn verify
```

## Troubleshooting

### Common Issues

1. **SOAP Connection Timeout**: Check `USPS_PICKUP_TIMEOUT` configuration
2. **Invalid Address**: Verify address format and required fields
3. **Service Unavailable**: Check USPS service status and network connectivity

### Logs

Check application logs for detailed error information:

```bash
kubectl logs -f deployment/carrier-pickup
```

## Contributing

1. Follow the established code patterns and conventions
2. Use constructor injection for dependencies
3. Implement comprehensive error handling
4. Add appropriate logging and monitoring
5. Include unit and integration tests
6. Update documentation for any API changes
