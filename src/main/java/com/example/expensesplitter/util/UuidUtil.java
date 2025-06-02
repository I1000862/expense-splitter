package com.example.expensesplitter.util;

import com.example.expensesplitter.exception.InvalidIdException;

import java.util.UUID;

public class UuidUtil {
    public static UUID parse(String uuidStr, String fieldName) {
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException ex) {
            throw new InvalidIdException("Invalid UUID format for " + fieldName + ".");
        }
    }
}
