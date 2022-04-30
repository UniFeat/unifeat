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

import unifeat.featureSelection.FeatureSelection;

/**
 * The abstract class contains the main methods and fields that are used in all
 * hybrid-based feature selection methods. This class inherits from
 * FeatureSelection class.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.FeatureSelection
 */
public abstract class HybridApproach extends FeatureSelection {

    /**
     * Initializes the parameters
     */
    public HybridApproach() {
        super();
    }

    /**
     * This method creates new object from one of the classes that has been
     * inherited from the HybridApproach class according to type of the feature 
     * selection method.
     * 
     * @param type type of the hybrid feature selection method
     * @param arguments a list of arguments that is applied in the feature
     * selection method
     * 
     * @return the created object that has been inherited from the 
     * HybridApproach class
     */
    public static HybridApproach newMethod(HybridType type, Object... arguments) {
        return null;
    }
}
