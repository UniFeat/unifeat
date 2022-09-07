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
package unifeat.gui.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import unifeat.util.ArraysFunc;
import unifeat.util.CriticalValue;
import unifeat.util.MathFunc;
import javax.swing.ImageIcon;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/**
 * This java class is used to create and show a panel for analyzing the results
 * based on the Friedman test.
 *
 * @author Shahin Salavati
 * @author Sina Tabakhi
 */
public class FriedmanPanel extends JFrame implements ActionListener {

    JLabel lbl_insertFile, lbl_worthOfVal;
    JTextField txt_filePath;
    JButton btn_open, btn_performTest;
    JComboBox cb_worthOfVal;
    //-----------------------------------------------
    JPanel panel_friedman;
    JLabel lbl_numDataset, lbl_numDatasetVal, lbl_numMethod, lbl_numMethodVal, lbl_chi_square, lbl_chi_squareVal,
            lbl_fdistribution, lbl_fdistributionVal;
    JPanel panel_criticalValTbl;
    JLabel lbl_criticalVal1, lbl_criticalVal2, lbl_criticalVal3, lbl_criticalVal4, lbl_criticalVal5, lbl_criticalVal6;
    //---------------------------------------------------
    JTable tbl_values;
    TableColumnModel columnModel;
    DefaultTableModel model;
    JScrollPane scroll_tbl;

    /**
     * Creates new form FriedmanPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public FriedmanPanel() {

        lbl_insertFile = new JLabel("Insert file:");
        lbl_insertFile.setBounds(20, 35, 90, 22);
        txt_filePath = new JTextField();
        txt_filePath.setBounds(110, 35, 210, 24);
        txt_filePath.setEditable(false);
        txt_filePath.setBackground(Color.WHITE);
        btn_open = new JButton("Open file...");
        btn_open.setBounds(340, 35, 95, 25);
        btn_open.addActionListener(this);

        lbl_worthOfVal = new JLabel("Worth of values:");
        lbl_worthOfVal.setBounds(20, 75, 90, 22);
        cb_worthOfVal = new JComboBox(new String[]{"descending order",
                    "ascending order"});
        cb_worthOfVal.setBounds(110, 75, 210, 25);
        btn_performTest = new JButton("Perform test");
        btn_performTest.setEnabled(false);
        btn_performTest.addActionListener(this);
        btn_performTest.setBounds(340, 75, 95, 25);

        /////////////////////// Friedman test panel ///////////////////////////
        panel_friedman = new JPanel();
        panel_friedman.setBounds(15, 120, 415, 170);
        panel_friedman.setLayout(null);
        panel_friedman.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED),
            "Friedman test ", TitledBorder.LEFT, TitledBorder.TOP));

        lbl_numDataset = new JLabel("Number of datasets:");
        lbl_numDataset.setBounds(15, 25, 130, 22);
        lbl_numDatasetVal = new JLabel("0");
        lbl_numDatasetVal.setBounds(130, 25, 30, 22);

        lbl_numMethod = new JLabel("Number of methods:");
        lbl_numMethod.setBounds(15, 60, 130, 22);
        lbl_numMethodVal = new JLabel("0");
        lbl_numMethodVal.setBounds(130, 60, 30, 22);

        lbl_chi_square = new JLabel("Chi-square:");
        lbl_chi_square.setBounds(15, 95, 130, 22);
        lbl_chi_squareVal = new JLabel("0");
        lbl_chi_squareVal.setBounds(130, 95, 60, 22);

        lbl_fdistribution = new JLabel("F-distribution:");
        lbl_fdistribution.setBounds(15, 130, 130, 22);
        lbl_fdistributionVal = new JLabel("0");
        lbl_fdistributionVal.setBounds(130, 130, 60, 22);

        panel_friedman.add(lbl_numDataset);
        panel_friedman.add(lbl_numDatasetVal);

        panel_friedman.add(lbl_numMethod);
        panel_friedman.add(lbl_numMethodVal);
        panel_friedman.add(lbl_chi_square);
        panel_friedman.add(lbl_chi_squareVal);
        panel_friedman.add(lbl_fdistribution);
        panel_friedman.add(lbl_fdistributionVal);

        /////////////////////// Critical value panel //////////////////////////
        panel_criticalValTbl = new JPanel();
        panel_criticalValTbl.setBounds(200, 20, 205, 140);
        panel_criticalValTbl.setLayout(null);
        panel_criticalValTbl.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED),
            "Critical values of the tables ", TitledBorder.LEFT, TitledBorder.TOP));
        
        lbl_criticalVal1 = new JLabel("<html>&alpha: 0.01</html");
        lbl_criticalVal1.setBounds(20, 25, 60, 22);

        lbl_criticalVal2 = new JLabel("F(,)");
        lbl_criticalVal2.setBounds(80, 25, 120, 22);

        lbl_criticalVal3 = new JLabel("<html>&alpha: 0.05</html");
        lbl_criticalVal3.setBounds(20, 65, 60, 22);

        lbl_criticalVal4 = new JLabel("F(,)");
        lbl_criticalVal4.setBounds(80, 65, 120, 22);

        lbl_criticalVal5 = new JLabel("<html>&alpha: 0.10</html");
        lbl_criticalVal5.setBounds(20, 105, 80, 22);

        lbl_criticalVal6 = new JLabel("F(,)");
        lbl_criticalVal6.setBounds(80, 105, 120, 22);

        panel_criticalValTbl.add(lbl_criticalVal1);
        panel_criticalValTbl.add(lbl_criticalVal2);
        panel_criticalValTbl.add(lbl_criticalVal3);
        panel_criticalValTbl.add(lbl_criticalVal4);
        panel_criticalValTbl.add(lbl_criticalVal5);
        panel_criticalValTbl.add(lbl_criticalVal6);
        panel_friedman.add(panel_criticalValTbl);

        //---------------------------------------------------------------------
        add(lbl_insertFile);
        add(txt_filePath);
        add(btn_open);

        add(lbl_worthOfVal);
        add(cb_worthOfVal);
        add(btn_performTest);

        add(panel_friedman);
        scroll_tbl = new JScrollPane();
        add(scroll_tbl);


        setLayout(null);
        //setSize(460, 460);
        setSize(460, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Friedman Test Panel");
        setIconImage(new ImageIcon(getClass().getResource("/unifeat/gui/icons/small_logo.png")).getImage());
        setVisible(true);
    }

    /**
     * The listener method for receiving action events.
     * Invoked when an action occurs.
     *
     * @param e an action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_open)) {
            btn_openActionPerformed(e);
        } else if (e.getSource().equals(btn_performTest)) {
            btn_performTestActionPerformed(e);
        }
    }

    /**
     * This method sets an action for the btn_open button.
     *
     * @param e an action event
     */
    private void btn_openActionPerformed(ActionEvent e) {
        JFileChooser jfch = new JFileChooser();
        jfch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (jfch.showOpenDialog(btn_open) == JFileChooser.APPROVE_OPTION) {
            txt_filePath.setText(jfch.getSelectedFile().getPath());
            btn_performTest.setEnabled(true);
        }
    }

