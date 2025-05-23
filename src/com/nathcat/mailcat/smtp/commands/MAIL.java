package com.nathcat.mailcat.smtp.commands;

import com.nathcat.mailcat.smtp.SMTP;
import com.nathcat.mailcat.smtp.exceptions.SyntaxException;

public class MAIL extends Command {
    public MAIL(String[] lines) throws SyntaxException {
        super(lines);

        String[] tokens = lines[0].split(SMTP.SP);

        if (tokens.length != 2) {
            throw new SyntaxException();
        }

        if (!tokens[0].contentEquals("FROM")) {
            throw new SyntaxException();
        }


    }
}
