package com.wikia.service.strategy;
/**
 * Author: Artur Dwornik
 * Date: 23.06.13
 * Time: 14:50
 */

public class UnkwnownWikiException extends Exception {
    private static final long serialVersionUID = 7147704600786451521L;

    public UnkwnownWikiException(String message, Throwable cause) {
        super(message, cause);
    }
}
