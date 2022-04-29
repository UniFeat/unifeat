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
package unifeat.featureSelection.filter.unsupervised;

import unifeat.util.ArraysFunc;
import unifeat.util.MathFunc;
import java.util.Arrays;
import unifeat.featureSelection.filter.FilterApproach;

/**
 * This java class is used to implement the mutual correlation method.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.FilterApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public class MutualCorrelation extends FilterApproach {

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains 
     * (<code>sizeSelectedFeatureSubset</code>) in which 
     * <code><b><i>sizeSelectedFeatureSubset</i></b></code> is the number of 
     * selected features
     */
    public MutualCorrelation(Object... arguments) {
        super((int)arguments[0]);
    }

    /**
     * Initializes the parameters
     *
     * @param sizeSelectedFeatureSubset the number of selected features
     */
    public MutualCorrelation(int sizeSelectedFeatureSubset) {
        super(sizeSelectedFeatureSubset);
    }

    /**
     * Computes the mutual correlation value between each pairs of features
     *
     * @param index1 the index of the feature 1
     * @param index2 the index of the feature 2
     * @param mean1 the mean value of the data corresponding to the feature 1
     * @param mean2 the mean value of the data corresponding to the feature 2
     * 
     * @return the mutual correlation value
     */
    private double computeMutualCorrelation(int index1, int index2, double mean1, double mean2) {
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        for (double[] sample : trainSet) {
            sum1 += sample[index1] * sample[index2];
            sum2 += sample[index1] * sample[index1];
            sum3 += sample[index2] * sample[index2];
        }
        sum1 -= trainSet.length * mean1 * mean2;
        sum2 -= trainSet.length * mean1 * mean1;
        sum3 -= trainSet.length * mean2 * mean2;

        if (sum2 == 0 && sum3 == 0) {
            return 1;
        } else if (sum2 == 0 || sum3 == 0) {
            return 0;
        } else {
            return sum1 / Math.sqrt(sum2 * sum3);
        }
    }

    /**
     * Finds index in new Data Structure(Symmetric Matrix)
     *
     * @param index1 index of the row
     * @param index2 index of the column
     * 
     * @return the index in new Data Structure
     */
    private static int findIndex(int index1, int index2) {
        if (index1 < index2) {
            return ((index2 * (index2 - 1)) / 2) + index1;
        } else {
            return ((index1 * (index1 - 1)) / 2) + index2;
        }
    }

    /**
     * Finds the maximum value in the array and returns its index
     *
     * @param array the input array
     * @param len the length of available values in the array
     * 
     * @return the index of the maximum value in the array
     */
    private int findMaxWithIndex(double[] array, int len) {
        int index = 0;
        double max = array[index];
        for (int i = 1; i <= len; i++) {
            if (array[i] > max) {
                max = array[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * Swaps the two entities of the input integer array
     *
     * @param array the input array
     * @param firstIndex index of the first entity in the array
     * @param secondIndex index of the second entity in the array
     */
    private void swapValue(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    /**
     * Swaps the two entities of the input double array
     *
     * @param array the input array
     * @param firstIndex index of the first entity in the array
     * @param secondIndex index of the second entity in the array
     */
    private void swapValue(double[] array, int firstIndex, int secondIndex) {
        double temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    /**
     * Starts the feature selection process by mutual correlation(MC) method
     */
    @Override
    public void evaluateFeatures() {
        double[] mean = new double[numFeatures];
        double[] correlationValues = new double[(numFeatures * (numFeatures - 1)) / 2]; // mutual correlation values
        double[] meanMutCorrelation = new double[numFeatures]; //the mean absolute mutual correlation values
        int[] indexFeatures = new int[numFeatures];
        int counter = 0;

        //initializes the feature index values
        for (int i = 0; i < indexFeatures.length; i++) {
            indexFeatures[i] = i;
        }

        //computes the mean values of the features
        for (int i = 0; i < numFeatures; i++) {
            mean[i] = MathFunc.computeMean(trainSet, i);
        }

        //computes the mutual correlation values between each pairs of features
        for (int i = 0; i < numFeatures; i++) {
            for (int j = 0; j < i; j++) {
                correlationValues[counter++] = computeMutualCorrelation(i, j, mean[i], mean[j]);
            }
        }

        //computes the mean absolute mutual correlation values for the features
        for (int i = 0; i < numFeatures; i++) {
            for (int j = 0; j < numFeatures; j++) {
                if (i != j) {
                    int index = findIndex(i, j);
                    meanMutCorrelation[i] += Math.abs(correlationValues[index]);
                }
            }
            meanMutCorrelation[i] /= (numFeatures - 1);
        }

        //starts the feature elimination process
        for (int i = numFeatures - 1; i >= numSelectedFeature; i--) {
            int maxIndex = findMaxWithIndex(meanMutCorrelation, i);
            swapValue(meanMutCorrelation, maxIndex, i);
            swapValue(indexFeatures, maxIndex, i);
            for (int j = 0; j < i; j++) {
                int index = findIndex(indexFeatures[j], indexFeatures[i]);
                meanMutCorrelation[j] = (i * meanMutCorrelation[j] - Math.abs(correlationValues[index])) / (i - 1);
            }
        }

        selectedFeatureSubset = Arrays.copyOfRange(indexFeatures, 0, numSelectedFeature);
        ArraysFunc.sortArray1D(selectedFeatureSubset, false);
//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }
    }
}
