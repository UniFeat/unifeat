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
package unifeat.featureSelection.wrapper.ACOBasedMethods;

/**
 * The abstract class contains the main methods and fields that are used to
 * represent discrete search space as a graph in ACO algorithm. The graph is
 * implemented as an 2D array.
 *
 * @author Sina Tabakhi
 */
public abstract class BasicGraphRepresentation {

    protected double[][] pheromone;

    /**
     * Initializes the parameters
     *
     * @param rows the number of rows in the pheromone array
     * @param cols the number of columns in the pheromone array
     */
    public BasicGraphRepresentation(int rows, int cols) {
        pheromone = new double[rows][cols];
    }

    /**
     * This method sets a pheromone value in a specific entry of the array.
     *
     * @param value the input pheromone value
     * @param indexRow the index of the row in the pheromone array
     * @param indexCol the index of the column in the pheromone array
     */
    protected abstract void setPheromone(double value, int indexRow, int indexCol);

    /**
     * This method returns a pheromone value in a specific entry of the
     * pheromone array that is determined by indeces of the row and column
     *
     * @param indexRow the index of the row in the pheromone array
     * @param indexCol the index of the column in the pheromone array
     *
     * @return a pheromone value
     */
    public abstract double getPheromone(int indexRow, int indexCol);

    /**
     * This method fills all entries of the pheromone array with a specific
     * input value.
     *
     * @param value the input value
     */
    public void fillPheromoneArray(double value) {
        for (int row = 0; row < pheromone.length; row++) {
            for (int col = 0; col < pheromone[row].length; col++) {
                setPheromone(value, row, col);
            }
        }
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return a string representation of the graph.
     */
    @Override
    public String toString() {
        String str = " ";
        for (int row = 0; row < pheromone.length; row++) {
            for (int col = 0; col < pheromone[row].length; col++) {
                str += getPheromone(row, col) + ",";
            }
            str = str.substring(0, str.length() - 1) + "\n";
        }
        return str;
    }
}
