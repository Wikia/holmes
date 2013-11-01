package com.wikia.classifier.filters.text;

import com.wikia.api.model.PageInfo;
import com.wikia.classifier.filters.FilterBase;
import com.wikia.classifier.input.WikiPageStructure;
import com.wikia.classifier.input.WikiStructureHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweble.wikitext.engine.CompilerException;
import org.sweble.wikitext.lazy.LinkTargetException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class PageToWikiStructureFilter extends FilterBase<PageInfo, WikiPageStructure> {
    private static final long serialVersionUID = 1478170222554444665L;
    private static Logger logger = LoggerFactory.getLogger(PageToWikiStructureFilter.class.toString());

    public PageToWikiStructureFilter() {
        super(PageInfo.class, WikiPageStructure.class);
    }

    @Override
    protected WikiPageStructure doFilter(PageInfo params) {
        try {
            return WikiStructureHelper.parse(params);
        } catch (FileNotFoundException | JAXBException | LinkTargetException | CompilerException e) {
            logger.warn("Cannot parse wikipage.", e);
            return null;
        }
    }
}
