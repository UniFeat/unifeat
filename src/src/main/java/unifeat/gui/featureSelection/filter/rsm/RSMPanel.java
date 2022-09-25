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
package unifeat.gui.featureSelection.filter.rsm;

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
 * of the random subspace method (RSM) method.
 *
 * @author Shahin Salavati
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.filter.unsupervised.RSM
 */
public class RSMPanel extends ParameterPanel {

    JLabel lbl_numSelection, lbl_numSelectionError,
            lbl_sizeSubspace, lbl_sizeSubspaceError,
            lbl_elimination, lbl_eliminationError,
            lbl_multivalMethod;
    JTextField txt_numSelection,
            txt_sizeSubspace,
            txt_elimination;
    JComboBox cb_multivalMethod;
    private int numSelection = 50, sizeSubspace = 0, elimination = 0;
    private static final int DEFAULT_NUM_SELECTION = 50, DEFAULT_SIZE_SUBSPACE = 0, DEFAULT_ELIMINATION = 0;
    private MultivariateMethodType multMethodName = MultivariateMethodType.MUTUAL_CORRELATION;
    private static final MultivariateMethodType DEFAULT_MULT_METHOD_NAME = MultivariateMethodType.MUTUAL_CORRELATION;

    /**
     * Creates new form RSMPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public RSMPanel() {
        super("Parameter Settings Panel",
                "Random subspace method (RSM) settings:",
                "<html> Random subspace method (RSM) has been proposed to reduce the computational complexity in determining the relevance features for multivariate methods. This method applies mutual correlation as a multivariate methods in the search strategy. </html>",
                "Option\n\n"
                + "Number of selections -> determines the number of selections in the algorithm.\n\n"
                + "Size of subspace -> the size of subspace which is selected from the original features (0 means the size of subspace is set to half of the number of original features).\n\n"
                + "Elimination threshold -> determines the number of relevant features in the selected subspace (0 means the elimination threshold is set to half of the size of subspace).\n\n"
                + "Multivariate method -> the name of multivariate feature selection method applied in the algorithm.\n\n",
                new Rectangle(10, 10, 450, 20),
                new Rectangle(10, 35, 510, 80),
                new Rectangle(170, 290, 75, 25),
                new Rectangle(290, 290, 75, 25),
                new Dimension(540, 380));

        Container contentPane = getContentPane();

        lbl_numSelection = new JLabel("Number of selections:");
        lbl_numSelection.setBounds(50, 135, 170, 22);
        txt_numSelection = new JTextField(String.valueOf(DEFAULT_NUM_SELECTION));
        txt_numSelection.setBounds(190, 135, 120, 24);
        txt_numSelection.addKeyListener(this);
        lbl_numSelectionError = new JLabel("");
        lbl_numSelectionError.setBounds(320, 135, 50, 22);
        lbl_numSelectionError.setForeground(Color.red);

        lbl_sizeSubspace = new JLabel("Size of subspace:");
        lbl_sizeSubspace.setBounds(50, 170, 170, 22);
        txt_sizeSubspace = new JTextField(Integer.toString(DEFAULT_SIZE_SUBSPACE));
        txt_sizeSubspace.setBounds(190, 170, 120, 24);
        txt_sizeSubspace.addKeyListener(this);
        lbl_sizeSubspaceError = new JLabel("");
        lbl_sizeSubspaceError.setBounds(320, 170, 50, 22);
        lbl_sizeSubspaceError.setForeground(Color.red);

        lbl_elimination = new JLabel("Elimination threshold:");
        lbl_elimination.setBounds(50, 205, 170, 22);
        txt_elimination = new JTextField(Integer.toString(DEFAULT_ELIMINATION));
        txt_elimination.setBounds(190, 205, 120, 24);
        txt_elimination.addKeyListener(this);
        lbl_eliminationError = new JLabel("");
        lbl_eliminationError.setBounds(320, 205, 50, 22);
        lbl_eliminationError.setForeground(Color.red);

        lbl_multivalMethod = new JLabel("Multivariate method:");
        lbl_multivalMethod.setBounds(50, 240, 170, 22);
        cb_multivalMethod = new JComboBox(MultivariateMethodType.asList());
        cb_multivalMethod.setBounds(190, 240, 150, 25);

        contentPane.add(lbl_numSelection);
        contentPane.add(txt_numSelection);
        contentPane.add(lbl_numSelectionError);

        contentPane.add(lbl_sizeSubspace);
        contentPane.add(txt_sizeSubspace);
        contentPane.add(lbl_sizeSubspaceError);

        contentPane.add(lbl_elimination);
        contentPane.add(txt_elimination);
        contentPane.add(lbl_eliminationError);

        contentPane.add(lbl_multivalMethod);
        contentPane.add(cb_multivalMethod);

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

        tempStr = txt_numSelection.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 1)) {
            lbl_numSelectionError.setText("*");
            enableOkButton = false;
        } else {
            lbl_numSelectionError.setText("");
        }

        tempStr = txt_sizeSubspace.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 0)) {
            lbl_sizeSubspaceError.setText("*");
            enableOkButton = false;
        } else {
            lbl_sizeSubspaceError.setText("");
        }

        tempStr = txt_elimination.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 0)) {
            lbl_eliminationError.setText("*");
            enableOkButton = false;
        } else {
            lbl_eliminationError.setText("");
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
        setNumSelection(Integer.parseInt(txt_numSelection.getText()));
        setSizeSubspace(Integer.parseInt(txt_sizeSubspace.getText()));
        setElimination(Integer.parseInt(txt_elimination.getText()));
        setMultMethodName(MultivariateMethodType.parse(cb_multivalMethod.getSelectedItem().toString()));
        super.btn_okActionPerformed(e);
    }

    /**
     * This method returns the number of selections value.
     *
     * @return the <code>numSelection</code> parameter
     */
    public int getNumSelection() {
        return numSelection;
    }

