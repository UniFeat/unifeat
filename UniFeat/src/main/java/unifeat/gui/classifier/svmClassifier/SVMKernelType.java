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
package unifeat.gui.classifier.svmClassifier;

import unifeat.featureSelection.EnumType;

/**
 * This java class is used to define the names of kernel used in SVM.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public final class SVMKernelType extends EnumType {

    public static final SVMKernelType NONE = new SVMKernelType("none");
    public static final SVMKernelType POLYNOMIAL = new SVMKernelType("Polynomial kernel");
    public static final SVMKernelType RBF = new SVMKernelType("RBF kernel");
    public static final SVMKernelType PEARSON_VII = new SVMKernelType("Pearson VII function-based universal kernel");

    /**
     * Creates new SVMKernelType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param kernelName the name of kernel
     */
    private SVMKernelType(String kernelName) {
        super(kernelName);
    }

    /**
     * Returns the names of kernel
     *
     * @return an array of names of kernel
     */
    public static String[] asList() {
        return new String[]{POLYNOMIAL.toString(),
            RBF.toString(),
            PEARSON_VII.toString()};
    }

    /**
     * Converts the kernel name to SVMKernelType
     *
     * @param type the name of kernel as string
     *
     * @return the SVM kernel type
     */
    public static SVMKernelType parse(String type) {
        return switch (type) {
            case "Polynomial kernel" -> POLYNOMIAL;
            case "RBF kernel" -> RBF;
            case "Pearson VII function-based universal kernel" -> PEARSON_VII;
            default -> NONE;
        };
    }
}
