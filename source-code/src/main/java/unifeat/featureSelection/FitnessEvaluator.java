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
package unifeat.featureSelection;

import unifeat.classifier.ClassifierType;
import unifeat.classifier.evaluation.wekaClassifier.CrossValidation;
import unifeat.gui.classifier.DTClassifierPanel;
import unifeat.gui.classifier.KNNClassifierPanel;
import unifeat.gui.classifier.svmClassifier.SVMClassifierPanel;
import unifeat.result.performanceMeasure.Criteria;
import unifeat.util.FileFunc;

/**
 * This java class is used to implement fitness evaluator of a solution in which
 * k-fold cross validation on training set is used for evaluating the
 * classification performance of a selected feature subset.
 *
 * @author Sina Tabakhi
 * @see unifeat.classifier.evaluation.wekaClassifier.CrossValidation
 */
public class FitnessEvaluator {

    private final String TEMP_PATH;
    private double[][] trainSet;
    private String[] nameFeatures;
    private String[] classLabel;
    private Object selectedEvaluationClassifierPanel;
    private ClassifierType classifierType = ClassifierType.NONE;
    private int kFolds;

    /**
     * Initializes the parameters
     *
     * @param path the temp path in the project
     * @param classifierName the name of given classifier
     * @param selectedEvaluationClassifierPanel panel of the selected classifier
     * contained the parameter values
     * @param kFolds the number of equal sized subsamples that is used in k-fold
     * cross validation
     */
    public FitnessEvaluator(String path, Object classifierName,
            Object selectedEvaluationClassifierPanel, int kFolds) {
        this.TEMP_PATH = path;
        this.classifierType = ClassifierType.parse(classifierName.toString());
        this.selectedEvaluationClassifierPanel = selectedEvaluationClassifierPanel;
        this.kFolds = kFolds;
    }

    /**
     * This method sets the information of the dataset.
     *
     * @param data the input dataset values
     * @param nameFeatures the string array of features names
     * @param classLabel the string array of class labels names
     */
    public void setDataInfo(double[][] data, String[] nameFeatures, String[] classLabel) {
        this.trainSet = data;
        this.nameFeatures = nameFeatures;
        this.classLabel = classLabel;
    }

    /**
     * This method performs k-fold cross validation on the reduced training set
     * which is achieved by selected feature subset.
     *
     * @param selectedFeature an array of indices of the selected feature subset
     *
     * @return the different criteria values
     */
    public Criteria crossValidation(int[] selectedFeature) {
        Criteria critria = new Criteria();
        String nameDataCSV = TEMP_PATH + "dataCSV.csv";
        String nameDataARFF = TEMP_PATH + "dataARFF.arff";
        FileFunc.createCSVFile(trainSet, selectedFeature, nameDataCSV, nameFeatures, classLabel);
        FileFunc.convertCSVtoARFF(nameDataCSV, nameDataARFF, TEMP_PATH, selectedFeature.length, nameFeatures.length - 1, nameFeatures, classLabel.length, classLabel);

        if (classifierType == ClassifierType.SVM) {
            SVMClassifierPanel svmPanel = (SVMClassifierPanel) selectedEvaluationClassifierPanel;
            critria = CrossValidation.SVM(nameDataARFF, svmPanel.getKernel(), svmPanel.getParameterC(), this.kFolds);
        } else if (classifierType == ClassifierType.NB) {
            critria = CrossValidation.naiveBayes(nameDataARFF, this.kFolds);
        } else if (classifierType == ClassifierType.DT) {
            DTClassifierPanel dtPanel = (DTClassifierPanel) selectedEvaluationClassifierPanel;
            critria = CrossValidation.dTree(nameDataARFF, dtPanel.getConfidence(), dtPanel.getMinNum(), this.kFolds);
        } else if (classifierType == ClassifierType.KNN) {
            KNNClassifierPanel dtPanel = (KNNClassifierPanel) selectedEvaluationClassifierPanel;
            critria = CrossValidation.kNN(nameDataARFF, dtPanel.getKNNValue(), this.kFolds);
        }

        return critria;
    }

    /**
     * This method creates a directory based on the specific path
     */
    public void createTempDirectory() {
        FileFunc.createDirectory(TEMP_PATH);
    }

    /**
     * This method deletes the current directory with all files in the directory
     */
    public void deleteTempDirectory() {
        FileFunc.deleteDirectoryWithAllFiles(TEMP_PATH);
    }
}
