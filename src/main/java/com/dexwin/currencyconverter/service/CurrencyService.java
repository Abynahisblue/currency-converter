package com.dexwin.currencyconverter.service;

import com.dexwin.currencyconverter.dto.CurrencyConversionResponse;

public interface CurrencyService {

    CurrencyConversionResponse convert(String source, String target, double amount);

}
