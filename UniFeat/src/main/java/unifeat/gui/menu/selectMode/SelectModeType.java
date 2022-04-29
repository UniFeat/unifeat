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
package unifeat.gui.menu.selectMode;

import unifeat.featureSelection.EnumType;

/**
 * This java class is used to define the names of select mode used in the result
 *
 * @author Sina Tabakhi
 * @see unifeat.featureSelection.EnumType
 */
public class SelectModeType extends EnumType {

    public static final SelectModeType NONE = new SelectModeType("none");
    public static final SelectModeType AVERAGE = new SelectModeType("Average");
    public static final SelectModeType TOTAL = new SelectModeType("Total");

    /**
     * Creates new SelectModeType. This method is called from within the
     * constructor to initialize the parameter.
     *
     * @param name the name of select mode
     */
    private SelectModeType(String name) {
        super(name);
    }

    /**
     * Returns the names of select mode
     *
     * @return an array of names of select mode
     */
    public static String[] asList() {
        return new String[]{AVERAGE.toString(),
            TOTAL.toString()};
    }

    /**
     * Converts the select mode name to SelectModeType
     *
     * @param type the name of select mode as string
     *
     * @return the select mode type
     */
    public static SelectModeType parse(String type) {
        return switch (type) {
            case "Average" -> AVERAGE;
            case "Total" -> TOTAL;
            default -> NONE;
        };
    }
}
