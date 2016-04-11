package io.github.mayhewsw.emoji.features;

import edu.illinois.cs.cogcomp.edison.features.Feature;
import edu.illinois.cs.cogcomp.edison.features.FeatureUtilities;
import edu.illinois.cs.cogcomp.edison.utilities.EdisonException;
import edu.illinois.cs.cogcomp.lbjava.classify.Classifier;
import edu.illinois.cs.cogcomp.lbjava.classify.FeatureVector;
import io.github.mayhewsw.emoji.Tweet;

import java.util.Set;

/**
 * A simple wrapper for {@code LBJava}-based feature extractors
 */
public abstract class  LBJavaFeatureExtractor extends Classifier {

    @Override
    public String getOutputType() {
        return "discrete%";
    }

    @Override
    public FeatureVector classify(Object o) {
        //Make sure the object is a Constituent
        if (!(o instanceof Tweet))
            throw new IllegalArgumentException("Instance must be of type Constituent");
        Tweet instance = (Tweet) o;

        FeatureVector featureVector;
        try {
            featureVector = FeatureUtilities.getLBJFeatures(getFeatures(instance));
        } catch (EdisonException e) {
            throw new RuntimeException(e);
        }
        return featureVector;
    }

    public abstract Set<Feature> getFeatures(Tweet instance) throws EdisonException;
}
