package com.nathcat.mailcat.smtp;

import com.nathcat.mailcat.smtp.commands.*;
import com.nathcat.mailcat.smtp.exceptions.CommandNotImplementedException;
import com.nathcat.mailcat.smtp.exceptions.InvalidCommandTypeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.stream;

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
    public Command readCommand() throws InvalidCommandTypeException, IOException, CommandNotImplementedException {
        while (is.available() == 0) {
            if (is.available() > 0) {
                break;
            }
        }

        String s = new String(is.readNBytes(is.available()), StandardCharsets.US_ASCII);
        String[] lines = s.split(SMTP.CRLF);
        String[] tokens = lines[0].split(SMTP.SP);

        if (tokens[0].contentEquals("EHLO")) return new EHLO(lines);
        else if (tokens[0].contentEquals("HELO")) return new HELO(lines);
        else if (tokens[0].contentEquals("MAIL")) throw new CommandNotImplementedException();
        else if (tokens[0].contentEquals("RCPT")) throw new CommandNotImplementedException();
        else if (tokens[0].contentEquals("DATA")) throw new CommandNotImplementedException();
        else if (tokens[0].contentEquals("RSET")) throw new CommandNotImplementedException();
        else if (tokens[0].contentEquals("SEND")) throw new CommandNotImplementedException();
        else if (tokens[0].contentEquals("SOML")) throw new CommandNotImplementedException();
        else if (tokens[0].contentEquals("SAML")) throw new CommandNotImplementedException();
        else if (tokens[0].contentEquals("VRFY")) throw new CommandNotImplementedException();
        else if (tokens[0].contentEquals("EXPN")) throw new CommandNotImplementedException();
        else if (tokens[0].contentEquals("HELP")) throw new CommandNotImplementedException();
        else if (tokens[0].contentEquals("NOOP")) throw new CommandNotImplementedException();
        else if (tokens[0].contentEquals("QUIT")) return new QUIT(lines);
        else if (tokens[0].contentEquals("TURN")) throw new CommandNotImplementedException();
        else throw new InvalidCommandTypeException(tokens[0]);
    }

    /**
     * Read a response from the server
     * @return The response from the server
     * @throws IOException
     */
    public Response readResponse() throws IOException {
        while (is.available() == 0) {
            if (is.available() > 0) {
                break;
            }
        }

        int code = Integer.parseInt(
                new String(is.readNBytes(3), StandardCharsets.US_ASCII)
        );

        String content = new String(is.readNBytes(is.available()), StandardCharsets.US_ASCII);

        if (code == 250) {
            if (content.charAt(0) == '-') {
                String[] lines = content.split(SMTP.CRLF);
                List<String> linesStream = Arrays.stream(lines).toList();
                String firstLine = linesStream.get(0).substring(1);
                lines = linesStream.subList(1, linesStream.size()).toArray(new String[0]);
                return new Response(code, firstLine + SMTP.CRLF + String.join(
                        SMTP.CRLF, List.of(lines)
                                .stream().map(e -> e.substring(4)).toList().toArray(new String[0])
                    )
                );
            }
            else {
                return new Response(code, content.trim());
            }
        }
        else {
            return new Response(code, content);
        }
    }
}
