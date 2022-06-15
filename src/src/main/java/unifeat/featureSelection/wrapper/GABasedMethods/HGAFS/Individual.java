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
package unifeat.featureSelection.wrapper.GABasedMethods.HGAFS;

import unifeat.featureSelection.wrapper.GABasedMethods.BasicIndividual;
import java.util.ArrayList;

/**
 * This java class is used to represent an individual in simple genetic
 * algorithm (Simple GA) method in which the type of gene vector is boolean.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.GABasedMethods.BasicIndividual
 */
public class Individual extends BasicIndividual<Boolean> {

    /**
     * Initializes the parameters
     *
     * @param dimension the dimension of problem which equals to total number of
     * features in the dataset
     */
    public Individual(int dimension) {
        super(Boolean.class, dimension);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int numSelectedFeatures() {
        int count = 0;
        for (Boolean x : this.genes) {
            if (x) {
                count++;
            }
        }
        return count;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int[] selectedFeaturesSubset() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < this.genes.length; i++) {
            if (this.genes[i]) {
                list.add(i);
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }
}
