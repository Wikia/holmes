package com.wikia.api.client.response;/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 09:40
 */

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class RevisionsQueryResponseWrapper extends ResponseWrapper<RevisionsQueryResponse> implements Serializable {
    private static final long serialVersionUID = -8908048824030547211L;

    public RevisionsQueryPage singlePageRevision() {
        Set<Map.Entry<Long,RevisionsQueryPage>> entries = this.getQueryResponse()
                .getPages()
                .entrySet();
        if( entries.size() != 1 ) {
            throw new IllegalStateException("Unexpected number of pages.");
        }
        return entries.iterator().next().getValue();
    }
}
