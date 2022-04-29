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
 * mutation.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public final class MutationType extends EnumType {

    public static final MutationType NONE = new MutationType("none", 0);
    public static final MutationType BITWISE_MUTATION = new MutationType("Bitwise mutation", 1);

    /**
     * Creates new MutationType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of mutation
     */
    private MutationType(String name) {
        super(name);
    }

    /**
     * Creates new MutationType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of mutation
     * @param value the value of mutation
     */
    private MutationType(String name, int value) {
        super(name, value);
    }

    /**
     * Returns the names of different mutation
     *
     * @return an array of names of mutation
     */
    public static String[] asList() {
        return new String[]{NONE.toString(),
            BITWISE_MUTATION.toString()};
    }

    /**
     * Returns the common names of different mutation
     *
     * @return an array of common names of mutation
     */
    public static String[] commonAsList() {
        return new String[]{NONE.toString(),
            BITWISE_MUTATION.toString()};
    }

    /**
     * Converts the mutation name to MutationType
     *
     * @param type the name of mutation as string
     *
     * @return the mutation type
     */
    public static MutationType parse(String type) {
        return switch (type) {
            case "Bitwise mutation" -> BITWISE_MUTATION;
            default -> NONE;
        };
    }
}
