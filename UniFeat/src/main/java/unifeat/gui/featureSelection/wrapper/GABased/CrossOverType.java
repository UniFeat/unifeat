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
package unifeat.gui.featureSelection.wrapper.GABased;

import unifeat.featureSelection.EnumType;

/**
 * This java class is used to define the names of different implementation of
 * crossover.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public final class CrossOverType extends EnumType {

    public static final CrossOverType NONE = new CrossOverType("none", 0);
    public static final CrossOverType ONE_POINT_CROSS_OVER = new CrossOverType("One-point crossover", 1);
    public static final CrossOverType TWO_POINT_CROSS_OVER = new CrossOverType("Two-point crossover", 2);
    public static final CrossOverType UNIFORM_CROSS_OVER = new CrossOverType("Uniform crossover", 3);

    /**
     * Creates new CrossOverType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of crossover
     */
    private CrossOverType(String name) {
        super(name);
    }

    /**
     * Creates new CrossOverType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of crossover
     * @param value the value of crossover
     */
    private CrossOverType(String name, int value) {
        super(name, value);
    }

    /**
     * Returns the names of different crossover
     *
     * @return an array of names of crossover
     */
    public static String[] asList() {
        return new String[]{NONE.toString(),
            ONE_POINT_CROSS_OVER.toString(),
            TWO_POINT_CROSS_OVER.toString(),
            UNIFORM_CROSS_OVER.toString()};
    }

    /**
     * Returns the common names of different crossover
     *
     * @return an array of common names of crossover
     */
    public static String[] commonAsList() {
        return new String[]{NONE.toString(),
            ONE_POINT_CROSS_OVER.toString(),
            TWO_POINT_CROSS_OVER.toString(),
            UNIFORM_CROSS_OVER.toString()};
    }

    /**
     * Converts the crossover name to CrossOverType
     *
     * @param type the name of crossover as string
     *
     * @return the crossover type
     */
    public static CrossOverType parse(String type) {
        return switch (type) {
            case "One-point crossover" -> ONE_POINT_CROSS_OVER;
            case "Two-point crossover" -> TWO_POINT_CROSS_OVER;
            case "Uniform crossover" -> UNIFORM_CROSS_OVER;
            default -> NONE;
        };
    }
}
