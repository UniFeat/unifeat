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
 * of the relevance-redundancy feature selection(RRFS) method.
 *
 * @author Shahin Salavati
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.filter.unsupervised.RRFS
 * @see unifeat.featureSelection.filter.supervised.RRFS
 */
public class RRFSPanel extends ParameterPanel {

    JLabel lbl_similarity, lbl_similarityError;
    JTextField txt_similarity;
    private double similarity = 0.4;
    private static final double DEFAULT_SIMILARITY = 0.4;

    /**
     * Creates new form RRFSPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public RRFSPanel() {
        super("Parameter Settings Panel",
                "Relevance-redundancy feature selection (RRFS) settings:",
                "<html>Relevance-redundancy feature selection (RRFS) is an efficient feature selection method based on relevance and redundancy analyses, which can work in both supervised and unsupervised modes.</html>",
                "Option\n\n" + 
                        "Similarity threshold -> maximum allowed similarity between two features (a real number in the range of [0, 1]).\n\n",
                                new Rectangle(10, 10, 440, 20),
                new Rectangle(10, 35, 450, 80),
                new Rectangle(140, 185, 75, 23),
                new Rectangle(260, 185, 75, 23),
                new Dimension(480, 280));
        
        Container contentPane = getContentPane();

        lbl_similarity = new JLabel("Similarity threshold:");
        lbl_similarity.setBounds(50, 135, 170, 22);
        txt_similarity = new JTextField(String.valueOf(DEFAULT_SIMILARITY));
        txt_similarity.setBounds(155, 135, 120, 21);
        txt_similarity.addKeyListener(this);
        lbl_similarityError = new JLabel("");
        lbl_similarityError.setBounds(285, 135, 50, 22);
        lbl_similarityError.setForeground(Color.red);

        contentPane.add(lbl_similarity);
        contentPane.add(txt_similarity);
        contentPane.add(lbl_similarityError);

        contentPane.validate();
//        contentPane.revalidate();
        contentPane.repaint();
//        this.pack();
    }

    /**
     * The listener method for receiving keyboard events (keystrokes).
     * Invoked when a key has been released.
     *
     * @param e an action event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        boolean enableOkButton = true;
        String tempStr;

        tempStr = txt_similarity.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) < 0 || Double.parseDouble(tempStr) > 1) {
            lbl_similarityError.setText("*");
            enableOkButton = false;
        } else {
            lbl_similarityError.setText("");
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
        setSimilarity(Double.parseDouble(txt_similarity.getText()));
        super.btn_okActionPerformed(e);
    }

    /**
     * This method returns the similarity threshold value.
     *
     * @return the <code>similarity threshold</code> parameter
     */
    public double getSimilarity() {
        return similarity;
    }

    /**
     * This method sets the similarity threshold value.
     *
     * @param similarity the similarity threshold value
     */
    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    /**
     * Sets the default values of the RRFS parameters
     */
    public void setDefaultValue() {
        txt_similarity.setText(String.valueOf(DEFAULT_SIMILARITY));
        similarity = DEFAULT_SIMILARITY;
    }

    /**
     * Sets the last values of the RRFS parameters entered by user
     *
     * @param simValue the value of the similarity
     */
    public void setUserValue(double simValue) {
        similarity = simValue;
        txt_similarity.setText(String.valueOf(similarity));
    }

//    public static void main(String[] arg) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            System.out.println("Error setting native LAF: " + e);
//        }
//
//        RRFSPanel dtpanel = new RRFSPanel();
//        Dialog dlg = new Dialog(dtpanel);
//        dtpanel.setVisible(true);
//        System.out.println("similarity value = " + dtpanel.getSimilarity());
//    }
}
