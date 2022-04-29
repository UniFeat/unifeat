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

import java.util.ArrayList;

/**
 * The abstract class contains the main methods and fields that are used in all
 * ACO-based feature selection methods. This class is used to represent an ant
 * in ACO algorithm.
 *
 * @author Sina Tabakhi
 */
public abstract class BasicAnt {

    protected ArrayList<Integer> featureSubset;
    private double fitness;

    /**
     * Initializes the parameters
     */
    public BasicAnt() {
        featureSubset = new ArrayList<>();
        fitness = 0;
    }

    /**
     * This method adds a specific feature to the current selected feature
     * subset by the ant.
     *
     * @param featIndex the index of the feature
     */
    public void addFeature(int featIndex) {
        featureSubset.add(featIndex);
    }

    /**
     * This method removes a specific feature of the current selected feature
     * subset by the ant.
     *
     * @param featIndex the index of the feature
     */
    public void removeFeature(int featIndex) {
        featureSubset.remove(new Integer(featIndex));
    }

    /**
     * This method returns the current selected feature subset by the ant.
     *
     * @return an array list of the selected feature subset by the ant
     */
    public ArrayList<Integer> getFeatureSubset() {
        return featureSubset;
    }

    /**
     * This method returns the feasible features that can be added to the
     * current solution of the ant.
     *
     * @return an array list of the feasible features
     */
    public abstract ArrayList<Integer> getFeasibleFeatureSet();

    /**
     * This method returns the size of current selected feature subset by the
     * ant.
     *
     * @return the size of current selected feature subset
     */
    public int getFeatureSubsetSize() {
        return featureSubset.size();
    }

    /**
     * This method clears the current selected feature subset by the ant.
     */
    public void emptyFeatureSubset() {
        featureSubset.clear();
    }

    /**
     * This method returns the fitness value of the ant.
     *
     * @return the fitness value of the ant
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * This method sets the fitness value of the ant.
     *
     * @param fitness the fitness value of the ant
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * Returns a string representation of the ant.
     *
     * @return a string representation of the ant.
     */
    @Override
    public String toString() {
        String str = "Selected Subset = [ ";
        for (int i = 0; i < getFeatureSubsetSize(); i++) {
            str += featureSubset.get(i) + ",";
        }
        str = str.substring(0, str.length() - 1) + " ]";
        str += "        Fitness = " + getFitness();
        return str;
    }
}
