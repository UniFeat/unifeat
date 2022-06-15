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

import unifeat.util.ArraysFunc;
import java.util.Arrays;
import unifeat.featureSelection.filter.WeightedFilterApproach;

/**
 * This java class is used to implement the Gini index method.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.WeightedFilterApproach
 * @see unifeat.featureSelection.FeatureWeighting
 * @see unifeat.featureSelection.FeatureSelection
 */
public class GiniIndex extends WeightedFilterApproach {

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains 
     * (<code>sizeSelectedFeatureSubset</code>) in which 
     * <code><b><i>sizeSelectedFeatureSubset</i></b></code> is the number of 
     * selected features
     */
    public GiniIndex(Object... arguments) {
        super((int)arguments[0]);
    }
    
    /**
     * Initializes the parameters
     *
     * @param sizeSelectedFeatureSubset the number of selected features
     */
    public GiniIndex(int sizeSelectedFeatureSubset) {
        super(sizeSelectedFeatureSubset);
    }

    /**
     * Computes the gini value of the data given by start and end indices
     *
     * @param indexStart the start index of the dataset
     * @param indexEnd the end index of the dataset
     * 
     * @return the gini value
     */
    private double computeGini(int indexStart, int indexEnd) {
        double gini = 0;
        int sizeUsedData = indexEnd - indexStart;
        int[] countClassSample = new int[numClass];

        //counts the number of samples in each class
        for (int i = indexStart; i < indexEnd; i++) {
            countClassSample[(int) trainSet[i][numFeatures]]++;
        }

        //computes the gini value of the data given by start and end indeces
        for (int i = 0; i < numClass; i++) {
            if (countClassSample[i] != 0) {
                double prob = countClassSample[i] / (double) sizeUsedData;
                gini += Math.pow(prob, 2);
            }
        }

        return 1 - gini;
    }

    /**
     * Starts the feature selection process by Gini index(GI) method
     */
    @Override
    public void evaluateFeatures() {
        double giniSystem = computeGini(0, trainSet.length); // computes the gini value of the system (over all dataset)
        featureValues = new double[numFeatures];
        int[] indecesGI;

        //computes the Gini index values of each feature
        for (int i = 0; i < numFeatures; i++) {
            double giniFeature = 0;
            ArraysFunc.sortArray2D(trainSet, i); // sorts the dataset values corresponding to a given feature(feature i)
            int indexStart = 0;
            double startValue = trainSet[indexStart][i];
            for (int j = 1; j < trainSet.length; j++) {
                if (startValue != trainSet[j][i]) {
                    double prob = (j - indexStart) / (double) trainSet.length;
                    giniFeature += prob * computeGini(indexStart, j);
                    indexStart = j;
                    startValue = trainSet[indexStart][i];
                }
            }
            double prob = (trainSet.length - indexStart) / (double) trainSet.length;
            giniFeature += prob * computeGini(indexStart, trainSet.length);
            featureValues[i] = giniSystem - giniFeature;
        }

        indecesGI = ArraysFunc.sortWithIndex(Arrays.copyOf(featureValues, featureValues.length), true);
//        for (int i = 0; i < numFeatures; i++) {
//            System.out.println(i + ") =  " + featureValues[i]);
//        }

        selectedFeatureSubset = Arrays.copyOfRange(indecesGI, 0, numSelectedFeature);
        ArraysFunc.sortArray1D(selectedFeatureSubset, false);
//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }
    }
}
