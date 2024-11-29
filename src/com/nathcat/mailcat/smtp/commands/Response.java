package com.nathcat.mailcat.smtp.commands;

public class Response {
    public final int code;
    public final String content;

    public Response(int code, String content) {
        this.code = code;
        this.content = content;
    }

    @Override
    public String toString() {
        return code + " " + content;
    }
}
