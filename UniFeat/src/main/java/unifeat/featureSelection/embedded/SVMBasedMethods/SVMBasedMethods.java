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
package unifeat.featureSelection.embedded.SVMBasedMethods;

import unifeat.classifier.WekaSVMKernel;
import unifeat.featureSelection.embedded.EmbeddedApproach;
import unifeat.gui.classifier.svmClassifier.SVMKernelType;
import unifeat.util.ArraysFunc;
import unifeat.util.FileFunc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.functions.SMO;
import weka.core.Instances;

/**
 * The abstract class contains the main methods and fields that are used in all
 * SVM-based feature selection methods. This class inherits from
 * EmbeddedApproach class.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.embedded.EmbeddedApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public abstract class SVMBasedMethods extends EmbeddedApproach {

    protected final String TEMP_PATH;
    protected SVMKernelType kernelType;
    protected double parameterC;
    protected double[] classLabelInTrainSet;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains (<code>path</code>,
     * <code>kernelType</code>, <code>Parameter c</code>) in which
     * <code><b><i>path</i></b></code> is the path of the project,
     * <code><b><i>kernelType</i></b></code> is the type of kernel to use, and
     * <code><b><i>Parameter c</i></b></code> is the complexity parameter C
     */
    public SVMBasedMethods(Object... arguments) {
        super((String) arguments[0]);
        kernelType = (SVMKernelType) arguments[1];
        parameterC = (double) arguments[2];
        TEMP_PATH = PROJECT_PATH + "Temp\\";
    }

    /**
     * Initializes the parameters
     *
     * @param path the path of the project
     * @param kernelType the type of kernel to use
     * @param c the complexity parameter C
     */
    public SVMBasedMethods(String path, SVMKernelType kernelType, double c) {
        super(path);
        this.kernelType = kernelType;
        this.parameterC = c;
        TEMP_PATH = PROJECT_PATH + "Temp\\";
    }

    /**
     * Creates an array of class labels available in the train set
     */
    public void createClassLabel() {
        ArrayList<Double> labels = new ArrayList<>();
        for (double[] sample : trainSet) {
            if (!labels.contains(sample[this.numFeatures])) {
                labels.add(sample[this.numFeatures]);
            }
        }

        this.classLabelInTrainSet = new double[this.numClass];
        for (int i = 0; i < labels.size(); i++) {
            this.classLabelInTrainSet[i] = labels.get(i);
        }
    }

    /**
     * Generates binary classifiers (SVM) using input data and based on selected
     * feature subset, and finally returns the weights of features.
     * One-Versus-One strategy is used to construct classifiers in multiclass
     * classification.
     *
     * @param selectedFeature an array of indices of the selected feature subset
     *
     * @return an array of the weights of features
     */
    protected double[][][] buildSVM_OneAgainstOne(int[] selectedFeature) {
        String nameDataCSV = TEMP_PATH + "dataCSV.csv";
        String nameDataARFF = TEMP_PATH + "dataARFF.arff";
        double[][][] weights = new double[numClass][numClass][selectedFeature.length];

        FileFunc.createCSVFile(trainSet, selectedFeature, nameDataCSV, nameFeatures, classLabel);
        FileFunc.convertCSVtoARFF(nameDataCSV, nameDataARFF, TEMP_PATH, selectedFeature.length, numFeatures, nameFeatures, numClass, classLabel);

        try {
            BufferedReader readerTrain = new BufferedReader(new FileReader(nameDataARFF));
            Instances dataTrain = new Instances(readerTrain);
            readerTrain.close();
            dataTrain.setClassIndex(dataTrain.numAttributes() - 1);

            SMO svm = new SMO();
            svm.setC(parameterC);
            svm.setKernel(WekaSVMKernel.parse(kernelType));
            svm.buildClassifier(dataTrain);

            for (int i = 0; i < numClass; i++) {
                for (int j = i + 1; j < numClass; j++) {
                    double[] weightsSparse = svm.sparseWeights()[i][j];
                    int[] indicesSparse = svm.sparseIndices()[i][j];
                    for (int k = 0; k < weightsSparse.length; k++) {
                        weights[i][j][indicesSparse[k]] = weightsSparse[k];
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(SVMBasedMethods.class.getName()).log(Level.SEVERE, null, ex);
        }

        return weights;
    }

    /**
     * Generates binary classifiers (SVM) using input data and based on selected
     * feature subset, and finally returns the weights of features.
     * One-Versus-All strategy is used to construct classifiers in multiclass
     * classification.
     *
     * @param selectedFeature an array of indices of the selected feature subset
     *
     * @return an array of the weights of features
     */
    protected double[][] buildSVM_OneAgainstRest(int[] selectedFeature) {
        double[][] weights = new double[numClass][selectedFeature.length];
        String[] tempClassLabel = new String[]{"c1", "c2"};

        for (int indexClass = 0; indexClass < numClass; indexClass++) {
            double[][] copyTrainSet = ArraysFunc.copyDoubleArray2D(trainSet);
            String nameDataCSV = TEMP_PATH + "dataCSV" + indexClass + ".csv";
            String nameDataARFF = TEMP_PATH + "dataARFF" + indexClass + ".arff";

            for (double[] dataRow : copyTrainSet) {
                if (dataRow[numFeatures] == classLabelInTrainSet[indexClass]) {
                    dataRow[numFeatures] = 0;
                } else {
                    dataRow[numFeatures] = 1;
                }
            }

            FileFunc.createCSVFile(copyTrainSet, selectedFeature, nameDataCSV, nameFeatures, tempClassLabel);
            FileFunc.convertCSVtoARFF(nameDataCSV, nameDataARFF, TEMP_PATH, selectedFeature.length, numFeatures, nameFeatures, tempClassLabel.length, tempClassLabel);

            try {
                BufferedReader readerTrain = new BufferedReader(new FileReader(nameDataARFF));
                Instances dataTrain = new Instances(readerTrain);
                readerTrain.close();
                dataTrain.setClassIndex(dataTrain.numAttributes() - 1);

                SMO svm = new SMO();
                svm.setC(parameterC);
                svm.setKernel(WekaSVMKernel.parse(kernelType));
                svm.buildClassifier(dataTrain);

                double[] weightsSparse = svm.sparseWeights()[0][1];
                int[] indicesSparse = svm.sparseIndices()[0][1];
                for (int k = 0; k < weightsSparse.length; k++) {
                    weights[indexClass][indicesSparse[k]] = weightsSparse[k];
                }
            } catch (Exception ex) {
                Logger.getLogger(SVMBasedMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return weights;
    }
}
