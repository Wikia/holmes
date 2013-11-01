package com.wikia.classifier.commands;

public interface Command {
    String getName();
    void execute(AppParams params);
}
