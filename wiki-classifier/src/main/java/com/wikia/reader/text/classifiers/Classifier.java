package com.wikia.reader.text.classifiers;

import com.wikia.reader.text.data.InstanceSource;
import com.wikia.reader.text.service.model.Classification;

/**
 * Author: Artur Dwornik
 * Date: 14.04.13
 * Time: 11:12
 */
public interface Classifier {
    public Classification classify(InstanceSource source);
}
