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
import java.util.Random;
import unifeat.featureSelection.filter.FilterApproach;

/**
 * This java class is used to implement the microarray gene selection based on
 * ant colony optimization (MGSACO) method.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.FilterApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public class MGSACO extends FilterApproach {

    private final double INIT_PHEROMONE_VALUE;
    private final int MAX_ITERATION;
    private int NUM_ANTS;
    private final double DECAY_RATE;
    private final double BETA;
    private final double PROB_CHOOSE_EQUATION;
    private double performBestSubset;
    private int[][] antSubsetSelected;
    private double[] antPerformValues;
    private double[] relevanceFeature;
    private double[] simValues;
    private double[] pheromoneValues;
    private int[] edgeCounter;
    private boolean[][] tabuList;
    private int[] currentState;
//    private final int SEED_VALUE = 0;
    private final double ERROR_SIMILARITY = 0.0001;
    private final double ERROR_RELEVANCE = 0.0001;
//    private Random RAND_NUMBER = new Random(SEED_VALUE);

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameter contains
     * (<code>sizeSelectedFeatureSubset</code>, <code>initPheromone</code>,
     * <code>numIterations</code>, <code>numAnt</code>,
     * <code>evaporationRate</code>, <code>betaParameter</code>,
     * <code>q0_Parameter</code>) in which
     * <code><b><i>sizeSelectedFeatureSubset</i></b></code> is the number of
     * selected features, <code><b><i>initPheromone</i></b></code> is the
     * initial value of the pheromone, <code><b><i>numIterations</i></b></code>
     * is the maximum number of iteration, <code><b><i>numAnt</i></b></code> is
     * the number of ants, <code><b><i>evaporationRate</i></b></code> is the
     * evaporation rate of the pheromone,
     * <code><b><i>betaParameter</i></b></code> is the beta parameter in the
     * state transition rule, and <code><b><i>q0_Parameter</i></b></code> is the
     * q0 parameter in the state transition rule
     */
    public MGSACO(Object... arguments) {
        super((int) arguments[0]);
        INIT_PHEROMONE_VALUE = (double) arguments[1];
        MAX_ITERATION = (int) arguments[2];
        NUM_ANTS = (int) arguments[3];
        DECAY_RATE = (double) arguments[4];
        BETA = (double) arguments[5];
        PROB_CHOOSE_EQUATION = (double) arguments[6];
    }

    /**
     * Initializes the parameters
     *
     * @param sizeSelectedFeatureSubset the number of selected features
     * @param initPheromone the initial value of the pheromone
     * @param numIterations the maximum number of iteration
     * @param numAnt the number of ants
     * @param evaporationRate the evaporation rate of the pheromone
     * @param betaParameter the beta parameter in the state transition rule
     * @param q0_Parameter the q0 parameter in the state transition rule
     */
    public MGSACO(int sizeSelectedFeatureSubset, double initPheromone, int numIterations, int numAnt, double evaporationRate, double betaParameter, double q0_Parameter) {
        super(sizeSelectedFeatureSubset);
        INIT_PHEROMONE_VALUE = initPheromone;
        MAX_ITERATION = numIterations;
        NUM_ANTS = numAnt;
        DECAY_RATE = evaporationRate;
        BETA = betaParameter;
        PROB_CHOOSE_EQUATION = q0_Parameter;
    }

    /**
     * Finds index in new Data Structure(Symmetric Matrix)
     *
     * @param index1 index of the row
     * @param index2 index of the column
     *
     * @return the index in new Data Structure
     */
    private static int findIndex(int index1, int index2) {
        if (index1 < index2) {
            return ((index2 * (index2 - 1)) / 2) + index1;
        } else {
            return ((index1 * (index1 - 1)) / 2) + index2;
        }
    }

    /**
     * Computes the relevance of each feature using the normalized term variance
     * (the relevance values normalized by softmax scaling function)
     */
    private void computeRelevance() {
        double mean = 0;
        double variance = 0;
        double parameterControl;

        //computes the term variance values of the data
        TermVariance tv = new TermVariance(numFeatures);
        tv.loadDataSet(trainSet, numFeatures, numClass);
        tv.evaluateFeatures();
        relevanceFeature = tv.getFeatureValues();

//        for (int i = 0; i < numFeatures; i++) {
//            System.out.println("relevance f(" + i + ") = " + relevanceFeature[i]);
//        }
        //normalizes the relevance values by softmax scaling function
        for (int i = 0; i < numFeatures; i++) {
            mean += relevanceFeature[i];
        }
        mean /= numFeatures;

        for (int i = 0; i < numFeatures; i++) {
            variance += Math.pow(relevanceFeature[i] - mean, 2);
        }
        variance = Math.sqrt(variance / (numFeatures - 1));

        if (variance == 0) {
            variance = ERROR_RELEVANCE;
        }

        parameterControl = mean / variance;
        if (parameterControl == 0) {
            parameterControl = ERROR_RELEVANCE;
        }

        for (int i = 0; i < numFeatures; i++) {
            relevanceFeature[i] = (relevanceFeature[i] - mean) / (variance * parameterControl);
            relevanceFeature[i] = 1.0 / (1.0 + Math.pow(Math.E, -1 * relevanceFeature[i]));
        }

//        for (int i = 0; i < numFeatures; i++) {
//            System.out.println("norm relevance f(" + i + ") = " + relevanceFeature[i]);
//        }
    }

    /**
     * Places the ants randomly on the graph nodes as their starting nodes
     */
    private void setStartNode() {
        boolean[] checkArray = new boolean[numFeatures];

        for (int i = 0; i < NUM_ANTS; i++) {
            //finds starting node randomly
            while (true) {
                int rand = new Random().nextInt(numFeatures);
//                int rand = RAND_NUMBER.nextInt(numFeatures);
                if (!checkArray[rand]) {
                    currentState[i] = rand;
                    checkArray[rand] = true;
                    break;
                }
            }
            //sets starting node into the tabu list
            tabuList[i][currentState[i]] = true;
            antSubsetSelected[i][0] = currentState[i];
        }
    }

    /**
     * Greedy state transition rule
     *
     * @param indexAnt index of the ant
     *
     * @return the index of the selected feature
     */
    private int greedyRule(int indexAnt) {
        int index = -1;
        double max = -Double.MAX_VALUE;

        for (int j = 0; j < numFeatures; j++) {
            if (!tabuList[indexAnt][j]) {
                int newIndex = findIndex(currentState[indexAnt], j);
                double result = pheromoneValues[newIndex] / Math.pow(simValues[newIndex] + ERROR_SIMILARITY, BETA);
                if (result > max) {
                    max = result;
                    index = j;
                }
            }
        }

        return index;
    }

    /**
     * Probability state transition rule
     *
     * @param indexAnt index of the ant
     *
     * @return the index of the selected feature
     */
    private int probRule(int indexAnt) {
        int index = -1;
        double rand = new Random().nextDouble();
//        double rand = RAND_NUMBER.nextDouble();
        double[] prob = new double[numFeatures];
        double sumOfProb = 0;
        for (int j = 0; j < numFeatures; j++) {
            if (!tabuList[indexAnt][j]) {
                int newIndex = findIndex(currentState[indexAnt], j);
                prob[j] = pheromoneValues[newIndex] / Math.pow(simValues[newIndex] + ERROR_SIMILARITY, BETA);
                sumOfProb += prob[j];
            }
        }
        for (int j = 0; j < numFeatures; j++) {
            if (!tabuList[indexAnt][j]) {
                prob[j] /= sumOfProb;
                if (rand <= prob[j]) {
                    index = j;
                    break;
                }
            }
        }

        //if the next node(feature) is not selected by previous process
        if (index == -1) {
            while (true) {
//                int rand1 = RAND_NUMBER.nextInt(numFeatures);
                int rand1 = new Random().nextInt(numFeatures);
                if (!tabuList[indexAnt][rand1]) {
                    index = rand1;
                    break;
                }
            }
        }

        return index;
    }

    /**
     * Chooses the next feature among unvisited features according to the state
     * transition rules
     *
     * @param indexAnt the index of the ant
     *
     * @return the index of the selected feature
     */
    private int stateTransitionRules(int indexAnt) {
        double q = new Random().nextDouble();
//        double q = RAND_NUMBER.nextDouble();
        if (q <= PROB_CHOOSE_EQUATION) {
            return greedyRule(indexAnt);
        } else {
            return probRule(indexAnt);
        }
    }

    /**
     * Evaluates the subset of selected features by using fitness function and
     * return the index of the ant with the max performance in the current
     * iteration
     *
     * @return the index of the ant with the maximum performance
     */
    private int evaluateSubsets() {
        int indexMaxPerformance = -1;
        double maxPerformance = 0;

        for (int i = 0; i < NUM_ANTS; i++) {
            antPerformValues[i] = 0;
            for (int j = 0; j < numSelectedFeature; j++) {
                antPerformValues[i] += relevanceFeature[antSubsetSelected[i][j]];
            }
            antPerformValues[i] /= numSelectedFeature;

            if (antPerformValues[i] > maxPerformance) {
                indexMaxPerformance = i;
                maxPerformance = antPerformValues[i];
            }
        }
        return indexMaxPerformance;
    }

    /**
     * Finds the best subset of feature up to know
     *
     * @param indexBestSubset the index of the found best subset in the current
     * iteration
     */
    private void findBestSubset(int indexBestSubset) {
        if (performBestSubset < antPerformValues[indexBestSubset]) {
            performBestSubset = antPerformValues[indexBestSubset];
            selectedFeatureSubset = Arrays.copyOf(antSubsetSelected[indexBestSubset], numSelectedFeature);
        }
    }

    /**
     * Updates intensity of pheromone values
     */
    private void pheromoneUpdatingRule() {
        double sum = NUM_ANTS * (numSelectedFeature - 1);
        int indexCounter = 0;

        for (int i = 0; i < numFeatures; i++) {
            for (int j = 0; j < i; j++) {
                pheromoneValues[indexCounter] = ((1 - DECAY_RATE) * pheromoneValues[indexCounter]) + (edgeCounter[indexCounter] / sum);
                indexCounter++;
            }
        }

        for (int i = 0; i < NUM_ANTS; i++) {
            for (int j = 0; j < numSelectedFeature - 1; j++) {
                int startIndex = antSubsetSelected[i][j];
                int endIndex = antSubsetSelected[i][j + 1];
                pheromoneValues[findIndex(startIndex, endIndex)] += antPerformValues[i];
            }
        }
    }

    /**
     * Starts the feature selection process by microarray gene selection based
     * on ant colony optimization (MGSACO) method
     */
    @Override
    public void evaluateFeatures() {
//        RAND_NUMBER = new Random(SEED_VALUE);
        if (NUM_ANTS == 0) {
            NUM_ANTS = numFeatures < 100 ? numFeatures : 100;
        }

        performBestSubset = 0;
        antSubsetSelected = new int[NUM_ANTS][numSelectedFeature];
        antPerformValues = new double[NUM_ANTS];
        relevanceFeature = new double[numFeatures];
        simValues = new double[(numFeatures * (numFeatures - 1)) / 2];
        edgeCounter = new int[(numFeatures * (numFeatures - 1)) / 2];
        tabuList = new boolean[NUM_ANTS][numFeatures];
        currentState = new int[NUM_ANTS];
        pheromoneValues = new double[(numFeatures * (numFeatures - 1)) / 2];
        int counter = 0;

        //computes the relevance values of the features
        computeRelevance();

        //computes the similarity values between pairs of feature
        for (int i = 0; i < numFeatures; i++) {
            for (int j = 0; j < i; j++) {
                simValues[counter++] = Math.abs(MathFunc.computeSimilarity(trainSet, i, j));
            }
        }

        //sets the initial intensity of pheromone
        Arrays.fill(pheromoneValues, INIT_PHEROMONE_VALUE);

        //starts the feature selection process
        for (int nc = 0; nc < MAX_ITERATION; nc++) {
            //System.out.println("          ------ Iteration " + nc + " -----");

            //sets the initial values of edge counter (EC) to zero
            Arrays.fill(edgeCounter, 0);

            //sets the initial values of tabu list to false
            for (int i = 0; i < NUM_ANTS; i++) {
                Arrays.fill(tabuList[i], false);
            }

            //places the ants randomly on the nodes in the graph
            setStartNode();

            //selects predefined number of features for all ants
            for (int i = 1; i < numSelectedFeature; i++) {
                for (int k = 0; k < NUM_ANTS; k++) {
                    int newFeature = stateTransitionRules(k);
                    tabuList[k][newFeature] = true;
                    antSubsetSelected[k][i] = newFeature;
                    edgeCounter[findIndex(currentState[k], newFeature)]++;
                    currentState[k] = newFeature;
                }
            }

            //evaluates the candidate subsets of selected features
            int bestAntIndex = evaluateSubsets();

            //finds the best subset of feature up to know
            findBestSubset(bestAntIndex);

            //updates intensity of the pheromone values
            pheromoneUpdatingRule();
        }

        ArraysFunc.sortArray1D(selectedFeatureSubset, false);
//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String validate() {
        if (NUM_ANTS > numFeatures) {
            return "The parameter value of MGSACO (number of ants) is incorrect.";
        }
        return "";
    }
}
