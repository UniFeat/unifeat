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

import unifeat.gui.ParameterPanel;
import unifeat.util.MathFunc;
import java.awt.Color;
import java.awt.Container;
//import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
//import javax.swing.SwingUtilities;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show a panel for the parameter settings
 * of the decision tree based method.
 *
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.embedded.TreeBasedMethods.TreeBasedMethods
 */
public class DecisionTreeBasedPanel extends ParameterPanel {

    JComboBox cb_treeType;
    JLabel lbl_treeType;
    private TreeType treeType = TreeType.C45;

    //C4.5 components and parameters
    private static final String C45_MORE_OPTION = "Option\n\n"
            + "Confidence factor -> the confidence factor used for pruning (smaller values incur more pruning).\n\n"
            + "MinNumSample -> the minimum number of samples per leaf.\n\n";
    private static final Dimension C45_PANEL_SIZE = new Dimension(480, 360);
    private static final Rectangle C45_OK_BUTTON_POSITION = new Rectangle(120, 270, 75, 25);
    private static final Rectangle C45_MORE_BUTTON_POSITION = new Rectangle(240, 270, 75, 25);
    private JLabel lbl_confidence, lbl_minNum, lbl_confidenceError, lbl_minNumError;
    private JTextField txt_confidence, txt_minNum;
    private static final double DEFAULT_CONFIDENCE = 0.25;
    private static final int DEFAULT_MIN_NUM = 2;
    private double confidence = 0.25;
    private int minNum = 2;

    //Random tree components and parameters
    private static final String RANDOM_TREE_MORE_OPTION = "Option\n\n"
            + "K value -> sets the number of randomly chosen attributes (0 means int(log_2(#predictors) + 1) is used).\n\n"
            + "Max depth -- the maximum depth of the tree (0 means unlimited depth).\n\n"
            + "Min num -- the minimum total weight of the instances in a leaf.\n\n"
            + "Min variance prop -- the minimum proportion of the total variance (over all the data) required for split.\n\n";
    private static final Dimension RANDOM_TREE_PANEL_SIZE = new Dimension(480, 440);
    private static final Rectangle RANDOM_TREE_OK_BUTTON_POSITION = new Rectangle(120, 340, 75, 25);
    private static final Rectangle RANDOM_TREE_MORE_BUTTON_POSITION = new Rectangle(240, 340, 75, 25);
    private JLabel lbl_randomTreeKValue, lbl_randomTreeKValueError,
            lbl_randomTreeMaxDepth, lbl_randomTreeMaxDepthError,
            lbl_randomTreeMinNum, lbl_randomTreeMinNumError,
            lbl_randomTreeMinVarianceProp, lbl_randomTreeMinVariancePropError;
    private JTextField txt_randomTreeKValue, txt_randomTreeMaxDepth, txt_randomTreeMinNum, txt_randomTreeMinVarianceProp;
    private static final int DEFAULT_RANDOM_TREE_K_VALUE = 0, DEFAULT_RANDOM_TREE_MAX_DEPTH = 0;
    private static final double DEFAULT_RANDOM_TREE_MIN_NUM = 1.0, DEFAULT_RANDOM_TREE_MIN_VARIANCE_PROP = 0.001;
    private int randomTreeKValue = 0, randomTreeMaxDepth = 0;
    private double randomTreeMinNum = 1.0, randomTreeMinVarianceProp = 0.001;

    //Random forest components and parameters
    private static final String RANDOM_FOREST_MORE_OPTION = "Option\n\n"
            + "Num features -> the number of randomly selected features (0 means int(log_2(#predictors) + 1) is used).\n\n"
            + "Max depth -- the maximum depth of the tree (0 means unlimited depth).\n\n"
            + "Num iterations -- the number of iterations to be performed.\n\n";
    private static final Dimension RANDOM_FOREST_PANEL_SIZE = new Dimension(480, 410);
    private static final Rectangle RANDOM_FOREST_OK_BUTTON_POSITION = new Rectangle(120, 310, 75, 25);
    private static final Rectangle RANDOM_FOREST_MORE_BUTTON_POSITION = new Rectangle(240, 310, 75, 25);
    private JLabel lbl_randomForestNumFeatures, lbl_randomForestNumFeaturesError,
            lbl_maxDepth, lbl_maxDepthError,
            lbl_randomForestNumIterations, lbl_randomForestNumIterationsError;
    private JTextField txt_randomForestNumFeatures, txt_maxDepth, txt_randomForestNumIterations;
    private static final int DEFAULT_RANDOM_FOREST_NUM_FEATURES = 0, DEFAULT_MAX_DEPTH = 0, DEFAULT_RANDOM_FOREST_NUM_ITERATION = 100;
    private int randomForestNumFeatures = 0, maxDepth = 0, randomForestNumIterations = 100;

