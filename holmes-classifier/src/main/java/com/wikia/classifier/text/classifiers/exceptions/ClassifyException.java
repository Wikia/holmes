package com.wikia.classifier.text.classifiers.exceptions;


import java.io.Serializable;

@SuppressWarnings("unused")
public class ClassifyException extends Exception implements Serializable {
    private static final long serialVersionUID = 3144350973180685017L;

    public ClassifyException() {
    }

    public ClassifyException(String message) {
        super(message);
    }

    public ClassifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassifyException(Throwable cause) {
        super(cause);
    }

    public ClassifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
