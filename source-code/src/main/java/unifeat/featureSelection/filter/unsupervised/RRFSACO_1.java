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
 * This java class is used to implement the relevance–redundancy feature
 * selection based on ant colony optimization, version1 (RRFSACO_1) method.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.filter.FilterApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public class RRFSACO_1 extends FilterApproach {

    private final int MAX_ITERATION;
    private int NUM_ANTS;
    private int NUM_FEAT_OF_ANT;
    private int TEMP_NUM_FEAT_OF_ANT;
    private final double DECAY_RATE;
    private final double BETA;
    private final double PROB_CHOOSE_EQUATION;
    private double[] relevanceFeature;
    private double[] simValues;
    private double[] pheromoneValues;
    private int[] featureCounter;
    private boolean[][] tabuList;
    private int[] currentState;
//    private final int SEED_VALUE = 0;
    private final double ERROR_SIMILARITY = 0.0001;
    private final double ERROR_RELEVANCE = 0.0001;
//    private Random RAND_NUMBER = new Random(SEED_VALUE);

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains
     * (<code>sizeSelectedFeatureSubset</code>, <code>numIterations</code>,
     * <code>numAnt</code>, <code>numFeatureOfAnt</code>,
     * <code>evaporationRate</code>, <code>betaParameter</code>,
     * <code>q0_Parameter</code>) in which
     * <code><b><i>sizeSelectedFeatureSubset</i></b></code> is the number of
     * selected features, <code><b><i>numIterations</i></b></code> is the
     * maximum number of iteration, <code><b><i>numAnt</i></b></code> is the
     * number of ants, <code><b><i>numFeatureOfAnt</i></b></code> is the number
     * of selected features by each ant in each iteration,
     * <code><b><i>evaporationRate</i></b></code> is the evaporation rate of the
     * pheromone, <code><b><i>betaParameter</i></b></code> is the beta parameter
     * in the state transition rule, and <code><b><i>q0_Parameter</i></b></code>
     * is the q0 parameter in the state transition rule
     */
    public RRFSACO_1(Object... arguments) {
        super((int) arguments[0]);
        MAX_ITERATION = (int) arguments[1];
        NUM_ANTS = (int) arguments[2];
        NUM_FEAT_OF_ANT = TEMP_NUM_FEAT_OF_ANT = (int) arguments[3];
        DECAY_RATE = (double) arguments[4];
        BETA = (double) arguments[5];
        PROB_CHOOSE_EQUATION = (double) arguments[6];
    }

    /**
     * Initializes the parameters
     *
     * @param sizeSelectedFeatureSubset the number of selected features
     * @param numIterations the maximum number of iteration
     * @param numAnt the number of ants
     * @param numFeatureOfAnt the number of selected features by each ant in
     * each iteration
     * @param evaporationRate the evaporation rate of the pheromone
     * @param betaParameter the beta parameter in the state transition rule
     * @param q0_Parameter the q0 parameter in the state transition rule
     */
    public RRFSACO_1(int sizeSelectedFeatureSubset, int numIterations, int numAnt, int numFeatureOfAnt, double evaporationRate, double betaParameter, double q0_Parameter) {
        super(sizeSelectedFeatureSubset);
        MAX_ITERATION = numIterations;
        NUM_ANTS = numAnt;
        NUM_FEAT_OF_ANT = TEMP_NUM_FEAT_OF_ANT = numFeatureOfAnt;
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
                double result = pheromoneValues[j] / Math.pow(simValues[newIndex] + ERROR_SIMILARITY, BETA);
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
                prob[j] = pheromoneValues[j] / Math.pow(simValues[newIndex] + ERROR_SIMILARITY, BETA);
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
     * Updates intensity of pheromone values
     */
    private void pheromoneUpdatingRule() {
        double sum = NUM_ANTS * (NUM_FEAT_OF_ANT - 1);

        for (int i = 0; i < numFeatures; i++) {
            pheromoneValues[i] = ((1 - DECAY_RATE) * pheromoneValues[i]) + (featureCounter[i] / sum);
        }
    }

    /**
     * Starts the feature selection process by relevance–redundancy feature
     * selection based on ant colony optimization, version1 (RRFSACO_1) method
     */
    @Override
    public void evaluateFeatures() {
//        RAND_NUMBER = new Random(SEED_VALUE);
        if (TEMP_NUM_FEAT_OF_ANT == 0) {
            NUM_FEAT_OF_ANT = numSelectedFeature;
        }

        if (NUM_ANTS == 0) {
            NUM_ANTS = numFeatures < 100 ? numFeatures : 100;
        }

        relevanceFeature = new double[numFeatures];
        simValues = new double[(numFeatures * (numFeatures - 1)) / 2];
        featureCounter = new int[numFeatures];
        tabuList = new boolean[NUM_ANTS][numFeatures];
        currentState = new int[NUM_ANTS];
        pheromoneValues = new double[numFeatures];
        int[] indecesFeature;
        int counter = 0;

        //computes the relevance values of the features
        computeRelevance();

        //sets the initial intensity of pheromone
        for (int i = 0; i < numFeatures; i++) {
            pheromoneValues[i] = relevanceFeature[i];
        }

        //computes the similarity values between pairs of feature
        for (int i = 0; i < numFeatures; i++) {
            for (int j = 0; j < i; j++) {
                simValues[counter++] = Math.abs(MathFunc.computeSimilarity(trainSet, i, j));
            }
        }

        //starts the feature selection process
        for (int nc = 0; nc < MAX_ITERATION; nc++) {
            //System.out.println("          ------ Iteration " + nc + " -----");

            //sets the initial values of feature counter (FC) to zero
            Arrays.fill(featureCounter, 0);

            //sets the initial values of tabu list to false
            for (int i = 0; i < NUM_ANTS; i++) {
                Arrays.fill(tabuList[i], false);
            }

            //places the ants randomly on the nodes in the graph
            setStartNode();

            //selects predefined number of features for all ants
            for (int i = 1; i < NUM_FEAT_OF_ANT; i++) {
                for (int k = 0; k < NUM_ANTS; k++) {
                    int newFeature = stateTransitionRules(k);
                    tabuList[k][newFeature] = true;
                    featureCounter[newFeature]++;
                    currentState[k] = newFeature;
                }
            }

            //updates intensity of the pheromone values
            pheromoneUpdatingRule();
        }

        indecesFeature = ArraysFunc.sortWithIndex(Arrays.copyOf(pheromoneValues, pheromoneValues.length), true);
//        for (int i = 0; i < numFeatures; i++) {
//            System.out.println(i + ") =  " + pheromoneValues[i]);
//        }

        selectedFeatureSubset = Arrays.copyOfRange(indecesFeature, 0, numSelectedFeature);
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
        if (NUM_ANTS > numFeatures || NUM_FEAT_OF_ANT > numFeatures) {
            return "The parameter values of RRFSACO_1 (number of ants or number of features for ant) are incorrect.";
        }
        return "";
    }
}
