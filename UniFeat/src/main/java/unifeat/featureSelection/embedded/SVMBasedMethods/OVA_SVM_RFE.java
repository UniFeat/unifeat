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
package unifeat.featureSelection.embedded.SVMBasedMethods;

import unifeat.gui.classifier.svmClassifier.SVMKernelType;
import unifeat.util.ArraysFunc;
import unifeat.util.FileFunc;
import unifeat.util.MathFunc;
import java.util.Arrays;

/**
 * This java class is used to implement OVA_SVM_RFE method for multiclass
 * classification based on SVM_RFE method (support vector machine method based
 * on recursive feature elimination) in which One-Versus-All (OVA) strategy is
 * applied to construct binary classifiers.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.embedded.SVMBasedMethods.SVMBasedMethods
 * @see unifeat.featureSelection.embedded.EmbeddedApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public class OVA_SVM_RFE extends SVMBasedMethods {

    private final double ERROR_DENOMINATOR = 1.0;
    private final double ERROR_TWO_CLASS_PROBLEM = 0.001;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains (<code>path</code>,
     * <code>kernelType</code>, <code>Parameter c</code>) in which
     * <code><b><i>path</i></b></code> is the path of the project,
     * <code><b><i>kernelType</i></b></code> is the type of kernel to use, and
     * <code><b><i>Parameter c</i></b></code> is the complexity parameter C
     */
    public OVA_SVM_RFE(Object... arguments) {
        super(arguments);
    }

    /**
     * Initializes the parameters
     *
     * @param path the path of the project
     * @param kernelType the type of kernel to use
     * @param c the complexity parameter C
     */
    public OVA_SVM_RFE(String path, SVMKernelType kernelType, double c) {
        super(path, kernelType, c);
    }

    /**
     * Generates binary classifiers (SVM by applying One-Versus-All (OVA)
     * strategy) using input data and based on selected feature subset, and
     * finally returns the weights of features
     *
     * @param selectedFeature an array of indices of the selected feature subset
     *
     * @return an array of the weights of features
     */
    private double[] getFeaturesWeights(int[] selectedFeature) {
        double[] featureValues = new double[selectedFeature.length];
        double[] averageValues = new double[selectedFeature.length];
        double[] standardDeviationValues = new double[selectedFeature.length];
        double[][] weights = buildSVM_OneAgainstRest(selectedFeature);

        //Normalize weight vector
        for (double[] weight : weights) {
            MathFunc.normalizeVector(weight);
        }

        for (double[] weight : weights) {
            for (int j = 0; j < weight.length; j++) {
                weight[j] *= weight[j];
            }
        }

        //Compute average values of features
        for (int j = 0; j < selectedFeature.length; j++) {
            averageValues[j] = MathFunc.computeMean(weights, j);
        }

        //Compute standard deviation values of features
        double denominatorValue = weights.length > 1 ? weights.length - 1.0 : ERROR_TWO_CLASS_PROBLEM;
        for (int j = 0; j < selectedFeature.length; j++) {
            standardDeviationValues[j] = MathFunc.computeStandardDeviation(weights, averageValues[j], j, denominatorValue);
        }

        //Compute scores of features
        for (int j = 0; j < selectedFeature.length; j++) {
            featureValues[j] = averageValues[j] / (standardDeviationValues[j] + ERROR_DENOMINATOR);
        }

        return featureValues;
    }

    /**
     * Starts the feature selection process by support vector machine method
     * based on recursive feature elimination using One-Versus-All strategy
     * (OVA_SVM_RFE)
     */
    @Override
    public void evaluateFeatures() {
        FileFunc.createDirectory(TEMP_PATH);
        this.createClassLabel();
        int[] indexFeatures = new int[numFeatures];

        //initializes the feature index values
        for (int i = 0; i < indexFeatures.length; i++) {
            indexFeatures[i] = i;
        }

        for (int i = 0; i < numFeatures - numSelectedFeature; i++) {
            System.out.println("\nIteration " + i + ":\n\n");

            ArraysFunc.sortArray1D(indexFeatures, false, 0, numFeatures - i);
            int[] featSpace = Arrays.copyOfRange(indexFeatures, 0, numFeatures - i);

            /**
             * generates SVM classifiers, computes weights of features, and find
             * index of a feature with smallest weight
             */
            int minIndex = MathFunc.findMinimumIndex(getFeaturesWeights(featSpace));

            /**
             * changing the place of the feature with the minIndex to the last
             * feature in the indexFeatures array
             */
            MathFunc.swap(indexFeatures, minIndex, numFeatures - i - 1);
        }

        selectedFeatureSubset = Arrays.copyOfRange(indexFeatures, 0, numSelectedFeature);
        ArraysFunc.sortArray1D(selectedFeatureSubset, false);

//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }
        FileFunc.deleteDirectoryWithAllFiles(TEMP_PATH);
    }
}
