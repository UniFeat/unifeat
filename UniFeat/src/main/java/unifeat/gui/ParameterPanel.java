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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The abstract class contains the main methods and fields that are used to
 * create and show a panel for the parameter settings.
 *
 * @author Sina Tabakhi
 */
public abstract class ParameterPanel extends JDialog
        implements ActionListener, KeyListener {

    protected JLabel lbl_title, lbl_about;
    protected JButton btn_ok, btn_more;
    protected JPanel panel_about;
    protected String moreOptionDescription;

    /**
     * Creates new form ParameterPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public ParameterPanel() {
        this("Panel Title",
                "Your method settings title:",
                "Description of your method",
                "Information of your method in details.",
                new Rectangle(10, 10, 140, 20),
                new Rectangle(10, 50, 400, 60),
                new Rectangle(120, 250, 75, 23),
                new Rectangle(240, 250, 75, 23),
                new Dimension(440, 340));
    }

    /**
     * Creates new form ParameterPanel. This method is called from within the
     * constructor to initialize the form.
     *
     * @param panelTitle the title of the created panel
     * @param methodTitle the title of the method settings
     * @param methodDescription the description of the method
     * @param moreOptionDescription the description of the parameters in details
     * that is shown in more option panel
     * @param methodTitlePosition the position of the <code>methodTitle</code>
     * in the created panel
     * @param methodDescriptionPosition the position of the
     * <code>methodDescription</code> in the created panel
     * @param okButtonPosition the position of the <code>Ok</code> button in the
     * created panel
     * @param moreButtonPosition the position of the <code>More</code> button in
     * the created panel
     * @param panelSize the size of the created panel
     */
    public ParameterPanel(String panelTitle, String methodTitle, String methodDescription, String moreOptionDescription,
            Rectangle methodTitlePosition, Rectangle methodDescriptionPosition, Rectangle okButtonPosition,
            Rectangle moreButtonPosition, Dimension panelSize) {
        super();
        this.moreOptionDescription = moreOptionDescription;
        lbl_title = new JLabel(methodTitle);
        lbl_title.setBounds(methodTitlePosition);

        panel_about = new JPanel();
        panel_about.setBounds(methodDescriptionPosition);
        panel_about.setLayout(null);
        panel_about.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(171, 170, 170)), "About"));
        lbl_about = new JLabel(methodDescription);
        lbl_about.setBounds(methodDescriptionPosition.x + 5, methodDescriptionPosition.y - 30, methodDescriptionPosition.width - 15, methodDescriptionPosition.height);

        panel_about.add(lbl_about);

        btn_ok = new JButton("Ok");
        btn_ok.setBounds(okButtonPosition);
        btn_ok.addActionListener(this);

        btn_more = new JButton("More");
        btn_more.setBounds(moreButtonPosition);
        btn_more.addActionListener(this);

        setModalityType(ModalityType.APPLICATION_MODAL);
        setIconImage(new ImageIcon(getClass().getResource("/unifeat/gui/icons/small_logo.png")).getImage());
        setSize(panelSize);
        setLayout(null);
        add(lbl_title);
        add(panel_about);
        add(btn_ok);
        add(btn_more);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle(panelTitle);
    }

    /**
     * The listener method for receiving action events. Invoked when an action
     * occurs.
     *
     * @param e an action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_ok)) {
            btn_okActionPerformed(e);
        } else if (e.getSource().equals(btn_more)) {
            btn_moreActionPerformed(e);
        }
    }

    /**
     * The listener method for receiving keyboard events (keystrokes). Invoked
     * when a key has been typed.
     *
     * @param e an action event
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * The listener method for receiving keyboard events (keystrokes). Invoked
     * when a key has been pressed.
     *
     * @param e an action event
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * The listener method for receiving keyboard events (keystrokes). Invoked
     * when a key has been released.
     *
     * @param e an action event
     */
    @Override
    public abstract void keyReleased(KeyEvent e);

    /**
     * This method sets an action for the btn_ok button.
     *
     * @param e an action event
     */
    protected void btn_okActionPerformed(ActionEvent e) {
        dispose();
    }

    /**
     * This method sets an action for the btn_more button.
     *
     * @param e an action event
     */
    protected void btn_moreActionPerformed(ActionEvent e) {
        MoreOpPanel morePanel = new MoreOpPanel(this.moreOptionDescription);
        morePanel.setVisible(true);
    }

    /**
     * This method sets the title of the panel.
     *
     * @param panelTitle the title of the panel
     */
    protected void setPanelTitle(String panelTitle) {
        setTitle(panelTitle);
    }

    /**
     * This method sets the title of the method settings.
     *
     * @param methodTitle the title of the method settings
     */
    protected void setMethodTitle(String methodTitle) {
        lbl_title.setText(methodTitle);
    }

    /**
     * This method sets the description of the method.
     *
     * @param methodDescription the description of the method
     */
    protected void setMethodDescription(String methodDescription) {
        lbl_about.setText(methodDescription);
    }

    /**
     * This method sets the description of the parameters in details that is
     * shown in more option panel.
     *
     * @param moreOptionDescription the description of the parameters in details
     */
    protected void setMoreOptionDescription(String moreOptionDescription) {
        this.moreOptionDescription = moreOptionDescription;
    }

    /**
     * This method returns the description of the parameters in details that is
     * shown in more option panel.
     *
     * @return the description of the parameters in details
     */
    protected String getMoreOptionDescription() {
        return this.moreOptionDescription;
    }

    /**
     * This method sets the size of the created panel.
     *
     * @param size the size of the created panel
     */
    protected void setPanelSize(Dimension size) {
        setSize(size);
    }

    /**
     * This method sets the position of the <code>methodTitle</code> in the
     * created panel.
     *
     * @param methodTitlePosition the position of the <code>methodTitle</code>
     * in the created panel
     */
    protected void setMethodTitlePosition(Rectangle methodTitlePosition) {
        lbl_title.setBounds(methodTitlePosition);
    }

    /**
     * This method sets the position of the <code>methodDescription</code> in
     * the created panel.
     *
     * @param methodDescriptionPosition the position of the
     * <code>methodDescription</code> in the created panel
     */
    protected void setMethodDescriptionPosition(Rectangle methodDescriptionPosition) {
        panel_about.setBounds(methodDescriptionPosition);
        lbl_about.setBounds(methodDescriptionPosition.x + 5, methodDescriptionPosition.y - 30, methodDescriptionPosition.width - 15, methodDescriptionPosition.height);
    }

    /**
     * This method sets the position of the <code>Ok</code> button in the
     * created panel.
     *
     * @param okButtonPosition the position of the <code>Ok</code> button in the
     * created panel
     */
    protected void setOkButtonPosition(Rectangle okButtonPosition) {
        btn_ok.setBounds(okButtonPosition);
    }

    /**
     * This method sets the position of the <code>More</code> button in the
     * created panel.
     *
     * @param moreButtonPosition the position of the <code>More</code> button in
     * the created panel
     */
    protected void setMoreButtonPosition(Rectangle moreButtonPosition) {
        btn_more.setBounds(moreButtonPosition);
    }
}
