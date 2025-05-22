package com.example.expensesplitter.util;

import com.example.expensesplitter.enums.Currency;

public class CurrencyFormatter {
    public static String formatAmount(double amount, Currency currency) {
        return currency.getSymbol() + String.format("%f", amount);
    }
}
