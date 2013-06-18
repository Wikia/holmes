package com.wikia.reader.text.classifiers;

import com.wikia.api.model.PageInfo;
import com.wikia.reader.text.classifiers.exceptions.ClassifyException;
import com.wikia.reader.text.service.model.Classification;

/**
 * Author: Artur Dwornik
 * Date: 14.04.13
 * Time: 11:12
 */
public interface Classifier {
    public Classification classify(PageInfo source) throws ClassifyException;
}