    /**
     * Creates new form DecisionTreeBasedPanel. This method is called from
     * within the constructor to initialize the form.
     */
    public DecisionTreeBasedPanel() {
        super("Parameter Settings Panel",
                "Decision tree settings:",
                "<html> In C4.5 and random tree, first of all, the tree will be constructed using a collection of patterns, and then the features"
                + " which are involved in the classification are selected as a final feature subset. </html>",
                C45_MORE_OPTION,
                new Rectangle(10, 10, 140, 20),
                new Rectangle(10, 35, 450, 80),
                C45_OK_BUTTON_POSITION,
                C45_MORE_BUTTON_POSITION,
                C45_PANEL_SIZE);

        Container contentPane = getContentPane();

        lbl_treeType = new JLabel("Tree type:");
        lbl_treeType.setBounds(30, 155, 120, 22);
        cb_treeType = new JComboBox();
        cb_treeType.setModel(new DefaultComboBoxModel(TreeType.asList()));
        cb_treeType.setBounds(130, 155, 200, 25);
        cb_treeType.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                treeType = TreeType.parse(cb_treeType.getSelectedItem().toString());
                removeComponents(treeType);
                addComponents(treeType);
            }
        });

        //C4.5 initial components
        lbl_confidence = new JLabel("Confidence factor:");
        lbl_confidence.setBounds(30, 190, 120, 22);
        txt_confidence = new JTextField(String.valueOf(DEFAULT_CONFIDENCE));
        txt_confidence.setBounds(130, 190, 120, 24);
        txt_confidence.addKeyListener(this);
        lbl_confidenceError = new JLabel("");
        lbl_confidenceError.setBounds(260, 190, 50, 22);
        lbl_confidenceError.setForeground(Color.red);

        lbl_minNum = new JLabel("MinNumSample:");
        lbl_minNum.setBounds(30, 225, 120, 22);
        txt_minNum = new JTextField(Integer.toString(DEFAULT_MIN_NUM));
        txt_minNum.setBounds(130, 225, 120, 24);
        txt_minNum.addKeyListener(this);
        lbl_minNumError = new JLabel("");
        lbl_minNumError.setBounds(260, 225, 50, 22);
        lbl_minNumError.setForeground(Color.red);

        //Random Tree initial components
        lbl_randomTreeKValue = new JLabel("K value:");
        lbl_randomTreeKValue.setBounds(30, 190, 120, 22);
        txt_randomTreeKValue = new JTextField(String.valueOf(DEFAULT_RANDOM_TREE_K_VALUE));
        txt_randomTreeKValue.setBounds(130, 190, 120, 24);
        txt_randomTreeKValue.addKeyListener(this);
        lbl_randomTreeKValueError = new JLabel("");
        lbl_randomTreeKValueError.setBounds(260, 190, 50, 22);
        lbl_randomTreeKValueError.setForeground(Color.red);

        lbl_randomTreeMaxDepth = new JLabel("Max depth:");
        lbl_randomTreeMaxDepth.setBounds(30, 225, 120, 22);
        txt_randomTreeMaxDepth = new JTextField(String.valueOf(DEFAULT_RANDOM_TREE_MAX_DEPTH));
        txt_randomTreeMaxDepth.setBounds(130, 225, 120, 24);
        txt_randomTreeMaxDepth.addKeyListener(this);
        lbl_randomTreeMaxDepthError = new JLabel("");
        lbl_randomTreeMaxDepthError.setBounds(260, 225, 50, 22);
        lbl_randomTreeMaxDepthError.setForeground(Color.red);

        lbl_randomTreeMinNum = new JLabel("MinNum:");
        lbl_randomTreeMinNum.setBounds(30, 260, 120, 22);
        txt_randomTreeMinNum = new JTextField(String.valueOf(DEFAULT_RANDOM_TREE_MIN_NUM));
        txt_randomTreeMinNum.setBounds(130, 260, 120, 24);
        txt_randomTreeMinNum.addKeyListener(this);
        lbl_randomTreeMinNumError = new JLabel("");
        lbl_randomTreeMinNumError.setBounds(260, 260, 50, 22);
        lbl_randomTreeMinNumError.setForeground(Color.red);

        lbl_randomTreeMinVarianceProp = new JLabel("Min Variance Prop:");
        lbl_randomTreeMinVarianceProp.setBounds(30, 295, 120, 22);
        txt_randomTreeMinVarianceProp = new JTextField(String.valueOf(DEFAULT_RANDOM_TREE_MIN_VARIANCE_PROP));
        txt_randomTreeMinVarianceProp.setBounds(130, 295, 120, 24);
        txt_randomTreeMinVarianceProp.addKeyListener(this);
        lbl_randomTreeMinVariancePropError = new JLabel("");
        lbl_randomTreeMinVariancePropError.setBounds(260, 295, 50, 22);
        lbl_randomTreeMinVariancePropError.setForeground(Color.red);

        //Random Forest initial components
        lbl_randomForestNumFeatures = new JLabel("Num features:");
        lbl_randomForestNumFeatures.setBounds(30, 190, 120, 22);
        txt_randomForestNumFeatures = new JTextField(String.valueOf(DEFAULT_RANDOM_FOREST_NUM_FEATURES));
        txt_randomForestNumFeatures.setBounds(130, 190, 120, 24);
        txt_randomForestNumFeatures.addKeyListener(this);
        lbl_randomForestNumFeaturesError = new JLabel("");
        lbl_randomForestNumFeaturesError.setBounds(260, 190, 50, 22);
        lbl_randomForestNumFeaturesError.setForeground(Color.red);

        lbl_maxDepth = new JLabel("Max depth:");
        lbl_maxDepth.setBounds(30, 225, 120, 22);
        txt_maxDepth = new JTextField(Integer.toString(DEFAULT_MAX_DEPTH));
        txt_maxDepth.setBounds(130, 225, 120, 24);
        txt_maxDepth.addKeyListener(this);
        lbl_maxDepthError = new JLabel("");
        lbl_maxDepthError.setBounds(260, 225, 50, 22);
        lbl_maxDepthError.setForeground(Color.red);

        lbl_randomForestNumIterations = new JLabel("Num iterations:");
        lbl_randomForestNumIterations.setBounds(30, 260, 120, 22);
        txt_randomForestNumIterations = new JTextField(Integer.toString(DEFAULT_RANDOM_FOREST_NUM_ITERATION));
        txt_randomForestNumIterations.setBounds(130, 260, 120, 24);
        txt_randomForestNumIterations.addKeyListener(this);
        lbl_randomForestNumIterationsError = new JLabel("");
        lbl_randomForestNumIterationsError.setBounds(260, 260, 50, 22);
        lbl_randomForestNumIterationsError.setForeground(Color.red);

        contentPane.add(lbl_treeType);
        contentPane.add(cb_treeType);
        addComponents(TreeType.C45);

        contentPane.validate();
