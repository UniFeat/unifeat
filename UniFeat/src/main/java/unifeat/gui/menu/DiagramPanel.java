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
package unifeat.gui.menu;

import unifeat.util.MathFunc;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
//import javax.swing.UIManager;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
//import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show the 2-D diagrams of the input
 * values
 *
 * @author Sina Tabakhi
 */
public class DiagramPanel extends JPanel implements ActionListener, MouseMotionListener {

    JFrame f;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem mi_save;
    JMenuItem mi_saveAs;
    JMenuItem mi_close;
    JTextField txt_info;
    //----------------------------------------------
    private final String PATH_PROJECT;
    private final double[][] LIST_ITERATION;
    private final int[] LIST_CASES;
    private ArrayList<TupleValues> axisValues;
    private final String NAME_MAIN_PANEL;
    private final String NAME_DIAGRAM;
    private final String NAME_Y_AXIS;
    private final String NAME_LEGEND_LABEL;
    private int intervalYDimension = 7;
    private int maxValue;
    private int minValue;
    private double tempMaxValue;
    private double tempMinValue;
    private final int X_DIMENSION_START = 80;
    private final int X_DIMENSION_END = 380;
    private final int Y_DIMENSION_START = 60;
    private final int Y_DIMENSION_END = 280;
    private final int WIDTH_LEGEND = 100;
    private final int X_D_START_LEGEND = X_DIMENSION_END + 20;
    private final int Y_D_START_LEGEND = Y_DIMENSION_START + 10;
    private final Color[] COLOR_SET = {new Color(192, 0, 0), //red color
        new Color(68, 114, 196), //blue color
        new Color(112, 173, 71), //green color
        new Color(255, 192, 0), //yellow color
        new Color(128, 128, 192), //purple color
        new Color(0, 0, 0), //black color
        new Color(0, 128, 128), //light green color
        new Color(128, 255, 255), //light blue color
        new Color(255, 128, 64), //orange color
        new Color(250, 50, 50)}; //light red color

    /**
     * Creates new form DiagramPanel. This method is called from within the
     * constructor to initialize the form.
     *
     * @param arrayIteration the results of all iteration
     * @param arrayCases the all different cases
     * @param namePanel the name of main panel
     * @param name the name of diagram
     * @param nameY the name of Y-axis
     * @param nameLegend the name of legend
     * @param path the path of the workspace
     */
    public DiagramPanel(double[][] arrayIteration, int[] arrayCases, String namePanel, String name, String nameY, String nameLegend, String path) {
        //sets the initial values
        LIST_ITERATION = arrayIteration;
        LIST_CASES = arrayCases;
        NAME_MAIN_PANEL = namePanel;
        NAME_DIAGRAM = name;
        NAME_Y_AXIS = nameY;
        NAME_LEGEND_LABEL = nameLegend;
        PATH_PROJECT = path;
        axisValues = new ArrayList<>();
        findMaxValue();
        findMinValue();
        maxValue = (int) (tempMaxValue);
        if (tempMaxValue - maxValue > 0) {
            maxValue++;
        }
        minValue = (int) tempMinValue;
        if (maxValue - minValue == 0) {
            if (maxValue < 99) {
                maxValue++;
            } else {
                minValue--;
            }
        }
        if ((maxValue - minValue) <= intervalYDimension) {
            intervalYDimension = maxValue - minValue;
        } else {
            int res = maxValue - minValue;
            double res1 = res / 6.0;
            double res2 = res / 5.0;
            if (res1 == (int) res1) {
                intervalYDimension = 6;
            } else if (res2 == (int) res2) {
                intervalYDimension = 5;
            } else {
                if (((res2 - (int) res2) >= 0.5) && ((res2 - (int) res2) > (res1 - (int) res1))) {
                    intervalYDimension = 5;
                } else {
                    intervalYDimension = 6;
                }
            }
        }

        //create the menu bar in the main panel
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        mi_save = new JMenuItem("Save");
        mi_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        mi_save.addActionListener(this);
        mi_saveAs = new JMenuItem("Save as");
        mi_saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        mi_saveAs.addActionListener(this);
        mi_close = new JMenuItem("Close");
        mi_close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        mi_close.addActionListener(this);
        txt_info = new JTextField("");
        txt_info.setBounds(60, 67, 80, 22);
        txt_info.setBackground(new Color(242, 242, 242));
        txt_info.setVisible(false);
        txt_info.setEditable(false);

        fileMenu.add(mi_save);
        fileMenu.add(mi_saveAs);
        fileMenu.add(mi_close);
        menuBar.add(fileMenu);
        menuBar.setBounds(0, 0, 530, 22);
        setBackground(Color.white);

        f = new JFrame(NAME_MAIN_PANEL);
        f.add(menuBar);
        f.add(txt_info);
        f.add(this);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setIconImage(new ImageIcon(getClass().getResource("/unifeat/gui/icons/small_logo.png")).getImage());
        f.setSize(530, 370);
        f.addMouseMotionListener(this);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
    }

