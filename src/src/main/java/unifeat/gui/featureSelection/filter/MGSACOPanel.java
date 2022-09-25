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
 * of the microarray gene selection based on ant colony optimization (MGSACO)
 * method.
 *
 * @author Shahin Salavati
 * @author Sina Tabakhi
 * @see unifeat.gui.ParameterPanel
 * @see unifeat.featureSelection.filter.unsupervised.MGSACO
 */
public class MGSACOPanel extends ParameterPanel {

    JLabel lbl_initPheromone, lbl_initPheromoneError,
            lbl_numIteration, lbl_numIterationError,
            lbl_numAnts, lbl_numAntsError,
            lbl_evRate, lbl_evRateError,
            lbl_beta, lbl_betaError,
            lbl_q0, lbl_q0Error;
    JTextField txt_initPheromone,
            txt_numIteration,
            txt_numAnts, txt_evRate,
            txt_beta, txt_q0;
    private double initPheromone = 0.2, evRate = 0.2, beta = 1, q0 = 0.7;
    private static final double DEFAULT_INIT_PHEROMONE = 0.2, DEFAULT_EV_RATE = 0.2, DEFAULT_BETA = 1, DEFAULT_Q0 = 0.7;
    private int numIteration = 50, numAnts = 0;
    private static final int DEFAULT_NUM_ITERATION = 50, DEFAULT_NUM_ANTS = 0;

