package com.nathcat.mailcat.smtp.commands;

import com.nathcat.mailcat.smtp.SMTP;

public class QUIT extends Command {

    public QUIT(String[] lines) {
        super(lines);

        this.type = Type.QUIT;
    }
}
