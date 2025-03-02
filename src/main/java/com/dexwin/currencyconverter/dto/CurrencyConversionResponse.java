package com.dexwin.currencyconverter.dto;

/**
 * @param result This now includes the symbol
 */
public record CurrencyConversionResponse(boolean success, String message, String result) {
}
