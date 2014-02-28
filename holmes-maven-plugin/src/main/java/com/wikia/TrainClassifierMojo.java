package com.wikia;

import com.beust.jcommander.internal.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wikia.classifier.classifiers.DefaultClassifierFactory;
import com.wikia.classifier.classifiers.model.PageWithType;
import com.wikia.classifier.classifiers.serialization.GZippedClassifierFileFormat;
import com.wikia.classifier.classifiers.training.ClassifierTrainingResult;
import com.wikia.classifier.classifiers.training.DishonestClassifierTrainer;
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

/**
 * Goal which touches a timestamp file.
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
            getLog().info("No training file specified.");
            return;
        }

        List<PageWithType> inputSet;
        try {
            inputSet = readFile(trainingSet);
        } catch (IOException e) {
            throw new MojoExecutionException(String.format("Cannot read training set file. (file:%s)", trainingSet), e);
        }

        DishonestClassifierTrainer trainer = new DishonestClassifierTrainer(new DefaultClassifierFactory());
        ClassifierTrainingResult trainingResult = trainer.train(inputSet);

        getLog().info(String.format("SuccessRate: %.2f", trainingResult.getSuccessRate() * 100.0));

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

    public List<PageWithType> readFile( String inputFile ) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(inputFile)) {
            return gson.fromJson(reader, new TypeToken<List<PageWithType>>() {
            }.getType());
        }
    }
}
