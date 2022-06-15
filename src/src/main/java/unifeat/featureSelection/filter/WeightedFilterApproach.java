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
package unifeat.featureSelection.filter;

import unifeat.featureSelection.*;
import unifeat.featureSelection.filter.supervised.*;
import unifeat.featureSelection.filter.unsupervised.*;

/**
 * The abstract class contains the main methods and fields that are used in all
 * weighted filter feature selection methods.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.FeatureWeighting
 */
public abstract class WeightedFilterApproach extends FeatureWeighting {

    /**
     * Initializes the parameters
     * 
     * @param sizeSelectedFeatureSubset the size of selected features subset
     */
    public WeightedFilterApproach(int sizeSelectedFeatureSubset) {
        super();
        this.numSelectedFeature = sizeSelectedFeatureSubset;
        this.selectedFeatureSubset = new int[this.numSelectedFeature];
    }

    /**
     * This method creates new object from one of the classes that has been
     * inherited from the WeightedFilterApproach class according to type of the 
     * feature selection method.
     * 
     * @param type type of the weighted filter feature selection method
     * @param isSupervised shows whether a method is supervised or unsupervised
     * @param arguments a list of arguments that is applied in the feature
     * selection method
     * 
     * @return the created object that has been inherited from the 
     * WeightedFilterApproach class
     */
    public static WeightedFilterApproach newMethod(FilterType type, boolean isSupervised, Object... arguments) {
        if (type == FilterType.INFORMATION_GAIN) {
            return new InformationGain(arguments);
        } else if (type == FilterType.GAIN_RATIO) {
            return new GainRatio(arguments);
        } else if (type == FilterType.SYMMETRICAL_UNCERTAINTY) {
            return new SymmetricalUncertainty(arguments);
        } else if (type == FilterType.FISHER_SCORE) {
            return new FisherScore(arguments);
        } else if (type == FilterType.GINI_INDEX) {
            return new GiniIndex(arguments);
        } else if (type == FilterType.LAPLACIAN_SCORE && isSupervised) {
            return new unifeat.featureSelection.filter.supervised.LaplacianScore(arguments);
        } else if (type == FilterType.LAPLACIAN_SCORE && !isSupervised) {
            return new unifeat.featureSelection.filter.unsupervised.LaplacianScore(arguments);
        } else if (type == FilterType.TERM_VARIANCE) {
            return new TermVariance(arguments);
        }
        return null;
    }
}
