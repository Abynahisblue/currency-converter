package com.dexwin.currencyconverter.dto;

public record CurrencyConversionResponse(
        boolean success,
        Query query,
        String result
) {
    public record Query(String from, String to, double amount) {}

}
