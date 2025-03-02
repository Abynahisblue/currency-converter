package com.dexwin.currencyconverter.service;

import com.dexwin.currencyconverter.dto.CurrencyConversionResponse;
import com.dexwin.currencyconverter.dto.ExchangeRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyExchangeRateServiceTest {

    @Mock
    private RestClient.Builder restClientBuilder;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private CurrencyExchangeRateService currencyService;
    private final String BASE_URL = "https://api.exchangerate.host";
    private final String API_KEY = "test-api-key";

    @BeforeEach
    void setUp() {
        when(restClientBuilder.baseUrl(anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);

        currencyService = new CurrencyExchangeRateService(restClientBuilder, BASE_URL, API_KEY);
    }

    @Test
    void convert_SuccessfulConversion_ReturnsCorrectFormattedResponse() {
        // Arrange
        String source = "USD";
        String target = "EUR";
        double amount = 100.0;
        double result = 85.23;
        String expectedUrl = "/convert?from=USD&to=EUR&amount=100.0&access_key=test-api-key";

        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        mockResponse.setSuccess(true);
        mockResponse.setResult(result);

        when(requestHeadersUriSpec.uri(expectedUrl)).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(ExchangeRateResponse.class)).thenReturn(mockResponse);

        // Act
        CurrencyConversionResponse response = currencyService.convert(source, target, amount);

        // Assert
        assertTrue(response.success());
        assertEquals("Conversion successful", response.message());
        assertEquals("€85.23", response.result());

        verify(requestHeadersUriSpec).uri(expectedUrl);
        verify(responseSpec).body(ExchangeRateResponse.class);
    }

    @Test
    void convert_WithZeroAmount_ReturnsZeroFormattedResponse() {
        // Arrange
        String source = "USD";
        String target = "EUR";
        double amount = 0.0;
        double result = 0.0;

        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        mockResponse.setSuccess(true);
        mockResponse.setResult(result);

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(ExchangeRateResponse.class)).thenReturn(mockResponse);

        // Act
        CurrencyConversionResponse response = currencyService.convert(source, target, amount);

        // Assert
        assertTrue(response.success());
        assertEquals("€0.00", response.result());
    }

    @Test
    void convert_WithLargeAmount_HandlesFormattingCorrectly() {
        // Arrange
        String source = "JPY";
        String target = "USD";
        double amount = 1000000.0;
        double result = 9152.76;

        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        mockResponse.setSuccess(true);
        mockResponse.setResult(result);

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(ExchangeRateResponse.class)).thenReturn(mockResponse);

        // Act
        CurrencyConversionResponse response = currencyService.convert(source, target, amount);

        // Assert
        assertTrue(response.success());
        assertEquals("$9,152.76", response.result());
    }

    @Test
    void convert_WithNegativeAmount_HandlesFormattingCorrectly() {
        // Arrange
        String source = "USD";
        String target = "GBP";
        double amount = -100.0;
        double result = -77.35;

        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        mockResponse.setSuccess(true);
        mockResponse.setResult(result);

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(ExchangeRateResponse.class)).thenReturn(mockResponse);

        // Act
        CurrencyConversionResponse response = currencyService.convert(source, target, amount);

        // Assert
        assertTrue(response.success());
        assertEquals("£-77.35", response.result());
    }

    @Test
    void convert_ApiReturnsFalseSuccess_ReturnsBadRequest() {
        // Arrange
        String source = "USD";
        String target = "EUR";
        double amount = 100.0;

        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        mockResponse.setSuccess(false);

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(ExchangeRateResponse.class)).thenReturn(mockResponse);

        // Act
        CurrencyConversionResponse response = currencyService.convert(source, target, amount);

        // Assert
        assertFalse(response.success());
        assertEquals("Conversion failed", response.message());
        assertEquals("0.00", response.result());
    }

    @Test
    void convert_ApiReturnsNull_ReturnsBadRequest() {
        // Arrange
        String source = "USD";
        String target = "EUR";
        double amount = 100.0;

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(ExchangeRateResponse.class)).thenReturn(null);

        // Act
        CurrencyConversionResponse response = currencyService.convert(source, target, amount);

        // Assert
        assertFalse(response.success());
        assertEquals("Conversion failed", response.message());
        assertEquals("0.00", response.result());
    }

    @Test
    void convert_ApiThrowsRestClientException_ReturnsError() {
        // Arrange
        String source = "USD";
        String target = "EUR";
        double amount = 100.0;
        String errorMessage = "API service unavailable";

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenThrow(new RestClientException(errorMessage));

        // Act
        CurrencyConversionResponse response = currencyService.convert(source, target, amount);

        // Assert
        assertFalse(response.success());
        assertEquals("Error fetching exchange rate: " + errorMessage, response.message());
        assertEquals("0.00", response.result());
    }

    @Test
    void convert_WithInvalidCurrencyCodes_HandlesApiErrorsCorrectly() {
        // Arrange
        String source = "INVALID";
        String target = "EUR";
        double amount = 100.0;
        String errorMessage = "Invalid currency code";

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenThrow(new RestClientResponseException(errorMessage, 400, "Bad Request", null, null, null));

        // Act
        CurrencyConversionResponse response = currencyService.convert(source, target, amount);

        // Assert
        assertFalse(response.success());
        assertTrue(response.message().contains(errorMessage));
        assertEquals("0.00", response.result());
    }

    @Test
    void convert_SameCurrencySourceAndTarget_FormatsCorrectly() {
        // Arrange
        String source = "USD";
        String target = "USD";
        double amount = 100.0;
        double result = 100.0;

        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        mockResponse.setSuccess(true);
        mockResponse.setResult(result);

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(ExchangeRateResponse.class)).thenReturn(mockResponse);

        // Act
        CurrencyConversionResponse response = currencyService.convert(source, target, amount);

        // Assert
        assertTrue(response.success());
        assertEquals("$100.00", response.result());
    }

    @Test
    void convert_VerifiesCorrectUrlConstruction() {
        // Arrange
        String source = "GBP";
        String target = "CAD";
        double amount = 50.50;

        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

        when(requestHeadersUriSpec.uri(urlCaptor.capture())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(ExchangeRateResponse.class)).thenReturn(new ExchangeRateResponse());

        // Act
        currencyService.convert(source, target, amount);

        // Assert
        String capturedUrl = urlCaptor.getValue();
        assertEquals("/convert?from=GBP&to=CAD&amount=50.5&access_key=test-api-key", capturedUrl);
    }
}