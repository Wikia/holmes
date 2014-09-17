package com.wikia.service;

import com.google.common.base.Strings;
import com.wikia.api.model.Page;
import com.wikia.api.model.PageInfo;
import com.wikia.api.service.PageServiceFactory;
import com.wikia.classifier.classifiers.CompositeClassifier;
import com.wikia.classifier.classifiers.exceptions.ClassifyException;
import com.wikia.service.model.ClassificationViewModel;
import com.wikia.service.strategy.UnknownWikiException;
import com.wikia.service.strategy.WikiUrlStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URL;

@Path("/classifications")
public class ClassificationResource {
    private static Logger logger = LoggerFactory.getLogger(ClassificationResource.class);
    private CompositeClassifier classifier;
    private PageServiceFactory pageServiceFactory = new PageServiceFactory();
    private WikiUrlStrategy wikiUrlStrategy;

    public CompositeClassifier getClassifier() {
        return classifier;
    }

    @SuppressWarnings("unused")
    public void setClassifier(CompositeClassifier classifier) {
        this.classifier = classifier;
    }

    public WikiUrlStrategy getWikiUrlStrategy() {
        return wikiUrlStrategy;
    }

    @SuppressWarnings("unused")
    public void setWikiUrlStrategy(WikiUrlStrategy wikiUrlStrategy) {
        this.wikiUrlStrategy = wikiUrlStrategy;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/")
    public ClassificationViewModel getClassifications(@RequestBody Page page) throws UnknownWikiException, IOException, ClassifyException {
        logger.debug(String.format("getClassifications(\"%s\")", page.getTitle()));

        if( Strings.isNullOrEmpty(page.getTitle()) ) {
            logger.warn("Empty page title. Wikia id is " + page.getWikiId() + " article id is " + page.getPageId());
            return  ClassificationViewModel.getDefaultFallback();
        }

        if( Strings.isNullOrEmpty(page.getWikiText()) ) {
            logger.warn("Empty wikitext. Wikia id is " + page.getWikiId() + " article id is " + page.getPageId());
            return  ClassificationViewModel.getDefaultFallback();
        }

        if( page.getTitle().startsWith("Special:") ) {
            logger.warn("Cannot index special page. Wikia id is " + page.getWikiId() + " article id is " + page.getPageId());
            return  ClassificationViewModel.getDefaultFallback();
        }

        return ClassificationViewModel.fromClassificationResult(getClassifier().classify(page));
    }
}
