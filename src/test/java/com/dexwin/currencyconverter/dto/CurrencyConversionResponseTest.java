package com.dexwin.currencyconverter.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyConversionResponseTest {

    @Test
    void testConstructor_SuccessfulConversion() {
        // Arrange
        boolean success = true;
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);
        String result = "€92.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, query, result);

        // Assert
        assertTrue(response.success(), "Success flag should be true");
        assertEquals(query, response.query(), "Query should match the input");
        assertEquals(result, response.result(), "Result should match the input");
    }

    @Test
    void testConstructor_FailedConversion() {
        // Arrange
        boolean success = false;
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);
        String result = "0.00";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, query, result);

        // Assert
        assertFalse(response.success(), "Success flag should be false");
        assertEquals(query, response.query(), "Query should match the input");
        assertEquals(result, response.result(), "Result should match the input");
    }

    @ParameterizedTest
    @ValueSource(strings = {"$123.45", "€100.00", "£75.50", "¥10,000.00", "₹9,999.99", "0.00", "-$50.25"})
    void testConstructor_VariousFormattedResults(String formattedResult) {
        // Arrange
        boolean success = true;
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, query, formattedResult);

        // Assert
        assertEquals(formattedResult, response.result(), "Result should handle various currency formats");
    }

    @Test
    void testQuery_NullCurrency() {
        // Arrange & Act & Assert
        assertDoesNotThrow(() -> {
            new CurrencyConversionResponse.Query(null, "EUR", 100.0);
        }, "Query should accept null source currency");

        assertDoesNotThrow(() -> {
            new CurrencyConversionResponse.Query("USD", null, 100.0);
        }, "Query should accept null target currency");
    }

    @Test
    void testQuery_EmptyCurrency() {
        // Arrange & Act & Assert
        assertDoesNotThrow(() -> {
            new CurrencyConversionResponse.Query("", "EUR", 100.0);
        }, "Query should accept empty source currency");

        assertDoesNotThrow(() -> {
            new CurrencyConversionResponse.Query("USD", "", 100.0);
        }, "Query should accept empty target currency");
    }

    @Test
    void testQuery_NegativeAmount() {
        // Arrange
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query("USD", "EUR", -100.0);

        // Assert
        assertEquals(-100.0, query.amount(), "Query should accept negative amounts");
    }

    @Test
    void testQuery_ZeroAmount() {
        // Arrange
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query("USD", "EUR", 0.0);

        // Assert
        assertEquals(0.0, query.amount(), "Query should accept zero amount");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testConstructor_EmptyOrNullResult(String result) {
        // Arrange
        boolean success = true;
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, query, result);

        // Assert
        assertEquals(result, response.result(), "Result should handle null or empty values");
    }

    @Test
    void testConstructor_VeryLongCurrencyCodes() {
        // Arrange
        boolean success = true;
        // Create very long currency codes (unusual but testing boundary)
        String longSourceCurrency = "VERYLONGCURRENCYCODE123";
        String longTargetCurrency = "ANOTHERLONGCURRENCYCODE456";
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query(
                longSourceCurrency, longTargetCurrency, 100.0);
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, query, result);

        // Assert
        assertEquals(longSourceCurrency, response.query().from(), "From currency should handle very long codes");
        assertEquals(longTargetCurrency, response.query().to(), "To currency should handle very long codes");
    }

    @Test
    void testConstructor_VeryLargeAmount() {
        // Arrange
        boolean success = true;
        double veryLargeAmount = Double.MAX_VALUE;
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query(
                "USD", "EUR", veryLargeAmount);
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, query, result);

        // Assert
        assertEquals(veryLargeAmount, response.query().amount(), "Amount should handle very large values");
    }

    @Test
    void testConstructor_VeryLongResult() {
        // Arrange
        boolean success = true;
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);
        // Create a result with more than 100 characters (unlikely in practice but testing the boundary)
        StringBuilder resultBuilder = new StringBuilder("$");
        for (int i = 0; i < 100; i++) {
            resultBuilder.append("9,");
        }
        resultBuilder.append("999.99");
        String longResult = resultBuilder.toString();

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, query, longResult);

        // Assert
        assertEquals(longResult, response.result(), "Result should handle very long strings");
    }

    @Test
    void testGetters_ImmutabilityCheck() {
        // Arrange
        boolean success = true;
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, query, result);

        // Assert - Since fields are final, this is mostly a conceptual test
        // to verify that repeated calls to getters return the same values
        assertEquals(success, response.success(), "First call to success()");
        assertEquals(query, response.query(), "First call to query()");
        assertEquals(result, response.result(), "First call to result()");

        assertEquals(success, response.success(), "Second call to success()");
        assertEquals(query, response.query(), "Second call to query()");
        assertEquals(result, response.result(), "Second call to result()");
    }

    @ParameterizedTest
    @MethodSource("provideSpecialCharactersForCurrencies")
    void testQuery_SpecialCharactersInCurrencies(String from, String to) {
        // Arrange & Act
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query(from, to, 100.0);

        // Assert
        assertEquals(from, query.from(), "From currency should handle special characters");
        assertEquals(to, query.to(), "To currency should handle special characters");
    }

    @ParameterizedTest
    @MethodSource("provideSpecialCharactersForResult")
    void testConstructor_SpecialCharactersInResult(String result) {
        // Arrange
        boolean success = true;
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, query, result);

        // Assert
        assertEquals(result, response.result(), "Result should handle special characters");
    }

    @Test
    void testToString_ContainsAllFields() {
        // This test assumes the default toString() from record
        // It's included to ensure toString works as expected

        // Arrange
        boolean success = true;
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, query, result);
        String toStringResult = response.toString();

        // Assert
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("USD"), "toString should contain source currency");
        assertTrue(toStringResult.contains("EUR"), "toString should contain target currency");
        assertTrue(toStringResult.contains("100.0"), "toString should contain amount");
        assertTrue(toStringResult.contains("$123.45"), "toString should contain result");
    }

    @Test
    void testEquals_SameValues() {
        // Arrange
        CurrencyConversionResponse.Query query1 = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);
        CurrencyConversionResponse.Query query2 = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);

        CurrencyConversionResponse response1 = new CurrencyConversionResponse(true, query1, "$123.45");
        CurrencyConversionResponse response2 = new CurrencyConversionResponse(true, query2, "$123.45");

        // Assert
        assertEquals(query1, query2, "Query objects with same values should be equal");
        assertEquals(response1, response2, "Response objects with same values should be equal");
    }

    @Test
    void testEquals_DifferentValues() {
        // Arrange
        CurrencyConversionResponse.Query query1 = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);
        CurrencyConversionResponse.Query query2 = new CurrencyConversionResponse.Query("USD", "GBP", 100.0);

        CurrencyConversionResponse response1 = new CurrencyConversionResponse(true, query1, "$123.45");
        CurrencyConversionResponse response2 = new CurrencyConversionResponse(true, query2, "$123.45");

        // Assert
        assertNotEquals(query1, query2, "Query objects with different values should not be equal");
        assertNotEquals(response1, response2, "Response objects with different values should not be equal");
    }

    @Test
    void testHashCode_ConsistencyCheck() {
        // Arrange
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query("USD", "EUR", 100.0);
        CurrencyConversionResponse response = new CurrencyConversionResponse(true, query, "$123.45");

        // Act
        int hashCode1 = response.hashCode();
        int hashCode2 = response.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2, "HashCode should be consistent across multiple calls");
    }

    private static Stream<Arguments> provideSpecialCharactersForCurrencies() {
        return Stream.of(
                Arguments.of("USD", "EUR"),
                Arguments.of("us-dollar", "euro"),
                Arguments.of("US_DOLLAR", "EURO"),
                Arguments.of("us.dollar", "euro"),
                Arguments.of("us$", "eur€"),
                Arguments.of("dollar@us", "euro@eu"),
                Arguments.of("us#dollar", "eu#euro"),
                Arguments.of("us&dollar", "eu&euro")
        );
    }

    private static Stream<Arguments> provideSpecialCharactersForResult() {
        return Stream.of(
                Arguments.of("$123.45"),
                Arguments.of("€123.45"),
                Arguments.of("£123.45"),
                Arguments.of("¥123.45"),
                Arguments.of("₹123.45"),
                Arguments.of("₽123.45"),
                Arguments.of("$123,456.78"),
                Arguments.of("$1'234'567.89"),  // Swiss format
                Arguments.of("$-123.45"),
                Arguments.of("-$123.45"),
                Arguments.of("$ 123.45"),
                Arguments.of("$+123.45")
        );
    }
}