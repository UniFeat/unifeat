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

import unifeat.featureSelection.wrapper.ACOBasedMethods.BasicAnt;
import unifeat.featureSelection.wrapper.ACOBasedMethods.BasicColony;
import java.util.ArrayList;

/**
 * This java class is used to represent an ant in optimal ant colony
 * optimization (Optimal ACO) method.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.ACOBasedMethods.BasicAnt
 */
public class Ant extends BasicAnt {

    /**
     * Counts number of successive steps that an ant is not able to decrease the
     * classification error rate
     */
    private int countSteps;

    /**
     * Initializes the parameters
     */
    public Ant() {
        super();
        countSteps = 0;
    }

    /**
     * This method returns the current count steps of the ant.
     *
     * @return the current count steps of the ant
     */
    public int getCountSteps() {
        return countSteps;
    }

    /**
     * This method sets the count steps of the ant.
     *
     * @param countSteps the input count steps
     */
    public void setCountSteps(int countSteps) {
        this.countSteps = countSteps;
    }

    /**
     * This method increases one step of the count steps of the ant.
     */
    public void increaseCountSteps() {
        countSteps++;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ArrayList<Integer> getFeasibleFeatureSet() {
        ArrayList<Integer> feasibleSet = new ArrayList<>();
        for (int i = 0; i < BasicColony.NUM_ORIGINAL_FEATURE; i++) {
            if (!featureSubset.contains(i)) {
                feasibleSet.add(i);
            }
        }

        return feasibleSet;
    }
}
