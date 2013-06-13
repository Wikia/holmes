package com.wikia.reader.text;/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 18:08
 */

import com.wikia.reader.input.TextChunk;
import com.wikia.reader.input.structured.WikiPageStructure;
import com.wikia.reader.input.structured.WikiStructureHelper;
import com.wikia.reader.providers.WikiaHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweble.wikitext.engine.CompilerException;
import org.sweble.wikitext.lazy.LinkTargetException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Helper {
    private static Logger logger = LoggerFactory.getLogger(Helper.class);

    public static void main(String[] args) throws IOException, LinkTargetException, JAXBException, CompilerException {
        TextChunk chunk = WikiaHelper.fetch("http://callofduty.wikia.com", "John_Price");
        WikiPageStructure structure = WikiStructureHelper.parse(chunk);
        Map<String, Double> vector = vectorize(structure);
        for (Map.Entry<String, Double> entry: vector.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static Map<String, Double> vectorize(WikiPageStructure structure) {
        Vectorizer vectorizer = new VectorizerImpl();
        return vectorizer.vectorize(structure);
    }

    public static Map<String, Double> vectorize(TextChunk chunk) {
        WikiPageStructure structure = null;
        try {
            structure = WikiStructureHelper.parse(chunk);
            Vectorizer vectorizer = new VectorizerImpl();
            return vectorizer.vectorize(structure);
        } catch (FileNotFoundException e) {
            logger.error("Vectorizer exception.", e);
        } catch (JAXBException e) {
            logger.error("Vectorizer exception.", e);
        } catch (LinkTargetException e) {
            logger.error("Vectorizer exception.", e);
        } catch (CompilerException e) {
            logger.error("Vectorizer exception.", e);
        }
        return null;
        // throw new VectorizerException("Cannot parse instance");
    }
}
