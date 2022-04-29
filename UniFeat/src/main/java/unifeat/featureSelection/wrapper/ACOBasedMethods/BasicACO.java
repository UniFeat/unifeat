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

import unifeat.classifier.ClassifierType;
import unifeat.featureSelection.FitnessEvaluator;
import unifeat.featureSelection.wrapper.WrapperApproach;

/**
 * The abstract class contains the main methods and fields that are used in all
 * ACO-based feature selection methods.
 *
 * @param <ColonyType> the type of colony implemented in ACO algorithm
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.WrapperApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public abstract class BasicACO<ColonyType> extends WrapperApproach {

    protected ColonyType colony;
    protected final int NUM_ITERATION;
    protected final int K_FOLDS;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains (<code>path</code>,
     * <code>numFeatures</code>, <code>classifierType</code>,
     * <code>selectedClassifierPan</code>, <code>numIteration</code>,
     * <code>colonySize</code>, <code>alphaParameter</code>,
     * <code>betaParameter</code>, <code>evaporationRate</code>,
     * <code>kFolds</code>, <code>initPheromone</code>) in which
     * <code><b><i>path</i></b></code> is the path of the project,
     * <code><b><i>numFeatures</i></b></code> is the number of original features
     * in the dataset, <code><b><i>classifierType</i></b></code> is the
     * classifier type for evaluating the fitness of a solution,
     * <code><b><i>selectedClassifierPan</i></b></code> is the selected
     * classifier panel, <code><b><i>numIteration</i></b></code> is the maximum
     * number of allowed iterations that algorithm repeated,
     * <code><b><i>colonySize</i></b></code> is the size of colony of candidate
     * solutions, <code><b><i>alphaParameter</i></b></code> is the alpha
     * parameter used in the state transition rule that shows the relative
     * importance of the pheromone, <code><b><i>betaParameter</i></b></code> is
     * the beta parameter used in the state transition rule that shows the
     * relative importance of heuristic information,
     * <code><b><i>evaporationRate</i></b></code> is the evaporation rate of the
     * pheromone, <code><b><i>kFolds</i></b></code> is the number of equal sized
     * subsamples that is used in k-fold cross validation, and
     * <code><b><i>initPheromone</i></b></code> is the initial value of the
     * pheromone
     */
    public BasicACO(Object... arguments) {
        super((String) arguments[0]);
        BasicColony.NUM_ORIGINAL_FEATURE = (int) arguments[1];
        this.NUM_ITERATION = (int) arguments[4];
        BasicColony.COLONY_SIZE = (int) arguments[5];
        BasicColony.ALPHA = (double) arguments[6];
        BasicColony.BETA = (double) arguments[7];
        BasicColony.RHO = (double) arguments[8];
        this.K_FOLDS = (int) arguments[9];
        BasicColony.INIT_PHEROMONE_VALUE = (double) arguments[10];

        BasicColony.fitnessEvaluator = new FitnessEvaluator(TEMP_PATH, (ClassifierType) arguments[2],
                arguments[3], this.K_FOLDS);
    }

    /**
     * This method creates the selected feature subset based on the best ant in
     * the colony.
     *
     * @return the array of indices of the selected feature subset
     */
    protected abstract int[] createSelectedFeatureSubset();
}
