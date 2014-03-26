package com.wikia.classifier.filters.text;

import com.wikia.api.model.PageInfo;
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
        } catch (FileNotFoundException | JAXBException | LinkTargetException | CompilerException e) {
            logger.warn("Cannot parse wikipage.", e);
            return null; // throw !!
        }
    }
}
