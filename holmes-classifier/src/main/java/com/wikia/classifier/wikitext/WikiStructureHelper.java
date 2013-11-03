package com.wikia.classifier.wikitext;

import com.wikia.api.model.PageInfo;
import org.sweble.wikitext.engine.*;
import org.sweble.wikitext.engine.Compiler;
import org.sweble.wikitext.engine.utils.SimpleWikiConfiguration;
import org.sweble.wikitext.lazy.LinkTargetException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;


public class WikiStructureHelper {

    public static WikiPageStructure parse(PageInfo textChunk) throws FileNotFoundException, JAXBException, LinkTargetException, CompilerException {
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
}
