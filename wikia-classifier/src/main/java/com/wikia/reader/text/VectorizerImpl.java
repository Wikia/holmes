package com.wikia.reader.text;/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 17:28
 */

import com.wikia.reader.input.structured.WikiPageCategory;
import com.wikia.reader.input.structured.WikiPageStructure;
import com.wikia.reader.input.structured.WikiPageTemplateArgument;
import com.wikia.reader.util.IterableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VectorizerImpl implements Vectorizer {
    private static Logger logger = LoggerFactory.getLogger(VectorizerImpl.class);
    private final Tokenizer tokenizer;

    public VectorizerImpl() {
        this(new TokenizerStopwordsFilter(new TokenizerImpl()));
    }

    public VectorizerImpl(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public Map<String, Double> vectorize(WikiPageStructure structure) {
        Map<String, Double> vector = new HashMap<>();

        enterPlain(structure, vector);
        enterArguments(structure, vector);
        enterCategories(structure, vector);

        return vector;
    }

    private void enterCategories(WikiPageStructure structure, Map<String, Double> vector) {
        for(WikiPageCategory category: structure.getCategories()) {
            vector.put("_category_" + category.getTitle(), 1.0);
            for(String token: tokenizer.tokenize(category.getTitle())) {
                vector.put("_category_token_" + category.getTitle(), 1.0);
            }
        }
    }

    private void enterArguments(WikiPageStructure structure, Map<String, Double> vector) {
        for(WikiPageTemplateArgument argument :structure.getTemplateArguments()) {
            if( !argument.getName().isEmpty() ) {
                vector.put("_arg_name_" + argument.getName(), 1.0);
            }
        }
    }

    private void enterPlain(WikiPageStructure structure, Map<String, Double> vector) {
        List<String> tokens = tokenizer.tokenize(structure.getPlain());
        Map<String, Integer> occurrenceMap = IterableUtil.countOccurrences(tokens);
        for(Map.Entry<String, Integer> entry: occurrenceMap.entrySet()) {
            vector.put(entry.getKey(), plainTransform(entry.getValue()));
        }
    }

    protected double plainTransform(int value) {
        if(value < 1) return 0;
        return value;
    }
}
