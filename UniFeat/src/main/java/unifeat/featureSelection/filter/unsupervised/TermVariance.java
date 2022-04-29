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
import unifeat.featureSelection.filter.WeightedFilterApproach;

/**
 * This java class is used to implement the term variance method.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.WeightedFilterApproach
 * @see unifeat.featureSelection.FeatureWeighting
 * @see unifeat.featureSelection.FeatureSelection
 */
public class TermVariance extends WeightedFilterApproach {

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameter contains
     * (<code>sizeSelectedFeatureSubset</code>) in which
     * <code><b><i>sizeSelectedFeatureSubset</i></b></code> is the number of
     * selected features
     */
    public TermVariance(Object... arguments) {
        super((int) arguments[0]);
    }

    /**
     * Initializes the parameters
     *
     * @param sizeSelectedFeatureSubset the number of selected features
     */
    public TermVariance(int sizeSelectedFeatureSubset) {
        super(sizeSelectedFeatureSubset);
    }

    /**
     * Starts the feature selection process by term variance(TV) method
     */
    @Override
    public void evaluateFeatures() {
        double[] meanValues = new double[numFeatures];
        featureValues = new double[numFeatures];
        int[] indecesTV;

        //computes the mean values of each feature
        for (int i = 0; i < numFeatures; i++) {
            meanValues[i] = MathFunc.computeMean(trainSet, i);
        }

        //computes the variance values of each feature
        for (int i = 0; i < numFeatures; i++) {
            featureValues[i] = MathFunc.computeVariance(trainSet, meanValues[i], i);
        }

        indecesTV = ArraysFunc.sortWithIndex(Arrays.copyOf(featureValues, featureValues.length), true);
//        for (int i = 0; i < numFeatures; i++) {
//            System.out.println(i + ") =  " + featureValues[i]);
//        }

        selectedFeatureSubset = Arrays.copyOfRange(indecesTV, 0, numSelectedFeature);
        ArraysFunc.sortArray1D(selectedFeatureSubset, false);
//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }
    }
}
