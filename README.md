# Currency Conversion Application

A RESTful API for real-time currency conversion using Spring Boot 3.2.

## Table of Contents

1. [Project Setup](#project-setup)
2. [Usage](#usage)
3. [Error Handling](#error-handling)
4. [Unit Testing](#unit-testing)
5. [Deployment](#deployment)
6. [Project Documentation](#project-documentation)

## Project Setup

To get a local copy up and running, follow these steps.

### Prerequisites

To install and run the application, ensure you have the following:

- Java 17 or later
- Maven
- Docker (optional for containerized deployment)

### Local Setup

- Clone the repository:

```sh
git clone https://github.com/your-repo/currency-conversion.git
```

- Navigate to the project directory:

```sh
cd currency-conversion
```

- Build the application:

```sh
mvn clean install
```

- Run the application:

```sh
mvn spring-boot:run
```

The logs indicate the application is running:

```sh
INFO 12345 --- [main] com.example.CurrencyApplication : Started CurrencyApplication in 5.123 seconds
```

### Usage

The application provides a REST API for currency conversion.

#### API Endpoints

- ``: Converts an amount from one currency to another.

#### Example Request

```sh
curl -X GET "http://localhost:8080/convert?source=USD&target=EUR&amount=100"
```

#### Example Response

```json
{
    "success": true,
    "query": {
        "from": "USD",
        "to": "JPY",
        "amount": 700.0
    },
    "result": "¥105,437.48"
}
```

## Error Handling

The application provides detailed error responses for various cases:

- **Invalid Currency Code**: Returns an error if an unsupported currency is used.
- **API Failures**: Handles failures from the external exchange rate provider.
- **Validation Errors**: Ensures numerical values for the amount.
- **Exception Logging**: Captures unexpected errors for debugging.

## Unit Testing

The application is tested using JUnit 5 and Mockito:

- **Mocking API Calls**: Simulates responses from the exchange rate provider.
- **Validation Testing**: Ensures conversion calculations are correct.
- **Edge Case Testing**: Covers scenarios like zero values and incorrect inputs.

To run tests:

```sh
mvn test
```

## Deployment

### Running with Docker

To run the application using Docker:

- Build the Docker image:

```sh
docker build -t currency-conversion-app .
```

- Run the container:

```sh
docker run -p 8080:8080 currency-conversion-app
```

## Project Documentation

### Design and Architectural Decisions

The system follows a layered architecture:

- **Controller Layer**: Handles HTTP requests (CurrencyController).
- **Service Layer**: Implements business logic (CurrencyExchangeRateService).
- **Utility Classes**: Provides helper functions (CurrencySymbolUtil).
- **Configuration**: Manages external API settings via `application.properties`.

### Tools Used

- **Spring Boot**: Provides a robust backend framework.
- **RestClient**: Fetches exchange rates from an external API.
- **JUnit & Mockito**: Used for unit testing.
- **Docker**: Supports containerized deployment.

### Future Improvements

- **Caching**: Implementing Redis to reduce API calls.
- **Authentication**: Adding security for API access.
- **GraphQL Support**: Allowing flexible queries for currency conversion.

## Conclusion

This application provides an efficient and scalable solution for currency conversion using Spring Boot, external APIs, and best practices in REST API design, error handling, and testing.


