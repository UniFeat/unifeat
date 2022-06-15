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
package unifeat.gui.featureSelection.wrapper.ACOBased;

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
 * of the basic ant colony optimization (BasicACO) method.
 *
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.wrapper.ACOBasedMethods.BasicACO
 */
public abstract class BasicACOPanel extends ParameterPanel
        implements ItemListener {

    JComboBox cb_classifierType;
    protected JButton btn_classifierType;
    JLabel lbl_classifierType,
            lbl_numIteration, lbl_numIterationError,
            lbl_colonySize, lbl_colonySizeError,
            lbl_alpha, lbl_alphaError,
            lbl_beta, lbl_betaError,
            lbl_evRate, lbl_evRateError,
            lbl_kFolds, lbl_kFoldsError;
    JTextField txt_numIteration, txt_colonySize,
            txt_alpha, txt_beta, txt_evRate,
            txt_kFolds;
    private double alpha = 1.0, beta = 0.1, evRate = 0.2;
    private static double DEFAULT_ALPHA = 1.0, DEFAULT_BETA = 0.1, DEFAULT_EV_RATE = 0.2;
    private int numIteration = 100, colonySize = 50, kFolds = 10;
    private static int DEFAULT_NUM_ITERATION = 100, DEFAULT_COLONY_SIZE = 50,
            DEFAULT_K_FOLDS = 10;
    private ClassifierType classifierType = ClassifierType.NB;
    private static final ClassifierType DEFAULT_CLASSIFIER_TYPE = ClassifierType.NB;
    //-------------- Evaluation classifier -----------------------------
    private SVMClassifierPanel svmEvaluationPanel;
    private DTClassifierPanel dtEvaluationPanel;
    private KNNClassifierPanel knnEvaluationPanel;
    private Object selectedClassifierPan = null;

    /**
     * Creates new form BasicACOPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public BasicACOPanel() {
        super("Parameter Settings Panel",
                "ACO Based methods settings:",
                "<html> This is a template for feature selection methods based on ant colony optimization (ACO). "
                + "In these methods, k-fold cross validation on training set is used for evaluating the "
                + "classification performance of a selected feature subset during feature selection process. </html>",
                "Option\n\n"
                + "Classifier type -> the classifier is used for evaluating the fitness of a solution.\n\n"
                + "Number of iterations -> the maximum number of allowed iterations that algorithm repeated.\n\n"
                + "Colony size -> the size of colony of candidate solutions.\n\n"
                + "Parameter alpha -> the alpha parameter in the state transition rule that shows the relative importance of the pheromone "
                + "(a real number greater than zero (i.e., alpha>0)).\n\n"
                + "Parameter beta -> the beta parameter in the state transition rule that shows the relative importance of heuristic information "
                + "(a real number greater than zero (i.e., beta>0)).\n\n"
                + "Evaporation rate -> the evaporation rate of the pheromone (a real number in the range of (0, 1)).\n\n"
                + "Folds -> the number of equal sized subsamples that is used in k-fold cross validation.\n\n",
                new Rectangle(10, 10, 540, 20),
                new Rectangle(10, 35, 545, 80),
                new Rectangle(190, 400, 75, 23),
                new Rectangle(310, 400, 75, 23),
                new Dimension(570, 490));
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

        lbl_colonySize = new JLabel("Colony size:");
        lbl_colonySize.setBounds(50, 205, 170, 22);
        txt_colonySize = new JTextField(Integer.toString(DEFAULT_COLONY_SIZE));
        txt_colonySize.setBounds(170, 205, 120, 21);
        txt_colonySize.addKeyListener(this);
        lbl_colonySizeError = new JLabel("");
        lbl_colonySizeError.setBounds(300, 205, 50, 22);
        lbl_colonySizeError.setForeground(Color.red);

        lbl_alpha = new JLabel("Parameter alpha:");
        lbl_alpha.setBounds(50, 240, 170, 22);
        txt_alpha = new JTextField(Double.toString(DEFAULT_ALPHA));
        txt_alpha.setBounds(170, 240, 120, 21);
        txt_alpha.addKeyListener(this);
        lbl_alphaError = new JLabel("");
        lbl_alphaError.setBounds(300, 240, 50, 22);
        lbl_alphaError.setForeground(Color.red);

        lbl_beta = new JLabel("Parameter beta:");
        lbl_beta.setBounds(50, 275, 170, 22);
        txt_beta = new JTextField(Double.toString(DEFAULT_BETA));
        txt_beta.setBounds(170, 275, 120, 21);
        txt_beta.addKeyListener(this);
        lbl_betaError = new JLabel("");
        lbl_betaError.setBounds(300, 275, 50, 22);
        lbl_betaError.setForeground(Color.red);

        lbl_evRate = new JLabel("Evaporation rate:");
        lbl_evRate.setBounds(50, 310, 170, 22);
        txt_evRate = new JTextField(Double.toString(DEFAULT_EV_RATE));
        txt_evRate.setBounds(170, 310, 120, 21);
        txt_evRate.addKeyListener(this);
        lbl_evRateError = new JLabel("");
        lbl_evRateError.setBounds(300, 310, 50, 22);
        lbl_evRateError.setForeground(Color.red);

        lbl_kFolds = new JLabel("Folds:");
        lbl_kFolds.setBounds(50, 345, 170, 22);
        txt_kFolds = new JTextField(Integer.toString(DEFAULT_K_FOLDS));
        txt_kFolds.setBounds(170, 345, 120, 21);
        txt_kFolds.addKeyListener(this);
        lbl_kFoldsError = new JLabel("");
        lbl_kFoldsError.setBounds(300, 345, 50, 22);
        lbl_kFoldsError.setForeground(Color.red);

        contentPane.add(lbl_classifierType);
        contentPane.add(cb_classifierType);
        contentPane.add(btn_classifierType);

        contentPane.add(lbl_numIteration);
        contentPane.add(txt_numIteration);
        contentPane.add(lbl_numIterationError);

        contentPane.add(lbl_colonySize);
        contentPane.add(txt_colonySize);
        contentPane.add(lbl_colonySizeError);

        contentPane.add(lbl_alpha);
        contentPane.add(txt_alpha);
        contentPane.add(lbl_alphaError);

        contentPane.add(lbl_beta);
        contentPane.add(txt_beta);
        contentPane.add(lbl_betaError);

        contentPane.add(lbl_evRate);
        contentPane.add(txt_evRate);
        contentPane.add(lbl_evRateError);

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

        tempStr = txt_colonySize.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 1)) {
            lbl_colonySizeError.setText("*");
            enableOkButton = false;
        } else {
            lbl_colonySizeError.setText("");
        }

        tempStr = txt_alpha.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0) {
            lbl_alphaError.setText("*");
            enableOkButton = false;
        } else {
            lbl_alphaError.setText("");
        }

        tempStr = txt_beta.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0) {
            lbl_betaError.setText("*");
            enableOkButton = false;
        } else {
            lbl_betaError.setText("");
        }

        tempStr = txt_evRate.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0 || Double.parseDouble(tempStr) >= 1) {
            lbl_evRateError.setText("*");
            enableOkButton = false;
        } else {
            lbl_evRateError.setText("");
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
        setColonySize(Integer.parseInt(txt_colonySize.getText()));
        setAlpha(Double.parseDouble(txt_alpha.getText()));
        setBeta(Double.parseDouble(txt_beta.getText()));
        setEvRate(Double.parseDouble(txt_evRate.getText()));
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
     * This method returns the size of colony value.
     *
     * @return the <code>colonySize</code> parameter
     */
    public int getColonySize() {
        return colonySize;
    }

    /**
     * This method sets the size of colony value.
     *
     * @param colonySize the size of colony value
     */
    public void setColonySize(int colonySize) {
        this.colonySize = colonySize;
    }

    /**
     * This method returns the alpha.
     *
     * @return the <code>alpha</code> parameter
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * This method sets the alpha value.
     *
     * @param alpha the alpha value
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * This method returns the beta value.
     *
     * @return the <code>beta</code> parameter
     */
    public double getBeta() {
        return beta;
    }

    /**
     * This method sets the beta value.
     *
     * @param beta the beta value
     */
    public void setBeta(double beta) {
        this.beta = beta;
    }

    /**
     * This method returns the evaporation rate.
     *
     * @return the <code>evaporation rate</code> parameter
     */
    public double getEvRate() {
        return evRate;
    }

    /**
     * This method sets the evaporation rate value.
     *
     * @param evRate the evaporation rate value
     */
    public void setEvRate(double evRate) {
        this.evRate = evRate;
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
     * Sets the default values of the basic ACO parameters
     */
    public void setDefaultValue() {
        cb_classifierType.setSelectedIndex(DEFAULT_CLASSIFIER_TYPE.getValue());
        txt_numIteration.setText(String.valueOf(DEFAULT_NUM_ITERATION));
        txt_colonySize.setText(String.valueOf(DEFAULT_COLONY_SIZE));
        txt_alpha.setText(String.valueOf(DEFAULT_ALPHA));
        txt_beta.setText(String.valueOf(DEFAULT_BETA));
        txt_evRate.setText(String.valueOf(DEFAULT_EV_RATE));
        txt_kFolds.setText(String.valueOf(DEFAULT_K_FOLDS));

        classifierType = DEFAULT_CLASSIFIER_TYPE;
        selectedClassifierPan = null; // set on naive bayes classifier
        numIteration = DEFAULT_NUM_ITERATION;
        colonySize = DEFAULT_COLONY_SIZE;
        alpha = DEFAULT_ALPHA;
        beta = DEFAULT_BETA;
        evRate = DEFAULT_EV_RATE;
        kFolds = DEFAULT_K_FOLDS;
    }

    /**
     * Replaces the default values of basic ACO parameters with user values
     *
     * @param numIteration the maximum number of allowed iterations that
     * algorithm repeated
     * @param colonySize the size of colony of candidate solutions
     * @param alpha the alpha parameter in the state transition rule that shows
     * the relative importance of the pheromone
     * @param beta the beta parameter in the state transition rule that shows
     * the relative importance of heuristic information
     * @param evRate the evaporation rate of the pheromone
     * @param kFolds the number of equal sized subsamples that is used in k-fold
     * cross validation
     */
    public void changeDefaultValue(int numIteration, int colonySize,
            double alpha, double beta, double evRate, int kFolds) {
        this.numIteration = numIteration;
        this.colonySize = colonySize;
        this.alpha = alpha;
        this.beta = beta;
        this.evRate = evRate;
        this.kFolds = kFolds;

        DEFAULT_NUM_ITERATION = this.numIteration;
        DEFAULT_COLONY_SIZE = this.colonySize;
        DEFAULT_ALPHA = this.alpha;
        DEFAULT_BETA = this.beta;
        DEFAULT_EV_RATE = this.evRate;
        DEFAULT_K_FOLDS = this.kFolds;

        txt_numIteration.setText(String.valueOf(DEFAULT_NUM_ITERATION));
        txt_colonySize.setText(String.valueOf(DEFAULT_COLONY_SIZE));
        txt_alpha.setText(String.valueOf(DEFAULT_ALPHA));
        txt_beta.setText(String.valueOf(DEFAULT_BETA));
        txt_evRate.setText(String.valueOf(DEFAULT_EV_RATE));
        txt_kFolds.setText(String.valueOf(DEFAULT_K_FOLDS));
    }

    /**
     * Sets the last values of the basic ACO parameters entered by user
     *
     * @param classifierType the selected classifier type
     * @param selectedClassifierPan the selected classifier panel
     * @param numIteration the maximum number of allowed iterations that
     * algorithm repeated
     * @param colonySize the size of colony of candidate solutions
     * @param alpha the alpha parameter in the state transition rule that shows
     * the relative importance of the pheromone
     * @param beta the beta parameter in the state transition rule that shows
     * the relative importance of heuristic information
     * @param evRate the evaporation rate of the pheromone
     * @param kFolds the number of equal sized subsamples that is used in k-fold
     * cross validation
     */
    public void setUserValue(ClassifierType classifierType, Object selectedClassifierPan, 
            int numIteration, int colonySize, double alpha, double beta, double evRate, int kFolds) {
        this.classifierType = classifierType;
        this.numIteration = numIteration;
        this.colonySize = colonySize;
        this.alpha = alpha;
        this.beta = beta;
        this.evRate = evRate;
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
        txt_colonySize.setText(String.valueOf(this.colonySize));
        txt_alpha.setText(String.valueOf(this.alpha));
        txt_beta.setText(String.valueOf(this.beta));
        txt_evRate.setText(String.valueOf(this.evRate));
        txt_kFolds.setText(String.valueOf(this.kFolds));
    }
}
