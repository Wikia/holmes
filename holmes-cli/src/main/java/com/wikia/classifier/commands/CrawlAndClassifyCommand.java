package com.wikia.classifier.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.wikia.api.model.PageInfo;
import com.wikia.api.service.PageServiceFactory;
import com.wikia.classifier.text.classifiers.Classifier;
import com.wikia.classifier.text.classifiers.model.ClassificationResult;
import com.wikia.classifier.text.classifiers.serialization.GZippedClassifierFileFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Parameters(commandDescription = "Fetch documents into files.")
public class CrawlAndClassifyCommand implements Command {
    private static Logger logger = LoggerFactory.getLogger(CrawlAndClassifyCommand.class.toString());

    @Parameter(required = true)
    private List<String> urls = new ArrayList<>();

    @Parameter( names = {"-c", "--classifier"}, required = true, description = "Serialized classifier file name.")
    private String classifierFilePath;

    @Override
    public String getName() {
        return "CrawlAndClassify";
    }

    @Override
    public void execute(AppParams params) {
        try {
            Classifier classifierManager = getClassifier();
            for(String url: urls) {
                for (PageInfo page : new PageServiceFactory().get(new URL(url)).getPages()) {
                    ClassificationResult classification = classifierManager.classify(page);
                    System.out.print(
                            String.format("%d,\"%s\",\"%s\"\n", page.getPageId(), page.getTitle(), classification.getSingleClass())
                    );
                }
            }
        } catch (Exception ex) {
            logger.error("Unexpected exception.", ex);
        }
    }

    private Classifier getClassifier() throws IOException {
        GZippedClassifierFileFormat gZippedClassifierFileFormat = new GZippedClassifierFileFormat();
        return gZippedClassifierFileFormat.read(classifierFilePath);
    }
}