//        contentPane.revalidate();
        contentPane.repaint();
//        this.pack();
    }

    /**
     * This method sets an action for the btn_ok button.
     *
     * @param e an action event
     */
    @Override
    protected void btn_okActionPerformed(ActionEvent e) {
        treeType = TreeType.parse(cb_treeType.getSelectedItem().toString());
        if (treeType == TreeType.C45) {
            setConfidence(Double.parseDouble(txt_confidence.getText()));
            setMinNum(Integer.parseInt(txt_minNum.getText()));
        } else if (treeType == TreeType.RANDOM_TREE) {
            setRandomTreeKValue(Integer.parseInt(txt_randomTreeKValue.getText()));
            setRandomTreeMaxDepth(Integer.parseInt(txt_randomTreeMaxDepth.getText()));
            setRandomTreeMinNum(Double.parseDouble(txt_randomTreeMinNum.getText()));
            setRandomTreeMinVarianceProp(Double.parseDouble(txt_randomTreeMinVarianceProp.getText()));
        } else if (treeType == TreeType.RANDOM_FOREST) {
            setRandomForestNumFeatures(Integer.parseInt(txt_randomForestNumFeatures.getText()));
            setMaxDepth(Integer.parseInt(txt_maxDepth.getText()));
            setRandomForestNumIterations(Integer.parseInt(txt_randomForestNumIterations.getText()));
        }
        super.btn_okActionPerformed(e);
    }

    /**
     * The listener method for receiving keyboard events (keystrokes). Invoked
     * when a key has been released.
     *
     * @param e an action event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(txt_confidence)
                || e.getSource().equals(txt_minNum)) {
            validate(TreeType.C45);
        } else if (e.getSource().equals(txt_randomTreeKValue)
                || e.getSource().equals(txt_randomTreeMaxDepth)
                || e.getSource().equals(txt_randomTreeMinNum)
                || e.getSource().equals(txt_randomTreeMinVarianceProp)) {
            validate(TreeType.RANDOM_TREE);
        } else if (e.getSource().equals(txt_randomForestNumFeatures)
                || e.getSource().equals(txt_maxDepth)
                || e.getSource().equals(txt_randomForestNumIterations)) {
            validate(TreeType.RANDOM_FOREST);
        }
    }

    /**
     * This method adds components to the main panel based on type of the tree
     *
     * @param tree type of the tree
     */
    private void addComponents(TreeType tree) {
        Container contentPane = getContentPane();
        if (tree == TreeType.C45) {
            setMoreOptionDescription(C45_MORE_OPTION);
            setPanelSize(C45_PANEL_SIZE);
            setOkButtonPosition(C45_OK_BUTTON_POSITION);
            setMoreButtonPosition(C45_MORE_BUTTON_POSITION);

            contentPane.add(lbl_confidence);
            contentPane.add(txt_confidence);
            contentPane.add(lbl_confidenceError);
            contentPane.add(lbl_minNum);
            contentPane.add(txt_minNum);
            contentPane.add(lbl_minNumError);
        } else if (tree == TreeType.RANDOM_TREE) {
            setMoreOptionDescription(RANDOM_TREE_MORE_OPTION);
            setPanelSize(RANDOM_TREE_PANEL_SIZE);
            setOkButtonPosition(RANDOM_TREE_OK_BUTTON_POSITION);
            setMoreButtonPosition(RANDOM_TREE_MORE_BUTTON_POSITION);

            contentPane.add(lbl_randomTreeKValue);
            contentPane.add(txt_randomTreeKValue);
            contentPane.add(lbl_randomTreeKValueError);
            contentPane.add(lbl_randomTreeMaxDepth);
            contentPane.add(txt_randomTreeMaxDepth);
            contentPane.add(lbl_randomTreeMaxDepthError);
            contentPane.add(lbl_randomTreeMinNum);
            contentPane.add(txt_randomTreeMinNum);
            contentPane.add(lbl_randomTreeMinNumError);
            contentPane.add(lbl_randomTreeMinVarianceProp);
            contentPane.add(txt_randomTreeMinVarianceProp);
            contentPane.add(lbl_randomTreeMinVariancePropError);
        } else if (tree == TreeType.RANDOM_FOREST) {
            setMoreOptionDescription(RANDOM_FOREST_MORE_OPTION);
            setPanelSize(RANDOM_FOREST_PANEL_SIZE);
            setOkButtonPosition(RANDOM_FOREST_OK_BUTTON_POSITION);
            setMoreButtonPosition(RANDOM_FOREST_MORE_BUTTON_POSITION);

            contentPane.add(lbl_randomForestNumFeatures);
            contentPane.add(txt_randomForestNumFeatures);
            contentPane.add(lbl_randomForestNumFeaturesError);
            contentPane.add(lbl_maxDepth);
            contentPane.add(txt_maxDepth);
            contentPane.add(lbl_maxDepthError);
            contentPane.add(lbl_randomForestNumIterations);
            contentPane.add(txt_randomForestNumIterations);
            contentPane.add(lbl_randomForestNumIterationsError);
        }
        setDefaultValue(tree);
        validate(tree);

//        contentPane.validate();
//        contentPane.revalidate();
//        contentPane.repaint();
//        this.pack();
    }

    /**
     * This method removes components from the main panel based on type of the
     * tree
     *
     * @param tree type of the tree
     */
    private void removeComponents(TreeType tree) {
        Container contentPane = getContentPane();
        if (tree == TreeType.C45) {
            removeComponentsRandomTree();
            removeComponentsRandomForest();
//            try {
//                removeComponentsRandomTree();
//            } catch (Exception ex) {
//                System.out.println("Random Forest");
//                removeComponentsRandomForest();
//            }
        } else if (tree == TreeType.RANDOM_TREE) {
            removeComponentsC45();
            removeComponentsRandomForest();
//            try {
//                removeComponentsC45();
//            } catch (Exception ex) {
//                System.out.println("Random Forest");
//                removeComponentsRandomForest();
//            }
        } else if (tree == TreeType.RANDOM_FOREST) {
            removeComponentsC45();
            removeComponentsRandomTree();
//            try {
//                removeComponentsC45();
//            } catch (Exception ex) {
//                System.out.println("Random Tree");
//                removeComponentsRandomTree();
//            }
        }
//        contentPane.validate();
//        contentPane.revalidate();
//        contentPane.repaint();
//        this.pack();
    }

    /**
     * This method removes c4.5 components from the main panel
     */
    private void removeComponentsC45() {
        Container contentPane = getContentPane();
        contentPane.remove(lbl_confidence);
        contentPane.remove(txt_confidence);
        contentPane.remove(lbl_confidenceError);
        contentPane.remove(lbl_minNum);
        contentPane.remove(txt_minNum);
        contentPane.remove(lbl_minNumError);
    }

    /**
     * This method removes random tree components from the main panel
     */
    private void removeComponentsRandomTree() {
        Container contentPane = getContentPane();
        contentPane.remove(lbl_randomTreeKValue);
        contentPane.remove(txt_randomTreeKValue);
        contentPane.remove(lbl_randomTreeKValueError);
        contentPane.remove(lbl_randomTreeMaxDepth);
        contentPane.remove(txt_randomTreeMaxDepth);
        contentPane.remove(lbl_randomTreeMaxDepthError);
        contentPane.remove(lbl_randomTreeMinNum);
        contentPane.remove(txt_randomTreeMinNum);
        contentPane.remove(lbl_randomTreeMinNumError);
        contentPane.remove(lbl_randomTreeMinVarianceProp);
        contentPane.remove(txt_randomTreeMinVarianceProp);
        contentPane.remove(lbl_randomTreeMinVariancePropError);
    }

    /**
     * This method removes random forest components from the main panel
     */
    private void removeComponentsRandomForest() {
        Container contentPane = getContentPane();
        contentPane.remove(lbl_randomForestNumFeatures);
        contentPane.remove(txt_randomForestNumFeatures);
        contentPane.remove(lbl_randomForestNumFeaturesError);
        contentPane.remove(lbl_maxDepth);
        contentPane.remove(txt_maxDepth);
        contentPane.remove(lbl_maxDepthError);
        contentPane.remove(lbl_randomForestNumIterations);
        contentPane.remove(txt_randomForestNumIterations);
        contentPane.remove(lbl_randomForestNumIterationsError);
    }

    /**
     * This method checks the status of the text fields due to correct input
     * value
     * 
     * @param tree the type of the tree
     */
    private void validate(TreeType tree) {
        boolean enableOkButton = true;
        String tempStr;

        if (tree == TreeType.C45) {
            tempStr = txt_confidence.getText();
            if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) < 0) {
                lbl_confidenceError.setText("*");
                enableOkButton = false;
            } else {
                lbl_confidenceError.setText("");
            }

            tempStr = txt_minNum.getText();
            if (!MathFunc.isInteger(tempStr) || Integer.parseInt(tempStr) < 0) {
                lbl_minNumError.setText("*");
                enableOkButton = false;
            } else {
                lbl_minNumError.setText("");
            }
        } else if (tree == TreeType.RANDOM_TREE) {
            tempStr = txt_randomTreeKValue.getText();
            if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 0)) {
                lbl_randomTreeKValueError.setText("*");
                enableOkButton = false;
            } else {
                lbl_randomTreeKValueError.setText("");
            }

            tempStr = txt_randomTreeMaxDepth.getText();
            if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 0)) {
                lbl_randomTreeMaxDepthError.setText("*");
                enableOkButton = false;
            } else {
                lbl_randomTreeMaxDepthError.setText("");
            }

            tempStr = txt_randomTreeMinNum.getText();
            if (!MathFunc.isDouble(tempStr) || (Double.parseDouble(tempStr) < 0)) {
                lbl_randomTreeMinNumError.setText("*");
                enableOkButton = false;
            } else {
                lbl_randomTreeMinNumError.setText("");
            }

            tempStr = txt_randomTreeMinVarianceProp.getText();
            if (!MathFunc.isDouble(tempStr) || (Double.parseDouble(tempStr) < 0)) {
                lbl_randomTreeMinVariancePropError.setText("*");
                enableOkButton = false;
            } else {
                lbl_randomTreeMinVariancePropError.setText("");
            }
        } else if (tree == TreeType.RANDOM_FOREST) {
            tempStr = txt_randomForestNumFeatures.getText();
            if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 0)) {
                lbl_randomForestNumFeaturesError.setText("*");
                enableOkButton = false;
            } else {
                lbl_randomForestNumFeaturesError.setText("");
            }

            tempStr = txt_maxDepth.getText();
            if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 0)) {
                lbl_maxDepthError.setText("*");
                enableOkButton = false;
            } else {
                lbl_maxDepthError.setText("");
            }

            tempStr = txt_randomForestNumIterations.getText();
            if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 1)) {
                lbl_randomForestNumIterationsError.setText("*");
                enableOkButton = false;
            } else {
                lbl_randomForestNumIterationsError.setText("");
            }
        }

        btn_ok.setEnabled(enableOkButton);
    }

    /**
     * This method returns the type of the tree.
     *
     * @return the <code>treeType</code> parameter
     */
    public TreeType getTreeType() {
        return treeType;
    }

    /**
     * This method returns the confidence factor value.
     *
     * @return the <code>Confidence factor</code> parameter
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * This method sets the confidence factor value.
     *
     * @param confidence the confidence factor value
     */
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    /**
     * This method returns the minimum number of samples per leaf.
     *
     * @return the <code>MinNumSample</code> parameter
     */
    public int getMinNum() {
        return minNum;
    }

    /**
     * This method sets the minimum number of samples per leaf value.
     *
     * @param minNum the minimum number of samples per leaf value.
     */
    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    /**
     * This method returns the number of randomly chosen attributes.
     *
     * @return the <code>KValue</code> parameter
     */
    public int getRandomTreeKValue() {
        return randomTreeKValue;
    }

    /**
     * This method sets the number of randomly chosen attributes.
     *
     * @param randomTreeKValue the number of randomly chosen attributes
     */
    public void setRandomTreeKValue(int randomTreeKValue) {
        this.randomTreeKValue = randomTreeKValue;
    }

    /**
     * This method returns the maximum depth of the tree.
     *
     * @return the <code>MaxDepth</code> parameter
     */
    public int getRandomTreeMaxDepth() {
        return randomTreeMaxDepth;
    }

    /**
     * This method sets the maximum depth of the tree.
     *
     * @param randomTreeMaxDepth the maximum depth of the tree
     */
    public void setRandomTreeMaxDepth(int randomTreeMaxDepth) {
        this.randomTreeMaxDepth = randomTreeMaxDepth;
    }

    /**
     * This method returns the minimum total weight of the instances in a leaf.
     *
     * @return the <code>MinNum</code> parameter
     */
    public double getRandomTreeMinNum() {
        return randomTreeMinNum;
    }

    /**
     * This method sets the minimum total weight of the instances in a leaf.
     *
     * @param randomTreeMinNum the minimum total weight of the instances in a
     * leaf
     */
    public void setRandomTreeMinNum(double randomTreeMinNum) {
        this.randomTreeMinNum = randomTreeMinNum;
    }

    /**
     * This method returns the minimum proportion of the total variance (over
     * all the data) required for split.
     *
     * @return the <code>MinVarianceProp</code> parameter
     */
    public double getRandomTreeMinVarianceProp() {
        return randomTreeMinVarianceProp;
    }

    /**
     * This method sets the minimum proportion of the total variance (over all
     * the data) required for split.
     *
     * @param randomTreeMinVarianceProp the minimum proportion required for
     * split
     */
    public void setRandomTreeMinVarianceProp(double randomTreeMinVarianceProp) {
        this.randomTreeMinVarianceProp = randomTreeMinVarianceProp;
    }

    /**
     * This method returns the number of randomly selected features.
     *
     * @return the <code>NumFeatures</code> parameter
     */
    public int getRandomForestNumFeatures() {
        return randomForestNumFeatures;
    }

    /**
     * This method sets the number of randomly selected features.
     *
     * @param randomForestNumFeatures The number of randomly selected features
     */
    public void setRandomForestNumFeatures(int randomForestNumFeatures) {
        this.randomForestNumFeatures = randomForestNumFeatures;
    }

    /**
     * This method returns the maximum depth of the tree.
     *
     * @return the <code>maxDepth</code> parameter
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * This method sets the maximum depth of the tree.
     *
     * @param maxDepth The maximum depth of the tree
     */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * This method returns the number of iterations to be performed.
     *
     * @return the <code>NumIterations</code> parameter
     */
    public int getRandomForestNumIterations() {
        return randomForestNumIterations;
    }

    /**
     * This method sets the number of iterations to be performed.
     *
     * @param randomForestNumIterations the number of iterations to be performed
     */
    public void setRandomForestNumIterations(int randomForestNumIterations) {
        this.randomForestNumIterations = randomForestNumIterations;
    }

    /**
     * Sets the default values of the tree parameters
     */
    public void setDefaultValue() {
        setDefaultValue(TreeType.C45);
    }

    /**
     * Sets the default values of the tree parameters
     *
     * @param tree the type of the tree
     */
    public void setDefaultValue(TreeType tree) {
        cb_treeType.setSelectedItem(tree.toString());
        if (tree == TreeType.C45) {
            txt_confidence.setText(String.valueOf(DEFAULT_CONFIDENCE));
            txt_minNum.setText(String.valueOf(DEFAULT_MIN_NUM));
            confidence = DEFAULT_CONFIDENCE;
            minNum = DEFAULT_MIN_NUM;
        } else if (tree == TreeType.RANDOM_TREE) {
            txt_randomTreeKValue.setText(String.valueOf(DEFAULT_RANDOM_TREE_K_VALUE));
            txt_randomTreeMaxDepth.setText(String.valueOf(DEFAULT_RANDOM_TREE_MAX_DEPTH));
            txt_randomTreeMinNum.setText(String.valueOf(DEFAULT_RANDOM_TREE_MIN_NUM));
            txt_randomTreeMinVarianceProp.setText(String.valueOf(DEFAULT_RANDOM_TREE_MIN_VARIANCE_PROP));
            randomTreeKValue = DEFAULT_RANDOM_TREE_K_VALUE;
            randomTreeMaxDepth = DEFAULT_RANDOM_TREE_MAX_DEPTH;
            randomTreeMinNum = DEFAULT_RANDOM_TREE_MIN_NUM;
            randomTreeMinVarianceProp = DEFAULT_RANDOM_TREE_MIN_VARIANCE_PROP;
        } else if (tree == TreeType.RANDOM_FOREST) {
            txt_randomForestNumFeatures.setText(String.valueOf(DEFAULT_RANDOM_FOREST_NUM_FEATURES));
            txt_maxDepth.setText(String.valueOf(DEFAULT_MAX_DEPTH));
            txt_randomForestNumIterations.setText(String.valueOf(DEFAULT_RANDOM_FOREST_NUM_ITERATION));
            randomForestNumFeatures = DEFAULT_RANDOM_FOREST_NUM_FEATURES;
            maxDepth = DEFAULT_MAX_DEPTH;
            randomForestNumIterations = DEFAULT_RANDOM_FOREST_NUM_ITERATION;
        }
    }

    /**
     * Sets the last values of the C4.5 parameters entered by user
     *
     * @param conf the confidence factor
     * @param minSample the minimum number of samples per leaf
     */
    public void setUserValue(double conf, int minSample) {
        this.confidence = conf;
        this.minNum = minSample;
        txt_confidence.setText(String.valueOf(this.confidence));
        txt_minNum.setText(String.valueOf(this.minNum));
    }

    /**
     * Sets the last values of the random tree parameters entered by user.
     *
     * @param kValue The number of randomly chosen attributes
     * @param maxDepth The maximum depth of the tree
     * @param minNum The minimum total weight of the instances in a leaf
     * @param minVarianceProp The minimum proportion of the total variance (over
     * all the data) required for split
     */
    public void setUserValue(int kValue, int maxDepth, double minNum, double minVarianceProp) {
        removeComponents(TreeType.RANDOM_TREE);
        addComponents(TreeType.RANDOM_TREE);
        this.randomTreeKValue = kValue;
        this.randomTreeMaxDepth = maxDepth;
        this.randomTreeMinNum = minNum;
        this.randomTreeMinVarianceProp = minVarianceProp;

        txt_randomTreeKValue.setText(String.valueOf(this.randomTreeKValue));
        txt_randomTreeMaxDepth.setText(String.valueOf(this.randomTreeMaxDepth));
        txt_randomTreeMinNum.setText(String.valueOf(this.randomTreeMinNum));
        txt_randomTreeMinVarianceProp.setText(String.valueOf(this.randomTreeMinVarianceProp));
    }

    /**
     * Sets the last values of the random forest parameters entered by user.
     *
     * @param numFeatures The number of randomly selected features
     * @param maxDepth The maximum depth of the tree
     * @param numIterations The number of iterations to be performed
     */
    public void setUserValue(int numFeatures, int maxDepth, int numIterations) {
        removeComponents(TreeType.RANDOM_FOREST);
        addComponents(TreeType.RANDOM_FOREST);
        this.randomForestNumFeatures = numFeatures;
        this.maxDepth = maxDepth;
        this.randomForestNumIterations = numIterations;

        txt_randomForestNumFeatures.setText(String.valueOf(this.randomForestNumFeatures));
        txt_maxDepth.setText(String.valueOf(this.maxDepth));
        txt_randomForestNumIterations.setText(String.valueOf(this.randomForestNumIterations));
    }

    /**
     * Removes a list of tree types from a combobox
     *
     * @param types the list of tree types that must be removed
     */
    public void removeTreeType(Object... types) {
        for (Object type : types) {
            cb_treeType.removeItem(type.toString());
        }
    }

