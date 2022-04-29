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
package unifeat.gui.classifier;

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
//import javax.swing.SwingUtilities;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show a panel for the parameter settings
 * of the decision tree classifier.
 *
 * @author Shahin Salavati
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 */
public class DTClassifierPanel extends ParameterPanel {

    JLabel lbl_confidence, lbl_minNum, lbl_confidenceError, lbl_minNumError;
    JTextField txt_confidence, txt_minNum;
    private static final double DEFAULT_CONFIDENCE = 0.25;
    private double confidence = 0.25;
    private static final int DEFAULT_MIN_NUM = 2;
    private int minNum = 2;

    /**
     * Creates new form DTClassifierPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public DTClassifierPanel() {
        super("Parameter Settings Panel",
                "Decision tree settings:",
                "The C4.5 decision tree with the post-pruning algorithm in the pruning phase.",
                "Option\n\n"
                + "Confidence factor -> the confidence factor used for pruning (smaller values incur more pruning).\n\n"
                + "MinNumSample -> the minimum number of samples per leaf.\n\n",
                new Rectangle(10, 10, 140, 20),
                new Rectangle(10, 35, 400, 60),
                new Rectangle(120, 220, 75, 23),
                new Rectangle(240, 220, 75, 23),
                new Dimension(440, 300));

        Container contentPane = getContentPane();

        lbl_confidence = new JLabel("Confidence factor:");
        lbl_confidence.setBounds(30, 135, 120, 22);
        txt_confidence = new JTextField(String.valueOf(DEFAULT_CONFIDENCE));
        txt_confidence.setBounds(130, 135, 120, 21);
        txt_confidence.addKeyListener(this);
        lbl_confidenceError = new JLabel("");
        lbl_confidenceError.setBounds(260, 135, 50, 22);
        lbl_confidenceError.setForeground(Color.red);

        lbl_minNum = new JLabel("MinNumSample:");
        lbl_minNum.setBounds(30, 170, 120, 22);
        txt_minNum = new JTextField(Integer.toString(DEFAULT_MIN_NUM));
        txt_minNum.setBounds(130, 170, 120, 21);
        txt_minNum.addKeyListener(this);
        lbl_minNumError = new JLabel("");
        lbl_minNumError.setBounds(260, 170, 50, 22);
        lbl_minNumError.setForeground(Color.red);

        contentPane.add(lbl_confidence);
        contentPane.add(txt_confidence);
        contentPane.add(lbl_minNum);
        contentPane.add(txt_minNum);
        contentPane.add(lbl_confidenceError);
        contentPane.add(lbl_minNumError);

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

        tempStr = txt_confidence.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) < 0) {
            lbl_confidenceError.setText("*");
            enableOkButton = false;
        } else {
            lbl_confidenceError.setText("");
        }

        tempStr = txt_minNum.getText();
        if (!MathFunc.isInteger(tempStr) || Integer.parseInt(tempStr) < 0) {
            lbl_minNumError.setText("*");
            enableOkButton = false;
        } else {
            lbl_minNumError.setText("");
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
        setConfidence(Double.parseDouble(txt_confidence.getText()));
        setMinNum(Integer.parseInt(txt_minNum.getText()));
        super.btn_okActionPerformed(e);
    }

    /**
     * This method returns the confidence factor value.
     *
     * @return the <code>Confidence factor</code> parameter
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * This method sets the confidence factor value.
     *
     * @param confidence the confidence factor value
     */
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    /**
     * This method returns the minimum number of samples per leaf.
     *
     * @return the <code>MinNumSample</code> parameter
     */
    public int getMinNum() {
        return minNum;
    }

    /**
     * This method sets the minimum number of samples per leaf value.
     *
     * @param minNum the minimum number of samples per leaf value.
     */
    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    /**
     * Sets the default values of the decision tree parameters
     */
    public void setDefaultValue() {
        txt_confidence.setText(String.valueOf(DEFAULT_CONFIDENCE));
        txt_minNum.setText(String.valueOf(DEFAULT_MIN_NUM));
        confidence = DEFAULT_CONFIDENCE;
        minNum = DEFAULT_MIN_NUM;
    }

    /**
     * Sets the last values of the decision tree parameters entered by user
     *
     * @param conf the confidence factor
     * @param minSample the minimum number of samples per leaf
     */
    public void setUserValue(double conf, int minSample) {
        confidence = conf;
        minNum = minSample;
        txt_confidence.setText(String.valueOf(confidence));
        txt_minNum.setText(String.valueOf(minNum));
    }

//    public static void main(String[] arg) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            System.out.println("Error setting native LAF: " + e);
//        }
//
//        SwingUtilities.invokeLater(() -> {
//            DTClassifierPanel dtpanel = new DTClassifierPanel();
//            Dialog dlg = new Dialog(dtpanel);
//            dtpanel.setVisible(true);
//            System.out.println("confidence = " + dtpanel.getConfidence());
//            System.out.println("minNum = " + dtpanel.getMinNum());
//        });
//    }
}
