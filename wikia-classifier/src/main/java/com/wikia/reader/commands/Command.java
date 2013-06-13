package com.wikia.reader.commands;

/**
 * Author: Artur Dwornik
 * Date: 30.03.13
 * Time: 17:55
 */
public interface Command {
    String getName();
    void execute(AppParams params);
}
