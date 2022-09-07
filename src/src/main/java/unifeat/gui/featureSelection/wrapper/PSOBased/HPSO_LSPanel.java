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
//import unifeat.gui.classifier.DTClassifierPanel;
//import unifeat.gui.classifier.KNNClassifierPanel;
//import unifeat.gui.classifier.svmClassifier.SVMClassifierPanel;
import unifeat.util.MathFunc;
import java.awt.Color;
import java.awt.Container;
//import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show a panel for the parameter settings
 * of the hybrid particle swarm optimization method using local search
 * (HPSO-LS).
 *
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.wrapper.PSOBasedMethods.HPSO_LS.HPSO_LS
 */
public class HPSO_LSPanel extends BasicPSOPanel {

    JLabel lbl_epsilon, lbl_epsilonError,
            lbl_alpha, lbl_alphaError;
    JTextField txt_epsilon, txt_alpha;
    private double epsilon = 0.5, alpha = 0.65;
    private static final double DEFAULT_EPSILON = 0.5, DEFAULT_ALPHA = 0.65;

    /**
     * Creates new form HPSO_LSPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public HPSO_LSPanel() {
        super();
        Container contentPane = getContentPane();

        lbl_epsilon = new JLabel("Parameter epsilon:");
        lbl_epsilon.setBounds(50, 450, 170, 22);
        txt_epsilon = new JTextField(String.valueOf(DEFAULT_EPSILON));
        txt_epsilon.setBounds(170, 450, 120, 24);
        txt_epsilon.addKeyListener(this);
        lbl_epsilonError = new JLabel("");
        lbl_epsilonError.setBounds(300, 450, 50, 22);
        lbl_epsilonError.setForeground(Color.red);

        lbl_alpha = new JLabel("Parameter alpha:");
        lbl_alpha.setBounds(50, 485, 170, 22);
        txt_alpha = new JTextField(String.valueOf(DEFAULT_ALPHA));
        txt_alpha.setBounds(170, 485, 120, 24);
        txt_alpha.addKeyListener(this);
        lbl_alphaError = new JLabel("");
        lbl_alphaError.setBounds(300, 485, 50, 22);
        lbl_alphaError.setForeground(Color.red);

        contentPane.add(lbl_epsilon);
        contentPane.add(txt_epsilon);
        contentPane.add(lbl_epsilonError);

        contentPane.add(lbl_alpha);
        contentPane.add(txt_alpha);
        contentPane.add(lbl_alphaError);

        this.setMethodTitle("HPSO_LS method settings:");
        this.setMethodDescription("<html> Hybrid particle swarm optimization method using local search (HPSO-LS)"
                + " is a version of PSO which uses a local search strategy to select the less correlated feature subset."
                + " Also, a subset size determination scheme is used to select a subset of features with reduced size. </html>");
        this.setMoreOptionDescription(super.getMoreOptionDescription()
                + "Parameter epsilon -> the epsilon parameter used in the subset size determining scheme (a real number in the range of (0, 1)).\n\n"
                + "Parameter alpha -> the alpha parameter used in the local search operation to control similar/dissimilar ((a real number in the range of (0, 1))).\n\n");
        this.setOkButtonPosition(new Rectangle(190, 530, 75, 25));
        this.setMoreButtonPosition(new Rectangle(310, 530, 75, 25));
        this.setPanelSize(new Dimension(575, 610));
        this.enablePositionValues(false);
        this.changeDefaultValue(100, 40, 1.0, 2.0, 2.0, 0.0, 1.0, -1.0, 1.0, 10);

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
        super.keyReleased(e);
        boolean enableOkButton = btn_ok.isEnabled();
        String tempStr;

        tempStr = txt_epsilon.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0 || Double.parseDouble(tempStr) >= 1) {
            lbl_epsilonError.setText("*");
            enableOkButton = false;
        } else {
            lbl_epsilonError.setText("");
        }

        tempStr = txt_alpha.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0 || Double.parseDouble(tempStr) >= 1) {
            lbl_alphaError.setText("*");
            enableOkButton = false;
        } else {
            lbl_alphaError.setText("");
        }

        btn_ok.setEnabled(enableOkButton);
    }

    /**
     * This method sets an action for the btn_ok button.
     *
     * @param e an action event
     */
    @Override
    protected void btn_okActionPerformed(ActionEvent e) {
        super.btn_okActionPerformed(e);
        setEpsilon(Double.parseDouble(txt_epsilon.getText()));
        setAlpha(Double.parseDouble(txt_alpha.getText()));
    }

