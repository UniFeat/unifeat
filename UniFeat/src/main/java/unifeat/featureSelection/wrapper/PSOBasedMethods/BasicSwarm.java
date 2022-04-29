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

import unifeat.featureSelection.FitnessEvaluator;
import java.lang.reflect.Array;

/**
 * The abstract class contains the main methods and fields that are used in all
 * PSO-based feature selection methods. This class is used to implement a swarm
 * of particles in PSO algorithm.
 *
 * @param <PosType> the type of position vector of each particle
 * @param <ParType> the type of particle implemented in PSO algorithm
 *
 * @author Sina Tabakhi
 */
public abstract class BasicSwarm<PosType, ParType> {

    protected ParType[] population;
    protected PosType[] gBest;
    private double gBestFitness;
    public static FitnessEvaluator fitnessEvaluator;
    public static int PROBLEM_DIMENSION;
    protected static int POPULATION_SIZE;
    protected static double INERTIA_WEIGHT;
    protected static double C1;
    protected static double C2;
    protected static double START_POS_INTERVAL;
    protected static double END_POS_INTERVAL;
    protected static double MIN_VELOCITY;
    protected static double MAX_VELOCITY;

    /**
     * Initializes the parameters
     *
     * @param pos the type of position vector of each particle
     * @param par the type of particle implemented in PSO algorithm
     */
    public BasicSwarm(Class<PosType> pos, Class<ParType> par) {
        population = (ParType[]) Array.newInstance(par, POPULATION_SIZE);
        gBest = (PosType[]) Array.newInstance(pos, PROBLEM_DIMENSION);
        gBestFitness = -1;
    }

    /**
     * This method initializes the position and velocity vectors of each
     * particle in the swarm.
     */
    public abstract void initialization();

    /**
     * This method evaluates the fitness of each particle in the swarm by
     * predefined fitness function.
     */
    public abstract void evaluateFitness();

    /**
     * This method updates the best position (personal best) of each particle in
     * the swarm.
     */
    public abstract void updatePersonalBest();

    /**
     * This method updates the global best position (global best) of the swarm.
     */
    public abstract void updateGlobalBest();

    /**
     * This method updates the velocity vector of each particle in the swarm.
     */
    public abstract void updateParticleVelocity();

    /**
     * This method updates the position vector of each particle in the swarm.
     */
    public abstract void updateParticlePosition();

    /**
     * This method returns the best position in the swarm (global best)
     *
     * @return the best position in the swarm
     */
    public PosType[] getGBest() {
        return gBest;
    }

    /**
     * This method sets the best position in the swarm (global best)
     *
     * @param gBest the best position in the swarm
     */
    public void setGBest(PosType[] gBest) {
        this.gBest = gBest;
    }

    /**
     * This method returns the fitness value of best position in the swarm
     * (global best)
     *
     * @return the fitness value of best position in the swarm
     */
    public double getGBestFitness() {
        return gBestFitness;
    }

    /**
     * This method sets the fitness value of best position in the swarm (global
     * best)
     *
     * @param gBestFitness the fitness value of best position in the swarm
     */
    public void setGBestFitness(double gBestFitness) {
        this.gBestFitness = gBestFitness;
    }

//    public void showPBestPosition() {
//        for (PosType entry : gBest) {
//            System.out.print(entry + ",");
//        }
//        System.out.println("          = " + gBestFitness);
//    }
}
