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

import unifeat.featureSelection.wrapper.ACOBasedMethods.BasicColony;
import unifeat.featureSelection.wrapper.GABasedMethods.SelectionOperator;
import unifeat.result.performanceMeasure.Criteria;
import unifeat.util.ArraysFunc;
import unifeat.util.MathFunc;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This java class is used to implement a colony of ants in optimal ant colony
 * optimization (Optimal ACO) method in which the type of ant is extended from
 * Ant class.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.ACOBasedMethods.BasicColony
 */
public class Colony extends BasicColony<Ant> {

    public static double PHI;

    /**
     * Initializes the parameters
     *
     * @param phi the phi parameter used in the pheromone update rule for
     * controlling the relative weight of classifier performance and feature
     * subset length
     */
    public Colony(double phi) {
        super(Ant.class);
        Colony.PHI = phi;
        for (int i = 0; i < COLONY_SIZE; i++) {
            colony[i] = new Ant();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void initialization() {
        graphRepresentation.fillPheromoneArray(INIT_PHEROMONE_VALUE);
//        System.out.println(graphRepresentation.toString());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setInitialState() {
        Boolean[] checkListFeatures = new Boolean[NUM_ORIGINAL_FEATURE];
        Arrays.fill(checkListFeatures, false);

        for (int ant = 0; ant < COLONY_SIZE; ant++) {
            colony[ant].emptyFeatureSubset();
            colony[ant].setFitness(0);
            colony[ant].setCountSteps(0);
        }

        for (int feat = 0; feat < COLONY_SIZE; feat++) {
            checkListFeatures[feat] = true;
        }
        MathFunc.randomize(checkListFeatures);

        for (int feat = 0, ant = 0; feat < NUM_ORIGINAL_FEATURE; feat++) {
            if (checkListFeatures[feat]) {
                colony[ant++].addFeature(feat);
            }
        }

//        for (int ant = 0; ant < COLONY_SIZE; ant++) {
//            System.out.println(colony[ant].toString());
//        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Criteria evaluateCurrentSolution(int antIndex) {
        int[] featureSet = ArraysFunc.convertArrayListToInt(colony[antIndex].getFeatureSubset());
        ArraysFunc.sortArray1D(featureSet, false);
        
        return fitnessEvaluator.crossValidation(featureSet);
    }

    /**
     * {@inheritDoc }
     * <p>
     * Classifier performance is used as heuristic desirability.
     */
    @Override
    public void operateStateTransitionRule(int antIndex) {
        ArrayList<Integer> feasibleFeatureSet = colony[antIndex].getFeasibleFeatureSet();
        double[] probabilities = new double[feasibleFeatureSet.size()];
        double[] fitnessValues = new double[feasibleFeatureSet.size()];
        double sumProb = 0;

        for (int feat = 0; feat < probabilities.length; feat++) {
            int currFeasible = feasibleFeatureSet.get(feat);
            colony[antIndex].addFeature(currFeasible);
            
            fitnessValues[feat] = evaluateCurrentSolution(antIndex).getAccuracy() / 100.0;
            double pheromone = graphRepresentation.getPheromone(0, currFeasible);
            probabilities[feat] = Math.pow(pheromone, ALPHA) * Math.pow(fitnessValues[feat], BETA);
            sumProb += probabilities[feat];
            
            colony[antIndex].removeFeature(currFeasible);
        }

        for (int feat = 0; feat < probabilities.length; feat++) {
            probabilities[feat] = probabilities[feat] / sumProb;
        }

        int selectedIndex = SelectionOperator.rouletteWheel(probabilities);
        int selectedFeat = feasibleFeatureSet.get(selectedIndex);
        colony[antIndex].addFeature(selectedFeat);
        colony[antIndex].setFitness(fitnessValues[selectedIndex]);

//        System.out.println(colony[antIndex].toString());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void constructSolution() {
        for (int ant = 0; ant < COLONY_SIZE; ant++) {
            while (colony[ant].getFeatureSubsetSize() < NUM_ORIGINAL_FEATURE
                    && colony[ant].getCountSteps() < 10) {
                double beforeFitness = colony[ant].getFitness();
                operateStateTransitionRule(ant);
                double afterFitness = colony[ant].getFitness();
                if (afterFitness <= beforeFitness) {
                    colony[ant].increaseCountSteps();
                } else {
                    colony[ant].setCountSteps(0);
                }
            }
        }

//        System.out.println(this.toString());
    }

    /**
     * {@inheritDoc }
     * <p>
     * Best ant deposits additional pheromone on nodes of the best solution.
     */
    @Override
    public void operatePheromoneUpdateRule() {
        /**
         * Pheromone evaporation on all nodes is triggered
         */
        for (int feat = 0; feat < NUM_ORIGINAL_FEATURE; feat++) {
            double evaporatedValue = (1.0 - RHO) * graphRepresentation.getPheromone(0, feat);
            graphRepresentation.setPheromone(evaporatedValue, 0, feat);
        }

        /**
         * Best ant deposits additional pheromone on nodes of the best solution
         */
        Ant bestAnt = getBestAnt();
        double firstValue = PHI * bestAnt.getFitness();
        double secondValue = ((1.0 - PHI) * (NUM_ORIGINAL_FEATURE - bestAnt.getFeatureSubsetSize())) / NUM_ORIGINAL_FEATURE;
        ArrayList<Integer> featureSet = bestAnt.getFeatureSubset();
        for (int i = 0; i < featureSet.size(); i++) {
            double prevValue = graphRepresentation.getPheromone(0, featureSet.get(i));
            graphRepresentation.setPheromone(prevValue + firstValue + secondValue, 0, featureSet.get(i));
        }

        /**
         * Each ant deposit a quantity of pheromone on each node that it has
         * used
         */
        for (int ant = 0; ant < COLONY_SIZE; ant++) {
            firstValue = PHI * colony[ant].getFitness();
            secondValue = ((1.0 - PHI) * (NUM_ORIGINAL_FEATURE - colony[ant].getFeatureSubsetSize())) / NUM_ORIGINAL_FEATURE;
            featureSet = colony[ant].getFeatureSubset();
            for (int i = 0; i < featureSet.size(); i++) {
                double prevValue = graphRepresentation.getPheromone(0, featureSet.get(i));
                graphRepresentation.setPheromone(prevValue + firstValue + secondValue, 0, featureSet.get(i));
            }
        }
    }

    /**
     * {@inheritDoc }
     * <p>
     * Best ant is selected based on classifier performance and length of
     * selected subsets.
     */
    @Override
    public Ant getBestAnt() {
        int fittestIndex = 0;
        double fittestValue = colony[0].getFitness();
        int subsetSize = colony[0].getFeatureSubsetSize();
        for (int ant = 1; ant < COLONY_SIZE; ant++) {
            if ((colony[ant].getFitness() > fittestValue)
                    || ((colony[ant].getFitness() == fittestValue)
                    && (colony[ant].getFeatureSubsetSize() < subsetSize))) {
                fittestIndex = ant;
                fittestValue = colony[ant].getFitness();
                subsetSize = colony[ant].getFeatureSubsetSize();
            }
        }
//        System.out.println(colony[fittestIndex].toString());
        return colony[fittestIndex];
    }
    
    /**
     * This method returns the size of the colony.
     *
     * @return the number of ants in the colony
     */
    public int getColonySize() {
        return COLONY_SIZE;
    }
}
