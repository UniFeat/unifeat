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
package unifeat.featureSelection;

import unifeat.dataset.DatasetInfo;
import unifeat.util.ArraysFunc;

/**
 * The abstract class contains the main methods and fields that are used in all
 * feature selection methods.
 *
 * @author Sina Tabakhi
 */
public abstract class FeatureSelection {

    protected double[][] trainSet;

    protected int numFeatures;

    protected int numClass;

    protected int[] selectedFeatureSubset;

    protected int numSelectedFeature;

    /**
     * Initializes the parameters
     */
    public FeatureSelection() {
    }

    /**
     * Loads the dataset
     *
     * @param ob an object of the DatasetInfo class
     */
    public void loadDataSet(DatasetInfo ob) {
        this.trainSet = ob.getTrainSet();
        this.numFeatures = ob.getNumFeature();
        this.numClass = ob.getNumClass();
    }

    /**
     * Loads the dataset
     *
     * @param data the input dataset values
     * @param numFeat the number of features in the dataset
     * @param numClasses the number of classes in the dataset
     */
    public void loadDataSet(double[][] data, int numFeat, int numClasses) {
        this.trainSet = ArraysFunc.copyDoubleArray2D(data);
        this.numFeatures = numFeat;
        this.numClass = numClasses;
    }

    /**
     * Starts the feature selection process by a given method
     */
    public abstract void evaluateFeatures();

    /**
     * This method returns the potential errors in the input parameters.
     *
     * @return a string contains the information about incorrect parameters 
     */
    public String validate() {
        return "";
    }

    /**
     * This method sets the number of features that should be selected by a 
     * given feature selection method.
     *
     * @param numSelectedFeature the number of selected features
     */
    public void setNumSelectedFeature(int numSelectedFeature) {
        this.numSelectedFeature = numSelectedFeature;
        this.selectedFeatureSubset = new int[this.numSelectedFeature];
    }

    /**
     * This method returns the subset of selected features by a given feature
     * selection method.
     *
     * @return an array of subset of selected features
     */
    public int[] getSelectedFeatureSubset() {
        return this.selectedFeatureSubset;
    }
}
