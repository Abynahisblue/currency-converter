package com.dexwin.currencyconverter.util;

import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

public class CurrencySymbolUtil {

    public static String getSymbol(String currencyCode) {
        try {
            // Get the currency symbol dynamically
            Currency currency = Currency.getInstance(currencyCode);
            return currency.getSymbol(Locale.getDefault()); // Uses system default locale
        } catch (IllegalArgumentException e) {
            return currencyCode;
        }
    }

}