    /**
     * This method sets the number of selections value.
     *
     * @param numSelection the number of selections value
     */
    public void setNumSelection(int numSelection) {
        this.numSelection = numSelection;
    }

    /**
     * This method returns the size of subspace value.
     *
     * @return the <code>sizeSubspace</code> parameter
     */
    public int getSizeSubspace() {
        return sizeSubspace;
    }

    /**
     * This method sets the size of subspace value.
     *
     * @param sizeSubspace the size of subspace value
     */
    public void setSizeSubspace(int sizeSubspace) {
        this.sizeSubspace = sizeSubspace;
    }

    /**
     * This method returns the elimination threshold value.
     *
     * @return the <code>elimination</code> parameter
     */
    public int getElimination() {
        return elimination;
    }

    /**
     * This method sets the elimination threshold value.
     *
     * @param elimination the elimination threshold value
     */
    public void setElimination(int elimination) {
        this.elimination = elimination;
    }

    /**
     * This method returns the name of multivariate method. The current values
     * are:<br>
     * - "Mutual correlation": the mutual correlation method.
     *
     * @return the <code>multMethodName</code>
     *
     * @see unifeat.featureSelection.filter.unsupervised.MutualCorrelation
     */
    public MultivariateMethodType getMultMethodName() {
        return multMethodName;
    }

    /**
     * This method sets the name of multivariate method. The current values
     * are:<br>
     * - "Mutual correlation": the mutual correlation method.
     *
     * @param multMethodName the name of multivariate method
     *
     * @see unifeat.featureSelection.filter.unsupervised.MutualCorrelation
     */
    public void setMultMethodName(MultivariateMethodType multMethodName) {
        this.multMethodName = multMethodName;
    }

    /**
     * Sets the default values of the RSM parameters
     */
    public void setDefaultValue() {
        txt_numSelection.setText(String.valueOf(DEFAULT_NUM_SELECTION));
        txt_sizeSubspace.setText(String.valueOf(DEFAULT_SIZE_SUBSPACE));
        txt_elimination.setText(String.valueOf(DEFAULT_ELIMINATION));
        cb_multivalMethod.setSelectedItem(DEFAULT_MULT_METHOD_NAME.toString());

        numSelection = DEFAULT_NUM_SELECTION;
        sizeSubspace = DEFAULT_SIZE_SUBSPACE;
        elimination = DEFAULT_ELIMINATION;
        multMethodName = DEFAULT_MULT_METHOD_NAME;
    }

    /**
     * Sets the last values of the RSM parameters entered by user
     *
     * @param numSelect the number of iteration in the RSM method
     * @param sizeSpace the size of the subspace
     * @param eliminValue the number of selected features in each subspace
     * @param nameMultMethod the name of the multivariate approach used in the
     * RSM
     *
     * @see unifeat.featureSelection.filter.unsupervised.MutualCorrelation
     */
    public void setUserValue(int numSelect, int sizeSpace, int eliminValue, MultivariateMethodType nameMultMethod) {
        numSelection = numSelect;
        sizeSubspace = sizeSpace;
        elimination = eliminValue;
        multMethodName = nameMultMethod;

        txt_numSelection.setText(String.valueOf(numSelection));
        txt_sizeSubspace.setText(String.valueOf(sizeSubspace));
        txt_elimination.setText(String.valueOf(elimination));
        cb_multivalMethod.setSelectedItem(multMethodName.toString());
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
//        RSMPanel dtpanel = new RSMPanel();
//        Dialog dlg = new Dialog(dtpanel);
//        dtpanel.setVisible(true);
//        System.out.println("num selection = " + dtpanel.getNumSelection());
//        System.out.println("size subspace = " + dtpanel.getSizeSubspace());
//        System.out.println("elimination = " + dtpanel.getElimination());
//        System.out.println("multivariate method = " + dtpanel.getMultMethodName());
//
//        dtpanel.setUserValue(10, 20, 30, DEFAULT_MULT_METHOD_NAME);
//        System.out.println("num selection = " + dtpanel.getNumSelection());
//        System.out.println("size subspace = " + dtpanel.getSizeSubspace());
//        System.out.println("elimination = " + dtpanel.getElimination());
//        System.out.println("multivariate method = " + dtpanel.getMultMethodName());
//    }
}
