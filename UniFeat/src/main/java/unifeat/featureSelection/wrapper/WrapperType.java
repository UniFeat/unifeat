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
package unifeat.featureSelection.wrapper;

import unifeat.featureSelection.EnumType;
import java.util.Arrays;

/**
 * This java class is used to define the names of wrapper-based feature
 * selection methods.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public class WrapperType extends EnumType {

    public static final WrapperType NONE = new WrapperType("none");
    public static final WrapperType BPSO = new WrapperType("Binary PSO");
    public static final WrapperType CPSO = new WrapperType("Continuous PSO");
    public static final WrapperType PSO42 = new WrapperType("PSO(4-2)");
    public static final WrapperType HPSO_LS = new WrapperType("HPSO-LS");
    public static final WrapperType SIMPLE_GA = new WrapperType("Simple GA");
    public static final WrapperType HGAFS = new WrapperType("HGAFS");
    public static final WrapperType OPTIMAL_ACO = new WrapperType("Optimal ACO");

    /**
     * Creates new WrapperType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of wrapper method
     */
    private WrapperType(String name) {
        super(name);
    }

    /**
     * Returns the names of wrapper-based feature selection methods
     *
     * @return an array of names of methods
     */
    public static String[] asList() {
        return new String[]{NONE.toString(),
            BPSO.toString(),
            CPSO.toString(),
            PSO42.toString(),
            HPSO_LS.toString(),
            SIMPLE_GA.toString(),
            HGAFS.toString(),
            OPTIMAL_ACO.toString()};
    }

    /**
     * Converts the wrapper method name to WrapperType
     *
     * @param type the name of wrapper method as string
     *
     * @return the wrapper method type
     */
    public static WrapperType parse(String type) {
        return switch (type) {
            case "Binary PSO" -> BPSO;
            case "Continuous PSO" -> CPSO;
            case "PSO(4-2)" -> PSO42;
            case "HPSO-LS" -> HPSO_LS;
            case "Simple GA" -> SIMPLE_GA;
            case "HGAFS" -> HGAFS;
            case "Optimal ACO" -> OPTIMAL_ACO;
            default -> NONE;
        };
    }

    /**
     * Checks whether the method is wrapper-based feature selection method or not
     *
     * @param type the name of feature selection method
     *
     * @return true if method is wrapper-based method
     */
    public static boolean isWrapperMethod(String type) {
        return !type.equals(NONE.toString())
                && Arrays.asList(WrapperType.asList()).contains(type);
    }
}
