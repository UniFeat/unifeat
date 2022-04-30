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

import unifeat.gui.featureSelection.embedded.decisionTreeBased.TreeType;
import unifeat.util.ArraysFunc;
import unifeat.util.FileFunc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;

/**
 * This java class is used to implement the decision tree based methods.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.embedded.TreeBasedMethods.TreeBasedMethods
 * @see unifeat.featureSelection.embedded.EmbeddedApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public class DecisionTreeBasedMethod extends TreeBasedMethods {

    private double confidenceValue;
    private int minNumSampleInLeaf;
    private int randomTreeKValue;
    private int randomTreeMaxDepth;
    private double randomTreeMinNum;
    private double randomTreeMinVarianceProp;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameter contains
     * <p>
     * if the type of tree is C4.5 (<code>path</code>, <code>tree type</code>,
     * <code>confidenceValue</code>, <code>minNumSampleInLeaf</code>) in which
     * <code><b><i>path</i></b></code> is the path of the project,
     * <code><b><i>tree type</i></b></code> is the type of tree,
     * <code><b><i>confidenceValue</i></b></code> is the confidence factor used
     * for pruning, <code><b><i>minNumSampleInLeaf</i></b></code> is the minimum
     * number of samples per leaf
     *
     * <p>
     * if the type of tree is random tree (<code>path</code>,
     * <code>tree type</code>, <code>KValue</code>, <code>MaxDepth</code>,
     * <code>MinNum,</code>, <code>MinVarianceProp</code>) in which
     * <code><b><i>path</i></b></code> is the path of the project,
     * <code><b><i>tree type</i></b></code> is the type of tree,
     * <code><b><i>KValue</i></b></code> is the number of randomly chosen
     * attributes, <code><b><i>MaxDepth</i></b></code> is the maximum depth of
     * the tree, <code><b><i>MinNum</i></b></code> is the minimum total weight
     * of the instances in a leaf, <code><b><i>MinVarianceProp</i></b></code> is
     * the minimum proportion of the total variance
     */
    public DecisionTreeBasedMethod(Object... arguments) {
        super(arguments);
        if (TREE_TYPE == TreeType.C45) {
            confidenceValue = (double) arguments[2];
            minNumSampleInLeaf = (int) arguments[3];
        } else if (TREE_TYPE == TreeType.RANDOM_TREE) {
            randomTreeKValue = (int) arguments[2];
            randomTreeMaxDepth = (int) arguments[3];
            randomTreeMinNum = (double) arguments[4];
            randomTreeMinVarianceProp = (double) arguments[5];
        }
    }

    /**
     * Initializes the parameters
     *
     * @param path the path of the project
     * @param confidence the confidence factor used for pruning
     * @param minNum the minimum number of samples per leaf
     */
    public DecisionTreeBasedMethod(String path, double confidence, int minNum) {
        super(path, TreeType.C45);
        this.confidenceValue = confidence;
        this.minNumSampleInLeaf = minNum;
    }

    /**
     * Initializes the parameters
     *
     * @param path the path of the project
     * @param randomTreeKValue the number of randomly chosen attributes
     * @param randomTreeMaxDepth the maximum depth of the tree
     * @param randomTreeMinNum the minimum total weight of the instances in a
     * leaf
     * @param randomTreeMinVarianceProp the minimum proportion of the total
     * variance (over all the data) required for split
     */
    public DecisionTreeBasedMethod(String path, int randomTreeKValue, int randomTreeMaxDepth,
            double randomTreeMinNum, double randomTreeMinVarianceProp) {
        super(path, TreeType.RANDOM_TREE);
        this.randomTreeKValue = randomTreeKValue;
        this.randomTreeMaxDepth = randomTreeMaxDepth;
        this.randomTreeMinNum = randomTreeMinNum;
        this.randomTreeMinVarianceProp = randomTreeMinVarianceProp;
    }

    /**
     * Finds the feature subset from the nodes of the created tree (Used for C4.5
     * and Random Tree)
     *
     * @param tree the generated tree based on the train set
     */
    @Override
    protected void selectedFeatureSubset(String tree) {
        String[] lines = tree.split("\n");
        ArrayList<Integer> featureSubset = new ArrayList<>();

        for (String line : lines) {
            line = line.replace("|   ", " ").trim();
            if (line.lastIndexOf(" <= ") != -1) {
                line = line.substring(0, line.lastIndexOf(" <= "));
            } else if (line.lastIndexOf(" >= ") != -1) {
                line = line.substring(0, line.lastIndexOf(" >= "));
            } else if (line.lastIndexOf(" = ") != -1) {
                line = line.substring(0, line.lastIndexOf(" = "));
            } else if (line.lastIndexOf(" > ") != -1) {
                line = line.substring(0, line.lastIndexOf(" > "));
            } else if (line.lastIndexOf(" < ") != -1) {
                line = line.substring(0, line.lastIndexOf(" < "));
            } else {
                line = "";
            }

            line = line.trim();

            if (line.length() != 0) {
                int index = Arrays.asList(nameFeatures).indexOf(line);
                if (!featureSubset.contains(index)) {
                    featureSubset.add(index);
                }
            }
        }

        this.setNumSelectedFeature(featureSubset.size());

        for (int i = 0; i < numSelectedFeature; i++) {
            selectedFeatureSubset[i] = featureSubset.get(i);
        }

        ArraysFunc.sortArray1D(selectedFeatureSubset, false);

//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String buildClassifier(Instances dataTrain) {
        try {
            if (TREE_TYPE == TreeType.C45) {
                J48 decisionTreeC45 = new J48();
                decisionTreeC45.setConfidenceFactor((float) confidenceValue);
                decisionTreeC45.setMinNumObj(minNumSampleInLeaf);
                decisionTreeC45.buildClassifier(dataTrain);
                return decisionTreeC45.toString();
            } else if (TREE_TYPE == TreeType.RANDOM_TREE) {
                RandomTree decisionTreeRandomTree = new RandomTree();
                decisionTreeRandomTree.setKValue(randomTreeKValue);
                decisionTreeRandomTree.setMaxDepth(randomTreeMaxDepth);
                decisionTreeRandomTree.setMinNum(randomTreeMinNum);
                decisionTreeRandomTree.setMinVarianceProp(randomTreeMinVarianceProp);
                decisionTreeRandomTree.buildClassifier(dataTrain);
                return decisionTreeRandomTree.toString();
            }
        } catch (Exception ex) {
            Logger.getLogger(DecisionTreeBasedMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * Starts the feature selection process by Decision Tree based methods
     */
    @Override
    public void evaluateFeatures() {
        FileFunc.createDirectory(TEMP_PATH);
        String nameDataCSV = TEMP_PATH + "dataCSV.csv";
        String nameDataARFF = TEMP_PATH + "dataARFF.arff";

        FileFunc.createCSVFile(trainSet, originalFeatureSet(), nameDataCSV, nameFeatures, classLabel);
        FileFunc.convertCSVtoARFF(nameDataCSV, nameDataARFF, TEMP_PATH, numFeatures, numFeatures, nameFeatures, numClass, classLabel);

        try {
            BufferedReader readerTrain = new BufferedReader(new FileReader(nameDataARFF));
            Instances dataTrain = new Instances(readerTrain);
            readerTrain.close();
            dataTrain.setClassIndex(dataTrain.numAttributes() - 1);

            selectedFeatureSubset(buildClassifier(dataTrain));
            //System.out.println(buildClassifier(dataTrain));
        } catch (Exception ex) {
            Logger.getLogger(DecisionTreeBasedMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileFunc.deleteDirectoryWithAllFiles(TEMP_PATH);
    }
}
