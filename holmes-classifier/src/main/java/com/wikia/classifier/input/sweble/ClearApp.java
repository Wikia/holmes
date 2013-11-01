package com.wikia.classifier.input.sweble;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 30.03.13
 * Time: 11:01
 */
public class ClearApp {
    private static Logger logger = Logger.getLogger(ClearApp.class.toString());
    
    public static void main(String args[]) throws IOException {

        String wikitext = FileUtils.readFileToString(new File(args[0]));
        String parsed = SwebleHelper.render(wikitext);
        System.out.print(parsed);
    }
}
