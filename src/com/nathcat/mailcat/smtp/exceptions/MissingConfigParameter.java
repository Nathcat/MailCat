package com.nathcat.mailcat.smtp.exceptions;

public class MissingConfigParameter extends Exception {
    public String name;

    public MissingConfigParameter(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "Missing a parameter \"" + name + "\" in the SMTP config file!";
    }
}
