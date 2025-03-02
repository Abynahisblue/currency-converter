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

<h3>Currency Conversion Application</h3>

A RESTful API for real-time currency conversion using Spring Boot 3.2.

Table of Contents

Project Setup

Usage

Error Handling

Unit Testing

Deployment

Project Documentation

Project Setup

To get a local copy up and running, follow these steps.

Prerequisites

To install and run the application, ensure you have the following:

Java 17 or later

Maven

Docker (optional for containerized deployment)

Local Setup

Clone the repository:

git clone https://github.com/your-repo/currency-conversion.git

Navigate to the project directory:

cd currency-conversion

Build the application:

mvn clean install

Run the application:

mvn spring-boot:run

The logs indicate the application is running:

INFO 12345 --- [main] com.example.CurrencyApplication : Started CurrencyApplication in 5.123 seconds

Usage

The application provides a REST API for currency conversion.

API Endpoints

Endpoint

Method

Description

/convert

GET

Converts an amount from one currency to another

Example Request

curl -X GET "http://localhost:8080/convert?source=USD&target=EUR&amount=100"

Example Response

{
    "success": true,
    "message": "Conversion successful",
    "result": "¥105,437.53"
}

Error Handling

The application provides detailed error responses for various cases:

Invalid Currency Code: Returns an error if an unsupported currency is used.

API Failures: Handles failures from the external exchange rate provider.

Validation Errors: Ensures numerical values for the amount.

Exception Logging: Captures unexpected errors for debugging.

Unit Testing

The application is tested using JUnit 5 and Mockito:

Mocking API Calls: Simulates responses from the exchange rate provider.

Validation Testing: Ensures conversion calculations are correct.

Edge Case Testing: Covers scenarios like zero values and incorrect inputs.

To run tests:

mvn test

Deployment

Running with Docker

To run the application using Docker:

Build the Docker image:

docker build -t currency-conversion-app .

Run the container:

docker run -p 8080:8080 currency-conversion-app

Project Documentation

Design and Architectural Decisions

The system follows a layered architecture:

Controller Layer: Handles HTTP requests (CurrencyController).

Service Layer: Implements business logic (CurrencyExchangeRateService).

Utility Classes: Provides helper functions (CurrencySymbolUtil).

Configuration: Manages external API settings via application.properties.

Tools Used

Spring Boot: Provides a robust backend framework.

RestClient: Fetches exchange rates from an external API.

JUnit & Mockito: Used for unit testing.

Docker: Supports containerized deployment.

Future Improvements

Caching: Implementing Redis to reduce API calls.

Authentication: Adding security for API access.

GraphQL Support: Allowing flexible queries for currency conversion.

Conclusion

This application provides an efficient and scalable solution for currency conversion using Spring Boot, external APIs, and best practices in REST API design, error handling, and testing.
