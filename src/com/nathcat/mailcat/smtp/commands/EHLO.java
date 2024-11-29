package com.nathcat.mailcat.smtp.commands;

import com.nathcat.mailcat.smtp.SMTP;
import com.nathcat.mailcat.smtp.exceptions.InvalidCommandTypeException;

import java.io.IOException;

/**
 * An EHLO SMTP command. As described in <a href="https://datatracker.ietf.org/doc/html/rfc5321#section-4.1.1.1">RFC 5321</a>
 *
 * @author Nathan Baines
 */
public class EHLO extends Command {
    public final String clientDomain;

    public EHLO(String[] lines) throws IOException, InvalidCommandTypeException {
        super(lines);

        this.type = Type.EHLO;

        String[] tokens = lines[0].split(SMTP.SP);

        //   ehlo           = "EHLO" SP ( Domain / address-literal ) CRLF
        //   helo           = "HELO" SP Domain CRLF
        clientDomain = tokens[1];
    }
}
