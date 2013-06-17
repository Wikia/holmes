package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 15:30
 */

import com.wikia.api.model.Page;
import com.wikia.reader.filters.FilterBase;
import com.wikia.reader.input.structured.WikiPageStructure;
import com.wikia.reader.input.structured.WikiStructureHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextChunkToWikiStructureFilter extends FilterBase<Page, WikiPageStructure> {
    private static Logger logger = LoggerFactory.getLogger(TextChunkToWikiStructureFilter.class);

    public TextChunkToWikiStructureFilter() {
        super(Page.class, WikiPageStructure.class);
    }

    @Override
    protected WikiPageStructure doFilter(Page params) {
        return WikiStructureHelper.parseOrNull(params.getTitle(), params.getWikiText());
    }
}
