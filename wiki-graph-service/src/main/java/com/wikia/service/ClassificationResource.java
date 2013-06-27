package com.wikia.service;/**
 * Author: Artur Dwornik
 * Date: 13.04.13
 * Time: 11:05
 */

import com.google.common.base.Strings;
import com.wikia.classifier.text.classifiers.CompositeClassifier;
import com.wikia.classifier.text.data.InstanceSource;
import com.wikia.service.model.ClassificationViewModel;
import com.wikia.service.strategy.UnkwnownWikiException;
import com.wikia.service.strategy.WikiUrlStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URL;

@Path("/classifications")
public class ClassificationResource {
    private static Logger logger = LoggerFactory.getLogger(ClassificationResource.class);
    private static CompositeClassifier classifierManager;
    private WikiUrlStrategy wikiUrlStrategy;
    static {
        // todo: use injection
        setClassifierManager(new CompositeClassifier());
    }

    public static CompositeClassifier getClassifierManager() {
        return classifierManager;
    }

    public static void setClassifierManager(CompositeClassifier classifierManager) {
        ClassificationResource.classifierManager = classifierManager;
    }

    public WikiUrlStrategy getWikiUrlStrategy() {
        return wikiUrlStrategy;
    }

    public void setWikiUrlStrategy(WikiUrlStrategy wikiUrlStrategy) {
        this.wikiUrlStrategy = wikiUrlStrategy;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{wikiName : .+}/{page}")
    public ClassificationViewModel getClassifications(@PathParam("wikiName") String wikiName,@PathParam("page")  String page) throws UnkwnownWikiException {
        logger.debug(String.format("getClassifications(\"%s\",\"%s\")", wikiName, page));
        if( Strings.isNullOrEmpty(wikiName) || Strings.isNullOrEmpty(page) || page.startsWith("Special:") ) {
            throw new UnsupportedOperationException("Wrong url."); // TODO: make me cleaner
        }
        URL url = wikiUrlStrategy.getUrl(wikiName);
        InstanceSource source = new InstanceSource(url, page, null);
        return ClassificationViewModel.fromClassificationResult(getClassifierManager().classify(source));
    }
}
