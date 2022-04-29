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
package unifeat.featureSelection.wrapper.PSOBasedMethods.HPSO_LS;

import unifeat.featureSelection.wrapper.PSOBasedMethods.BasicParticle;
import java.util.ArrayList;

/**
 * This java class is used to represent a particle in hybrid particle swarm
 * optimization method using local search (HPSO-LS) in which the type of
 * position vector is boolean and the type of velocity vector is double.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.wrapper.PSOBasedMethods.BasicParticle
 */
public class Particle extends BasicParticle<Boolean, Double> {

    /**
     * Initializes the parameters
     *
     * @param dimension the dimension of problem which equals to total number of
     * features in the dataset
     */
    public Particle(int dimension) {
        super(Boolean.class, Double.class, dimension);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int numSelectedFeatures() {
        int count = 0;
        for (Boolean x : this.position) {
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
        for (int i = 0; i < this.position.length; i++) {
            if (this.position[i]) {
                list.add(i);
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }
}
