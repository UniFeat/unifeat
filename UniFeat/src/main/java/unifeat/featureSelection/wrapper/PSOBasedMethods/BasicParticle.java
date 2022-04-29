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

import java.lang.reflect.Array;

/**
 * The abstract class contains the main methods and fields that are used in all
 * PSO-based feature selection methods. This class is used to represent a
 * particle in PSO algorithm.
 *
 * @param <PosType> the type of position vector of each particle
 * @param <VelType> the type of velocity vector of each particle
 *
 * @author Sina Tabakhi
 */
public abstract class BasicParticle<PosType, VelType> {

    public PosType[] position;
    public VelType[] velocity;
    private double fitness;
    public PosType[] pBest;
    private double pBestFitness;

    /**
     * Initializes the parameters
     *
     * @param pos the type of position vector of each particle
     * @param vel the type of velocity vector of each particle
     * @param dimension the dimension of problem which equals to total number of
     * features in the dataset
     */
    public BasicParticle(Class<PosType> pos, Class<VelType> vel, Integer dimension) {
        position = (PosType[]) Array.newInstance(pos, dimension);
        velocity = (VelType[]) Array.newInstance(vel, dimension);
        pBest = (PosType[]) Array.newInstance(pos, dimension);
        pBestFitness = -1;
    }

    /**
     * This method returns the number of selected features by the particle based
     * on position vector.
     *
     * @return number of selected features by the particle
     */
    public abstract int numSelectedFeatures();

    /**
     * This method returns the indices of selected features by the particle
     * based on position vector.
     *
     * @return the array of indices of the selected features by the particle
     */
    public abstract int[] selectedFeaturesSubset();

    /**
     * This method returns the fitness value of the particle.
     *
     * @return the fitness value of the particle
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * This method sets the fitness value of the particle.
     *
     * @param fitness the fitness value of the particle
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * This method returns the fitness value of best position of the particle
     * (personal best)
     *
     * @return the fitness value of best position of the particle
     */
    public double getPBestFitness() {
        return pBestFitness;
    }

    /**
     * This method sets the fitness value of best position of the particle
     * (personal best)
     *
     * @param pBestFitness the fitness value of best position of the particle
     */
    public void setPBestFitness(double pBestFitness) {
        this.pBestFitness = pBestFitness;
    }

//    public void showPosition() {
//        for (PosType entry : position) {
//            System.out.print(entry + ",");
//        }
//        System.out.println("          = " + fitness);
//    }
//
//    public void showPBestPosition() {
//        for (PosType entry : pBest) {
//            System.out.print(entry + ",");
//        }
//        System.out.println("          = " + pBestFitness);
//    }
//
//    public void showVelocity() {
//        for (VelType entry : velocity) {
//            System.out.print(entry + ",");
//        }
//        System.out.println();
//    }
}
