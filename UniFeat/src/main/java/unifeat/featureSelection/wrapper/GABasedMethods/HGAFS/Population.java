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
package unifeat.featureSelection.wrapper.GABasedMethods.HGAFS;

import unifeat.featureSelection.wrapper.GABasedMethods.BasicPopulation;
import unifeat.featureSelection.wrapper.GABasedMethods.CrossoverOperator;
import unifeat.featureSelection.wrapper.GABasedMethods.MutationOperator;
import unifeat.featureSelection.wrapper.GABasedMethods.SelectionOperator;
import unifeat.gui.featureSelection.wrapper.GABased.CrossOverType;
import unifeat.gui.featureSelection.wrapper.GABased.MutationType;
import unifeat.gui.featureSelection.wrapper.GABased.ReplacementType;
import unifeat.gui.featureSelection.wrapper.GABased.SelectionType;
import unifeat.result.performanceMeasure.Criteria;
import unifeat.util.ArraysFunc;
import unifeat.util.MathFunc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This java class is used to implement a population of individuals in hybrid
 * genetic algorithm for feature selection using local search (HGAFS) in which
 * the type of individual is extended from Individual class.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.GABasedMethods.BasicPopulation
 */
public class Population extends BasicPopulation<Individual> {

    private double[][] trainSet;
    private int seedValue = 0;
    private Random rand = new Random(seedValue);
    public static double EPSILON;
    public static double MU;
    private int constantSubsetSize;
    private double[] featCorrelationValue;
    private ArrayList<Integer> dissimilarSet;
    private ArrayList<Integer> similarSet;
    private Individual[] matingPool;

