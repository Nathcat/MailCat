package com.nathcat.mailcat.smtp;

import com.nathcat.mailcat.smtp.commands.Command;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Wrap an output stream
 */
public class SMTPOutputStream extends OutputStream {
    private final OutputStream os;

    public SMTPOutputStream(OutputStream os) {
        this.os = os;
    }

    @Override
    public void write(int b) throws IOException {
        this.os.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.os.write(b);
    }
}
