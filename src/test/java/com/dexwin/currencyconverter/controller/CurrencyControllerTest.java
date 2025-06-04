package com.dexwin.currencyconverter.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private double extractNumericResult(String jsonResponse) throws Exception {
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String resultWithSymbol = jsonNode.get("result").asText(); // Extract "€0.96"
        String numericResult = resultWithSymbol.replaceAll("[^0-9.]", ""); // Remove currency symbols
        return Double.parseDouble(numericResult);
    }

//    @Test
//    public void should_convert_EUR_to_USD_with_rate_greater_than_1() throws Exception {
//        String response = this.mockMvc.perform(get("/convert?source=EUR&target=USD&amount=1"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        double convertedValue = extractNumericResult(response);
//        assertThat(convertedValue).isGreaterThan(1.0);
//    }


    @Test
    public void should_convert_USD_to_EUR_with_rate_less_than_1() throws Exception {
        String response = this.mockMvc.perform(get("/convert?source=USD&target=EUR&amount=1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        double convertedValue = extractNumericResult(response);
        assertThat(convertedValue).isLessThan(1.0);
    }
}
