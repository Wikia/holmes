package com.wikia.service.strategy;


public class UnknownWikiException extends Exception {
    private static final long serialVersionUID = 7147704600786451521L;

    public UnknownWikiException(String message, Throwable cause) {
        super(message, cause);
    }
}
