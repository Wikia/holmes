package com.wikia.classifier.commands;

import com.beust.jcommander.Parameter;

public class AppParams {
    @Parameter(names = "-v")
    private boolean verbose = false;

    public boolean isVerbose() {
        return verbose;
    }
}
