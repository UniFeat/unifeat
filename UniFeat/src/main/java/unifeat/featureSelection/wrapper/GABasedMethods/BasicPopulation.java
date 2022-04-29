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

import unifeat.featureSelection.FitnessEvaluator;
import unifeat.gui.featureSelection.wrapper.GABased.CrossOverType;
import unifeat.gui.featureSelection.wrapper.GABased.MutationType;
import unifeat.gui.featureSelection.wrapper.GABased.ReplacementType;
import unifeat.gui.featureSelection.wrapper.GABased.SelectionType;
import java.lang.reflect.Array;

/**
 * The abstract class contains the main methods and fields that are used in all
 * GA-based feature selection methods. This class is used to implement a
 * population of individuals in GA algorithm.
 *
 * @param <IndividualType> the type of individual implemented in GA algorithm
 *
 * @author Sina Tabakhi
 */
public abstract class BasicPopulation<IndividualType> {

    protected IndividualType[] population;
    public static FitnessEvaluator fitnessEvaluator;
    public static int PROBLEM_DIMENSION;
    protected static int POPULATION_SIZE;
    protected static double CROSS_OVER_RATE;
    protected static double MUTATION_RATE;
    protected static SelectionType SELECTION_TYPE;
    protected static CrossOverType CROSSOVER_TYPE;
    protected static MutationType MUTATION_TYPE;
    protected static ReplacementType REPLACEMENT_TYPE;

    /**
     * Initializes the parameters
     *
     * @param individual the type of individual implemented in GA algorithm
     */
    public BasicPopulation(Class<IndividualType> individual) {
        population = (IndividualType[]) Array.newInstance(individual, POPULATION_SIZE);
    }

    /**
     * This method initializes each individual in the population.
     */
    public abstract void initialization();

    /**
     * This method evaluates the fitness of each individual in the population by
     * predefined fitness function.
     */
    public abstract void evaluateFitness();

    /**
     * This method selects parents from the individuals of a population
     * according to their fitness that will recombine for next generation.
     */
    public abstract void operateSelection();

    /**
     * This method recombines (cross over) the parents to generate new
     * offsprings.
     */
    public abstract void operateCrossOver();

    /**
     * This method mutates new offsprings by changing the value of some genes in
     * them.
     */
    public abstract void operateMutation();

    /**
     * This method handles populations from one generation to the next
     * generation.
     */
    public abstract void operateGenerationReplacement();

    /**
     * This method returns the fittest individual in the population
     *
     * @return the fittest individual in the population
     */
    public abstract IndividualType getFittestIndividual();

    /**
     * This method returns an array of fitness values of individuals in a
     * population
     *
     * @return an array of fitness values of individuals
     */
    public abstract double[] getFitness();
}
