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
package unifeat.result;

import unifeat.featureSelection.EnumType;

/**
 * This java class is used to define the types of result.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public class ResultType extends EnumType {

    public static final ResultType NONE = new ResultType("none");
    public static final ResultType DATASET_INFORMATION = new ResultType("Dataset information");
    public static final ResultType SELECTED_FEATURE_SUBSET = new ResultType("Selected feature subset");
    public static final ResultType FEATURE_VALUES = new ResultType("Feature values");
    public static final ResultType PERFORMANCE_MEASURES = new ResultType("Performance measures");

    /**
     * Creates new ResultType. This method is called from within the constructor
     * to initialize the parameter.
     *
     * @param name the name of result
     */
    private ResultType(String name) {
        super(name);
    }

    /**
     * Returns the names of result
     *
     * @return an array of names of result
     */
    public static String[] asList() {
        return new String[]{NONE.toString(),
            DATASET_INFORMATION.toString(),
            SELECTED_FEATURE_SUBSET.toString(),
            FEATURE_VALUES.toString(),
            PERFORMANCE_MEASURES.toString()};
    }

    /**
     * Converts the result name to ResultType
     *
     * @param type the name of result as string
     *
     * @return the result type
     *
     * @throws Exception if the specified type is not available
     */
    public static ResultType parse(String type) throws Exception {
        switch (type) {
            case "none":
                return NONE;
            case "Dataset information":
                return DATASET_INFORMATION;
            case "Selected feature subset":
                return SELECTED_FEATURE_SUBSET;
            case "Feature values":
                return FEATURE_VALUES;
            case "Performance measures":
                return PERFORMANCE_MEASURES;
            default:
                throw new Exception("Undefine type");
        }
    }
}
