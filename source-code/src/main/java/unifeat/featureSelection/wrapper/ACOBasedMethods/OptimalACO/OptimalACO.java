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
package unifeat.featureSelection.wrapper.ACOBasedMethods.OptimalACO;

import unifeat.featureSelection.wrapper.ACOBasedMethods.BasicACO;
import unifeat.featureSelection.wrapper.ACOBasedMethods.BasicColony;
import unifeat.util.ArraysFunc;

/**
 * This java class is used to implement feature selection method based on
 * optimal ant colony optimization (Optimal ACO) in which the type of Colony is
 * extended from Colony class.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.ACOBasedMethods.BasicACO
 * @see unifeat.featureSelection.wrapper.WrapperApproach
 * @see unifeat.featureSelection.FeatureSelection
 */
public class OptimalACO extends BasicACO<Colony> {

    /**
     * Initializes the parameters
     *
     * @param arguments array of parameters contains (<code>path</code>,
     * <code>numFeatures</code>, <code>classifierType</code>,
     * <code>selectedClassifierPan</code>, <code>numIteration</code>,
     * <code>colonySize</code>, <code>alphaParameter</code>,
     * <code>betaParameter</code>, <code>evaporationRate</code>,
     * <code>kFolds</code>, <code>initPheromone</code>,
     * <code>phiParameter</code>) in which <code><b><i>path</i></b></code> is
     * the path of the project, <code><b><i>numFeatures</i></b></code> is the
     * number of original features in the dataset,
     * <code><b><i>classifierType</i></b></code> is the classifier type for
     * evaluating the fitness of a solution,
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
     * subsamples that is used in k-fold cross validation,
     * <code><b><i>initPheromone</i></b></code> is the initial value of the
     * pheromone, and <code><b><i>phiParameter</i></b></code> is the phi
     * parameter used in the pheromone update rule for controlling the relative
     * weight of classifier performance and feature subset length
     */
    public OptimalACO(Object... arguments) {
        super(arguments);
        BasicColony.graphRepresentation = new GraphRepresentation(1, BasicColony.NUM_ORIGINAL_FEATURE);
        colony = new Colony((double) arguments[11]);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected int[] createSelectedFeatureSubset() {
        Ant betsAnt = colony.getBestAnt();
        return ArraysFunc.convertArrayListToInt(betsAnt.getFeatureSubset());
    }

    /**
     * Starts the feature selection process by optimal ant colony optimization
     * (Optimal ACO) method
     */
    @Override
    public void evaluateFeatures() {
        Colony.fitnessEvaluator.createTempDirectory();
        Colony.fitnessEvaluator.setDataInfo(trainSet, nameFeatures, classLabel);
        colony.initialization();
        for (int i = 0; i < NUM_ITERATION; i++) {
            System.out.println("\nIteration " + i + ":\n\n");
            colony.setInitialState();
            colony.constructSolution();
            colony.operatePheromoneUpdateRule();
        }

        selectedFeatureSubset = createSelectedFeatureSubset();
        numSelectedFeature = selectedFeatureSubset.length;

        ArraysFunc.sortArray1D(selectedFeatureSubset, false);

//        for (int i = 0; i < numSelectedFeature; i++) {
//            System.out.println("ranked  = " + selectedFeatureSubset[i]);
//        }
        Colony.fitnessEvaluator.deleteTempDirectory();
    }
}
