package com.wikia.classifier.wikitext;

import com.wikia.api.model.PageInfo;
import org.sweble.wikitext.engine.*;
import org.sweble.wikitext.engine.Compiler;
import org.sweble.wikitext.engine.config.WikiConfigurationInterface;
import org.sweble.wikitext.engine.utils.SimpleWikiConfiguration;
import org.sweble.wikitext.lazy.LinkTargetException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;


public class WikiStructureHelper {
    // configuration is not thread safe but can be cached, so we're using thread local.
    private static ThreadLocal<WikiConfigurationInterface> threadLocalConfig = new ThreadLocal<WikiConfigurationInterface>() {
        @Override
        protected WikiConfigurationInterface initialValue() {
            try {
                return new SimpleWikiConfiguration("classpath:/org/sweble/wikitext/engine/SimpleWikiConfiguration.xml");
            } catch (FileNotFoundException | JAXBException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static WikiPageStructure parse(PageInfo textChunk) throws FileNotFoundException, JAXBException, LinkTargetException, CompilerException {
        return parse(textChunk.getTitle(), textChunk.getWikiText());
    }

    public static WikiPageStructure parse(String title, String wikiText) throws FileNotFoundException, JAXBException, LinkTargetException, CompilerException {
        WikiConfigurationInterface configuration = threadLocalConfig.get();
        if ( configuration == null ) {
            throw new IllegalStateException("Cannot parse wikitext. WikiConfiguration not loaded.");
        }
        Compiler compiler = new Compiler(configuration);

        PageTitle pageTitle = PageTitle.make(configuration, title);
        PageId pageId = new PageId(pageTitle, -1);
        CompiledPage cp = compiler.postprocess(pageId, wikiText, null);
        WikitextReaderVisitor p = new WikitextReaderVisitor(title);
        p.go(cp.getPage());
        return p.getStructure();
    }
}
