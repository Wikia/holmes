package com.wikia.classifier.commands;

import com.beust.jcommander.Parameter;

import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 30.03.13
 * Time: 17:42
 */
public class AppParams {
    private static Logger logger = Logger.getLogger(AppParams.class.toString());

    @Parameter(names = "-v")
    private boolean verbose = false;

    public boolean isVerbose() {
        return verbose;
    }
}