    /**
     * This method returns the epsilon value.
     *
     * @return the <code>epsilon</code> parameter
     */
    public double getEpsilon() {
        return epsilon;
    }

    /**
     * This method sets the epsilon value.
     *
     * @param epsilon the epsilon value
     */
    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    /**
     * This method returns the alpha value.
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
     * Sets the default values of the HPSO_LS parameters
     */
    @Override
    public void setDefaultValue() {
        super.setDefaultValue();
        txt_epsilon.setText(String.valueOf(DEFAULT_EPSILON));
        txt_alpha.setText(String.valueOf(DEFAULT_ALPHA));

        epsilon = DEFAULT_EPSILON;
        alpha = DEFAULT_ALPHA;
    }

    /**
     * Sets the last values of the HPSO_LS parameters entered by user
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
     * @param epsilon the epsilon parameter used in the subset size determining
     * scheme
     * @param alpha the alpha parameter used in the local search operation
     * (control similar/dissimilar)
     */
    public void setUserValue(ClassifierType classifierType, Object selectedClassifierPan, int numIteration, int populationSize,
            double inertiaWeight, double c1, double c2, double startPosInterval, double endPosInterval,
            double minVelocity, double maxVelocity, int kFolds, double epsilon, double alpha) {
        super.setUserValue(classifierType, selectedClassifierPan, numIteration, populationSize,
                inertiaWeight, c1, c2, startPosInterval, endPosInterval, minVelocity, maxVelocity, kFolds);
        this.epsilon = epsilon;
        this.alpha = alpha;

        txt_epsilon.setText(String.valueOf(this.epsilon));
        txt_alpha.setText(String.valueOf(this.alpha));
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
//        HPSO_LSPanel dtpanel = new HPSO_LSPanel();
//        Dialog dlg = new Dialog(dtpanel);
//        dtpanel.setVisible(true);
//        System.out.println("classifier type = " + dtpanel.getClassifierType().toString());
//        if (dtpanel.getClassifierType() == ClassifierType.SVM) {
//            SVMClassifierPanel pan = (SVMClassifierPanel) dtpanel.getSelectedClassifierPan();
//            System.out.println("user:   kernel = " + pan.getKernel().toString()
//                    + "   C = " + pan.getParameterC());
//        } else if (dtpanel.getClassifierType() == ClassifierType.DT) {
//            DTClassifierPanel pan = (DTClassifierPanel) dtpanel.getSelectedClassifierPan();
//            System.out.println("user:    min num = " + pan.getMinNum()
//                    + "  confidence = " + pan.getConfidence());
//        } else if (dtpanel.getClassifierType() == ClassifierType.KNN) {
//            KNNClassifierPanel pan = (KNNClassifierPanel) dtpanel.getSelectedClassifierPan();
//            System.out.println("user:    KNN Value = " + pan.getKNNValue());
//        }
//        System.out.println("num iteration = " + dtpanel.getNumIteration());
//        System.out.println("population size = " + dtpanel.getPopulationSize());
//        System.out.println("inertia weight = " + dtpanel.getInertiaWeight());
//        System.out.println("c1 = " + dtpanel.getC1());
//        System.out.println("c2 = " + dtpanel.getC2());
//        System.out.println("start position = " + dtpanel.getStartPosInterval());
//        System.out.println("end position = " + dtpanel.getEndPosInterval());
//        System.out.println("max velocity = " + dtpanel.getMinVelocity());
//        System.out.println("min velocity = " + dtpanel.getMaxVelocity());
//        System.out.println("K fold = " + dtpanel.getKFolds());
//        System.out.println("epsilon = " + dtpanel.getEpsilon());
//        System.out.println("alpha = " + dtpanel.getAlpha());
//
//        dtpanel.setDefaultValue();
//
//        System.out.println("classifier type = " + dtpanel.getClassifierType().toString());
//        if (dtpanel.getClassifierType() == ClassifierType.SVM) {
//            SVMClassifierPanel pan = (SVMClassifierPanel) dtpanel.getSelectedClassifierPan();
//            System.out.println("user:   kernel = " + pan.getKernel().toString()
//                    + "   C = " + pan.getParameterC());
//        } else if (dtpanel.getClassifierType() == ClassifierType.DT) {
//            DTClassifierPanel pan = (DTClassifierPanel) dtpanel.getSelectedClassifierPan();
//            System.out.println("user:    min num = " + pan.getMinNum()
//                    + "  confidence = " + pan.getConfidence());
//        } else if (dtpanel.getClassifierType() == ClassifierType.KNN) {
//            KNNClassifierPanel pan = (KNNClassifierPanel) dtpanel.getSelectedClassifierPan();
//            System.out.println("user:    KNN Value = " + pan.getKNNValue());
//        }
//        System.out.println("num iteration = " + dtpanel.getNumIteration());
//        System.out.println("population size = " + dtpanel.getPopulationSize());
//        System.out.println("inertia weight = " + dtpanel.getInertiaWeight());
//        System.out.println("c1 = " + dtpanel.getC1());
//        System.out.println("c2 = " + dtpanel.getC2());
//        System.out.println("start position = " + dtpanel.getStartPosInterval());
//        System.out.println("end position = " + dtpanel.getEndPosInterval());
//        System.out.println("max velocity = " + dtpanel.getMinVelocity());
//        System.out.println("min velocity = " + dtpanel.getMaxVelocity());
//        System.out.println("K fold = " + dtpanel.getKFolds());
//        System.out.println("epsilon = " + dtpanel.getEpsilon());
//        System.out.println("alpha = " + dtpanel.getAlpha());
//
//        dtpanel.setUserValue(ClassifierType.SVM, new SVMClassifierPanel(), 20, 10, 0.6, 0.5, 0.3, 10, 20, 30, 40, 50, 0.8, 0.9);
//
//        System.out.println("classifier type = " + dtpanel.getClassifierType().toString());
//        if (dtpanel.getClassifierType() == ClassifierType.SVM) {
//            SVMClassifierPanel pan = (SVMClassifierPanel) dtpanel.getSelectedClassifierPan();
//            System.out.println("user:   kernel = " + pan.getKernel().toString()
//                    + "   C = " + pan.getParameterC());
//        } else if (dtpanel.getClassifierType() == ClassifierType.DT) {
//            DTClassifierPanel pan = (DTClassifierPanel) dtpanel.getSelectedClassifierPan();
//            System.out.println("user:    min num = " + pan.getMinNum()
//                    + "  confidence = " + pan.getConfidence());
//        } else if (dtpanel.getClassifierType() == ClassifierType.KNN) {
//            KNNClassifierPanel pan = (KNNClassifierPanel) dtpanel.getSelectedClassifierPan();
//            System.out.println("user:    KNN Value = " + pan.getKNNValue());
//        }
//        System.out.println("num iteration = " + dtpanel.getNumIteration());
//        System.out.println("population size = " + dtpanel.getPopulationSize());
//        System.out.println("inertia weight = " + dtpanel.getInertiaWeight());
//        System.out.println("c1 = " + dtpanel.getC1());
//        System.out.println("c2 = " + dtpanel.getC2());
//        System.out.println("start position = " + dtpanel.getStartPosInterval());
//        System.out.println("end position = " + dtpanel.getEndPosInterval());
//        System.out.println("min velocity = " + dtpanel.getMinVelocity());
//        System.out.println("max velocity = " + dtpanel.getMaxVelocity());
//        System.out.println("K fold = " + dtpanel.getKFolds());
//        System.out.println("epsilon = " + dtpanel.getEpsilon());
//        System.out.println("alpha = " + dtpanel.getAlpha());
//    }
}