//    public static void main(String[] arg) {
//        try {
//            // Check if Nimbus is supported and get its classname
//            for (UIManager.LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(lafInfo.getName())) {
//                    UIManager.setLookAndFeel(lafInfo.getClassName());
//                    UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException eOut) {
//            try {
//                // If Nimbus is not available, set to the system look and feel
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
//            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException eIn) {
//                System.out.println("Error setting native LAF: " + eIn);
//            }
//        }
//
//        SwingUtilities.invokeLater(() -> {
//            DecisionTreeBasedPanel dtpanel = new DecisionTreeBasedPanel();
//            Dialog dlg = new Dialog(dtpanel);
//            dtpanel.setVisible(true);
//
//            System.out.println("Treetype = " + dtpanel.getTreeType());
//            if (dtpanel.getTreeType().equals(TreeType.C45)) {
//                System.out.println("confidence = " + dtpanel.getConfidence());
//                System.out.println("minNum = " + dtpanel.getMinNum());
//            } else if (dtpanel.getTreeType().equals(TreeType.RANDOM_TREE)) {
//                System.out.println("KValue = " + dtpanel.getRandomTreeKValue());
//                System.out.println("MaxDepth = " + dtpanel.getRandomTreeMaxDepth());
//                System.out.println("MinNum = " + dtpanel.getRandomTreeMinNum());
//                System.out.println("MinVarianceProp = " + dtpanel.getRandomTreeMinVarianceProp());
//            } else if (dtpanel.getTreeType().equals(TreeType.RANDOM_FOREST)) {
//                System.out.println("randomForestNumFeatures = " + dtpanel.getRandomForestNumFeatures());
//                System.out.println("maxDepth = " + dtpanel.getMaxDepth());
//                System.out.println("randomForestNumIteration = " + dtpanel.getRandomForestNumIterations());
//            }
//        });
//    }
}
