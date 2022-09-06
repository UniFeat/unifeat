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
package unifeat.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show a panel for showing the results.
 *
 * @author Shahin Salavati
 * @author Sina Tabakhi
 */
public class ResultPanel extends JPanel implements ActionListener {

    JButton btn_showFeat, btn_showDir, btn_saveRes, btn_exit;
    JTextArea txtArea_main;
    JScrollPane scroll;
    String pathProject;
    JFrame f;

    /**
     * Creates new form ResultPanel. This method is called from within the
     * constructor to initialize the form.
     *
     * @param path the path of the project
     */
    public ResultPanel(String path) {
        super();
        pathProject = path;

        txtArea_main = new JTextArea();
        txtArea_main.setLineWrap(true);
        txtArea_main.setEditable(false);

        scroll = new JScrollPane();
        scroll.setBounds(0, 0, 695, 400);
        scroll.setViewportView(txtArea_main);

        btn_showFeat = new JButton("View subsets");
        btn_showFeat.setBounds(70, 420, 130, 23);
        btn_showFeat.setEnabled(false);
        btn_showFeat.addActionListener(this);

        btn_showDir = new JButton("View train/test sets");
        btn_showDir.setBounds(210, 420, 130, 23);
        btn_showDir.setEnabled(false);
        btn_showDir.addActionListener(this);

        btn_saveRes = new JButton("Save results");
        btn_saveRes.setBounds(350, 420, 130, 23);
        btn_saveRes.setEnabled(false);
        btn_saveRes.addActionListener(this);

        btn_exit = new JButton("Close");
        btn_exit.setBounds(490, 420, 130, 23);
        btn_exit.setEnabled(false);
        btn_exit.addActionListener(this);

        setSize(700, 400);
        setLayout(null);
        add(scroll);
        add(btn_showFeat);
        add(btn_showDir);
        add(btn_saveRes);
        add(btn_exit);

        f = new JFrame("Results");
        f.add(this);
        f.setIconImage(new ImageIcon(getClass().getResource("/unifeat/gui/icons/small_logo.png")).getImage());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(700, 500);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
    }

    /**
     * The listener method for receiving action events. Invoked when an action
     * occurs.
     *
     * @param e an action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_showFeat)) {
            btn_showFeatActionPerformed(e);
        } else if (e.getSource().equals(btn_showDir)) {
            btn_showDirActionPerformed(e);
        } else if (e.getSource().equals(btn_saveRes)) {
            btn_saveResActionPerformed(e);
        } else if (e.getSource().equals(btn_exit)) {
            btn_exitActionPerformed(e);
        }
    }

    /**
     * This method sets an action for the btn_showFeat button.
     *
     * @param e an action event
     *
     * @see unifeat.gui.MainPanel
     */
    private void btn_showFeatActionPerformed(ActionEvent e) {
        try {
            Desktop.getDesktop().open(new File(pathProject + "FeatureSubsetsFile.txt"));
        } catch (IOException ex) {
            Logger.getLogger(ResultPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method sets an action for the btn_showDir button.
     *
     * @param e an action event
     */
    private void btn_showDirActionPerformed(ActionEvent e) {
        try {
            Desktop.getDesktop().open(new File(pathProject));
        } catch (IOException ex) {
            Logger.getLogger(ResultPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method sets an action for the btn_saveRes button.
     *
     * @param e an action event
     */
    private void btn_saveResActionPerformed(ActionEvent e) {
        JFileChooser jfch = new JFileChooser();
        jfch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (jfch.showSaveDialog(btn_saveRes) == JFileChooser.APPROVE_OPTION) {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new FileWriter(Paths.get(jfch.getSelectedFile().getPath() , "ResultFile.txt").toString()));
                pw.println(txtArea_main.getText());
                pw.close();
            } catch (IOException ex) {
                Logger.getLogger(ResultPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method sets an action for the btn_exit button.
     *
     * @param e an action event
     */
    private void btn_exitActionPerformed(ActionEvent e) {
        f.dispose();
    }

    /**
     * Appends the given text to the end of the documents
     *
     * @param text the text to insert
     */
    public void setMessage(String text) {
        txtArea_main.append(text);
        txtArea_main.setCaretPosition(txtArea_main.getDocument().getLength());
    }

    /**
     * Enables the status of all buttons
     */
    public void setEnabledButton() {
        btn_showFeat.setEnabled(true);
        btn_showDir.setEnabled(true);
        btn_saveRes.setEnabled(true);
        btn_exit.setEnabled(true);
    }

//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            System.out.println("Error setting native LAF: " + e);
//        }
//
//        ResultPanel mop = new ResultPanel("C:\\Users\\ST\\Desktop\\");
//
//        for (int i = 0; i < 30; i++) {
//            mop.setMessage("Showing results ....................(" + i + ")\n");
//        }
//        mop.setEnabledButton();
//    }
}
