package com.dexwin.currencyconverter.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRateResponseTest {

    @Test
    void testConstructor_DefaultValues() {
        // Act
        ExchangeRateResponse response = new ExchangeRateResponse();

        // Assert
        assertNull(response.getResult(), "Default result should be null");
        assertFalse(response.isSuccess(), "Default success should be false");
    }

    @Test
    void testSetterGetter_Result() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        Double expectedResult = 123.45;

        // Act
        response.setResult(expectedResult);
        Double actualResult = response.getResult();

        // Assert
        assertEquals(expectedResult, actualResult, "Result getter should return the value set by setter");
    }

    @Test
    void testSetterGetter_Success() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();

        // Act - Test setting to true
        response.setSuccess(true);
        boolean actualSuccessTrue = response.isSuccess();

        // Assert
        assertTrue(actualSuccessTrue, "Success getter should return true after setting to true");

        // Act - Test setting to false
        response.setSuccess(false);
        boolean actualSuccessFalse = response.isSuccess();

        // Assert
        assertFalse(actualSuccessFalse, "Success getter should return false after setting to false");
    }

    @Test
    void testSetterGetter_NullResult() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        Double initialValue = 100.0;
        response.setResult(initialValue);

        // Act
        response.setResult(null);
        Double actualResult = response.getResult();

        // Assert
        assertNull(actualResult, "Result getter should return null after setting to null");
    }

    @Test
    void testSetterGetter_ZeroResult() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        Double expectedResult = 0.0;

        // Act
        response.setResult(expectedResult);
        Double actualResult = response.getResult();

        // Assert
        assertEquals(expectedResult, actualResult, "Result getter should handle zero values correctly");
        assertNotNull(actualResult, "Zero result should not be null");
    }

    @Test
    void testSetterGetter_NegativeResult() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        Double expectedResult = -123.45;

        // Act
        response.setResult(expectedResult);
        Double actualResult = response.getResult();

        // Assert
        assertEquals(expectedResult, actualResult, "Result getter should handle negative values correctly");
    }

    @Test
    void testSetterGetter_MinimumDoubleValue() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        Double expectedResult = Double.MIN_VALUE;

        // Act
        response.setResult(expectedResult);
        Double actualResult = response.getResult();

        // Assert
        assertEquals(expectedResult, actualResult, "Result getter should handle minimum double values");
    }

    @Test
    void testSetterGetter_MaximumDoubleValue() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        Double expectedResult = Double.MAX_VALUE;

        // Act
        response.setResult(expectedResult);
        Double actualResult = response.getResult();

        // Assert
        assertEquals(expectedResult, actualResult, "Result getter should handle maximum double values");
    }

    @Test
    void testSetterGetter_PositiveInfinity() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        Double expectedResult = Double.POSITIVE_INFINITY;

        // Act
        response.setResult(expectedResult);
        Double actualResult = response.getResult();

        // Assert
        assertEquals(expectedResult, actualResult, "Result getter should handle positive infinity");
        assertTrue(Double.isInfinite(actualResult), "Result should be identified as infinite");
    }

    @Test
    void testSetterGetter_NegativeInfinity() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        Double expectedResult = Double.NEGATIVE_INFINITY;

        // Act
        response.setResult(expectedResult);
        Double actualResult = response.getResult();

        // Assert
        assertEquals(expectedResult, actualResult, "Result getter should handle negative infinity");
        assertTrue(Double.isInfinite(actualResult), "Result should be identified as infinite");
    }

    @Test
    void testSetterGetter_NaN() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        Double expectedResult = Double.NaN;

        // Act
        response.setResult(expectedResult);
        Double actualResult = response.getResult();

        // Assert
        assertTrue(Double.isNaN(actualResult), "Result getter should handle NaN values");
    }

    @Test
    void testResultPrecision() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        Double veryPreciseValue = 123.4567890123456;

        // Act
        response.setResult(veryPreciseValue);
        Double actualResult = response.getResult();

        // Assert
        assertEquals(veryPreciseValue, actualResult, "Result getter should preserve the precision of double values");
    }

    @Test
    void testMultipleSetterCalls() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        Double firstValue = 100.0;
        Double secondValue = 200.0;
        boolean firstSuccessValue = true;
        boolean secondSuccessValue = false;

        // Act & Assert for result
        response.setResult(firstValue);
        assertEquals(firstValue, response.getResult(), "First result value should be stored correctly");

        response.setResult(secondValue);
        assertEquals(secondValue, response.getResult(), "Result value should be updated after second setter call");

        // Act & Assert for success
        response.setSuccess(firstSuccessValue);
        assertTrue(response.isSuccess(), "First success value should be stored correctly");

        response.setSuccess(secondSuccessValue);
        assertFalse(response.isSuccess(), "Success value should be updated after second setter call");
    }
}