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
package unifeat.featureSelection.wrapper.PSOBasedMethods.HPSO_LS;

import unifeat.featureSelection.wrapper.PSOBasedMethods.BasicSwarm;
import unifeat.result.performanceMeasure.Criteria;
import unifeat.util.ArraysFunc;
import unifeat.util.MathFunc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This java class is used to implement a swarm of particles in hybrid particle
 * swarm optimization method using local search (HPSO-LS) in which the type of
 * position vector is boolean and the type of particle is extended from Particle
 * class.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.PSOBasedMethods.BasicSwarm
 */
public class Swarm extends BasicSwarm<Boolean, Particle> {

    private double[][] trainSet;
    private int seedValue = 0;
    private Random rand = new Random(seedValue);
    public static double EPSILON;
    public static double ALPHA;
    private int constantSubsetSize;
    private ArrayList<Integer> dissimilarSet;
    private ArrayList<Integer> similarSet;

    /**
     * Initializes the parameters
     *
     * @param epsilon the epsilon parameter used in the subset size determining
     * scheme
     * @param alpha the alpha parameter used in the local search operation
     * (control similar/dissimilar)
     */
    public Swarm(double epsilon, double alpha) {
        super(Boolean.class, Particle.class);
        Swarm.EPSILON = epsilon;
        Swarm.ALPHA = alpha;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = new Particle(PROBLEM_DIMENSION);
        }
    }

    /**
     * {@inheritDoc }
     * Each particle is randomly initialized in the predefined ranges of values.
     * The number of selected features in each particle is constant and defined
     * by a scheme.
     */
    @Override
    public void initialization() {
        constantSubsetSize = determineSubsetSize();

        double[] featCorrelation = computeCorrelation();
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

        for (int par = 0; par < POPULATION_SIZE; par++) {
            for (int dim = 0; dim < PROBLEM_DIMENSION; dim++) {
                population[par].position[dim] = dim < constantSubsetSize;
                population[par].velocity[dim] = MathFunc.generateRandNum(MIN_VELOCITY, MAX_VELOCITY, rand);
            }
            MathFunc.randomize(population[par].position);
        }
    }

    /**
     * {@inheritDoc }
     * K-fold cross validation on training set is used for evaluating the
     * classification performance of selected feature subset by each particle.
     */
    @Override
    public void evaluateFitness() {
        for (int par = 0; par < POPULATION_SIZE; par++) {
            if (population[par].numSelectedFeatures() > 0) {
                Criteria criteria = fitnessEvaluator.crossValidation(population[par].selectedFeaturesSubset());
                population[par].setFitness(criteria.getAccuracy());
            } else {
                population[par].setFitness(0);
            }
        }
    }

    /**
     * {@inheritDoc }
     * Personal best position is updated when the classification performance of
     * the particle's new position is better than personal best.
     */
    @Override
    public void updatePersonalBest() {
        for (int par = 0; par < POPULATION_SIZE; par++) {
            if (population[par].getFitness() > population[par].getPBestFitness()) {
                population[par].pBest = Arrays.copyOf(population[par].position, PROBLEM_DIMENSION);
                population[par].setPBestFitness(population[par].getFitness());
            }
        }
    }

    /**
     * {@inheritDoc }
     * Global best position is updated when the classification performance of
     * any personal best position of the particles is better than global best.
     */
    @Override
    public void updateGlobalBest() {
        int maxIndex = 0;
        double maxValue = population[0].getPBestFitness();
        for (int par = 1; par < POPULATION_SIZE; par++) {
            if (population[par].getPBestFitness() > maxValue) {
                maxIndex = par;
                maxValue = population[par].getPBestFitness();
            }
        }

        if (maxValue > this.getGBestFitness()) {
            this.setGBest(Arrays.copyOf(population[maxIndex].pBest, PROBLEM_DIMENSION));
            this.setGBestFitness(maxValue);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void updateParticleVelocity() {
        for (int par = 0; par < POPULATION_SIZE; par++) {
            for (int dim = 0; dim < PROBLEM_DIMENSION; dim++) {
                double firstPart = INERTIA_WEIGHT * population[par].velocity[dim];
                double secondPart = C1 * rand.nextDouble() * subtractBoolean(population[par].pBest[dim], population[par].position[dim]);
                double thirdPart = C2 * rand.nextDouble() * subtractBoolean(gBest[dim], population[par].position[dim]);
                population[par].velocity[dim] = firstPart + secondPart + thirdPart;
                if (population[par].velocity[dim] > MAX_VELOCITY) {
                    population[par].velocity[dim] = MAX_VELOCITY;
                } else if (population[par].velocity[dim] < MIN_VELOCITY) {
                    population[par].velocity[dim] = MIN_VELOCITY;
                }
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void updateParticlePosition() {
        for (int par = 0; par < POPULATION_SIZE; par++) {
            for (int dim = 0; dim < PROBLEM_DIMENSION; dim++) {
                population[par].position[dim] = rand.nextDouble() < sigmoidFunc(population[par].velocity[dim]);
            }
        }
    }

    /**
     * This method performs a local search strategy on each particle which is
     * based on correlation of features.
     */
    public void operateLocalSearch() {
        int Nd = (int) (ALPHA * constantSubsetSize);
        int Ns = constantSubsetSize - Nd;
        for (int par = 0; par < POPULATION_SIZE; par++) {
            ArrayList<Integer> Xd = new ArrayList<>();
            ArrayList<Integer> Xs = new ArrayList<>();

            for (Integer entry : dissimilarSet) {
                if (population[par].position[entry]) {
                    Xd.add(entry);
                }
            }

            for (Integer entry : similarSet) {
                if (population[par].position[entry]) {
                    Xs.add(entry);
                }
            }

            if (Xd.size() < Nd) {
                for (int entry = 0, count = 0; entry < dissimilarSet.size() && count < Nd - Xd.size(); entry++) {
                    if (!population[par].position[dissimilarSet.get(entry)]) {
                        population[par].position[dissimilarSet.get(entry)] = true;
                        count++;
                    }
                }
            } else if (Xd.size() > Nd) {
                for (int entry = Xd.size() - 1; entry >= Nd; entry--) {
                    population[par].position[Xd.get(entry)] = false;
                }
            }

            if (Xs.size() < Ns) {
                for (int entry = 0, count = 0; entry < similarSet.size() && count < Ns - Xs.size(); entry++) {
                    if (!population[par].position[similarSet.get(entry)]) {
                        population[par].position[similarSet.get(entry)] = true;
                        count++;
                    }
                }
            } else if (Xs.size() > Ns) {
                for (int row = Xs.size() - 1; row >= Ns; row--) {
                    population[par].position[Xs.get(row)] = false;
                }
            }
        }
    }

    /**
     * This method determines the size of feature subset. The number of selected
     * features in each particle is constant and defined by this value.
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
     * This method subtracts two boolean values.
     * <p>
     * If the input value equals to true, the result will be considered as 1,
     * otherwise 0.
     *
     * @param num1 the first input value
     * @param num2 the second input value
     *
     * @return the result of subtraction
     */
    private int subtractBoolean(boolean num1, boolean num2) {
        int intNum1 = num1 ? 1 : 0;
        int intNum2 = num2 ? 1 : 0;
        return intNum1 - intNum2;
    }

    /**
     * This method transforms a input value to the range of (0,1) using sigmoid
     * function.
     *
     * @param value the input value that will be transformed
     *
     * @return the transformed value of the input value
     */
    private double sigmoidFunc(double value) {
        return 1.0 / (1.0 + Math.pow(Math.E, -value));
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
