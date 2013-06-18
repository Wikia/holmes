package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 15:30
 */

import com.wikia.api.model.PageInfo;
import com.wikia.reader.filters.FilterBase;
import com.wikia.reader.input.structured.WikiPageStructure;
import com.wikia.reader.input.structured.WikiStructureHelper;

public class PageToWikiStructureFilter extends FilterBase<PageInfo, WikiPageStructure> {
    private static final long serialVersionUID = 1478170222554444665L;

    public PageToWikiStructureFilter() {
        super(PageInfo.class, WikiPageStructure.class);
    }

    @Override
    protected WikiPageStructure doFilter(PageInfo params) {
        return WikiStructureHelper.parseOrNull(params.getTitle(), params.getWikiText());
    }
}