    /**
     * Finds the maximum value of the input array
     */
    private void findMaxValue() {
        tempMaxValue = LIST_ITERATION[0][0];
        for (int i = 0; i < LIST_ITERATION.length; i++) {
            for (int j = 0; j < LIST_ITERATION[0].length; j++) {
                if (LIST_ITERATION[i][j] > tempMaxValue) {
                    tempMaxValue = LIST_ITERATION[i][j];
                }
            }
        }
    }

    /**
     * Finds the minimum value of the input array
     */
    private void findMinValue() {
        tempMinValue = LIST_ITERATION[0][0];
        for (int i = 0; i < LIST_ITERATION.length; i++) {
            for (int j = 0; j < LIST_ITERATION[0].length; j++) {
                if (LIST_ITERATION[i][j] < tempMinValue) {
                    tempMinValue = LIST_ITERATION[i][j];
                }
            }
        }
    }

    /**
     * This method is used to show the diagram.
     * 
     * @param g the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        axisValues = new ArrayList<TupleValues>();

        /**
         * draw framework of the diagram
         */
        g.drawLine(X_DIMENSION_START, Y_DIMENSION_START - 1, X_DIMENSION_END, Y_DIMENSION_START - 1); //draw up horizontal line(-)
        g.drawLine(X_DIMENSION_END, Y_DIMENSION_START, X_DIMENSION_END, Y_DIMENSION_END); //draw right vertical line(|)
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(X_DIMENSION_START, Y_DIMENSION_END, X_DIMENSION_END, Y_DIMENSION_END); //draw down horizontal line(-)
        g2d.drawLine(X_DIMENSION_START, Y_DIMENSION_START, X_DIMENSION_START, Y_DIMENSION_END); //draw left vertical line(|)
        g2d.setStroke(new BasicStroke(1));

        /**
         * draw labels of diagram
         */
        //x-axis title
        g.setFont(new Font("Tahoma", Font.BOLD, 13));
        g.drawString("number of features", X_DIMENSION_START + ((X_DIMENSION_END - X_DIMENSION_START) / 4), Y_DIMENSION_END + 50);
        //title diagram
        g.setFont(new Font("Times New Roman", Font.BOLD, 22));
        g.drawString(NAME_DIAGRAM, 160, 45);
        //y-axis title
        g2d.rotate(Math.toRadians(270), 10, 10);
        g2d.setFont(new Font("Tahoma", Font.BOLD, 13));
        g2d.drawString(NAME_Y_AXIS, -(Y_DIMENSION_END - (Y_DIMENSION_START / 2)), 30);
        g2d.rotate(Math.toRadians(90), 10, 10);

