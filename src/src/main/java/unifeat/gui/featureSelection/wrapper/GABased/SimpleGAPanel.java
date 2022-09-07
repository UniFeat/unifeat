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

//import unifeat.classifier.ClassifierType;
//import unifeat.gui.classifier.DTClassifierPanel;
//import unifeat.gui.classifier.KNNClassifierPanel;
//import unifeat.gui.classifier.svmClassifier.SVMClassifierPanel;
import java.awt.Container;
//import java.awt.Dialog;
import java.awt.Rectangle;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show a panel for the parameter settings
 * of the simple genetic algorithm (Simple GA).
 *
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.wrapper.GABasedMethods.SimpleGA.SimpleGA
 */
public class SimpleGAPanel extends BasicGAPanel {

    /**
     * Creates new form SimpleGAPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public SimpleGAPanel() {
        super();
        Container contentPane = getContentPane();

        this.setMethodTitle("Simple GA method settings:");
        this.setMethodDescription("<html> Simple genetic algorithm (Simple GA) "
                + "is a version of GA in which each individual is randomly initialized. Additionally, "
                + "k-fold cross validation on training set is used for evaluating the classification "
                + "performance of a selected feature subset during feature selection process. </html>");

        this.setMethodDescriptionPosition(new Rectangle(10, 35, 540, 90));
        
        contentPane.validate();
//        contentPane.revalidate();
        contentPane.repaint();
//        this.pack();
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
//        SimpleGAPanel dtpanel = new SimpleGAPanel();
//        Dialog dlg = new Dialog(dtpanel);
//        dtpanel.setVisible(true);
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
//
//        System.out.println("selection type = " + dtpanel.getSelectionType().toString());
//        System.out.println("crossover type = " + dtpanel.getCrossOverType().toString());
//        System.out.println("mutation type = " + dtpanel.getMutationType().toString());
//        System.out.println("replacement type = " + dtpanel.getReplacementType().toString());
//        System.out.println("num iteration = " + dtpanel.getNumIteration());
//        System.out.println("population size = " + dtpanel.getPopulationSize());
//        System.out.println("crossover rate = " + dtpanel.getCrossoverRate());
//        System.out.println("mutation rate = " + dtpanel.getMutationRate());
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
//        System.out.println("selection type = " + dtpanel.getSelectionType().toString());
//        System.out.println("crossover type = " + dtpanel.getCrossOverType().toString());
//        System.out.println("mutation type = " + dtpanel.getMutationType().toString());
//        System.out.println("replacement type = " + dtpanel.getReplacementType().toString());
//        System.out.println("num iteration = " + dtpanel.getNumIteration());
//        System.out.println("population size = " + dtpanel.getPopulationSize());
//        System.out.println("crossover rate = " + dtpanel.getCrossoverRate());
//        System.out.println("mutation rate = " + dtpanel.getMutationRate());
//        System.out.println("K fold = " + dtpanel.getKFolds());
//
//        dtpanel.setUserValue(ClassifierType.SVM, new SVMClassifierPanel(),
//                SelectionType.RANK_BASED_SELECTION, CrossOverType.UNIFORM_CROSS_OVER,
//                MutationType.BITWISE_MUTATION, ReplacementType.TOTAL_REPLACEMENT,
//                20, 10, 0.2, 0.02, 4);
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
//        System.out.println("selection type = " + dtpanel.getSelectionType().toString());
//        System.out.println("crossover type = " + dtpanel.getCrossOverType().toString());
//        System.out.println("mutation type = " + dtpanel.getMutationType().toString());
//        System.out.println("replacement type = " + dtpanel.getReplacementType().toString());
//        System.out.println("num iteration = " + dtpanel.getNumIteration());
//        System.out.println("population size = " + dtpanel.getPopulationSize());
//        System.out.println("crossover rate = " + dtpanel.getCrossoverRate());
//        System.out.println("mutation rate = " + dtpanel.getMutationRate());
//        System.out.println("K fold = " + dtpanel.getKFolds());
//    }
}
