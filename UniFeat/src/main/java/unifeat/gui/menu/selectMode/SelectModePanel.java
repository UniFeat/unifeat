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
package unifeat.gui.menu.selectMode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show a panel for the selecting mode of
 * showing the results.
 * <p>
 * Two modes are available: results of each iteration or average results of all
 * iteration.
 * 
 * @author Sina Tabakhi
 */
public class SelectModePanel extends JDialog implements ActionListener {

    JLabel lbl_selectMode;
    JButton btn_ok;
    ButtonGroup bg_mode;
    JRadioButton rd_average, rd_total;
    SelectModeType nameMode;

    /**
     * Creates new form SelectModePanel. This method is called from within the
     * constructor to initialize the form.
     */
    public SelectModePanel() {

        setNameMode(SelectModeType.NONE);
        lbl_selectMode = new JLabel("Selects a mode:");
        lbl_selectMode.setBounds(20, 40, 90, 22);

        rd_average = new JRadioButton("Average of all iteration");
        rd_average.setBounds(110, 30, 170, 20);
        rd_average.setSelected(true);
        rd_total = new JRadioButton("Total results of each iteration");
        rd_total.setBounds(110, 60, 195, 20);

        bg_mode = new ButtonGroup();
        bg_mode.add(rd_average);
        bg_mode.add(rd_total);

        btn_ok = new JButton("Ok");
        btn_ok.setBounds(110, 110, 70, 23);
        btn_ok.addActionListener(this);


        setModalityType(ModalityType.APPLICATION_MODAL);
        setSize(310, 190);
        setLayout(null);

        add(rd_average);
        add(rd_total);
        add(lbl_selectMode);
        add(btn_ok);

        setIconImage(new ImageIcon(getClass().getResource("/unifeat/gui/icons/small_logo.png")).getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Selection Mode Panel");
    }

    /**
     * The listener method for receiving action events.
     * Invoked when an action occurs.
     *
     * @param e an action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_ok)) {
            btn_okActionPerformed(e);
        }
    }

    /**
     * This method sets an action for the btn_ok button.
     *
     * @param e an action event
     */
    private void btn_okActionPerformed(ActionEvent e) {
        if (rd_average.isSelected()) {
            setNameMode(SelectModeType.AVERAGE);
        } else if (rd_total.isSelected()) {
            setNameMode(SelectModeType.TOTAL);
        }
        dispose();
    }

    /**
     * This method returns the type of mode. Two values can be returned:<br>
     * - "Average": means that the average results of all iteration<br>
     * - "Total": means that the total results of each iteration
     * 
     * @return the <code>nameMode</code> parameter
     */
    public SelectModeType getNameMode() {
        return nameMode;
    }

    /**
     * This method sets the type of mode. Two types of modes are as follows:<br>
     * - "Average": shows the average results of all iteration<br>
     * - "Total": shows the total results of each iteration
     *
     * @param nameMode the name of mode
     */
    public void setNameMode(SelectModeType nameMode) {
        this.nameMode = nameMode;
    }

//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            System.out.println("Error setting native LAF: " + e);
//        }
//
//        SelectModePanel app = new SelectModePanel();
//        JDialog dlg = new JDialog(app);
//        app.setVisible(true);
//        System.out.println("Status = " + app.getNameMode());
//    }
}
