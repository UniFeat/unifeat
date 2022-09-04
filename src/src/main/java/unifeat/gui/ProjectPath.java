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

import unifeat.util.FileFunc;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show a panel for the selecting path for
 * the project.
 *
 * @author Shahin Salavati
 */
public class ProjectPath extends JFrame implements ActionListener {

    JButton btn_browse, btn_select;
    JLabel lbl_select, lbl_path;
    JTextField txt_path;
    String path = "";
    JPanel mainPanel;

    /**
     * Creates new form ProjectPath. This method is called from within the
     * constructor to initialize the form.
     */
    public ProjectPath() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        lbl_select = new JLabel("Please select a folder for the tool:");
        lbl_select.setBounds(20, 30, 204, 14);

        lbl_path = new JLabel("Folder:");
        lbl_path.setBounds(20, 70, 34, 14);

        txt_path = new JTextField();
        txt_path.setBounds(60, 67, 180, 21);
        txt_path.setBackground(Color.WHITE);
        txt_path.setEditable(false);

        btn_browse = new JButton("Browse...");
        btn_browse.setBounds(250, 66, 80, 23);
        btn_browse.addActionListener(this);

        btn_select = new JButton("Ok");
        btn_select.setBounds(140, 125, 69, 23);
        btn_select.addActionListener(this);
        btn_select.setEnabled(false);

        mainPanel.add(lbl_select);
        mainPanel.add(btn_browse);
        mainPanel.add(btn_select);
        mainPanel.add(lbl_path);
        mainPanel.add(txt_path);

        this.setTitle("Workspase Selection");
        this.setIconImage(new ImageIcon(getClass().getResource("/unifeat/gui/icons/small_logo.png")).getImage());
        this.setSize(350, 190);
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * The listener method for receiving action events.
     * Invoked when an action occurs.
     *
     * @param e an action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_browse)) {
            btn_browseActionPerformed(e);
        } else if (e.getSource().equals(btn_select)) {
            btn_selectActionPerformed(e);
        }
    }

    /**
     * This method sets an action for the btn_browse button.
     *
     * @param e an action event
     */
    private void btn_browseActionPerformed(ActionEvent e) {
        JFileChooser jfch = new JFileChooser();
        jfch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (jfch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            txt_path.setText(jfch.getSelectedFile().getPath());
            path = txt_path.getText();
            btn_select.setEnabled(true);
        }
    }

    /**
     * This method sets an action for the btn_select button.
     *
     * @param e an action event
     */
    private void btn_selectActionPerformed(ActionEvent e) {
        //creates the two folder (CSV - ARFF)in the selected path
        FileFunc.createDirectory(path + "\\CSV\\");
        FileFunc.createDirectory(path + "\\ARFF\\");

        MainPanel ui = new MainPanel(path);
        ui.createAndShow();
        this.setVisible(false);
    }

    /**
     * This method is the main method to start the tool.
     *
     * @param args an array of String objects for supplying command-line 
     * arguments
     * 
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        Handler globalExceptionHandler = new Handler();
        Thread.setDefaultUncaughtExceptionHandler(globalExceptionHandler);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Error setting native LAF: " + e);
        }

        SwingUtilities.invokeLater(() -> {
            ProjectPath sp = new ProjectPath();
        });
    }
}

/**
 * This java class is used to global exception handling in the program.
 * 
 * @author Sina Tabakhi
 */
class Handler implements Thread.UncaughtExceptionHandler {
    
    /**
     * This method handles uncaught exceptions.
     *
     * @param t the main thread of the program 
     * @param e the unhandled exception
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        JOptionPane.showMessageDialog(null, "An error occurred in running the program. Please report the error to the developer team.", 
                                    "Error", JOptionPane.ERROR_MESSAGE);

        Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, 
                "An error occurred in running the program. Please report the error to the developer team.", 
                e);
    }
}

