package com.dexwin.currencyconverter.dto;

public class CurrencyConversionResponse {
    private final boolean success;
    private final String message;
    private final String result;  // This now includes the symbol

    public CurrencyConversionResponse(boolean success, String message, String result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getResult() { return result; }
}
