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
package unifeat.featureSelection.wrapper.ACOBasedMethods.OptimalACO;

import unifeat.featureSelection.wrapper.ACOBasedMethods.BasicGraphRepresentation;

/**
 * This java class is used to represent a graph in optimal ant colony
 * optimization (optimal ACO) method.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.ACOBasedMethods.BasicGraphRepresentation
 */
public class GraphRepresentation extends BasicGraphRepresentation {

    /**
     * Initializes the parameters
     *
     * @param rows the number of rows in the pheromone array
     * @param cols the number of columns in the pheromone array
     */
    public GraphRepresentation(int rows, int cols) {
        super(rows, cols);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setPheromone(double value, int indexRow, int indexCol) {
        pheromone[indexRow][indexCol] = value;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public double getPheromone(int indexRow, int indexCol) {
        return pheromone[indexRow][indexCol];
    }
}
