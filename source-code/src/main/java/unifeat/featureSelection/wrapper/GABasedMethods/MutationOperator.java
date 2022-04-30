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

/**
 * This java class is used to implement various mutation operators for mutating
 * new offsprings by changing the value of some genes in them.
 * <p>
 * The methods in this class is contained brief description of the applications.
 *
 * @author Sina Tabakhi
 */
public class MutationOperator {

    /**
     * Mutates new offsprings by changing the value of some genes in them using
     * bitwise mutation
     *
     * @param parent the first parent
     * @param prob the probability of mutation operation
     */
    public static void bitwiseMutation(boolean[] parent, double prob) {
        for (int gene = 0; gene < parent.length; gene++) {
            if (Math.random() <= prob) {
                parent[gene] = !parent[gene];
            }
        }
    }

    /**
     * Mutates new offsprings by changing the value of some genes in them using
     * bitwise mutation
     *
     * @param parent the first parent
     * @param prob the probability of mutation operation
     */
    public static void bitwiseMutation(Boolean[] parent, double prob) {
        for (int gene = 0; gene < parent.length; gene++) {
            if (Math.random() <= prob) {
                parent[gene] = !parent[gene];
            }
        }
    }
}
