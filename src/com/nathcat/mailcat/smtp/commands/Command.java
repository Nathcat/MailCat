package com.nathcat.mailcat.smtp.commands;

import com.nathcat.mailcat.smtp.SMTP;
import com.nathcat.mailcat.smtp.exceptions.InvalidCommandTypeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Command {
    public enum Type {
        EHLO,
        MAIL
    }

    public Type type;
    protected String[] lines;

    public Command(String[] lines) {
        this.lines = lines;
    }

    /**
     * Build a response from this command
     * @return The response to reply to the originator with
     */
    public String buildResponse() {
        return null;
    }
}
