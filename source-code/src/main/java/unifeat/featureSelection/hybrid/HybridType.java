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
package unifeat.featureSelection.hybrid;

import unifeat.featureSelection.EnumType;
import java.util.Arrays;

/**
 * This java class is used to define the names of hybrid-based feature selection
 * methods.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public class HybridType extends EnumType {

    public static final HybridType NONE = new HybridType("none");

    /**
     * Creates new HybridType. This method is called from within the constructor
     * to initialize the parameter.
     *
     * @param name the name of hybrid method
     */
    private HybridType(String name) {
        super(name);
    }

    /**
     * Returns the names of hybrid-based feature selection methods
     *
     * @return an array of names of methods
     */
    public static String[] asList() {
        return new String[]{NONE.toString()};
    }

    /**
     * Converts the hybrid method name to HybridType
     *
     * @param type the name of hybrid method as string
     *
     * @return the hybrid method type
     */
    public static HybridType parse(String type) {
        return switch (type) {
            case "none" -> NONE;
            default -> NONE;
        };
    }

    /**
     * Checks whether the method is hybrid-based feature selection method or not
     *
     * @param type the name of feature selection method
     *
     * @return true if method is hybrid-based method
     */
    public static boolean isHybridMethod(String type) {
        return !type.equals(NONE.toString())
                && Arrays.asList(HybridType.asList()).contains(type);
    }
}
