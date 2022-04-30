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
 * generation replacement.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public final class ReplacementType extends EnumType {

    public static final ReplacementType NONE = new ReplacementType("none", 0);
    public static final ReplacementType TOTAL_REPLACEMENT = new ReplacementType("Total replacement", 1);

    /**
     * Creates new ReplacementType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of replacement
     */
    private ReplacementType(String name) {
        super(name);
    }

    /**
     * Creates new ReplacementType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of replacement
     * @param value the value of replacement
     */
    private ReplacementType(String name, int value) {
        super(name, value);
    }

    /**
     * Returns the names of different replacement
     *
     * @return an array of names of replacement
     */
    public static String[] asList() {
        return new String[]{NONE.toString(),
            TOTAL_REPLACEMENT.toString()};
    }

    /**
     * Returns the common names of different replacement
     *
     * @return an array of common names of replacement
     */
    public static String[] commonAsList() {
        return new String[]{NONE.toString(),
            TOTAL_REPLACEMENT.toString()};
    }

    /**
     * Converts the replacement name to ReplacementType
     *
     * @param type the name of replacement as string
     *
     * @return the replacement type
     */
    public static ReplacementType parse(String type) {
        return switch (type) {
            case "Total replacement" -> TOTAL_REPLACEMENT;
            default -> NONE;
        };
    }
}
