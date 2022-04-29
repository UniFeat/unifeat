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
package unifeat.gui.featureSelection.filter;

import unifeat.gui.ParameterPanel;
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
 * of the Laplacian score method.
 *
 * @author Shahin Salavati
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.filter.unsupervised.LaplacianScore
 * @see unifeat.featureSelection.filter.supervised.LaplacianScore
 */
public class LaplacianScorePanel extends ParameterPanel {

    JLabel lbl_KNearest, lbl_KNearestError,
            lbl_constParam, lbl_constParamError;
    JTextField txt_KNearest,
            txt_constParam;
    private double constParam = 100;
    private static final double DEFAULT_CONST_PARAM = 100;
    private int KNearest = 5;
    private static final int DEFAULT_K_NEAREST = 5;

    /**
     * Creates new form LaplacianScorePanel. This method is called from within
     * the constructor to initialize the form.
     */
    public LaplacianScorePanel() {
        super("Parameter Settings Panel",
                "Laplacian score settings:",
                "<html>Laplacian score of a feature evaluates locality preserving power of the feature. It is assumed that if the distances between two samples are as small as possible, they are related to the same subject. Laplacian score can be applied in the two supervised and unsupervised modes. </html>",
                "Option\n\n"
                + "k-nearest neighbor -> determines the number of data used as nearest neighbor (this parameter is used in the unsupervised version of Laplacian score).\n\n"
                + "Constant parameter -> the normalize parameter.\n\n",
                new Rectangle(10, 10, 540, 20),
                new Rectangle(10, 35, 545, 80),
                new Rectangle(190, 220, 75, 23),
                new Rectangle(310, 220, 75, 23),
                new Dimension(570, 300));

        Container contentPane = getContentPane();

        lbl_KNearest = new JLabel("k-nearest neighbor:");
        lbl_KNearest.setBounds(50, 135, 170, 22);
        txt_KNearest = new JTextField(String.valueOf(DEFAULT_K_NEAREST));
        txt_KNearest.setBounds(170, 135, 120, 21);
        txt_KNearest.addKeyListener(this);
        lbl_KNearestError = new JLabel("");
        lbl_KNearestError.setBounds(300, 135, 50, 22);
        lbl_KNearestError.setForeground(Color.red);

        lbl_constParam = new JLabel("Constant parameter:");
        lbl_constParam.setBounds(50, 170, 170, 22);
        txt_constParam = new JTextField(String.valueOf(DEFAULT_CONST_PARAM));
        txt_constParam.setBounds(170, 170, 120, 21);
        txt_constParam.addKeyListener(this);
        lbl_constParamError = new JLabel("");
        lbl_constParamError.setBounds(300, 170, 50, 22);
        lbl_constParamError.setForeground(Color.red);

        contentPane.add(lbl_KNearest);
        contentPane.add(txt_KNearest);
        contentPane.add(lbl_KNearestError);

        contentPane.add(lbl_constParam);
        contentPane.add(txt_constParam);
        contentPane.add(lbl_constParamError);

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

        tempStr = txt_KNearest.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 1)) {
            lbl_KNearestError.setText("*");
            enableOkButton = false;
        } else {
            lbl_KNearestError.setText("");
        }

        tempStr = txt_constParam.getText();
        if (!MathFunc.isDouble(tempStr) || (Double.parseDouble(tempStr) <= 0)) {
            lbl_constParamError.setText("*");
            enableOkButton = false;
        } else {
            lbl_constParamError.setText("");
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
        setKNearest(Integer.parseInt(txt_KNearest.getText()));
        setConstParam(Double.parseDouble(txt_constParam.getText()));
        super.btn_okActionPerformed(e);
    }

    /**
     * This method returns the k-nearest neighbor value.
     *
     * @return the <code>k-nearest neighbor</code> parameter
     */
    public int getKNearest() {
        return KNearest;
    }

    /**
     * This method sets the k-nearest neighbor value.
     *
     * @param KNearest the k-nearest neighbor value.
     */
    public void setKNearest(int KNearest) {
        this.KNearest = KNearest;
    }

    /**
     * This method returns the const parameter value.
     *
     * @return the <code>const parameter</code>
     */
    public double getConstParam() {
        return constParam;
    }

    /**
     * This method sets the const parameter value.
     *
     * @param constParam the const parameter value
     */
    public void setConstParam(double constParam) {
        this.constParam = constParam;
    }

    /**
     * Sets the default values of the Laplacian score parameters
     */
    public void setDefaultValue() {
        txt_KNearest.setText(String.valueOf(DEFAULT_K_NEAREST));
        txt_constParam.setText(String.valueOf(DEFAULT_CONST_PARAM));

        KNearest = DEFAULT_K_NEAREST;
        constParam = DEFAULT_CONST_PARAM;
    }

    /**
     * Sets the last values of the Laplacian score parameters entered by user
     *
     * @param kNNValue the k-nearest neighbor value
     * @param constant the constant value used in the similarity measure
     */
    public void setUserValue(int kNNValue, double constant) {
        KNearest = kNNValue;
        constParam = constant;

        txt_KNearest.setText(String.valueOf(KNearest));
        txt_constParam.setText(String.valueOf(constParam));
    }

//    public static void main(String[] arg) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            System.out.println("Error setting native LAF: " + e);
//        }
//
//        LaplacianScorePanel dtpanel = new LaplacianScorePanel();
//        Dialog dlg = new Dialog(dtpanel);
//        dtpanel.setVisible(true);
//        System.out.println("K-Nearest neighbor = " + dtpanel.getKNearest());
//        System.out.println("const parameter = " + dtpanel.getConstParam());
//    }
}
