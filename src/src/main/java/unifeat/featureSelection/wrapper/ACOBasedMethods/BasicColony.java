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

import unifeat.featureSelection.FitnessEvaluator;
import unifeat.featureSelection.wrapper.ACOBasedMethods.OptimalACO.GraphRepresentation;
import unifeat.result.performanceMeasure.Criteria;
import java.lang.reflect.Array;

/**
 * The abstract class contains the main methods and fields that are used in all
 * ACO-based feature selection methods. This class is used to implement a colony
 * of ants in ACO algorithm.
 *
 * @param <AntType> the type of ant implemented in ACO algorithm
 *
 * @author Sina Tabakhi
 */
public abstract class BasicColony<AntType> {

    protected AntType[] colony;
    public static FitnessEvaluator fitnessEvaluator;
    public static GraphRepresentation graphRepresentation;
    public static int NUM_ORIGINAL_FEATURE;
    protected static int COLONY_SIZE;
    protected static double INIT_PHEROMONE_VALUE;
    protected static double ALPHA;
    protected static double BETA;
    protected static double RHO;

    /**
     * Initializes the parameters
     *
     * @param ant the type of ant implemented in ACO algorithm
     */
    public BasicColony(Class<AntType> ant) {
        colony = (AntType[]) Array.newInstance(ant, COLONY_SIZE);
    }

    /**
     * This method initializes the problem parameters.
     */
    public abstract void initialization();

    /**
     * This method places any ant randomly to one feature as their initial
     * states.
     */
    public abstract void setInitialState();

    /**
     * This method evaluates the fitness of each ant in the colony by predefined
     * fitness function.
     *
     * @param antIndex index of the ant in the colony
     *
     * @return Criteria values of the fitness function
     * @see unifeat.result.performanceMeasure.Criteria
     */
    public abstract Criteria evaluateCurrentSolution(int antIndex);

    /**
     * This method selects the next state and adds it to the current selected
     * feature subset by using state transition rule that is combination of
     * heuristic desirability and pheromone levels.
     *
     * @param antIndex index of the ant in the colony
     */
    public abstract void operateStateTransitionRule(int antIndex);

    /**
     * This method constructs solutions completely of each ant in the colony by
     * applying state transition rule repeatedly.
     */
    public abstract void constructSolution();

    /**
     * This method updates the current pheromone values by decreasing pheromone
     * concentrations and then deposit the quantity of pheromone by ants.
     */
    public abstract void operatePheromoneUpdateRule();

    /**
     * This method returns the best ant in the colony
     *
     * @return the best ant in the colony
     */
    public abstract AntType getBestAnt();

    /**
     * Returns a string representation of the colony.
     *
     * @return a string representation of the colony.
     */
    @Override
    public String toString() {
        String str = "Colony:\n";
        for (int ant = 0; ant < COLONY_SIZE; ant++) {
            str += "    ant(" + (ant + 1) + ")    " + colony[ant].toString() + "\n";
        }
        return str;
    }
}
