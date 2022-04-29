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
package unifeat.featureSelection.filter.unsupervised;

import unifeat.util.ArraysFunc;
import unifeat.util.MathFunc;
import java.util.Arrays;
import unifeat.featureSelection.filter.WeightedFilterApproach;

/**
 * This java class is used to implement the Laplacian score method. Also, it is
 * the unsupervised version of the Laplacian score.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.WeightedFilterApproach
 * @see unifeat.featureSelection.FeatureWeighting
 * @see unifeat.featureSelection.FeatureSelection
 */
public class LaplacianScore extends WeightedFilterApproach {

    private final double CONSTANT_VALUE;
    private final int K_NEAREST_NEIGHBOR_VALUE;
    private final double ERROR_DENOMINATOR = 0.0001;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains
     * (<code>sizeSelectedFeatureSubset</code>, <code>constant</code>,
     * <code>kNNValue</code>) in which
     * <code><b><i>sizeSelectedFeatureSubset</i></b></code> is the number of
     * selected features, <code><b><i>constant</i></b></code> is the constant
     * value used in the similarity measure, and
     * <code><b><i>kNNValue</i></b></code> is the k-nearest neighbor value
     */
    public LaplacianScore(Object... arguments) {
        super((int) arguments[0]);
        CONSTANT_VALUE = (double) arguments[1];
        K_NEAREST_NEIGHBOR_VALUE = (int) arguments[2];
    }

    /**
     * Initializes the parameters
     *
     * @param sizeSelectedFeatureSubset the number of selected features
     * @param constant the constant value used in the similarity measure
     * @param kNNValue the k-nearest neighbor value
     */
    public LaplacianScore(int sizeSelectedFeatureSubset, double constant, int kNNValue) {
        super(sizeSelectedFeatureSubset);
        CONSTANT_VALUE = constant;
        K_NEAREST_NEIGHBOR_VALUE = kNNValue;
    }

    /**
     * Constructs nearest neighbor graph (G) of the data space
     *
     * @return the nearest neighbor graph
     */
    private boolean[][] constructNeighborGraph() {
        boolean[][] tempMatrix = new boolean[trainSet.length][trainSet.length];

        for (int i = 0; i < trainSet.length; i++) {
            double[] distance = new double[trainSet.length];
            int[] indexDataSort;

            //finds the k-nearest neighbor data
            for (int j = 0; j < trainSet.length; j++) {
                if (i != j) {
                    //computes the euclidean distance between two data point
                    for (int k = 0; k < numFeatures; k++) {
                        distance[j] += Math.pow(trainSet[i][k] - trainSet[j][k], 2);
                    }
                    distance[j] = Math.sqrt(distance[j]);
                } else {
                    distance[j] = Double.MAX_VALUE;
                }
            }

            indexDataSort = ArraysFunc.sortWithIndex(distance, false);

            for (int j = 0; j < K_NEAREST_NEIGHBOR_VALUE; j++) {
                tempMatrix[i][indexDataSort[j]] = tempMatrix[indexDataSort[j]][i] = true;
            }
        }

        return tempMatrix;
    }

    /**
     * Constructs weight matrix(S) which models the local structure of the data
     * space
     *
     * @param neighborGraph the nearest neighbor graph (G)
     *
     * @return the weight matrix
     */
    private double[][] constructWeightMatrix(boolean[][] neighborGraph) {
        double[][] tempMatrix = new double[trainSet.length][trainSet.length];

        for (int i = 0; i < trainSet.length; i++) {
            for (int j = 0; j <= i; j++) {
                if (neighborGraph[i][j] == true) {
                    // computes euclidean distance value between data i-th and j-th
                    double euclideanDistance = 0.0;
                    for (int k = 0; k < numFeatures; k++) {
                        euclideanDistance += Math.pow(trainSet[i][k] - trainSet[j][k], 2);
                    }
                    tempMatrix[i][j] = tempMatrix[j][i] = Math.pow(Math.E, -(euclideanDistance / CONSTANT_VALUE));
                } else {
                    tempMatrix[i][j] = 0;
                }
            }
        }
        return tempMatrix;
    }

    /**
     * Constructs the diagonal matrix (D) based on the weight matrix
     *
     * @param simMatrix the weight matrix(S)
     *
     * @return the diagonal matrix
     */
    private double[][] constructDiagonalMatrix(double[][] simMatrix) {
        double[][] tempMatrix = new double[trainSet.length][trainSet.length];

        for (int i = 0; i < tempMatrix.length; i++) {
            for (int j = 0; j < tempMatrix.length; j++) {
                tempMatrix[i][i] += simMatrix[i][j];
            }
        }
        return tempMatrix;
    }

