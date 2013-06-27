package com.wikia.classifier;

import com.beust.jcommander.JCommander;
import com.wikia.classifier.commands.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 30.03.13
 * Time: 17:33
 */
public class App {
    private static Logger logger = Logger.getLogger(App.class.toString());
    private static Command[] commands = new Command[] {
            new CrawlAndClassifyCommand(),
            new ServerCommand(),
    };

    public static void main(String[] args) {
        AppParams appParams = new AppParams();
        JCommander jc = new JCommander(appParams);

        for(Command command: commands) {
            jc.addCommand(command.getName(), command);
        }

        jc.parse(args);

        if( appParams.isVerbose() ) {
            Logger.getGlobal().setLevel(Level.FINE);
            logger.log(Level.FINE, "Log level set to FINE.");
        }

        Command parsedCommand = null;
        for(Command command: commands) {
            if( command.getName().equals(jc.getParsedCommand()) ) {
                parsedCommand = command;
            }
        }
        if(parsedCommand != null) {
            parsedCommand.execute(appParams);
        } else {
            System.out.print("Command not found.");
        }
    }
}
