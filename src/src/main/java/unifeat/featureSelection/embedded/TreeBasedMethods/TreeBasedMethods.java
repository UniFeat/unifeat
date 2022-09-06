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
package unifeat.featureSelection.embedded.TreeBasedMethods;

import java.io.File;
import unifeat.featureSelection.embedded.EmbeddedApproach;
import unifeat.gui.featureSelection.embedded.decisionTreeBased.TreeType;
import weka.core.Instances;

/**
 * The abstract class contains the main methods and fields that are used in all
 * Tree-based feature selection methods. This class inherits from
 * EmbeddedApproach class.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.embedded.EmbeddedApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public abstract class TreeBasedMethods extends EmbeddedApproach {

    protected final String TEMP_PATH;
    protected final TreeType TREE_TYPE;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameter contains (<code>path</code>,
     * <code>tree type</code>) in which <code><b><i>path</i></b></code> is the
     * path of the project, and <code><b><i>tree type</i></b></code> is the type
     * of tree
     */
    public TreeBasedMethods(Object... arguments) {
        super((String) arguments[0]);
        TREE_TYPE = (TreeType) arguments[1];
        TEMP_PATH = PROJECT_PATH + "Temp" + File.separator;
    }

    /**
     * Finds the feature subset from the nodes of the created tree
     *
     * @param tree the generated tree based on the train set
     */
    abstract protected void selectedFeatureSubset(String tree);

    /**
     * Generates a classifier using input data
     *
     * @param dataTrain the data to train the classifier
     *
     * @return the output of the generated classifier
     */
    abstract protected String buildClassifier(Instances dataTrain);
}
