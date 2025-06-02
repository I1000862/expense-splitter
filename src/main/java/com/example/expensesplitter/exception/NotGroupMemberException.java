package com.example.expensesplitter.exception;

public class NotGroupMemberException extends RuntimeException {


    public NotGroupMemberException() {
        super("User is not a member of this group.");
    }

    public NotGroupMemberException(String msg) {
        super(msg);
    }
}
