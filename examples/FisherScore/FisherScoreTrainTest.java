package FisherScore;

import unifeat.dataset.DatasetInfo;
import unifeat.featureSelection.filter.supervised.FisherScore;
import unifeat.util.FileFunc;

public class FisherScoreTrainTest {

    public static void main(String[] args) {
        //reading the datasets files
        DatasetInfo data = new DatasetInfo();
        data.preProcessing("data/trainSet.csv", "data/testSet.csv", "data/classLabels.txt");

        //printing some information of the dataset
        int sizeSelectedFeatureSubset = 2;
        System.out.println(" no. of all samples : " + data.getNumData()
                + "\n no. of samples in training set :  " + data.getNumTrainSet()
                + "\n no .of samples in test set : " + data.getNumTestSet()
                + "\n no. of features : " + data.getNumFeature()
                + "\n no. of classes : " + data.getNumClass());

        //performing the feature selection by fisher score method
        FisherScore method = new FisherScore(sizeSelectedFeatureSubset);
        method.loadDataSet(data);

        String message = method.validate();
        //checking the validity of user input values
        if (!message.isEmpty()) {
            System.out.print("Error!\n  " + message);
        } else {
            method.evaluateFeatures();
            int[] subset = method.getSelectedFeatureSubset();
            double[] fisherScoreValues = method.getFeatureValues();

            //printing the subset of selected features
            System.out.print("\n subset of selected features: ");
            for (int i = 0; i < subset.length; i++) {
                System.out.print((subset[i] + 1) + "  ");
            }

            //printing the fisher score values
            System.out.println("\n\n fisher score values: ");
            for (int i = 0; i < fisherScoreValues.length; i++) {
                System.out.println(" " + (i + 1) + " : " + fisherScoreValues[i]);
            }

            //creating reduced datasets as the CSV file format
            FileFunc.createCSVFile(data.getTrainSet(), subset, "data/newTrainSet.csv", data.getNameFeatures(), data.getClassLabel());
            FileFunc.createCSVFile(data.getTestSet(), subset, "data/newTestSet.csv", data.getNameFeatures(), data.getClassLabel());

            //creating reduced datasets as the ARFF file format
            FileFunc.convertCSVtoARFF("data/newTrainSet.csv", "data/newTrainSet.arff", "data", sizeSelectedFeatureSubset, data);
            FileFunc.convertCSVtoARFF("data/newTestSet.csv", "data/newTestSet.arff", "data", sizeSelectedFeatureSubset, data);
        }
    }
}
