package com.wikia.classifier.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.wikia.api.model.PageInfo;
import com.wikia.api.service.PageServiceFactory;
import com.wikia.classifier.text.classifiers.Classifier;
import com.wikia.classifier.text.classifiers.DefaultClassifierFactory;
import com.wikia.classifier.text.classifiers.model.PageWithType;
import com.wikia.classifier.text.classifiers.model.ClassificationResult;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Parameters(commandDescription = "Fetch documents into files.")
public class CrawlAndClassifyCommand implements Command {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CrawlAndClassifyCommand.class.toString());

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") // it's updated
    @Parameter(required = true)
    private List<String> urls = new ArrayList<>();

    @Override
    public String getName() {
        return "CrawlAndClassify";
    }

    @Override
    public void execute(AppParams params) {
        try {
            Classifier classifierManager = new DefaultClassifierFactory()
                    .build(new ArrayList<PageWithType>()); // TODO: get actual training set
            for(String url: urls) {
                for (PageInfo page : new PageServiceFactory().get(new URL(url)).getPages()) {
                    ClassificationResult classification = classifierManager.classify(page);
                    System.out.print(
                            String.format("\"%s\", \"%s\"\n", page.getTitle(), classification.getSingleClass())
                    );
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unexpected exception.", ex);
        }
    }
}
