package com.wikia.classifier.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.wikia.api.model.Page;
import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.model.ClassificationResult;
import com.wikia.classifier.classifiers.serialization.GZippedClassifierFileFormat;
import com.wikia.classifier.domain.ClassifiedPage;
import com.wikia.classifier.io.JsonPerLinePageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Parameters(commandDescription = "Fetch documents into files.")
public class ClassifyCommand implements Command {
    private static Logger logger = LoggerFactory.getLogger(ClassifyCommand.class.toString());

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Parameter(required = true, description = "List of json input files")
    private List<String> inputFiles = new ArrayList<>();

    @SuppressWarnings("FieldCanBeLocal")
    @Parameter(names = {"-c", "--classifier"}, required = true, description = "Serialized classifier file name.")
    private String classifierFilePath = "";

    @SuppressWarnings("FieldCanBeLocal")
    @Parameter(names = {"-t", "--threads"}, required = false, description = "Number of threads in thread pool.")
    private Integer numberOfThreads = 4;

    @Override
    public String getName() {
        return "Classify";
    }

    @Override
    public void execute(AppParams params) {
        try {
            ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(numberOfThreads));
            ExecutorService outputThreadService = Executors.newSingleThreadExecutor();
            final AtomicInteger activeJobs = new AtomicInteger(0);

            final ThreadLocal<Classifier> classifierThreadLocal = new ThreadLocal<Classifier>(){
                @Override
                protected Classifier initialValue() {
                    try {
                        return getClassifier();    //To change body of overridden methods use File | Settings | File Templates.
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            for (String inputFile : inputFiles) {
                for (final Iterable<Page> pageBatch : Iterables.partition(
                        new JsonPerLinePageProvider(inputFile).getPages(), 100)) {
                    activeJobs.incrementAndGet();
                    final ListenableFuture<List<String>> future = listeningExecutorService.submit(new Callable<List<String>>() {
                        @Override
                        public List<String> call() throws Exception {
                            List<String> lines = new ArrayList<>();
                            Classifier classifier = classifierThreadLocal.get();
                            for ( Page page: pageBatch ) {
                                if ( page.getTitle() == null ) continue;
                                if ( page.getWikiText() == null ) continue;
                                if ( page.getPageId() == null ) continue;

                                try {
                                    ClassificationResult classification = classifier.classify(page);
                                    ClassifiedPage classifiedPage = new ClassifiedPage(page, classification);
                                    classifiedPage.setWikiText(null);
                                    String outLine = new Gson().toJson(classifiedPage) + "\n";
                                    lines.add(outLine);
                                } catch (Exception ex) {
                                    logger.warn("Exception while processing '" + page.getTitle() + "'." , ex);
                                }
                            }

                            return lines;
                        }
                    });

                    final PrintStream outStream = System.out;

                    future.addListener(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if ( activeJobs.decrementAndGet() == 0 ) {
                                    synchronized (activeJobs) {
                                        activeJobs.notifyAll();
                                    }
                                }

                                List<String> outLines = future.get();
                                for ( String line: outLines ) {
                                    outStream.print(line);
                                }
                            } catch (InterruptedException e) {
                                logger.warn("Processing page batch interrupted.", e);
                            } catch (ExecutionException e) {
                                logger.error("Unhandled exception during processing of page batch.", e);
                            }
                        }
                    }, outputThreadService);

                }
            }

            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized ( activeJobs ) {
                while ( activeJobs.get() != 0 ) { activeJobs.wait(); }
            }
            listeningExecutorService.shutdown();
            outputThreadService.shutdown();
        } catch (Exception ex) {
            logger.error("Unexpected exception.", ex);
        }
    }

    private Classifier getClassifier() throws IOException {
        GZippedClassifierFileFormat gZippedClassifierFileFormat = new GZippedClassifierFileFormat();
        return gZippedClassifierFileFormat.read(classifierFilePath);
    }
}

