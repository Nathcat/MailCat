package com.nathcat.mailcat.smtp.commands;

import com.nathcat.mailcat.smtp.SMTP;

public class HELO extends Command {

    public final String clientDomain;

    public HELO(String[] lines) {
        super(lines);

        this.type = Type.HELO;

        String[] tokens = lines[0].split(SMTP.SP);

        // HELO <SP> <domain> <CRLF>
        clientDomain = tokens[1];
    }
}
