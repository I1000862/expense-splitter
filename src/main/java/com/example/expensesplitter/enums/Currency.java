package com.example.expensesplitter.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Currency {
    USD,
    EUR,
    INR,
    GBP,
    NPR;

    @JsonCreator
    public static Currency from(String value) {
        return Currency.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}