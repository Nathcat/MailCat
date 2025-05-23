package com.nathcat.mailcat.smtp;

import com.nathcat.mailcat.smtp.commands.*;

public class MailTransaction {
    public MAIL initiator;
    public RCPT[] recipients;
    public DATA data;
}
