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
 * of the basic genetic algorithm (BasicGA) method.
 *
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.wrapper.GABasedMethods.BasicGA
 */
public abstract class BasicGAPanel extends ParameterPanel
        implements ItemListener {

    JComboBox cb_classifierType, cb_selectionType, cb_crossoverType,
            cb_mutationType, cb_replacementType;
    protected JButton btn_classifierType;
    JLabel lbl_classifierType, lbl_selectionType, lbl_crossoverType,
            lbl_mutationType, lbl_replacementType,
            lbl_numIteration, lbl_numIterationError,
            lbl_populationSize, lbl_populationSizeError,
            lbl_crossoverRate, lbl_crossoverRateError,
            lbl_mutationRate, lbl_mutationRateError,
            lbl_kFolds, lbl_kFoldsError;
    JTextField txt_numIteration, txt_populationSize,
            txt_crossoverRate, txt_mutationRate,
            txt_kFolds;
    private double crossoverRate = 0.6, mutationRate = 0.02;
    private static double DEFAULT_CROSSOVER_RATE = 0.6, DEFAULT_MUTATION_RATE = 0.02;
    private int numIteration = 100, populationSize = 30, kFolds = 10;
    private static int DEFAULT_NUM_ITERATION = 100, DEFAULT_POPULATION_SIZE = 30,
            DEFAULT_K_FOLDS = 10;
    private ClassifierType classifierType = ClassifierType.NB;
    private static final ClassifierType DEFAULT_CLASSIFIER_TYPE = ClassifierType.NB;
    private SelectionType selectionType = SelectionType.FITNESS_PROPORTIONAL_SELECTION;
    private CrossOverType crossoverType = CrossOverType.ONE_POINT_CROSS_OVER;
    private MutationType mutationType = MutationType.BITWISE_MUTATION;
    private ReplacementType replacementType = ReplacementType.TOTAL_REPLACEMENT;
    private static SelectionType DEFAULT_SELECTION_TYPE = SelectionType.FITNESS_PROPORTIONAL_SELECTION;
    private static CrossOverType DEFAULT_CROSSOVER_TYPE = CrossOverType.ONE_POINT_CROSS_OVER;
    private static MutationType DEFAULT_MUTATION_TYPE = MutationType.BITWISE_MUTATION;
    private static ReplacementType DEFAULT_REPLACEMENT_TYPE = ReplacementType.TOTAL_REPLACEMENT;
    //-------------- Evaluation classifier -----------------------------
    private SVMClassifierPanel svmEvaluationPanel;
    private DTClassifierPanel dtEvaluationPanel;
    private KNNClassifierPanel knnEvaluationPanel;
    private Object selectedClassifierPan = null;

    /**
     * Creates new form BasicGAPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public BasicGAPanel() {
        super("Parameter Settings Panel",
                "GA Based methods settings:",
                "<html> This is a template for feature selection methods based on genetic algorithm (GA). "
                + "In these methods, k-fold cross validation on training set is used for evaluating the "
                + "classification performance of a selected feature subset during feature selection process. </html>",
                "Option\n\n"
                + "Classifier type -> the classifier is used for evaluating the fitness of a solution.\n\n"
                + "Selection type -> the selection is used for selecting parents from the individuals of a population according to their fitness.\n\n"
                + "Crossover type -> the crossover is used for recombining the parents to generate new offsprings based on crossover rate.\n\n"
                + "Mutation type -> the mutation is used for mutating new offsprings by changing the value of some genes in them based on mutation rate.\n\n"
                + "Replacement type -> the replacement is used for handling populations from one generation to the next generation.\n\n"
                + "Number of iterations -> the maximum number of allowed iterations that algorithm repeated.\n\n"
                + "Population size -> the size of population of candidate solutions.\n\n"
                + "Crossover rate -> the probability of crossover operation (a real number in the range of (0, 1)).\n\n"
                + "Mutation rate -> the probability of mutation operation (a real number in the range of (0, 1)).\n\n"
                + "Folds -> the number of equal sized subsamples that is used in k-fold cross validation.\n\n",
                new Rectangle(10, 10, 540, 20),
                new Rectangle(10, 35, 545, 80),
                new Rectangle(190, 510, 75, 23),
                new Rectangle(310, 510, 75, 23),
                new Dimension(570, 600));

        Container contentPane = getContentPane();

        lbl_classifierType = new JLabel("Classifier type:");
        lbl_classifierType.setBounds(50, 155, 170, 22);
        cb_classifierType = new JComboBox();
        cb_classifierType.setModel(new DefaultComboBoxModel(ClassifierType.asList()));
        cb_classifierType.setSelectedIndex(ClassifierType.NB.getValue());
        cb_classifierType.setBounds(170, 155, 170, 22);
        cb_classifierType.addItemListener(this);

        btn_classifierType = new JButton("More option...");
        btn_classifierType.setBounds(350, 155, 105, 23);
        btn_classifierType.setEnabled(false);
        btn_classifierType.addActionListener(this);

        lbl_selectionType = new JLabel("Selection type:");
        lbl_selectionType.setBounds(50, 190, 170, 22);
        cb_selectionType = new JComboBox();
        cb_selectionType.setModel(new DefaultComboBoxModel(SelectionType.commonAsList()));
        cb_selectionType.setSelectedIndex(SelectionType.FITNESS_PROPORTIONAL_SELECTION.getValue());
        cb_selectionType.setBounds(170, 190, 170, 22);
        cb_selectionType.addItemListener(this);

        lbl_crossoverType = new JLabel("Crossover type:");
        lbl_crossoverType.setBounds(50, 225, 170, 22);
        cb_crossoverType = new JComboBox();
        cb_crossoverType.setModel(new DefaultComboBoxModel(CrossOverType.commonAsList()));
        cb_crossoverType.setSelectedIndex(CrossOverType.ONE_POINT_CROSS_OVER.getValue());
        cb_crossoverType.setBounds(170, 225, 170, 22);
        cb_crossoverType.addItemListener(this);

        lbl_mutationType = new JLabel("Mutation type:");
        lbl_mutationType.setBounds(50, 260, 170, 22);
        cb_mutationType = new JComboBox();
        cb_mutationType.setModel(new DefaultComboBoxModel(MutationType.commonAsList()));
        cb_mutationType.setSelectedIndex(MutationType.BITWISE_MUTATION.getValue());
        cb_mutationType.setBounds(170, 260, 170, 22);
        cb_mutationType.addItemListener(this);

        lbl_replacementType = new JLabel("Replacement type:");
        lbl_replacementType.setBounds(50, 295, 170, 22);
        cb_replacementType = new JComboBox();
        cb_replacementType.setModel(new DefaultComboBoxModel(ReplacementType.commonAsList()));
        cb_replacementType.setSelectedIndex(ReplacementType.TOTAL_REPLACEMENT.getValue());
        cb_replacementType.setBounds(170, 295, 170, 22);
        cb_replacementType.addItemListener(this);

        lbl_numIteration = new JLabel("Number of iterations:");
        lbl_numIteration.setBounds(50, 330, 170, 22);
        txt_numIteration = new JTextField(Integer.toString(DEFAULT_NUM_ITERATION));
        txt_numIteration.setBounds(170, 330, 120, 21);
        txt_numIteration.addKeyListener(this);
        lbl_numIterationError = new JLabel("");
        lbl_numIterationError.setBounds(300, 330, 50, 22);
        lbl_numIterationError.setForeground(Color.red);

        lbl_populationSize = new JLabel("Population size:");
        lbl_populationSize.setBounds(50, 365, 170, 22);
        txt_populationSize = new JTextField(Integer.toString(DEFAULT_POPULATION_SIZE));
        txt_populationSize.setBounds(170, 365, 120, 21);
        txt_populationSize.addKeyListener(this);
        lbl_populationSizeError = new JLabel("");
        lbl_populationSizeError.setBounds(300, 365, 50, 22);
        lbl_populationSizeError.setForeground(Color.red);

        lbl_crossoverRate = new JLabel("Crossover rate:");
        lbl_crossoverRate.setBounds(50, 400, 170, 22);
        txt_crossoverRate = new JTextField(Double.toString(DEFAULT_CROSSOVER_RATE));
        txt_crossoverRate.setBounds(170, 400, 120, 21);
        txt_crossoverRate.addKeyListener(this);
        lbl_crossoverRateError = new JLabel("");
        lbl_crossoverRateError.setBounds(300, 400, 50, 22);
        lbl_crossoverRateError.setForeground(Color.red);

        lbl_mutationRate = new JLabel("Mutation rate:");
        lbl_mutationRate.setBounds(50, 435, 170, 22);
        txt_mutationRate = new JTextField(Double.toString(DEFAULT_MUTATION_RATE));
        txt_mutationRate.setBounds(170, 435, 120, 21);
        txt_mutationRate.addKeyListener(this);
        lbl_mutationRateError = new JLabel("");
        lbl_mutationRateError.setBounds(300, 435, 50, 22);
        lbl_mutationRateError.setForeground(Color.red);

        lbl_kFolds = new JLabel("Folds:");
        lbl_kFolds.setBounds(50, 470, 170, 22);
        txt_kFolds = new JTextField(Integer.toString(DEFAULT_K_FOLDS));
        txt_kFolds.setBounds(170, 470, 120, 21);
        txt_kFolds.addKeyListener(this);
        lbl_kFoldsError = new JLabel("");
        lbl_kFoldsError.setBounds(300, 470, 50, 22);
        lbl_kFoldsError.setForeground(Color.red);

        contentPane.add(lbl_classifierType);
        contentPane.add(cb_classifierType);
        contentPane.add(btn_classifierType);

        contentPane.add(lbl_selectionType);
        contentPane.add(cb_selectionType);

        contentPane.add(lbl_crossoverType);
        contentPane.add(cb_crossoverType);

        contentPane.add(lbl_mutationType);
        contentPane.add(cb_mutationType);

        contentPane.add(lbl_replacementType);
        contentPane.add(cb_replacementType);

        contentPane.add(lbl_numIteration);
        contentPane.add(txt_numIteration);
        contentPane.add(lbl_numIterationError);

        contentPane.add(lbl_populationSize);
        contentPane.add(txt_populationSize);
        contentPane.add(lbl_populationSizeError);

        contentPane.add(lbl_crossoverRate);
        contentPane.add(txt_crossoverRate);
        contentPane.add(lbl_crossoverRateError);

        contentPane.add(lbl_mutationRate);
        contentPane.add(txt_mutationRate);
        contentPane.add(lbl_mutationRateError);

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

        if (SelectionType.parse(cb_selectionType.getSelectedItem().toString()) == SelectionType.NONE) {
            enableOkButton = false;
        }

        if (CrossOverType.parse(cb_crossoverType.getSelectedItem().toString()) == CrossOverType.NONE) {
            enableOkButton = false;
        }

        if (MutationType.parse(cb_mutationType.getSelectedItem().toString()) == MutationType.NONE) {
            enableOkButton = false;
        }

        if (ReplacementType.parse(cb_replacementType.getSelectedItem().toString()) == ReplacementType.NONE) {
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

        tempStr = txt_crossoverRate.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0 || Double.parseDouble(tempStr) >= 1) {
            lbl_crossoverRateError.setText("*");
            enableOkButton = false;
        } else {
            lbl_crossoverRateError.setText("");
        }

        tempStr = txt_mutationRate.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0 || Double.parseDouble(tempStr) >= 1) {
            lbl_mutationRateError.setText("*");
            enableOkButton = false;
        } else {
            lbl_mutationRateError.setText("");
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
        } else if (e.getSource().equals(cb_selectionType)) {
            cb_selectionTypeItemStateChanged(e);
        } else if (e.getSource().equals(cb_crossoverType)) {
            cb_crossoverTypeItemStateChanged(e);
        } else if (e.getSource().equals(cb_mutationType)) {
            cb_mutationTypeItemStateChanged(e);
        } else if (e.getSource().equals(cb_replacementType)) {
            cb_replacementTypeItemStateChanged(e);
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
     * This method sets an action for the cb_selectionType combo box.
     *
     * @param e an action event
     */
    private void cb_selectionTypeItemStateChanged(ItemEvent e) {
        selectionType = SelectionType.parse(cb_selectionType.getSelectedItem().toString());
        keyReleased(null);
    }

    /**
     * This method sets an action for the cb_crossoverType combo box.
     *
     * @param e an action event
     */
    private void cb_crossoverTypeItemStateChanged(ItemEvent e) {
        crossoverType = CrossOverType.parse(cb_crossoverType.getSelectedItem().toString());
        keyReleased(null);
    }

    /**
     * This method sets an action for the cb_mutationType combo box.
     *
     * @param e an action event
     */
    private void cb_mutationTypeItemStateChanged(ItemEvent e) {
        mutationType = MutationType.parse(cb_mutationType.getSelectedItem().toString());
        keyReleased(null);
    }

    /**
     * This method sets an action for the cb_replacementType combo box.
     *
     * @param e an action event
     */
    private void cb_replacementTypeItemStateChanged(ItemEvent e) {
        replacementType = ReplacementType.parse(cb_replacementType.getSelectedItem().toString());
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
        setCrossoverRate(Double.parseDouble(txt_crossoverRate.getText()));
        setMutationRate(Double.parseDouble(txt_mutationRate.getText()));
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
     * This method returns the selected selection type.
     *
     * @return the <code>selectionType</code> parameter
     */
    public SelectionType getSelectionType() {
        return selectionType;
    }

    /**
     * This method sets the selected selection type.
     *
     * @param selectionType the selected selection type
     */
    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

    /**
     * This method returns the selected crossover type.
     *
     * @return the <code>crossoverType</code> parameter
     */
    public CrossOverType getCrossOverType() {
        return crossoverType;
    }

    /**
     * This method sets the selected crossover type.
     *
     * @param crossoverType the selected crossover type
     */
    public void setCrossOverType(CrossOverType crossoverType) {
        this.crossoverType = crossoverType;
    }

    /**
     * This method returns the selected mutation type.
     *
     * @return the <code>mutationType</code> parameter
     */
    public MutationType getMutationType() {
        return mutationType;
    }

    /**
     * This method sets the selected mutation type.
     *
     * @param mutationType the selected mutation type
     */
    public void setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
    }

    /**
     * This method returns the selected replacement type.
     *
     * @return the <code>replacementType</code> parameter
     */
    public ReplacementType getReplacementType() {
        return replacementType;
    }

    /**
     * This method sets the selected replacement type.
     *
     * @param replacementType the selected replacement type
     */
    public void setReplacementType(ReplacementType replacementType) {
        this.replacementType = replacementType;
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
     * This method returns the crossover rate value.
     *
     * @return the <code>crossoverRate</code> parameter
     */
    public double getCrossoverRate() {
        return crossoverRate;
    }

    /**
     * This method sets the crossover rate value.
     *
     * @param crossoverRate the crossover rate value
     */
    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    /**
     * This method returns the mutation rate value.
     *
     * @return the <code>mutationRate</code> parameter
     */
    public double getMutationRate() {
        return mutationRate;
    }

    /**
     * This method sets the mutation rate value.
     *
     * @param mutationRate the mutation rate value
     */
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
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
     * Sets the default values of the basic GA parameters
     */
    public void setDefaultValue() {
        cb_classifierType.setSelectedIndex(DEFAULT_CLASSIFIER_TYPE.getValue()); // set on naive bayes classifier
        cb_selectionType.setSelectedIndex(DEFAULT_SELECTION_TYPE.getValue()); // set on fitness proportional selection
        cb_crossoverType.setSelectedIndex(DEFAULT_CROSSOVER_TYPE.getValue()); // set on one-point crossover
        cb_mutationType.setSelectedIndex(DEFAULT_MUTATION_TYPE.getValue()); // set on bitwise mutation
        cb_replacementType.setSelectedIndex(DEFAULT_REPLACEMENT_TYPE.getValue()); // set on total replacement
        txt_numIteration.setText(String.valueOf(DEFAULT_NUM_ITERATION));
        txt_populationSize.setText(String.valueOf(DEFAULT_POPULATION_SIZE));
        txt_crossoverRate.setText(String.valueOf(DEFAULT_CROSSOVER_RATE));
        txt_mutationRate.setText(String.valueOf(DEFAULT_MUTATION_RATE));
        txt_kFolds.setText(String.valueOf(DEFAULT_K_FOLDS));

        classifierType = DEFAULT_CLASSIFIER_TYPE;
        selectedClassifierPan = null; // set on naive bayes classifier
        selectionType = DEFAULT_SELECTION_TYPE;
        crossoverType = DEFAULT_CROSSOVER_TYPE;
        mutationType = DEFAULT_MUTATION_TYPE;
        replacementType = DEFAULT_REPLACEMENT_TYPE;
        numIteration = DEFAULT_NUM_ITERATION;
        populationSize = DEFAULT_POPULATION_SIZE;
        crossoverRate = DEFAULT_CROSSOVER_RATE;
        mutationRate = DEFAULT_MUTATION_RATE;
        kFolds = DEFAULT_K_FOLDS;
    }

    /**
     * Replaces the default values of basic GA parameters with user values
     *
     * @param selectionType the selected selection type
     * @param crossoverType the selected crossover type
     * @param mutationType the selected mutation type
     * @param replacementType the selected replacement type
     * @param numIteration the maximum number of allowed iterations that
     * algorithm repeated
     * @param populationSize the size of population of candidate solutions
     * @param crossoverRate the probability of crossover operation
     * @param mutationRate the probability of mutation operation
     * @param kFolds the number of equal sized subsamples that is used in k-fold
     * cross validation
     */
    public void changeDefaultValue(SelectionType selectionType, CrossOverType crossoverType,
            MutationType mutationType, ReplacementType replacementType,
            int numIteration, int populationSize,
            double crossoverRate, double mutationRate, int kFolds) {
        this.selectionType = selectionType;
        this.crossoverType = crossoverType;
        this.mutationType = mutationType;
        this.replacementType = replacementType;
        this.numIteration = numIteration;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.kFolds = kFolds;

        DEFAULT_SELECTION_TYPE = this.selectionType;
        DEFAULT_CROSSOVER_TYPE = this.crossoverType;
        DEFAULT_MUTATION_TYPE = this.mutationType;
        DEFAULT_REPLACEMENT_TYPE = this.replacementType;
        DEFAULT_NUM_ITERATION = this.numIteration;
        DEFAULT_POPULATION_SIZE = this.populationSize;
        DEFAULT_CROSSOVER_RATE = this.crossoverRate;
        DEFAULT_MUTATION_RATE = this.mutationRate;
        DEFAULT_K_FOLDS = this.kFolds;

        cb_selectionType.setSelectedIndex(this.selectionType.getValue());
        cb_crossoverType.setSelectedIndex(this.crossoverType.getValue());
        cb_mutationType.setSelectedIndex(this.mutationType.getValue());
        cb_replacementType.setSelectedIndex(this.replacementType.getValue());
        txt_numIteration.setText(String.valueOf(DEFAULT_NUM_ITERATION));
        txt_populationSize.setText(String.valueOf(DEFAULT_POPULATION_SIZE));
        txt_crossoverRate.setText(String.valueOf(DEFAULT_CROSSOVER_RATE));
        txt_mutationRate.setText(String.valueOf(DEFAULT_MUTATION_RATE));
        txt_kFolds.setText(String.valueOf(DEFAULT_K_FOLDS));
    }

    /**
     * Sets the last values of the basic GA parameters entered by user
     *
     * @param classifierType the selected classifier type
     * @param selectedClassifierPan the selected classifier panel
     * @param selectionType the selected selection type
     * @param crossoverType the selected crossover type
     * @param mutationType the selected mutation type
     * @param replacementType the selected replacement type
     * @param numIteration the maximum number of allowed iterations that
     * algorithm repeated
     * @param populationSize the size of population of candidate solutions
     * @param crossoverRate the probability of crossover operation
     * @param mutationRate the probability of mutation operation
     * @param kFolds the number of equal sized subsamples that is used in k-fold
     * cross validation
     */
    public void setUserValue(ClassifierType classifierType, Object selectedClassifierPan,
            SelectionType selectionType, CrossOverType crossoverType,
            MutationType mutationType, ReplacementType replacementType,
            int numIteration, int populationSize,
            double crossoverRate, double mutationRate, int kFolds) {
        this.classifierType = classifierType;
        this.selectionType = selectionType;
        this.crossoverType = crossoverType;
        this.mutationType = mutationType;
        this.replacementType = replacementType;
        this.numIteration = numIteration;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
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

        cb_selectionType.setSelectedIndex(this.selectionType.getValue());
        cb_crossoverType.setSelectedIndex(this.crossoverType.getValue());
        cb_mutationType.setSelectedIndex(this.mutationType.getValue());
        cb_replacementType.setSelectedIndex(this.replacementType.getValue());
        txt_numIteration.setText(String.valueOf(this.numIteration));
        txt_populationSize.setText(String.valueOf(this.populationSize));
        txt_crossoverRate.setText(String.valueOf(this.crossoverRate));
        txt_mutationRate.setText(String.valueOf(this.mutationRate));
        txt_kFolds.setText(String.valueOf(this.kFolds));
    }
}
