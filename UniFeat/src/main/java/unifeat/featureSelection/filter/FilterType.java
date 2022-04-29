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

import unifeat.featureSelection.EnumType;

/**
 * This java class is used to define the names of filter-based feature selection
 * methods.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public class FilterType extends EnumType {

    public static final FilterType NONE = new FilterType("none");

    //Supervised filter-based feature selection methods
    public static final FilterType INFORMATION_GAIN = new FilterType("Information gain");
    public static final FilterType GAIN_RATIO = new FilterType("Gain ratio");
    public static final FilterType SYMMETRICAL_UNCERTAINTY = new FilterType("Symmetrical uncertainty");
    public static final FilterType FISHER_SCORE = new FilterType("Fisher score");
    public static final FilterType GINI_INDEX = new FilterType("Gini index");
    public static final FilterType MRMR = new FilterType("Minimal redundancy maximal relevance (MRMR)");

    //Unsupervised filter-based feature selection methods
    public static final FilterType TERM_VARIANCE = new FilterType("Term variance");
    public static final FilterType MUTUAL_CORRELATION = new FilterType("Mutual correlation");
    public static final FilterType RSM = new FilterType("Random subspace method (RSM)");
    public static final FilterType UFSACO = new FilterType("UFSACO");
    public static final FilterType RRFSACO_1 = new FilterType("RRFSACO_1");
    public static final FilterType RRFSACO_2 = new FilterType("RRFSACO_2");
    public static final FilterType IRRFSACO_1 = new FilterType("IRRFSACO_1");
    public static final FilterType IRRFSACO_2 = new FilterType("IRRFSACO_2");
    public static final FilterType MGSACO = new FilterType("MGSACO");

    //Supervised and unsupervised-based feature selection methods
    public static final FilterType LAPLACIAN_SCORE = new FilterType("Laplacian score");
    public static final FilterType RRFS = new FilterType("Relevance-redundancy feature selection (RRFS)");

    /**
     * Creates new FilterType. This method is called from within the constructor
     * to initialize the parameter.
     *
     * @param name the name of filter method
     */
    public FilterType(String name) {
        super(name);
    }

    /**
     * Converts the filter method name to FilterType
     *
     * @param type the name of filter method as string
     *
     * @return the filter method type
     */
    public static FilterType parse(String type) {
        return switch (type) {
            case "none" -> NONE;
            case "Information gain" -> INFORMATION_GAIN;
            case "Gain ratio" -> GAIN_RATIO;
            case "Symmetrical uncertainty" -> SYMMETRICAL_UNCERTAINTY;
            case "Fisher score" -> FISHER_SCORE;
            case "Gini index" -> GINI_INDEX;
            case "Laplacian score" -> LAPLACIAN_SCORE;
            case "Minimal redundancy maximal relevance (MRMR)" -> MRMR;
            case "Relevance-redundancy feature selection (RRFS)" -> RRFS;
            case "Term variance" -> TERM_VARIANCE;
            case "Mutual correlation" -> MUTUAL_CORRELATION;
            case "Random subspace method (RSM)" -> RSM;
            case "UFSACO" -> UFSACO;
            case "RRFSACO_1" -> RRFSACO_1;
            case "RRFSACO_2" -> RRFSACO_2;
            case "IRRFSACO_1" -> IRRFSACO_1;
            case "IRRFSACO_2" -> IRRFSACO_2;
            case "MGSACO" -> MGSACO;
            default -> NONE;
        };
    }
}
