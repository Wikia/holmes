package com.wikia.service;

import com.google.common.base.Strings;
import com.wikia.api.model.PageInfo;
import com.wikia.api.service.PageServiceFactory;
import com.wikia.classifier.classifiers.CompositeClassifier;
import com.wikia.classifier.classifiers.exceptions.ClassifyException;
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

    public void setClassifier(CompositeClassifier classifier) {
        this.classifier = classifier;
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
    public ClassificationViewModel getClassifications(@PathParam("wikiName") String wikiName,@PathParam("page")  String page) throws UnkwnownWikiException, IOException, ClassifyException {
        logger.debug(String.format("getClassifications(\"%s\",\"%s\")", wikiName, page));
        if( Strings.isNullOrEmpty(wikiName) || Strings.isNullOrEmpty(page) || page.startsWith("Special:") ) {
            throw new UnsupportedOperationException("Wrong url."); // TODO: make me cleaner
        }
        URL url = getWikiUrlStrategy().getUrl(wikiName);
        PageInfo pageInfo = pageServiceFactory.get(url).getPage(page);

        return ClassificationViewModel.fromClassificationResult(getClassifier().classify(pageInfo));
    }
}
