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
package unifeat.gui.featureSelection.filter.rsm;

import unifeat.featureSelection.EnumType;

/**
 * This java class is used to define the names of multivariate method used in
 * RSM feature selection method.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public final class MultivariateMethodType extends EnumType {

    public static final MultivariateMethodType NONE = new MultivariateMethodType("none");
    public static final MultivariateMethodType MUTUAL_CORRELATION = new MultivariateMethodType("Mutual correlation");

    /**
     * Creates new MultivariateMethodType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of multivariate method
     */
    private MultivariateMethodType(String name) {
        super(name);
    }

    /**
     * Returns the names of multivariate method
     *
     * @return an array of multivariate methods
     */
    public static String[] asList() {
        return new String[]{MUTUAL_CORRELATION.toString()};
    }

    /**
     * Converts the multivariate method name to MultivariateMethodType
     *
     * @param type the name of multivariate method as string
     *
     * @return the multivariate method type
     */
    public static MultivariateMethodType parse(String type) {
        return switch (type) {
            case "Mutual correlation" -> MUTUAL_CORRELATION;
            default -> NONE;
        };
    }
}
