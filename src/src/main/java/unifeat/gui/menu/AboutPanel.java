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
import java.awt.Cursor;
import java.awt.Desktop;
//import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show a panel for description of the
 * tool.
 *
 * @author Sina Tabakhi
 */
public class AboutPanel extends JDialog implements MouseListener {

    JLabel lbl_logo, lbl_name, lbl_descrip, lbl_ver, lbl_verVal,
            lbl_author, lbl_authorVal, lbl_home, lbl_homVal, lbl_footer_1;
    JPanel panel_footer;

    /**
     * Creates new form AboutPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public AboutPanel() {
        super();
        lbl_logo = new JLabel(new ImageIcon(getClass().getResource("/unifeat/gui/icons/logo.png")));
        lbl_logo.setBounds(1, 1, 240, 240);

        lbl_name = new JLabel(new ImageIcon(getClass().getResource("/unifeat/gui/icons/logo_name.png")));
        lbl_name.setBounds(240, 10, 270, 90);

        lbl_descrip = new JLabel("<html>Universal Feature Selection Tool (UniFeat) is an open-source tool, entirely developed in Java, for performing feature selection process in different areas of research.<html>");
        lbl_descrip.setBounds(260, 100, 395, 50);

        lbl_ver = new JLabel("Version:");
        lbl_ver.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl_ver.setBounds(260, 160, 70, 15);

        lbl_verVal = new JLabel("0.1.1");
        lbl_verVal.setBounds(340, 160, 100, 15);

        lbl_author = new JLabel("Author:");
        lbl_author.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl_author.setBounds(260, 185, 70, 15);

        lbl_authorVal = new JLabel("<html><a href=\"\">UniFeat team</a></html>");
        lbl_authorVal.setBounds(340, 185, 100, 15);
        lbl_authorVal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl_authorVal.addMouseListener(this);

        lbl_home = new JLabel("Home Page:");
        lbl_home.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl_home.setBounds(260, 210, 80, 15);

        lbl_homVal = new JLabel("<html><a href=\"\">https://unifeat.github.io/</a></html>");
        lbl_homVal.setBounds(340, 210, 155, 15);
        lbl_homVal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl_homVal.addMouseListener(this);

        panel_footer = new JPanel();
        panel_footer.setBounds(0, 250, 710, 45);
        panel_footer.setLayout(null);
        panel_footer.setBackground(new Color(228, 231, 237));

        lbl_footer_1 = new JLabel("UniFeat is developed at the University of Kurdistan, Iran, and distributed under the terms of the MIT License.");
        lbl_footer_1.setBounds(20, 10, 680, 15);

        panel_footer.add(lbl_footer_1);

        setModalityType(ModalityType.APPLICATION_MODAL);
        setSize(710, 323);
        setLayout(null);

        add(lbl_logo);
        add(lbl_name);
        add(lbl_descrip);
        add(lbl_ver);
        add(lbl_verVal);
        add(lbl_author);
        add(lbl_authorVal);
        add(lbl_home);
        add(lbl_homVal);
        add(panel_footer);

        setIconImage(new ImageIcon(getClass().getResource("/unifeat/gui/icons/small_logo.png")).getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("About UniFeat");
    }

    /**
     * Opens default browser using the given URL
     *
     * @param url the url of a webpage
     */
    private void openURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(AboutPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The listener method for receiving interesting mouse events on a
     * component. Invoked when the mouse button has been clicked on a component.
     *
     * @param e an mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(lbl_authorVal)) {
            openURL("https://unifeat.github.io/people.html");
        } else if (e.getSource().equals(lbl_homVal)) {
            openURL("https://unifeat.github.io/");
        }
    }

    /**
     * The listener method for receiving interesting mouse events on a
     * component. Invoked when the mouse button has been pressed on a component.
     *
     * @param e an mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * The listener method for receiving interesting mouse events on a
     * component. Invoked when the mouse button has been released on a
     * component.
     *
     * @param e an mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * The listener method for receiving interesting mouse events on a
     * component. Invoked when the mouse enters a component.
     *
     * @param e an mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * The listener method for receiving interesting mouse events on a
     * component. Invoked when the mouse exits a component.
     *
     * @param e an mouse event
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

//    public static void main(String[] args) {
//        try {
//            // Check if Nimbus is supported and get its classname
//            for (UIManager.LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(lafInfo.getName())) {
//                    UIManager.setLookAndFeel(lafInfo.getClassName());
//                    UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
//
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
//        AboutPanel mop = new AboutPanel();
//        Dialog dlg = new Dialog(mop);
//        mop.setVisible(true);
//    }
}
