package com.dexwin.currencyconverter.service;

import com.dexwin.currencyconverter.dto.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

/**
 * TODO: Implementation of this class has to be backed by https://api.exchangerate.host/latest?base=EUR&symbols=AUD,CAD,CHF,CNY,GBP,JPY,USD
 */

@Service
public class CurrencyExchangeRateService implements CurrencyService {

    private final RestClient restClient;
    private final String apiKey;

    public CurrencyExchangeRateService(RestClient.Builder restClientBuilder,
                                       @Value("${api.exchangerate.host.url}") String baseUrl,
                                       @Value("${api.exchangerate.host.key}") String apiKey) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    @Override
    public double convert(String source, String target, double amount) {
        String url = "/convert?from=" + source + "&to=" + target + "&amount=" + amount + "&access_key=" + apiKey;
        System.out.print(url);
        ExchangeRateResponse response = restClient.get()
                .uri(url)
                .retrieve()
                .body(ExchangeRateResponse.class);

        if(response != null && response.isSuccess()){
            return response.getResult();
        }
        return 0;
    }

}

