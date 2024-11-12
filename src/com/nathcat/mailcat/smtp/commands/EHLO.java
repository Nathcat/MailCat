package com.nathcat.mailcat.smtp.commands;

import com.nathcat.mailcat.smtp.SMTP;
import com.nathcat.mailcat.smtp.exceptions.InvalidCommandTypeException;

import java.io.IOException;
import java.io.InputStream;

/**
 * An EHLO SMTP command. As described in <a href="https://datatracker.ietf.org/doc/html/rfc5321#section-4.1.1.1">RFC 5321</a>
 *
 * @author Nathan Baines
 */
public class EHLO extends Command {
    public final String domain;

    public EHLO(String[] lines) throws IOException, InvalidCommandTypeException {
        super(lines);

        String[] tokens = lines[0].split(SMTP.SP);

        //   ehlo           = "EHLO" SP ( Domain / address-literal ) CRLF
        //   helo           = "HELO" SP Domain CRLF
        domain = tokens[1];
    }

    @Override
    public String buildResponse() {
        return "250 " + SMTP.domain + " Hello from MailCat!" + SMTP.CRLF;
    }
}
