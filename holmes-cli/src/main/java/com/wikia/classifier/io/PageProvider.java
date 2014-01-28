package com.wikia.classifier.io;

import com.wikia.api.model.Page;

import java.io.IOException;

public interface PageProvider {
    Iterable<Page> getPages() throws IOException;
}
