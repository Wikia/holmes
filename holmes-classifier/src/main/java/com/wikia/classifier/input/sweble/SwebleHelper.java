package com.wikia.classifier.input.sweble;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.sweble.wikitext.engine.*;
import org.sweble.wikitext.engine.Compiler;
import org.sweble.wikitext.engine.utils.HtmlPrinter;
import org.sweble.wikitext.engine.utils.SimpleWikiConfiguration;
import org.sweble.wikitext.lazy.LinkTargetException;

import javax.xml.bind.JAXBException;

public class SwebleHelper
{
    private static Logger logger = Logger.getLogger(SwebleHelper.class.toString());

    public static void main(String[] args) throws Exception
    {
        if (args.length < 1)
        {
            System.err.println("Usage: java -jar swc-example-basic-VERSION.jar [--html|--weka] TITLE");
            System.err.println();
            System.err.println("  The program will look for a file called `TITLE.wikitext',");
            System.err.println("  parse the file and write an HTML version to `TITLE.html'.");
            return;
        }

        boolean renderHtml = true;

        int i = 0;
        if (args[i].equalsIgnoreCase("--html"))
        {
            renderHtml = true;
            ++i;
        }
        else if (args[i].equalsIgnoreCase("--weka"))
        {
            renderHtml = false;
            ++i;
        }

        String fileTitle = args[i];

        String html = run(
                new File(fileTitle + ".wikitext"),
                fileTitle,
                renderHtml);

        FileUtils.writeStringToFile(
                new File(fileTitle + (renderHtml ? ".html" : ".weka")),
                html);
    }

    static String run(File file, String fileTitle, boolean renderHtml) throws Exception
    {
        String wikitext = FileUtils.readFileToString(file);
        return render(wikitext, fileTitle, renderHtml);


    }

    public static String render(String wikitext) {
        return render(wikitext, "title", false);
    }

    public static String render(String wikitext,String fileTitle,  boolean renderHtml) {
        try {
            // Set-up a simple wiki configuration
            SimpleWikiConfiguration config = new SimpleWikiConfiguration(
                    "classpath:/org/sweble/wikitext/engine/SimpleWikiConfiguration.xml");

            final int wrapCol = 80;

            // Instantiate a compiler for wiki pages
            Compiler compiler = new Compiler(config);

            // Retrieve a page
            PageTitle pageTitle = PageTitle.make(config, fileTitle);

            PageId pageId = new PageId(pageTitle, -1);


            // Compile the retrieved page
            CompiledPage cp = compiler.postprocess(pageId, wikitext, null);

            // Render the compiled page as HTML
            StringWriter w = new StringWriter();

            if (renderHtml)
            {
                HtmlPrinter p = new HtmlPrinter(w, pageTitle.getFullTitle());
                p.setCssResource("/org/sweble/wikitext/engine/utils/HtmlPrinter.css", "");
                p.setStandaloneHtml(true, "");
                p.go(cp.getPage());
                return w.toString();
            }
            else
            {
                WikitextVisitor p = new WikitextVisitor(config, wrapCol);
                return (String) p.go(cp.getPage());
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Error while parsing wikitext.", e);
        } catch (CompilerException e) {
            logger.log(Level.SEVERE, "Error while parsing wikitext.", e);
        } catch (LinkTargetException e) {
            logger.log(Level.SEVERE, "Error while parsing wikitext.", e);
        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "Error while parsing wikitext.", e);
        }
        return null; // FIXME !!
    }
}