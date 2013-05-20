package com.wikia.reader.filters.text;/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 22:52
 */

import com.google.common.base.Strings;
import com.wikia.reader.filters.CollectionFilterBase;
import com.wikia.reader.input.structured.WikiPageStructure;
import com.wikia.reader.input.structured.WikiPageTemplate;
import com.wikia.reader.input.structured.WikiPageTemplateArgument;
import com.wikia.reader.text.Tokenizer;
import com.wikia.reader.text.TokenizerImpl;
import com.wikia.reader.text.TokenizerStopwordsFilter;
import com.wikia.reader.text.matrix.SparseMatrix;
import com.wikia.reader.util.StopWordsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExtractTemplatePropertiesFilter extends CollectionFilterBase<WikiPageStructure, SparseMatrix> {
    private static Logger logger = LoggerFactory.getLogger(ExtractTemplatePropertiesFilter.class);
    private boolean extractTemplateName = true;
    private boolean extractArgval = false;
    private String delimiters = " \r\n\t.,;:'\"()?!<>[]{}|";
    private List<String> stopWords = StopWordsHelper.defaultStopWords();
    Tokenizer tokenizer = new TokenizerStopwordsFilter(
            new TokenizerImpl(delimiters), stopWords, 2);

    public ExtractTemplatePropertiesFilter() {
        super(WikiPageStructure.class, SparseMatrix.class);
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageStructure> params) {
        SparseMatrix matrix = new SparseMatrix();
        for(WikiPageStructure wikiPageStructure: params) {
            for(WikiPageTemplateArgument argument: wikiPageStructure.getTemplateArguments()) {
                String argName = argument.getName();
                argName = argName.trim().toLowerCase();
                if( argName.length() > 2 ) {
                    matrix.put(wikiPageStructure.getTitle(), "arg:" + argName, 1);
                }
                if(extractArgval) {
                    for(String token: tokenizer.tokenize(argument.getStringValue())) {
                        matrix.put(wikiPageStructure.getTitle(), "argval:" + token.toLowerCase(), 0.01);
                    }
                }
            }
            if(isExtractTemplateName()) {
                for(WikiPageTemplate template: wikiPageStructure.getTemplates()) {
                    for(String token: tokenizer.tokenize(template.getName())) {
                        matrix.put(wikiPageStructure.getTitle(), "template:" + token.toLowerCase(), 1);
                    }
                    for(String subName: template.getChildNames()) {
                        for(String token: tokenizer.tokenize(subName)) {
                            matrix.put(wikiPageStructure.getTitle(), "template:" + token.toLowerCase(), 1);
                        }
                    }
                }
            }
        }
        return matrix;
    }

    public boolean isExtractTemplateName() {
        return extractTemplateName;
    }

    public void setExtractTemplateName(boolean extractTemplateName) {
        this.extractTemplateName = extractTemplateName;
    }
}
