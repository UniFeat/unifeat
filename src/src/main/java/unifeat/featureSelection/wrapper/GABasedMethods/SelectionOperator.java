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
package unifeat.featureSelection.wrapper.GABasedMethods;

import unifeat.util.ArraysFunc;
import unifeat.util.MathFunc;

/**
 * This java class is used to implement various selection operators for
 * selecting parents from the individuals of a population according to their
 * fitness that will recombine for next generation.
 * <p>
 * The methods in this class is contained brief description of the applications.
 *
 * @author Sina Tabakhi
 */
public class SelectionOperator {

    /**
     * Selects an individual in a population according to their probabilities
     * using roulette wheel algorithm
     *
     * @param probabilities the array of probabilities of selecting individuals
     *
     * @return the index of selected individual
     */
    public static int rouletteWheel(double[] probabilities) {
        double randomNumber = Math.random();
        double sumInRoulette = 0;
        for (int indiv = 0; indiv < probabilities.length; indiv++) {
            sumInRoulette += probabilities[indiv];
            if (randomNumber <= sumInRoulette) {
                return indiv;
            }
        }

        return probabilities.length - 1;
    }

    /**
     * Selects parents from the individuals of a population according to their
     * fitness values using fitness proportional selection method in which
     * roulette wheel algorithm is used for selecting each individual based on
     * their probabilities
     *
     * @param fitnessValues the array of fitness values of individuals in a
     * population
     * @param matingPoolSize the size of the mating pool
     *
     * @return an array of indices of the selected individuals
     */
    public static int[] fitnessProportionalSelection(double[] fitnessValues, int matingPoolSize) {
        int[] maitingPool = new int[matingPoolSize];
        int populationSize = fitnessValues.length;
        double[] probabilities = new double[populationSize];
        double sumFitness = MathFunc.sum(fitnessValues);

        /**
         * computes probability of each individual based on their fitness
         */
        for (int indiv = 0; indiv < populationSize; indiv++) {
            probabilities[indiv] = fitnessValues[indiv] / sumFitness;
        }

        for (int i = 0; i < matingPoolSize; i++) {
            maitingPool[i] = rouletteWheel(probabilities);
        }

        return maitingPool;
    }

    /**
     * Selects parents from the individuals of a population according to their
     * fitness values using rank-based selection method in which roulette wheel
     * algorithm is used for selecting each individual based on their ranks
     *
     * @param fitnessValues the array of fitness values of individuals in a
     * population
     * @param matingPoolSize the size of the mating pool
     *
     * @return an array of indices of the selected individuals
     */
    public static int[] rankBasedSelection(double[] fitnessValues, int matingPoolSize) {
        int[] maitingPool = new int[matingPoolSize];
        int populationSize = fitnessValues.length;
        double[] probabilities = new double[populationSize];
        int[] indecesIndividual = ArraysFunc.sortWithIndex(fitnessValues, false);
        double sumRank;
        int[] rank = new int[populationSize];

        for (int i = 0; i < populationSize; i++) {
            rank[indecesIndividual[i]] = i + 1;
        }

        sumRank = MathFunc.sum(rank);

        /**
         * computes probability of each individual based on their ranks
         */
        for (int indiv = 0; indiv < populationSize; indiv++) {
            probabilities[indiv] = rank[indiv] / sumRank;
        }

        for (int i = 0; i < matingPoolSize; i++) {
            maitingPool[i] = rouletteWheel(probabilities);
        }

        return maitingPool;
    }
}
