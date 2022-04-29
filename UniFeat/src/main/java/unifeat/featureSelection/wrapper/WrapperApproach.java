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
package unifeat.featureSelection.wrapper;

import unifeat.dataset.DatasetInfo;
import unifeat.featureSelection.FeatureSelection;
import unifeat.featureSelection.wrapper.ACOBasedMethods.OptimalACO.OptimalACO;
import unifeat.featureSelection.wrapper.GABasedMethods.HGAFS.HGAFS;
import unifeat.featureSelection.wrapper.GABasedMethods.SimpleGA.SimpleGA;
import unifeat.featureSelection.wrapper.PSOBasedMethods.BPSO.BPSO;
import unifeat.featureSelection.wrapper.PSOBasedMethods.CPSO.CPSO;
import unifeat.featureSelection.wrapper.PSOBasedMethods.HPSO_LS.HPSO_LS;
import unifeat.featureSelection.wrapper.PSOBasedMethods.PSO42.PSO42;
import java.util.ArrayList;

/**
 * The abstract class contains the main methods and fields that are used in all
 * wrapper-based feature selection methods.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.FeatureSelection
 */
public abstract class WrapperApproach extends FeatureSelection {

    protected String[] nameFeatures;

    protected String[] classLabel;

    protected final String PROJECT_PATH;

    protected final String TEMP_PATH;

    /**
     * Initializes the parameters
     * 
     * @param path the path of the project
     */
    public WrapperApproach(String path) {
        super();
        this.PROJECT_PATH = path;
        this.TEMP_PATH = this.PROJECT_PATH + "Temp\\";
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
     * inherited from the WrapperApproach class according to type of the feature 
     * selection method.
     * 
     * @param type type of the wrapper feature selection method
     * @param arguments a list of arguments that is applied in the feature
     * selection method
     * 
     * @return the created object that has been inherited from the 
     * WrapperApproach class
     */
    public static WrapperApproach newMethod(WrapperType type, Object... arguments) {
        if (type == WrapperType.BPSO) {
            return new BPSO(arguments);
        } else if (type == WrapperType.CPSO) {
            return new CPSO(arguments);
        } else if (type == WrapperType.PSO42) {
            return new PSO42(arguments);
        } else if (type == WrapperType.HPSO_LS) {
            return new HPSO_LS(arguments);
        } else if (type == WrapperType.SIMPLE_GA) {
            return new SimpleGA(arguments);
        } else if (type == WrapperType.HGAFS) {
            return new HGAFS(arguments);
        } else if (type == WrapperType.OPTIMAL_ACO) {
            return new OptimalACO(arguments);
        }
        return null;
    }
}
