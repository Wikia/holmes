package com.wikia.service.strategy;


public class UnkwnownWikiException extends Exception {
    private static final long serialVersionUID = 7147704600786451521L;

    public UnkwnownWikiException(String message, Throwable cause) {
        super(message, cause);
    }
}
