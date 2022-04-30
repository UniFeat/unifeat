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

import unifeat.featureSelection.EnumType;

/**
 * This java class is used to define the names of classifiers.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public final class ClassifierType extends EnumType {

    public static final ClassifierType NONE = new ClassifierType("none", 0);
    public static final ClassifierType SVM = new ClassifierType("Support Vector Machine (SVM)", 1);
    public static final ClassifierType NB = new ClassifierType("Naive Bayes (NB)", 2);
    public static final ClassifierType DT = new ClassifierType("Decision Tree (C4.5)", 3);
    public static final ClassifierType KNN = new ClassifierType("K-Nearest Neighbours (KNN)", 4);

    /**
     * Creates new ClassifierType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of classifier
     */
    private ClassifierType(String name) {
        super(name);
    }

    /**
     * Creates new ClassifierType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of classifier
     * @param value the value of classifier
     */
    private ClassifierType(String name, int value) {
        super(name, value);
    }

    /**
     * Returns the names of classifiers
     *
     * @return an array of names of classifiers
     */
    public static String[] asList() {
        return new String[]{NONE.toString(),
            SVM.toString(),
            NB.toString(),
            DT.toString(),
            KNN.toString()};
    }

    /**
     * Converts the classifier name to ClassifierType
     *
     * @param type the name of classifier as string
     *
     * @return the classifier type
     */
    public static ClassifierType parse(String type) {
        return switch (type) {
            case "Support Vector Machine (SVM)" -> SVM;
            case "Naive Bayes (NB)" -> NB;
            case "Decision Tree (C4.5)" -> DT;
            case "K-Nearest Neighbours (KNN)" -> KNN;
            default -> NONE;
        };
    }
}
