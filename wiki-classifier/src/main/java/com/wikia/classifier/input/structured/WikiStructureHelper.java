package com.wikia.classifier.input.structured;

import com.wikia.api.model.Page;
import com.wikia.api.service.PageServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweble.wikitext.engine.*;
import org.sweble.wikitext.engine.Compiler;
import org.sweble.wikitext.engine.utils.SimpleWikiConfiguration;
import org.sweble.wikitext.lazy.LinkTargetException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 01:40
 */
public class WikiStructureHelper {
    private static Logger logger = LoggerFactory.getLogger(WikiStructureHelper.class.toString());

    public static WikiPageStructure parse(Page textChunk) throws FileNotFoundException, JAXBException, LinkTargetException, CompilerException {
        return parse(textChunk.getTitle(), textChunk.getWikiText());
    }

    public static WikiPageStructure parse(String title, String wikiText) throws FileNotFoundException, JAXBException, LinkTargetException, CompilerException {

        SimpleWikiConfiguration config = new SimpleWikiConfiguration(
                "classpath:/org/sweble/wikitext/engine/SimpleWikiConfiguration.xml");
        org.sweble.wikitext.engine.Compiler compiler = new Compiler(config);
        PageTitle pageTitle = PageTitle.make(config, title);
        PageId pageId = new PageId(pageTitle, -1);
        CompiledPage cp = compiler.postprocess(pageId, wikiText, null);
        WikitextReaderVisitor p = new WikitextReaderVisitor(title);
        p.go(cp.getPage());
        return p.getStructure();
    }

    public static WikiPageStructure parseOrNull(String title, String wikiText) {
        try {
            return parse(title, wikiText);
        } catch (FileNotFoundException e) {
            logger.error("Wiki parsing exception.", e);
        } catch (JAXBException e) {
            logger.error("Wiki parsing exception.", e);
        } catch (LinkTargetException e) {
            logger.error("Wiki parsing exception.", e);
        } catch (CompilerException e) {
            logger.error("Wiki parsing exception.", e);
        }
        return null;
    }

    public static void main(String[] args) throws IOException, LinkTargetException, JAXBException, CompilerException {
        Page chunk = new PageServiceFactory().get(new URL("http://callofduty.wikia.com")).getPage("John_Price");

        WikiPageStructure structure = parse(chunk);
        System.out.print(structure.getPlain());
        System.out.println("links: ");
        for(WikiPageInternalLink link: structure.getInternalLinks()) {
            System.out.println(link.getTitle() + " : " + link.getTo());
        }
        System.out.println();
        System.out.println("categories: ");
        for(WikiPageCategory link: structure.getCategories()) {
            System.out.println(link.getTitle());
        }
        System.out.println();
        System.out.println("arguments: ");
        for(WikiPageTemplateArgument arg: structure.getTemplateArguments()) {
            if( !arg.getName().isEmpty() ) {
                System.out.println(arg.getName());
            }
        }
    }
}