        /**
         * draw x-axis gridline and value(-)
         */
        g2d.setFont(new Font("Tahoma", Font.PLAIN, 11));
        g2d.setStroke(
                new BasicStroke(1, //width
                BasicStroke.CAP_SQUARE, //end cap
                BasicStroke.JOIN_MITER, //join style
                1, //miter limit
                new float[]{3, 3}, //dash pattern
                0.0f));
        g2d.setColor(new Color(242, 242, 242));
        //draw x-axis value labels
        int cutPoint2 = (X_DIMENSION_END - X_DIMENSION_START) / (LIST_CASES.length + 1);
        int maskCutPoint2 = cutPoint2;
        for (int i = 0; i < LIST_CASES.length; i++) {
            int XPosition = X_DIMENSION_START + maskCutPoint2;
            maskCutPoint2 += cutPoint2;
            //draw vertical gridline (|)
            g2d.drawLine(XPosition, Y_DIMENSION_END - 2, XPosition, Y_DIMENSION_START + 1);
        }

        g2d.setColor(Color.black);
        maskCutPoint2 = cutPoint2;
        for (int i = 0; i < LIST_CASES.length; i++) {
            int XPosition = X_DIMENSION_START + maskCutPoint2;
            maskCutPoint2 += cutPoint2;
            g2d.drawLine(XPosition, Y_DIMENSION_END + 4, XPosition, Y_DIMENSION_END); //draw small vertical line(|)
            g2d.drawString(String.valueOf(LIST_CASES[i]), XPosition - 3, Y_DIMENSION_END + 22); //draw label small vertical line(|)
        }

        /**
         * draw y-axis gridline and value(-)
         */
        g2d.setColor(new Color(242, 242, 242));
        g2d.setStroke(
                new BasicStroke(1, //width
                BasicStroke.CAP_SQUARE, //end cap
                BasicStroke.JOIN_MITER, //join style
                1, //miter limit
                new float[]{3, 3}, //dash pattern
                0.0f));                //dash phase
        double cutPoint1 = ((Y_DIMENSION_END - 20) - Y_DIMENSION_START) / (intervalYDimension);
        double maskCutPoint1 = cutPoint1;
        for (int i = 0; i < intervalYDimension; i++) {
            int yPosition = (int) (Y_DIMENSION_END - maskCutPoint1);
            maskCutPoint1 += cutPoint1;
            //draw horizontal gridline (-)
            g2d.drawLine(X_DIMENSION_START + 1, yPosition, X_DIMENSION_END, yPosition);
        }

        g2d.setColor(Color.black);
        maskCutPoint1 = 0;
        int interval = (int) Math.round((maxValue - minValue) / (double) intervalYDimension);
        for (int i = 0; i <= (intervalYDimension); i++) {
            int yPosition = (int) (Y_DIMENSION_END - maskCutPoint1);
            maskCutPoint1 += cutPoint1;
            g2d.drawLine(X_DIMENSION_START, yPosition, X_DIMENSION_START - 5, yPosition); //draw small horizontal line(-)
            g2d.drawString(String.valueOf(minValue + (interval * i)), X_DIMENSION_START - 22, yPosition + 3); //Draw label horizontal line(-)
        }

        /**
         *  sets data point values
         */
        g2d.setStroke(new BasicStroke(2));
        double yDimension = ((Y_DIMENSION_END - 20.0) - Y_DIMENSION_START) / intervalYDimension;
        for (int i = 0; i < LIST_ITERATION.length; i++) {
            maskCutPoint2 = cutPoint2;
            g2d.setColor(COLOR_SET[i]);
            int XPosition = X_DIMENSION_START + maskCutPoint2;
            int YPosition = (int) (((LIST_ITERATION[i][0] - minValue) / interval) * yDimension);
            Point key = new Point(XPosition - 3, Y_DIMENSION_END - YPosition - 3);
            axisValues.add(new TupleValues(key, new Point(i, 0)));
            g2d.fillRect(key.x, key.y, 8, 8);

            for (int j = 1; j < LIST_CASES.length; j++) {
                maskCutPoint2 += cutPoint2;
                int XNewPosition = X_DIMENSION_START + maskCutPoint2;
                int YNewPosition = (int) (((LIST_ITERATION[i][j] - minValue) / interval) * yDimension);
                key = new Point(XNewPosition - 3, Y_DIMENSION_END - YNewPosition - 3);
                axisValues.add(new TupleValues(key, new Point(i, j)));
                g2d.fillRect(key.x, key.y, 8, 8);
                g2d.drawLine(XPosition, Y_DIMENSION_END - YPosition, XNewPosition, Y_DIMENSION_END - YNewPosition);
                XPosition = XNewPosition;
                YPosition = YNewPosition;
            }
        }

