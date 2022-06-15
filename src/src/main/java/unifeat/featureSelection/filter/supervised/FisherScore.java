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
package unifeat.featureSelection.filter.supervised;

import unifeat.util.MathFunc;
import unifeat.util.ArraysFunc;
import java.util.Arrays;
import unifeat.featureSelection.filter.WeightedFilterApproach;

/**
 * This java class is used to implement the Fisher score method.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.WeightedFilterApproach
 * @see unifeat.featureSelection.FeatureWeighting
 * @see unifeat.featureSelection.FeatureSelection
 */
public class FisherScore extends WeightedFilterApproach {

    private final double ERROR_DENOMINATOR = 0.0001;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameter contains 
     * (<code>sizeSelectedFeatureSubset</code>) in which 
     * <code><b><i>sizeSelectedFeatureSubset</i></b></code> is the number of 
     * selected features
     */
    public FisherScore(Object... arguments) {
        super((int)arguments[0]);
    }
    
    /**
     * Initializes the parameters
     *
     * @param sizeSelectedFeatureSubset the number of selected features
     */
    public FisherScore(int sizeSelectedFeatureSubset) {
        super(sizeSelectedFeatureSubset);
    }

    /**
     * Starts the feature selection process by Fisher score(FS) method
     */
    @Override
    public void evaluateFeatures() {
        featureValues = new double[numFeatures];
        double[] meanFeatures = new double[numFeatures]; // the mean values of each feature
        double[][] meanFeatureClass = new double[numClass][numFeatures]; // the mean values of each feature on each class
        double[][] varianceFeatureClass = new double[numClass][numFeatures]; // the variance values of each feature on each class
        int[] numClassSample = new int[numClass]; // the number of samples in each class
        int[] indecesFS;

        //counts the number of samples in each class
        for (double[] sample : trainSet) {
            numClassSample[(int) sample[numFeatures]]++;
        }

        //computes the mean values of each feature
        for (int i = 0; i < numFeatures; i++) {
            meanFeatures[i] = MathFunc.computeMean(trainSet, i);
        }

        //computes the mean values of each feature on each class
        for (double[] sample : trainSet) {
            int indexClass = (int) sample[numFeatures];
            for (int j = 0; j < numFeatures; j++) {
                meanFeatureClass[indexClass][j] += sample[j];
            }
        }
        for (int i = 0; i < numClass; i++) {
            for (int j = 0; j < numFeatures; j++) {
                meanFeatureClass[i][j] /= numClassSample[i];
            }
        }

        //computes the variance values of each feature on each class
        for (double[] sample : trainSet) {
            int indexClass = (int) sample[numFeatures];
            for (int j = 0; j < numFeatures; j++) {
                varianceFeatureClass[indexClass][j] += Math.pow(sample[j] - meanFeatureClass[indexClass][j], 2);
            }
        }
        for (int i = 0; i < numClass; i++) {
            for (int j = 0; j < numFeatures; j++) {
                varianceFeatureClass[i][j] /= numClassSample[i];
            }
        }

        //computes the Fisher score values of the features
        for (int i = 0; i < numFeatures; i++) {
            double sum1 = 0;
            double sum2 = 0;
            for (int j = 0; j < numClass; j++) {
                sum1 += numClassSample[j] * Math.pow(meanFeatureClass[j][i] - meanFeatures[i], 2);
                sum2 += numClassSample[j] * varianceFeatureClass[j][i];
            }
            if (sum2 == 0) {
                featureValues[i] = sum1 / ERROR_DENOMINATOR;
            } else {
                featureValues[i] = sum1 / sum2;
            }
        }

        indecesFS = ArraysFunc.sortWithIndex(Arrays.copyOf(featureValues, featureValues.length), true);
//        for (int i = 0; i < numFeatures; i++) {
//            System.out.println("Fisher(f" + i + ") = " + featureValues[i]);
//        }

        selectedFeatureSubset = Arrays.copyOfRange(indecesFS, 0, numSelectedFeature);
        ArraysFunc.sortArray1D(selectedFeatureSubset, false);
//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }
    }
}