    /**
     * Estimates each feature values
     *
     * @param diag the diagonal matrix(D)
     * @param index the index of the feature
     *
     * @return the estimation of the features
     */
    private double[][] estimateFeatureMatrix(double[][] diag, int index) {
        double numeratorValue = 0;
        double denominatorValue = 0;
        double fractionResult;
        double[][] estFeature = new double[trainSet.length][1];

        //f(index) * diagonal matrix(D) * identity matrix(1)
        for (int i = 0; i < trainSet.length; i++) {
            numeratorValue += trainSet[i][index] * diag[i][i];
        }

        //transpose identity matrix(1) * diagonal matrix(D) * identity matrix(1)
        for (int i = 0; i < diag.length; i++) {
            denominatorValue += diag[i][i];
        }
        if (denominatorValue == 0) {
            fractionResult = numeratorValue / ERROR_DENOMINATOR;
        } else {
            fractionResult = numeratorValue / denominatorValue;
        }

        for (int i = 0; i < trainSet.length; i++) {
            estFeature[i][0] = trainSet[i][index] - fractionResult;
        }

        return estFeature;
    }

    /**
     * Checks the values of data corresponding to a given feature
     *
     * @param index the index of the feature
     *
     * @return true if the all values is equal to zero
     */
    private boolean isZeroFeat(int index) {
        for (double[] sample : trainSet) {
            if (sample[index] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Chaining multiple three matrix
     *
     * @param matrix1 the first matrix
     * @param matrix2 the second matrix
     * @param matrix3 the third matrix
     *
     * @return the result of the chaining multiple
     */
    private double multThreeMatrix(double[][] matrix1, double[][] matrix2, double[][] matrix3) {
        double[][] result = MathFunc.multMatrix(MathFunc.multMatrix(matrix1, matrix2), matrix3);
        return result[0][0];
    }

    /**
     * Starts the feature selection process by Laplacian score(LS) method
     */
    @Override
    public void evaluateFeatures() {
        boolean[][] nearestNeighborGraph; // nearest neighbor graph
        double[][] weightMatrix; // weight matrix of the data space
        double[][] identityMatrix = new double[trainSet.length][1]; // identity matrix
        double[][] diagonalMatrix; // diagonal matrix
        double[][] graphLaplacian; // graph Laplacian
        featureValues = new double[numFeatures];
        int[] indecesLS;

        //constructs an identity matrix
        for (double[] row : identityMatrix) {
            row[0] = 1;
        }

        //constructs nearest neighbor graph
        nearestNeighborGraph = constructNeighborGraph();

        //constructs weight matrix
        weightMatrix = constructWeightMatrix(nearestNeighborGraph);

        //construct diagonal matrix
        diagonalMatrix = constructDiagonalMatrix(weightMatrix);

        //computes the graph Laplacian
        graphLaplacian = MathFunc.subMatrix(diagonalMatrix, weightMatrix);

        //computs the Laplacian score values for the features
        for (int i = 0; i < numFeatures; i++) {
            if (!isZeroFeat(i)) {
                double[][] estimateFeature = estimateFeatureMatrix(diagonalMatrix, i);
                double numeratorValue = multThreeMatrix(MathFunc.transMatrix(estimateFeature), graphLaplacian, estimateFeature);
                double denominatorValue = multThreeMatrix(MathFunc.transMatrix(estimateFeature), diagonalMatrix, estimateFeature);
                if (denominatorValue == 0) {
                    featureValues[i] = numeratorValue / ERROR_DENOMINATOR;
                } else {
                    featureValues[i] = numeratorValue / denominatorValue;
                }
            } else {
                featureValues[i] = 1 / ERROR_DENOMINATOR;
            }
        }

        indecesLS = ArraysFunc.sortWithIndex(Arrays.copyOf(featureValues, featureValues.length), false);
//        for (int i = 0; i < numFeatures; i++) {
//            System.out.println(i + ") =  " + featureValues[i]);
//        }

        selectedFeatureSubset = Arrays.copyOfRange(indecesLS, 0, numSelectedFeature);
        ArraysFunc.sortArray1D(selectedFeatureSubset, false);
//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validate() {
        if (K_NEAREST_NEIGHBOR_VALUE >= trainSet.length) {
            return "The parameter value of Laplacian score (k-nearest neighbor) is incorrect.";
        }
        return "";
    }
}
