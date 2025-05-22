package com.example.expensesplitter.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Currency {
    USD("USD", "$"),
    EUR("EUR", "€"),
    INR("INR", "₹"),
    GBP("GBP", "£"),
    NPR("NPR", "रु");

    private final String code;
    private final String symbol;

    Currency(String code, String symbol) {
        this.code = code;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }


    @JsonCreator
    public static Currency from(String value) {
        return Currency.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}