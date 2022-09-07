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
package unifeat.gui.featureSelection.embedded;

import unifeat.gui.ParameterPanel;
import unifeat.gui.classifier.svmClassifier.SVMKernelType;
import unifeat.util.MathFunc;
import java.awt.Color;
import java.awt.Container;
//import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show a panel for the parameter settings
 * of the multiple support vector machine method based on recursive feature
 * elimination (MSVM_RFE).
 *
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.embedded.SVMBasedMethods.MSVM_RFE
 */
public class MSVM_RFEPanel extends ParameterPanel {

    JLabel lbl_kernel, lbl_parameterC, lbl_parameterCError,
            lbl_numFold, lbl_numFoldError,
            lbl_numRun, lbl_numRunError;
    JComboBox cb_kernel;
    JTextField txt_parameterC, txt_numFold, txt_numRun;
    private SVMKernelType typeKernel = SVMKernelType.POLYNOMIAL;
    private double parameterC = 1.0;
    private int numFold = 5, numRun = 20;
    private static final SVMKernelType DEFAULT_TYPE_KERNEL = SVMKernelType.POLYNOMIAL;
    private static final double DEFAULT_PARAMETER_C = 1.0;
    private static final int DEFAULT_NUM_FOLD = 5, DEFAULT_NUM_RUN = 20;

    /**
     * Creates new form MSVM_RFEPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public MSVM_RFEPanel() {
        super("Parameter Settings Panel",
                "MSVM_RFE settings:",
                "<html> MSVM_RFE method is used for binary classification problem in which multiple linear SVMs trained on subsamples of the original training data. K-fold cross validation is used as the resampling method.</html>",
                "Option\n\n"
                + "Kernel -> the kernel to use in SVM.\n\n"
                + "Parameter c -> The complexity parameter C in SVM.\n\n"
                + "Fold -> the number of subsamples in k-fold cross validation.\n\n"
                + "Number of runs -> the number of multiple runs of k-fold cross validation.\n\n",
                new Rectangle(10, 10, 160, 20),
                new Rectangle(10, 35, 400, 80),
                new Rectangle(120, 295, 75, 25),
                new Rectangle(240, 295, 75, 25),
                new Dimension(440, 395));

        Container contentPane = getContentPane();

        lbl_kernel = new JLabel("Kernel:");
        lbl_kernel.setBounds(50, 135, 120, 22);
        cb_kernel = new JComboBox(SVMKernelType.asList());
        cb_kernel.setBounds(140, 135, 245, 25);

        lbl_parameterC = new JLabel("Parameter c:");
        lbl_parameterC.setBounds(50, 170, 170, 22);
        txt_parameterC = new JTextField(Double.toString(DEFAULT_PARAMETER_C));
        txt_parameterC.setBounds(140, 170, 120, 24);
        txt_parameterC.addKeyListener(this);
        lbl_parameterCError = new JLabel("");
        lbl_parameterCError.setBounds(270, 170, 50, 22);
        lbl_parameterCError.setForeground(Color.red);

        lbl_numFold = new JLabel("Fold:");
        lbl_numFold.setBounds(50, 205, 170, 22);
        txt_numFold = new JTextField(Integer.toString(DEFAULT_NUM_FOLD));
        txt_numFold.setBounds(140, 205, 120, 24);
        txt_numFold.addKeyListener(this);
        lbl_numFoldError = new JLabel("");
        lbl_numFoldError.setBounds(270, 205, 50, 22);
        lbl_numFoldError.setForeground(Color.red);

        lbl_numRun = new JLabel("Number of runs:");
        lbl_numRun.setBounds(50, 240, 170, 22);
        txt_numRun = new JTextField(Integer.toString(DEFAULT_NUM_RUN));
        txt_numRun.setBounds(140, 240, 120, 24);
        txt_numRun.addKeyListener(this);
        lbl_numRunError = new JLabel("");
        lbl_numRunError.setBounds(270, 240, 50, 22);
        lbl_numRunError.setForeground(Color.red);

        contentPane.add(lbl_kernel);
        contentPane.add(cb_kernel);

        contentPane.add(lbl_parameterC);
        contentPane.add(txt_parameterC);
        contentPane.add(lbl_parameterCError);

        contentPane.add(lbl_numFold);
        contentPane.add(txt_numFold);
        contentPane.add(lbl_numFoldError);

        contentPane.add(lbl_numRun);
        contentPane.add(txt_numRun);
        contentPane.add(lbl_numRunError);

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

        tempStr = txt_parameterC.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) < 0) {
            lbl_parameterCError.setText("*");
            enableOkButton = false;
        } else {
            lbl_parameterCError.setText("");
        }

        tempStr = txt_numFold.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 2)) {
            lbl_numFoldError.setText("*");
            enableOkButton = false;
        } else {
            lbl_numFoldError.setText("");
        }

        tempStr = txt_numRun.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 1)) {
            lbl_numRunError.setText("*");
            enableOkButton = false;
        } else {
            lbl_numRunError.setText("");
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
        setKernel(SVMKernelType.parse(cb_kernel.getSelectedItem().toString()));
        setParameterC(Double.parseDouble(txt_parameterC.getText()));
        setNumFold(Integer.parseInt(txt_numFold.getText()));
        setNumRun(Integer.parseInt(txt_numRun.getText()));
        super.btn_okActionPerformed(e);
    }

    /**
     * This method returns the name of kernel.
     *
     * @return the <code>Kernel</code> parameter
     */
    public SVMKernelType getKernel() {
        return typeKernel;
    }

