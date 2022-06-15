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
 * filter-based feature selection methods.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.FeatureSelection
 */
public abstract class FilterApproach extends FeatureSelection {

    /**
     * Initializes the parameters
     * 
     * @param sizeSelectedFeatureSubset the size of selected features subset
     */
    public FilterApproach(int sizeSelectedFeatureSubset) {
        super();
        this.numSelectedFeature = sizeSelectedFeatureSubset;
        this.selectedFeatureSubset = new int[this.numSelectedFeature];
    }

    /**
     * This method creates new object from one of the classes that has been
     * inherited from the FilterApproach class according to type of the feature 
     * selection method.
     * 
     * @param type type of the filter feature selection method
     * @param isSupervised shows whether a method is supervised or unsupervised
     * @param arguments a list of arguments that is applied in the feature
     * selection method
     * 
     * @return the created object that has been inherited from the 
     * FilterApproach class
     */
    public static FilterApproach newMethod(FilterType type, boolean isSupervised, Object... arguments) {
        if (type == FilterType.MRMR) {
            return new MRMR(arguments);
        } else if (type == FilterType.RRFS && isSupervised) {
            return new unifeat.featureSelection.filter.supervised.RRFS(arguments);
        } else if (type == FilterType.RRFS && !isSupervised) {
            return new unifeat.featureSelection.filter.unsupervised.RRFS(arguments);
        } else if (type == FilterType.MUTUAL_CORRELATION) {
            return new MutualCorrelation(arguments);
        } else if (type == FilterType.RSM) {
            return new RSM(arguments);
        } else if (type == FilterType.UFSACO) {
            return new UFSACO(arguments);
        } else if (type == FilterType.RRFSACO_1) {
            return new RRFSACO_1(arguments);
        } else if (type == FilterType.RRFSACO_2) {
            return new RRFSACO_2(arguments);
        } else if (type == FilterType.IRRFSACO_1) {
            return new IRRFSACO_1(arguments);
        } else if (type == FilterType.IRRFSACO_2) {
            return new IRRFSACO_2(arguments);
        } else if (type == FilterType.MGSACO) {
            return new MGSACO(arguments);
        }
        return null;
    }
}
