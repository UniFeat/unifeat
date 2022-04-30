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

import unifeat.featureSelection.filter.FilterType;

/**
 * This java class is used to define the names of supervised filter-based
 * feature selection methods.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.FilterType
 */
public class SupervisedFilterType extends FilterType {

    /**
     * Creates new SupervisedFilterType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of supervised filter method
     */
    private SupervisedFilterType(String name) {
        super(name);
    }

    /**
     * Returns the names of supervised filter-based feature selection methods
     *
     * @return an array of names of methods
     */
    public static String[] asList() {
        return new String[]{NONE.toString(),
            INFORMATION_GAIN.toString(),
            GAIN_RATIO.toString(),
            SYMMETRICAL_UNCERTAINTY.toString(),
            FISHER_SCORE.toString(),
            GINI_INDEX.toString(),
            LAPLACIAN_SCORE.toString(),
            MRMR.toString(),
            RRFS.toString()};
    }
}
