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
package unifeat.classifier.evaluation.wekaClassifier;

import unifeat.classifier.WekaSVMKernel;
import unifeat.gui.classifier.svmClassifier.SVMKernelType;
import unifeat.result.performanceMeasure.Criteria;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

/**
 * This java class is used to apply the classifiers for computing the
 * performance of the feature selection methods. The classifiers have been
 * implemented as the Weka software. The k-fold cross validation method is used
 * for evaluating classifiers.
 *
 * @author Sina Tabakhi
 */
public class CrossValidation {

    /**
     * This method builds and evaluates the support vector machine(SVM)
     * classifier. The SMO is used as the SVM classifier implemented in the Weka
     * software.
     *
     * @param pathTrainData the path of the train set
     * @param svmKernel the kernel to use
     * @param c the complexity parameter C
     * @param kFold the number of equal sized subsamples
     *
     * @return the different criteria values
     * @see unifeat.result.performanceMeasure.Criteria
     */
    public static Criteria SVM(String pathTrainData, SVMKernelType svmKernel, double c, int kFold) {
        Criteria criteria = new Criteria();
        try {
            BufferedReader readerTrain = new BufferedReader(new FileReader(pathTrainData));
            Instances dataTrain = new Instances(readerTrain);
            readerTrain.close();
            dataTrain.setClassIndex(dataTrain.numAttributes() - 1);

            SMO svm = new SMO();
            svm.setC(c);
            svm.setKernel(WekaSVMKernel.parse(svmKernel));
            svm.buildClassifier(dataTrain);
            Evaluation eval = new Evaluation(dataTrain);
            eval.crossValidateModel(svm, dataTrain, kFold, new Random(1));

            //Set different criteria values
            criteria.setErrorRate(eval.errorRate() * 100);
            criteria.setAccuracy(100 - criteria.getErrorRate());
        } catch (Exception ex) {
            Logger.getLogger(CrossValidation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return criteria;
    }

    /**
     * This method builds and evaluates the naiveBayes(NB) classifier. The
     * naiveBayes is used as the NB classifier implemented in the Weka software.
     *
     * @param pathTrainData the path of the train set
     * @param kFold the number of equal sized subsamples
     *
     * @return the different criteria values
     * @see unifeat.result.performanceMeasure.Criteria
     */
    public static Criteria naiveBayes(String pathTrainData, int kFold) {
        Criteria criteria = new Criteria();
        try {
            BufferedReader readerTrain = new BufferedReader(new FileReader(pathTrainData));
            Instances dataTrain = new Instances(readerTrain);
            readerTrain.close();
            dataTrain.setClassIndex(dataTrain.numAttributes() - 1);

            NaiveBayes nb = new NaiveBayes();
            nb.buildClassifier(dataTrain);
            Evaluation eval = new Evaluation(dataTrain);
            eval.crossValidateModel(nb, dataTrain, kFold, new Random(1));

            //Set different criteria values
            criteria.setErrorRate(eval.errorRate() * 100);
            criteria.setAccuracy(100 - criteria.getErrorRate());
        } catch (Exception ex) {
            Logger.getLogger(CrossValidation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return criteria;
    }

    /**
     * This method builds and evaluates the decision tree(DT) classifier. The
     * j48 is used as the DT classifier implemented in the Weka software.
     *
     * @param pathTrainData the path of the train set
     * @param confidenceValue The confidence factor used for pruning
     * @param minNumSampleInLeaf The minimum number of instances per leaf
     * @param kFold the number of equal sized subsamples
     *
     * @return the different criteria values
     * @see unifeat.result.performanceMeasure.Criteria
     */
    public static Criteria dTree(String pathTrainData, double confidenceValue, int minNumSampleInLeaf, int kFold) {
        Criteria criteria = new Criteria();
        try {
            BufferedReader readerTrain = new BufferedReader(new FileReader(pathTrainData));
            Instances dataTrain = new Instances(readerTrain);
            readerTrain.close();
            dataTrain.setClassIndex(dataTrain.numAttributes() - 1);

            J48 decisionTree = new J48();
            decisionTree.setConfidenceFactor((float) confidenceValue);
            decisionTree.setMinNumObj(minNumSampleInLeaf);
            decisionTree.buildClassifier(dataTrain);
            Evaluation eval = new Evaluation(dataTrain);
            eval.crossValidateModel(decisionTree, dataTrain, kFold, new Random(1));

            //Set different criteria values
            criteria.setErrorRate(eval.errorRate() * 100);
            criteria.setAccuracy(100 - criteria.getErrorRate());
        } catch (Exception ex) {
            Logger.getLogger(CrossValidation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return criteria;
    }

    /**
     * This method builds and evaluates the k-nearest neighbours(knn)
     * classifier. The IBk is used as the knn classifier implemented in the Weka
     * software.
     *
     * @param pathTrainData the path of the train set
     * @param kNNValue the number of neighbours to use
     * @param kFold the number of equal sized subsamples
     *
     * @return the different criteria values
     * @see unifeat.result.performanceMeasure.Criteria
     */
    public static Criteria kNN(String pathTrainData, int kNNValue, int kFold) {
        Criteria criteria = new Criteria();
        try {
            BufferedReader readerTrain = new BufferedReader(new FileReader(pathTrainData));
            Instances dataTrain = new Instances(readerTrain);
            readerTrain.close();
            dataTrain.setClassIndex(dataTrain.numAttributes() - 1);

            IBk knn = new IBk();
            knn.setKNN(kNNValue);
            knn.buildClassifier(dataTrain);
            Evaluation eval = new Evaluation(dataTrain);
            eval.crossValidateModel(knn, dataTrain, kFold, new Random(1));

            //Set different criteria values
            criteria.setErrorRate(eval.errorRate() * 100);
            criteria.setAccuracy(100 - criteria.getErrorRate());
        } catch (Exception ex) {
            Logger.getLogger(CrossValidation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return criteria;
    }
}