    /**
     * Initializes the parameters
     *
     * @param epsilon the epsilon parameter used in the subset size determining
     * scheme
     * @param mu the mu parameter used in the local search operation (control
     * similar/dissimilar)
     */
    public Population(double epsilon, double mu) {
        super(Individual.class);
        Population.EPSILON = epsilon;
        Population.MU = mu;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = new Individual(PROBLEM_DIMENSION);
        }
    }

    /**
     * {@inheritDoc }
     * <p>
     * Each individual is randomly initialized in the predefined ranges of
     * values. The number of selected features in each individual is constant
     * and defined by a scheme.
     */
    @Override
    public void initialization() {
        constantSubsetSize = determineSubsetSize();

        featCorrelationValue = computeCorrelation();
        int[] indecesFeatures = ArraysFunc.sortWithIndex(computeCorrelation(), false);

        dissimilarSet = new ArrayList<>();
        similarSet = new ArrayList<>();
        int dissimilarSetSize = PROBLEM_DIMENSION / 2;
        for (int dim = 0; dim < PROBLEM_DIMENSION; dim++) {
            if (dim < dissimilarSetSize) {
                dissimilarSet.add(indecesFeatures[dim]);
            } else {
                similarSet.add(indecesFeatures[dim]);
            }
        }

        for (int indiv = 0; indiv < POPULATION_SIZE; indiv++) {
            for (int gene = 0; gene < PROBLEM_DIMENSION; gene++) {
                population[indiv].genes[gene] = gene < constantSubsetSize;
            }
            MathFunc.randomize(population[indiv].genes);
        }
    }

    /**
     * {@inheritDoc }
     * K-fold cross validation on training set is used for evaluating the
     * classification performance of selected feature subset by each individual.
     * Also, inverse of summation of the correlation of selected features by
     * individual is added to the accuracy of classifier.
     */
    @Override
    public void evaluateFitness() {
        for (int indiv = 0; indiv < POPULATION_SIZE; indiv++) {

            /**
             * compute the inverse of summation of the correlation of individual
             * selected features
             */
            double sumCor = 0;
            for (int gene = 0; gene < PROBLEM_DIMENSION; gene++) {
                if (population[indiv].genes[gene]) {
                    sumCor += featCorrelationValue[gene];
                }
            }

            sumCor = 1.0 / sumCor;

            if (population[indiv].numSelectedFeatures() > 0) {
                Criteria criteria = fitnessEvaluator.crossValidation(population[indiv].selectedFeaturesSubset());
                population[indiv].setFitness(criteria.getAccuracy() + sumCor);
            } else {
                population[indiv].setFitness(0 + sumCor);
            }
        }
    }

    /**
     * {@inheritDoc }
     * <p>
     * The selection type is selected based on selectionType by user.
     */
    @Override
    public void operateSelection() {
        int[] selectedParentIndices = new int[POPULATION_SIZE];
        matingPool = new Individual[POPULATION_SIZE];

        for (int indiv = 0; indiv < matingPool.length; indiv++) {
            matingPool[indiv] = new Individual(PROBLEM_DIMENSION);
        }

        if (Population.SELECTION_TYPE == SelectionType.FITNESS_PROPORTIONAL_SELECTION) {
            selectedParentIndices = SelectionOperator.fitnessProportionalSelection(this.getFitness(), POPULATION_SIZE);
        } else if (Population.SELECTION_TYPE == SelectionType.RANK_BASED_SELECTION) {
            selectedParentIndices = SelectionOperator.rankBasedSelection(this.getFitness(), POPULATION_SIZE);
        }

        for (int indiv = 0; indiv < POPULATION_SIZE; indiv++) {
            matingPool[indiv].genes = Arrays.copyOf(population[selectedParentIndices[indiv]].genes, PROBLEM_DIMENSION);
            matingPool[indiv].setFitness(population[selectedParentIndices[indiv]].getFitness());
        }
    }

    /**
     * {@inheritDoc }
     * <p>
     * The crossover type is selected based on crossoverType by user.
     */
    @Override
    public void operateCrossOver() {
        if (Population.CROSSOVER_TYPE == CrossOverType.ONE_POINT_CROSS_OVER) {
            for (int indiv = 0; indiv < matingPool.length - 1; indiv += 2) {
                if (Math.random() < CROSS_OVER_RATE) {
                    CrossoverOperator.onePointCrossover(matingPool[indiv].genes, matingPool[indiv + 1].genes);
                }
            }
        } else if (Population.CROSSOVER_TYPE == CrossOverType.TWO_POINT_CROSS_OVER) {
            for (int indiv = 0; indiv < matingPool.length - 1; indiv += 2) {
                if (Math.random() < CROSS_OVER_RATE) {
                    CrossoverOperator.twoPointCrossover(matingPool[indiv].genes, matingPool[indiv + 1].genes);
                }
            }
        } else if (Population.CROSSOVER_TYPE == CrossOverType.UNIFORM_CROSS_OVER) {
            for (int indiv = 0; indiv < matingPool.length - 1; indiv += 2) {
                CrossoverOperator.uniformCrossover(matingPool[indiv].genes, matingPool[indiv + 1].genes, CROSS_OVER_RATE);
            }
        }
    }

    /**
     * {@inheritDoc }
     * <p>
     * The mutation type is selected based on mutationType by user.
     */
    @Override
    public void operateMutation() {
        if (Population.MUTATION_TYPE == MutationType.BITWISE_MUTATION) {
            for (Individual indiv : matingPool) {
                MutationOperator.bitwiseMutation(indiv.genes, MUTATION_RATE);
            }
        }
    }

    /**
     * This method performs a local search strategy on each individual which is
     * based on correlation of features.
     */
    public void operateLocalSearch() {
        int Nd = (int) (MU * constantSubsetSize);
        int Ns = constantSubsetSize - Nd;
        for (int indiv = 0; indiv < matingPool.length; indiv++) {
            ArrayList<Integer> Xd = new ArrayList<>();
            ArrayList<Integer> Xs = new ArrayList<>();

            for (Integer entry : dissimilarSet) {
                if (matingPool[indiv].genes[entry]) {
                    Xd.add(entry);
                }
            }

            for (Integer entry : similarSet) {
                if (matingPool[indiv].genes[entry]) {
                    Xs.add(entry);
                }
            }

            if (Xd.size() < Nd) {
                for (int entry = 0, count = 0; entry < dissimilarSet.size() && count < Nd - Xd.size(); entry++) {
                    if (!matingPool[indiv].genes[dissimilarSet.get(entry)]) {
                        matingPool[indiv].genes[dissimilarSet.get(entry)] = true;
                        count++;
                    }
                }
            } else if (Xd.size() > Nd) {
                for (int entry = Xd.size() - 1; entry >= Nd; entry--) {
                    matingPool[indiv].genes[Xd.get(entry)] = false;
                }
            }

            if (Xs.size() < Ns) {
                for (int entry = 0, count = 0; entry < similarSet.size() && count < Ns - Xs.size(); entry++) {
                    if (!matingPool[indiv].genes[similarSet.get(entry)]) {
                        matingPool[indiv].genes[similarSet.get(entry)] = true;
                        count++;
                    }
                }
            } else if (Xs.size() > Ns) {
                for (int row = Xs.size() - 1; row >= Ns; row--) {
                    matingPool[indiv].genes[Xs.get(row)] = false;
                }
            }
        }
    }

    /**
     * {@inheritDoc }
     * <p>
     * The replacement type is selected based on replacementType by user.
     */
    @Override
    public void operateGenerationReplacement() {
        if (Population.REPLACEMENT_TYPE == ReplacementType.TOTAL_REPLACEMENT) {
            population = matingPool;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Individual getFittestIndividual() {
        int fittestIndex = 0;
        double fittestValue = population[0].getFitness();

        for (int indiv = 1; indiv < POPULATION_SIZE; indiv++) {
            if (population[indiv].getFitness() > fittestValue) {
                fittestValue = population[indiv].getFitness();
                fittestIndex = indiv;
            }
        }
        return population[fittestIndex];
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public double[] getFitness() {
        double[] fitnessValues = new double[POPULATION_SIZE];

        for (int indiv = 0; indiv < POPULATION_SIZE; indiv++) {
            fitnessValues[indiv] = population[indiv].getFitness();
        }

        return fitnessValues;
    }

    /**
     * This method determines the size of feature subset. The number of selected
     * features in each individual is constant and defined by this value.
     *
     * @return the determined value of the size of feature subset
     */
    private int determineSubsetSize() {
        int subsetSizeEndInterval = (int) (EPSILON * PROBLEM_DIMENSION);
        double[] probabilities = new double[subsetSizeEndInterval + 1];
        double sumProbabilities = 0;

        /**
         * compute probabilities of each subset size
         */
        for (int k = 3; k <= subsetSizeEndInterval; k++) {
            int numerator = PROBLEM_DIMENSION - k;
            double denominator = 0;
            for (int i = 1; i <= numerator; i++) {
                denominator += (PROBLEM_DIMENSION - i);
            }
            probabilities[k] = numerator / denominator;
            sumProbabilities += probabilities[k];
        }

        /**
         * normalize all the probabilities values such that the summation of
         * them is equaled to 1
         */
        for (int k = 3; k <= subsetSizeEndInterval; k++) {
            probabilities[k] /= sumProbabilities;
        }

        /**
         * Roulette wheel procedure
         */
        double randomNumber = rand.nextDouble();
        double sumInRoulette = 0;
        for (int k = 3; k <= subsetSizeEndInterval; k++) {
            sumInRoulette += probabilities[k];
            if (randomNumber <= sumInRoulette) {
                return k;
            }
        }

        return subsetSizeEndInterval;
    }

    /**
     * This method computes the correlation of each feature to other features.
     * 
     * @return computed correlation of each feature to other features
     */
    private double[] computeCorrelation() {
        double[] cor = new double[PROBLEM_DIMENSION];

        for (int i = 0; i < PROBLEM_DIMENSION; i++) {
            cor[i] = 0;
            for (int j = 0; j < PROBLEM_DIMENSION; j++) {
                if (i != j) {
                    cor[i] += Math.abs(MathFunc.computePearsonCorCoef(trainSet, i, j));
                }
            }
            cor[i] /= (PROBLEM_DIMENSION - 1);
        }

        return cor;
    }

    /**
     * This method sets the information of the dataset.
     *
     * @param data the input dataset values
     */
    public void setDataInfo(double[][] data) {
        this.trainSet = data;
    }
}
