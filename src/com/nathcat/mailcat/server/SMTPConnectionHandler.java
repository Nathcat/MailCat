package com.nathcat.mailcat.server;

import com.nathcat.mailcat.smtp.SMTPInputStream;
import com.nathcat.mailcat.smtp.SMTPOutputStream;
import com.nathcat.mailcat.smtp.commands.Command;
import com.nathcat.mailcat.smtp.exceptions.InvalidCommandTypeException;

import java.io.IOException;
import java.net.Socket;

public class SMTPConnectionHandler extends Thread {
    private Socket s;
    private SMTPInputStream in;
    private SMTPOutputStream out;

    public SMTPConnectionHandler(Socket s) throws IOException {
        this.s = s;
        in = new SMTPInputStream(this.s.getInputStream());
        out = new SMTPOutputStream(this.s.getOutputStream());
        setDaemon(true);
        start();
    }

    public void log(String str) {
        System.out.println("SMTP Handler " + getId() + ": " + str);
    }

    public void logError(String str) {
        System.out.println("\033[31;1mSMTP Handler " + getId() + " error: " + str + "\033[0m");
    }

    @Override
    public void run() {
        log("Got connection, waiting for command");

        while (true) {
            try {
                Command command = in.readCommand();

                log("Received command:\n" + command + "\n");

                out.writeCommand(command);
            } catch (InvalidCommandTypeException | IOException e) {
                logError(e.getClass().getName() + ": " + e.getMessage());
                break;
            }
        }
    }
}
