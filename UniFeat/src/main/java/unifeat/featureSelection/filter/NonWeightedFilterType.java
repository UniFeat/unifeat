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

import java.util.Arrays;

/**
 * This java class is used to define the names of non weighted filter-based
 * feature selection methods.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.FilterType
 */
public class NonWeightedFilterType extends FilterType {

    /**
     * Creates new NonWeightedFilterType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of non weighted filter method
     */
    private NonWeightedFilterType(String name) {
        super(name);
    }

    /**
     * Returns the names of filter-based feature selection methods that are not
     * feature weighting
     *
     * @return an array of names of methods
     */
    public static String[] asList() {
        return new String[]{MRMR.toString(),
            RRFS.toString(),
            MUTUAL_CORRELATION.toString(),
            RSM.toString(),
            UFSACO.toString(),
            RRFSACO_1.toString(),
            RRFSACO_2.toString(),
            IRRFSACO_1.toString(),
            IRRFSACO_2.toString(),
            MGSACO.toString()};
    }

    /**
     * Checks whether the method is filter-based feature weighting method or not
     *
     * @param type the name of feature selection method
     *
     * @return true if method is filter-based method(Methods that are not
     * classified as feature weighting method)
     */
    public static boolean isNonWeightedFilterMethod(String type) {
        return Arrays.asList(NonWeightedFilterType.asList()).contains(type);
    }
}
