package com.dexwin.currencyconverter.service;

import com.dexwin.currencyconverter.dto.CurrencyConversionResponse;
import com.dexwin.currencyconverter.dto.ExchangeRateResponse;
import com.dexwin.currencyconverter.util.CurrencySymbolUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.text.DecimalFormat;

/**
 * TODO: Implementation of this class has to be backed by https://api.exchangerate.host/latest?base=EUR&symbols=AUD,CAD,CHF,CNY,GBP,JPY,USD
 */

@Service
public class CurrencyExchangeRateService implements CurrencyService {

    private final RestClient restClient;
    private final String apiKey;
    private final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00"); // Format to 2 decimal places

    public CurrencyExchangeRateService(RestClient.Builder restClientBuilder,
                                       @Value("${api.exchangerate.host.url}") String baseUrl,
                                       @Value("${api.exchangerate.host.key}") String apiKey) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    @Override
    public ResponseEntity<CurrencyConversionResponse> convert(String source, String target, double amount) {
        String url = "/convert?from=" + source + "&to=" + target + "&amount=" + amount + "&access_key=" + apiKey;

        try {
            ExchangeRateResponse response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(ExchangeRateResponse.class);

            if (response != null && response.isSuccess()) {
                String targetSymbol = CurrencySymbolUtil.getSymbol(target);

                // Append the target symbol directly to the result
                // String formattedResult = decimalFormat.format(response.getResult()) + " " + targetSymbol;
                String formattedResult = targetSymbol + decimalFormat.format(response.getResult());

                return ResponseEntity.ok(new CurrencyConversionResponse(
                        true,
                        "Conversion successful",
                        formattedResult
                ));
            } else {
                return ResponseEntity.badRequest().body(new CurrencyConversionResponse(false, "Conversion failed", "0.00"));
            }

        } catch (RestClientException e) {
            return ResponseEntity.internalServerError().body(new CurrencyConversionResponse(false, "Error fetching exchange rate: " + e.getMessage(), "0.00"));
        }
    }
}
