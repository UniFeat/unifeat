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
package unifeat.featureSelection.wrapper.PSOBasedMethods.BPSO;

import unifeat.featureSelection.wrapper.PSOBasedMethods.BasicPSO;
import unifeat.util.ArraysFunc;
import java.util.ArrayList;

/**
 * This java class is used to implement feature selection method based on binary
 * particle swarm optimization (BPSO) in which the type of Swarm is extended
 * from Swarm class.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.PSOBasedMethods.BasicPSO
 * @see unifeat.featureSelection.wrapper.WrapperApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public class BPSO extends BasicPSO<Swarm> {

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
    public BPSO(Object... arguments) {
        super(arguments);
        swarm = new Swarm();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected int[] createSelectedFeatureSubset() {
        ArrayList<Integer> featSubset = new ArrayList();
        for (int i = 0; i < Swarm.PROBLEM_DIMENSION; i++) {
            if (swarm.getGBest()[i]) {
                featSubset.add(i);
            }
        }
        return featSubset.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Starts the feature selection process by binary particle swarm
     * optimization (BPSO) method
     */
    @Override
    public void evaluateFeatures() {
        Swarm.fitnessEvaluator.createTempDirectory();
        Swarm.fitnessEvaluator.setDataInfo(trainSet, nameFeatures, classLabel);
        swarm.initialization();
        for (int i = 0; i < NUM_ITERATION; i++) {
            System.out.println("\nIteration " + i + ":\n\n");
            swarm.evaluateFitness();
            swarm.updatePersonalBest();
            swarm.updateGlobalBest();
            swarm.updateParticleVelocity();
            swarm.updateParticlePosition();
        }

        selectedFeatureSubset = createSelectedFeatureSubset();
        numSelectedFeature = selectedFeatureSubset.length;

        ArraysFunc.sortArray1D(selectedFeatureSubset, false);

//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }

        Swarm.fitnessEvaluator.deleteTempDirectory();
    }
}
