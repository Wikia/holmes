package com.wikia;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wikia.classifier.classifiers.model.PageWithType;
import com.wikia.classifier.classifiers.serialization.GZippedClassifierFileFormat;
import com.wikia.classifier.classifiers.training.ClassifierTrainer;
import com.wikia.classifier.classifiers.training.ClassifierTrainerFactory;
import com.wikia.classifier.classifiers.training.ClassifierTrainingResult;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * Trains and verifies classifier.
 */
@SuppressWarnings("unused")
@Mojo(name="train", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class TrainClassifierMojo extends AbstractMojo {

    @Parameter(defaultValue = "", required = true)
    private String trainingSet = null;
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/")
    private File outputPath = null;
    @Component
    private MavenProjectHelper projectHelper = null;
    @Component
    private MavenProject mavenProject = null;

    public void execute() throws MojoExecutionException {
        if ( trainingSet == null || trainingSet.isEmpty() ) {
            throw new MojoExecutionException("No training file specified.");
        }

        ClassifierTrainingResult trainingResult = train(trainingSet);

        getLog().info(String.format("Success Rate: %.2f", trainingResult.getSuccessRate() * 100.0));
        getLog().info(String.format("False Negatives: %.2f", trainingResult.getFalseNegativeErrorRate() * 100.0));
        getLog().info(String.format("False Positives: %.2f", trainingResult.getFalsePositiveErrorRate() * 100.0));
        getLog().info(String.format("Wrong Type: %.2f", trainingResult.getWrongTypeErrorRate() * 100.0));

        if (outputPath.exists() || outputPath.mkdirs()) {
            try {
                new GZippedClassifierFileFormat().write(trainingResult.getClassifier(), outputPath + "/classifier.out");
            } catch (IOException e) {
                throw new MojoExecutionException("Cannot write output file.", e);
            }

            projectHelper.addResource(mavenProject, outputPath.getAbsolutePath(), Lists.newArrayList("**/*"), null);
        } else {
            throw new MojoExecutionException(String.format("Cannot create %s", outputPath));
        }
    }

    private static Map<String, ClassifierTrainingResult> trainCache = Maps.newHashMap();

    public static ClassifierTrainingResult train(String trainingSetFileName) throws MojoExecutionException {
        if (trainCache.containsKey(trainingSetFileName)) {
            return trainCache.get(trainingSetFileName);
        } else {
            List<PageWithType> inputSet;
            try {
                inputSet = readFile(trainingSetFileName);
            } catch (IOException e) {
                throw new MojoExecutionException(String.format("Cannot read training set file. (file:%s)", trainingSetFileName), e);
            }

            ClassifierTrainer trainer = new ClassifierTrainerFactory().create();
            ClassifierTrainingResult trainingResult = trainer.train(inputSet);
            trainCache.put(trainingSetFileName, trainingResult);
            return trainingResult;
        }
    }

    public static List<PageWithType> readFile( String inputFile ) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(inputFile)) {
            return gson.fromJson(reader, new TypeToken<List<PageWithType>>() {
            }.getType());
        }
    }
}
