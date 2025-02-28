package com.dexwin.currencyconverter.dto;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeRateResponse {
    private Double result;
    private  boolean success;


    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
