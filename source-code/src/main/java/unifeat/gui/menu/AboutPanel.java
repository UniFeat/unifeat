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
            lbl_author, lbl_authorVal, lbl_home, lbl_homVal, lbl_footer_1,
            lbl_footer_2, lbl_footer_3, lbl_nameUni, lbl_License;
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
        lbl_descrip.setBounds(260, 100, 375, 50);

        lbl_ver = new JLabel("Version:");
        lbl_ver.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl_ver.setBounds(260, 160, 70, 15);

        lbl_verVal = new JLabel("0.1.0");
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
        lbl_home.setBounds(260, 210, 70, 15);

        lbl_homVal = new JLabel("<html><a href=\"\">https://unifeat.github.io/</a></html>");
        lbl_homVal.setBounds(340, 210, 120, 15);
        lbl_homVal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl_homVal.addMouseListener(this);

        panel_footer = new JPanel();
        panel_footer.setBounds(0, 250, 670, 45);
        panel_footer.setLayout(null);
        panel_footer.setBackground(new Color(225, 225, 225));

        lbl_footer_1 = new JLabel("UniFeat is developed at the ");
        lbl_footer_1.setBounds(50, 10, 135, 15);

        lbl_nameUni = new JLabel("<html><a href=\"\">University of Kurdistan</a></html>");
        lbl_nameUni.setBounds(185, 10, 109, 15);
        lbl_nameUni.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl_nameUni.addMouseListener(this);

        lbl_footer_2 = new JLabel(", Iran, and distributed under the terms of the ");
        lbl_footer_2.setBounds(294, 10, 223, 15);

        lbl_License = new JLabel("<html><a href=\"\">MIT License</a></html>");
        lbl_License.setBounds(517, 10, 57, 15);
        lbl_License.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl_License.addMouseListener(this);

        lbl_footer_3 = new JLabel(".");
        lbl_footer_3.setBounds(574, 10, 5, 15);

        panel_footer.add(lbl_footer_1);
        panel_footer.add(lbl_nameUni);
        panel_footer.add(lbl_footer_2);
        panel_footer.add(lbl_License);
        panel_footer.add(lbl_footer_3);

        setModalityType(ModalityType.APPLICATION_MODAL);
        setSize(670, 323);
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
        } else if (e.getSource().equals(lbl_nameUni)) {
            openURL("http://international.uok.ac.ir/");
        } else if (e.getSource().equals(lbl_License)) {
            openURL("https://opensource.org/licenses/MIT");
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
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            System.out.println("Error setting native LAF: " + e);
//        }
//
//        AboutPanel mop = new AboutPanel();
//        Dialog dlg = new Dialog(mop);
//        mop.setVisible(true);
//    }
}
