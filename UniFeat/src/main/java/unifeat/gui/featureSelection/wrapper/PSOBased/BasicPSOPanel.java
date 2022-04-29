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
package unifeat.gui.featureSelection.wrapper.PSOBased;

import unifeat.classifier.ClassifierType;
import unifeat.gui.ParameterPanel;
import unifeat.gui.classifier.DTClassifierPanel;
import unifeat.gui.classifier.KNNClassifierPanel;
import unifeat.gui.classifier.svmClassifier.SVMClassifierPanel;
import unifeat.util.MathFunc;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * This java class is used to create and show a panel for the parameter settings
 * of the basic particle swarm optimization (BasicPSO) method.
 *
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.wrapper.PSOBasedMethods.BasicPSO
 */
public abstract class BasicPSOPanel extends ParameterPanel 
        implements ItemListener {

    JComboBox cb_classifierType;
    protected JButton btn_classifierType;
    JLabel lbl_classifierType,
            lbl_numIteration, lbl_numIterationError,
            lbl_populationSize, lbl_populationSizeError,
            lbl_inertiaWeight, lbl_inertiaWeightError,
            lbl_c1, lbl_c1Error,
            lbl_c2, lbl_c2Error,
            lbl_posInterval,
            lbl_startPosInterval, lbl_startPosIntervalError,
            lbl_endPosInterval, lbl_endPosIntervalError,
            lbl_velocityInterval,
            lbl_minVelocity, lbl_minVelocityError,
            lbl_maxVelocity, lbl_maxVelocityError,
            lbl_kFolds, lbl_kFoldsError;
    JTextField txt_numIteration, txt_populationSize,
            txt_inertiaWeight, txt_c1, txt_c2,
            txt_startPosInterval, txt_endPosInterval,
            txt_minVelocity, txt_maxVelocity,
            txt_kFolds;
    private double inertiaWeight = 0.7298, c1 = 1.49618, c2 = 1.49618,
            startPosInterval = 0, endPosInterval = 1, minVelocity = -1, maxVelocity = 1;
    private static double DEFAULT_INERTIA_WEIGHT = 0.7298, DEFAULT_C1 = 1.49618, DEFAULT_C2 = 1.49618,
            DEFAULT_START_POS_INTERVAL = 0, DEFAULT_END_POS_INTERVAL = 1,
            DEFAULT_MIN_VELOCITY = -1, DEFAULT_MAX_VELOCITY = 1;
    private int numIteration = 100, populationSize = 30, kFolds = 10;
    private static int DEFAULT_NUM_ITERATION = 100, DEFAULT_POPULATION_SIZE = 30,
            DEFAULT_K_FOLDS = 10;
    private ClassifierType classifierType = ClassifierType.NB;
    private static final ClassifierType DEFAULT_CLASSIFIER_TYPE = ClassifierType.NB;
    //-------------- Evaluation classifier -----------------------------
    private SVMClassifierPanel svmEvaluationPanel;
    private DTClassifierPanel dtEvaluationPanel;
    private KNNClassifierPanel knnEvaluationPanel;
    private Object selectedClassifierPan = null;

    /**
     * Creates new form BasicPSOPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public BasicPSOPanel() {
        super("Parameter Settings Panel",
                "PSO Based methods settings:",
                "<html> This is a template for feature selection methods based on particle swarm optimization (PSO). "
                + "In these methods, k-fold cross validation on training set is used for evaluating the "
                + "classification performance of a selected feature subset during feature selection process. </html>",
                "Option\n\n"
                + "Classifier type -> the classifier is used for evaluating the fitness of a solution.\n\n"
                + "Number of iterations -> the maximum number of allowed iterations that algorithm repeated.\n\n"
                + "Population size -> the size of population of candidate solutions.\n\n"
                + "Parameter w -> the inertia weight in the velocity updating rule (a real number greater than zero (i.e., w>0)).\n\n"
                + "Parameter c1 and c2 -> the acceleration constants in the velocity updating rule (real numbers greater than zero).\n\n"
                + "Position interval -> the position values of each particle in the range of start and end values.\n\n"
                + "Velocity interval -> the velocity values of each particle in the range of start and end values.\n\n"
                + "Folds -> the number of equal sized subsamples that is used in k-fold cross validation.\n\n",
                new Rectangle(10, 10, 540, 20),
                new Rectangle(10, 35, 545, 80),
                new Rectangle(190, 460, 75, 23),
                new Rectangle(310, 460, 75, 23),
                new Dimension(570, 550));
        Container contentPane = getContentPane();

        lbl_classifierType = new JLabel("Classifier type:");
        lbl_classifierType.setBounds(50, 135, 170, 22);
        cb_classifierType = new JComboBox();
        cb_classifierType.setModel(new DefaultComboBoxModel(ClassifierType.asList()));
        cb_classifierType.setSelectedIndex(2); // set on naive bayes classifier
        cb_classifierType.setBounds(170, 135, 170, 22);
        cb_classifierType.addItemListener(this);

        btn_classifierType = new JButton("More option...");
        btn_classifierType.setBounds(350, 135, 105, 23);
        btn_classifierType.setEnabled(false);
        btn_classifierType.addActionListener(this);

        lbl_numIteration = new JLabel("Number of iterations:");
        lbl_numIteration.setBounds(50, 170, 170, 22);
        txt_numIteration = new JTextField(Integer.toString(DEFAULT_NUM_ITERATION));
        txt_numIteration.setBounds(170, 170, 120, 21);
        txt_numIteration.addKeyListener(this);
        lbl_numIterationError = new JLabel("");
        lbl_numIterationError.setBounds(300, 170, 50, 22);
        lbl_numIterationError.setForeground(Color.red);

        lbl_populationSize = new JLabel("Population size:");
        lbl_populationSize.setBounds(50, 205, 170, 22);
        txt_populationSize = new JTextField(Integer.toString(DEFAULT_POPULATION_SIZE));
        txt_populationSize.setBounds(170, 205, 120, 21);
        txt_populationSize.addKeyListener(this);
        lbl_populationSizeError = new JLabel("");
        lbl_populationSizeError.setBounds(300, 205, 50, 22);
        lbl_populationSizeError.setForeground(Color.red);

        lbl_inertiaWeight = new JLabel("Parameter w:");
        lbl_inertiaWeight.setBounds(50, 240, 170, 22);
        txt_inertiaWeight = new JTextField(Double.toString(DEFAULT_INERTIA_WEIGHT));
        txt_inertiaWeight.setBounds(170, 240, 120, 21);
        txt_inertiaWeight.addKeyListener(this);
        lbl_inertiaWeightError = new JLabel("");
        lbl_inertiaWeightError.setBounds(300, 240, 50, 22);
        lbl_inertiaWeightError.setForeground(Color.red);

        lbl_c1 = new JLabel("Parameter c1:");
        lbl_c1.setBounds(50, 275, 170, 22);
        txt_c1 = new JTextField(Double.toString(DEFAULT_C1));
        txt_c1.setBounds(170, 275, 120, 21);
        txt_c1.addKeyListener(this);
        lbl_c1Error = new JLabel("");
        lbl_c1Error.setBounds(300, 275, 50, 22);
        lbl_c1Error.setForeground(Color.red);

        lbl_c2 = new JLabel("Parameter c2:");
        lbl_c2.setBounds(50, 310, 170, 22);
        txt_c2 = new JTextField(Double.toString(DEFAULT_C2));
        txt_c2.setBounds(170, 310, 120, 21);
        txt_c2.addKeyListener(this);
        lbl_c2Error = new JLabel("");
        lbl_c2Error.setBounds(300, 310, 50, 22);
        lbl_c2Error.setForeground(Color.red);

        lbl_posInterval = new JLabel("Position interval:");
        lbl_posInterval.setBounds(50, 345, 170, 22);

        lbl_startPosInterval = new JLabel("start:");
        lbl_startPosInterval.setBounds(170, 345, 170, 22);
        txt_startPosInterval = new JTextField(Double.toString(DEFAULT_START_POS_INTERVAL));
        txt_startPosInterval.setBounds(200, 345, 120, 21);
        txt_startPosInterval.addKeyListener(this);
        lbl_startPosIntervalError = new JLabel("");
        lbl_startPosIntervalError.setBounds(330, 345, 50, 22);
        lbl_startPosIntervalError.setForeground(Color.red);

        lbl_endPosInterval = new JLabel("end:");
        lbl_endPosInterval.setBounds(350, 345, 170, 22);
        txt_endPosInterval = new JTextField(Double.toString(DEFAULT_END_POS_INTERVAL));
        txt_endPosInterval.setBounds(380, 345, 120, 21);
        txt_endPosInterval.addKeyListener(this);
        lbl_endPosIntervalError = new JLabel("");
        lbl_endPosIntervalError.setBounds(510, 345, 50, 22);
        lbl_endPosIntervalError.setForeground(Color.red);

        lbl_velocityInterval = new JLabel("Velocity interval:");
        lbl_velocityInterval.setBounds(50, 380, 170, 22);

        lbl_minVelocity = new JLabel("start:");
        lbl_minVelocity.setBounds(170, 380, 170, 22);
        txt_minVelocity = new JTextField(Double.toString(DEFAULT_MIN_VELOCITY));
        txt_minVelocity.setBounds(200, 380, 120, 21);
        txt_minVelocity.addKeyListener(this);
        lbl_minVelocityError = new JLabel("");
        lbl_minVelocityError.setBounds(330, 380, 50, 22);
        lbl_minVelocityError.setForeground(Color.red);

        lbl_maxVelocity = new JLabel("end:");
        lbl_maxVelocity.setBounds(350, 380, 170, 22);
        txt_maxVelocity = new JTextField(Double.toString(DEFAULT_MAX_VELOCITY));
        txt_maxVelocity.setBounds(380, 380, 120, 21);
        txt_maxVelocity.addKeyListener(this);
        lbl_maxVelocityError = new JLabel("");
        lbl_maxVelocityError.setBounds(510, 380, 50, 22);
        lbl_maxVelocityError.setForeground(Color.red);

        lbl_kFolds = new JLabel("Folds:");
        lbl_kFolds.setBounds(50, 415, 170, 22);
        txt_kFolds = new JTextField(Integer.toString(DEFAULT_K_FOLDS));
        txt_kFolds.setBounds(170, 415, 120, 21);
        txt_kFolds.addKeyListener(this);
        lbl_kFoldsError = new JLabel("");
        lbl_kFoldsError.setBounds(300, 415, 50, 22);
        lbl_kFoldsError.setForeground(Color.red);

        contentPane.add(lbl_classifierType);
        contentPane.add(cb_classifierType);
        contentPane.add(btn_classifierType);

        contentPane.add(lbl_numIteration);
        contentPane.add(txt_numIteration);
        contentPane.add(lbl_numIterationError);

        contentPane.add(lbl_populationSize);
        contentPane.add(txt_populationSize);
        contentPane.add(lbl_populationSizeError);

        contentPane.add(lbl_inertiaWeight);
        contentPane.add(txt_inertiaWeight);
        contentPane.add(lbl_inertiaWeightError);

        contentPane.add(lbl_c1);
        contentPane.add(txt_c1);
        contentPane.add(lbl_c1Error);

        contentPane.add(lbl_c2);
        contentPane.add(txt_c2);
        contentPane.add(lbl_c2Error);

        contentPane.add(lbl_posInterval);
        contentPane.add(lbl_startPosInterval);
        contentPane.add(txt_startPosInterval);
        contentPane.add(lbl_startPosIntervalError);

        contentPane.add(lbl_endPosInterval);
        contentPane.add(txt_endPosInterval);
        contentPane.add(lbl_endPosIntervalError);

        contentPane.add(lbl_velocityInterval);
        contentPane.add(lbl_minVelocity);
        contentPane.add(txt_minVelocity);
        contentPane.add(lbl_minVelocityError);

        contentPane.add(lbl_maxVelocity);
        contentPane.add(txt_maxVelocity);
        contentPane.add(lbl_maxVelocityError);

        contentPane.add(lbl_kFolds);
        contentPane.add(txt_kFolds);
        contentPane.add(lbl_kFoldsError);

        contentPane.validate();
//        contentPane.revalidate();
        contentPane.repaint();
//        this.pack();
    }

    /**
     * The listener method for receiving keyboard events (keystrokes). Invoked
     * when a key has been released.
     *
     * @param e an action event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        boolean enableOkButton = true;
        String tempStr;

        if (ClassifierType.parse(cb_classifierType.getSelectedItem().toString()) == ClassifierType.NONE) {
            enableOkButton = false;
        }

        tempStr = txt_numIteration.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 1)) {
            lbl_numIterationError.setText("*");
            enableOkButton = false;
        } else {
            lbl_numIterationError.setText("");
        }

        tempStr = txt_populationSize.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 1)) {
            lbl_populationSizeError.setText("*");
            enableOkButton = false;
        } else {
            lbl_populationSizeError.setText("");
        }

        tempStr = txt_inertiaWeight.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0) {
            lbl_inertiaWeightError.setText("*");
            enableOkButton = false;
        } else {
            lbl_inertiaWeightError.setText("");
        }

        tempStr = txt_c1.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0) {
            lbl_c1Error.setText("*");
            enableOkButton = false;
        } else {
            lbl_c1Error.setText("");
        }

        tempStr = txt_c2.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0) {
            lbl_c2Error.setText("*");
            enableOkButton = false;
        } else {
            lbl_c2Error.setText("");
        }

        tempStr = txt_startPosInterval.getText();
        if (!MathFunc.isDouble(tempStr)) {
            lbl_startPosIntervalError.setText("*");
            enableOkButton = false;
        } else {
            lbl_startPosIntervalError.setText("");
        }

        tempStr = txt_endPosInterval.getText();
        if (!MathFunc.isDouble(tempStr)) {
            lbl_endPosIntervalError.setText("*");
            enableOkButton = false;
        } else {
            lbl_endPosIntervalError.setText("");
        }

        tempStr = txt_minVelocity.getText();
        if (!MathFunc.isDouble(tempStr)) {
            lbl_minVelocityError.setText("*");
            enableOkButton = false;
        } else {
            lbl_minVelocityError.setText("");
        }

        tempStr = txt_maxVelocity.getText();
        if (!MathFunc.isDouble(tempStr)) {
            lbl_maxVelocityError.setText("*");
            enableOkButton = false;
        } else {
            lbl_maxVelocityError.setText("");
        }

        tempStr = txt_kFolds.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) <= 1)) {
            lbl_kFoldsError.setText("*");
            enableOkButton = false;
        } else {
            lbl_kFoldsError.setText("");
        }

        btn_ok.setEnabled(enableOkButton);
    }

    /**
     * The listener method for receiving action events. Invoked when an action
     * occurs.
     *
     * @param e an action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_ok)) {
            btn_okActionPerformed(e);
        } else if (e.getSource().equals(btn_more)) {
            btn_moreActionPerformed(e);
        } else if (e.getSource().equals(btn_classifierType)) {
            btn_moreOpClassifierActionPerformed(e);
        }
    }

    /**
     * The listener method for receiving item events. Invoked when an item has
     * been selected or deselected by the user.
     *
     * @param e an action event
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(cb_classifierType)) {
            cb_classifierItemStateChanged(e);
        }
    }

    /**
     * This method sets an action for the btn_moreOpClassifier button.
     *
     * @param e an action event
     */
    private void btn_moreOpClassifierActionPerformed(ActionEvent e) {
        classifierType = ClassifierType.parse(cb_classifierType.getSelectedItem().toString());
        if (classifierType == ClassifierType.SVM) {
            SVMClassifierPanel currentPanel = (SVMClassifierPanel) selectedClassifierPan;
            svmEvaluationPanel = new SVMClassifierPanel();
            Dialog svmDlg = new Dialog(svmEvaluationPanel);
            svmEvaluationPanel.setUserValue(currentPanel.getKernel(), currentPanel.getParameterC());
            svmEvaluationPanel.setVisible(true);
            selectedClassifierPan = svmEvaluationPanel;
            System.out.println("kernel = " + svmEvaluationPanel.getKernel().toString()
                    + "   C = " + svmEvaluationPanel.getParameterC() + "\n"
                    + "kernel = " + ((SVMClassifierPanel) selectedClassifierPan).getKernel().toString()
                    + "   C = " + ((SVMClassifierPanel) selectedClassifierPan).getParameterC());
        } else if (classifierType == ClassifierType.DT) {
            DTClassifierPanel currentPanel = (DTClassifierPanel) selectedClassifierPan;
            dtEvaluationPanel = new DTClassifierPanel();
            Dialog dtDlg = new Dialog(dtEvaluationPanel);
            dtEvaluationPanel.setUserValue(currentPanel.getConfidence(), currentPanel.getMinNum());
            dtEvaluationPanel.setVisible(true);
            selectedClassifierPan = dtEvaluationPanel;
            System.out.println("min num = " + dtEvaluationPanel.getMinNum()
                    + "  confidence = " + dtEvaluationPanel.getConfidence() + "\n"
                    + "min num = " + ((DTClassifierPanel) selectedClassifierPan).getMinNum()
                    + "  confidence = " + ((DTClassifierPanel) selectedClassifierPan).getConfidence());
        } else if (classifierType == ClassifierType.KNN) {
            KNNClassifierPanel currentPanel = (KNNClassifierPanel) selectedClassifierPan;
            knnEvaluationPanel = new KNNClassifierPanel();
            Dialog dtDlg = new Dialog(knnEvaluationPanel);
            knnEvaluationPanel.setUserValue(currentPanel.getKNNValue());
            knnEvaluationPanel.setVisible(true);
            selectedClassifierPan = knnEvaluationPanel;
            System.out.println("KNN Value = " + knnEvaluationPanel.getKNNValue() + "\n"
                    + "KNN Value = " + ((KNNClassifierPanel) selectedClassifierPan).getKNNValue());
        }
    }

    /**
     * This method sets an action for the cb_classifier combo box.
     *
     * @param e an action event
     */
    private void cb_classifierItemStateChanged(ItemEvent e) {
        classifierType = ClassifierType.parse(cb_classifierType.getSelectedItem().toString());
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (classifierType == ClassifierType.SVM) {
                svmEvaluationPanel = new SVMClassifierPanel();
                svmEvaluationPanel.setDefaultValue();
                btn_classifierType.setEnabled(true);
                selectedClassifierPan = svmEvaluationPanel;
                System.out.println("default:   kernel = " + ((SVMClassifierPanel) selectedClassifierPan).getKernel().toString()
                        + "   C = " + ((SVMClassifierPanel) selectedClassifierPan).getParameterC());
            } else if (classifierType == ClassifierType.DT) {
                dtEvaluationPanel = new DTClassifierPanel();
                dtEvaluationPanel.setDefaultValue();
                btn_classifierType.setEnabled(true);
                selectedClassifierPan = dtEvaluationPanel;
                System.out.println("default:    min num = " + ((DTClassifierPanel) selectedClassifierPan).getMinNum()
                        + "  confidence = " + ((DTClassifierPanel) selectedClassifierPan).getConfidence());
            } else if (classifierType == ClassifierType.KNN) {
                knnEvaluationPanel = new KNNClassifierPanel();
                knnEvaluationPanel.setDefaultValue();
                btn_classifierType.setEnabled(true);
                selectedClassifierPan = knnEvaluationPanel;
                System.out.println("default:    KNN Value = " + ((KNNClassifierPanel) selectedClassifierPan).getKNNValue());
            } else {
                btn_classifierType.setEnabled(false);
            }
        }
        keyReleased(null);
    }

    /**
     * This method sets an action for the btn_ok button.
     *
     * @param e an action event
     */
    @Override
    protected void btn_okActionPerformed(ActionEvent e) {
        setNumIteration(Integer.parseInt(txt_numIteration.getText()));
        setPopulationSize(Integer.parseInt(txt_populationSize.getText()));
        setInertiaWeight(Double.parseDouble(txt_inertiaWeight.getText()));
        setC1(Double.parseDouble(txt_c1.getText()));
        setC2(Double.parseDouble(txt_c2.getText()));
        setStartPosInterval(Double.parseDouble(txt_startPosInterval.getText()));
        setEndPosInterval(Double.parseDouble(txt_endPosInterval.getText()));
        setMinVelocity(Double.parseDouble(txt_minVelocity.getText()));
        setMaxVelocity(Double.parseDouble(txt_maxVelocity.getText()));
        setKFolds(Integer.parseInt(txt_kFolds.getText()));
        super.btn_okActionPerformed(e);
    }

    /**
     * This method returns the selected classifier type.
     *
     * @return the <code>classifierType</code> parameter
     */
    public ClassifierType getClassifierType() {
        return classifierType;
    }

    /**
     * This method sets the selected classifier type.
     *
     * @param classifierType the selected classifier type
     */
    public void setClassifierType(ClassifierType classifierType) {
        this.classifierType = classifierType;
    }

    /**
     * This method returns the selected classifier panel value.
     *
     * @return the <code>selectedClassifierPan</code> parameter
     */
    public Object getSelectedClassifierPan() {
        return selectedClassifierPan;
    }

    /**
     * This method sets the selected classifier panel value.
     *
     * @param selectedClassifierPan the selected classifier panel value
     */
    public void setSelectedClassifierPan(Object selectedClassifierPan) {
        this.selectedClassifierPan = selectedClassifierPan;
    }

    /**
     * This method returns the number of iterations value.
     *
     * @return the <code>numIteration</code> parameter
     */
    public int getNumIteration() {
        return numIteration;
    }

    /**
     * This method sets the number of iterations value.
     *
     * @param numIteration the number of iterations value
     */
    public void setNumIteration(int numIteration) {
        this.numIteration = numIteration;
    }

    /**
     * This method returns the size of population value.
     *
     * @return the <code>populationSize</code> parameter
     */
    public int getPopulationSize() {
        return populationSize;
    }

    /**
     * This method sets the size of population value.
     *
     * @param populationSize the size of population value
     */
    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    /**
     * This method returns the inertia weight (w) value.
     *
     * @return the <code>inertiaWeight</code> parameter
     */
    public double getInertiaWeight() {
        return inertiaWeight;
    }

    /**
     * This method sets the inertia weight (w) value.
     *
     * @param inertiaWeight the inertia weight (w) value
     */
    public void setInertiaWeight(double inertiaWeight) {
        this.inertiaWeight = inertiaWeight;
    }

    /**
     * This method returns the c1 value.
     *
     * @return the <code>c1</code> parameter
     */
    public double getC1() {
        return c1;
    }

    /**
     * This method sets the c1 value.
     *
     * @param c1 the c1 value
     */
    public void setC1(double c1) {
        this.c1 = c1;
    }

    /**
     * This method returns the c2 value.
     *
     * @return the <code>c2</code> parameter
     */
    public double getC2() {
        return c2;
    }

    /**
     * This method sets the c2 value.
     *
     * @param c2 the c2 value
     */
    public void setC2(double c2) {
        this.c2 = c2;
    }

    /**
     * This method returns the position interval start value.
     *
     * @return the <code>startPosInterval</code> parameter
     */
    public double getStartPosInterval() {
        return startPosInterval;
    }

    /**
     * This method sets the position interval start value.
     *
     * @param startPosInterval the position interval start value
     */
    public void setStartPosInterval(double startPosInterval) {
        this.startPosInterval = startPosInterval;
    }

    /**
     * This method returns the position interval end value.
     *
     * @return the <code>endPosInterval</code> parameter
     */
    public double getEndPosInterval() {
        return endPosInterval;
    }

    /**
     * This method sets the position interval end value.
     *
     * @param endPosInterval the position interval end value
     */
    public void setEndPosInterval(double endPosInterval) {
        this.endPosInterval = endPosInterval;
    }

    /**
     * This method returns the velocity interval start value.
     *
     * @return the <code>minVelocity</code> parameter
     */
    public double getMinVelocity() {
        return minVelocity;
    }

    /**
     * This method sets the velocity interval start value.
     *
     * @param minVelocity the velocity interval start value
     */
    public void setMinVelocity(double minVelocity) {
        this.minVelocity = minVelocity;
    }

    /**
     * This method returns the velocity interval end value.
     *
     * @return the <code>maxVelocity</code> parameter
     */
    public double getMaxVelocity() {
        return maxVelocity;
    }

    /**
     * This method sets the velocity interval end value.
     *
     * @param maxVelocity the velocity interval end value
     */
    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    /**
     * This method returns the k folds value.
     *
     * @return the <code>kFolds</code> parameter
     */
    public int getKFolds() {
        return kFolds;
    }

    /**
     * This method sets the k folds value.
     *
     * @param kFolds the k folds value
     */
    public void setKFolds(int kFolds) {
        this.kFolds = kFolds;
    }

    /**
     * Sets the default values of the basic PSO parameters
     */
    public void setDefaultValue() {
        cb_classifierType.setSelectedIndex(DEFAULT_CLASSIFIER_TYPE.getValue());
        txt_numIteration.setText(String.valueOf(DEFAULT_NUM_ITERATION));
        txt_populationSize.setText(String.valueOf(DEFAULT_POPULATION_SIZE));
        txt_inertiaWeight.setText(String.valueOf(DEFAULT_INERTIA_WEIGHT));
        txt_c1.setText(String.valueOf(DEFAULT_C1));
        txt_c2.setText(String.valueOf(DEFAULT_C2));
        txt_startPosInterval.setText(String.valueOf(DEFAULT_START_POS_INTERVAL));
        txt_endPosInterval.setText(String.valueOf(DEFAULT_END_POS_INTERVAL));
        txt_minVelocity.setText(String.valueOf(DEFAULT_MIN_VELOCITY));
        txt_maxVelocity.setText(String.valueOf(DEFAULT_MAX_VELOCITY));
        txt_kFolds.setText(String.valueOf(DEFAULT_K_FOLDS));

        classifierType = DEFAULT_CLASSIFIER_TYPE;
        selectedClassifierPan = null; // set on naive bayes classifier
        numIteration = DEFAULT_NUM_ITERATION;
        populationSize = DEFAULT_POPULATION_SIZE;
        inertiaWeight = DEFAULT_INERTIA_WEIGHT;
        c1 = DEFAULT_C1;
        c2 = DEFAULT_C2;
        startPosInterval = DEFAULT_START_POS_INTERVAL;
        endPosInterval = DEFAULT_END_POS_INTERVAL;
        minVelocity = DEFAULT_MIN_VELOCITY;
        maxVelocity = DEFAULT_MAX_VELOCITY;
        kFolds = DEFAULT_K_FOLDS;
    }

    /**
     * Replaces the default values of basic PSO parameters with user values
     *
     * @param numIteration the maximum number of allowed iterations that
     * algorithm repeated
     * @param populationSize the size of population of candidate solutions
     * @param inertiaWeight the inertia weight in the velocity updating rule
     * @param c1 the acceleration constant in the velocity updating rule
     * @param c2 the acceleration constant in the velocity updating rule
     * @param startPosInterval the position interval start value of each
     * particle
     * @param endPosInterval the position interval end value of each particle
     * @param minVelocity the velocity interval end value of each particle
     * @param maxVelocity the velocity interval end value of each particle
     * @param kFolds the number of equal sized subsamples that is used in k-fold
     * cross validation
     */
    public void changeDefaultValue(int numIteration, int populationSize,
            double inertiaWeight, double c1, double c2, double startPosInterval, double endPosInterval,
            double minVelocity, double maxVelocity, int kFolds) {
        this.numIteration = numIteration;
        this.populationSize = populationSize;
        this.inertiaWeight = inertiaWeight;
        this.c1 = c1;
        this.c2 = c2;
        this.startPosInterval = startPosInterval;
        this.endPosInterval = endPosInterval;
        this.minVelocity = minVelocity;
        this.maxVelocity = maxVelocity;
        this.kFolds = kFolds;

        DEFAULT_NUM_ITERATION = this.numIteration;
        DEFAULT_POPULATION_SIZE = this.populationSize;
        DEFAULT_INERTIA_WEIGHT = this.inertiaWeight;
        DEFAULT_C1 = this.c1;
        DEFAULT_C2 = this.c2;
        DEFAULT_START_POS_INTERVAL = this.startPosInterval;
        DEFAULT_END_POS_INTERVAL = this.endPosInterval;
        DEFAULT_MIN_VELOCITY = this.minVelocity;
        DEFAULT_MAX_VELOCITY = this.maxVelocity;
        DEFAULT_K_FOLDS = this.kFolds;

        txt_numIteration.setText(String.valueOf(DEFAULT_NUM_ITERATION));
        txt_populationSize.setText(String.valueOf(DEFAULT_POPULATION_SIZE));
        txt_inertiaWeight.setText(String.valueOf(DEFAULT_INERTIA_WEIGHT));
        txt_c1.setText(String.valueOf(DEFAULT_C1));
        txt_c2.setText(String.valueOf(DEFAULT_C2));
        txt_startPosInterval.setText(String.valueOf(DEFAULT_START_POS_INTERVAL));
        txt_endPosInterval.setText(String.valueOf(DEFAULT_END_POS_INTERVAL));
        txt_minVelocity.setText(String.valueOf(DEFAULT_MIN_VELOCITY));
        txt_maxVelocity.setText(String.valueOf(DEFAULT_MAX_VELOCITY));
        txt_kFolds.setText(String.valueOf(DEFAULT_K_FOLDS));
    }

    /**
     * Enables the values of text box
     *
     * @param enable the status of the value
     */
    public void enablePositionValues(boolean enable) {
        txt_startPosInterval.setEnabled(enable);
        txt_startPosInterval.setBackground(Color.WHITE);
        txt_endPosInterval.setEnabled(enable);
        txt_endPosInterval.setBackground(Color.WHITE);
    }

    /**
     * Sets the last values of the basic PSO parameters entered by user
     *
     * @param classifierType the selected classifier type
     * @param selectedClassifierPan the selected classifier panel
     * @param numIteration the maximum number of allowed iterations that
     * algorithm repeated
     * @param populationSize the size of population of candidate solutions
     * @param inertiaWeight the inertia weight in the velocity updating rule
     * @param c1 the acceleration constant in the velocity updating rule
     * @param c2 the acceleration constant in the velocity updating rule
     * @param startPosInterval the position interval start value of each
     * particle
     * @param endPosInterval the position interval end value of each particle
     * @param minVelocity the velocity interval end value of each particle
     * @param maxVelocity the velocity interval end value of each particle
     * @param kFolds the number of equal sized subsamples that is used in k-fold
     * cross validation
     */
    public void setUserValue(ClassifierType classifierType, Object selectedClassifierPan, int numIteration, int populationSize,
            double inertiaWeight, double c1, double c2, double startPosInterval, double endPosInterval,
            double minVelocity, double maxVelocity, int kFolds) {
        this.classifierType = classifierType;
        this.numIteration = numIteration;
        this.populationSize = populationSize;
        this.inertiaWeight = inertiaWeight;
        this.c1 = c1;
        this.c2 = c2;
        this.startPosInterval = startPosInterval;
        this.endPosInterval = endPosInterval;
        this.minVelocity = minVelocity;
        this.maxVelocity = maxVelocity;
        this.kFolds = kFolds;

        cb_classifierType.setSelectedIndex(this.classifierType.getValue());
        this.selectedClassifierPan = selectedClassifierPan;
        if (this.classifierType == ClassifierType.SVM) {
            svmEvaluationPanel = (SVMClassifierPanel) this.selectedClassifierPan;
        } else if (this.classifierType == ClassifierType.DT) {
            dtEvaluationPanel = (DTClassifierPanel) this.selectedClassifierPan;
        } else if (this.classifierType == ClassifierType.KNN) {
            knnEvaluationPanel = (KNNClassifierPanel) this.selectedClassifierPan;
        }

        txt_numIteration.setText(String.valueOf(this.numIteration));
        txt_populationSize.setText(String.valueOf(this.populationSize));
        txt_inertiaWeight.setText(String.valueOf(this.inertiaWeight));
        txt_c1.setText(String.valueOf(this.c1));
        txt_c2.setText(String.valueOf(this.c2));
        txt_startPosInterval.setText(String.valueOf(this.startPosInterval));
        txt_endPosInterval.setText(String.valueOf(this.endPosInterval));
        txt_minVelocity.setText(String.valueOf(this.minVelocity));
        txt_maxVelocity.setText(String.valueOf(this.maxVelocity));
        txt_kFolds.setText(String.valueOf(this.kFolds));
    }
}
