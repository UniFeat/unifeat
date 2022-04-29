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
package unifeat.featureSelection.embedded;

import unifeat.featureSelection.EnumType;
import java.util.Arrays;

/**
 * This java class is used to define the names of embedded-based feature
 * selection methods.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public class EmbeddedType extends EnumType {

    public static final EmbeddedType NONE = new EmbeddedType("none");
    public static final EmbeddedType DECISION_TREE_BASED = new EmbeddedType("Decision tree based methods");
    public static final EmbeddedType RANDOM_FOREST_METHOD = new EmbeddedType("Random forest method");
    public static final EmbeddedType SVM_RFE = new EmbeddedType("SVM_RFE");
    public static final EmbeddedType MSVM_RFE = new EmbeddedType("MSVM_RFE");
    public static final EmbeddedType OVO_SVM_RFE = new EmbeddedType("OVO_SVM_RFE");
    public static final EmbeddedType OVA_SVM_RFE = new EmbeddedType("OVA_SVM_RFE");

    /**
     * Creates new EmbeddedType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of embedded method
     */
    private EmbeddedType(String name) {
        super(name);
    }

    /**
     * Returns the names of embedded-based feature selection methods
     *
     * @return an array of names of methods
     */
    public static String[] asList() {
        return new String[]{NONE.toString(),
            DECISION_TREE_BASED.toString(),
            RANDOM_FOREST_METHOD.toString(),
            SVM_RFE.toString(),
            MSVM_RFE.toString(),
            OVO_SVM_RFE.toString(),
            OVA_SVM_RFE.toString()};
    }

    /**
     * Converts the embedded method name to EmbeddedType
     *
     * @param type the name of embedded method as string
     *
     * @return the embedded method type
     */
    public static EmbeddedType parse(String type) {
        return switch (type) {
            case "Decision tree based methods" -> DECISION_TREE_BASED;
            case "Random forest method" -> RANDOM_FOREST_METHOD;
            case "SVM_RFE" -> SVM_RFE;
            case "MSVM_RFE" -> MSVM_RFE;
            case "OVO_SVM_RFE" -> OVO_SVM_RFE;
            case "OVA_SVM_RFE" -> OVA_SVM_RFE;
            default -> NONE;
        };
    }

    /**
     * Checks whether the method is embedded-based feature selection method or
     * not
     *
     * @param type the name of feature selection method
     *
     * @return true if method is embedded-based method
     */
    public static boolean isEmbeddedMethod(String type) {
        return !type.equals(NONE.toString())
                && Arrays.asList(EmbeddedType.asList()).contains(type);
    }
}
