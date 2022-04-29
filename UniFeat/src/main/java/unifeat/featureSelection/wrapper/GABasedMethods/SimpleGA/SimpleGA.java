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
package unifeat.featureSelection.wrapper.GABasedMethods.SimpleGA;

import unifeat.featureSelection.wrapper.GABasedMethods.BasicGA;
import unifeat.util.ArraysFunc;
import java.util.ArrayList;

/**
 * This java class is used to implement feature selection method based on simple
 * genetic algorithm (Simple GA) in which the type of Population is extended
 * from Population class.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.GABasedMethods.BasicGA
 * @see unifeat.featureSelection.wrapper.WrapperApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public class SimpleGA extends BasicGA<Population> {

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
    public SimpleGA(Object... arguments) {
        super(arguments);
        population = new Population();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected int[] createSelectedFeatureSubset() {
        ArrayList<Integer> featSubset = new ArrayList();
        population.evaluateFitness();
        Individual fittestIndividual = population.getFittestIndividual();

        for (int i = 0; i < Population.PROBLEM_DIMENSION; i++) {
            if (fittestIndividual.genes[i]) {
                featSubset.add(i);
            }
        }
        return featSubset.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Starts the feature selection process by simple genetic algorithm (Simple
     * GA) method
     */
    @Override
    public void evaluateFeatures() {
        Population.fitnessEvaluator.createTempDirectory();
        Population.fitnessEvaluator.setDataInfo(trainSet, nameFeatures, classLabel);
        population.initialization();
        for (int i = 0; i < NUM_ITERATION; i++) {
            System.out.println("\nIteration " + i + ":\n\n");
            population.evaluateFitness();
            population.operateSelection();
            population.operateCrossOver();
            population.operateMutation();
            population.operateGenerationReplacement();
        }

        selectedFeatureSubset = createSelectedFeatureSubset();
        numSelectedFeature = selectedFeatureSubset.length;

        ArraysFunc.sortArray1D(selectedFeatureSubset, false);

//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }

        Population.fitnessEvaluator.deleteTempDirectory();
    }
}
