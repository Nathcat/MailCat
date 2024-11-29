package com.nathcat.mailcat.smtp.commands;

import com.nathcat.mailcat.smtp.SMTP;
import com.nathcat.mailcat.smtp.exceptions.InvalidCommandTypeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Command {
    public enum Type {
        EHLO,
        HELO,
        MAIL,
        RCPT,
        DATA,
        RSET,
        SEND,
        SOML,
        SAML,
        VRFY,
        EXPN,
        HELP,
        NOOP,
        QUIT,
        TURN
    }

    public Type type;
    protected String[] lines;

    public Command(String[] lines) {
        this.lines = lines;
    }
}
