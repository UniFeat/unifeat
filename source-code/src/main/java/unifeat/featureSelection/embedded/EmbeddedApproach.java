/*
 * The MIT License
 *
 * Copyright 2022 UniFeat
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package unifeat.featureSelection.embedded;

import unifeat.featureSelection.embedded.SVMBasedMethods.MSVM_RFE;
import unifeat.featureSelection.embedded.SVMBasedMethods.OVA_SVM_RFE;
import unifeat.featureSelection.embedded.SVMBasedMethods.OVO_SVM_RFE;
import unifeat.featureSelection.embedded.SVMBasedMethods.SVM_RFE;
import unifeat.featureSelection.embedded.TreeBasedMethods.RandomForestMethod;
import unifeat.featureSelection.embedded.TreeBasedMethods.DecisionTreeBasedMethod;
import unifeat.dataset.DatasetInfo;
import unifeat.featureSelection.FeatureSelection;
import java.util.ArrayList;

/**
 * The abstract class contains the main methods and fields that are used in all
 * embedded-based feature selection methods.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.FeatureSelection
 */
public abstract class EmbeddedApproach extends FeatureSelection {

    protected String[] nameFeatures;

    protected String[] classLabel;

    protected final String PROJECT_PATH;

    /**
     * Initializes the parameters
     * 
     * @param path the path of the project
     */
    public EmbeddedApproach(String path) {
        super();
        this.PROJECT_PATH = path;
    }

    /**
     * Loads the dataset
     *
     * @param ob an object of the DatasetInfo class
     */
    @Override
    public void loadDataSet(DatasetInfo ob) {
        super.loadDataSet(ob);
        this.nameFeatures = ob.getNameFeatures();
        this.classLabel = ob.getClassLabel();
    }

    /**
     * Loads the dataset
     *
     * @param data the input dataset values
     * @param numFeat the number of features in the dataset
     * @param numClasses the number of classes in the dataset
     */
    @Override
    public void loadDataSet(double[][] data, int numFeat, int numClasses) {
        super.loadDataSet(data, numFeat, numClasses);
        this.nameFeatures = new String[this.numFeatures + 1];
        for (int i = 0; i < this.nameFeatures.length; i++) {
            this.nameFeatures[i] = "f" + i;
        }
        ArrayList<String> labels = new ArrayList();
        for (double[] sample : data) {
            if (!labels.contains(Double.toString(sample[this.numFeatures]))) {
                labels.add(Double.toString(sample[this.numFeatures]));
            }
        }
        this.classLabel = new String[this.numClass];
        this.classLabel = labels.toArray(this.classLabel);
    }

    /**
     * This method creates an array of indices of features and returns it.
     *
     * @return an array of features indices
     */
    protected int[] originalFeatureSet() {
        int[] featureSet = new int[this.numFeatures];
        for (int i = 0; i < featureSet.length; i++) {
            featureSet[i] = i;
        }
        return featureSet;
    }

    /**
     * This method creates new object from one of the classes that has been
     * inherited from the EmbeddedApproach class according to type of the feature 
     * selection method.
     * 
     * @param type type of the embedded feature selection method
     * @param arguments a list of arguments that is applied in the feature
     * selection method
     * 
     * @return the created object that has been inherited from the 
     * EmbeddedApproach class
     */
    public static EmbeddedApproach newMethod(EmbeddedType type, Object... arguments) {
        if (type == EmbeddedType.DECISION_TREE_BASED) {
            return new DecisionTreeBasedMethod(arguments);
        } else if (type == EmbeddedType.RANDOM_FOREST_METHOD) {
            return new RandomForestMethod(arguments);
        } else if (type == EmbeddedType.SVM_RFE) {
            return new SVM_RFE(arguments);
        } else if (type == EmbeddedType.MSVM_RFE) {
            return new MSVM_RFE(arguments);
        } else if (type == EmbeddedType.OVO_SVM_RFE) {
            return new OVO_SVM_RFE(arguments);
        } else if (type == EmbeddedType.OVA_SVM_RFE) {
            return new OVA_SVM_RFE(arguments);
        }
        return null;
    }
}
