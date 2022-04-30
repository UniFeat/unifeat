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
package unifeat.util;

import unifeat.dataset.DatasetInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

/**
 * This java class is used to implement various utility methods for manipulating
 * files and directories. The methods in this class is contained brief 
 * description of the applications.
 *
 * @author Sina Tabakhi
 */
public final class FileFunc {
    
    /**
     * This method creates a new directory denoted by pathname
     *
     * @param pathname path of the directory 
     */
    public static void createDirectory(String pathname) {
        File dir = new File(pathname);
        dir.mkdir();
    }

    /**
     * This method deletes all files in the directory denoted by pathname
     * 
     * @param pathname path of the directory 
     */
    public static void deleteFilesInDirectory(String pathname) {
        File dir = new File(pathname);
        if (dir.isDirectory()) {
            File[] directory = dir.listFiles();
            for (File directoryPath : directory) {
                directoryPath.delete();
            }
        }
    }
    
    /**
     * This method deletes the current directory with all files in the directory
     * denoted by pathname
     * 
     * @param pathname path of the directory 
     */
    public static void deleteDirectoryWithAllFiles(String pathname) {
        deleteFilesInDirectory(pathname);
        File dir = new File(pathname);
        if (dir.isDirectory()) {
            dir.delete();
        }
    }
    
    /**
     * This method creates a CSV (Comma delimited) file of the input data
     *
     * @param oldData the input data
     * @param selectedFeature the list of selected Feature
     * @param name name of the path for created CSV file
     * @param featureNames a string array of features names
     * @param classNames a string array of class labels names
     */
    public static void createCSVFile(double[][] oldData, int[] selectedFeature, String name, String[] featureNames, String[] classNames) {
        int numSamples = oldData.length;
        int sizeFeatureSet = selectedFeature.length;
        int numFeats = oldData[0].length - 1;
        try {
            FileWriter fw = new FileWriter(name, false);
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < sizeFeatureSet; i++) {
                pw.print(featureNames[selectedFeature[i]] + ",");
            }
            pw.println(featureNames[numFeats]);
            for (int i = 0; i < numSamples; i++) {
                for (int j = 0; j < sizeFeatureSet; j++) {
                    pw.print(oldData[i][selectedFeature[j]] + ",");
                }
                int index = (int) oldData[i][numFeats];
                pw.println(classNames[index]);
            }
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(FileFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method converts CSV file to ARFF file for the Weka Software
     *
     * @param nameCSV the path of the CSV file
     * @param nameARFF the path for the created ARFF file
     * @param pathProject the path of the project
     * @param sizeFeatureSubset the number of selected features
     * @param ob the object of the DatasetInfo
     */
    public static void convertCSVtoARFF(String nameCSV, String nameARFF, String pathProject, int sizeFeatureSubset, DatasetInfo ob) {
        convertCSVtoARFF(nameCSV, nameARFF, pathProject, sizeFeatureSubset,
                ob.getNumFeature(), ob.getNameFeatures(), ob.getNumClass(), ob.getClassLabel());
    }
    
    /**
     * This method converts CSV file to ARFF file for the Weka Software
     *
     * @param nameCSV the path of the CSV file
     * @param nameARFF the path for the created ARFF file
     * @param pathProject the path of the project
     * @param sizeFeatureSubset the number of selected features
     * @param numFeature the number of original features with class
     * @param nameFeatures the array of features' names
     * @param numClass the number of classes
     * @param classLabel the array of class labels' names
     */
    public static void convertCSVtoARFF(String nameCSV, String nameARFF, String pathProject, int sizeFeatureSubset,
                                        int numFeature, String[] nameFeatures, int numClass, String[] classLabel) {
        int selectLine = sizeFeatureSubset + 3;
        File tempFile = new File(pathProject + "tempFile.arff");

        String createLabelString = "@attribute " + nameFeatures[numFeature] + " {";
        for (int i = 0; i < numClass - 1; i++) {
            createLabelString += classLabel[i] + ",";
        }
        createLabelString += classLabel[numClass - 1] + "}";
        try {
            FileInputStream fis = new FileInputStream(nameCSV);
            //load CSV File
            CSVLoader loader = new CSVLoader();
            loader.setSource(fis);
            Instances data = loader.getDataSet();
            //load ARFF File
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(tempFile);
            saver.writeBatch();
            //Refine Header ARFF File
            BufferedReader br = new BufferedReader(new FileReader(tempFile));
            FileWriter fw = new FileWriter(nameARFF, false);
            PrintWriter pw = new PrintWriter(fw);
            while (selectLine-- > 1) {
                pw.println(br.readLine());
            }
            pw.println(createLabelString);
            br.readLine();
            while (br.ready()) {
                pw.println(br.readLine());
            }
            br.close();
            fw.close();
            fis.close();
            tempFile.delete();
        } catch (IOException ex) {
            Logger.getLogger(FileFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
