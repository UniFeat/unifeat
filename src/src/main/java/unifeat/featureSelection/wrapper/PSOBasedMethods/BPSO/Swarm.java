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

import unifeat.featureSelection.wrapper.PSOBasedMethods.BasicSwarm;
import unifeat.result.performanceMeasure.Criteria;
import unifeat.util.MathFunc;
import java.util.Arrays;
import java.util.Random;

/**
 * This java class is used to implement a swarm of particles in binary particle
 * swarm optimization (BPSO) method in which the type of position vector is
 * boolean and the type of particle is extended from Particle class.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.PSOBasedMethods.BasicSwarm
 */
public class Swarm extends BasicSwarm<Boolean, Particle> {

    private int seedValue = 0;
    private Random rand = new Random(seedValue);

    /**
     * Initializes the parameters
     */
    public Swarm() {
        super(Boolean.class, Particle.class);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = new Particle(PROBLEM_DIMENSION);
        }
    }

    /**
     * {@inheritDoc }
     * Each particle is randomly initialized in the predefined ranges of values.
     */
    @Override
    public void initialization() {
        for (int par = 0; par < POPULATION_SIZE; par++) {
            for (int dim = 0; dim < PROBLEM_DIMENSION; dim++) {
                population[par].position[dim] = MathFunc.generateRandNum(START_POS_INTERVAL, END_POS_INTERVAL, rand) > 0.5;
                population[par].velocity[dim] = MathFunc.generateRandNum(MIN_VELOCITY, MAX_VELOCITY, rand);
            }

            if (population[par].numSelectedFeatures() == 0) {
                population[par].position[rand.nextInt(PROBLEM_DIMENSION)] = true;
            }
        }
    }

    /**
     * {@inheritDoc }
     * K-fold cross validation on training set is used for evaluating the
     * classification performance of selected feature subset by each particle.
     */
    @Override
    public void evaluateFitness() {
        for (int par = 0; par < POPULATION_SIZE; par++) {
            if (population[par].numSelectedFeatures() > 0) {
                Criteria criteria = fitnessEvaluator.crossValidation(population[par].selectedFeaturesSubset());
                population[par].setFitness(criteria.getAccuracy());
            } else {
                population[par].setFitness(0);
            }
        }
    }

    /**
     * {@inheritDoc }
     * Personal best position is updated when the classification performance of
     * the particle's new position is better than personal best.
     */
    @Override
    public void updatePersonalBest() {
        for (int par = 0; par < POPULATION_SIZE; par++) {
            if (population[par].getFitness() > population[par].getPBestFitness()) {
                population[par].pBest = Arrays.copyOf(population[par].position, PROBLEM_DIMENSION);
                population[par].setPBestFitness(population[par].getFitness());
            }
        }
    }

    /**
     * {@inheritDoc }
     * Global best position is updated when the classification performance of
     * any personal best position of the particles is better than global best.
     */
    @Override
    public void updateGlobalBest() {
        int maxIndex = 0;
        double maxValue = population[0].getPBestFitness();
        for (int par = 1; par < POPULATION_SIZE; par++) {
            if (population[par].getPBestFitness() > maxValue) {
                maxIndex = par;
                maxValue = population[par].getPBestFitness();
            }
        }

        if (maxValue > this.getGBestFitness()) {
            this.setGBest(Arrays.copyOf(population[maxIndex].pBest, PROBLEM_DIMENSION));
            this.setGBestFitness(maxValue);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void updateParticleVelocity() {
        for (int par = 0; par < POPULATION_SIZE; par++) {
            for (int dim = 0; dim < PROBLEM_DIMENSION; dim++) {
                double firstPart = INERTIA_WEIGHT * population[par].velocity[dim];
                double secondPart = C1 * rand.nextDouble() * subtractBoolean(population[par].pBest[dim], population[par].position[dim]);
                double thirdPart = C2 * rand.nextDouble() * subtractBoolean(gBest[dim], population[par].position[dim]);
                population[par].velocity[dim] = firstPart + secondPart + thirdPart;
                if (population[par].velocity[dim] > MAX_VELOCITY) {
                    population[par].velocity[dim] = MAX_VELOCITY;
                } else if (population[par].velocity[dim] < MIN_VELOCITY) {
                    population[par].velocity[dim] = MIN_VELOCITY;
                }
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void updateParticlePosition() {
        for (int par = 0; par < POPULATION_SIZE; par++) {
            for (int dim = 0; dim < PROBLEM_DIMENSION; dim++) {
                population[par].position[dim] = rand.nextDouble() < sigmoidFunc(population[par].velocity[dim]);
            }
        }
    }

    /**
     * This method subtracts two boolean values.
     * <p>
     * If the input value equals to true, the result will be considered as 1,
     * otherwise 0.
     *
     * @param num1 the first input value
     * @param num2 the second input value
     *
     * @return the result of subtraction
     */
    private int subtractBoolean(boolean num1, boolean num2) {
        int intNum1 = num1 ? 1 : 0;
        int intNum2 = num2 ? 1 : 0;
        return intNum1 - intNum2;
    }

    /**
     * This method transforms a input value to the range of (0,1) using sigmoid
     * function.
     *
     * @param value the input value that will be transformed
     *
     * @return the transformed value of the input value
     */
    private double sigmoidFunc(double value) {
        return 1.0 / (1.0 + Math.pow(Math.E, -value));
    }
}
