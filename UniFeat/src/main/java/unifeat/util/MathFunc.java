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

import java.util.Arrays;
import java.util.Random;

/**
 * This java class is used to implement various utility methods for performing
 * basic statistical operation such as the mean and variance.
 * <p>
 * Also, some numeric operation on the matrices are provides such as multiple of
 * two matrices, subtract of two matrices, and transpose of the matrix.
 * <p>
 * The methods in this class is contained brief description of the applications.
 *
 * @author Sina Tabakhi
 */
public final class MathFunc {

    /**
     * Returns the base 2 logarithm of a double value
     *
     * @param x a value
     *
     * @return the based 2 logarithm of x
     */
    public static double log2(double x) {
        if (x == 0) {
            return 0;
        } else {
            return Math.log(x) / Math.log(2);
        }
    }

    /**
     * Checks whether the input string is an integer value or not
     *
     * @param input input string
     *
     * @return true if the input string is an integer value
     */
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether the input string is an double value or not
     *
     * @param input input string
     *
     * @return true if the input string is an double value
     */
    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Rounds the argument value to a double value with given number of
     * floating-point
     *
     * @param num a floating-point value
     * @param numFloatPoint the number of floating-point of the num
     *
     * @return the string value of the argument rounded with given number of
     * floating-point
     */
    public static String roundDouble(double num, int numFloatPoint) {
        String precision = "%." + String.valueOf(numFloatPoint) + "f";
        return String.format(precision, num);
    }

    /**
     * Computes the sum of the elements of input array
     *
     * @param array the input array
     *
     * @return the sum of the elements of input array
     */
    public static double sum(double[] array) {
        return sum(array, 0, array.length);
    }

    /**
     * Computes the sum of the elements of input array
     *
     * @param array the input array
     *
     * @return the sum of the elements of input array
     */
    public static int sum(int[] array) {
        return sum(array, 0, array.length);
    }

    /**
     * Computes the sum of the elements of input array in the range of [start,
     * end)
     *
     * @param array the input array
     * @param start the first index of the input array
     * @param end the second index of the input array (exclusive)
     *
     * @return the sum of the elements of input array
     */
    public static double sum(double[] array, int start, int end) {
        double sumValue = 0;
        for (int i = start; i < end; i++) {
            sumValue += array[i];
        }
        return sumValue;
    }

    /**
     * Computes the sum of the elements of input array in the range of [start,
     * end)
     *
     * @param array the input array
     * @param start the first index of the input array
     * @param end the second index of the input array (exclusive)
     *
     * @return the sum of the elements of input array
     */
    public static int sum(int[] array, int start, int end) {
        int sumValue = 0;
        for (int i = start; i < end; i++) {
            sumValue += array[i];
        }
        return sumValue;
    }

    /**
     * Computes the mean value of the data corresponding to a given column
     * (column index)
     *
     * @param data the input data
     * @param index the index of the given column
     *
     * @return the mean value of the data
     */
    public static double computeMean(double[][] data, int index) {
        double sum = 0;
        for (double[] sample : data) {
            sum += sample[index];
        }
        return sum / data.length;
    }

    /**
     * Calculates the average values of all columns
     *
     * @param array the input array
     *
     * @return the average values of columns of the input array
     */
    public static double[][] computeAverageArray(double[][] array) {
        double[][] tempAverage = new double[1][array[0].length];
        for (int j = 0; j < tempAverage[0].length; j++) {
            for (double[] row : array) {
                tempAverage[0][j] += row[j];
            }
            tempAverage[0][j] = Double.parseDouble(MathFunc.roundDouble(tempAverage[0][j] / array.length, 3));
        }
        return tempAverage;
    }

    /**
     * Computes the variance value of the data corresponding to a given column
     * (column index)
     *
     * @param data the input data
     * @param mean the mean value of the data corresponding to a given column
     * @param index the index of the given column
     *
     * @return the variance value of the data
     */
    public static double computeVariance(double[][] data, double mean, int index) {
        return computeVariance(data, mean, index, data.length);
    }

    /**
     * Computes the variance value of the data corresponding to a given column
     * (column index) based on a specific denominator value
     *
     * @param data the input data
     * @param mean the mean value of the data corresponding to a given column
     * @param index the index of the given column
     * @param denominatorValue the denominator value of the final division
     *
     * @return the variance value of the data
     */
    public static double computeVariance(double[][] data, double mean, int index, double denominatorValue) {
        double sum = 0;
        for (double[] sample : data) {
            sum += Math.pow(sample[index] - mean, 2);
        }
        return sum / denominatorValue;
    }