    /**
     * This method sets an action for the btn_performTest button.
     *
     * @param e an action event
     */
    private void btn_performTestActionPerformed(ActionEvent e) {
        boolean typeSort;
        String[][] inputStr = new String[1][1];
        String[] nameMethod = new String[1];
        int numRow = 0;
        int numCol = 0;

        //computes the number of rows and the number of columns
        try {
            BufferedReader br = new BufferedReader(new FileReader(txt_filePath.getText()));
            numCol = br.readLine().split(",").length;
            while (br.ready()) {
                br.readLine();
                numRow++;
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(FriedmanPanel.class.getName()).log(Level.SEVERE, null, ex);
        }


        //reads the data file information(gets values)
        try {
            inputStr = new String[numRow][numCol];
            nameMethod = new String[numCol];
            int counter = 0;

            BufferedReader br = new BufferedReader(new FileReader(txt_filePath.getText()));
            nameMethod = br.readLine().split(",");
            while (br.ready()) {
                inputStr[counter++] = br.readLine().split(",");
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(FriedmanPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //sets the values of the table
        lbl_numDatasetVal.setText(String.valueOf(numRow));
        lbl_numMethodVal.setText(String.valueOf(numCol - 1));
        model = new DefaultTableModel(nameMethod, numRow + 2);
        tbl_values = new JTable();
        tbl_values.setModel(model);
        tbl_values.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbl_values.setEnabled(false);

        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                tbl_values.setValueAt(inputStr[i][j], i, j);
            }
        }

        //selects the type of sort
        typeSort = cb_worthOfVal.getSelectedItem().equals("descending order");

        //computes average ranks
        tbl_values.setValueAt("Average rank", numRow + 1, 0);
        double[] aveRank = new double[numCol - 1];
        double[][] values = new double[numRow][numCol - 1];
        double[][] methodRanks = new double[numRow][numCol - 1];
        for (int i = 0; i < numRow; i++) {
            for (int j = 1; j < numCol; j++) {
                values[i][j - 1] = Double.parseDouble(inputStr[i][j]);
            }
        }

        for (int i = 0; i < numRow; i++) {
            int[] indeces = ArraysFunc.sortWithIndex(values[i], typeSort);
            double rankCounter = 1;
            int indexStart = 0;
            double startValue = values[i][indexStart];

            for (int j = 1; j < numCol - 1; j++) {
                if (startValue == values[i][j]) {
                    rankCounter += (j + 1);
                } else {
                    int sameValue = j - indexStart;
                    rankCounter /= sameValue;
                    for (int k = indexStart; k < j; k++) {
                        methodRanks[i][indeces[k]] = rankCounter;
                    }
                    indexStart = j;
                    startValue = values[i][indexStart];
                    rankCounter = j + 1;
                }
            }
            int sameValue = numCol - 1 - indexStart;
            rankCounter /= sameValue;
            for (int k = indexStart; k < numCol - 1; k++) {
                methodRanks[i][indeces[k]] = rankCounter;
            }
        }

        for (int j = 0; j < numCol - 1; j++) {
            for (int i = 0; i < numRow; i++) {
                aveRank[j] += methodRanks[i][j];
            }
            aveRank[j] /= numRow;
            aveRank[j] = Double.parseDouble(MathFunc.roundDouble(aveRank[j], 2));
            tbl_values.setValueAt(MathFunc.roundDouble(aveRank[j], 2), numRow + 1, j + 1);
        }

        //creates average ranks in the table
        remove(scroll_tbl);
        scroll_tbl = new JScrollPane(tbl_values);
        int sizeCol = numCol * 81;
        int sizeRow = numRow * 31;
        if (sizeRow > 300) {
            sizeRow = 300;
        }
        if (sizeCol > 900) {
            sizeCol = 900;
        }

        scroll_tbl.setBounds(20, 310, sizeCol, sizeRow);
        scroll_tbl.setBorder(BorderFactory.createEmptyBorder());
        add(scroll_tbl);
        //default size (460, 350)
        if (sizeCol > 420) {
            setSize(sizeCol + 40, 350 + sizeRow);
        } else {
            setSize(460 + 40, 350 + sizeRow);
        }
        setLocationRelativeTo(null);


        //computes chi-square and Fisher distribution(FF)
        double sumRank = 0;
        int N = numRow; //number of datasets
        int K = numCol - 1; //number of methods
        for (int i = 0; i < aveRank.length; i++) {
            sumRank += (aveRank[i] * aveRank[i]);
        }
        double firstPart = (12.0 * N) / (K * (K + 1));
        double thirdPart = (K * Math.pow(K + 1, 2)) / 4.0;
        double chi = firstPart * (sumRank - thirdPart);
        double FF = ((N - 1) * chi) / ((N * (K - 1)) - chi);
        lbl_chi_squareVal.setText(MathFunc.roundDouble(chi, 3));
        lbl_fdistributionVal.setText(MathFunc.roundDouble(FF, 3));

        //computes critical values of the table
        int df1 = K - 1;
        int df2 = (K - 1) * (N - 1);
        if (df1 > 20 || df2 > 100) {
            lbl_criticalVal2.setText("F(" + String.valueOf(df1) + "," + String.valueOf(df2) + ")=not available");
            lbl_criticalVal4.setText("F(" + String.valueOf(df1) + "," + String.valueOf(df2) + ")=not available");
            lbl_criticalVal6.setText("F(" + String.valueOf(df1) + "," + String.valueOf(df2) + ")=not available");
        } else {
            lbl_criticalVal2.setText("F(" + String.valueOf(df1) + "," + String.valueOf(df2) + ")=" + CriticalValue.FDistributionValue(df1, df2, 0.01));
            lbl_criticalVal4.setText("F(" + String.valueOf(df1) + "," + String.valueOf(df2) + ")=" + CriticalValue.FDistributionValue(df1, df2, 0.05));
            lbl_criticalVal6.setText("F(" + String.valueOf(df1) + "," + String.valueOf(df2) + ")=" + CriticalValue.FDistributionValue(df1, df2, 0.10));
        }
    }

//    public static void main(String[] args) {
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
//        FriedmanPanel app = new FriedmanPanel();
//    }
}
