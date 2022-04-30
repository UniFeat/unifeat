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
import java.util.Arrays;
import unifeat.featureSelection.filter.WeightedFilterApproach;

/**
 * This java class is used to implement the gain ratio method.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.WeightedFilterApproach
 * @see unifeat.featureSelection.FeatureWeighting
 * @see unifeat.featureSelection.FeatureSelection
 */
public class GainRatio extends WeightedFilterApproach {

    private final double ERROR_DENOMINATOR = 0.0001;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains 
     * (<code>sizeSelectedFeatureSubset</code>) in which 
     * <code><b><i>sizeSelectedFeatureSubset</i></b></code> is the number of 
     * selected features
     */
    public GainRatio(Object... arguments) {
        super((int)arguments[0]);
    }
    
    /**
     * Initializes the parameters
     *
     * @param sizeSelectedFeatureSubset the number of selected features
     */
    public GainRatio(int sizeSelectedFeatureSubset) {
        super(sizeSelectedFeatureSubset);
    }

    /**
     * Computes the split information values of the data for all features
     *
     * @return an array of the split information values
     */
    private double[] splitInformation() {
        double[] splitInformationValues = new double[numFeatures];

        for (int i = 0; i < numFeatures; i++) {
            ArraysFunc.sortArray2D(trainSet, i); // sorts the dataset values corresponding to a given feature(feature i)
            int indexStart = 0;
            double startValue = trainSet[indexStart][i];
            for (int j = 1; j < trainSet.length; j++) {
                if (startValue != trainSet[j][i]) {
                    double prob = (j - indexStart) / (double) trainSet.length;
                    splitInformationValues[i] -= prob * MathFunc.log2(prob);
                    indexStart = j;
                    startValue = trainSet[indexStart][i];
                }
            }
            double prob = (trainSet.length - indexStart) / (double) trainSet.length;
            splitInformationValues[i] -= prob * MathFunc.log2(prob);
            if (splitInformationValues[i] == 0) {
                splitInformationValues[i] = ERROR_DENOMINATOR;
            }
        }

        return splitInformationValues;
    }

    /**
     * Starts the feature selection process by gain ratio(GR) method
     */
    @Override
    public void evaluateFeatures() {
        double[] infoGainValues;
        double[] splitInfoValues;
        featureValues = new double[numFeatures];
        int[] indecesGR;

        //computes the information gain values of the data
        InformationGain infoGain = new InformationGain(numFeatures);
        infoGain.loadDataSet(trainSet, numFeatures, numClass);
        infoGain.evaluateFeatures();
        infoGainValues = infoGain.getFeatureValues();

        //computes the split information values of the data
        splitInfoValues = splitInformation();

        //computes the gain ratio values
        for (int i = 0; i < numFeatures; i++) {
            featureValues[i] = infoGainValues[i] / splitInfoValues[i];
//            System.out.println(i + ")= " + infoGainValues[i] + " , " + splitInfoValues[i] + " , " + featureValues[i]);
        }

        indecesGR = ArraysFunc.sortWithIndex(Arrays.copyOf(featureValues, featureValues.length), true);
        selectedFeatureSubset = Arrays.copyOfRange(indecesGR, 0, numSelectedFeature);
        ArraysFunc.sortArray1D(selectedFeatureSubset, false);
//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }
    }
}
