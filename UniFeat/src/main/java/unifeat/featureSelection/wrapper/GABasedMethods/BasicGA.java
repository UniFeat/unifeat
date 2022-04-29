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

import unifeat.classifier.ClassifierType;
import unifeat.featureSelection.FitnessEvaluator;
import unifeat.featureSelection.wrapper.WrapperApproach;
import unifeat.gui.featureSelection.wrapper.GABased.CrossOverType;
import unifeat.gui.featureSelection.wrapper.GABased.MutationType;
import unifeat.gui.featureSelection.wrapper.GABased.ReplacementType;
import unifeat.gui.featureSelection.wrapper.GABased.SelectionType;

/**
 * The abstract class contains the main methods and fields that are used in all
 * GA-based feature selection methods.
 *
 * @param <PopulationType> the type of population implemented in GA algorithm
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.WrapperApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public abstract class BasicGA<PopulationType> extends WrapperApproach {

    protected PopulationType population;
    protected final int NUM_ITERATION;
    protected final int K_FOLDS;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains ( <code>path</code>,
     * <code>numFeatures</code>, <code>classifierType</code>,
     * <code>selectedClassifierPan</code>, <code>selectionType</code>,
     * <code>crossoverType</code>, <code>mutationType</code>,
     * <code>replacementType</code>, <code>numIteration</code>
     * <code>populationSize</code>, <code>crossoverRate</code>,
     * <code>mutationRate</code>, <code>kFolds</code>) in which
     * <code><b><i>path</i></b></code> is the path of the project,
     * <code><b><i>numFeatures</i></b></code> is the number of original features
     * in the dataset, <code><b><i>classifierType</i></b></code> is the
     * classifier type for evaluating the fitness of a solution,
     * <code><b><i>selectedClassifierPan</i></b></code> is the selected
     * classifier panel, <code><b><i>selectionType</i></b></code> is used for
     * selecting parents from the individuals of a population according to their
     * fitness, <code><b><i>crossoverType</i></b></code> is used for recombining
     * the parents to generate new offsprings based on crossover rate,
     * <code><b><i>mutationType</i></b></code> is used for mutating new
     * offsprings by changing the value of some genes in them based on mutation
     * rate, <code><b><i>replacementType</i></b></code> is used for handling
     * populations from one generation to the next generation,
     * <code><b><i>numIteration</i></b></code> is the maximum number of allowed
     * iterations that algorithm repeated,
     * <code><b><i>populationSize</i></b></code> is the size of population of
     * candidate solutions, <code><b><i>crossoverRate</i></b></code> is the
     * probability of crossover operation, <code><b><i>mutationRate
     * </i></b></code> is the probability of mutation operation, and
     * <code><b><i>kFolds</i></b></code> is the number of equal sized subsamples
     * that is used in k-fold cross validation
     */
    public BasicGA(Object... arguments) {
        super((String) arguments[0]);
        BasicPopulation.PROBLEM_DIMENSION = (int) arguments[1];
        BasicPopulation.SELECTION_TYPE = (SelectionType) arguments[4];
        BasicPopulation.CROSSOVER_TYPE = (CrossOverType) arguments[5];
        BasicPopulation.MUTATION_TYPE = (MutationType) arguments[6];
        BasicPopulation.REPLACEMENT_TYPE = (ReplacementType) arguments[7];
        this.NUM_ITERATION = (int) arguments[8];
        BasicPopulation.POPULATION_SIZE = (int) arguments[9];
        BasicPopulation.CROSS_OVER_RATE = (double) arguments[10];
        BasicPopulation.MUTATION_RATE = (double) arguments[11];
        this.K_FOLDS = (int) arguments[12];
        BasicPopulation.fitnessEvaluator = new FitnessEvaluator(TEMP_PATH, (ClassifierType) arguments[2],
                arguments[3], this.K_FOLDS);
    }

    /**
     * This method creates the selected feature subset based on the fittest
     * individual in the population.
     *
     * @return the array of indices of the selected feature subset
     */
    protected abstract int[] createSelectedFeatureSubset();

    /**
     * {@inheritDoc }
     */
    @Override
    public String validate() {
        if (K_FOLDS > trainSet.length) {
            return "The parameter values of GA-based method (number of folds) are incorred.";
        }
        return "";
    }
}
