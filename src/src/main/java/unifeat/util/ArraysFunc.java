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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This java class is used to implement various utility methods for manipulating
 * arrays and matrices. The methods in this class are contained brief description
 * of the applications.
 *
 * @author Sina Tabakhi
 */
public final class ArraysFunc {

    /**
     * Creates a copy of the two dimensional array
     *
     * @param initArray initial array
     *
     * @return a copy array of the input array
     */
    public static double[][] copyDoubleArray2D(double[][] initArray) {
        return copyDoubleArray2D(initArray, 0, initArray.length);
    }

    /**
     * Creates a copy of the two dimensional array by using the indices of rows
     *
     * @param initArray initial array
     * @param indexStart the start index of the data
     * @param indexEnd the end index of the data
     *
     * @return a copy array of the input array
     */
    public static double[][] copyDoubleArray2D(double[][] initArray, int indexStart, int indexEnd) {
        double[][] copyArray = new double[indexEnd - indexStart][initArray[0].length];
        for (int i = indexStart, j = 0; i < indexEnd; i++, j++) {
            copyArray[j] = Arrays.copyOf(initArray[i], initArray[i].length);
        }
        return copyArray;
    }

    /**
     * Sorts the two dimensional array by using the index of column as ascending
     * order
     *
     * @param array the input array
     * @param col the index of the column
     */
    public static void sortArray2D(double[][] array, int col) {
        int i, j;

        for (i = 1; i < array.length; i++) {
            double[] next = Arrays.copyOf(array[i], array[i].length);
            for (j = i - 1; j >= 0 && (next[col] < array[j][col]); j--) {
                array[j + 1] = Arrays.copyOf(array[j], array[j].length);
            }
            array[j + 1] = Arrays.copyOf(next, next.length);
        }
    }

    /**
     * Sorts the two dimensional array by using the index of column as ascending
     * order
     *
     * @param array the input array
     * @param col the index of the column
     * @param indexStart the start index of the data
     * @param indexEnd the end index of the data
     */
    public static void sortArray2D(double[][] array, int col, int indexStart, int indexEnd) {
        int i, j;

        for (i = indexStart + 1; i < indexEnd; i++) {
            double[] next = Arrays.copyOf(array[i], array[i].length);
            for (j = i - 1; j >= indexStart && (next[col] < array[j][col]); j--) {
                array[j + 1] = Arrays.copyOf(array[j], array[j].length);
            }
            array[j + 1] = Arrays.copyOf(next, next.length);
        }
    }

    /**
     * Sorts the one dimensional array (double values) as descending or
     * ascending order
     *
     * @param array the input array
     * @param descending indicates the type of sorting
     */
    public static void sortArray1D(double[] array, boolean descending) {
        Arrays.sort(array);
        if (descending) {
            double[] tempArray = Arrays.copyOfRange(array, 0, array.length);
            for (int i = 0, j = array.length - 1; i < array.length; i++, j--) {
                array[i] = tempArray[j];
            }
        }
    }

    /**
     * Sorts the one dimensional array (integer values) as descending or
     * ascending order
     *
     * @param array the input array
     * @param descending indicates the type of sorting
     */
    public static void sortArray1D(int[] array, boolean descending) {
        Arrays.sort(array);
        if (descending) {
            int[] tempArray = Arrays.copyOfRange(array, 0, array.length);
            for (int i = 0, j = array.length - 1; i < array.length; i++, j--) {
                array[i] = tempArray[j];
            }
        }
    }

    /**
     * Sorts the one dimensional array (integer values) as descending or
     * ascending order
     *
     * @param array the input array
     * @param descending indicates the type of sorting
     * @param indexStart the start index of the data
     * @param indexEnd the end index of the data
     */
    public static void sortArray1D(int[] array, boolean descending, int indexStart, int indexEnd) {
        Arrays.sort(array, indexStart, indexEnd);
        if (descending) {
            int[] tempArray = Arrays.copyOfRange(array, 0, array.length);
            for (int i = indexStart, j = indexEnd - 1; i < indexEnd; i++, j--) {
                array[i] = tempArray[j];
            }
        }
    }

    /**
     * Sorts the one dimensional array (double values) by values and returns a
     * list of indices
     *
     * @param array the input array
     * @param descending indicates the type of sorting
     *
     * @return the sorted array
     */
    public static int[] sortWithIndex(double[] array, boolean descending) {
        int[] index = new int[array.length];
        int i, j;
        for (i = 0; i < index.length; i++) {
            index[i] = i;
        }

        if (descending) {
            for (i = 1; i < array.length; i++) {
                double next = array[i];
                int nextIndex = index[i];
                for (j = i - 1; j >= 0 && (next > array[j]); j--) {
                    array[j + 1] = array[j];
                    index[j + 1] = index[j];
                }
                array[j + 1] = next;
                index[j + 1] = nextIndex;
            }
        } else {
            for (i = 1; i < array.length; i++) {
                double next = array[i];
                int nextIndex = index[i];
                for (j = i - 1; j >= 0 && (next < array[j]); j--) {
                    array[j + 1] = array[j];
                    index[j + 1] = index[j];
                }
                array[j + 1] = next;
                index[j + 1] = nextIndex;
            }
        }
        return index;
    }

    /**
     * Sorts the one dimensional array (integer values) by values and returns a
     * list of indices
     *
     * @param array the input array
     * @param descending indicates the type of sorting
     *
     * @return the sorted array
     */
    public static int[] sortWithIndex(int[] array, boolean descending) {
        int[] index = new int[array.length];
        int i, j;
        for (i = 0; i < index.length; i++) {
            index[i] = i;
        }

        if (descending) {
            for (i = 1; i < array.length; i++) {
                int next = array[i];
                int nextIndex = index[i];
                for (j = i - 1; j >= 0 && (next > array[j]); j--) {
                    array[j + 1] = array[j];
                    index[j + 1] = index[j];
                }
                array[j + 1] = next;
                index[j + 1] = nextIndex;
            }
        } else {
            for (i = 1; i < array.length; i++) {
                int next = array[i];
                int nextIndex = index[i];
                for (j = i - 1; j >= 0 && (next < array[j]); j--) {
                    array[j + 1] = array[j];
                    index[j + 1] = index[j];
                }
                array[j + 1] = next;
                index[j + 1] = nextIndex;
            }
        }
        return index;
    }

    /**
     * Converts the string input to double values
     *
     * @param array the string input array
     *
     * @return the double array obtained from input array
     */
    public static double[][] convertStringToDouble(String[][] array) {
        double[][] results = new double[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                results[i][j] = Double.parseDouble(array[i][j]);
            }
        }
        return results;
    }

    /**
     * Converts the ArrayList type to an array of integer values
     *
     * @param list the input ArrayList
     *
     * @return an array of integer values
     */
    public static int[] convertArrayListToInt(ArrayList<Integer> list) {
        return list.stream().mapToInt(i -> i).toArray();
    }
}
