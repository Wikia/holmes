package com.wikia.api.client.response;

import com.google.gson.annotations.SerializedName;

/**
 * Author: Artur Dwornik
 * Date: 28.06.13
 * Time: 02:58
 *
 */
public class LinksResponseWrapper extends ResponseWrapper<LinksResponse> {
    private static final long serialVersionUID = 6207163753857853709L;
    @SerializedName("query-continue")
    private LinksQueryContinue queryContinue;

    public LinksQueryContinue getQueryContinue() {
        return queryContinue;
    }

    public void setQueryContinue(LinksQueryContinue queryContinue) {
        this.queryContinue = queryContinue;
    }
}

/*
{
    "query-continue": {
        "links": {
            "plcontinue": "736|0|Adolf_Hitler"
        }
    },
    "query": {
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
                        "title": "Aarau"
                    },
                    {
                        "ns": 0,
                        "title": "Abba Eban"
                    },
                    {
                        "ns": 0,
                        "title": "Abdominal aortic aneurysm"
                    },
                    {
                        "ns": 0,
                        "title": "Abraham Pais"
                    },
                    {
                        "ns": 0,
                        "title": "Absent-minded professor"
                    },
                    {
                        "ns": 0,
                        "title": "Absorption refrigerator"
                    },
                    {
                        "ns": 0,
                        "title": "Action-angle variables"
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
    }
}
*/