package com.wikia.classifier.filters.text;

import com.wikia.api.model.PageInfo;
import com.wikia.api.model.Page;
import com.wikia.classifier.filters.FilterBase;
import com.wikia.classifier.wikitext.WikiPageFeatures;
import com.wikia.classifier.wikitext.WikiTextFeaturesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweble.wikitext.engine.CompilerException;
import org.sweble.wikitext.lazy.LinkTargetException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class PageToFeaturesFilter extends FilterBase<PageInfo, WikiPageFeatures> {
    private static final long serialVersionUID = 1478170222554444665L;
    private static Logger logger = LoggerFactory.getLogger(PageToFeaturesFilter.class.toString());

    public PageToFeaturesFilter() {
        super(PageInfo.class, WikiPageFeatures.class);
    }

    @Override
    protected WikiPageFeatures doFilter(PageInfo params) {
        try {
            return WikiTextFeaturesHelper.parse(params);
        } catch (Exception e) {

            String errorMessage = getErrorMessage(params);
            logger.warn( errorMessage, e );

            WikiPageFeatures fallbackResult = new WikiPageFeatures(params.getTitle());
            fallbackResult.setPlain(params.getWikiText());

            return fallbackResult;
        }
    }

    private String getErrorMessage(PageInfo params) {
        // At the moment - all implementations of interface PageInfo
        // are actually instances of Page.class
        // but from point of view of dynamic casting type safety - it is better to do following check
        if( Page.class.isInstance(params) ) {
            Page page = Page.class.cast(params);
            
            return new StringBuilder()
                    .append("Cannot parse wikipage. ")
                    .append("Title is ").append(page.getTitle())
                    .append("Wikia id is ").append(page.getWikiId())
                    .append("Article id is ").append(page.getPageId())
                    .toString();
        } else {
            return "Cannot parse wikipage and params is not instance of Page.class";
        }
    }
}
