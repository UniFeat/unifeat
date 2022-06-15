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

//import unifeat.classifier.ClassifierType;
//import unifeat.gui.classifier.DTClassifierPanel;
//import unifeat.gui.classifier.KNNClassifierPanel;
//import unifeat.gui.classifier.svmClassifier.SVMClassifierPanel;
//import java.awt.Dialog;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show a panel for the parameter settings
 * of the binary particle swarm optimization (BPSO) method.
 *
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.wrapper.PSOBasedMethods.BPSO.BPSO
 */
public class BPSOPanel extends BasicPSOPanel {

    /**
     * Creates new form BPSOPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public BPSOPanel() {
        super();

        this.setMethodTitle("Binary PSO method settings:");
        this.setMethodDescription("<html> Feature selection based on binary particle swarm optimization (BPSO) "
                + "is basic PSO method in which each particle is randomly initialized. Additionally, "
                + "k-fold cross validation on training set is used for evaluating the classification "
                + "performance of a selected feature subset during feature selection process. </html>");
        this.enablePositionValues(false);
    }

//    public static void main(String[] arg) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            System.out.println("Error setting native LAF: " + e);
//        }
//
//        BPSOPanel dtpanel = new BPSOPanel();
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
//
//        dtpanel.setUserValue(ClassifierType.SVM, new SVMClassifierPanel(), 20, 10, 0.6, 0.5, 0.3, 10, 20, 30, 40, 50);
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
//    }
}
