package com.example.expensesplitter.exception;

public class AlreadyGroupMemberException extends RuntimeException {
    public AlreadyGroupMemberException() {
        super("User is already a member of this group.");
    }

    public AlreadyGroupMemberException(String msg) {
        super(msg);
    }
}
