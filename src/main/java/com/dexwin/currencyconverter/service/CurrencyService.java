package com.dexwin.currencyconverter.service;

import com.dexwin.currencyconverter.dto.CurrencyConversionResponse;
import org.springframework.http.ResponseEntity;

public interface CurrencyService {

    ResponseEntity<CurrencyConversionResponse> convert(String source, String target, double amount);

}
