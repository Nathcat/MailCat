package com.nathcat.mailcat.server;

import com.nathcat.mailcat.smtp.SMTP;
import com.nathcat.mailcat.smtp.exceptions.MissingConfigParameter;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class SMTPServer {
    public static ArrayList<SMTPConnectionHandler> handlers = new ArrayList<>();

    public static void main(String[] args) throws IOException, ParseException, MissingConfigParameter {
        SMTP.getConfig();

        ServerSocket ss = new ServerSocket(SMTP.port);

        while (true) {
            handlers.add(new SMTPConnectionHandler(ss.accept()));
        }
    }
}
