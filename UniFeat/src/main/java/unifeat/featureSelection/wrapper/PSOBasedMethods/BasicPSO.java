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
package unifeat.featureSelection.wrapper.PSOBasedMethods;

import unifeat.classifier.ClassifierType;
import unifeat.featureSelection.FitnessEvaluator;
import unifeat.featureSelection.wrapper.WrapperApproach;

/**
 * The abstract class contains the main methods and fields that are used in all
 * PSO-based feature selection methods.
 *
 * @param <SwarmType> the type of swarm implemented in PSO algorithm
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.WrapperApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public abstract class BasicPSO<SwarmType> extends WrapperApproach {

    protected SwarmType swarm;
    protected final int NUM_ITERATION;
    protected final int K_FOLDS;

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains ( <code>path</code>,
     * <code>numFeatures</code>, <code>classifierType</code>,
     * <code>selectedClassifierPan</code>, <code>numIteration</code>
     * <code>populationSize</code>, <code>inertiaWeight</code>,
     * <code>parameter c1</code>, <code>parameter c2</code>,
     * <code>startPosInterval</code>, <code>endPosInterval</code>,
     * <code>minVelocity</code>, <code>maxVelocity</code>, <code>kFolds</code>)
     * in which <code><b><i>path</i></b></code> is the path of the project,
     * <code><b><i>numFeatures</i></b></code> is the number of original features
     * in the dataset, <code><b><i>classifierType</i></b></code> is the
     * classifier type for evaluating the fitness of a solution,
     * <code><b><i>selectedClassifierPan</i></b></code> is the selected
     * classifier panel, <code><b><i>numIteration</i></b></code> is the maximum
     * number of allowed iterations that algorithm repeated,
     * <code><b><i>populationSize</i></b></code> is the size of population of
     * candidate solutions, <code><b><i>inertiaWeight</i></b></code> is the
     * inertia weight in the velocity updating rule, <code><b><i>parameter
     * c1</i></b></code> is the acceleration constant in the velocity updating
     * rule, <code><b><i>parameter c2</i></b></code> is the acceleration
     * constant in the velocity updating rule,
     * <code><b><i>startPosInterval</i></b></code> is the position interval
     * start value, <code><b><i>endPosInterval</i></b></code> is the position
     * interval end value, <code><b><i>minVelocity</i></b></code> is the
     * velocity interval start value, <code><b><i>maxVelocity</i></b></code> is
     * the velocity interval end value, and <code><b><i>kFolds</i></b></code> is
     * the number of equal sized subsamples that is used in k-fold cross
     * validation
     */
    public BasicPSO(Object... arguments) {
        super((String) arguments[0]);
        BasicSwarm.PROBLEM_DIMENSION = (int) arguments[1];
        this.NUM_ITERATION = (int) arguments[4];
        BasicSwarm.POPULATION_SIZE = (int) arguments[5];
        BasicSwarm.INERTIA_WEIGHT = (double) arguments[6];
        BasicSwarm.C1 = (double) arguments[7];
        BasicSwarm.C2 = (double) arguments[8];
        BasicSwarm.START_POS_INTERVAL = (double) arguments[9];
        BasicSwarm.END_POS_INTERVAL = (double) arguments[10];
        BasicSwarm.MIN_VELOCITY = (double) arguments[11];
        BasicSwarm.MAX_VELOCITY = (double) arguments[12];
        this.K_FOLDS = (int) arguments[13];
        BasicSwarm.fitnessEvaluator = new FitnessEvaluator(TEMP_PATH, (ClassifierType) arguments[2],
                arguments[3], this.K_FOLDS);
    }

    /**
     * This method creates the selected feature subset based on global best
     * position in the swarm.
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
            return "The parameter values of PSO-based method (number of folds) are incorrect.";
        }
        return "";
    }
}
