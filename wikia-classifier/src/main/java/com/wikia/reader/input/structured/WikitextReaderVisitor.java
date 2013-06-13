package com.wikia.reader.input.structured;

import de.fau.cs.osr.ptk.common.AstVisitor;
import de.fau.cs.osr.ptk.common.ast.AstNode;
import de.fau.cs.osr.ptk.common.ast.ContentNode;
import de.fau.cs.osr.ptk.common.ast.NodeList;
import de.fau.cs.osr.ptk.common.ast.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweble.wikitext.engine.Page;
import org.sweble.wikitext.lazy.encval.IllegalCodePoint;
import org.sweble.wikitext.lazy.parser.*;
import org.sweble.wikitext.lazy.preprocessor.*;


/**
 * Author: Artur Dwornik
 * Date: 05.04.13
 * Time: 20:08
 */
public class WikitextReaderVisitor extends AstVisitor {
    private static Logger logger = LoggerFactory.getLogger(WikitextReaderVisitor.class.toString());
    private final StringBuilder stringBuilder = new StringBuilder();
    private final WikiPageStructure wikiPageStructure;

    public WikitextReaderVisitor(String title) {
        wikiPageStructure = new WikiPageStructure(title);
        stringBuilder.append(title + "\n\n");
    }

    @Override
    protected boolean before(AstNode node) {
        return super.before(node);
    }

    @Override
    protected Object after(AstNode node, Object result) {
        return super.after(node, result);
    }

    private void emmitPlain(String plainText) {
        stringBuilder.append(plainText);
    }

    public WikiPageStructure getStructure() {
        wikiPageStructure.setPlain(stringBuilder.toString());
        return wikiPageStructure;
    }

    /// VISITORS

    public void visit(AstNode node) {
        //logger.info("Node: " + node.getNodeName());
    }

    public void visit(Section section) {
        //logger.info("Section: " + section.getTitle());

        wikiPageStructure.getSections().add(new WikiPageSection(asPlain(section.getTitle())));
        iterate(section.getTitle());
        /*
        for(AstNode node: section.getTitle()) {
            if(node instanceof Text) {
                Text textNode = (Text) node;
                emmitPlain(textNode.getContent());
            } else if( node instanceof Bold ) {
            } else if( node instanceof Italics ) {
            } else if( node instanceof InternalLink ) {

            } else {
                logger.info("Unexpected element in Section title: " + node.getNodeName());
            }
        }*/
        emmitPlain("\n");

        iterate(section.getBody());
    }

    public void visit(Paragraph p)
    {
        logger.debug("Paragraph: ");
        iterate(p.getContent());
        emmitPlain("\n");
    }

    public void visit(Text text)
    {
        logger.debug("Text: " + text.getContent().length());
        emmitPlain(text.getContent());
    }

    public void visit(HorizontalRule hr)
    {
        //logger.info("HorizontalRule: " + hr.getNodeTypeName());
    }

    public void visit(XmlElement e)
    {
        logger.debug("XmlElement: " + e.getNodeTypeName());
        iterate(e.getBody());
    }

    public void visit(NodeList n)
    {
        iterate(n);
    }

    public void visit(Page p)
    {
        iterate(p.getContent());
    }

    public void visit(ImageLink imageLink)
    {
    }

    public void visit(IllegalCodePoint illegalCodePoint)
    {
    }

    public void visit(XmlComment xmlComment)
    {
    }

    public void visit(Template template)
    {
        logger.debug("Template" + template.getName() + " " + template.getChildNames());
        wikiPageStructure.getTemplates().add(new WikiPageTemplate(asPlain(template.getName()), template.getChildNames()));
        iterate(template.getArgs());
    }

    public void visit(TemplateArgument templateArgument)
    {
        String name = asPlain(templateArgument.getName());
        String value = asPlain(templateArgument.getValue());
        wikiPageStructure.getTemplateArguments().add(new WikiPageTemplateArgument(name, value));
        logger.debug("Template argument" + templateArgument.getName() + " = " + templateArgument.getValue());
        iterate(templateArgument.getName());
        emmitPlain(":\n");
        iterate(templateArgument.getValue());
        emmitPlain("\n");
    }

    public void visit(TemplateParameter templateParameter)
    {
        logger.info("Template parameter" + templateParameter.getName() + " = " + templateParameter.getDefaultValue());
    }

    public void visit(TagExtension n)
    {
    }

    public void visit(MagicWord magicWord)
    {
        logger.info("magic word" + magicWord.getWord());
    }

    public void visit(InternalLink link) {
        if(link.getTarget().startsWith("Category:")) {
            visitCategory(link);
        } else {
            logger.debug("Internal Link: " + link.getTitle().getNodeName() + " " + link.getTarget());
            wikiPageStructure.getInternalLinks().add(
                    new WikiPageInternalLink(asPlain(link.getTitle().getContent()), link.getTarget()));
            iterate(link.getTitle().getContent());
        }
    }

    public void visitCategory(InternalLink categoryLink) {
        String title = asPlain(categoryLink.getTitle().getContent());
        String target = categoryLink.getTarget().replaceAll("Category:", "");
        emmitPlain(target + " " + title);
        WikiPageCategory category = new WikiPageCategory(target);
        wikiPageStructure.getCategories().add(category);
    }

    public void visit(ExternalLink link) {
        logger.debug("External Link: " + link.getTitle().getNodeName() + " " + link.getTarget());
        logger.debug("             : " + link.getTitle());
        iterate(link.getTitle());
    }


    public void visit(Bold b)
    {
        iterate(b.getContent());
    }

    public void visit(Italics i)
    {
        iterate(i.getContent());
    }

    public void visit(Whitespace w)
    {
        emmitPlain(" ");
    }

    private String asPlain(NodeList content) {
        StringBuilder sb = new StringBuilder();
        for(AstNode node: content) {
            if(node instanceof Text) {
                sb.append(((Text) node).getContent());
            } else if(node instanceof Whitespace) {
                sb.append(" ");
            } else if(node instanceof InternalLink) {
                sb.append(asPlain(((InternalLink) node).getTitle().getContent()));
            } else if(node instanceof ContentNode) {
                sb.append(asPlain(((ContentNode) node).getContent()));
            }
        }
        return sb.toString();
    }
}
