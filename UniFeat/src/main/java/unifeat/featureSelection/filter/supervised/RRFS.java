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
import unifeat.featureSelection.filter.FilterApproach;

/**
 * This java class is used to implement the relevance-redundancy feature 
 * selection(RRFS) method. Also, it is the supervised version of the RRFS.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.FilterApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public class RRFS extends FilterApproach {

    private final double MAX_SIM_VALUE; //maximum allowed similarity between two features

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains 
     * (<code>sizeSelectedFeatureSubset</code>, <code>maxSimilarity</code>) in 
     * which <code><b><i>sizeSelectedFeatureSubset</i></b></code> is the number 
     * of selected features, and <code><b><i>maxSimilarity</i></b></code> is 
     * maximum allowed similarity between two features
     */
    public RRFS(Object... arguments) {
        super((int)arguments[0]);
        MAX_SIM_VALUE = (double)arguments[1];
    }
    
    /**
     * Initializes the parameters
     *
     * @param sizeSelectedFeatureSubset the number of selected features
     * @param maxSimilarity maximum allowed similarity between two features
     */
    public RRFS(int sizeSelectedFeatureSubset, double maxSimilarity) {
        super(sizeSelectedFeatureSubset);
        MAX_SIM_VALUE = maxSimilarity;
    }

    /**
     * Starts the feature selection process by relevance-redundancy feature
     * selection(RRFS) method
     */
    @Override
    public void evaluateFeatures() {
        double[] fScoreValues; //Fisher score values
        int[] indexFeatures;
        int prev, next;

        //computes the Fisher score values of the data
        FisherScore fScore = new FisherScore(numFeatures);
        fScore.loadDataSet(trainSet, numFeatures, numClass);
        fScore.evaluateFeatures();
        fScoreValues = fScore.getFeatureValues();

//        for (int i = 0; i < fScoreValues.length; i++) {
//            System.out.println(i + ")= " + fScoreValues[i]);
//        }

        //sorts the features by their relevance values(Fisher score values)
        indexFeatures = ArraysFunc.sortWithIndex(fScoreValues, true);

        //starts the feature selection process
        selectedFeatureSubset[0] = indexFeatures[0];
        prev = 0;
        next = 1;
        for (int i = 1; i < numFeatures && next < numSelectedFeature; i++) {
            double simValue = Math.abs(MathFunc.computeSimilarity(trainSet, indexFeatures[i], indexFeatures[prev]));
            if (simValue < MAX_SIM_VALUE) {
                selectedFeatureSubset[next] = indexFeatures[i];
                prev = i;
                next++;
            }
        }

//        for (int i = 0; i < next; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }

        if (next < numSelectedFeature) {
            selectedFeatureSubset = Arrays.copyOfRange(selectedFeatureSubset, 0, next);
        }
        ArraysFunc.sortArray1D(selectedFeatureSubset, false);
    }
}
