package com.example.expensesplitter.exception;

public class InactiveGroupException extends RuntimeException {
    public InactiveGroupException() {
        super("Group is not active.");
    }

    public InactiveGroupException(String msg) {
        super(msg);
    }
}
