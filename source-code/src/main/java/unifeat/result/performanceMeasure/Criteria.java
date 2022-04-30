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
package unifeat.result.performanceMeasure;

/**
 * This java class is used to set different criteria values used in the feature
 * selection area of research.
 *
 * @author Sina Tabakhi
 */
public class Criteria {

    private double accuracy;
    private double errorRate;
    private double time;

    /**
     * Initializes the parameters
     */
    public Criteria() {
        accuracy = 0;
        errorRate = 0;
        time = 0;
    }

    /**
     * This method sets the accuracy value.
     *
     * @param accuracy the accuracy value
     */
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * This method returns the accuracy.
     *
     * @return the <code>accuracy</code> value
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * This method sets the error rate value.
     *
     * @param errorRate the error rate value
     */
    public void setErrorRate(double errorRate) {
        this.errorRate = errorRate;
    }

    /**
     * This method returns the error rate.
     *
     * @return the <code>error rate</code> value
     */
    public double getErrorRate() {
        return errorRate;
    }

    /**
     * This method sets the time value.
     *
     * @param time the time value
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * This method returns the time.
     *
     * @return the <code>time</code> value
     */
    public double getTime() {
        return time;
    }
}
