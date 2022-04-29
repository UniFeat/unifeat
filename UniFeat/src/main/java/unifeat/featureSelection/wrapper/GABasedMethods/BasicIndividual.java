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

import java.lang.reflect.Array;

/**
 * The abstract class contains the main methods and fields that are used in all
 * GA-based feature selection methods. This class is used to represent a
 * individual in GA algorithm.
 *
 * @param <GeneType> the type of genes of each individual
 *
 * @author Sina Tabakhi
 */
public abstract class BasicIndividual<GeneType> {

    public GeneType[] genes;
    private double fitness;

    /**
     * Initializes the parameters
     *
     * @param gene the type of genes of each individual
     * @param dimension the dimension of problem which equals to total number of
     * features in the dataset
     */
    public BasicIndividual(Class<GeneType> gene, Integer dimension) {
        genes = (GeneType[]) Array.newInstance(gene, dimension);
    }

    /**
     * This method returns the number of selected features by the individual
     * based on its gene vector.
     *
     * @return number of selected features by the individual
     */
    public abstract int numSelectedFeatures();

    /**
     * This method returns the indices of selected features by the individual
     * based on its gene vector.
     *
     * @return the array of indices of the selected features by the individual
     */
    public abstract int[] selectedFeaturesSubset();

    /**
     * This method returns the fitness value of the individual.
     *
     * @return the fitness value of the individual
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * This method sets the fitness value of the individual.
     *
     * @param fitness the fitness value of the individual
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

//    public void showIndividual() {
//        for (GeneType entry : genes) {
//            System.out.print(entry + ",");
//        }
//        System.out.println("          = " + fitness);
//    }
}
