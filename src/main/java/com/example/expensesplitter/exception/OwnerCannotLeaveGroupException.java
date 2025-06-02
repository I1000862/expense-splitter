package com.example.expensesplitter.exception;

public class OwnerCannotLeaveGroupException extends RuntimeException {
    public OwnerCannotLeaveGroupException() {
        super("Owner cannot leave the group.");
    }

    public OwnerCannotLeaveGroupException(String msg) {
        super(msg);
    }
}
