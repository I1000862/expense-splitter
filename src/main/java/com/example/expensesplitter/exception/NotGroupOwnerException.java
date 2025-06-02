package com.example.expensesplitter.exception;

public class NotGroupOwnerException extends RuntimeException {
    public NotGroupOwnerException() {
        super("Only the group owner can perform this action.");
    }

    public NotGroupOwnerException(String msg) {
        super(msg);
    }

}
