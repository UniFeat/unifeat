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
package unifeat.gui.featureSelection.embedded.decisionTreeBased;

import unifeat.featureSelection.EnumType;

/**
 * This java class is used to define the names of different implementation of
 * Tree.
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public final class TreeType extends EnumType {

    public static final TreeType NONE = new TreeType("none");
    public static final TreeType C45 = new TreeType("C4.5");
    public static final TreeType RANDOM_TREE = new TreeType("Random tree");
    public static final TreeType RANDOM_FOREST = new TreeType("Random forest");

    /**
     * Creates new TreeType. This method is called from within the constructor
     * to initialize the parameter.
     *
     * @param treeName the name of tree
     */
    private TreeType(String treeName) {
        super(treeName);
    }

    /**
     * Returns the names of different tree
     *
     * @return an array of names of tree
     */
    public static String[] asList() {
        return new String[]{C45.toString(),
            RANDOM_TREE.toString(),
            RANDOM_FOREST.toString()};
    }

    /**
     * Converts the tree name to TreeType
     *
     * @param type the name of tree as string
     *
     * @return the tree type
     */
    public static TreeType parse(String type) {
        return switch (type) {
            case "C4.5" -> C45;
            case "Random tree" -> RANDOM_TREE;
            case "Random forest" -> RANDOM_FOREST;
            default -> NONE;
        };
    }
}
