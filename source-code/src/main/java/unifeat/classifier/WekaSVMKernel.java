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
package unifeat.classifier;

import unifeat.gui.classifier.svmClassifier.SVMKernelType;
import weka.classifiers.functions.supportVector.*;

/**
 * This java class is used to convert SVM kernel implemented in unifeat tool to SVM
 * kernel implemented in weka software.
 *
 * @author Sina Tabakhi
 */
public final class WekaSVMKernel {

    /**
     * This method return the weka SVM kernel type according to the unifeat SVM
     * kernel type.
     *
     * @param type unifeat SVM kernel type
     *
     * @return weka SVM kernel type
     */
    public static Kernel parse(SVMKernelType type) {
        try {
            if (type == SVMKernelType.POLYNOMIAL) {
                return PolyKernel.class.newInstance();
            } else if (type == SVMKernelType.RBF) {
                return RBFKernel.class.newInstance();
            } else if (type == SVMKernelType.PEARSON_VII) {
                return Puk.class.newInstance();
            }
            return null;
        } catch (InstantiationException | IllegalAccessException ex) {
            return null;
        }
    }
}
