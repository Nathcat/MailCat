package com.nathcat.mailcat.smtp;

import com.nathcat.mailcat.smtp.commands.*;
import com.nathcat.mailcat.smtp.exceptions.CommandNotImplementedException;

/**
 * Allows programs to implement their own handling methods for SMTP commands
 */
public interface ICommandHandler {
    String helo(HELO c);
    String ehlo(EHLO c);
    String quit(QUIT c);

    /**
     * Handle a command received from the endpoint
     * @param c The parsed command from the endpoint
     * @return The string to reply with
     * @throws CommandNotImplementedException Thrown when the requested command has not been implemented yet
     */
    default String handle(Command c) throws CommandNotImplementedException {
        return switch (c.type) {
            case HELO -> helo((HELO) c);
            case EHLO -> ehlo((EHLO) c);
            case QUIT -> quit((QUIT) c);
            case MAIL, DATA, RCPT, RSET, SEND, SOML, SAML, VRFY, EXPN, HELP, NOOP, TURN -> throw new CommandNotImplementedException();
        };
    }
}
