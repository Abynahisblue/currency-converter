<h3>Currency Converter (Coding Test - Software Engineer)</h3>

This exercise focuses on functionality, "clear code" and how well the application is tested.

Imagine you are part of the engineering team  at Dexwin which is responsible for Finance topics
(e.g. invoicing advertisers and paying publishers. For the sake of this exercise, let's assume that Dexwin only operates
with Euro as a currency within the EU, we don't - but let's assume).

There are plans to expand into additional markets outside of the EUR zone. For this, your team has received a request to
build a small helper app to perform currency exchange rate conversions. This is the starting point for adding currency
conversion functionality to the entire platform later on and allowing the business to expand into new regions.

Your task is to complete a concept for a currency conversion solution and to implement a small but key part of the
application including some tests.

For getting actual the conversion rates, assume you would use this service: https://exchangerate.host/
(request sample: `https://api.exchangerate.host/latest?base=EUR&symbols=AUD,CAD,CHF,CNY,GBP,JPY,USD`)

Tasks:
 * Implement 'CurrencyExchangeRateService' (please use this https://exchangerate.host as back-end service)
 * Write at least on test case (or as many as you think necessary)
 * Please introduce any change to any existing class if you think it improves the solution


Good luck!

Currency Conversion Application - Documentation
1. Introduction
The Currency Conversion Application is a RESTful API built using Spring Boot 3.2. It allows users to convert currency values using real-time exchange rates. The application integrates with an external exchange rate provider and follows best practices in REST API design, dependency injection, error handling, and unit testing.

2. Project Architecture
The application follows a layered architecture:
Controller Layer (CurrencyController) – Handles HTTP requests and responses.
Service Layer (CurrencyExchangeRateService) – Implements business logic and fetches exchange rates using RestClient.
Utility Classes (CurrencySymbolUtil) – Provides helper functions, such as currency symbol mapping.
Configuration (application.properties) – Manages external API settings.
3. Implementation Details
3.1 Dependencies
The project is built with Maven and includes the following dependencies:

spring-boot-starter-web – Provides support for RESTful APIs.
spring-boot-starter-webflux – Enables RestClient for making API calls.
spring-boot-starter-test – Provides JUnit and Mockito for testing.
3.2 Controller - CurrencyController
The controller exposes an endpoint for currency conversion:

Receives request parameters (source, target, amount).
Calls the service layer to fetch exchange rates.
Returns a JSON response with the converted amount or an error message.
3.3 Service - CurrencyExchangeRateService
The service class is responsible for:

Building API requests dynamically based on user input.
Using RestClient (from spring-boot-starter-webflux) to fetch exchange rates.
Processing API responses and extracting the conversion result.
Handling exceptions gracefully to ensure system reliability.
3.4 Utility Class - CurrencySymbolUtil
A utility class maps currency codes (e.g., USD, EUR) to their respective symbols ($, €). This ensures that the converted amount is displayed in a user-friendly format.

4. Configuration
The application properties file (application.properties) stores:

Base URL for the exchange rate API.
API key for authentication.
Using externalized configuration makes it easy to change API settings without modifying the code.

5. Error Handling
The application handles errors effectively:

Invalid Currency Codes – Returns an error if the user enters an unsupported currency.
API Failures – Provides a fallback response if the exchange rate provider is down.
Validation Checks – Ensures valid numerical input for the amount.
Exception Logging – Captures errors for debugging.

6. Unit Testing
The application is tested using JUnit 5 and Mockito:
Mocking RestClient Calls – The exchange rate API is simulated in tests.
Validation of Conversion Logic – Ensures calculations and formatting are correct.
Edge Case Testing – Covers scenarios like zero amounts and API failures.

8. Deployment
7.1 Running Locally
Clone the repository.
Navigate to the project folder.
Run mvn spring-boot:run.
Access the API via Postman or a web browser.

7.2 Docker Deployment
A Dockerfile can be created to containerize the application.
The container can be deployed to cloud services.

10. Conclusion
This documentation outlines the Currency Conversion App, covering architecture, API integration, error handling, and testing. It leverages Spring Boot 3.2, RestClient, and best coding practices for a scalable and maintainable system.
