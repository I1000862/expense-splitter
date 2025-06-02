package com.example.expensesplitter.enums.group;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GroupStatus {
    ACTIVE,
    INACTIVE;

    @JsonCreator
    public static GroupStatus from(String value) {
        return GroupStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}