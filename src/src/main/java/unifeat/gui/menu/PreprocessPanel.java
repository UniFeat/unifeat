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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/**
 * This java class is used to create and show a panel for preprocessing of the
 * datasets.
 *
 * @author Shahin Salavati
 * @author Sina Tabakhi
 */
public class PreprocessPanel extends JFrame
        implements ActionListener {

    JLabel lbl_about, lbl_inputFile;
    JTextField txt_inputFile;
    JButton btn_selectFile, btn_save, btn_close;
    JPanel panel_about, panel_delimiter;
    JRadioButton rd_tab, rd_semicolon, rd_space, rd_comma;
    ButtonGroup btnGroup;
    JCheckBox ch_convert, ch_transpose;

    /**
     * Creates new form PreprocessPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public PreprocessPanel() {

        panel_about = new JPanel();
        panel_about.setBounds(10, 30, 480, 60);
        panel_about.setLayout(null);
        panel_about.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED),
            "About ", TitledBorder.LEFT, TitledBorder.TOP));
        lbl_about = new JLabel("This screen lets you prepare the dataset in the UniFeat format.");
        lbl_about.setBounds(15, 2, 455, 60);

        panel_about.add(lbl_about);

        /////////////////////// input file section ////////////////////////////
        lbl_inputFile = new JLabel("Select input file:");
        lbl_inputFile.setBounds(30, 110, 110, 22);
        txt_inputFile = new JTextField();
        txt_inputFile.setBounds(120, 110, 200, 24);
        txt_inputFile.setEditable(false);
        txt_inputFile.setBackground(Color.WHITE);
        btn_selectFile = new JButton("Open file...");
        btn_selectFile.setBounds(340, 108, 100, 25);
        btn_selectFile.addActionListener(this);

        /////////////////////// Delimiter panel ///////////////////////////////
        panel_delimiter = new JPanel();
        panel_delimiter.setBounds(30, 160, 105, 130);
        panel_delimiter.setLayout(null);
        panel_delimiter.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED),
            "Delimiter ", TitledBorder.LEFT, TitledBorder.TOP));
        
        rd_tab = new JRadioButton("Tab");
        rd_tab.setBounds(10, 25, 85, 22);
        rd_semicolon = new JRadioButton("Semicolon");
        rd_semicolon.setBounds(10, 50, 85, 22);
        rd_space = new JRadioButton("Space");
        rd_space.setBounds(10, 75, 85, 22);
        rd_comma = new JRadioButton("Comma");
        rd_comma.setBounds(10, 100, 85, 22);
        rd_tab.setSelected(true);
        btnGroup = new ButtonGroup();
        btnGroup.add(rd_tab);
        btnGroup.add(rd_semicolon);
        btnGroup.add(rd_space);
        btnGroup.add(rd_comma);

        panel_delimiter.add(rd_tab);
        panel_delimiter.add(rd_semicolon);
        panel_delimiter.add(rd_space);
        panel_delimiter.add(rd_comma);

        /////////////////////// check box section /////////////////////////////
        ch_convert = new JCheckBox("Convert to Comma delimited");
        ch_convert.setBounds(150, 200, 230, 22);

        ch_transpose = new JCheckBox("Transpose (rotate) dataset from rows to columns or vice versa");
        ch_transpose.setBounds(150, 230, 380, 22);

        btn_save = new JButton("Save file...");
        btn_save.setBounds(140, 310, 90, 25);
        btn_save.setEnabled(false);
        btn_save.addActionListener(this);

        btn_close = new JButton("Close");
        btn_close.setBounds(250, 310, 90, 25);
        btn_close.addActionListener(this);

        add(panel_about);

        add(lbl_inputFile);
        add(txt_inputFile);
        add(btn_selectFile);

        add(panel_delimiter);

        add(ch_convert);
        add(ch_transpose);
        add(btn_save);
        add(btn_close);

        setLayout(null);
        setSize(520, 385);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/unifeat/gui/icons/small_logo.png")).getImage());
        setTitle("Preprocessing Panel");
        setResizable(false);
        setVisible(true);
    }

    /**
     * The listener method for receiving action events. Invoked when an action
     * occurs.
     *
     * @param e an action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_selectFile)) {
            btn_selectFileActionPerformed(e);
        } else if (e.getSource().equals(btn_save)) {
            btn_saveActionPerformed(e);
        } else if (e.getSource().equals(btn_close)) {
            btn_closeActionPerformed(e);
        }
    }

    /**
     * This method sets an action for the btn_selectFile button.
     *
     * @param e an action event
     */
    private void btn_selectFileActionPerformed(ActionEvent e) {
        JFileChooser jfch = new JFileChooser();
        jfch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (jfch.showOpenDialog(btn_selectFile) == JFileChooser.APPROVE_OPTION) {
            txt_inputFile.setText(jfch.getSelectedFile().getPath());
            btn_save.setEnabled(true);
        }
    }

    /**
     * This method sets an action for the btn_save button.
     *
     * @param e an action event
     */
    private void btn_saveActionPerformed(ActionEvent e) {
        char delimiter;
        String oldPath = txt_inputFile.getText();
        String newPath = "";

        JFileChooser jfch = new JFileChooser();
        jfch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (jfch.showSaveDialog(btn_save) == JFileChooser.APPROVE_OPTION) {
            newPath = Paths.get(jfch.getSelectedFile().getAbsolutePath(), "NewData.data").toString();
        }

        if (rd_tab.isSelected()) {
            delimiter = '	';
        } else if (rd_semicolon.isSelected()) {
            delimiter = ';';
        } else if (rd_space.isSelected()) {
            delimiter = ' ';
        } else {
            delimiter = ',';
        }

        if (ch_convert.isSelected() && !ch_transpose.isSelected()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(oldPath));
                PrintWriter pw = new PrintWriter(new FileWriter(newPath, false));
                while (br.ready()) {
                    pw.println(br.readLine().replace(delimiter, ','));
                }
                br.close();
                pw.close();
            } catch (IOException ex) {
                Logger.getLogger(PreprocessPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (ch_transpose.isSelected()) {
            int numRow = 1;
            int numCol = 0;
            //counts the number of lines in the input file
            try {
                BufferedReader br = new BufferedReader(new FileReader(oldPath));
                numCol = br.readLine().split(Character.toString(delimiter)).length;
                while (br.ready()) {
                    br.readLine();
                    numRow++;
                }
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(PreprocessPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            //transpose (rotate) dataset from rows to columns or vice versa
            try {
                BufferedReader br = new BufferedReader(new FileReader(oldPath));
                PrintWriter pw = new PrintWriter(new FileWriter(newPath, false));
                String[][] tempArray = new String[numRow][numCol];
                int counter = 0;

                while (br.ready()) {
                    tempArray[counter++] = br.readLine().split(Character.toString(delimiter));
                }
                br.close();

                //if the original data are separated by comma delimiter
                if (ch_convert.isSelected()) {
                    delimiter = ',';
                }
                for (int i = 0; i < numCol; i++) {
                    for (int j = 0; j < numRow - 1; j++) {
                        pw.print(tempArray[j][i] + delimiter);
                    }
                    pw.println(tempArray[numRow - 1][i]);
                }
                pw.close();
            } catch (IOException ex) {
                Logger.getLogger(PreprocessPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method sets an action for the btn_close button.
     *
     * @param e an action event
     */
    private void btn_closeActionPerformed(ActionEvent e) {
        dispose();
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
//        PreprocessPanel processPanel = new PreprocessPanel();
//    }
}