        /**
         * draw legend
         */
        //draw framework of legend
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.black);
        g2d.drawLine(X_D_START_LEGEND, Y_DIMENSION_START, X_D_START_LEGEND + WIDTH_LEGEND + 5, Y_DIMENSION_START); //draw up horizontal line(-)

        //draw String title
        g2d.setFont(new Font("Tahoma", Font.PLAIN, 12));
        int height = 20;
        for (int i = 0; i < LIST_ITERATION.length; i++) {
            g2d.drawString(NAME_LEGEND_LABEL + "(" + (i + 1) + ")", X_D_START_LEGEND + 35, Y_D_START_LEGEND + (height * i) + 4); //draw String
        }

        //draw sign and square
        g2d.setStroke(new BasicStroke(2));
        int maskHeight = 0;
        for (int i = 0; i < LIST_ITERATION.length; i++) {
            g2d.setColor(COLOR_SET[i]);
            g2d.drawLine(X_D_START_LEGEND + 4, Y_D_START_LEGEND + maskHeight, X_D_START_LEGEND + 30, Y_D_START_LEGEND + maskHeight); //draw sign
            g2d.fillRect(X_D_START_LEGEND + 13, Y_D_START_LEGEND + maskHeight - 4, 8, 8); //draw square
            maskHeight += height;
        }

        //draw other framework of legend
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.black);
        int EndLegend = (LIST_ITERATION.length * height) + Y_D_START_LEGEND - (height / 2);
        g2d.drawLine(X_D_START_LEGEND + WIDTH_LEGEND + 5, Y_DIMENSION_START, X_D_START_LEGEND + WIDTH_LEGEND + 5, EndLegend); //draw right vertical line(|)
        g2d.drawLine(X_D_START_LEGEND, EndLegend, X_D_START_LEGEND + WIDTH_LEGEND + 5, EndLegend); //draw down horizontal line(-)
        g2d.drawLine(X_D_START_LEGEND, Y_DIMENSION_START, X_D_START_LEGEND, EndLegend); //draw left vertical line(|)
    }

    /**
     * The listener method for receiving action events.
     * Invoked when an action occurs.
     *
     * @param e an action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(mi_save)) {
            mi_saveActionPerformed(e);
        } else if (e.getSource().equals(mi_saveAs)) {
            mi_saveAsActionPerformed(e);
        } else if (e.getSource().equals(mi_close)) {
            mi_closeActionPerformed(e);
        }
    }

    /**
     * This method sets an action for the mi_save button.
     *
     * @param e an action event
     */
    private void mi_saveActionPerformed(ActionEvent e) {
        Container c = f.getContentPane();
        BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
        c.paint(im.getGraphics());
        BufferedImage im2 = im.getSubimage(0, 25, c.getWidth(), c.getHeight() - 25);
        try {
            ImageIO.write(im2, "png", new File(PATH_PROJECT + "diagram.png"));
        } catch (IOException ex) {
            Logger.getLogger(DiagramPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method sets an action for the mi_saveAs button.
     *
     * @param e an action event
     */
    private void mi_saveAsActionPerformed(ActionEvent e) {
        JFileChooser jfch = new JFileChooser(PATH_PROJECT);
        jfch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (jfch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String newPath = jfch.getSelectedFile().getPath();
            Container c = f.getContentPane();
            BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
            c.paint(im.getGraphics());
            BufferedImage im2 = im.getSubimage(0, 25, c.getWidth(), c.getHeight() - 25);
            try {
                ImageIO.write(im2, "png", new File(newPath + "\\diagram.png"));
            } catch (IOException ex) {
                Logger.getLogger(DiagramPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method sets an action for the mi_close button.
     *
     * @param e an action event
     */
    private void mi_closeActionPerformed(ActionEvent e) {
        f.dispose();
    }

    /**
     * The listener method for receiving mouse motion events on a component.
     * Invoked when a mouse button is pressed on a component and then dragged.
     *
     * @param e an action event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * The listener method for receiving mouse motion events on a component.
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e an action event
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        TupleValues point = new TupleValues(new Point(e.getX() - 6, e.getY() - 28), null);
        Point res = point.get(axisValues);
        if (res != null) {
            txt_info.setBounds(e.getX() + 6, e.getY() - 30, 80, 22);
            //txt_info.setText("Value: " + String.valueOf(LIST_ITERATION[res.x][res.y]));
            txt_info.setText("Value: " + MathFunc.roundDouble(LIST_ITERATION[res.x][res.y], 3));
            txt_info.setVisible(true);
        } else {
            txt_info.setVisible(false);
        }
    }

    /**
     * This java class is used to create a data structure for pair
     * {@code (key, val)} in which <code>key</code> and <code>val</code> are
     * Point data type.
     */
    public class TupleValues {

        Point key;
        Point val;

        /**
         * Constructs and initializes a TupleValues with the specified
         * {@code (key, val)}.
         * 
         * @param key the key of the newly constructed <code>TupleValues</code>
         * @param val the val of the newly constructed <code>TupleValues</code>
         */
        public TupleValues(Point key, Point val) {
            this.key = key;
            this.val = val;
        }

        /**
         * Returns the key of this TupleValues in Point data type.
         * 
         * @return the key of this TupleValues.
         */
        public Point getKey() {
            return this.key;
        }

        /**
         * Returns the val of this TupleValues in Point data type.
         *
         * @return the val of this TupleValues.
         */
        public Point getVal() {
            return this.val;
        }

        /**
         * Determines whether or not two TupleValues are equal in their keys. 
         * Two instances of <code>TupleValues</code> are equal in their keys
         * if the values of their <code>x</code> and <code>y</code> member
         * fields are the same.
         * 
         * @param obj an object to be compared with this <code>TupleValues</code>
         * 
         * @return <code>true</code> if the object to be compared has the same
         *         values; <code>false</code> otherwise.
         */
        public boolean equalsKey(TupleValues obj) {
            return (this.getKey().x == obj.getKey().x)
                    && (this.getKey().y == obj.getKey().y);
        }

        /**
         * Returns the value to which the specified key is saved in the list, or
         * null if this list contains no element for the key.
         * <p>
         * More formally, if this list contains a mapping from a key k to a value
         * v, then this method returns v; otherwise it returns null.
         *
         * @param list the array of <code>TupleValues</code>
         *
         * @return the value to which the specified key is saved in the list, or
         *         null if the key is not exist in the list
         */
        public Point get(ArrayList<TupleValues> list) {
            for (Iterator it = list.listIterator(); it.hasNext();) {
                TupleValues tuple = (TupleValues) it.next();
                if (this.equalsKey(tuple)) {
                    return tuple.getVal();
                }
            }
            return null;
        }
    }

//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            System.out.println("Error setting native LAF: " + e);
//        }
//        double[][] testValues = {{10, 20, 30, 40, 50},
//            {20, 30, 30, 20, 40},
//            {50, 40, 30, 20, 15},
//            {5, 15, 20, 22, 27},
//            {12, 14, 18, 25, 30},
//            {15, 25, 35, 45, 55},
//            {25, 35, 35, 25, 45},
//            {55, 45, 35, 25, 20},
//            {10, 20, 25, 27, 34},
//            {17, 19, 25, 30, 35}};
//
//        double[][] averageTestValues = {{10, 20, 30, 40, 50}};
//        int[] allCases = {10, 20, 30, 40, 50};
//
//        //DiagramPanel digPanel = new DiagramPanel(averageTestValues, allCases, "Average classification error rate diagram", "naive bayes", "Classification Error Rate (%)", "average", "C:\\Users\\ST\\Desktop\\");
//        DiagramPanel app = new DiagramPanel(testValues, allCases, "Classification accuracy diagram", "Decision Tree (C4.5)", "Classification Accuracy (%)", "iteration", "C:\\Users\\ST\\Desktop\\");
//    }
}
