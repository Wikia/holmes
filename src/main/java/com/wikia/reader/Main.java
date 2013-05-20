package com.wikia.reader;

import com.wikia.reader.input.TextChunk;
import com.wikia.reader.nlp.annie.AnnieHelper;
import com.wikia.reader.providers.Provider;
import com.wikia.reader.providers.api.WikiApiProviderFactory;
import com.wikia.reader.providers.api.WikiFixedProviderFactory;
import com.wikia.reader.util.AsyncQueue;
import com.wikia.reader.util.AsyncQueues;
import gate.Annotation;
import gate.util.GateException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Author: Artur Dwornik
 * Date: 23.03.13
 * Time: 23:33
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //annotateTextExample("http://callofduty.wikia.com", Arrays.asList("John_Price"));
        //readWikiSubsetExample("http://callofduty.wikia.com", Arrays.asList("M4A1"));
        //readWikiSubsetExample("http://callofduty.wikia.com", Arrays.asList("Prologue (Modern Warfare 3)"));
        //readWikiSubsetExample("http://callofduty.wikia.com", Arrays.asList("Call_of_Duty:_Modern_Warfare_3"));
        //readAllWikiExample();
        //App.main(new String[]{"-v", "crawlAndClassify", "http://callofduty.wikia.com/"});
        App.main(new String[]{"-v", "Server"});
    }

    private static void annotateTextExample(String root, Iterable<String> index) throws IOException {
        Provider provider = new WikiFixedProviderFactory(root, index).get();
        AsyncQueue<TextChunk> queue = provider.provide();
        Iterator<TextChunk> iterator = AsyncQueues.synchronize(queue);
        while( iterator.hasNext() ) {
            TextChunk textChunk = iterator.next();
            System.out.print(textChunk.getWikiText()+"\n");
            //SwebleHelper.render(textChunk.getWikiText(), "Title", false)
            //System.out.print();
            String text = textChunk.getSgml();
            try {
                AnnieHelper.SortedAnnotationList list = AnnieHelper.run(textChunk);

                for(int i=0; i<text.length(); i++) {
                    for(Object o: list) {
                        Annotation annotation = (Annotation)o;
                        if(annotation.getEndNode().getOffset() == i) {
                            System.out.print("{");
                            System.out.print(annotation.getType());
                            System.out.print("}");
                        }
                    }
                    System.out.print(text.charAt(i));
                }
            } catch (GateException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }

    private static void readWikiSubsetExample(String root, Iterable<String> index) throws IOException {
        Provider provider = new WikiFixedProviderFactory(root, index).get();
        AsyncQueue<TextChunk> queue = provider.provide();
        Iterator<TextChunk> iterator = AsyncQueues.synchronize(queue);
        while( iterator.hasNext() ) {
            TextChunk textChunk = iterator.next();
            System.out.print(textChunk.getWikiText()+"\n");
            //SwebleHelper.render(textChunk.getWikiText(), "Title", false)
            //System.out.print();
            try {
                AnnieHelper.SortedAnnotationList list = AnnieHelper.run(textChunk);
                for(Object o: list) {
                    Annotation annotation = (Annotation)o;
                    String name = textChunk.getSgml().substring((int)(long) annotation.getStartNode().getOffset(),
                            (int)(long) annotation.getEndNode().getOffset());
                    System.out.println(name + " is a " + annotation.getType());
                    //System.out.println(annotation);
                }
            } catch (GateException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }

    private static void readAllWikiExample() throws IOException {
        Provider provider = new WikiApiProviderFactory("http://callofduty.wikia.com").get();
        AsyncQueue<TextChunk> queue = provider.provide();
        Iterator<TextChunk> iterator = AsyncQueues.synchronize(queue);
        while( iterator.hasNext() ) {
            TextChunk textChunk = iterator.next();
            System.out.print(textChunk.getSgml()+"\n");
        }
    }
}
