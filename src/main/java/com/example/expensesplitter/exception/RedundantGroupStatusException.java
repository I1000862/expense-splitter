package com.example.expensesplitter.exception;

public class RedundantGroupStatusException extends RuntimeException {
    public RedundantGroupStatusException() {
        super("The group already has this status.");
    }

    public RedundantGroupStatusException(String msg) {
        super(msg);
    }
}
