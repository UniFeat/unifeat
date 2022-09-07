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
 * of the optimal ant colony optimization (OptimalACO) method.
 *
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.wrapper.ACOBasedMethods.OptimalACO.OptimalACO
 */
public class OptimalACOPanel extends BasicACOPanel {

    JLabel lbl_initPheromone, lbl_initPheromoneError,
            lbl_phi, lbl_phiError;
    JTextField txt_initPheromone, txt_phi;
    private double initPheromone = 1.0, phi = 0.8;
    private static final double DEFAULT_INIT_PHEROMONE = 1.0, DEFAULT_PHI = 0.8;

    /**
     * Creates new form OptimalACOPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public OptimalACOPanel() {
        super();
        Container contentPane = getContentPane();

        lbl_initPheromone = new JLabel("Initial pheromone:");
        lbl_initPheromone.setBounds(50, 380, 170, 22);
        txt_initPheromone = new JTextField(Double.toString(DEFAULT_INIT_PHEROMONE));
        txt_initPheromone.setBounds(170, 380, 120, 24);
        txt_initPheromone.addKeyListener(this);
        lbl_initPheromoneError = new JLabel("");
        lbl_initPheromoneError.setBounds(300, 380, 50, 22);
        lbl_initPheromoneError.setForeground(Color.red);

        lbl_phi = new JLabel("Parameter phi:");
        lbl_phi.setBounds(50, 415, 170, 22);
        txt_phi = new JTextField(Double.toString(DEFAULT_PHI));
        txt_phi.setBounds(170, 415, 120, 24);
        txt_phi.addKeyListener(this);
        lbl_phiError = new JLabel("");
        lbl_phiError.setBounds(300, 415, 50, 22);
        lbl_phiError.setForeground(Color.red);

        contentPane.add(lbl_initPheromone);
        contentPane.add(txt_initPheromone);
        contentPane.add(lbl_initPheromoneError);

        contentPane.add(lbl_phi);
        contentPane.add(txt_phi);
        contentPane.add(lbl_phiError);

        this.setMethodTitle("Optimal ACO method settings:");
        this.setMethodDescription("<html> Optimal ant colony optimization (Optimal ACO) is a version of ACO in which the "
                + "classifier performance is used as heuristic information. Also, pheromone values are updated based on the "
                + "classifier performance and feature subset length. Additionally, k-fold cross validation on training set is used "
                + "for evaluating the classification performance of a selected feature subset during feature selection process. </html>");
        this.setMoreOptionDescription(super.getMoreOptionDescription()
                + "Initial pheromone -> the initial value of the pheromone.\n\n"
                + "Parameter phi -> the phi parameter in the pheromone update rule for "
                + "controlling the relative weight of classifier performance and feature "
                + "subset length (a real number in the range of (0, 1)).\n\n");
        this.setMethodDescriptionPosition(new Rectangle(10, 35, 550, 90));
        this.setOkButtonPosition(new Rectangle(190, 470, 75, 25));
        this.setMoreButtonPosition(new Rectangle(310, 470, 75, 25));
        this.setPanelSize(new Dimension(580, 560));

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

        tempStr = txt_initPheromone.getText();
        if (!MathFunc.isDouble(tempStr)) {
            lbl_initPheromoneError.setText("*");
            enableOkButton = false;
        } else {
            lbl_initPheromoneError.setText("");
        }

        tempStr = txt_phi.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0 || Double.parseDouble(tempStr) >= 1) {
            lbl_phiError.setText("*");
            enableOkButton = false;
        } else {
            lbl_phiError.setText("");
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
        setInitPheromone(Double.parseDouble(txt_initPheromone.getText()));
        setPhi(Double.parseDouble(txt_phi.getText()));
    }

    /**
     * This method returns the initial pheromone value.
     *
     * @return the <code>initPheromone</code> parameter
     */
    public double getInitPheromone() {
        return initPheromone;
    }

    /**
     * This method sets the initial pheromone value.
     *
     * @param initPheromone the initial pheromone value
     */
    public void setInitPheromone(double initPheromone) {
        this.initPheromone = initPheromone;
    }

    /**
     * This method returns the phi value.
     *
     * @return the <code>phi</code> parameter
     */
    public double getPhi() {
        return phi;
    }

    /**
     * This method sets the phi value.
     *
     * @param phi the phi value
     */
    public void setPhi(double phi) {
        this.phi = phi;
    }

    /**
     * Sets the default values of the Optimal ACO parameters
     */
    @Override
    public void setDefaultValue() {
        super.setDefaultValue();
        txt_initPheromone.setText(String.valueOf(DEFAULT_INIT_PHEROMONE));
        txt_phi.setText(String.valueOf(DEFAULT_PHI));

        initPheromone = DEFAULT_INIT_PHEROMONE;
        phi = DEFAULT_PHI;
    }

    /**
     * Sets the last values of the Optimal ACO parameters entered by user
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
     * @param initPheromone the initial value of the pheromone
     * @param phi the phi parameter in the pheromone update rule that shows the
     * relative importance of classifier performance
     */
    public void setUserValue(ClassifierType classifierType, Object selectedClassifierPan,
            int numIteration, int colonySize, double alpha, double beta, double evRate, int kFolds, double initPheromone, double phi) {
        super.setUserValue(classifierType, selectedClassifierPan, numIteration,
                colonySize, alpha, beta, evRate, kFolds);
        this.initPheromone = initPheromone;
        this.phi = phi;

        txt_initPheromone.setText(String.valueOf(this.initPheromone));
        txt_phi.setText(String.valueOf(this.phi));
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
//        OptimalACOPanel dtpanel = new OptimalACOPanel();
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
//        System.out.println("colony size = " + dtpanel.getColonySize());
//        System.out.println("alpha = " + dtpanel.getAlpha());
//        System.out.println("beta = " + dtpanel.getBeta());
//        System.out.println("evRate = " + dtpanel.getEvRate());
//        System.out.println("K fold = " + dtpanel.getKFolds());
//        System.out.println("init pheromone = " + dtpanel.getInitPheromone());
//        System.out.println("phi = " + dtpanel.getPhi());
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
//        System.out.println("colony size = " + dtpanel.getColonySize());
//        System.out.println("alpha = " + dtpanel.getAlpha());
//        System.out.println("beta = " + dtpanel.getBeta());
//        System.out.println("evRate = " + dtpanel.getEvRate());
//        System.out.println("K fold = " + dtpanel.getKFolds());
//        System.out.println("init pheromone = " + dtpanel.getInitPheromone());
//        System.out.println("phi = " + dtpanel.getPhi());
//
//        dtpanel.setUserValue(ClassifierType.SVM, new SVMClassifierPanel(), 20, 10, 0.66, 0.55, 0.33, 9, 1.5, 0.44);
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
//        System.out.println("colony size = " + dtpanel.getColonySize());
//        System.out.println("alpha = " + dtpanel.getAlpha());
//        System.out.println("beta = " + dtpanel.getBeta());
//        System.out.println("evRate = " + dtpanel.getEvRate());
//        System.out.println("K fold = " + dtpanel.getKFolds());
//        System.out.println("init pheromone = " + dtpanel.getInitPheromone());
//        System.out.println("phi = " + dtpanel.getPhi());
//    }
}
