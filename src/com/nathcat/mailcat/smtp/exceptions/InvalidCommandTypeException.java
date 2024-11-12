package com.nathcat.mailcat.smtp.exceptions;

public class InvalidCommandTypeException extends Exception {
    private String value = "";

    public InvalidCommandTypeException(String v) {
        value = v;
    }

    @Override
    public String getMessage() {
        return "Command type \"" + value + "\" not recognised!";
    }
}