    /**
     * This method sets the name of kernel.
     *
     * @param kernelName the name of kernel
     */
    public void setKernel(SVMKernelType kernelName) {
        this.typeKernel = kernelName;
    }

    /**
     * This method returns the complexity parameter C.
     *
     * @return the <code>C</code> parameter
     */
    public double getParameterC() {
        return parameterC;
    }

    /**
     * This method sets the complexity parameter C.
     *
     * @param c the complexity parameter C
     */
    public void setParameterC(double c) {
        this.parameterC = c;
    }

    /**
     * This method returns the number of subsamples value.
     *
     * @return the <code>numFold</code> parameter
     */
    public int getNumFold() {
        return numFold;
    }

    /**
     * This method sets the number of subsamples value.
     *
     * @param numFold the number of subsamples value
     */
    public void setNumFold(int numFold) {
        this.numFold = numFold;
    }

    /**
     * This method returns the number of runs value.
     *
     * @return the <code>numRun</code> parameter
     */
    public int getNumRun() {
        return numRun;
    }

    /**
     * This method sets the number of runs value.
     *
     * @param numRun the number of runs value
     */
    public void setNumRun(int numRun) {
        this.numRun = numRun;
    }

    /**
     * Sets the default values of the MSVM_RFE parameters
     */
    public void setDefaultValue() {
        cb_kernel.setSelectedItem(DEFAULT_TYPE_KERNEL.toString());
        txt_parameterC.setText(String.valueOf(DEFAULT_PARAMETER_C));
        txt_numFold.setText(String.valueOf(DEFAULT_NUM_FOLD));
        txt_numRun.setText(String.valueOf(DEFAULT_NUM_RUN));
        typeKernel = DEFAULT_TYPE_KERNEL;
        parameterC = DEFAULT_PARAMETER_C;
        numFold = DEFAULT_NUM_FOLD;
        numRun = DEFAULT_NUM_RUN;
    }

    /**
     * Sets the last values of the MSVM_RFE parameters entered by user
     *
     * @param type the name of the kernel
     * @param c the complexity parameter C
     * @param numFold the number of subsamples in k-fold cross validation
     * @param numRun the number of multiple runs of k-fold CV
     */
    public void setUserValue(SVMKernelType type, double c, int numFold, int numRun) {
        typeKernel = type;
        parameterC = c;
        this.numFold = numFold;
        this.numRun = numRun;
        cb_kernel.setSelectedItem(typeKernel.toString());
        txt_parameterC.setText(String.valueOf(parameterC));
        txt_numFold.setText(String.valueOf(this.numFold));
        txt_numRun.setText(String.valueOf(this.numRun));
    }

    /**
     * Sets the status of the kernel's combo box
     *
     * @param status the status of the kernel
     */
    public void setEnableKernelType(boolean status) {
        cb_kernel.setEnabled(status);
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
//        MSVM_RFEPanel dtpanel = new MSVM_RFEPanel();
//        Dialog dlg = new Dialog(dtpanel);
//        dtpanel.setVisible(true);
//        System.out.println("kernel = " + dtpanel.getKernel().toString()
//                + "   C = " + dtpanel.getParameterC()
//                + "   Fold = " + dtpanel.getNumFold()
//                + "   numRun = " + dtpanel.getNumRun());
//        dtpanel.setDefaultValue();
//        System.out.println("kernel = " + dtpanel.getKernel().toString()
//                + "   C = " + dtpanel.getParameterC()
//                + "   Fold = " + dtpanel.getNumFold()
//                + "   numRun = " + dtpanel.getNumRun());
//        dtpanel.setUserValue(SVMKernelType.RBF, 2.0, 7, 10);
//        System.out.println("kernel = " + dtpanel.getKernel().toString()
//                + "   C = " + dtpanel.getParameterC()
//                + "   Fold = " + dtpanel.getNumFold()
//                + "   numRun = " + dtpanel.getNumRun());
//        dtpanel.setDefaultValue();
//        System.out.println("kernel = " + dtpanel.getKernel().toString()
//                + "   C = " + dtpanel.getParameterC()
//                + "   Fold = " + dtpanel.getNumFold()
//                + "   numRun = " + dtpanel.getNumRun());
//    }
}
