package com.nathcat.mailcat.smtp;

import com.nathcat.mailcat.smtp.commands.Command;
import com.nathcat.mailcat.smtp.commands.EHLO;
import com.nathcat.mailcat.smtp.exceptions.InvalidCommandTypeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * A class which wraps an existing input stream to allow
 * more convenient reading of SMTP commands.
 * @author Nathan Baines
 */
public class SMTPInputStream extends InputStream {
    private final InputStream is;

    public SMTPInputStream(InputStream is) {
        this.is = is;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    /**
     * Read a command from the wrapped input stream.
     * @return The command read from the input stream
     * @throws InvalidCommandTypeException Thrown if the received command does not match any known SMTP command
     * @throws IOException Thrown in the case of an input / output error
     */
    public Command readCommand() throws InvalidCommandTypeException, IOException {
        while (is.available() == 0) {
            if (is.available() > 0) {
                break;
            }
        }

        String s = new String(is.readNBytes(is.available()), StandardCharsets.US_ASCII);
        String[] lines = s.split(SMTP.CRLF);
        String[] tokens = lines[0].split(SMTP.SP);

        if (tokens[0].contentEquals("EHLO")) return new EHLO(lines);
        else throw new InvalidCommandTypeException(tokens[0]);
    }
}
