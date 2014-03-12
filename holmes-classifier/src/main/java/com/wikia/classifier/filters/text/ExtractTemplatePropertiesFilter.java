package com.wikia.classifier.filters.text;

import com.wikia.classifier.filters.CollectionFilterBase;
import com.wikia.classifier.wikitext.WikiPageFeatures;
import com.wikia.classifier.wikitext.WikiPageTemplate;
import com.wikia.classifier.wikitext.WikiPageTemplateArgument;
import com.wikia.classifier.util.text.Tokenizer;
import com.wikia.classifier.util.text.TokenizerImpl;
import com.wikia.classifier.util.text.TokenizerStopwordsFilter;
import com.wikia.classifier.util.matrix.SparseMatrix;
import com.wikia.classifier.util.StopWordsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class ExtractTemplatePropertiesFilter extends CollectionFilterBase<WikiPageFeatures, SparseMatrix> {
    private static final long serialVersionUID = 4313041391716712506L;
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(ExtractTemplatePropertiesFilter.class);
    private boolean extractTemplateName = true;
    private boolean extractArgval = false;
    private String delimiters = " \r\n\t.,;:'\"()?!<>[]{}|";
    private List<String> stopWords = StopWordsHelper.defaultStopWords();
    Tokenizer tokenizer = new TokenizerStopwordsFilter(
            new TokenizerImpl(delimiters), stopWords, 2);

    public ExtractTemplatePropertiesFilter() {
        super(WikiPageFeatures.class, SparseMatrix.class);
    }

    @Override
    protected SparseMatrix doFilter(Collection<WikiPageFeatures> params) {
        SparseMatrix matrix = new SparseMatrix();
        int i = 0;
        for(WikiPageFeatures wikiPageFeatures : params) {
            for(WikiPageTemplateArgument argument: wikiPageFeatures.getTemplateArguments()) {
                String argName = argument.getName();
                argName = argName.trim().toLowerCase();
                if( argName.length() > 2 ) {
                    matrix.put(String.valueOf(i), "arg:" + argName, 1);
                }
                if(extractArgval) {
                    for(String token: tokenizer.tokenize(argument.getStringValue())) {
                        matrix.put(String.valueOf(i), "argval:" + token.toLowerCase(), 0.01);
                    }
                }
            }
            if(isExtractTemplateName()) {
                for(WikiPageTemplate template: wikiPageFeatures.getTemplates()) {
                    for(String token: tokenizer.tokenize(template.getName())) {
                        matrix.put(String.valueOf(i), "template:" + token.toLowerCase(), 1);
                    }
                    for(String subName: template.getChildNames()) {
                        for(String token: tokenizer.tokenize(subName)) {
                            matrix.put(String.valueOf(i), "template:" + token.toLowerCase(), 1);
                        }
                    }
                }
            }
            i++;
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
