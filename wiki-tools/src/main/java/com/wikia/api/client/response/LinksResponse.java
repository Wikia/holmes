package com.wikia.api.client.response;

import java.io.Serializable;
import java.util.Map;



public class LinksResponse implements Serializable {
    private static final long serialVersionUID = -4636948491531910785L;
    private Map<String, LinksPage> pages;

    public Map<String, LinksPage> getPages() {
        return pages;
    }

    public void setPages(Map<String, LinksPage> pages) {
        this.pages = pages;
    }
}

/*
"pages": {
    "736": {
        "pageid": 736,
        "ns": 0,
        "title": "Albert Einstein",
        "links": [
            {
                "ns": 0,
                "title": "A priori and a posteriori"
            },
            {
                "ns": 0,
                "title": "Adiabatic invariant"
            },
            {
                "ns": 0,
                "title": "Adolf Gr\u00fcnbaum"
            }
        ]
    }
}
*/