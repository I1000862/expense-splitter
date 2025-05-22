package com.example.expensesplitter.exception;

public class InvalidIdException extends IllegalArgumentException{
    public InvalidIdException (String msg) {
        super(msg);
    }
}
