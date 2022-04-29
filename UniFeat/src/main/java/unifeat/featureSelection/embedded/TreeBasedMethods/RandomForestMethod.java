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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.Utils;

/**
 * This java class is used to implement the random forest based method for
 * feature selection.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.embedded.TreeBasedMethods.TreeBasedMethods
 * @see unifeat.featureSelection.embedded.EmbeddedApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public class RandomForestMethod extends TreeBasedMethods {

    private int randomForestNumFeatures;
    private int randomForestMaxDepth;
    private int randomForestNumIterations;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameter contains (<code>path</code>,
     * <code>tree type</code>, <code>NumFeatures</code>, <code>MaxDepth</code>,
     * <code>NumIterations</code>) in which <code><b><i>path</i></b></code> is
     * the path of the project, <code><b><i>tree type</i></b></code> is the type
     * of tree, <code><b><i>NumFeatures</i></b></code> is the number of randomly
     * selected features, <code><b><i>MaxDepth</i></b></code> is the maximum
     * depth of the tree, <code><b><i>NumIterations</i></b></code> is the number
     * of iterations to be performed
     */
    public RandomForestMethod(Object... arguments) {
        super(arguments);
        randomForestNumFeatures = (int) arguments[2];
        randomForestMaxDepth = (int) arguments[3];
        randomForestNumIterations = (int) arguments[4];
    }

    /**
     * Initializes the parameters
     *
     * @param path the path of the project
     * @param randomForestNumFeatures the number of randomly selected features
     * @param randomForestMaxDepth the maximum depth of the tree
     * @param randomForestNumIterations the number of iterations to be performed
     */
    public RandomForestMethod(String path, int randomForestNumFeatures, int randomForestMaxDepth, int randomForestNumIterations) {
        super(path, TreeType.RANDOM_FOREST);
        this.randomForestNumFeatures = randomForestNumFeatures;
        this.randomForestMaxDepth = randomForestMaxDepth;
        this.randomForestNumIterations = randomForestNumIterations;
    }

    /**
     * Finds the feature subset from the nodes of the created tree (Used for
     * Random Forest)
     *
     * @param tree the generated tree based on the train set
     */
    @Override
    protected void selectedFeatureSubset(String tree) {
        String[] lines = tree.split(" ");
        int[] featureIndices = new int[numFeatures];

        for (int i = 0; i < featureIndices.length; i++) {
            featureIndices[i] = Integer.parseInt(lines[i]);
//            System.out.println("" + featureIndices[i]);
        }

        selectedFeatureSubset = Arrays.copyOfRange(featureIndices, 0, numSelectedFeature);
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
            RandomForest decisionTreeRandomForest = new RandomForest();
            decisionTreeRandomForest.setNumFeatures(randomForestNumFeatures);
            decisionTreeRandomForest.setMaxDepth(randomForestMaxDepth);
            decisionTreeRandomForest.setNumIterations(randomForestNumIterations);
            decisionTreeRandomForest.setComputeAttributeImportance(true);
            decisionTreeRandomForest.buildClassifier(dataTrain);

            /**
             * Creating an array of indices of the features based on descending
             * order of features' importance
             */
            double[] nodeCounts = new double[numFeatures + 1];
            double[] impurityScores = decisionTreeRandomForest.computeAverageImpurityDecreasePerAttribute(nodeCounts);
            int[] sortedIndices = Utils.sort(impurityScores);
            String sortedIndicesToString = "";
            for (int i = sortedIndices.length - 1; i >= 0; i--) {
                if (sortedIndices[i] != numFeatures) {
                    sortedIndicesToString += String.valueOf(sortedIndices[i]) + " ";
                }
            }

            return sortedIndicesToString.trim();
            //return decisionTreeRandomForest.toString();
        } catch (Exception ex) {
            Logger.getLogger(RandomForestMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * Starts the feature selection process by Random Forest based method
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
        } catch (Exception ex) {
            Logger.getLogger(RandomForestMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileFunc.deleteDirectoryWithAllFiles(TEMP_PATH);
    }
}
