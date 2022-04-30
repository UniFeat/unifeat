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

import java.util.Random;

/**
 * This java class is used to implement various crossover operators for
 * recombining the two parents to generate new offsprings
 * <p>
 * The methods in this class is contained brief description of the applications.
 *
 * @author Sina Tabakhi
 */
public class CrossoverOperator {

    /**
     * Changes the values of the two elements in the two arrays identified by
     * index1 and index2
     *
     * @param <GeneType> the type of elements in the input arrays
     * @param parent1 the first input array
     * @param index1 the index of element in the parent1 array that should be
     * changed
     * @param parent2 the second input array
     * @param index2 the index of element in the parent2 array that should be
     * changed
     */
    public static <GeneType> void swap(GeneType[] parent1, int index1, GeneType[] parent2, int index2) {
        GeneType temp = parent1[index1];
        parent1[index1] = parent2[index2];
        parent2[index2] = temp;
    }

    /**
     * Changes the values of the two elements in the two arrays identified by
     * index1 and index2
     *
     * @param parent1 the first input array
     * @param index1 the index of element in the parent1 array that should be
     * changed
     * @param parent2 the second input array
     * @param index2 the index of element in the parent2 array that should be
     * changed
     */
    public static void swap(boolean[] parent1, int index1, boolean[] parent2, int index2) {
        boolean temp = parent1[index1];
        parent1[index1] = parent2[index2];
        parent2[index2] = temp;
    }

    /**
     * Changes the values of the two elements in the two arrays identified by
     * index1 and index2
     *
     * @param parent1 the first input array
     * @param index1 the index of element in the parent1 array that should be
     * changed
     * @param parent2 the second input array
     * @param index2 the index of element in the parent2 array that should be
     * changed
     */
    public static void swap(double[] parent1, int index1, double[] parent2, int index2) {
        double temp = parent1[index1];
        parent1[index1] = parent2[index2];
        parent2[index2] = temp;
    }

    /**
     * Recombines (cross over) the two parents to generate new offsprings using
     * one-point crossover
     *
     * @param <GeneType> the type of elements in the input arrays
     * @param parent1 the first parent
     * @param parent2 the second parent
     */
    public static <GeneType> void onePointCrossover(GeneType[] parent1, GeneType[] parent2) {
        int point = (new Random()).nextInt(parent1.length);
        for (int gene = point; gene < parent1.length; gene++) {
            swap(parent1, gene, parent2, gene);
        }
    }

    /**
     * Recombines (cross over) the two parents to generate new offsprings using
     * one-point crossover
     *
     * @param parent1 the first parent
     * @param parent2 the second parent
     */
    public static void onePointCrossover(boolean[] parent1, boolean[] parent2) {
        int point = (new Random()).nextInt(parent1.length);
        for (int gene = point; gene < parent1.length; gene++) {
            swap(parent1, gene, parent2, gene);
        }
    }

    /**
     * Recombines (cross over) the two parents to generate new offsprings using
     * one-point crossover
     *
     * @param parent1 the first parent
     * @param parent2 the second parent
     */
    public static void onePointCrossover(double[] parent1, double[] parent2) {
        int point = (new Random()).nextInt(parent1.length);
        for (int gene = point; gene < parent1.length; gene++) {
            swap(parent1, gene, parent2, gene);
        }
    }

    /**
     * Recombines (cross over) the two parents to generate new offsprings using
     * two-point crossover
     *
     * @param <GeneType> the type of elements in the input arrays
     * @param parent1 the first parent
     * @param parent2 the second parent
     */
    public static <GeneType> void twoPointCrossover(GeneType[] parent1, GeneType[] parent2) {
        int point1 = (new Random()).nextInt(parent1.length);
        int point2 = (new Random()).nextInt(parent1.length);

        if (point2 < point1) {
            int temp = point1;
            point1 = point2;
            point2 = temp;
        }

        for (int gene = point1; gene < point2; gene++) {
            swap(parent1, gene, parent2, gene);
        }
    }

    /**
     * Recombines (cross over) the two parents to generate new offsprings using
     * two-point crossover
     *
     * @param parent1 the first parent
     * @param parent2 the second parent
     */
    public static void twoPointCrossover(boolean[] parent1, boolean[] parent2) {
        int point1 = (new Random()).nextInt(parent1.length);
        int point2 = (new Random()).nextInt(parent1.length);

        if (point2 < point1) {
            int temp = point1;
            point1 = point2;
            point2 = temp;
        }

        for (int gene = point1; gene < point2; gene++) {
            swap(parent1, gene, parent2, gene);
        }
    }

    /**
     * Recombines (cross over) the two parents to generate new offsprings using
     * two-point crossover
     *
     * @param parent1 the first parent
     * @param parent2 the second parent
     */
    public static void twoPointCrossover(double[] parent1, double[] parent2) {
        int point1 = (new Random()).nextInt(parent1.length);
        int point2 = (new Random()).nextInt(parent1.length);

        if (point2 < point1) {
            int temp = point1;
            point1 = point2;
            point2 = temp;
        }

        for (int gene = point1; gene < point2; gene++) {
            swap(parent1, gene, parent2, gene);
        }
    }

    /**
     * Recombines (cross over) the two parents to generate new offsprings using
     * uniform crossover
     *
     * @param <GeneType> the type of elements in the input arrays
     * @param parent1 the first parent
     * @param parent2 the second parent
     * @param prob the probability of crossover operation
     */
    public static <GeneType> void uniformCrossover(GeneType[] parent1, GeneType[] parent2, double prob) {
        for (int gene = 0; gene < parent1.length; gene++) {
            if (Math.random() <= prob) {
                swap(parent1, gene, parent2, gene);
            }
        }
    }

    /**
     * Recombines (cross over) the two parents to generate new offsprings using
     * uniform crossover
     *
     * @param parent1 the first parent
     * @param parent2 the second parent
     * @param prob the probability of crossover operation
     */
    public static void uniformCrossover(boolean[] parent1, boolean[] parent2, double prob) {
        for (int gene = 0; gene < parent1.length; gene++) {
            if (Math.random() <= prob) {
                swap(parent1, gene, parent2, gene);
            }
        }
    }

    /**
     * Recombines (cross over) the two parents to generate new offsprings using
     * uniform crossover
     *
     * @param parent1 the first parent
     * @param parent2 the second parent
     * @param prob the probability of crossover operation
     */
    public static void uniformCrossover(double[] parent1, double[] parent2, double prob) {
        for (int gene = 0; gene < parent1.length; gene++) {
            if (Math.random() <= prob) {
                swap(parent1, gene, parent2, gene);
            }
        }
    }
}
