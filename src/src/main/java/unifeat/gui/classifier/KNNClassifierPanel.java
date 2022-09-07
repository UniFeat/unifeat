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
 * of the k-nearest neighbours classifier.
 *
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 */
public class KNNClassifierPanel extends ParameterPanel {

    JLabel lbl_kNNValue, lbl_kNNValueError;
    JTextField txt_kNNValue;
    private static final int DEFAULT_KNN_VALUE = 1;
    private int kNNValue = 1;

    /**
     * Creates new form KNNClassifierPanel. This method is called from within
     * the constructor to initialize the form.
     */
    public KNNClassifierPanel() {
        super("Parameter Settings Panel",
                "K-nearest neighbours settings:",
                "The K-nearest neighbours classifier.",
                "Option\n\n"
                + "KNN value -> the number of neighbours to use.\n\n",
                new Rectangle(10, 10, 200, 20),
                new Rectangle(10, 35, 400, 60),
                new Rectangle(120, 190, 75, 25),
                new Rectangle(240, 190, 75, 25),
                new Dimension(440, 270));

        Container contentPane = getContentPane();

        lbl_kNNValue = new JLabel("KNN value:");
        lbl_kNNValue.setBounds(30, 135, 120, 22);
        txt_kNNValue = new JTextField(String.valueOf(DEFAULT_KNN_VALUE));
        txt_kNNValue.setBounds(130, 135, 120, 24);
        txt_kNNValue.addKeyListener(this);
        lbl_kNNValueError = new JLabel("");
        lbl_kNNValueError.setBounds(260, 135, 50, 22);
        lbl_kNNValueError.setForeground(Color.red);

        contentPane.add(lbl_kNNValue);
        contentPane.add(txt_kNNValue);
        contentPane.add(lbl_kNNValueError);

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

        tempStr = txt_kNNValue.getText();
        if (!MathFunc.isInteger(tempStr) || Integer.parseInt(tempStr) <= 0) {
            lbl_kNNValueError.setText("*");
            enableOkButton = false;
        } else {
            lbl_kNNValueError.setText("");
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
        setKNNValue(Integer.parseInt(txt_kNNValue.getText()));
        super.btn_okActionPerformed(e);
    }

    /**
     * This method returns the number of neighbours to use.
     *
     * @return the <code>kNNValue</code> parameter
     */
    public int getKNNValue() {
        return kNNValue;
    }

    /**
     * This method sets the number of neighbours to use.
     *
     * @param kNNValue the number of neighbours to use
     */
    public void setKNNValue(int kNNValue) {
        this.kNNValue = kNNValue;
    }

    /**
     * Sets the default values of the k-nearest neighbours parameters
     */
    public void setDefaultValue() {
        txt_kNNValue.setText(String.valueOf(DEFAULT_KNN_VALUE));
        kNNValue = DEFAULT_KNN_VALUE;
    }

    /**
     * Sets the last values of the k-nearest neighbours parameters entered by
     * user
     *
     * @param kNNValue the number of neighbours to use
     */
    public void setUserValue(int kNNValue) {
        this.kNNValue = kNNValue;
        txt_kNNValue.setText(String.valueOf(this.kNNValue));
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
//        SwingUtilities.invokeLater(() -> {
//            KNNClassifierPanel dtpanel = new KNNClassifierPanel();
//            Dialog dlg = new Dialog(dtpanel);
//            dtpanel.setVisible(true);
//            System.out.println("kNNValue = " + dtpanel.getKNNValue());
//        });
//    }
}
