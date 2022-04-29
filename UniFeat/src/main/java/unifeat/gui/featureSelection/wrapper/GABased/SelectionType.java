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
 * selection.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public final class SelectionType extends EnumType {

    public static final SelectionType NONE = new SelectionType("none", 0);
    public static final SelectionType FITNESS_PROPORTIONAL_SELECTION = new SelectionType("Fitness proportional selection", 1);
    public static final SelectionType RANK_BASED_SELECTION = new SelectionType("Rank-based selection", 2);

    /**
     * Creates new SelectionType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of selection
     */
    private SelectionType(String name) {
        super(name);
    }

    /**
     * Creates new SelectionType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of selection
     * @param value the value of selection
     */
    private SelectionType(String name, int value) {
        super(name, value);
    }

    /**
     * Returns the names of different selection
     *
     * @return an array of names of selection
     */
    public static String[] asList() {
        return new String[]{NONE.toString(),
            FITNESS_PROPORTIONAL_SELECTION.toString(),
            RANK_BASED_SELECTION.toString()};
    }

    /**
     * Returns the common names of different selection
     *
     * @return an array of common names of selection
     */
    public static String[] commonAsList() {
        return new String[]{NONE.toString(),
            FITNESS_PROPORTIONAL_SELECTION.toString(),
            RANK_BASED_SELECTION.toString()};
    }

    /**
     * Converts the selection name to SelectionType
     *
     * @param type the name of selection as string
     *
     * @return the selection type
     */
    public static SelectionType parse(String type) {
        return switch (type) {
            case "Fitness proportional selection" -> FITNESS_PROPORTIONAL_SELECTION;
            case "Rank-based selection" -> RANK_BASED_SELECTION;
            default -> NONE;
        };
    }
}
