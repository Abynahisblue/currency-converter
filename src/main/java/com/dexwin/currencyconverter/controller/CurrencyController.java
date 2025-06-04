package com.dexwin.currencyconverter.controller;

import com.dexwin.currencyconverter.dto.CurrencyConversionResponse;
import com.dexwin.currencyconverter.service.CurrencyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyController {
    @Value("${spring.application.name:beanstalk-demo}")
    private String applicationName;
    private final CurrencyService currencyService;

    public CurrencyController(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/convert")
    public ResponseEntity<CurrencyConversionResponse> convertCurrency(
            @RequestParam String source,
            @RequestParam String target,
            @RequestParam double amount) {
        CurrencyConversionResponse response = currencyService.convert(source, target, amount);

        if (response.success()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }



    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello from AWS Elastic Beanstalk Java App!");
        response.put("application", applicationName);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("version", "1.0.0");
        return response;
    }

    @GetMapping("/api/info")
    public Map<String, Object> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", applicationName);
        response.put("java_version", System.getProperty("java.version"));
        response.put("os_name", System.getProperty("os.name"));
        response.put("available_processors", Runtime.getRuntime().availableProcessors());
        response.put("max_memory", Runtime.getRuntime().maxMemory());
        response.put("free_memory", Runtime.getRuntime().freeMemory());
        return response;
    }

    @GetMapping("/api/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("uptime", ManagementFactory.getRuntimeMXBean().getUptime());
        return response;
    }
}