    /**
     * Creates new form MGSACOPanel. This method is called from within the
     * constructor to initialize the form.
     */
    public MGSACOPanel() {
        super("Parameter Settings Panel",
                "MGSACO settings:",
                "<html> Microarray gene selection based on ant colony optimization (MGSACO) is a framework to combine the computational efficiency of the filter approach and the good performance of the ACO algorithm. MGSACO is unsupervised filter based gene selection method. </html>",
                "Option\n\n"
                + "Initial pheromone -> the initial value of the pheromone.\n\n"
                + "Number of iterations -> the maximum number of allowed iterations that algorithm repeated.\n\n"
                + "Number of ants -> define the number of ants which it's depend to the number of original features (0 means the number of ants for each data set is set to the number of its original features. But,for the data sets with more than 100 features this parameter is set to 100).\n\n"
                + "Evaporation rate -> the evaporation rate of the pheromone (a real number in the range of (0, 1)).\n\n"
                + "Parameter beta -> the beta parameter in the state transition rule (a real number greater than zero (i.e., beta>0)).\n\n"
                + "Parameter q0 -> the q0 parameter in the state transition rule (a real number in the range of [0, 1]).",
                new Rectangle(10, 10, 540, 20),
                new Rectangle(10, 35, 545, 80),
                new Rectangle(190, 360, 75, 25),
                new Rectangle(310, 360, 75, 25),
                new Dimension(575, 450));

        Container contentPane = getContentPane();

        lbl_initPheromone = new JLabel("Initial pheromone:");
        lbl_initPheromone.setBounds(50, 135, 170, 22);
        txt_initPheromone = new JTextField(String.valueOf(DEFAULT_INIT_PHEROMONE));
        txt_initPheromone.setBounds(180, 135, 120, 24);
        txt_initPheromone.addKeyListener(this);
        lbl_initPheromoneError = new JLabel("");
        lbl_initPheromoneError.setBounds(310, 135, 50, 22);
        lbl_initPheromoneError.setForeground(Color.red);

        lbl_numIteration = new JLabel("Number of iterations:");
        lbl_numIteration.setBounds(50, 170, 170, 22);
        txt_numIteration = new JTextField(String.valueOf(DEFAULT_NUM_ITERATION));
        txt_numIteration.setBounds(180, 170, 120, 24);
        txt_numIteration.addKeyListener(this);
        lbl_numIterationError = new JLabel("");
        lbl_numIterationError.setBounds(310, 170, 50, 22);
        lbl_numIterationError.setForeground(Color.red);

        lbl_numAnts = new JLabel("Number of ants:");
        lbl_numAnts.setBounds(50, 205, 170, 22);
        txt_numAnts = new JTextField(String.valueOf(DEFAULT_NUM_ANTS));
        txt_numAnts.setBounds(180, 205, 120, 24);
        txt_numAnts.addKeyListener(this);
        lbl_numAntsError = new JLabel("");
        lbl_numAntsError.setBounds(310, 205, 50, 22);
        lbl_numAntsError.setForeground(Color.red);

        lbl_evRate = new JLabel("Evaporation rate:");
        lbl_evRate.setBounds(50, 240, 170, 22);
        txt_evRate = new JTextField(String.valueOf(DEFAULT_EV_RATE));
        txt_evRate.setBounds(180, 240, 120, 24);
        txt_evRate.addKeyListener(this);
        lbl_evRateError = new JLabel("");
        lbl_evRateError.setBounds(310, 240, 50, 22);
        lbl_evRateError.setForeground(Color.red);

        lbl_beta = new JLabel("Parameter beta:");
        lbl_beta.setBounds(50, 275, 170, 22);
        txt_beta = new JTextField(String.valueOf(DEFAULT_BETA));
        txt_beta.setBounds(180, 275, 120, 24);
        txt_beta.addKeyListener(this);
        lbl_betaError = new JLabel("");
        lbl_betaError.setBounds(310, 275, 50, 22);
        lbl_betaError.setForeground(Color.red);

        lbl_q0 = new JLabel("Parameter q0:");
        lbl_q0.setBounds(50, 310, 170, 22);
        txt_q0 = new JTextField(String.valueOf(DEFAULT_Q0));
        txt_q0.setBounds(180, 310, 120, 24);
        txt_q0.addKeyListener(this);
        lbl_q0Error = new JLabel("");
        lbl_q0Error.setBounds(310, 310, 50, 22);
        lbl_q0Error.setForeground(Color.red);

        contentPane.add(lbl_initPheromone);
        contentPane.add(txt_initPheromone);
        contentPane.add(lbl_initPheromoneError);

        contentPane.add(lbl_numIteration);
        contentPane.add(txt_numIteration);
        contentPane.add(lbl_numIterationError);

        contentPane.add(lbl_numAnts);
        contentPane.add(txt_numAnts);
        contentPane.add(lbl_numAntsError);

        contentPane.add(lbl_evRate);
        contentPane.add(txt_evRate);
        contentPane.add(lbl_evRateError);

        contentPane.add(lbl_beta);
        contentPane.add(txt_beta);
        contentPane.add(lbl_betaError);

        contentPane.add(lbl_q0);
        contentPane.add(txt_q0);
        contentPane.add(lbl_q0Error);

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

        tempStr = txt_initPheromone.getText();
        if (!MathFunc.isDouble(tempStr)) {
            lbl_initPheromoneError.setText("*");
            enableOkButton = false;
        } else {
            lbl_initPheromoneError.setText("");
        }

        tempStr = txt_numIteration.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 1)) {
            lbl_numIterationError.setText("*");
            enableOkButton = false;
        } else {
            lbl_numIterationError.setText("");
        }

        tempStr = txt_numAnts.getText();
        if (!MathFunc.isInteger(tempStr) || (Integer.parseInt(tempStr) < 0)) {
            lbl_numAntsError.setText("*");
            enableOkButton = false;
        } else {
            lbl_numAntsError.setText("");
        }

        tempStr = txt_evRate.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0 || Double.parseDouble(tempStr) >= 1) {
            lbl_evRateError.setText("*");
            enableOkButton = false;
        } else {
            lbl_evRateError.setText("");
        }

        tempStr = txt_beta.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) <= 0) {
            lbl_betaError.setText("*");
            enableOkButton = false;
        } else {
            lbl_betaError.setText("");
        }

        tempStr = txt_q0.getText();
        if (!MathFunc.isDouble(tempStr) || Double.parseDouble(tempStr) < 0 || Double.parseDouble(tempStr) > 1) {
            lbl_q0Error.setText("*");
            enableOkButton = false;
        } else {
            lbl_q0Error.setText("");
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
        setInitPheromone(Double.parseDouble(txt_initPheromone.getText()));
        setNumIteration(Integer.parseInt(txt_numIteration.getText()));
        setNumAnts(Integer.parseInt(txt_numAnts.getText()));
        setEvRate(Double.parseDouble(txt_evRate.getText()));
        setBeta(Double.parseDouble(txt_beta.getText()));
        setQ0(Double.parseDouble(txt_q0.getText()));
        super.btn_okActionPerformed(e);
    }

    /**
     * This method returns the initial pheromone value.
     *
     * @return the <code>initPheromone</code> parameter
     */
    public double getInitPheromone() {
        return initPheromone;
    }

    /**
     * This method sets the initial pheromone value.
     *
     * @param initPheromone the initial pheromone value
     */
    public void setInitPheromone(double initPheromone) {
        this.initPheromone = initPheromone;
    }

    /**
     * This method returns the number of iterations value.
     *
     * @return the <code>numIteration</code> parameter
     */
    public int getNumIteration() {
        return numIteration;
    }

    /**
     * This method sets the number of iterations value.
     *
     * @param numIteration the number of iterations value
     */
    public void setNumIteration(int numIteration) {
        this.numIteration = numIteration;
    }

    /**
     * This method returns the number of ants value.
     *
     * @return the <code>numAnts</code> parameter
     */
    public int getNumAnts() {
        return numAnts;
    }

    /**
     * This method sets the number of ants value.
     *
     * @param numAnts the number of ants value
     */
    public void setNumAnts(int numAnts) {
        this.numAnts = numAnts;
    }

    /**
     * This method returns the evaporation rate.
     *
     * @return the <code>evaporation rate</code> parameter
     */
    public double getEvRate() {
        return evRate;
    }

    /**
     * This method sets the evaporation rate value.
     *
     * @param evRate the evaporation rate value
     */
    public void setEvRate(double evRate) {
        this.evRate = evRate;
    }

    /**
     * This method returns the beta.
     *
     * @return the <code>beta</code> parameter
     */
    public double getBeta() {
        return beta;
    }

    /**
     * This method sets the beta value.
     *
     * @param beta the beta value
     */
    public void setBeta(double beta) {
        this.beta = beta;
    }

    /**
     * This method returns the q0.
     *
     * @return the <code>q0</code> parameter
     */
    public double getQ0() {
        return q0;
    }

    /**
     * This method sets the q0 value.
     *
     * @param q0 the q0 value
     */
    public void setQ0(double q0) {
        this.q0 = q0;
    }

    /**
     * Sets the default values of the MGSACO parameters
     */
    public void setDefaultValue() {
        txt_initPheromone.setText(String.valueOf(DEFAULT_INIT_PHEROMONE));
        txt_numIteration.setText(String.valueOf(DEFAULT_NUM_ITERATION));
        txt_numAnts.setText(String.valueOf(DEFAULT_NUM_ANTS));
        txt_evRate.setText(String.valueOf(DEFAULT_EV_RATE));
        txt_beta.setText(String.valueOf(DEFAULT_BETA));
        txt_q0.setText(String.valueOf(DEFAULT_Q0));

        initPheromone = DEFAULT_INIT_PHEROMONE;
        numIteration = DEFAULT_NUM_ITERATION;
        numAnts = DEFAULT_NUM_ANTS;
        evRate = DEFAULT_EV_RATE;
        beta = DEFAULT_BETA;
        q0 = DEFAULT_Q0;
    }

    /**
     * Sets the last values of the MGSACO parameters entered by user
     *
     * @param initPheromoneValue the initial value of the pheromone
     * @param numIterations the maximum number of iteration
     * @param numberAnt the number of ants
     * @param evaporationRate the evaporation rate of the pheromone
     * @param betaParameter the beta parameter in the state transition rule
     * @param q0_Parameter the q0 parameter in the state transition rule
     */
    public void setUserValue(double initPheromoneValue, int numIterations, int numberAnt, double evaporationRate, double betaParameter, double q0_Parameter) {
        initPheromone = initPheromoneValue;
        numIteration = numIterations;
        numAnts = numberAnt;
        evRate = evaporationRate;
        beta = betaParameter;
        q0 = q0_Parameter;

        txt_initPheromone.setText(String.valueOf(initPheromone));
        txt_numIteration.setText(String.valueOf(numIteration));
        txt_numAnts.setText(String.valueOf(numAnts));
        txt_evRate.setText(String.valueOf(evRate));
        txt_beta.setText(String.valueOf(beta));
        txt_q0.setText(String.valueOf(q0));
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
//        MGSACOPanel dtpanel = new MGSACOPanel();
//        Dialog dlg = new Dialog(dtpanel);
//        dtpanel.setVisible(true);
//        System.out.println("Init pheromone = " + dtpanel.getInitPheromone());
//        System.out.println("num iteration = " + dtpanel.getNumIteration());
//        System.out.println("num ants = " + dtpanel.getNumAnts());
//        System.out.println("evaporation rate = " + dtpanel.getEvRate());
//        System.out.println("beta = " + dtpanel.getBeta());
//        System.out.println("parameter q0 = " + dtpanel.getQ0());
//    }
}
