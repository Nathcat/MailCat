package com.nathcat.mailcat.server;

import com.nathcat.mailcat.smtp.*;
import com.nathcat.mailcat.smtp.commands.*;
import com.nathcat.mailcat.smtp.exceptions.CommandNotImplementedException;
import com.nathcat.mailcat.smtp.exceptions.InvalidCommandTypeException;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.Stack;

public class SMTPConnectionHandler extends Thread {
    private Socket s;
    private SMTPInputStream in;
    private SMTPOutputStream out;
    private Stack<Command> commandStack;
    private MailTransaction mailTransaction;

    private ICommandHandler handler = new ICommandHandler() {
        @Override
        public String helo(HELO c) {
            return "250 " + SMTP.domain + SMTP.CRLF;
        }

        @Override
        public String ehlo(EHLO c) {
            return "250 " + SMTP.domain + " Hello from MailCat!" + SMTP.CRLF;
        }

        @Override
        public String quit(QUIT c) {
            return "221 OK" + SMTP.CRLF;
        }

        @Override
        public String mail(MAIL c) {
            if (mailTransaction != null) {
                return "501 Mail transaction is already in progress." + SMTP.CRLF;
            }

            mailTransaction = new MailTransaction();
            mailTransaction.initiator = c;

            return "250 OK";
        }
    };

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
        commandStack = new Stack<>();
        log("Got connection, waiting for command");

        try {
            out.write(("220 " + SMTP.domain + " Welcome to MailCat!" + SMTP.CRLF).getBytes(StandardCharsets.US_ASCII));
        } catch (IOException e) {
            logError("Failed on opening message!");

            throw new RuntimeException(e);
        }

        while (true) {
            try {
                Command command = in.readCommand();
                commandStack.push(command);

                log("Received command:\n" + command + "\n");

                String r = handler.handle(command);

                if (r != null) {
                    out.write(r.getBytes(StandardCharsets.US_ASCII));
                    out.flush();
                }

                if (command.getClass() == QUIT.class) {
                    s.close();
                    break;
                }

            } catch (InvalidCommandTypeException e) {
                try {
                    out.write("500 Syntax error, command unrecognized".getBytes(StandardCharsets.US_ASCII));

                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            } catch (CommandNotImplementedException e) {
                try {
                    out.write("502 Command not implemented".getBytes(StandardCharsets.US_ASCII));
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            } catch (IOException e) {
                logError(e.getClass().getName() + ": " + e.getMessage());
                break;
            }
        }
    }
}
