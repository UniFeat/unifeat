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
package unifeat.gui.classifier.svmClassifier;

import unifeat.gui.ParameterPanel;
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
 * of the support vector machine classifier.
 *
 * @author Shahin Salavati
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 */
public class SVMClassifierPanel extends ParameterPanel {

    JLabel lbl_kernel,
            lbl_parameterC, lbl_parameterCError;
    JComboBox cb_kernel;
    JTextField txt_parameterC;
    private SVMKernelType typeKernel = SVMKernelType.POLYNOMIAL;
    private double parameterC = 1.0;
    private static final SVMKernelType DEFAULT_TYPE_KERNEL = SVMKernelType.POLYNOMIAL;
    private static final double DEFAULT_PARAMETER_C = 1.0;

    /**
     * Creates new form SVMClassifierPanel. This method is called from within
     * the constructor to initialize the form.
     */
    public SVMClassifierPanel() {
        super("Parameter Settings Panel",
                "Support vector machine settings:",
                "The SVM classifier with one-vs-one strategy for the multiclass problems.",
                "Option\n\n"
                + "Kernel -> the kernel to use.\n\n"
                + "Parameter c -> The complexity parameter C.\n\n",
                new Rectangle(10, 10, 215, 20),
                new Rectangle(10, 35, 470, 60),
                new Rectangle(150, 225, 75, 25),
                new Rectangle(270, 225, 75, 25),
                new Dimension(500, 315));

        Container contentPane = getContentPane();

        lbl_kernel = new JLabel("Kernel:");
        lbl_kernel.setBounds(50, 135, 120, 22);
        cb_kernel = new JComboBox(SVMKernelType.asList());
        cb_kernel.setBounds(130, 135, 285, 25);

        lbl_parameterC = new JLabel("Parameter c:");
        lbl_parameterC.setBounds(50, 170, 170, 22);
        txt_parameterC = new JTextField(Double.toString(DEFAULT_PARAMETER_C));
        txt_parameterC.setBounds(130, 170, 130, 24);
        txt_parameterC.addKeyListener(this);
        lbl_parameterCError = new JLabel("");
        lbl_parameterCError.setBounds(265, 170, 50, 22);
        lbl_parameterCError.setForeground(Color.red);

        contentPane.add(lbl_kernel);
        contentPane.add(cb_kernel);

        contentPane.add(lbl_parameterC);
        contentPane.add(txt_parameterC);
        contentPane.add(lbl_parameterCError);

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
     * Sets the default values of the support vector machine parameters
     */
    public void setDefaultValue() {
        cb_kernel.setSelectedItem(DEFAULT_TYPE_KERNEL.toString());
        txt_parameterC.setText(String.valueOf(DEFAULT_PARAMETER_C));
        typeKernel = DEFAULT_TYPE_KERNEL;
        parameterC = DEFAULT_PARAMETER_C;
    }

    /**
     * Sets the last values of the support vector machine parameters entered by
     * user
     *
     * @param type the name of the kernel
     * @param c the complexity parameter C
     */
    public void setUserValue(SVMKernelType type, double c) {
        typeKernel = type;
        parameterC = c;
        cb_kernel.setSelectedItem(typeKernel.toString());
        txt_parameterC.setText(String.valueOf(parameterC));
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
//        SVMClassifierPanel dtpanel = new SVMClassifierPanel();
//        Dialog dlg = new Dialog(dtpanel);
//        dtpanel.setVisible(true);
//        System.out.println("kernel = " + dtpanel.getKernel().toString()
//                + "   C = " + dtpanel.getParameterC());
//        dtpanel.setDefaultValue();
//        System.out.println("kernel = " + dtpanel.getKernel().toString()
//                + "   C = " + dtpanel.getParameterC());
//        dtpanel.setUserValue(SVMKernelType.RBF, 2.0);
//        System.out.println("kernel = " + dtpanel.getKernel().toString()
//                + "   C = " + dtpanel.getParameterC());
//        dtpanel.setDefaultValue();
//        System.out.println("kernel = " + dtpanel.getKernel().toString()
//                + "   C = " + dtpanel.getParameterC());
//    }
}
