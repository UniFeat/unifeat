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
import unifeat.util.MathFunc;
import unifeat.featureSelection.filter.FilterApproach;

/**
 * This java class is used to implement the minimal redundancy maximal
 * relevance (mRMR) method.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.FilterApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public class MRMR extends FilterApproach {

    private double[][] probFeature;
    private double[][] valuesFeature;
    //private double ERROR_DENOMINATOR = 0.0001;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains 
     * (<code>sizeSelectedFeatureSubset</code>) in which 
     * <code><b><i>sizeSelectedFeatureSubset</i></b></code> is the number of 
     * selected features
     */
    public MRMR(Object... arguments) {
        super((int)arguments[0]);
    }
    
    /**
     * Initializes the parameters
     *
     * @param sizeSelectedFeatureSubset the number of selected features
     */
    public MRMR(int sizeSelectedFeatureSubset) {
        super(sizeSelectedFeatureSubset);
    }

    /**
     * Computes the number of different values of each feature
     *
     * @param index the index of the feature
     *
     * @return the number of different values
     */
    private int computeNumValue(int index) {
        int count = 0;

        for (int i = 1; i < trainSet.length; i++) {
            if (trainSet[i][index] != trainSet[i - 1][index]) {
                count++;
            }
        }

        return count + 1;
    }

    /**
     * Saves the different values of each feature
     *
     * @param index the index of the feature
     */
    private void computeDifferentValue(int index) {
        int count = 0;

        for (int i = 1; i < trainSet.length; i++) {
            if (trainSet[i][index] != trainSet[i - 1][index]) {
                valuesFeature[index][count++] = trainSet[i - 1][index];
            }
        }
        valuesFeature[index][count] = trainSet[trainSet.length - 1][index];
    }

    /**
     * Computes the probabilities values of each feature
     *
     * @param index the index of the feature
     */
    private void computeProbFeat(int index) {
        int count = 0;
        int indexStart = 0;

        for (int i = 1; i < trainSet.length; i++) {
            if (trainSet[i][index] != trainSet[i - 1][index]) {
                probFeature[index][count++] = (i - indexStart) / (double) trainSet.length; // probability of the feature based on its given value
//                if (probFeature[index][count - 1] == 0) {
//                    probFeature[index][count - 1] = ERROR_DENOMINATOR;
//                }
                indexStart = i;
            }
        }
        probFeature[index][count] = (trainSet.length - indexStart) / (double) trainSet.length; // probability of the feature based on its given value
//        if (probFeature[index][count] == 0) {
//            probFeature[index][count] = ERROR_DENOMINATOR;
//        }
    }

    /**
     * Computes the joint probabilities values between two features
     *
     * @param indexFeat2 the index of the second feature
     * @param indexStartData the start index of the dataset
     * @param indexEndData the end index of the dataset
     *
     * @return an array of the joint probabilities values
     */
    private double[] computeJointProb(int indexFeat2, int indexStartData, int indexEndData) {
        double[] jointProbValue = new double[probFeature[indexFeat2].length];
        ArraysFunc.sortArray2D(trainSet, indexFeat2, indexStartData, indexEndData); //sorts the dataset values corresponding to a given feature(feature indexFeat2)
        int indexStart = indexStartData;
        int j = -1;

        for (int i = indexStartData + 1; i < indexEndData; i++) {
            if (trainSet[i][indexFeat2] != trainSet[i - 1][indexFeat2]) {
                for (j = j + 1; j < valuesFeature[indexFeat2].length; j++) {
                    if (valuesFeature[indexFeat2][j] == trainSet[i - 1][indexFeat2]) {
                        jointProbValue[j] = (i - indexStart) / (double) trainSet.length; //probability of the feature based on its given value
                        break;
                    }
                }
                indexStart = i;
            }
        }

        for (j = j + 1; j < valuesFeature[indexFeat2].length; j++) {
            if (valuesFeature[indexFeat2][j] == trainSet[indexEndData - 1][indexFeat2]) {
                jointProbValue[j] = (indexEndData - indexStart) / (double) trainSet.length; //probability of the feature based on its given value
                break;
            }
        }

        return jointProbValue;
    }

    /**
     * Computes the mutual information values between two features
     *
     * @param index1 the index of the first feature
     * @param index2 the index of the second feature
     *
     * @return the mutual information value
     */
    private double computeMutualInfo(int index1, int index2) {
        double mutualInfoValue = 0;
        ArraysFunc.sortArray2D(trainSet, index1); //sorts the dataset values corresponding to a given feature(feature index1)
        int indexStart = 0;

        for (int i = 1; i < trainSet.length; i++) {
            if (trainSet[i][index1] != trainSet[i - 1][index1]) {
                double probFeat1 = (i - indexStart) / (double) trainSet.length; //probability of the feature based on its given value
                double[] jointProb = computeJointProb(index2, indexStart, i); //joint probabilitis values between feature index1 and index2

                //update mutual information value of the given feature
                for (int j = 0; j < jointProb.length; j++) {
                    if (jointProb[j] != 0) {
                        double denominatorValue = probFeat1 * probFeature[index2][j];
                        mutualInfoValue += jointProb[j] * MathFunc.log2(jointProb[j] / denominatorValue);
                    }
                }

                indexStart = i;
            }
        }

        double probFeat1 = (trainSet.length - indexStart) / (double) trainSet.length; //probability of the feature based on its given value
        double[] jointProb = computeJointProb(index2, indexStart, trainSet.length); //joint probabilitis values between feature index1 and index2

        //update mutual information value of the given feature
        for (int j = 0; j < jointProb.length; j++) {
            if (jointProb[j] != 0) {
                double denominatorValue = probFeat1 * probFeature[index2][j];
                mutualInfoValue += jointProb[j] * MathFunc.log2(jointProb[j] / denominatorValue);
            }
        }

        return mutualInfoValue;
    }

    /**
     * Finds the maximum value in the array and returns its index
     *
     * @param array the input array
     *
     * @return the index of the maximum value in the array
     */
    private int findMaxWithIndex(double[] array) {
        int index = 0;
        double max = array[index];

        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * Checks that is the current feature (index) available in the subset of
     * selected feature
     *
     * @param index the index of the feature
     * @param currentSize the current size of the selected features subset
     *
     * @return true if the current feature has been selected
     */
    private boolean isSelectedFeature(int index, int currentSize) {
        for (int i = 0; i < currentSize; i++) {
            if (selectedFeatureSubset[i] == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * Starts the feature selection process by minimal redundancy maximal 
     * relevance (mRMR) method
     */
    @Override
    public void evaluateFeatures() {
        double[] mutualInfoFeatClass = new double[numFeatures]; //mutual information values between features and class
        probFeature = new double[numFeatures + 1][]; //probabilities values of the features (+ class feature)
        valuesFeature = new double[numFeatures + 1][]; //different values of the features (+ class feature)

        //computes the probabilities values of each feature
        for (int i = 0; i <= numFeatures; i++) {
            ArraysFunc.sortArray2D(trainSet, i); //sorts the dataset values corresponding to a given feature(feature i)
            probFeature[i] = new double[computeNumValue(i)]; //computes the number of different values in feature i
            valuesFeature[i] = new double[probFeature[i].length];
            computeDifferentValue(i); //saves the different values of each feature
            computeProbFeat(i); //computes the probabilities values of each feature
        }

        //computes the mutual information values between features and class
        for (int i = 0; i < numFeatures; i++) {
            mutualInfoFeatClass[i] = computeMutualInfo(i, numFeatures);
        }

        //starts the feature selection process
        selectedFeatureSubset[0] = findMaxWithIndex(mutualInfoFeatClass); //finds a feature with the maximum mutual information value
        for (int i = 1; i < numSelectedFeature; i++) {
            double maxValue = -Double.MAX_VALUE;
            int indexMaxValue = -1;

            //finds the relevant feature from the current features set
            for (int j = 0; j < numFeatures; j++) {
                if (!isSelectedFeature(j, i)) {
                    double result = 0;
                    for (int k = 0; k < i; k++) {
                        result += computeMutualInfo(j, selectedFeatureSubset[k]);
                    }
                    result /= i;
                    result = mutualInfoFeatClass[j] - result;
                    if (result > maxValue) {
                        maxValue = result;
                        indexMaxValue = j;
                    }
                }
            }
            selectedFeatureSubset[i] = indexMaxValue;
        }

        ArraysFunc.sortArray1D(selectedFeatureSubset, false);
//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }
    }
}
