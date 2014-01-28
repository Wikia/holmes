package com.wikia.classifier;

import com.beust.jcommander.JCommander;
import com.wikia.classifier.commands.*;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class.toString());
    private static Command[] commands = new Command[] {
            new CrawlAndClassifyCommand(),
            new ServerCommand(),
            new TrainCommand(),
            new ClassifyCommand(),
    };

    public static void main(String[] args) {
        AppParams appParams = new AppParams();
        JCommander jc = new JCommander(appParams);

        for(Command command: commands) {
            jc.addCommand(command.getName(), command);
        }

        jc.parse(args);

        if( appParams.isVerbose() ) {
            org.apache.log4j.Logger.getRootLogger().setLevel(Level.ALL);
            logger.info("Switching to verbose mode.");
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
