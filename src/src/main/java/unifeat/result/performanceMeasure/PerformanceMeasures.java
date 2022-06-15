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
package unifeat.result.performanceMeasure;

import unifeat.util.MathFunc;

/**
 * This java class is used to save all performance measure values obtained from
 * different runs of given feature selection method.
 *
 * @author Sina Tabakhi
 * @see unifeat.result.performanceMeasure.Criteria
 */
public class PerformanceMeasures {

    private Criteria[][] measures;
    private Criteria[] averageValues;

    /**
     * Initializes the parameters
     *
     * @param numRuns numbers of runs of the feature selection method
     * @param numSelectedSubsets numbers of selected feature subsets
     */
    public PerformanceMeasures(int numRuns, int numSelectedSubsets) {
        measures = new Criteria[numRuns][numSelectedSubsets];
        for (int i = 0; i < measures.length; i++) {
            for (int j = 0; j < measures[i].length; j++) {
                measures[i][j] = new Criteria();
            }
        }

        averageValues = new Criteria[numSelectedSubsets];
        for (int i = 0; i < averageValues.length; i++) {
            averageValues[i] = new Criteria();
        }
    }

    /**
     * Sets the obtained criteria values from feature selection method
     *
     * @param i the current run of the feature selection method
     * @param j the current subset of the selected features
     * @param criteria values obtained from the feature selection method
     */
    public void setCriteria(int i, int j, Criteria criteria) {
        measures[i][j] = criteria;
    }

    /**
     * Computes the average values of all criteria over number of runs
     */
    public void computeAverageValues() {
        for (int j = 0; j < measures[0].length; j++) {
            for (int i = 0; i < measures.length; i++) {
                averageValues[j].setAccuracy(averageValues[j].getAccuracy() + measures[i][j].getAccuracy());
                averageValues[j].setErrorRate(averageValues[j].getErrorRate() + measures[i][j].getErrorRate());
                averageValues[j].setTime(averageValues[j].getTime() + measures[i][j].getTime());
            }
            averageValues[j].setAccuracy(Double.parseDouble(MathFunc.roundDouble(averageValues[j].getAccuracy() / measures.length, 3)));
            averageValues[j].setErrorRate(Double.parseDouble(MathFunc.roundDouble(averageValues[j].getErrorRate() / measures.length, 3)));
            averageValues[j].setTime(Double.parseDouble(MathFunc.roundDouble(averageValues[j].getTime() / measures.length, 3)));
        }
    }

    /**
     * Returns the accuracy values of the feature selection method
     *
     * @return an array of the accuracy values
     */
    public double[][] getAccuracyValues() {
        double[][] array = new double[measures.length][measures[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = measures[i][j].getAccuracy();
            }
        }
        return array;
    }

    /**
     * Returns the error rate values of the feature selection method
     *
     * @return an array of the error rate values
     */
    public double[][] getErrorRateValues() {
        double[][] array = new double[measures.length][measures[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = measures[i][j].getErrorRate();
            }
        }
        return array;
    }

    /**
     * Returns the execution time values of the feature selection method
     *
     * @return an array of the execution time values
     */
    public double[][] getTimeValues() {
        double[][] array = new double[measures.length][measures[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = measures[i][j].getTime();
            }
        }
        return array;
    }

    /**
     * Returns the average values of the accuracy of the feature selection
     * method over number of runs
     *
     * @return an array of the average values of the accuracy
     */
    public double[][] getAverageAccuracyValues() {
        double[][] array = new double[1][averageValues.length];
        for (int j = 0; j < array[0].length; j++) {
            array[0][j] = averageValues[j].getAccuracy();
        }
        return array;
    }

    /**
     * Returns the average values of the error rate of the feature selection
     * method over number of runs
     *
     * @return an array of the average values of the error rate
     */
    public double[][] getAverageErrorRateValues() {
        double[][] array = new double[1][averageValues.length];
        for (int j = 0; j < array[0].length; j++) {
            array[0][j] = averageValues[j].getErrorRate();
        }
        return array;
    }

    /**
     * Returns the average values of the execution time of the feature selection
     * method over number of runs
     *
     * @return an array of the average values of the execution time
     */
    public double[][] getAverageTimeValues() {
        double[][] array = new double[1][averageValues.length];
        for (int j = 0; j < array[0].length; j++) {
            array[0][j] = averageValues[j].getTime();
        }
        return array;
    }
}
