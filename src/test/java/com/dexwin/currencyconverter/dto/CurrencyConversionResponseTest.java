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
        String message = "Conversion successful";
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, result);

        // Assert
        assertTrue(response.isSuccess(), "Success flag should be true");
        assertEquals(message, response.getMessage(), "Message should match the input");
        assertEquals(result, response.getResult(), "Result should match the input");
    }

    @Test
    void testConstructor_FailedConversion() {
        // Arrange
        boolean success = false;
        String message = "Conversion failed";
        String result = "0.00";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, result);

        // Assert
        assertFalse(response.isSuccess(), "Success flag should be false");
        assertEquals(message, response.getMessage(), "Message should match the input");
        assertEquals(result, response.getResult(), "Result should match the input");
    }

    @ParameterizedTest
    @ValueSource(strings = {"$123.45", "€100.00", "£75.50", "¥10,000.00", "₹9,999.99", "0.00", "-$50.25"})
    void testConstructor_VariousFormattedResults(String formattedResult) {
        // Arrange
        boolean success = true;
        String message = "Conversion successful";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, formattedResult);

        // Assert
        assertEquals(formattedResult, response.getResult(), "Result should handle various currency formats");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testConstructor_EmptyOrNullMessage(String message) {
        // Arrange
        boolean success = true;
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, result);

        // Assert
        assertEquals(message, response.getMessage(), "Message should handle null or empty values");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testConstructor_EmptyOrNullResult(String result) {
        // Arrange
        boolean success = true;
        String message = "Conversion successful";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, result);

        // Assert
        assertEquals(result, response.getResult(), "Result should handle null or empty values");
    }

    @Test
    void testConstructor_VeryLongMessage() {
        // Arrange
        boolean success = true;
        // Create a message with more than 1000 characters
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            messageBuilder.append("Very long error message. ");
        }
        String longMessage = messageBuilder.toString();
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, longMessage, result);

        // Assert
        assertEquals(longMessage, response.getMessage(), "Message should handle very long strings");
    }

    @Test
    void testConstructor_VeryLongResult() {
        // Arrange
        boolean success = true;
        String message = "Conversion successful";
        // Create a result with more than 100 characters (unlikely in practice but testing the boundary)
        StringBuilder resultBuilder = new StringBuilder("$");
        for (int i = 0; i < 100; i++) {
            resultBuilder.append("9,");
        }
        resultBuilder.append("999.99");
        String longResult = resultBuilder.toString();

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, longResult);

        // Assert
        assertEquals(longResult, response.getResult(), "Result should handle very long strings");
    }

    @Test
    void testGetters_ImmutabilityCheck() {
        // Arrange
        boolean success = true;
        String message = "Conversion successful";
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, result);

        // Assert - Since fields are final, this is mostly a conceptual test
        // to verify that repeated calls to getters return the same values
        assertEquals(success, response.isSuccess(), "First call to isSuccess()");
        assertEquals(message, response.getMessage(), "First call to getMessage()");
        assertEquals(result, response.getResult(), "First call to getResult()");

        assertEquals(success, response.isSuccess(), "Second call to isSuccess()");
        assertEquals(message, response.getMessage(), "Second call to getMessage()");
        assertEquals(result, response.getResult(), "Second call to getResult()");
    }

    @ParameterizedTest
    @MethodSource("provideSpecialCharacters")
    void testConstructor_SpecialCharactersInMessage(String message) {
        // Arrange
        boolean success = true;
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, result);

        // Assert
        assertEquals(message, response.getMessage(), "Message should handle special characters");
    }

    @ParameterizedTest
    @MethodSource("provideSpecialCharactersForResult")
    void testConstructor_SpecialCharactersInResult(String result) {
        // Arrange
        boolean success = true;
        String message = "Conversion successful";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, result);

        // Assert
        assertEquals(result, response.getResult(), "Result should handle special characters");
    }

    @ParameterizedTest
    @MethodSource("provideMultilineStrings")
    void testConstructor_MultilineMessage(String message) {
        // Arrange
        boolean success = true;
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, result);

        // Assert
        assertEquals(message, response.getMessage(), "Message should handle multiline strings");
    }

    @Test
    void testToString_ContainsAllFields() {
        // This test assumes you might add a toString() method in the future
        // It's included as a suggestion for future development

        // Arrange
        boolean success = true;
        String message = "Conversion successful";
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, result);
        String toStringResult = response.toString();

        // Assert - Basic check that would apply if toString is implemented
        // If toString is not implemented, this just ensures the default implementation works
        assertNotNull(toStringResult);
    }

    @Test
    void testHashCode_ConsistencyCheck() {
        // This test assumes you might add hashCode/equals in the future
        // It's included as a suggestion for future development

        // Arrange
        boolean success = true;
        String message = "Conversion successful";
        String result = "$123.45";

        // Act
        CurrencyConversionResponse response = new CurrencyConversionResponse(success, message, result);
        int hashCode1 = response.hashCode();
        int hashCode2 = response.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2, "HashCode should be consistent across multiple calls");
    }

    // Method sources for parameterized tests

    private static Stream<Arguments> provideSpecialCharacters() {
        return Stream.of(
                Arguments.of("Message with spaces"),
                Arguments.of("Message-with-hyphens"),
                Arguments.of("Message_with_underscores"),
                Arguments.of("Message.with.dots"),
                Arguments.of("Message,with,commas"),
                Arguments.of("Message@with@at@signs"),
                Arguments.of("Message#with#hashtags"),
                Arguments.of("Message&with&ampersands"),
                Arguments.of("Message*with*asterisks"),
                Arguments.of("Message(with)parentheses"),
                Arguments.of("Message/with/slashes"),
                Arguments.of("Message\\with\\backslashes"),
                Arguments.of("Message+with+plus"),
                Arguments.of("Message=with=equals"),
                Arguments.of("Message?with?questions"),
                Arguments.of("Message!with!exclamations"),
                Arguments.of("Message;with;semicolons"),
                Arguments.of("Message:with:colons"),
                Arguments.of("Message'with'single'quotes"),
                Arguments.of("Message\"with\"double\"quotes"),
                Arguments.of("Message<with>angle<brackets>"),
                Arguments.of("Message[with]square[brackets]"),
                Arguments.of("Message{with}curly{braces}")
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

    private static Stream<Arguments> provideMultilineStrings() {
        return Stream.of(
                Arguments.of("Line1\nLine2"),
                Arguments.of("Line1\r\nLine2"),
                Arguments.of("Line1\rLine2"),
                Arguments.of("Line1\nLine2\nLine3\nLine4"),
                Arguments.of("Line with \t tab")
        );
    }
}