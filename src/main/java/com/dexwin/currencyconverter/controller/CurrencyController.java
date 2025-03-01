package com.dexwin.currencyconverter.controller;

import com.dexwin.currencyconverter.dto.CurrencyConversionResponse;
import com.dexwin.currencyconverter.service.CurrencyExchangeRateService;
import com.dexwin.currencyconverter.service.CurrencyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    private final CurrencyService currencyService;
    private final CurrencyExchangeRateService currencyExchangeRateService;

    public CurrencyController(final CurrencyService currencyService, CurrencyExchangeRateService currencyExchangeRateService) {
        this.currencyService = currencyService;
        this.currencyExchangeRateService = currencyExchangeRateService;
    }

    @GetMapping("/convert")
    public ResponseEntity<CurrencyConversionResponse> convertCurrency(
            @RequestParam String source,
            @RequestParam String target,
            @RequestParam double amount) {
        return currencyExchangeRateService.convert(source, target, amount);
    }
}