    /**
     * Computes the standard deviation value of the data corresponding to a
     * given column (column index) based on a specific denominator value
     *
     * @param data the input data
     * @param mean the mean value of the data corresponding to a given column
     * @param index the index of the given column
     * @param denominatorValue the denominator value of the final division
     *
     * @return the standard deviation value of the data
     */
    public static double computeStandardDeviation(double[][] data, double mean, int index, double denominatorValue) {
        return Math.sqrt(computeVariance(data, mean, index, denominatorValue));
    }

    /**
     * Computes the similarity value between two features using cosine
     * similarity
     *
     * @param data the input data
     * @param index1 the index of the first feature
     * @param index2 the index of the second feature
     *
     * @return the similarity value
     */
    public static double computeSimilarity(double[][] data, int index1, int index2) {
        double sum1 = 0.0;
        double sum2 = 0.0;
        double sum3 = 0.0;

        for (double[] sample : data) {
            sum1 += (sample[index1] * sample[index2]);
            sum2 += (sample[index1] * sample[index1]);
            sum3 += (sample[index2] * sample[index2]);
        }

        if (sum2 == 0 && sum3 == 0) {
            return 1;
        } else if (sum2 == 0 || sum3 == 0) {
            return 0;
        } else {
            return sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3));
        }
    }

    /**
     * Computes the correlation value between two features using Pearson
     * correlation coefficient
     *
     * @param data the input data
     * @param index1 the index of the first feature
     * @param index2 the index of the second feature
     *
     * @return the Pearson correlation coefficient value
     */
    public static double computePearsonCorCoef(double[][] data, int index1, int index2) {
        double numerator = 0.0;
        double denominator1 = 0.0;
        double denominator2 = 0.0;
        double meanColumn1 = computeMean(data, index1);
        double meanColumn2 = computeMean(data, index2);

        for (double[] sample : data) {
            numerator += (sample[index1] - meanColumn1) * (sample[index2] - meanColumn2);
            denominator1 += Math.pow(sample[index1] - meanColumn1, 2);
            denominator2 += Math.pow(sample[index2] - meanColumn2, 2);
        }

        if (denominator1 == 0 && denominator2 == 0) {
            return 1;
        } else if (denominator1 == 0 || denominator2 == 0) {
            return 0;
        }

        double result = numerator / (Math.sqrt(denominator1) * Math.sqrt(denominator2));
        if (result > 1.0) {
            return 1.0;
        } else if (result < -1.0) {
            return -1.0;
        } else {
            return result;
        }
    }

    /**
     * Calculates the error rate values based on the array of accuracies
     *
     * @param accuracy the input array from accuracies
     *
     * @return the error rate values
     */
    public static double[][] computeErrorRate(double[][] accuracy) {
        double[][] errorArray = new double[accuracy.length][accuracy[0].length];
        for (int i = 0; i < errorArray.length; i++) {
            for (int j = 0; j < errorArray[i].length; j++) {
                errorArray[i][j] = Double.parseDouble(MathFunc.roundDouble(100 - accuracy[i][j], 3));
            }
        }
        return errorArray;
    }

    /**
     * Computes the multiple of two matrices
     *
     * @param matrix1 the first input matrix
     * @param matrix2 the second input matrix
     *
     * @return a matrix based on the result of the multiple of two matrices
     */
    public static double[][] multMatrix(double[][] matrix1, double[][] matrix2) {
        int row1 = matrix1.length;
        int col1 = matrix1[0].length;
        int col2 = matrix2[0].length;
        double[][] multipleMatrix = new double[row1][col2];

        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col2; j++) {
                multipleMatrix[i][j] = 0;
                for (int k = 0; k < col1; k++) {
                    multipleMatrix[i][j] += (matrix1[i][k] * matrix2[k][j]);
                }
            }
        }
        return multipleMatrix;
    }

    /**
     * Computes the subtract of two matrices
     *
     * @param matrix1 the first input matrix
     * @param matrix2 the second input matrix
     *
     * @return a matrix based on the result of the subtract of two matrices
     */
    public static double[][] subMatrix(double[][] matrix1, double[][] matrix2) {
        double[][] subtractMatrix = new double[matrix1.length][matrix1[0].length];

        for (int i = 0; i < subtractMatrix.length; i++) {
            for (int j = 0; j < subtractMatrix[i].length; j++) {
                subtractMatrix[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }

        return subtractMatrix;
    }

    /**
     * Computes the transpose of the input matrix
     *
     * @param matrix the input matrix
     *
     * @return a matrix based on the result of the transpose of the matrix
     */
    public static double[][] transMatrix(double[][] matrix) {
        double transposeMatrix[][] = new double[matrix[0].length][matrix.length];

        for (int i = 0; i < transposeMatrix.length; i++) {
            for (int j = 0; j < transposeMatrix[i].length; j++) {
                transposeMatrix[i][j] = matrix[j][i];
            }
        }

        return transposeMatrix;
    }

    /**
     * Finds the minimum value of the input array
     *
     * @param array the input array
     *
     * @return a minimum value of the array
     */
    public static double findMinimumValue(double[] array) {
        double min = array[0];
        for (double item : array) {
            if (item < min) {
                min = item;
            }
        }
        return min;
    }

    /**
     * Finds the minimum value of the input array and returns its index
     *
     * @param array the input array
     *
     * @return the index of the minimum value in the array
     */
    public static int findMinimumIndex(double[] array) {
        int indexMin = 0;
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
                indexMin = i;
            }
        }
        return indexMin;
    }

    /**
     * Changes the values of the two elements in the input array identified by
     * firstIndex and secondIndex
     *
     * @param array the input array
     * @param firstIndex the first index of the input array
     * @param secondIndex the second index of the input array
     */
    public static void swap(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    /**
     * Changes the values of the two elements in the input array identified by
     * firstIndex and secondIndex
     *
     * @param <T> type of the array
     * @param array the input array
     * @param firstIndex the first index of the input array
     * @param secondIndex the second index of the input array
     */
    public static <T> void swap(T[] array, int firstIndex, int secondIndex) {
        T temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    /**
     * Changes the values of the two rows in the input array identified by
     * firstIndex and secondIndex
     *
     * @param array the input array
     * @param firstIndex the first index of the input array
     * @param secondIndex the second index of the input array
     */
    public static void swap(double[][] array, int firstIndex, int secondIndex) {
        double[] temp = Arrays.copyOf(array[firstIndex], array[firstIndex].length);
        array[firstIndex] = Arrays.copyOf(array[secondIndex], array[secondIndex].length);
        array[secondIndex] = Arrays.copyOf(temp, temp.length);
    }

    /**
     * Normalizes values of the vector
     *
     * @param vector the input vector
     *
     * @return the normalized values of the input vector
     */
    public static double[] normalizeVector(double[] vector) {
        double magnitude = 0;
        for (int i = 0; i < vector.length; i++) {
            magnitude += (vector[i] * vector[i]);
        }

        if (magnitude == 0) {
            return vector;
        } else {
            magnitude = Math.sqrt(magnitude);
            for (int i = 0; i < vector.length; i++) {
                vector[i] /= magnitude;
            }
            return vector;
        }
    }

    /**
     * Shuffles a given array using Fisher–Yates shuffle Algorithm
     *
     * @param array the input array
     */
    public static void randomize(double[][] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, i, rand.nextInt(i));
        }
    }

    /**
     * Shuffles a given array using Fisher–Yates shuffle Algorithm
     *
     * @param <T> type of the input array
     * @param array the input array
     */
    public static <T> void randomize(T[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, i, rand.nextInt(i));
        }
    }

    /**
     * Generates a random number that is drawn from a uniform distribution in a
     * specific interval.
     *
     * @param start the beginning number of the interval
     * @param end the end number of the interval
     *
     * @return a generated random number in the specific interval
     */
    public static double generateRandNum(double start, double end) {
        return generateRandNum(start, end, new Random());
    }

    /**
     * Generates a random double number that is drawn from a uniform
     * distribution in a specific interval.
     *
     * @param start the beginning number of the interval
     * @param end the end number of the interval
     * @param rand the Random class for generating number
     *
     * @return a generated random double number in the specific interval
     */
    public static double generateRandNum(double start, double end, Random rand) {
        return (rand.nextDouble() * (end - start)) + start;
    }

    /**
     * Generates a random integer number that is drawn from a uniform
     * distribution in a specific interval.
     *
     * @param start the beginning number of the interval
     * @param end the end number of the interval
     * @param rand the Random class for generating number
     *
     * @return a generated random integer number in the specific interval
     */
    public static int generateRandNum(int start, int end, Random rand) {
        return rand.nextInt((end - start) + 1) + start;
    }
}
