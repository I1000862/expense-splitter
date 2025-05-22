package com.example.expensesplitter.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GroupType {
    SHARED,
    PERSONAL;

    @JsonCreator
    public static GroupType from(String value) {
        return GroupType.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }

}