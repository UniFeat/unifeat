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

import unifeat.classifier.*;
import unifeat.dataset.DatasetInfo;
import unifeat.featureSelection.embedded.EmbeddedApproach;
import unifeat.featureSelection.embedded.EmbeddedType;
import unifeat.featureSelection.filter.FilterApproach;
import unifeat.featureSelection.filter.FilterType;
import unifeat.featureSelection.filter.NonWeightedFilterType;
import unifeat.featureSelection.filter.WeightedFilterApproach;
import unifeat.featureSelection.filter.WeightedFilterType;
import unifeat.featureSelection.filter.supervised.SupervisedFilterType;
import unifeat.featureSelection.filter.unsupervised.UnsupervisedFilterType;
import unifeat.featureSelection.hybrid.HybridApproach;
import unifeat.featureSelection.hybrid.HybridType;
import unifeat.featureSelection.wrapper.WrapperApproach;
import unifeat.featureSelection.wrapper.WrapperType;
import unifeat.gui.classifier.DTClassifierPanel;
import unifeat.gui.classifier.KNNClassifierPanel;
import unifeat.gui.classifier.svmClassifier.SVMClassifierPanel;
import unifeat.gui.featureSelection.embedded.MSVM_RFEPanel;
import unifeat.gui.featureSelection.embedded.decisionTreeBased.DecisionTreeBasedPanel;
import unifeat.gui.featureSelection.embedded.decisionTreeBased.TreeType;
import unifeat.gui.featureSelection.filter.IRRFSACO_1Panel;
import unifeat.gui.featureSelection.filter.IRRFSACO_2Panel;
import unifeat.gui.featureSelection.filter.LaplacianScorePanel;
import unifeat.gui.featureSelection.filter.MGSACOPanel;
import unifeat.gui.featureSelection.filter.RRFSACO_1Panel;
import unifeat.gui.featureSelection.filter.RRFSACO_2Panel;
import unifeat.gui.featureSelection.filter.RRFSPanel;
import unifeat.gui.featureSelection.filter.rsm.RSMPanel;
import unifeat.gui.featureSelection.filter.UFSACOPanel;
import unifeat.gui.featureSelection.filter.rsm.MultivariateMethodType;
import unifeat.gui.featureSelection.wrapper.ACOBased.OptimalACOPanel;
import unifeat.gui.featureSelection.wrapper.GABased.HGAFSPanel;
import unifeat.gui.featureSelection.wrapper.GABased.SimpleGAPanel;
import unifeat.gui.featureSelection.wrapper.PSOBased.BPSOPanel;
import unifeat.gui.featureSelection.wrapper.PSOBased.CPSOPanel;
import unifeat.gui.featureSelection.wrapper.PSOBased.HPSO_LSPanel;
import unifeat.gui.featureSelection.wrapper.PSOBased.PSO42Panel;
import unifeat.gui.menu.AboutPanel;
import unifeat.gui.menu.DiagramPanel;
import unifeat.gui.menu.FriedmanPanel;
import unifeat.gui.menu.PreprocessPanel;
import unifeat.gui.menu.selectMode.SelectModePanel;
import unifeat.gui.menu.selectMode.SelectModeType;
import unifeat.result.ResultType;
import unifeat.result.Results;
import unifeat.util.FileFunc;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
//import javax.swing.SwingUtilities;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

/**
 * This java class is used to create and show the main panel of the project. The
 * management of all panels and forms have been done in this class.
 *
 * @author Sina Tabakhi
 * @author Shahin Salavati
 */
public class MainPanel extends JPanel {

    JPanel panel_filePath, panel_randomSet, panel_ttSet,
            panel_featureMethod, panel_classifier, panel_config;
    ButtonGroup bg_dataset;
    JRadioButton rd_randSet, rd_ttset;
    //---------------- Random Panel -------------------------------------------
    JButton btn_inputdst, btn_classlbl;
    JLabel lbl_dataset, lbl_inputdst, lbl_classlbl;
    JTextField txt_inputdst, txt_classLbl;
    //--------------- TrainingTest Panel --------------------------------------
    JButton btn_trainSet, btn_testSet, btn_classLabel;
    JLabel lbl_trainSet, lbl_testSet, lbl_classlabel;
    JTextField txt_trainSet, txt_testSet, txt_classLabel;
    //--------------- Feature Selection Panel ---------------------------------
    JTabbedPane tab_pane;
    JPanel panel_filter, panel_wrapper, panel_embedded, panel_hybrid,
            panel_numFeat;
    JComboBox cb_supervised, cb_unsupervised, cb_wrapper, cb_embedded,
            cb_hybrid;
    JLabel lbl_sup, lbl_unsup, lbl_wrapper, lbl_embedded, lbl_hybrid;
    JButton btn_moreOpFilter, btn_moreOpWrapper, btn_moreOpEmbedded,
            btn_moreOpHybrid;
    JTextArea txtArea_feature;
    JScrollPane sc_feature;
    //--------------- Classifier Panel ----------------------------------------
    JComboBox cb_classifier;
    JLabel lbl_classifier;
    JButton btn_moreOpClassifier;
    //--------------- Configuration Panel--------------------------------------
    JComboBox cb_start;
    JLabel lbl_start;
    JButton btn_start, btn_exit;
    //--------------- Menu Panel ----------------------------------------------
    EventHandler eh;
    JMenuBar menuBar;
    JMenu file, diagram, analyse, help;
    JMenuItem mi_preprocess, mi_exit, mi_exeTime, mi_accur, mi_error,
            mi_friedman, mi_help, mi_about;
    JSeparator sep_file, sep_help;
    //--------------- Progress Bar --------------------------------------------
    JProgressBar progressBar;
    int progressValue, runCode = 0;
    //-------------- Other variables ------------------------------------------
    private final String PATH_PROJECT;
    private final String PATH_DATA_CSV;
    private final String PATH_DATA_ARFF;
    private double simValue; //RRFS method
    private double constParam; //Laplacian score method
    private int KNearest; //Laplacian score method
    private int numSelection, sizeSubspace, elimination; //RSM method
    private MultivariateMethodType multMethodName; //RSM method
    private int numIteration, numAnts, numFeatOfAnt; //ACO-based methods
    private double initPheromone, evRate, alpha, beta, q0; //ACO_based methods
    private TreeType decisionTreeBasedMethodType; //DT based method[Embedded]
    private double confidence; //DT based method[Embedded_C45]
    private int minNum; //DT based method[Embedded_C45]
    private int randomForestNumFeatures, maxDepth, randomForestNumIterations; //DT based method[Embedded _ Random Forest]
    private int randomTreeKValue, randomTreeMaxDepth; //DT based method[Embedded_Random Tree]
    private double randomTreeMinNum, randomTreeMinVarianceProp; //DT based method[Embedded_Rendom Tree]
    private SVMClassifierPanel svmFeatureSelectionPanel;
    private MSVM_RFEPanel msvmFeatureSelectionPanel; //MSVM_RFE method
    private BPSOPanel bpsoFeatSelectionPanel; //BPSO method
    private CPSOPanel cpsoFeatSelectionPanel; //CPSO method
    private PSO42Panel pso42FeatSelectionPanel; //PSO(4-2) method
    private HPSO_LSPanel hpsolsFeatSelectionPanel; //HPSO-LS method
    private SimpleGAPanel simpleGAFeatSelectionPanel; //Simple GA method
    private HGAFSPanel hgafsFeatSelectionPanel; //HGAFS method
    private OptimalACOPanel optimalACOFeatSelectionPanel; //Optimal ACO method
    private DatasetInfo data;
    //-------------- Evaluation classifier panels -----------------------------
    private SVMClassifierPanel svmEvaluationPanel;
    private DTClassifierPanel dtEvaluationPanel;
    private KNNClassifierPanel knnEvaluationPanel;
    private Object selectedEvaluationClassifierPanel;
    //-------------- Result variables -----------------------------------------
    private int[] numSelectedSubsets;
    private Results finalResults;

    /**
     * Creates new form MainPanel. This method is called from within the
     * constructor to initialize the form.
     *
     * @param path the path of the project
     */
    public MainPanel(String path) {
        eh = new EventHandler();
        setLayout(null);

        /////////////////// set the path files ////////////////////////////////
        PATH_PROJECT = path + "\\";
        PATH_DATA_CSV = PATH_PROJECT + "CSV\\";
        PATH_DATA_ARFF = PATH_PROJECT + "ARFF\\";

        /////////////////////// "File Paths" panel ////////////////////////////
        panel_filePath = new JPanel();
        panel_filePath.setBounds(10, 15, 825, 210);
        panel_filePath.setLayout(null);
        panel_filePath.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "File paths"));
        lbl_dataset = new JLabel("Dataset option:");
        lbl_dataset.setBounds(15, 25, 85, 20);
        bg_dataset = new ButtonGroup();
        rd_randSet = new JRadioButton("Random training/test sets");
        rd_ttset = new JRadioButton("Training/Test sets");
        rd_randSet.setBounds(105, 25, 170, 20);
        rd_ttset.setBounds(275, 25, 195, 20);

        bg_dataset.add(rd_ttset);
        bg_dataset.add(rd_randSet);

        rd_randSet.addItemListener(eh);
        rd_ttset.addItemListener(eh);

        panel_filePath.add(rd_randSet);
        panel_filePath.add(rd_ttset);
        panel_filePath.add(lbl_dataset);

        ///////////////// "Random Training/Test sets" panel ///////////////////
        panel_randomSet = new JPanel();
        panel_randomSet.setBounds(10, 60, 400, 140);
        panel_randomSet.setLayout(null);
        panel_randomSet.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Random training/test sets"));
        lbl_inputdst = new JLabel("Input dataset:");
        lbl_inputdst.setBounds(15, 46, 80, 15);
        lbl_classlbl = new JLabel("Input class label:");
        lbl_classlbl.setBounds(15, 82, 90, 15);
        txt_inputdst = new JTextField();
        txt_inputdst.setBounds(110, 45, 170, 21);
        txt_inputdst.setEditable(false);
        txt_classLbl = new JTextField();
        txt_classLbl.setBounds(110, 80, 170, 21);
        txt_classLbl.setEditable(false);
        btn_inputdst = new JButton("Open file...");
        btn_inputdst.setBounds(295, 44, 87, 23);
        btn_classlbl = new JButton("Open file...");
        btn_classlbl.setBounds(295, 79, 87, 23);

        btn_inputdst.addActionListener(eh);
        btn_classlbl.addActionListener(eh);

        panel_randomSet.add(lbl_inputdst);
        panel_randomSet.add(lbl_classlbl);
        panel_randomSet.add(txt_inputdst);
        panel_randomSet.add(txt_classLbl);
        panel_randomSet.add(btn_classlbl);
        panel_randomSet.add(btn_inputdst);

        ////////////////// "Training/Test sets" panel /////////////////////////
        panel_ttSet = new JPanel();
        panel_ttSet.setBounds(415, 60, 400, 140);
        panel_ttSet.setLayout(null);
        panel_ttSet.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Training/Test sets"));
        lbl_trainSet = new JLabel("Input training set:");
        lbl_trainSet.setBounds(15, 37, 90, 15);
        lbl_testSet = new JLabel("Input test set:");
        lbl_testSet.setBounds(15, 72, 80, 15);
        lbl_classlabel = new JLabel("Input class label:");
        lbl_classlabel.setBounds(15, 107, 90, 15);
        txt_trainSet = new JTextField();
        txt_trainSet.setBounds(110, 35, 170, 21);
        txt_trainSet.setEditable(false);
        txt_testSet = new JTextField();
        txt_testSet.setBounds(110, 70, 170, 21);
        txt_testSet.setEditable(false);
        txt_classLabel = new JTextField();
        txt_classLabel.setBounds(110, 105, 170, 21);
        txt_classLabel.setEditable(false);
        btn_trainSet = new JButton("Open file...");
        btn_trainSet.setBounds(295, 34, 87, 23);
        btn_testSet = new JButton("Open file...");
        btn_testSet.setBounds(295, 69, 87, 23);
        btn_classLabel = new JButton("Open file...");
        btn_classLabel.setBounds(295, 104, 87, 23);

        btn_trainSet.addActionListener(eh);
        btn_testSet.addActionListener(eh);
        btn_classLabel.addActionListener(eh);

        panel_ttSet.add(lbl_classlabel);
        panel_ttSet.add(lbl_trainSet);
        panel_ttSet.add(lbl_testSet);
        panel_ttSet.add(txt_trainSet);
        panel_ttSet.add(txt_testSet);
        panel_ttSet.add(txt_classLabel);
        panel_ttSet.add(btn_trainSet);
        panel_ttSet.add(btn_testSet);
        panel_ttSet.add(btn_classLabel);
        panel_filePath.add(panel_randomSet);
        panel_filePath.add(panel_ttSet);

        //////////////// "Feature selection methods" panel ////////////////////
        panel_featureMethod = new JPanel();
        panel_featureMethod.setBounds(10, 235, 555, 160);
        panel_featureMethod.setLayout(null);
        panel_featureMethod.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Feature selection approaches"));
        tab_pane = new JTabbedPane();
        tab_pane.setBounds(20, 25, 520, 120);

        //--------- Filter Panel-----------------------
        panel_filter = new JPanel();
        panel_filter.setLayout(null);

        lbl_sup = new JLabel("Supervised:");
        lbl_sup.setBounds(10, 20, 120, 22);
        cb_supervised = new JComboBox();
        cb_supervised.setModel(new DefaultComboBoxModel(
                SupervisedFilterType.asList()));
        cb_supervised.setBounds(90, 20, 280, 22);

        lbl_unsup = new JLabel("Unsupervised:");
        lbl_unsup.setBounds(10, 55, 120, 22);
        cb_unsupervised = new JComboBox();
        cb_unsupervised.setModel(new DefaultComboBoxModel(
                UnsupervisedFilterType.asList()));
        cb_unsupervised.setBounds(90, 55, 280, 22);

        cb_supervised.addItemListener(eh);
        cb_unsupervised.addItemListener(eh);

        btn_moreOpFilter = new JButton("More option...");
        btn_moreOpFilter.setBounds(400, 35, 105, 23);
        btn_moreOpFilter.setEnabled(false);
        btn_moreOpFilter.addActionListener(eh);

        panel_filter.setLayout(null);
        panel_filter.add(lbl_sup);
        panel_filter.add(lbl_unsup);
        panel_filter.add(cb_supervised);
        panel_filter.add(cb_unsupervised);
        panel_filter.add(btn_moreOpFilter);

        //--------- Wrapper Panel-----------------------
        panel_wrapper = new JPanel();
        lbl_wrapper = new JLabel("Select method:");
        lbl_wrapper.setBounds(10, 35, 120, 22);

        cb_wrapper = new JComboBox();
        cb_wrapper.setModel(new DefaultComboBoxModel(
                WrapperType.asList()));
        cb_wrapper.setBounds(90, 35, 280, 22);
        cb_wrapper.addItemListener(eh);

        btn_moreOpWrapper = new JButton("More option...");
        btn_moreOpWrapper.setBounds(400, 35, 105, 23);
        btn_moreOpWrapper.setEnabled(false);
        btn_moreOpWrapper.addActionListener(eh);

        panel_wrapper.setLayout(null);
        panel_wrapper.add(cb_wrapper);
        panel_wrapper.add(lbl_wrapper);
        panel_wrapper.add(btn_moreOpWrapper);

        //--------- Embedded Panel---------------------
        panel_embedded = new JPanel();
        lbl_embedded = new JLabel("Select method:");
        lbl_embedded.setBounds(10, 35, 120, 22);

        cb_embedded = new JComboBox();
        cb_embedded.setModel(new DefaultComboBoxModel(
                EmbeddedType.asList()));
        cb_embedded.setBounds(90, 35, 280, 22);
        cb_embedded.addItemListener(eh);

        btn_moreOpEmbedded = new JButton("More option...");
        btn_moreOpEmbedded.setBounds(400, 35, 105, 23);
        btn_moreOpEmbedded.setEnabled(false);
        btn_moreOpEmbedded.addActionListener(eh);

        panel_embedded.setLayout(null);
        panel_embedded.add(cb_embedded);
        panel_embedded.add(lbl_embedded);
        panel_embedded.add(btn_moreOpEmbedded);

        //--------- Hybrid Panel-----------------------
        panel_hybrid = new JPanel();
        lbl_hybrid = new JLabel("Select method:");
        lbl_hybrid.setBounds(10, 35, 120, 22);

        cb_hybrid = new JComboBox();
        cb_hybrid.setModel(new DefaultComboBoxModel(
                HybridType.asList()));
        cb_hybrid.setBounds(90, 35, 280, 22);
        cb_hybrid.addItemListener(eh);

        btn_moreOpHybrid = new JButton("More option...");
        btn_moreOpHybrid.setBounds(400, 35, 105, 23);
        btn_moreOpHybrid.setEnabled(false);
        btn_moreOpHybrid.addActionListener(eh);

        panel_hybrid.setLayout(null);
        panel_hybrid.add(cb_hybrid);
        panel_hybrid.add(lbl_hybrid);
        panel_hybrid.add(btn_moreOpHybrid);

        //--------- Num Feature Selected Panel------------
        txtArea_feature = new JTextArea("5,10,15,20");
        txtArea_feature.setLineWrap(true);

        sc_feature = new JScrollPane();
        sc_feature.setBounds(15, 25, 225, 120);
        sc_feature.setViewportView(txtArea_feature);

        panel_numFeat = new JPanel();
        panel_numFeat.setBounds(580, 235, 255, 160);
        panel_numFeat.setLayout(null);
        panel_numFeat.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Numbers of selected features"));
        panel_numFeat.add(sc_feature);

        //--------- sets all panel in the Feature Panel----------
        tab_pane.add("Filter", panel_filter);
        tab_pane.add("Wrapper", panel_wrapper);
        tab_pane.add("Embedded", panel_embedded);
        //tab_pane.add("Hybrid", panel_hybrid);

        panel_featureMethod.add(tab_pane);

        ////////////////////// "Classifier" panel /////////////////////////////
        panel_classifier = new JPanel();
        panel_classifier.setBounds(10, 405, 408, 140);
        panel_classifier.setLayout(null);
        panel_classifier.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Classifier"));
        cb_classifier = new JComboBox();
        cb_classifier.setModel(new DefaultComboBoxModel(
                ClassifierType.asList()));
        cb_classifier.setBounds(110, 40, 250, 22);
        lbl_classifier = new JLabel("Select classifier:");
        lbl_classifier.setBounds(15, 40, 140, 22);

        btn_moreOpClassifier = new JButton("More option...");
        btn_moreOpClassifier.setBounds(140, 90, 110, 23);
        btn_moreOpClassifier.setEnabled(false);

        btn_moreOpClassifier.addActionListener(eh);
        cb_classifier.addItemListener(eh);

        panel_classifier.add(lbl_classifier);
        panel_classifier.add(cb_classifier);
        panel_classifier.add(btn_moreOpClassifier);

        ///////////////////// "Run configuration" panel ///////////////////////
        panel_config = new JPanel();
        panel_config.setBounds(428, 405, 408, 140);
        panel_config.setLayout(null);
        panel_config.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Run configuration"));
        cb_start = new JComboBox();
        cb_start.setModel(new DefaultComboBoxModel(new String[]{
            "none", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
        cb_start.setBounds(130, 40, 200, 22);
        cb_start.addItemListener(eh);
        lbl_start = new JLabel("Number of runs:");
        lbl_start.setBounds(15, 40, 140, 22);
        btn_start = new JButton("start");
        btn_start.addActionListener(eh);
        btn_exit = new JButton("Exit");
        btn_start.setBounds(110, 90, 80, 23);
        btn_exit.setBounds(210, 90, 80, 23);
        btn_exit.addActionListener(eh);

        panel_config.add(btn_start);
        panel_config.add(btn_exit);
        panel_config.add(cb_start);
        panel_config.add(lbl_start);

        //---------------------------------------------------------------
        add(panel_filePath);
        add(panel_featureMethod);
        add(panel_numFeat);
        add(panel_classifier);
        add(panel_config);
        this.setBackground(new Color(240, 240, 240));

        ////////////////////////////// menuBar  ///////////////////////////////
        menuBar = new JMenuBar();

        ///// file menu /////
        mi_preprocess = new JMenuItem("Preprocess");
        mi_preprocess.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        mi_preprocess.addActionListener(eh);
        mi_exit = new JMenuItem("Exit");
        mi_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        mi_exit.addActionListener(eh);
        file = new JMenu("File");
        file.setMnemonic('F');
        file.add(mi_preprocess);
        file.addSeparator();
        file.add(mi_exit);

        ///// diagram menu /////
        mi_exeTime = new JMenuItem("Execution time");
        mi_exeTime.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
        mi_exeTime.setEnabled(false);
        mi_exeTime.addActionListener(eh);
        mi_accur = new JMenuItem("Accuracy");
        mi_accur.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        mi_accur.setEnabled(false);
        mi_accur.addActionListener(eh);
        mi_error = new JMenuItem("Error rate");
        mi_error.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        mi_error.setEnabled(false);
        mi_error.addActionListener(eh);

        diagram = new JMenu("Diagram");
        diagram.setMnemonic('D');
        diagram.add(mi_exeTime);
        diagram.add(mi_accur);
        diagram.add(mi_error);

        ///// analyse menu /////
        mi_friedman = new JMenuItem("Friedman test");
        mi_friedman.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        mi_friedman.addActionListener(eh);
        analyse = new JMenu("Analyze");
        analyse.setMnemonic('A');
        analyse.add(mi_friedman);

        ///// help menu /////
        mi_help = new JMenuItem("Help contents");
        mi_help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.SHIFT_MASK));
        mi_help.addActionListener(eh);
        mi_about = new JMenuItem("About");
        mi_about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        mi_about.addActionListener(eh);
        help = new JMenu("Help");
        help.setMnemonic('H');
        help.add(mi_help);
        help.addSeparator();
        help.add(mi_about);

        menuBar.add(file);
        menuBar.add(diagram);
        menuBar.add(analyse);
        menuBar.add(help);

        ////////////////////////// progressBar  ///////////////////////////////
        progressBar = new JProgressBar();
        progressBar.setBounds(10, 560, 825, 20);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(51, 153, 255));
        progressBar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));

        add(progressBar);
    }

    /**
     * Recreates MainPanel based on the updated progress bar value.
     *
     * @param g the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        progressBar.setValue(progressValue);
    }

    /**
     * The event handler class for handling action events.
     */
    class EventHandler implements ActionListener, ItemListener {

        /**
         * The listener method for receiving action events. Invoked when an
         * action occurs.
         *
         * @param e an action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(mi_preprocess)) {
                mi_preprocessActionPerformed(e);
            } else if (e.getSource().equals(mi_exit)) {
                mi_exitActionPerformed(e);
            } else if (e.getSource().equals(mi_exeTime)) {
                mi_exeTimeActionPerformed(e);
            } else if (e.getSource().equals(mi_accur)) {
                mi_accurActionPerformed(e);
            } else if (e.getSource().equals(mi_error)) {
                mi_errorActionPerformed(e);
            } else if (e.getSource().equals(mi_friedman)) {
                mi_friedmanActionPerformed(e);
            } else if (e.getSource().equals(mi_help)) {
                mi_helpActionPerformed(e);
            } else if (e.getSource().equals(mi_about)) {
                mi_aboutActionPerformed(e);
            } else if (e.getSource().equals(btn_inputdst)) {
                btn_inputdstActionPerformed(e);
            } else if (e.getSource().equals(btn_classlbl)) {
                btn_classlblActionPerformed(e);
            } else if (e.getSource().equals(btn_trainSet)) {
                btn_trainSetActionPerformed(e);
            } else if (e.getSource().equals(btn_testSet)) {
                btn_testSetActionPerformed(e);
            } else if (e.getSource().equals(btn_classLabel)) {
                btn_classLabelActionPerformed(e);
            } else if (e.getSource().equals(btn_moreOpFilter)) {
                btn_moreOpFilterActionPerformed(e);
            } else if (e.getSource().equals(btn_moreOpWrapper)) {
                btn_moreOpWrapperActionPerformed(e);
            } else if (e.getSource().equals(btn_moreOpEmbedded)) {
                btn_moreOpEmbeddedActionPerformed(e);
            } else if (e.getSource().equals(btn_moreOpHybrid)) {
                btn_moreOpHybridActionPerformed(e);
            } else if (e.getSource().equals(btn_moreOpClassifier)) {
                btn_moreOpClassifierActionPerformed(e);
            } else if (e.getSource().equals(btn_start)) {
                btn_startActionPerformed(e);
            } else if (e.getSource().equals(btn_exit)) {
                btn_exitActionPerformed(e);
            }
        }

        /**
         * The listener method for receiving item events. Invoked when an item
         * has been selected or deselected by the user.
         *
         * @param e an action event
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource().equals(rd_randSet)) {
                rd_randSetItemStateChanged(e);
            } else if (e.getSource().equals(rd_ttset)) {
                rd_ttsetSetItemStateChanged(e);
            } else if (e.getSource().equals(cb_supervised)) {
                cb_supervisedItemStateChanged(e);
            } else if (e.getSource().equals(cb_unsupervised)) {
                cb_unsupervisedItemStateChanged(e);
            } else if (e.getSource().equals(cb_wrapper)) {
                cb_wrapperItemStateChanged(e);
            } else if (e.getSource().equals(cb_embedded)) {
                cb_embeddedItemStateChanged(e);
            } else if (e.getSource().equals(cb_hybrid)) {
                cb_hybridItemStateChanged(e);
            } else if (e.getSource().equals(cb_classifier)) {
                cb_classifierItemStateChanged(e);
            } else if (e.getSource().equals(cb_start)) {
                cb_startItemStateChanged(e);
            }
        }
    }

    /**
     * This method sets an action for the mi_preprocess menu item.
     *
     * @param e an action event
     */
    private void mi_preprocessActionPerformed(ActionEvent e) {
        PreprocessPanel processPanel = new PreprocessPanel();
    }

    /**
     * This method sets an action for the mi_exit menu item.
     *
     * @param e an action event
     */
    private void mi_exitActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    /**
     * This method sets an action for the mi_exeTime menu item.
     *
     * @param e an action event
     */
    private void mi_exeTimeActionPerformed(ActionEvent e) {
        SelectModePanel selectPanel = new SelectModePanel();
        JDialog selecdlg = new JDialog(selectPanel);
        selectPanel.setVisible(true);
        if (selectPanel.getNameMode() == SelectModeType.AVERAGE) {
            DiagramPanel digPanel = new DiagramPanel(finalResults.getPerformanceMeasures().getAverageTimeValues(), numSelectedSubsets,
                    "Average execution time diagram", cb_classifier.getSelectedItem().toString(), "          Execution Time (s)",
                    "average", PATH_PROJECT);
        } else if (selectPanel.getNameMode() == SelectModeType.TOTAL) {
            DiagramPanel digPanel = new DiagramPanel(finalResults.getPerformanceMeasures().getTimeValues(), numSelectedSubsets,
                    "Execution time diagram", cb_classifier.getSelectedItem().toString(), "          Execution Time (s)",
                    "iteration", PATH_PROJECT);
        }
    }

    /**
     * This method sets an action for the mi_accur menu item.
     *
     * @param e an action event
     */
    private void mi_accurActionPerformed(ActionEvent e) {
        SelectModePanel selectPanel = new SelectModePanel();
        JDialog selecdlg = new JDialog(selectPanel);
        selectPanel.setVisible(true);
        if (selectPanel.getNameMode() == SelectModeType.AVERAGE) {
            DiagramPanel digPanel = new DiagramPanel(finalResults.getPerformanceMeasures().getAverageAccuracyValues(), numSelectedSubsets,
                    "Average classification accuracy diagram", cb_classifier.getSelectedItem().toString(), "Classification Accuracy (%)",
                    "average", PATH_PROJECT);
        } else if (selectPanel.getNameMode() == SelectModeType.TOTAL) {
            DiagramPanel digPanel = new DiagramPanel(finalResults.getPerformanceMeasures().getAccuracyValues(), numSelectedSubsets,
                    "Classification accuracy diagram", cb_classifier.getSelectedItem().toString(), "Classification Accuracy (%)",
                    "iteration", PATH_PROJECT);
        }
    }

    /**
     * This method sets an action for the mi_error menu item.
     *
     * @param e an action event
     */
    private void mi_errorActionPerformed(ActionEvent e) {
        SelectModePanel selectPanel = new SelectModePanel();
        JDialog selecdlg = new JDialog(selectPanel);
        selectPanel.setVisible(true);
        if (selectPanel.getNameMode() == SelectModeType.AVERAGE) {
            DiagramPanel digPanel = new DiagramPanel(finalResults.getPerformanceMeasures().getAverageErrorRateValues(), numSelectedSubsets,
                    "Average classification error rate diagram", cb_classifier.getSelectedItem().toString(), "Classification Error Rate (%)",
                    "average", PATH_PROJECT);
        } else if (selectPanel.getNameMode() == SelectModeType.TOTAL) {
            DiagramPanel digPanel = new DiagramPanel(finalResults.getPerformanceMeasures().getErrorRateValues(), numSelectedSubsets,
                    "Classification error rate diagram", cb_classifier.getSelectedItem().toString(), "Classification Error Rate (%)",
                    "iteration", PATH_PROJECT);
        }
    }

    /**
     * This method sets an action for the mi_friedman menu item.
     *
     * @param e an action event
     */
    private void mi_friedmanActionPerformed(ActionEvent e) {
        FriedmanPanel friedtest_Panel = new FriedmanPanel();
    }

    /**
     * This method sets an action for the mi_help menu item.
     *
     * @param e an action event
     */
    private void mi_helpActionPerformed(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://unifeat.github.io/documentation.html"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method sets an action for the mi_about menu item.
     *
     * @param e an action event
     */
    private void mi_aboutActionPerformed(ActionEvent e) {
        AboutPanel aboutPan = new AboutPanel();
        Dialog aboutdlg = new Dialog(aboutPan);
        aboutPan.setVisible(true);
    }

    /**
     * This method sets an action for the btn_inputdst button.
     *
     * @param e an action event
     */
    private void btn_inputdstActionPerformed(ActionEvent e) {
        JFileChooser jfch = new JFileChooser();
        jfch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (jfch.showOpenDialog(btn_inputdst) == JFileChooser.APPROVE_OPTION) {
            txt_inputdst.setText(jfch.getSelectedFile().getPath());
        }
    }

    /**
     * This method sets an action for the btn_classlbl button.
     *
     * @param e an action event
     */
    private void btn_classlblActionPerformed(ActionEvent e) {
        JFileChooser jfch = new JFileChooser();
        jfch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (jfch.showOpenDialog(btn_classlbl) == JFileChooser.APPROVE_OPTION) {
            txt_classLbl.setText(jfch.getSelectedFile().getPath());
        }
    }

    /**
     * This method sets an action for the btn_trainSet button.
     *
     * @param e an action event
     */
    private void btn_trainSetActionPerformed(ActionEvent e) {
        JFileChooser jfch = new JFileChooser();
        jfch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (jfch.showOpenDialog(btn_trainSet) == JFileChooser.APPROVE_OPTION) {
            txt_trainSet.setText(jfch.getSelectedFile().getPath());
        }
    }

    /**
     * This method sets an action for the btn_testSet button.
     *
     * @param e an action event
     */
    private void btn_testSetActionPerformed(ActionEvent e) {
        JFileChooser jfch = new JFileChooser();
        jfch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (jfch.showOpenDialog(btn_testSet) == JFileChooser.APPROVE_OPTION) {
            txt_testSet.setText(jfch.getSelectedFile().getPath());
        }
    }

    /**
     * This method sets an action for the btn_classLabel button.
     *
     * @param e an action event
     */
    private void btn_classLabelActionPerformed(ActionEvent e) {
        JFileChooser jfch = new JFileChooser();
        jfch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (jfch.showOpenDialog(btn_classLabel) == JFileChooser.APPROVE_OPTION) {
            txt_classLabel.setText(jfch.getSelectedFile().getPath());
        }
    }

    /**
     * This method sets an action for the btn_moreOpFilter button.
     *
     * @param e an action event
     */
    private void btn_moreOpFilterActionPerformed(ActionEvent e) {
        FilterType suprvisedVersion = FilterType.parse(cb_supervised.getSelectedItem().toString());
        FilterType unsupervisedVersion = FilterType.parse(cb_unsupervised.getSelectedItem().toString());
        if (suprvisedVersion == FilterType.LAPLACIAN_SCORE) {
            LaplacianScorePanel lapScorePanel = new LaplacianScorePanel();
            Dialog lapScoreDlg = new Dialog(lapScorePanel);
            lapScorePanel.setUserValue(KNearest, constParam);
            lapScorePanel.setVisible(true);
            KNearest = lapScorePanel.getKNearest();
            constParam = lapScorePanel.getConstParam();
//            System.out.println("user value...   k-NN = " + KNearest
//                    + "   constParam = " + constParam);
        } else if (suprvisedVersion == FilterType.RRFS) {
            RRFSPanel rrfsPanel = new RRFSPanel();
            Dialog rrfsDlg = new Dialog(rrfsPanel);
            rrfsPanel.setUserValue(simValue);
            rrfsPanel.setVisible(true);
            simValue = rrfsPanel.getSimilarity();
//            System.out.println("user value:   simValue = " + simValue);
        } else if (unsupervisedVersion == FilterType.LAPLACIAN_SCORE) {
            LaplacianScorePanel lapScorePanel = new LaplacianScorePanel();
            Dialog lapScoreDlg = new Dialog(lapScorePanel);
            lapScorePanel.setUserValue(KNearest, constParam);
            lapScorePanel.setVisible(true);
            KNearest = lapScorePanel.getKNearest();
            constParam = lapScorePanel.getConstParam();
//            System.out.println("user value...   k-NN = " + KNearest
//                    + "   constParam = " + constParam);
        } else if (unsupervisedVersion == FilterType.RSM) {
            RSMPanel rsmPanel = new RSMPanel();
            Dialog rsmDlg = new Dialog(rsmPanel);
            rsmPanel.setUserValue(numSelection, sizeSubspace, elimination, multMethodName);
            rsmPanel.setVisible(true);
            numSelection = rsmPanel.getNumSelection();
            sizeSubspace = rsmPanel.getSizeSubspace();
            elimination = rsmPanel.getElimination();
            multMethodName = rsmPanel.getMultMethodName();
//            System.out.println("user values:   numSelection = " + numSelection
//                    + "   sizeSubspace = " + sizeSubspace
//                    + "   elimination = " + elimination
//                    + "    multMethodName = " + multMethodName);
        } else if (unsupervisedVersion == FilterType.RRFS) {
            RRFSPanel rrfsPanel = new RRFSPanel();
            Dialog rrfsDlg = new Dialog(rrfsPanel);
            rrfsPanel.setUserValue(simValue);
            rrfsPanel.setVisible(true);
            simValue = rrfsPanel.getSimilarity();
//            System.out.println("user value:   simValue = " + simValue);
        } else if (unsupervisedVersion == FilterType.UFSACO) {
            UFSACOPanel ufsacoPanel = new UFSACOPanel();
            Dialog ufsacoDlg = new Dialog(ufsacoPanel);
            ufsacoPanel.setUserValue(initPheromone, numIteration, numAnts, numFeatOfAnt, evRate, beta, q0);
            ufsacoPanel.setVisible(true);
            initPheromone = ufsacoPanel.getInitPheromone();
            numIteration = ufsacoPanel.getNumIteration();
            numAnts = ufsacoPanel.getNumAnts();
            numFeatOfAnt = ufsacoPanel.getNumFeatOfAnt();
            evRate = ufsacoPanel.getEvRate();
            beta = ufsacoPanel.getBeta();
            q0 = ufsacoPanel.getQ0();
//            System.out.println("user value:   initPheromone = " + initPheromone
//                    + "   numIteration = " + numIteration
//                    + "   numAnts = " + numAnts
//                    + "   numFeatOfAnt = " + numFeatOfAnt
//                    + "   evRate = " + evRate
//                    + "   beta = " + beta
//                    + "   q0 = " + q0);
        } else if (unsupervisedVersion == FilterType.RRFSACO_1) {
            RRFSACO_1Panel rrfsacoPanel = new RRFSACO_1Panel();
            Dialog rrfsacoDlg = new Dialog(rrfsacoPanel);
            rrfsacoPanel.setUserValue(numIteration, numAnts, numFeatOfAnt, evRate, beta, q0);
            rrfsacoPanel.setVisible(true);
            numIteration = rrfsacoPanel.getNumIteration();
            numAnts = rrfsacoPanel.getNumAnts();
            numFeatOfAnt = rrfsacoPanel.getNumFeatOfAnt();
            evRate = rrfsacoPanel.getEvRate();
            beta = rrfsacoPanel.getBeta();
            q0 = rrfsacoPanel.getQ0();
//            System.out.println("user value:   numIteration = " + numIteration
//                    + "   numAnts = " + numAnts
//                    + "   numFeature = " + numFeatOfAnt
//                    + "   evRate = " + evRate
//                    + "   beta = " + beta
//                    + "   q0 = " + q0);
        } else if (unsupervisedVersion == FilterType.RRFSACO_2) {
            RRFSACO_2Panel rrfsacoPanel = new RRFSACO_2Panel();
            Dialog rrfsacoDlg = new Dialog(rrfsacoPanel);
            rrfsacoPanel.setUserValue(initPheromone, numIteration, numAnts, numFeatOfAnt, evRate, alpha, beta, q0);
            rrfsacoPanel.setVisible(true);
            initPheromone = rrfsacoPanel.getInitPheromone();
            numIteration = rrfsacoPanel.getNumIteration();
            numAnts = rrfsacoPanel.getNumAnts();
            numFeatOfAnt = rrfsacoPanel.getNumFeatOfAnt();
            evRate = rrfsacoPanel.getEvRate();
            alpha = rrfsacoPanel.getAlpha();
            beta = rrfsacoPanel.getBeta();
            q0 = rrfsacoPanel.getQ0();
//            System.out.println("user value:   initPheromone = " + initPheromone
//                    + "   numIteration = " + numIteration
//                    + "   numAnts = " + numAnts
//                    + "   numFeature = " + numFeatOfAnt
//                    + "   evRate = " + evRate
//                    + "   alpha = " + alpha
//                    + "   beta = " + beta
//                    + "   q0 = " + q0);
        } else if (unsupervisedVersion == FilterType.IRRFSACO_1) {
            IRRFSACO_1Panel irrfsacoPanel = new IRRFSACO_1Panel();
            Dialog irrfsacoDlg = new Dialog(irrfsacoPanel);
            irrfsacoPanel.setUserValue(numIteration, numAnts, numFeatOfAnt, evRate, beta, q0);
            irrfsacoPanel.setVisible(true);
            numIteration = irrfsacoPanel.getNumIteration();
            numAnts = irrfsacoPanel.getNumAnts();
            numFeatOfAnt = irrfsacoPanel.getNumFeatOfAnt();
            evRate = irrfsacoPanel.getEvRate();
            beta = irrfsacoPanel.getBeta();
            q0 = irrfsacoPanel.getQ0();
//            System.out.println("user value:   numIteration = " + numIteration
//                    + "   numAnts = " + numAnts
//                    + "   numFeature = " + numFeatOfAnt
//                    + "   evRate = " + evRate
//                    + "   beta = " + beta
//                    + "   q0 = " + q0);
        } else if (unsupervisedVersion == FilterType.IRRFSACO_2) {
            IRRFSACO_2Panel irrfsacoPanel = new IRRFSACO_2Panel();
            Dialog irrfsacoDlg = new Dialog(irrfsacoPanel);
            irrfsacoPanel.setUserValue(initPheromone, numIteration, numAnts, numFeatOfAnt, evRate, alpha, beta, q0);
            irrfsacoPanel.setVisible(true);
            initPheromone = irrfsacoPanel.getInitPheromone();
            numIteration = irrfsacoPanel.getNumIteration();
            numAnts = irrfsacoPanel.getNumAnts();
            numFeatOfAnt = irrfsacoPanel.getNumFeatOfAnt();
            evRate = irrfsacoPanel.getEvRate();
            alpha = irrfsacoPanel.getAlpha();
            beta = irrfsacoPanel.getBeta();
            q0 = irrfsacoPanel.getQ0();
//            System.out.println("user value:   initPheromone = " + initPheromone
//                    + "   numIteration = " + numIteration
//                    + "   numAnts = " + numAnts
//                    + "   numFeature = " + numFeatOfAnt
//                    + "   evRate = " + evRate
//                    + "   alpha = " + alpha
//                    + "   beta = " + beta
//                    + "   q0 = " + q0);
        } else if (unsupervisedVersion == FilterType.MGSACO) {
            MGSACOPanel mgsacoPanel = new MGSACOPanel();
            Dialog mgsacoDlg = new Dialog(mgsacoPanel);
            mgsacoPanel.setUserValue(initPheromone, numIteration, numAnts, evRate, beta, q0);
            mgsacoPanel.setVisible(true);
            initPheromone = mgsacoPanel.getInitPheromone();
            numIteration = mgsacoPanel.getNumIteration();
            numAnts = mgsacoPanel.getNumAnts();
            evRate = mgsacoPanel.getEvRate();
            beta = mgsacoPanel.getBeta();
            q0 = mgsacoPanel.getQ0();
//            System.out.println("user value:   initPheromone = " + initPheromone
//                    + "   numIteration = " + numIteration
//                    + "   numAnts = " + numAnts
//                    + "   evRate = " + evRate
//                    + "   beta = " + beta
//                    + "   q0 = " + q0);
        }
    }

    /**
     * This method sets an action for the btn_moreOpWrapper button.
     *
     * @param e an action event
     */
    private void btn_moreOpWrapperActionPerformed(ActionEvent e) {
        WrapperType type = WrapperType.parse(cb_wrapper.getSelectedItem().toString());
        if (type == WrapperType.BPSO) {
            BPSOPanel bpsoPanel = new BPSOPanel();
            Dialog bpsoDlg = new Dialog(bpsoPanel);
            bpsoPanel.setUserValue(bpsoFeatSelectionPanel.getClassifierType(), bpsoFeatSelectionPanel.getSelectedClassifierPan(),
                    bpsoFeatSelectionPanel.getNumIteration(), bpsoFeatSelectionPanel.getPopulationSize(),
                    bpsoFeatSelectionPanel.getInertiaWeight(), bpsoFeatSelectionPanel.getC1(),
                    bpsoFeatSelectionPanel.getC2(), bpsoFeatSelectionPanel.getStartPosInterval(),
                    bpsoFeatSelectionPanel.getEndPosInterval(), bpsoFeatSelectionPanel.getMinVelocity(),
                    bpsoFeatSelectionPanel.getMaxVelocity(), bpsoFeatSelectionPanel.getKFolds());

            bpsoPanel.setVisible(true);
            bpsoFeatSelectionPanel = bpsoPanel;
//            System.out.println("classifier type = " + bpsoFeatSelectionPanel.getClassifierType().toString());
//            if (bpsoFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                SVMClassifierPanel pan = (SVMClassifierPanel) bpsoFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:   kernel = " + pan.getKernel().toString()
//                        + "   C = " + pan.getParameterC());
//            } else if (bpsoFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                DTClassifierPanel pan = (DTClassifierPanel) bpsoFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    min num = " + pan.getMinNum()
//                        + "  confidence = " + pan.getConfidence());
//            } else if (bpsoFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                KNNClassifierPanel pan = (KNNClassifierPanel) bpsoFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    KNN Value = " + pan.getKNNValue());
//            }
//            System.out.println("num iteration = " + bpsoFeatSelectionPanel.getNumIteration());
//            System.out.println("population size = " + bpsoFeatSelectionPanel.getPopulationSize());
//            System.out.println("inertia weight = " + bpsoFeatSelectionPanel.getInertiaWeight());
//            System.out.println("c1 = " + bpsoFeatSelectionPanel.getC1());
//            System.out.println("c2 = " + bpsoFeatSelectionPanel.getC2());
//            System.out.println("start position = " + bpsoFeatSelectionPanel.getStartPosInterval());
//            System.out.println("end position = " + bpsoFeatSelectionPanel.getEndPosInterval());
//            System.out.println("min velocity = " + bpsoFeatSelectionPanel.getMinVelocity());
//            System.out.println("max velocity = " + bpsoFeatSelectionPanel.getMaxVelocity());
//            System.out.println("K fold = " + bpsoFeatSelectionPanel.getKFolds());
        } else if (type == WrapperType.CPSO) {
            CPSOPanel cpsoPanel = new CPSOPanel();
            Dialog cpsoDlg = new Dialog(cpsoPanel);
            cpsoPanel.setUserValue(cpsoFeatSelectionPanel.getClassifierType(), cpsoFeatSelectionPanel.getSelectedClassifierPan(),
                    cpsoFeatSelectionPanel.getNumIteration(), cpsoFeatSelectionPanel.getPopulationSize(),
                    cpsoFeatSelectionPanel.getInertiaWeight(), cpsoFeatSelectionPanel.getC1(),
                    cpsoFeatSelectionPanel.getC2(), cpsoFeatSelectionPanel.getStartPosInterval(),
                    cpsoFeatSelectionPanel.getEndPosInterval(), cpsoFeatSelectionPanel.getMinVelocity(),
                    cpsoFeatSelectionPanel.getMaxVelocity(), cpsoFeatSelectionPanel.getKFolds(),
                    cpsoFeatSelectionPanel.getTheta());

            cpsoPanel.setVisible(true);
            cpsoFeatSelectionPanel = cpsoPanel;
//            System.out.println("classifier type = " + cpsoFeatSelectionPanel.getClassifierType().toString());
//            if (cpsoFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                SVMClassifierPanel pan = (SVMClassifierPanel) cpsoFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:   kernel = " + pan.getKernel().toString()
//                        + "   C = " + pan.getParameterC());
//            } else if (cpsoFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                DTClassifierPanel pan = (DTClassifierPanel) cpsoFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    min num = " + pan.getMinNum()
//                        + "  confidence = " + pan.getConfidence());
//            } else if (cpsoFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                KNNClassifierPanel pan = (KNNClassifierPanel) cpsoFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    KNN Value = " + pan.getKNNValue());
//            }
//            System.out.println("num iteration = " + cpsoFeatSelectionPanel.getNumIteration());
//            System.out.println("population size = " + cpsoFeatSelectionPanel.getPopulationSize());
//            System.out.println("inertia weight = " + cpsoFeatSelectionPanel.getInertiaWeight());
//            System.out.println("c1 = " + cpsoFeatSelectionPanel.getC1());
//            System.out.println("c2 = " + cpsoFeatSelectionPanel.getC2());
//            System.out.println("start position = " + cpsoFeatSelectionPanel.getStartPosInterval());
//            System.out.println("end position = " + cpsoFeatSelectionPanel.getEndPosInterval());
//            System.out.println("min velocity = " + cpsoFeatSelectionPanel.getMinVelocity());
//            System.out.println("max velocity = " + cpsoFeatSelectionPanel.getMaxVelocity());
//            System.out.println("K fold = " + cpsoFeatSelectionPanel.getKFolds());
//            System.out.println("theta = " + cpsoFeatSelectionPanel.getTheta());
        } else if (type == WrapperType.PSO42) {
            PSO42Panel pso42Panel = new PSO42Panel();
            Dialog pso42Dlg = new Dialog(pso42Panel);
            pso42Panel.setUserValue(pso42FeatSelectionPanel.getClassifierType(), pso42FeatSelectionPanel.getSelectedClassifierPan(),
                    pso42FeatSelectionPanel.getNumIteration(), pso42FeatSelectionPanel.getPopulationSize(),
                    pso42FeatSelectionPanel.getInertiaWeight(), pso42FeatSelectionPanel.getC1(),
                    pso42FeatSelectionPanel.getC2(), pso42FeatSelectionPanel.getStartPosInterval(),
                    pso42FeatSelectionPanel.getEndPosInterval(), pso42FeatSelectionPanel.getMinVelocity(),
                    pso42FeatSelectionPanel.getMaxVelocity(), pso42FeatSelectionPanel.getKFolds(),
                    pso42FeatSelectionPanel.getTheta());

            pso42Panel.setVisible(true);
            pso42FeatSelectionPanel = pso42Panel;
//            System.out.println("classifier type = " + pso42FeatSelectionPanel.getClassifierType().toString());
//            if (pso42FeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                SVMClassifierPanel pan = (SVMClassifierPanel) pso42FeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:   kernel = " + pan.getKernel().toString()
//                        + "   C = " + pan.getParameterC());
//            } else if (pso42FeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                DTClassifierPanel pan = (DTClassifierPanel) pso42FeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    min num = " + pan.getMinNum()
//                        + "  confidence = " + pan.getConfidence());
//            } else if (pso42FeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                KNNClassifierPanel pan = (KNNClassifierPanel) pso42FeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    KNN Value = " + pan.getKNNValue());
//            }
//            System.out.println("num iteration = " + pso42FeatSelectionPanel.getNumIteration());
//            System.out.println("population size = " + pso42FeatSelectionPanel.getPopulationSize());
//            System.out.println("inertia weight = " + pso42FeatSelectionPanel.getInertiaWeight());
//            System.out.println("c1 = " + pso42FeatSelectionPanel.getC1());
//            System.out.println("c2 = " + pso42FeatSelectionPanel.getC2());
//            System.out.println("start position = " + pso42FeatSelectionPanel.getStartPosInterval());
//            System.out.println("end position = " + pso42FeatSelectionPanel.getEndPosInterval());
//            System.out.println("min velocity = " + pso42FeatSelectionPanel.getMinVelocity());
//            System.out.println("max velocity = " + pso42FeatSelectionPanel.getMaxVelocity());
//            System.out.println("K fold = " + pso42FeatSelectionPanel.getKFolds());
//            System.out.println("theta = " + pso42FeatSelectionPanel.getTheta());
        } else if (type == WrapperType.HPSO_LS) {
            HPSO_LSPanel hpsolsPanel = new HPSO_LSPanel();
            Dialog hpsolsDlg = new Dialog(hpsolsPanel);
            hpsolsPanel.setUserValue(hpsolsFeatSelectionPanel.getClassifierType(), hpsolsFeatSelectionPanel.getSelectedClassifierPan(),
                    hpsolsFeatSelectionPanel.getNumIteration(), hpsolsFeatSelectionPanel.getPopulationSize(),
                    hpsolsFeatSelectionPanel.getInertiaWeight(), hpsolsFeatSelectionPanel.getC1(),
                    hpsolsFeatSelectionPanel.getC2(), hpsolsFeatSelectionPanel.getStartPosInterval(),
                    hpsolsFeatSelectionPanel.getEndPosInterval(), hpsolsFeatSelectionPanel.getMinVelocity(),
                    hpsolsFeatSelectionPanel.getMaxVelocity(), hpsolsFeatSelectionPanel.getKFolds(),
                    hpsolsFeatSelectionPanel.getEpsilon(), hpsolsFeatSelectionPanel.getAlpha());

            hpsolsPanel.setVisible(true);
            hpsolsFeatSelectionPanel = hpsolsPanel;
//            System.out.println("classifier type = " + hpsolsFeatSelectionPanel.getClassifierType().toString());
//            if (hpsolsFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                SVMClassifierPanel pan = (SVMClassifierPanel) hpsolsFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:   kernel = " + pan.getKernel().toString()
//                        + "   C = " + pan.getParameterC());
//            } else if (hpsolsFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                DTClassifierPanel pan = (DTClassifierPanel) hpsolsFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    min num = " + pan.getMinNum()
//                        + "  confidence = " + pan.getConfidence());
//            } else if (hpsolsFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                KNNClassifierPanel pan = (KNNClassifierPanel) hpsolsFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    KNN Value = " + pan.getKNNValue());
//            }
//            System.out.println("num iteration = " + hpsolsFeatSelectionPanel.getNumIteration());
//            System.out.println("population size = " + hpsolsFeatSelectionPanel.getPopulationSize());
//            System.out.println("inertia weight = " + hpsolsFeatSelectionPanel.getInertiaWeight());
//            System.out.println("c1 = " + hpsolsFeatSelectionPanel.getC1());
//            System.out.println("c2 = " + hpsolsFeatSelectionPanel.getC2());
//            System.out.println("start position = " + hpsolsFeatSelectionPanel.getStartPosInterval());
//            System.out.println("end position = " + hpsolsFeatSelectionPanel.getEndPosInterval());
//            System.out.println("min velocity = " + hpsolsFeatSelectionPanel.getMinVelocity());
//            System.out.println("max velocity = " + hpsolsFeatSelectionPanel.getMaxVelocity());
//            System.out.println("K fold = " + hpsolsFeatSelectionPanel.getKFolds());
//            System.out.println("epsilon = " + hpsolsFeatSelectionPanel.getEpsilon());
//            System.out.println("alpha = " + hpsolsFeatSelectionPanel.getAlpha());
        } else if (type == WrapperType.SIMPLE_GA) {
            SimpleGAPanel simpleGAPanel = new SimpleGAPanel();
            Dialog simpleGADlg = new Dialog(simpleGAPanel);
            simpleGAPanel.setUserValue(simpleGAFeatSelectionPanel.getClassifierType(), simpleGAFeatSelectionPanel.getSelectedClassifierPan(),
                    simpleGAFeatSelectionPanel.getSelectionType(), simpleGAFeatSelectionPanel.getCrossOverType(),
                    simpleGAFeatSelectionPanel.getMutationType(), simpleGAFeatSelectionPanel.getReplacementType(),
                    simpleGAFeatSelectionPanel.getNumIteration(), simpleGAFeatSelectionPanel.getPopulationSize(),
                    simpleGAFeatSelectionPanel.getCrossoverRate(), simpleGAFeatSelectionPanel.getMutationRate(),
                    simpleGAFeatSelectionPanel.getKFolds());

            simpleGAPanel.setVisible(true);
            simpleGAFeatSelectionPanel = simpleGAPanel;

//            System.out.println("classifier type = " + simpleGAFeatSelectionPanel.getClassifierType().toString());
//            if (simpleGAFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                SVMClassifierPanel pan = (SVMClassifierPanel) simpleGAFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:   kernel = " + pan.getKernel().toString()
//                        + "   C = " + pan.getParameterC());
//            } else if (simpleGAFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                DTClassifierPanel pan = (DTClassifierPanel) simpleGAFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    min num = " + pan.getMinNum()
//                        + "  confidence = " + pan.getConfidence());
//            } else if (simpleGAFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                KNNClassifierPanel pan = (KNNClassifierPanel) simpleGAFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    KNN Value = " + pan.getKNNValue());
//            }
//            System.out.println("selection type = " + simpleGAFeatSelectionPanel.getSelectionType().toString());
//            System.out.println("crossover type = " + simpleGAFeatSelectionPanel.getCrossOverType().toString());
//            System.out.println("mutation type = " + simpleGAFeatSelectionPanel.getMutationType().toString());
//            System.out.println("replacement type = " + simpleGAFeatSelectionPanel.getReplacementType().toString());
//            System.out.println("num iteration = " + simpleGAFeatSelectionPanel.getNumIteration());
//            System.out.println("population size = " + simpleGAFeatSelectionPanel.getPopulationSize());
//            System.out.println("crossover rate = " + simpleGAFeatSelectionPanel.getCrossoverRate());
//            System.out.println("mutation rate = " + simpleGAFeatSelectionPanel.getMutationRate());
//            System.out.println("K fold = " + simpleGAFeatSelectionPanel.getKFolds());
        } else if (type == WrapperType.HGAFS) {
            HGAFSPanel hgafsPanel = new HGAFSPanel();
            Dialog hgafsDlg = new Dialog(hgafsPanel);
            hgafsPanel.setUserValue(hgafsFeatSelectionPanel.getClassifierType(), hgafsFeatSelectionPanel.getSelectedClassifierPan(),
                    hgafsFeatSelectionPanel.getSelectionType(), hgafsFeatSelectionPanel.getCrossOverType(),
                    hgafsFeatSelectionPanel.getMutationType(), hgafsFeatSelectionPanel.getReplacementType(),
                    hgafsFeatSelectionPanel.getNumIteration(), hgafsFeatSelectionPanel.getPopulationSize(),
                    hgafsFeatSelectionPanel.getCrossoverRate(), hgafsFeatSelectionPanel.getMutationRate(),
                    hgafsFeatSelectionPanel.getKFolds(), hgafsFeatSelectionPanel.getEpsilon(),
                    hgafsFeatSelectionPanel.getMu());

            hgafsPanel.setVisible(true);
            hgafsFeatSelectionPanel = hgafsPanel;

//            System.out.println("classifier type = " + hgafsFeatSelectionPanel.getClassifierType().toString());
//            if (hgafsFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                SVMClassifierPanel pan = (SVMClassifierPanel) hgafsFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:   kernel = " + pan.getKernel().toString()
//                        + "   C = " + pan.getParameterC());
//            } else if (hgafsFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                DTClassifierPanel pan = (DTClassifierPanel) hgafsFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    min num = " + pan.getMinNum()
//                        + "  confidence = " + pan.getConfidence());
//            } else if (hgafsFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                KNNClassifierPanel pan = (KNNClassifierPanel) hgafsFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    KNN Value = " + pan.getKNNValue());
//            }
//            System.out.println("selection type = " + hgafsFeatSelectionPanel.getSelectionType().toString());
//            System.out.println("crossover type = " + hgafsFeatSelectionPanel.getCrossOverType().toString());
//            System.out.println("mutation type = " + hgafsFeatSelectionPanel.getMutationType().toString());
//            System.out.println("replacement type = " + hgafsFeatSelectionPanel.getReplacementType().toString());
//            System.out.println("num iteration = " + hgafsFeatSelectionPanel.getNumIteration());
//            System.out.println("population size = " + hgafsFeatSelectionPanel.getPopulationSize());
//            System.out.println("crossover rate = " + hgafsFeatSelectionPanel.getCrossoverRate());
//            System.out.println("mutation rate = " + hgafsFeatSelectionPanel.getMutationRate());
//            System.out.println("K fold = " + hgafsFeatSelectionPanel.getKFolds());
//            System.out.println("epsilon = " + hgafsFeatSelectionPanel.getEpsilon());
//            System.out.println("mu = " + hgafsFeatSelectionPanel.getMu());
        } else if (type == WrapperType.OPTIMAL_ACO) {
            OptimalACOPanel optimalacoPanel = new OptimalACOPanel();
            Dialog optimalacoDlg = new Dialog(optimalacoPanel);
            optimalacoPanel.setUserValue(optimalACOFeatSelectionPanel.getClassifierType(), optimalACOFeatSelectionPanel.getSelectedClassifierPan(),
                    optimalACOFeatSelectionPanel.getNumIteration(), optimalACOFeatSelectionPanel.getColonySize(),
                    optimalACOFeatSelectionPanel.getAlpha(), optimalACOFeatSelectionPanel.getBeta(),
                    optimalACOFeatSelectionPanel.getEvRate(), optimalACOFeatSelectionPanel.getKFolds(),
                    optimalACOFeatSelectionPanel.getInitPheromone(), optimalACOFeatSelectionPanel.getPhi());

            optimalacoPanel.setVisible(true);
            optimalACOFeatSelectionPanel = optimalacoPanel;

//            System.out.println("classifier type = " + optimalACOFeatSelectionPanel.getClassifierType().toString());
//            if (optimalACOFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                SVMClassifierPanel pan = (SVMClassifierPanel) optimalACOFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:   kernel = " + pan.getKernel().toString()
//                        + "   C = " + pan.getParameterC());
//            } else if (optimalACOFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                DTClassifierPanel pan = (DTClassifierPanel) optimalACOFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    min num = " + pan.getMinNum()
//                        + "  confidence = " + pan.getConfidence());
//            } else if (optimalACOFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                KNNClassifierPanel pan = (KNNClassifierPanel) optimalACOFeatSelectionPanel.getSelectedClassifierPan();
//                System.out.println("user:    KNN Value = " + pan.getKNNValue());
//            }
//
//            System.out.println("num iteration = " + optimalACOFeatSelectionPanel.getNumIteration());
//            System.out.println("colony size = " + optimalACOFeatSelectionPanel.getColonySize());
//            System.out.println("alpha = " + optimalACOFeatSelectionPanel.getAlpha());
//            System.out.println("beta = " + optimalACOFeatSelectionPanel.getBeta());
//            System.out.println("evRate = " + optimalACOFeatSelectionPanel.getEvRate());
//            System.out.println("K fold = " + optimalACOFeatSelectionPanel.getKFolds());
//            System.out.println("init pheromone = " + optimalACOFeatSelectionPanel.getInitPheromone());
//            System.out.println("phi = " + optimalACOFeatSelectionPanel.getPhi());
        }
    }

    /**
     * This method sets an action for the btn_moreOpEmbedded button.
     *
     * @param e an action event
     */
    private void btn_moreOpEmbeddedActionPerformed(ActionEvent e) {
        EmbeddedType type = EmbeddedType.parse(cb_embedded.getSelectedItem().toString());
        if (type == EmbeddedType.DECISION_TREE_BASED) {
            DecisionTreeBasedPanel dtBasedPanel = new DecisionTreeBasedPanel();
            Dialog dtBasedDlg = new Dialog(dtBasedPanel);
            if (decisionTreeBasedMethodType == TreeType.C45) {
                dtBasedPanel.setUserValue(confidence, minNum);
            } else if (decisionTreeBasedMethodType == TreeType.RANDOM_TREE) {
                dtBasedPanel.setUserValue(randomTreeKValue, randomTreeMaxDepth, randomTreeMinNum, randomTreeMinVarianceProp);
            }
            dtBasedPanel.removeTreeType(TreeType.RANDOM_FOREST);
            dtBasedPanel.setVisible(true);
            decisionTreeBasedMethodType = dtBasedPanel.getTreeType();
            if (decisionTreeBasedMethodType == TreeType.C45) {
                confidence = dtBasedPanel.getConfidence();
                minNum = dtBasedPanel.getMinNum();
//                System.out.println("user value...   " + decisionTreeBasedMethodType.toString()
//                        + "   confidence = " + confidence
//                        + "   minNum = " + minNum);
            } else if (decisionTreeBasedMethodType == TreeType.RANDOM_TREE) {
                randomTreeKValue = dtBasedPanel.getRandomTreeKValue();
                randomTreeMaxDepth = dtBasedPanel.getRandomTreeMaxDepth();
                randomTreeMinNum = dtBasedPanel.getRandomTreeMinNum();
                randomTreeMinVarianceProp = dtBasedPanel.getRandomTreeMinVarianceProp();
//                System.out.println("user value...   " + decisionTreeBasedMethodType.toString()
//                        + "   randomTreeKValue = " + randomTreeKValue
//                        + "   randomTreeMaxDepth = " + randomTreeMaxDepth
//                        + "   randomTreeMinNum = " + randomTreeMinNum
//                        + "   randomTreeMinVarianceProp = " + randomTreeMinVarianceProp);
            }
        } else if (type == EmbeddedType.RANDOM_FOREST_METHOD) {
            DecisionTreeBasedPanel dtBasedPanel = new DecisionTreeBasedPanel();
            Dialog dtBasedDlg = new Dialog(dtBasedPanel);
            dtBasedPanel.setUserValue(randomForestNumFeatures, maxDepth, randomForestNumIterations);
            dtBasedPanel.removeTreeType(TreeType.C45, TreeType.RANDOM_TREE);
            dtBasedPanel.setMethodTitle("Random forest settings:");
            dtBasedPanel.setMethodDescription("<html> Random forest uses the importance of features for feature selection. </html>");
            dtBasedPanel.setVisible(true);
            decisionTreeBasedMethodType = dtBasedPanel.getTreeType();
            randomForestNumFeatures = dtBasedPanel.getRandomForestNumFeatures();
            maxDepth = dtBasedPanel.getMaxDepth();
            randomForestNumIterations = dtBasedPanel.getRandomForestNumIterations();
//            System.out.println("user value...   " + decisionTreeBasedMethodType.toString()
//                    + "   randomForestNumFeatures = " + randomForestNumFeatures
//                    + "   maxDepth = " + maxDepth
//                    + "   randomforestNumIterations = " + randomForestNumIterations);
        } else if (type == EmbeddedType.SVM_RFE) {
            SVMClassifierPanel svmPanel = new SVMClassifierPanel();
            Dialog svmDlg = new Dialog(svmPanel);
            svmPanel.setUserValue(svmFeatureSelectionPanel.getKernel(), svmFeatureSelectionPanel.getParameterC());
            svmPanel.setEnableKernelType(false);
            svmPanel.setMethodTitle("SVM_RFE settings:");
            svmPanel.setMethodDescription("<html>Support vector machine method based on recursive feature elimination (SVM_RFE).</html>");
            svmPanel.setVisible(true);
            svmFeatureSelectionPanel = svmPanel;
//            System.out.println("kernel = " + svmFeatureSelectionPanel.getKernel().toString()
//                    + "   C = " + svmFeatureSelectionPanel.getParameterC());
        } else if (type == EmbeddedType.MSVM_RFE) {
            MSVM_RFEPanel msvmPanel = new MSVM_RFEPanel();
            Dialog msvmDlg = new Dialog(msvmPanel);
            msvmPanel.setUserValue(msvmFeatureSelectionPanel.getKernel(), msvmFeatureSelectionPanel.getParameterC(),
                    msvmFeatureSelectionPanel.getNumFold(), msvmFeatureSelectionPanel.getNumRun());
            msvmPanel.setEnableKernelType(false);
            msvmPanel.setVisible(true);
            msvmFeatureSelectionPanel = msvmPanel;
//            System.out.println("kernel = " + msvmFeatureSelectionPanel.getKernel().toString()
//                    + "   C = " + msvmFeatureSelectionPanel.getParameterC()
//                    + "   Fold = " + msvmFeatureSelectionPanel.getNumFold()
//                    + "   numRun = " + msvmFeatureSelectionPanel.getNumRun());
        } else if (type == EmbeddedType.OVO_SVM_RFE) {
            SVMClassifierPanel svmPanel = new SVMClassifierPanel();
            Dialog svmDlg = new Dialog(svmPanel);
            svmPanel.setUserValue(svmFeatureSelectionPanel.getKernel(), svmFeatureSelectionPanel.getParameterC());
            svmPanel.setEnableKernelType(false);
            svmPanel.setMethodTitle("OVO_SVM_RFE settings:");
            svmPanel.setMethodDescription("<html>OVO_SVM_RFE method is used for multiclass classification problem in which one-versus-one (OVO) strategy is applied to construct binary classifiers. The feature selection process is based on SVM_RFE method.</html>");
            svmPanel.setMethodDescriptionPosition(new Rectangle(10, 35, 400, 80));
            svmPanel.setVisible(true);
            svmFeatureSelectionPanel = svmPanel;
//            System.out.println("kernel = " + svmFeatureSelectionPanel.getKernel().toString()
//                    + "   C = " + svmFeatureSelectionPanel.getParameterC());
        } else if (type == EmbeddedType.OVA_SVM_RFE) {
            SVMClassifierPanel svmPanel = new SVMClassifierPanel();
            Dialog svmDlg = new Dialog(svmPanel);
            svmPanel.setUserValue(svmFeatureSelectionPanel.getKernel(), svmFeatureSelectionPanel.getParameterC());
            svmPanel.setEnableKernelType(false);
            svmPanel.setMethodTitle("OVA_SVM_RFE settings:");
            svmPanel.setMethodDescription("<html>OVA_SVM_RFE method is used for multiclass classification problem in which one-versus-all (OVA) strategy is applied to construct binary classifiers. The feature selection process is based on SVM_RFE method.</html>");
            svmPanel.setMethodDescriptionPosition(new Rectangle(10, 35, 400, 80));
            svmPanel.setVisible(true);
            svmFeatureSelectionPanel = svmPanel;
//            System.out.println("kernel = " + svmFeatureSelectionPanel.getKernel().toString()
//                    + "   C = " + svmFeatureSelectionPanel.getParameterC());
        }
    }

    /**
     * This method sets an action for the btn_moreOpHybrid button.
     *
     * @param e an action event
     */
    private void btn_moreOpHybridActionPerformed(ActionEvent e) {
        System.out.println("More option Hybrid");
    }

    /**
     * This method sets an action for the btn_moreOpClassifier button.
     *
     * @param e an action event
     */
    private void btn_moreOpClassifierActionPerformed(ActionEvent e) {
        ClassifierType classifierType = ClassifierType.parse(cb_classifier.getSelectedItem().toString());
        if (classifierType == ClassifierType.SVM) {
            SVMClassifierPanel currentPanel = (SVMClassifierPanel) selectedEvaluationClassifierPanel;
            svmEvaluationPanel = new SVMClassifierPanel();
            Dialog svmDlg = new Dialog(svmEvaluationPanel);
            svmEvaluationPanel.setUserValue(currentPanel.getKernel(), currentPanel.getParameterC());
            svmEvaluationPanel.setVisible(true);
            //typeKernel = svmEvaluationPanel.getKernel();
            selectedEvaluationClassifierPanel = svmEvaluationPanel;
            //System.out.println("kernel = " + typeKernel);
//            System.out.println("kernel = " + svmEvaluationPanel.getKernel().toString()
//                    + "   C = " + svmEvaluationPanel.getParameterC() + "\n"
//                    + "kernel = " + ((SVMClassifierPanel) selectedEvaluationClassifierPanel).getKernel().toString()
//                    + "   C = " + ((SVMClassifierPanel) selectedEvaluationClassifierPanel).getParameterC());
        } else if (classifierType == ClassifierType.DT) {
            DTClassifierPanel currentPanel = (DTClassifierPanel) selectedEvaluationClassifierPanel;
            dtEvaluationPanel = new DTClassifierPanel();
            Dialog dtDlg = new Dialog(dtEvaluationPanel);
            dtEvaluationPanel.setUserValue(currentPanel.getConfidence(), currentPanel.getMinNum());
            dtEvaluationPanel.setVisible(true);
            //confidence = dtEvaluationPanel.getConfidence();
            //minNum = dtEvaluationPanel.getMinNum();
            selectedEvaluationClassifierPanel = dtEvaluationPanel;
            //System.out.println("min num = " + minNum + "  confidence = " + confidence);
//            System.out.println("min num = " + dtEvaluationPanel.getMinNum() + "  confidence = " + dtEvaluationPanel.getConfidence() + "\n"
//                    + "min num = " + ((DTClassifierPanel) selectedEvaluationClassifierPanel).getMinNum() + "  confidence = " + ((DTClassifierPanel) selectedEvaluationClassifierPanel).getConfidence());
        } else if (classifierType == ClassifierType.KNN) {
            KNNClassifierPanel currentPanel = (KNNClassifierPanel) selectedEvaluationClassifierPanel;
            knnEvaluationPanel = new KNNClassifierPanel();
            Dialog dtDlg = new Dialog(knnEvaluationPanel);
            knnEvaluationPanel.setUserValue(currentPanel.getKNNValue());
            knnEvaluationPanel.setVisible(true);
            //confidence = knnEvaluationPanel.getKNNValue();
            selectedEvaluationClassifierPanel = knnEvaluationPanel;
            //System.out.println("KNN Value = " + minNum);
//            System.out.println("KNN Value = " + knnEvaluationPanel.getKNNValue() + "\n"
//                    + "KNN Value = " + ((KNNClassifierPanel) selectedEvaluationClassifierPanel).getKNNValue());
        }
    }

    /**
     * This method sets an action for the btn_start button.
     *
     * @param e an action event
     */
    private void btn_startActionPerformed(ActionEvent e) {
        //gets the number of selected features
        if (!txtArea_feature.getText().equals("")) {
            String[] numCases = txtArea_feature.getText().split(",");
            numSelectedSubsets = new int[numCases.length];
            for (int i = 0; i < numCases.length; i++) {
                numSelectedSubsets[i] = Integer.parseInt(numCases[i]);
            }
        }

        //reads the data information
        data = new DatasetInfo();
        if (isCorrectDataset()) {
            if (rd_randSet.isSelected()) {
                data.preProcessing(txt_inputdst.getText(), txt_classLbl.getText());
            } else {
                data.preProcessing(txt_trainSet.getText(), txt_testSet.getText(), txt_classLabel.getText());
            }
        }

        if (printErrorMessages()) {
            //delete old CSV and ARFF files
            FileFunc.deleteFilesInDirectory(PATH_DATA_CSV);
            FileFunc.deleteFilesInDirectory(PATH_DATA_ARFF);

            //runs the progress bar
            Counter c = new Counter();
            Thread t = new Thread(c);
            t.start();
            repaint();
            runCode = 0;
        }
    }

    /**
     * This method sets an action for the btn_exit button.
     *
     * @param e an action event
     */
    private void btn_exitActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    /**
     * This method sets an action for the rd_randSet radio button.
     *
     * @param e an action event
     */
    private void rd_randSetItemStateChanged(ItemEvent e) {
        txt_trainSet.setEnabled(false);
        txt_trainSet.setText("");
        txt_trainSet.setBackground(new Color(240, 240, 240));
        txt_testSet.setEnabled(false);
        txt_testSet.setText("");
        txt_testSet.setBackground(new Color(240, 240, 240));
        txt_classLabel.setEnabled(false);
        txt_classLabel.setText("");
        txt_classLabel.setBackground(new Color(240, 240, 240));
        btn_trainSet.setEnabled(false);
        btn_testSet.setEnabled(false);
        btn_classLabel.setEnabled(false);

        txt_inputdst.setEnabled(true);
        txt_inputdst.setBackground(Color.WHITE);
        txt_classLbl.setEnabled(true);
        txt_classLbl.setBackground(Color.WHITE);
        btn_classlbl.setEnabled(true);
        btn_inputdst.setEnabled(true);
    }

    /**
     * This method sets an action for the rd_ttsetSet radio button.
     *
     * @param e an action event
     */
    private void rd_ttsetSetItemStateChanged(ItemEvent e) {
        txt_inputdst.setEnabled(false);
        txt_inputdst.setText("");
        txt_inputdst.setBackground(new Color(240, 240, 240));
        txt_classLbl.setEnabled(false);
        txt_classLbl.setText("");
        txt_classLbl.setBackground(new Color(240, 240, 240));
        btn_inputdst.setEnabled(false);
        btn_classlbl.setEnabled(false);

        txt_classLabel.setEnabled(true);
        txt_classLabel.setBackground(Color.WHITE);
        txt_testSet.setEnabled(true);
        txt_testSet.setBackground(Color.WHITE);
        txt_trainSet.setEnabled(true);
        txt_trainSet.setBackground(Color.WHITE);
        btn_classLabel.setEnabled(true);
        btn_testSet.setEnabled(true);
        btn_trainSet.setEnabled(true);
    }

    /**
     * This method sets an action for the cb_supervised combo box.
     *
     * @param e an action event
     */
    private void cb_supervisedItemStateChanged(ItemEvent e) {
        FilterType filterType = FilterType.parse(cb_supervised.getSelectedItem().toString());
        if (filterType != FilterType.NONE) {
            cb_unsupervised.setSelectedItem(FilterType.NONE.toString());
            cb_wrapper.setSelectedItem(WrapperType.NONE.toString());
            cb_embedded.setSelectedItem(EmbeddedType.NONE.toString());
            cb_hybrid.setSelectedItem(HybridType.NONE.toString());
            if (filterType == FilterType.LAPLACIAN_SCORE) {
                LaplacianScorePanel lapScorePanel = new LaplacianScorePanel();
                lapScorePanel.setDefaultValue();
                KNearest = lapScorePanel.getKNearest();
                constParam = lapScorePanel.getConstParam();
                btn_moreOpFilter.setEnabled(true);
//                System.out.println("default:   k-NN = " + KNearest
//                        + "   constParam = " + constParam);
            } else if (filterType == FilterType.RRFS) {
                RRFSPanel rrfsPanel = new RRFSPanel();
                rrfsPanel.setDefaultValue();
                simValue = rrfsPanel.getSimilarity();
                btn_moreOpFilter.setEnabled(true);
//                System.out.println("default:   simValue = " + simValue);
            } else {
                btn_moreOpFilter.setEnabled(false);
            }
        } else {
            btn_moreOpFilter.setEnabled(false);
        }
    }

    /**
     * This method sets an action for the cb_unsupervised combo box.
     *
     * @param e an action event
     */
    private void cb_unsupervisedItemStateChanged(ItemEvent e) {
        FilterType filterType = FilterType.parse(cb_unsupervised.getSelectedItem().toString());
        if (filterType != FilterType.NONE) {
            cb_supervised.setSelectedItem(FilterType.NONE.toString());
            cb_wrapper.setSelectedItem(WrapperType.NONE.toString());
            cb_embedded.setSelectedItem(EmbeddedType.NONE.toString());
            cb_hybrid.setSelectedItem(HybridType.NONE.toString());
            if (filterType == FilterType.LAPLACIAN_SCORE) {
                LaplacianScorePanel lapScorePanel = new LaplacianScorePanel();
                lapScorePanel.setDefaultValue();
                KNearest = lapScorePanel.getKNearest();
                constParam = lapScorePanel.getConstParam();
                btn_moreOpFilter.setEnabled(true);
//                System.out.println("default:   k-NN = " + KNearest
//                        + "   constParam = " + constParam);
            } else if (filterType == FilterType.RSM) {
                RSMPanel rsmPanel = new RSMPanel();
                rsmPanel.setDefaultValue();
                numSelection = rsmPanel.getNumSelection();
                sizeSubspace = rsmPanel.getSizeSubspace();
                elimination = rsmPanel.getElimination();
                multMethodName = rsmPanel.getMultMethodName();
                btn_moreOpFilter.setEnabled(true);
//                System.out.println("default:   numSelection = " + numSelection
//                        + "   sizeSubspace = " + sizeSubspace
//                        + "   elimination = " + elimination
//                        + "    multMethodName = " + multMethodName);
            } else if (filterType == FilterType.RRFS) {
                RRFSPanel rrfsPanel = new RRFSPanel();
                rrfsPanel.setDefaultValue();
                simValue = rrfsPanel.getSimilarity();
                btn_moreOpFilter.setEnabled(true);
//                System.out.println("default:   simValue = " + simValue);
            } else if (filterType == FilterType.UFSACO) {
                UFSACOPanel ufsacoPanel = new UFSACOPanel();
                ufsacoPanel.setDefaultValue();
                initPheromone = ufsacoPanel.getInitPheromone();
                numIteration = ufsacoPanel.getNumIteration();
                numAnts = ufsacoPanel.getNumAnts();
                numFeatOfAnt = ufsacoPanel.getNumFeatOfAnt();
                evRate = ufsacoPanel.getEvRate();
                beta = ufsacoPanel.getBeta();
                q0 = ufsacoPanel.getQ0();
                btn_moreOpFilter.setEnabled(true);
//                System.out.println("default:   initPheromone = " + initPheromone
//                        + "   numIteration = " + numIteration
//                        + "   numAnts = " + numAnts
//                        + "   numFeature = " + numFeatOfAnt
//                        + "   evRate = " + evRate
//                        + "   beta = " + beta
//                        + "   q0 = " + q0);
            } else if (filterType == FilterType.RRFSACO_1) {
                RRFSACO_1Panel rrfsacoPanel = new RRFSACO_1Panel();
                rrfsacoPanel.setDefaultValue();
                numIteration = rrfsacoPanel.getNumIteration();
                numAnts = rrfsacoPanel.getNumAnts();
                numFeatOfAnt = rrfsacoPanel.getNumFeatOfAnt();
                evRate = rrfsacoPanel.getEvRate();
                beta = rrfsacoPanel.getBeta();
                q0 = rrfsacoPanel.getQ0();
                btn_moreOpFilter.setEnabled(true);
//                System.out.println("default:   numIteration = " + numIteration
//                        + "   numAnts = " + numAnts
//                        + "   numFeature = " + numFeatOfAnt
//                        + "   evRate = " + evRate
//                        + "   beta = " + beta
//                        + "   q0 = " + q0);
            } else if (filterType == FilterType.RRFSACO_2) {
                RRFSACO_2Panel rrfsacoPanel = new RRFSACO_2Panel();
                rrfsacoPanel.setDefaultValue();
                initPheromone = rrfsacoPanel.getInitPheromone();
                numIteration = rrfsacoPanel.getNumIteration();
                numAnts = rrfsacoPanel.getNumAnts();
                numFeatOfAnt = rrfsacoPanel.getNumFeatOfAnt();
                evRate = rrfsacoPanel.getEvRate();
                alpha = rrfsacoPanel.getAlpha();
                beta = rrfsacoPanel.getBeta();
                q0 = rrfsacoPanel.getQ0();
                btn_moreOpFilter.setEnabled(true);
//                System.out.println("default:   initPheromone = " + initPheromone
//                        + "   numIteration = " + numIteration
//                        + "   numAnts = " + numAnts
//                        + "   numFeature = " + numFeatOfAnt
//                        + "   evRate = " + evRate
//                        + "   alpha = " + alpha
//                        + "   beta = " + beta
//                        + "   q0 = " + q0);
            } else if (filterType == FilterType.IRRFSACO_1) {
                IRRFSACO_1Panel irrfsacoPanel = new IRRFSACO_1Panel();
                irrfsacoPanel.setDefaultValue();
                numIteration = irrfsacoPanel.getNumIteration();
                numAnts = irrfsacoPanel.getNumAnts();
                numFeatOfAnt = irrfsacoPanel.getNumFeatOfAnt();
                evRate = irrfsacoPanel.getEvRate();
                beta = irrfsacoPanel.getBeta();
                q0 = irrfsacoPanel.getQ0();
                btn_moreOpFilter.setEnabled(true);
//                System.out.println("default:   numIteration = " + numIteration
//                        + "   numAnts = " + numAnts
//                        + "   numFeature = " + numFeatOfAnt
//                        + "   evRate = " + evRate
//                        + "   beta = " + beta
//                        + "   q0 = " + q0);
            } else if (filterType == FilterType.IRRFSACO_2) {
                IRRFSACO_2Panel irrfsacoPanel = new IRRFSACO_2Panel();
                irrfsacoPanel.setDefaultValue();
                initPheromone = irrfsacoPanel.getInitPheromone();
                numIteration = irrfsacoPanel.getNumIteration();
                numAnts = irrfsacoPanel.getNumAnts();
                numFeatOfAnt = irrfsacoPanel.getNumFeatOfAnt();
                evRate = irrfsacoPanel.getEvRate();
                alpha = irrfsacoPanel.getAlpha();
                beta = irrfsacoPanel.getBeta();
                q0 = irrfsacoPanel.getQ0();
                btn_moreOpFilter.setEnabled(true);
//                System.out.println("default:   initPheromone = " + initPheromone
//                        + "   numIteration = " + numIteration
//                        + "   numAnts = " + numAnts
//                        + "   numFeature = " + numFeatOfAnt
//                        + "   evRate = " + evRate
//                        + "   alpha = " + alpha
//                        + "   beta = " + beta
//                        + "   q0 = " + q0);
            } else if (filterType == FilterType.MGSACO) {
                MGSACOPanel mgsacoPanel = new MGSACOPanel();
                mgsacoPanel.setDefaultValue();
                initPheromone = mgsacoPanel.getInitPheromone();
                numIteration = mgsacoPanel.getNumIteration();
                numAnts = mgsacoPanel.getNumAnts();
                evRate = mgsacoPanel.getEvRate();
                beta = mgsacoPanel.getBeta();
                q0 = mgsacoPanel.getQ0();
                btn_moreOpFilter.setEnabled(true);
//                System.out.println("default:   initPheromone = " + initPheromone
//                        + "   numIteration = " + numIteration
//                        + "   numAnts = " + numAnts
//                        + "   evRate = " + evRate
//                        + "   beta = " + beta
//                        + "   q0 = " + q0);
            } else {
                btn_moreOpFilter.setEnabled(false);
            }
        } else {
            btn_moreOpFilter.setEnabled(false);
        }
    }

    /**
     * This method sets an action for the cb_wrapper combo box.
     *
     * @param e an action event
     */
    private void cb_wrapperItemStateChanged(ItemEvent e) {
        WrapperType wrapperType = WrapperType.parse(cb_wrapper.getSelectedItem().toString());
        if (wrapperType != WrapperType.NONE) {
            cb_supervised.setSelectedItem(FilterType.NONE.toString());
            cb_unsupervised.setSelectedItem(FilterType.NONE.toString());
            cb_embedded.setSelectedItem(EmbeddedType.NONE.toString());
            cb_hybrid.setSelectedItem(HybridType.NONE.toString());
            if (wrapperType == WrapperType.BPSO) {
                BPSOPanel bpsoPanel = new BPSOPanel();
                bpsoPanel.setDefaultValue();
                bpsoFeatSelectionPanel = bpsoPanel;
                btn_moreOpWrapper.setEnabled(true);
//                System.out.println("classifier type = " + bpsoFeatSelectionPanel.getClassifierType().toString());
//                if (bpsoFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                    SVMClassifierPanel pan = (SVMClassifierPanel) bpsoFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:   kernel = " + pan.getKernel().toString()
//                            + "   C = " + pan.getParameterC());
//                } else if (bpsoFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                    DTClassifierPanel pan = (DTClassifierPanel) bpsoFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    min num = " + pan.getMinNum()
//                            + "  confidence = " + pan.getConfidence());
//                } else if (bpsoFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                    KNNClassifierPanel pan = (KNNClassifierPanel) bpsoFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    KNN Value = " + pan.getKNNValue());
//                }
//                System.out.println("default:    num iteration = " + bpsoFeatSelectionPanel.getNumIteration());
//                System.out.println("population size = " + bpsoFeatSelectionPanel.getPopulationSize());
//                System.out.println("inertia weight = " + bpsoFeatSelectionPanel.getInertiaWeight());
//                System.out.println("c1 = " + bpsoFeatSelectionPanel.getC1());
//                System.out.println("c2 = " + bpsoFeatSelectionPanel.getC2());
//                System.out.println("start position = " + bpsoFeatSelectionPanel.getStartPosInterval());
//                System.out.println("end position = " + bpsoFeatSelectionPanel.getEndPosInterval());
//                System.out.println("min velocity = " + bpsoFeatSelectionPanel.getMinVelocity());
//                System.out.println("max velocity = " + bpsoFeatSelectionPanel.getMaxVelocity());
//                System.out.println("K fold = " + bpsoFeatSelectionPanel.getKFolds());
            } else if (wrapperType == WrapperType.CPSO) {
                CPSOPanel cpsoPanel = new CPSOPanel();
                cpsoPanel.setDefaultValue();
                cpsoFeatSelectionPanel = cpsoPanel;
                btn_moreOpWrapper.setEnabled(true);
//                System.out.println("classifier type = " + cpsoFeatSelectionPanel.getClassifierType().toString());
//                if (cpsoFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                    SVMClassifierPanel pan = (SVMClassifierPanel) cpsoFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:   kernel = " + pan.getKernel().toString()
//                            + "   C = " + pan.getParameterC());
//                } else if (cpsoFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                    DTClassifierPanel pan = (DTClassifierPanel) cpsoFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    min num = " + pan.getMinNum()
//                            + "  confidence = " + pan.getConfidence());
//                } else if (cpsoFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                    KNNClassifierPanel pan = (KNNClassifierPanel) cpsoFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    KNN Value = " + pan.getKNNValue());
//                }
//                System.out.println("default:   num iteration = " + cpsoFeatSelectionPanel.getNumIteration());
//                System.out.println("population size = " + cpsoFeatSelectionPanel.getPopulationSize());
//                System.out.println("inertia weight = " + cpsoFeatSelectionPanel.getInertiaWeight());
//                System.out.println("c1 = " + cpsoFeatSelectionPanel.getC1());
//                System.out.println("c2 = " + cpsoFeatSelectionPanel.getC2());
//                System.out.println("start position = " + cpsoFeatSelectionPanel.getStartPosInterval());
//                System.out.println("end position = " + cpsoFeatSelectionPanel.getEndPosInterval());
//                System.out.println("min velocity = " + cpsoFeatSelectionPanel.getMinVelocity());
//                System.out.println("max velocity = " + cpsoFeatSelectionPanel.getMaxVelocity());
//                System.out.println("K fold = " + cpsoFeatSelectionPanel.getKFolds());
//                System.out.println("theta = " + cpsoFeatSelectionPanel.getTheta());
            } else if (wrapperType == WrapperType.PSO42) {
                PSO42Panel pso42Panel = new PSO42Panel();
                pso42Panel.setDefaultValue();
                pso42FeatSelectionPanel = pso42Panel;
                btn_moreOpWrapper.setEnabled(true);
//                System.out.println("classifier type = " + pso42FeatSelectionPanel.getClassifierType().toString());
//                if (pso42FeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                    SVMClassifierPanel pan = (SVMClassifierPanel) pso42FeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:   kernel = " + pan.getKernel().toString()
//                            + "   C = " + pan.getParameterC());
//                } else if (pso42FeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                    DTClassifierPanel pan = (DTClassifierPanel) pso42FeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    min num = " + pan.getMinNum()
//                            + "  confidence = " + pan.getConfidence());
//                } else if (pso42FeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                    KNNClassifierPanel pan = (KNNClassifierPanel) pso42FeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    KNN Value = " + pan.getKNNValue());
//                }
//                System.out.println("default:   num iteration = " + pso42FeatSelectionPanel.getNumIteration());
//                System.out.println("population size = " + pso42FeatSelectionPanel.getPopulationSize());
//                System.out.println("inertia weight = " + pso42FeatSelectionPanel.getInertiaWeight());
//                System.out.println("c1 = " + pso42FeatSelectionPanel.getC1());
//                System.out.println("c2 = " + pso42FeatSelectionPanel.getC2());
//                System.out.println("start position = " + pso42FeatSelectionPanel.getStartPosInterval());
//                System.out.println("end position = " + pso42FeatSelectionPanel.getEndPosInterval());
//                System.out.println("min velocity = " + pso42FeatSelectionPanel.getMinVelocity());
//                System.out.println("max velocity = " + pso42FeatSelectionPanel.getMaxVelocity());
//                System.out.println("K fold = " + pso42FeatSelectionPanel.getKFolds());
//                System.out.println("theta = " + pso42FeatSelectionPanel.getTheta());
            } else if (wrapperType == WrapperType.HPSO_LS) {
                HPSO_LSPanel hpsolsPanel = new HPSO_LSPanel();
                hpsolsPanel.setDefaultValue();
                hpsolsFeatSelectionPanel = hpsolsPanel;
                btn_moreOpWrapper.setEnabled(true);
//                System.out.println("classifier type = " + hpsolsFeatSelectionPanel.getClassifierType().toString());
//                if (hpsolsFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                    SVMClassifierPanel pan = (SVMClassifierPanel) hpsolsFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:   kernel = " + pan.getKernel().toString()
//                            + "   C = " + pan.getParameterC());
//                } else if (hpsolsFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                    DTClassifierPanel pan = (DTClassifierPanel) hpsolsFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    min num = " + pan.getMinNum()
//                            + "  confidence = " + pan.getConfidence());
//                } else if (hpsolsFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                    KNNClassifierPanel pan = (KNNClassifierPanel) hpsolsFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    KNN Value = " + pan.getKNNValue());
//                }
//                System.out.println("default:   num iteration = " + hpsolsFeatSelectionPanel.getNumIteration());
//                System.out.println("population size = " + hpsolsFeatSelectionPanel.getPopulationSize());
//                System.out.println("inertia weight = " + hpsolsFeatSelectionPanel.getInertiaWeight());
//                System.out.println("c1 = " + hpsolsFeatSelectionPanel.getC1());
//                System.out.println("c2 = " + hpsolsFeatSelectionPanel.getC2());
//                System.out.println("start position = " + hpsolsFeatSelectionPanel.getStartPosInterval());
//                System.out.println("end position = " + hpsolsFeatSelectionPanel.getEndPosInterval());
//                System.out.println("min velocity = " + hpsolsFeatSelectionPanel.getMinVelocity());
//                System.out.println("max velocity = " + hpsolsFeatSelectionPanel.getMaxVelocity());
//                System.out.println("K fold = " + hpsolsFeatSelectionPanel.getKFolds());
//                System.out.println("epsilon = " + hpsolsFeatSelectionPanel.getEpsilon());
//                System.out.println("alpha = " + hpsolsFeatSelectionPanel.getAlpha());
            } else if (wrapperType == WrapperType.SIMPLE_GA) {
                SimpleGAPanel simpleGAPanel = new SimpleGAPanel();
                simpleGAPanel.setDefaultValue();
                simpleGAFeatSelectionPanel = simpleGAPanel;
                btn_moreOpWrapper.setEnabled(true);

//                System.out.println("classifier type = " + simpleGAFeatSelectionPanel.getClassifierType().toString());
//                if (simpleGAFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                    SVMClassifierPanel pan = (SVMClassifierPanel) simpleGAFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:   kernel = " + pan.getKernel().toString()
//                            + "   C = " + pan.getParameterC());
//                } else if (simpleGAFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                    DTClassifierPanel pan = (DTClassifierPanel) simpleGAFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    min num = " + pan.getMinNum()
//                            + "  confidence = " + pan.getConfidence());
//                } else if (simpleGAFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                    KNNClassifierPanel pan = (KNNClassifierPanel) simpleGAFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    KNN Value = " + pan.getKNNValue());
//                }
//                System.out.println("selection type = " + simpleGAFeatSelectionPanel.getSelectionType().toString());
//                System.out.println("crossover type = " + simpleGAFeatSelectionPanel.getCrossOverType().toString());
//                System.out.println("mutation type = " + simpleGAFeatSelectionPanel.getMutationType().toString());
//                System.out.println("replacement type = " + simpleGAFeatSelectionPanel.getReplacementType().toString());
//                System.out.println("num iteration = " + simpleGAFeatSelectionPanel.getNumIteration());
//                System.out.println("population size = " + simpleGAFeatSelectionPanel.getPopulationSize());
//                System.out.println("crossover rate = " + simpleGAFeatSelectionPanel.getCrossoverRate());
//                System.out.println("mutation rate = " + simpleGAFeatSelectionPanel.getMutationRate());
//                System.out.println("K fold = " + simpleGAFeatSelectionPanel.getKFolds());
            } else if (wrapperType == WrapperType.HGAFS) {
                HGAFSPanel hgafsPanel = new HGAFSPanel();
                hgafsPanel.setDefaultValue();
                hgafsFeatSelectionPanel = hgafsPanel;
                btn_moreOpWrapper.setEnabled(true);

//                System.out.println("classifier type = " + hgafsFeatSelectionPanel.getClassifierType().toString());
//                if (hgafsFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                    SVMClassifierPanel pan = (SVMClassifierPanel) hgafsFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:   kernel = " + pan.getKernel().toString()
//                            + "   C = " + pan.getParameterC());
//                } else if (hgafsFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                    DTClassifierPanel pan = (DTClassifierPanel) hgafsFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    min num = " + pan.getMinNum()
//                            + "  confidence = " + pan.getConfidence());
//                } else if (hgafsFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                    KNNClassifierPanel pan = (KNNClassifierPanel) hgafsFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    KNN Value = " + pan.getKNNValue());
//                }
//
//                System.out.println("selection type = " + hgafsFeatSelectionPanel.getSelectionType().toString());
//                System.out.println("crossover type = " + hgafsFeatSelectionPanel.getCrossOverType().toString());
//                System.out.println("mutation type = " + hgafsFeatSelectionPanel.getMutationType().toString());
//                System.out.println("replacement type = " + hgafsFeatSelectionPanel.getReplacementType().toString());
//                System.out.println("num iteration = " + hgafsFeatSelectionPanel.getNumIteration());
//                System.out.println("population size = " + hgafsFeatSelectionPanel.getPopulationSize());
//                System.out.println("crossover rate = " + hgafsFeatSelectionPanel.getCrossoverRate());
//                System.out.println("mutation rate = " + hgafsFeatSelectionPanel.getMutationRate());
//                System.out.println("K fold = " + hgafsFeatSelectionPanel.getKFolds());
//                System.out.println("epsilon = " + hgafsFeatSelectionPanel.getEpsilon());
//                System.out.println("mu = " + hgafsFeatSelectionPanel.getMu());
            } else if (wrapperType == WrapperType.OPTIMAL_ACO) {
                OptimalACOPanel optimalacoPanel = new OptimalACOPanel();
                optimalacoPanel.setDefaultValue();
                optimalACOFeatSelectionPanel = optimalacoPanel;
                btn_moreOpWrapper.setEnabled(true);

//                System.out.println("classifier type = " + optimalACOFeatSelectionPanel.getClassifierType().toString());
//                if (optimalACOFeatSelectionPanel.getClassifierType() == ClassifierType.SVM) {
//                    SVMClassifierPanel pan = (SVMClassifierPanel) optimalACOFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:   kernel = " + pan.getKernel().toString()
//                            + "   C = " + pan.getParameterC());
//                } else if (optimalACOFeatSelectionPanel.getClassifierType() == ClassifierType.DT) {
//                    DTClassifierPanel pan = (DTClassifierPanel) optimalACOFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    min num = " + pan.getMinNum()
//                            + "  confidence = " + pan.getConfidence());
//                } else if (optimalACOFeatSelectionPanel.getClassifierType() == ClassifierType.KNN) {
//                    KNNClassifierPanel pan = (KNNClassifierPanel) optimalACOFeatSelectionPanel.getSelectedClassifierPan();
//                    System.out.println("default:    KNN Value = " + pan.getKNNValue());
//                }
//
//                System.out.println("num iteration = " + optimalACOFeatSelectionPanel.getNumIteration());
//                System.out.println("colony size = " + optimalACOFeatSelectionPanel.getColonySize());
//                System.out.println("alpha = " + optimalACOFeatSelectionPanel.getAlpha());
//                System.out.println("beta = " + optimalACOFeatSelectionPanel.getBeta());
//                System.out.println("evRate = " + optimalACOFeatSelectionPanel.getEvRate());
//                System.out.println("K fold = " + optimalACOFeatSelectionPanel.getKFolds());
//                System.out.println("init pheromone = " + optimalACOFeatSelectionPanel.getInitPheromone());
//                System.out.println("phi = " + optimalACOFeatSelectionPanel.getPhi());
            } else {
                btn_moreOpWrapper.setEnabled(false);
            }
        } else {
            btn_moreOpWrapper.setEnabled(false);
        }
    }

    /**
     * This method sets an action for the cb_embedded combo box.
     *
     * @param e an action event
     */
    private void cb_embeddedItemStateChanged(ItemEvent e) {
        EmbeddedType embeddedType = EmbeddedType.parse(cb_embedded.getSelectedItem().toString());
        if (embeddedType != EmbeddedType.NONE) {
            cb_supervised.setSelectedItem(FilterType.NONE.toString());
            cb_unsupervised.setSelectedItem(FilterType.NONE.toString());
            cb_wrapper.setSelectedItem(WrapperType.NONE.toString());
            cb_hybrid.setSelectedItem(HybridType.NONE.toString());
            if (embeddedType == EmbeddedType.DECISION_TREE_BASED) {
                DecisionTreeBasedPanel dtBasedPanel = new DecisionTreeBasedPanel();
                dtBasedPanel.setDefaultValue();
                decisionTreeBasedMethodType = dtBasedPanel.getTreeType();
                confidence = dtBasedPanel.getConfidence();
                minNum = dtBasedPanel.getMinNum();
                btn_moreOpEmbedded.setEnabled(true);
//                System.out.println("default:   dtType = " + decisionTreeBasedMethodType.toString()
//                        + "   confidence = " + confidence
//                        + "   minNum = " + minNum);
            } else if (embeddedType == EmbeddedType.RANDOM_FOREST_METHOD) {
                DecisionTreeBasedPanel dtBasedPanel = new DecisionTreeBasedPanel();
                dtBasedPanel.setDefaultValue(TreeType.RANDOM_FOREST);
                decisionTreeBasedMethodType = dtBasedPanel.getTreeType();
                randomForestNumFeatures = dtBasedPanel.getRandomForestNumFeatures();
                maxDepth = dtBasedPanel.getMaxDepth();
                randomForestNumIterations = dtBasedPanel.getRandomForestNumIterations();
                btn_moreOpEmbedded.setEnabled(true);
//                System.out.println("default...   " + decisionTreeBasedMethodType.toString()
//                        + "   randomForestNumFeatures = " + randomForestNumFeatures
//                        + "   maxDepth = " + maxDepth
//                        + "   randomforestNumIterations = " + randomForestNumIterations);
            } else if (embeddedType == EmbeddedType.MSVM_RFE) {
                MSVM_RFEPanel msvmPanel = new MSVM_RFEPanel();
                msvmPanel.setDefaultValue();
                msvmFeatureSelectionPanel = msvmPanel;
                btn_moreOpEmbedded.setEnabled(true);
//                System.out.println("default:   kernel = " + msvmFeatureSelectionPanel.getKernel().toString()
//                        + "   C = " + msvmFeatureSelectionPanel.getParameterC()
//                        + "   Fold = " + msvmFeatureSelectionPanel.getNumFold()
//                        + "   numRun = " + msvmFeatureSelectionPanel.getNumRun());
            } else if (embeddedType == EmbeddedType.SVM_RFE
                    || embeddedType == EmbeddedType.OVO_SVM_RFE
                    || embeddedType == EmbeddedType.OVA_SVM_RFE) {
                SVMClassifierPanel svmPanel = new SVMClassifierPanel();
                svmPanel.setDefaultValue();
                svmFeatureSelectionPanel = svmPanel;
                btn_moreOpEmbedded.setEnabled(true);
//                System.out.println("default:   kernel = " + svmFeatureSelectionPanel.getKernel().toString()
//                        + "   C = " + svmFeatureSelectionPanel.getParameterC());
            } else {
                btn_moreOpEmbedded.setEnabled(false);
            }
        } else {
            btn_moreOpEmbedded.setEnabled(false);
        }
    }

    /**
     * This method sets an action for the cb_hybrid combo box.
     *
     * @param e an action event
     */
    private void cb_hybridItemStateChanged(ItemEvent e) {
        HybridType hybridType = HybridType.parse(cb_hybrid.getSelectedItem().toString());
        if (hybridType != HybridType.NONE) {
            cb_supervised.setSelectedItem(FilterType.NONE.toString());
            cb_unsupervised.setSelectedItem(FilterType.NONE.toString());
            cb_wrapper.setSelectedItem(WrapperType.NONE.toString());
            cb_embedded.setSelectedItem(EmbeddedType.NONE.toString());
//            if (cb_hybrid.getSelectedItem().equals("name methods")) {
//                btn_moreOpHybrid.setEnabled(true);
//            } else {
//                btn_moreOpHybrid.setEnabled(false);
//            }
        } else {
            btn_moreOpHybrid.setEnabled(false);
        }
    }

    /**
     * This method sets an action for the cb_classifier combo box.
     *
     * @param e an action event
     */
    private void cb_classifierItemStateChanged(ItemEvent e) {
        ClassifierType classifierType = ClassifierType.parse(cb_classifier.getSelectedItem().toString());
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (classifierType == ClassifierType.SVM) {
                svmEvaluationPanel = new SVMClassifierPanel();
                svmEvaluationPanel.setDefaultValue();
                //typeKernel = svmEvaluationPanel.getKernel();
                btn_moreOpClassifier.setEnabled(true);
                selectedEvaluationClassifierPanel = svmEvaluationPanel;
                //System.out.println("default:   kernel = " + typeKernel);
//                System.out.println("default:   kernel = " + ((SVMClassifierPanel) selectedEvaluationClassifierPanel).getKernel().toString()
//                        + "   C = " + ((SVMClassifierPanel) selectedEvaluationClassifierPanel).getParameterC());
            } else if (classifierType == ClassifierType.DT) {
                dtEvaluationPanel = new DTClassifierPanel();
                dtEvaluationPanel.setDefaultValue();
                //confidence = dtEvaluationPanel.getConfidence();
                //minNum = dtEvaluationPanel.getMinNum();
                btn_moreOpClassifier.setEnabled(true);
                selectedEvaluationClassifierPanel = dtEvaluationPanel;
                //System.out.println("default:    min num = " + minNum + "  confidence = " + confidence);
//                System.out.println("default:    min num = " + ((DTClassifierPanel) selectedEvaluationClassifierPanel).getMinNum()
//                        + "  confidence = " + ((DTClassifierPanel) selectedEvaluationClassifierPanel).getConfidence());
            } else if (classifierType == ClassifierType.KNN) {
                knnEvaluationPanel = new KNNClassifierPanel();
                knnEvaluationPanel.setDefaultValue();
                //confidence = knnEvaluationPanel.getKNNValue();
                btn_moreOpClassifier.setEnabled(true);
                selectedEvaluationClassifierPanel = knnEvaluationPanel;
                //System.out.println("default:    KNN Value = " + minNum);
//                System.out.println("default:    KNN Value = " + ((KNNClassifierPanel) selectedEvaluationClassifierPanel).getKNNValue());
            } else {
                btn_moreOpClassifier.setEnabled(false);
            }
        }
    }

    /**
     * This method sets an action for the cb_start combo box.
     *
     * @param e an action event
     */
    private void cb_startItemStateChanged(ItemEvent e) {
    }

    /**
     * This method returns a list of parameters that are applied in a given
     * filter-based feature selection method
     *
     * @param type type of the feature selection method
     * @param sizeSelectedFeatureSubset the number of selected features
     *
     * @return a list of parameters
     */
    private Object[] getFilterApproachParameters(FilterType type, int sizeSelectedFeatureSubset) {
        Object[] parameters;
        if (type == FilterType.RRFS) {
            parameters = new Object[2];
            parameters[1] = simValue;
        } else if (type == FilterType.RSM) {
            parameters = new Object[5];
            int newSizeSubspace = sizeSubspace;
            int newElimination = elimination;
            if (sizeSubspace == 0 && elimination == 0) {
                newSizeSubspace = data.getNumFeature() / 2 + 1;
                newElimination = newSizeSubspace / 2 + 1;
            } else if (elimination == 0) {
                newElimination = newSizeSubspace / 2 + 1;
            }
            parameters[1] = numSelection;
            parameters[2] = newSizeSubspace;
            parameters[3] = newElimination;
            parameters[4] = multMethodName;
        } else if (type == FilterType.UFSACO) {
            parameters = new Object[8];
            parameters[1] = initPheromone;
            parameters[2] = numIteration;
            parameters[3] = numAnts;
            parameters[4] = numFeatOfAnt;
            parameters[5] = evRate;
            parameters[6] = beta;
            parameters[7] = q0;
        } else if (type == FilterType.RRFSACO_1) {
            parameters = new Object[7];
            parameters[1] = numIteration;
            parameters[2] = numAnts;
            parameters[3] = numFeatOfAnt;
            parameters[4] = evRate;
            parameters[5] = beta;
            parameters[6] = q0;
        } else if (type == FilterType.RRFSACO_2) {
            parameters = new Object[9];
            parameters[1] = initPheromone;
            parameters[2] = numIteration;
            parameters[3] = numAnts;
            parameters[4] = numFeatOfAnt;
            parameters[5] = evRate;
            parameters[6] = alpha;
            parameters[7] = beta;
            parameters[8] = q0;
        } else if (type == FilterType.IRRFSACO_1) {
            parameters = new Object[7];
            parameters[1] = numIteration;
            parameters[2] = numAnts;
            parameters[3] = numFeatOfAnt;
            parameters[4] = evRate;
            parameters[5] = beta;
            parameters[6] = q0;
        } else if (type == FilterType.IRRFSACO_2) {
            parameters = new Object[9];
            parameters[1] = initPheromone;
            parameters[2] = numIteration;
            parameters[3] = numAnts;
            parameters[4] = numFeatOfAnt;
            parameters[5] = evRate;
            parameters[6] = alpha;
            parameters[7] = beta;
            parameters[8] = q0;
        } else if (type == FilterType.MGSACO) {
            parameters = new Object[7];
            parameters[1] = initPheromone;
            parameters[2] = numIteration;
            parameters[3] = numAnts;
            parameters[4] = evRate;
            parameters[5] = beta;
            parameters[6] = q0;
        } else {
            parameters = new Object[1];
        }

        parameters[0] = sizeSelectedFeatureSubset;

        return parameters;
    }

    /**
     * This method returns a list of parameters that are applied in a given
     * feature weighting method
     *
     * @param type type of the feature selection method
     * @param sizeSelectedFeatureSubset the number of selected features
     *
     * @return a list of parameters
     */
    private Object[] getWeightedFilterApproachParameters(FilterType type, int sizeSelectedFeatureSubset) {
        Object[] parameters;
        if (type == FilterType.LAPLACIAN_SCORE) {
            boolean isSupervised = !cb_supervised.getSelectedItem().equals(FilterType.NONE.toString());
            if (isSupervised) {
                parameters = new Object[2];
                parameters[1] = constParam;
            } else {
                parameters = new Object[3];
                parameters[1] = constParam;
                parameters[2] = KNearest;
            }
        } else {
            parameters = new Object[1];
        }

        parameters[0] = sizeSelectedFeatureSubset;

        return parameters;
    }

    /**
     * This method returns a list of parameters that are applied in a given
     * wrapper-based feature selection method
     *
     * @param type type of the feature selection method
     * @param numFeatures the number of original features
     *
     * @return a list of parameters
     */
    private Object[] getWrapperApproachParameters(WrapperType type, int numFeatures) {
        Object[] parameters;

        if (type == WrapperType.BPSO) {
            parameters = new Object[14];
            parameters[2] = bpsoFeatSelectionPanel.getClassifierType();
            parameters[3] = bpsoFeatSelectionPanel.getSelectedClassifierPan();
            parameters[4] = bpsoFeatSelectionPanel.getNumIteration();
            parameters[5] = bpsoFeatSelectionPanel.getPopulationSize();
            parameters[6] = bpsoFeatSelectionPanel.getInertiaWeight();
            parameters[7] = bpsoFeatSelectionPanel.getC1();
            parameters[8] = bpsoFeatSelectionPanel.getC2();
            parameters[9] = bpsoFeatSelectionPanel.getStartPosInterval();
            parameters[10] = bpsoFeatSelectionPanel.getEndPosInterval();
            parameters[11] = bpsoFeatSelectionPanel.getMinVelocity();
            parameters[12] = bpsoFeatSelectionPanel.getMaxVelocity();
            parameters[13] = bpsoFeatSelectionPanel.getKFolds();
        } else if (type == WrapperType.CPSO) {
            parameters = new Object[15];
            parameters[2] = cpsoFeatSelectionPanel.getClassifierType();
            parameters[3] = cpsoFeatSelectionPanel.getSelectedClassifierPan();
            parameters[4] = cpsoFeatSelectionPanel.getNumIteration();
            parameters[5] = cpsoFeatSelectionPanel.getPopulationSize();
            parameters[6] = cpsoFeatSelectionPanel.getInertiaWeight();
            parameters[7] = cpsoFeatSelectionPanel.getC1();
            parameters[8] = cpsoFeatSelectionPanel.getC2();
            parameters[9] = cpsoFeatSelectionPanel.getStartPosInterval();
            parameters[10] = cpsoFeatSelectionPanel.getEndPosInterval();
            parameters[11] = cpsoFeatSelectionPanel.getMinVelocity();
            parameters[12] = cpsoFeatSelectionPanel.getMaxVelocity();
            parameters[13] = cpsoFeatSelectionPanel.getKFolds();
            parameters[14] = cpsoFeatSelectionPanel.getTheta();
        } else if (type == WrapperType.PSO42) {
            parameters = new Object[15];
            parameters[2] = pso42FeatSelectionPanel.getClassifierType();
            parameters[3] = pso42FeatSelectionPanel.getSelectedClassifierPan();
            parameters[4] = pso42FeatSelectionPanel.getNumIteration();
            parameters[5] = pso42FeatSelectionPanel.getPopulationSize();
            parameters[6] = pso42FeatSelectionPanel.getInertiaWeight();
            parameters[7] = pso42FeatSelectionPanel.getC1();
            parameters[8] = pso42FeatSelectionPanel.getC2();
            parameters[9] = pso42FeatSelectionPanel.getStartPosInterval();
            parameters[10] = pso42FeatSelectionPanel.getEndPosInterval();
            parameters[11] = pso42FeatSelectionPanel.getMinVelocity();
            parameters[12] = pso42FeatSelectionPanel.getMaxVelocity();
            parameters[13] = pso42FeatSelectionPanel.getKFolds();
            parameters[14] = pso42FeatSelectionPanel.getTheta();
        } else if (type == WrapperType.HPSO_LS) {
            parameters = new Object[16];
            parameters[2] = hpsolsFeatSelectionPanel.getClassifierType();
            parameters[3] = hpsolsFeatSelectionPanel.getSelectedClassifierPan();
            parameters[4] = hpsolsFeatSelectionPanel.getNumIteration();
            parameters[5] = hpsolsFeatSelectionPanel.getPopulationSize();
            parameters[6] = hpsolsFeatSelectionPanel.getInertiaWeight();
            parameters[7] = hpsolsFeatSelectionPanel.getC1();
            parameters[8] = hpsolsFeatSelectionPanel.getC2();
            parameters[9] = hpsolsFeatSelectionPanel.getStartPosInterval();
            parameters[10] = hpsolsFeatSelectionPanel.getEndPosInterval();
            parameters[11] = hpsolsFeatSelectionPanel.getMinVelocity();
            parameters[12] = hpsolsFeatSelectionPanel.getMaxVelocity();
            parameters[13] = hpsolsFeatSelectionPanel.getKFolds();
            parameters[14] = hpsolsFeatSelectionPanel.getEpsilon();
            parameters[15] = hpsolsFeatSelectionPanel.getAlpha();
        } else if (type == WrapperType.SIMPLE_GA) {
            parameters = new Object[13];
            parameters[2] = simpleGAFeatSelectionPanel.getClassifierType();
            parameters[3] = simpleGAFeatSelectionPanel.getSelectedClassifierPan();
            parameters[4] = simpleGAFeatSelectionPanel.getSelectionType();
            parameters[5] = simpleGAFeatSelectionPanel.getCrossOverType();
            parameters[6] = simpleGAFeatSelectionPanel.getMutationType();
            parameters[7] = simpleGAFeatSelectionPanel.getReplacementType();
            parameters[8] = simpleGAFeatSelectionPanel.getNumIteration();
            parameters[9] = simpleGAFeatSelectionPanel.getPopulationSize();
            parameters[10] = simpleGAFeatSelectionPanel.getCrossoverRate();
            parameters[11] = simpleGAFeatSelectionPanel.getMutationRate();
            parameters[12] = simpleGAFeatSelectionPanel.getKFolds();
        } else if (type == WrapperType.HGAFS) {
            parameters = new Object[15];
            parameters[2] = hgafsFeatSelectionPanel.getClassifierType();
            parameters[3] = hgafsFeatSelectionPanel.getSelectedClassifierPan();
            parameters[4] = hgafsFeatSelectionPanel.getSelectionType();
            parameters[5] = hgafsFeatSelectionPanel.getCrossOverType();
            parameters[6] = hgafsFeatSelectionPanel.getMutationType();
            parameters[7] = hgafsFeatSelectionPanel.getReplacementType();
            parameters[8] = hgafsFeatSelectionPanel.getNumIteration();
            parameters[9] = hgafsFeatSelectionPanel.getPopulationSize();
            parameters[10] = hgafsFeatSelectionPanel.getCrossoverRate();
            parameters[11] = hgafsFeatSelectionPanel.getMutationRate();
            parameters[12] = hgafsFeatSelectionPanel.getKFolds();
            parameters[13] = hgafsFeatSelectionPanel.getEpsilon();
            parameters[14] = hgafsFeatSelectionPanel.getMu();
        } else if (type == WrapperType.OPTIMAL_ACO) {
            parameters = new Object[12];
            parameters[2] = optimalACOFeatSelectionPanel.getClassifierType();
            parameters[3] = optimalACOFeatSelectionPanel.getSelectedClassifierPan();
            parameters[4] = optimalACOFeatSelectionPanel.getNumIteration();
            parameters[5] = optimalACOFeatSelectionPanel.getColonySize();
            parameters[6] = optimalACOFeatSelectionPanel.getAlpha();
            parameters[7] = optimalACOFeatSelectionPanel.getBeta();
            parameters[8] = optimalACOFeatSelectionPanel.getEvRate();
            parameters[9] = optimalACOFeatSelectionPanel.getKFolds();
            parameters[10] = optimalACOFeatSelectionPanel.getInitPheromone();
            parameters[11] = optimalACOFeatSelectionPanel.getPhi();
        } else {
            parameters = new Object[2];
        }

        parameters[0] = PATH_PROJECT;
        parameters[1] = numFeatures;

        return parameters;
    }

    /**
     * This method returns a list of parameters that are applied in a given
     * embedded-based feature selection method
     *
     * @param type type of the feature selection method
     *
     * @return a list of parameters
     */
    private Object[] getEmbeddedApproachParameters(EmbeddedType type) {
        Object[] parameters;
        if (type == EmbeddedType.DECISION_TREE_BASED) {
            if (decisionTreeBasedMethodType == TreeType.C45) {
                parameters = new Object[4];
                parameters[2] = confidence;
                parameters[3] = minNum;
            } else if (decisionTreeBasedMethodType == TreeType.RANDOM_TREE) {
                parameters = new Object[6];
                parameters[2] = randomTreeKValue;
                parameters[3] = randomTreeMaxDepth;
                parameters[4] = randomTreeMinNum;
                parameters[5] = randomTreeMinVarianceProp;
            } else {
                parameters = new Object[2];
            }
            parameters[1] = decisionTreeBasedMethodType;
        } else if (type == EmbeddedType.RANDOM_FOREST_METHOD) {
            parameters = new Object[5];
            parameters[1] = TreeType.RANDOM_FOREST;
            parameters[2] = randomForestNumFeatures;
            parameters[3] = maxDepth;
            parameters[4] = randomForestNumIterations;
        } else if (type == EmbeddedType.SVM_RFE
                || type == EmbeddedType.OVO_SVM_RFE
                || type == EmbeddedType.OVA_SVM_RFE) {
            parameters = new Object[3];
            parameters[1] = svmFeatureSelectionPanel.getKernel();
            parameters[2] = svmFeatureSelectionPanel.getParameterC();
        } else if (type == EmbeddedType.MSVM_RFE) {
            parameters = new Object[5];
            parameters[1] = msvmFeatureSelectionPanel.getKernel();
            parameters[2] = msvmFeatureSelectionPanel.getParameterC();
            parameters[3] = msvmFeatureSelectionPanel.getNumFold();
            parameters[4] = msvmFeatureSelectionPanel.getNumRun();
        } else {
            parameters = new Object[1];
        }

        parameters[0] = PATH_PROJECT;

        return parameters;
    }

    /**
     * This method returns a list of parameters that are applied in a given
     * hybrid-based feature selection method
     *
     * @param type type of the feature selection method
     * @param sizeSelectedFeatureSubset the number of selected features
     *
     * @return a list of parameters
     */
    private Object[] getHybridApproachParameters(HybridType type, int sizeSelectedFeatureSubset) {
        Object[] parameters;

        if (type == HybridType.NONE) {
            parameters = new Object[1];
        } else {
            parameters = new Object[1];
        }

        parameters[0] = sizeSelectedFeatureSubset;

        return parameters;
    }

    /**
     * This method performs the feature selection based on weighted filter
     * approach
     *
     * @param type name of the feature selection method
     * @param isSupervised shows that the feature selection method is supervised
     */
    private void weightedFilterApproachPerform(FilterType type, boolean isSupervised) {
        WeightedFilterApproach method = WeightedFilterApproach.newMethod(type, isSupervised,
                getWeightedFilterApproachParameters(type, 0));
        method.loadDataSet(data);
        String message = method.validate();
        if (!message.equals("")) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            progressValue = 1;
            repaint();
            int numRuns = Integer.parseInt(cb_start.getSelectedItem().toString());
            double totalValuesProgress = numRuns * numSelectedSubsets.length;
            ResultPanel resPanel = new ResultPanel(PATH_PROJECT);
            finalResults = new Results(data, numRuns, numSelectedSubsets.length, PATH_PROJECT,
                    type, cb_classifier.getSelectedItem(), selectedEvaluationClassifierPanel);

            //save initial information of the dataset
            resPanel.setMessage(finalResults.toString(ResultType.DATASET_INFORMATION));

            for (int i = 0; i < numRuns; i++) {
                resPanel.setMessage("  Iteration (" + (i + 1) + "):\n");
                for (int j = 0; j < numSelectedSubsets.length; j++) {
                    resPanel.setMessage("    " + numSelectedSubsets[j] + " feature selected:\n");

                    //Set new parameter values of feature selection method
                    method.setNumSelectedFeature(numSelectedSubsets[j]);
                    method.loadDataSet(data);

                    long startTime = System.currentTimeMillis();
                    method.evaluateFeatures();

                    finalResults.setTime((System.currentTimeMillis() - startTime) / 1000.0);
                    finalResults.setCurrentSelectedSubset(i, j, method.getSelectedFeatureSubset());
                    finalResults.setCurrentFeatureValues(method.getFeatureValues());
                    finalResults.computePerformanceMeasures(i, j);

                    //shows new results in the panel of results
                    resPanel.setMessage(finalResults.toString(ResultType.FEATURE_VALUES));
                    resPanel.setMessage(finalResults.toString(ResultType.SELECTED_FEATURE_SUBSET));

                    //updates the value of progress bar
                    progressValue = (int) ((upProgValue(numSelectedSubsets.length, i, j) / totalValuesProgress) * 100);
                    repaint();
                }
                //randomly splits the datasets
                if (rd_randSet.isSelected()) {
                    data.preProcessing(txt_inputdst.getText(), txt_classLbl.getText());
                }
            }
            finalResults.createFeatureFiles();
            finalResults.computeAverageValuesOfPerformanceMeasures();

            //show the result values in the panel of result
            resPanel.setMessage(finalResults.toString(ResultType.PERFORMANCE_MEASURES));
            resPanel.setEnabledButton();
            setEnabledItem();
        }
    }

    /**
     * This method performs the feature selection based on filter-based approach
     *
     * @param type name of the feature selection method
     * @param isSupervised shows that the feature selection method is supervised
     */
    private void filterApproachPerform(FilterType type, boolean isSupervised) {
        FilterApproach method = FilterApproach.newMethod(type, isSupervised,
                getFilterApproachParameters(type, 0));
        method.loadDataSet(data);
        String message = method.validate();
        if (!message.equals("")) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            progressValue = 1;
            repaint();
            int numRuns = Integer.parseInt(cb_start.getSelectedItem().toString());
            double totalValuesProgress = numRuns * numSelectedSubsets.length;
            ResultPanel resPanel = new ResultPanel(PATH_PROJECT);
            finalResults = new Results(data, numRuns, numSelectedSubsets.length, PATH_PROJECT,
                    type, cb_classifier.getSelectedItem(), selectedEvaluationClassifierPanel);

            //save initial information of the dataset
            resPanel.setMessage(finalResults.toString(ResultType.DATASET_INFORMATION));

            for (int i = 0; i < numRuns; i++) {
                resPanel.setMessage("  Iteration (" + (i + 1) + "):\n");
                for (int j = 0; j < numSelectedSubsets.length; j++) {
                    resPanel.setMessage("    " + numSelectedSubsets[j] + " feature selected:\n");

                    //Set new parameter values of feature selection method
                    method.setNumSelectedFeature(numSelectedSubsets[j]);
                    method.loadDataSet(data);

                    long startTime = System.currentTimeMillis();
                    method.evaluateFeatures();

                    finalResults.setTime((System.currentTimeMillis() - startTime) / 1000.0);
                    finalResults.setCurrentSelectedSubset(i, j, method.getSelectedFeatureSubset());
                    finalResults.computePerformanceMeasures(i, j);

                    //shows new results in the panel of results
                    resPanel.setMessage(finalResults.toString(ResultType.SELECTED_FEATURE_SUBSET));

                    //updates the value of progress bar
                    progressValue = (int) ((upProgValue(numSelectedSubsets.length, i, j) / totalValuesProgress) * 100);
                    repaint();
                }
                //randomly splits the datasets
                if (rd_randSet.isSelected()) {
                    data.preProcessing(txt_inputdst.getText(), txt_classLbl.getText());
                }
            }
            finalResults.createFeatureFiles();
            finalResults.computeAverageValuesOfPerformanceMeasures();

            //show the result values in the panel of result
            resPanel.setMessage(finalResults.toString(ResultType.PERFORMANCE_MEASURES));
            resPanel.setEnabledButton();
            setEnabledItem();
        }
    }

    /**
     * This method performs the feature selection based on wrapper-based
     * approach
     *
     * @param type name of the feature selection method
     */
    private void wrapperApproachPerform(WrapperType type) {
        WrapperApproach method = WrapperApproach.newMethod(type, getWrapperApproachParameters(type, data.getNumFeature()));
        method.loadDataSet(data);
        String message = method.validate();
        if (!message.equals("")) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            progressValue = 1;
            repaint();
            int numRuns = Integer.parseInt(cb_start.getSelectedItem().toString());
            double totalValuesProgress = numRuns * numSelectedSubsets.length;
            ResultPanel resPanel = new ResultPanel(PATH_PROJECT);
            finalResults = new Results(data, numRuns, numSelectedSubsets.length, PATH_PROJECT,
                    type, cb_classifier.getSelectedItem(), selectedEvaluationClassifierPanel);

            //save initial information of the dataset
            resPanel.setMessage(finalResults.toString(ResultType.DATASET_INFORMATION));

            for (int i = 0; i < numRuns; i++) {
                resPanel.setMessage("  Iteration (" + (i + 1) + "):\n");
                for (int j = 0; j < numSelectedSubsets.length; j++) {

                    //Set new parameter values of feature selection method
                    method.setNumSelectedFeature(numSelectedSubsets[j]);
                    method.loadDataSet(data);

                    long startTime = System.currentTimeMillis();
                    method.evaluateFeatures();

                    finalResults.setTime((System.currentTimeMillis() - startTime) / 1000.0);
                    finalResults.setCurrentSelectedSubset(i, j, method.getSelectedFeatureSubset());
                    finalResults.computePerformanceMeasures(i, j);

                    //shows new results in the panel of results
                    resPanel.setMessage("    " + method.getSelectedFeatureSubset().length + " feature selected:\n");
                    resPanel.setMessage(finalResults.toString(ResultType.SELECTED_FEATURE_SUBSET));

                    //updates the value of progress bar
                    progressValue = (int) ((upProgValue(numSelectedSubsets.length, i, j) / totalValuesProgress) * 100);
                    repaint();
                }
                //randomly splits the datasets
                if (rd_randSet.isSelected()) {
                    data.preProcessing(txt_inputdst.getText(), txt_classLbl.getText());
                }
            }
            finalResults.createFeatureFiles();
            finalResults.computeAverageValuesOfPerformanceMeasures();

            //show the result values in the panel of result
            resPanel.setMessage(finalResults.toString(ResultType.PERFORMANCE_MEASURES));
            resPanel.setEnabledButton();
            setEnabledItem();
        }
    }

    /**
     * This method performs the feature selection based on embedded-based
     * approach
     *
     * @param type name of the feature selection method
     */
    private void embeddedApproachPerform(EmbeddedType type) {
        EmbeddedApproach method = EmbeddedApproach.newMethod(type, getEmbeddedApproachParameters(type));
        method.loadDataSet(data);
        String message = method.validate();
        if (!message.equals("")) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            progressValue = 1;
            repaint();
            int numRuns = Integer.parseInt(cb_start.getSelectedItem().toString());
            double totalValuesProgress = numRuns * numSelectedSubsets.length;
            ResultPanel resPanel = new ResultPanel(PATH_PROJECT);
            finalResults = new Results(data, numRuns, numSelectedSubsets.length, PATH_PROJECT,
                    type, cb_classifier.getSelectedItem(), selectedEvaluationClassifierPanel);

            //save initial information of the dataset
            resPanel.setMessage(finalResults.toString(ResultType.DATASET_INFORMATION));

            for (int i = 0; i < numRuns; i++) {
                resPanel.setMessage("  Iteration (" + (i + 1) + "):\n");
                for (int j = 0; j < numSelectedSubsets.length; j++) {
                    resPanel.setMessage("    " + numSelectedSubsets[j] + " feature selected:\n");

                    //Set new parameter values of feature selection method
                    method.setNumSelectedFeature(numSelectedSubsets[j]);
                    method.loadDataSet(data);

                    long startTime = System.currentTimeMillis();
                    method.evaluateFeatures();

                    finalResults.setTime((System.currentTimeMillis() - startTime) / 1000.0);
                    finalResults.setCurrentSelectedSubset(i, j, method.getSelectedFeatureSubset());
                    finalResults.computePerformanceMeasures(i, j);

                    //shows new results in the panel of results
                    resPanel.setMessage(finalResults.toString(ResultType.SELECTED_FEATURE_SUBSET));

                    //updates the value of progress bar
                    progressValue = (int) ((upProgValue(numSelectedSubsets.length, i, j) / totalValuesProgress) * 100);
                    repaint();
                }
                //randomly splits the datasets
                if (rd_randSet.isSelected()) {
                    data.preProcessing(txt_inputdst.getText(), txt_classLbl.getText());
                }
            }
            finalResults.createFeatureFiles();
            finalResults.computeAverageValuesOfPerformanceMeasures();

            //show the result values in the panel of result
            resPanel.setMessage(finalResults.toString(ResultType.PERFORMANCE_MEASURES));
            resPanel.setEnabledButton();
            setEnabledItem();
        }
    }

    /**
     * This method performs the feature selection based on hybrid-based approach
     *
     * @param type name of the feature selection method
     */
    private void hybridApproachPerform(HybridType type) {
        HybridApproach method = HybridApproach.newMethod(type, getHybridApproachParameters(type, 0));
        method.loadDataSet(data);
        String message = method.validate();
        if (!message.equals("")) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            progressValue = 1;
            repaint();
            int numRuns = Integer.parseInt(cb_start.getSelectedItem().toString());
            double totalValuesProgress = numRuns * numSelectedSubsets.length;
            ResultPanel resPanel = new ResultPanel(PATH_PROJECT);
            finalResults = new Results(data, numRuns, numSelectedSubsets.length, PATH_PROJECT,
                    type, cb_classifier.getSelectedItem(), selectedEvaluationClassifierPanel);

            //save initial information of the dataset
            resPanel.setMessage(finalResults.toString(ResultType.DATASET_INFORMATION));

            for (int i = 0; i < numRuns; i++) {
                resPanel.setMessage("  Iteration (" + (i + 1) + "):\n");
                for (int j = 0; j < numSelectedSubsets.length; j++) {
                    resPanel.setMessage("    " + numSelectedSubsets[j] + " feature selected:\n");

                    //Set new parameter values of feature selection method
                    method.setNumSelectedFeature(numSelectedSubsets[j]);
                    method.loadDataSet(data);

                    long startTime = System.currentTimeMillis();
                    method.evaluateFeatures();

                    finalResults.setTime((System.currentTimeMillis() - startTime) / 1000.0);
                    finalResults.setCurrentSelectedSubset(i, j, method.getSelectedFeatureSubset());
                    finalResults.computePerformanceMeasures(i, j);

                    //shows new results in the panel of results
                    resPanel.setMessage(finalResults.toString(ResultType.SELECTED_FEATURE_SUBSET));

                    //updates the value of progress bar
                    progressValue = (int) ((upProgValue(numSelectedSubsets.length, i, j) / totalValuesProgress) * 100);
                    repaint();
                }
                //randomly splits the datasets
                if (rd_randSet.isSelected()) {
                    data.preProcessing(txt_inputdst.getText(), txt_classLbl.getText());
                }
            }
            finalResults.createFeatureFiles();
            finalResults.computeAverageValuesOfPerformanceMeasures();

            //show the result values in the panel of result
            resPanel.setMessage(finalResults.toString(ResultType.PERFORMANCE_MEASURES));
            resPanel.setEnabledButton();
            setEnabledItem();
        }
    }

    /**
     * enables the status of diagrams menu item
     */
    private void setEnabledItem() {
        mi_exeTime.setEnabled(true);
        mi_accur.setEnabled(true);
        mi_error.setEnabled(true);
    }

    /**
     * This method checks the status of the paths of the data files
     *
     * @return true if the paths are valid
     */
    private boolean isCorrectDataset() {
        return !((rd_randSet.isSelected()
                && (txt_inputdst.getText().equals("")
                || txt_classLbl.getText().equals("")))
                || (rd_ttset.isSelected()
                && (txt_trainSet.getText().equals("")
                || txt_testSet.getText().equals("")
                || txt_classLabel.getText().equals(""))));
    }

    /**
     * This method prints the error messages due to unselected or incorrect
     * input values in the dataset, parameter settings, classifier, and run
     * configuration panels
     *
     * @return true if any error have been occurred
     */
    private boolean printErrorMessages() {
        String errorMessages[] = {"  - Path of input dataset or class label isn't valid.",
            "  - Dataset file is incorrect.",
            "  - Class label file is incorrect.",
            "  - Train and test sets aren't compatible.",
            "  - Labels of the samples aren't compatible to the class label file.",
            "  - Feature selction method hasn't been selected.",
            "  - Number of selected features is greater than the original features.",
            "  - Numbers of selected features are empty.",
            "  - Classifier hasn't been selected.",
            "  - Number of runs haven't been selected."};
        String selectedMessages = "Following errors were occured before the starting feature selection process:\n";

        boolean checkError = false;

        //checks the status of the dataset panel
        if (isCorrectDataset()) {
            if (!data.isCorrectDataset()) {
                selectedMessages += errorMessages[1] + "\n";
                checkError = true;
            }
            if (!data.isCompatibleTrainTestSet()) {
                selectedMessages += errorMessages[3] + "\n";
                checkError = true;
            }
            if (!data.isCorrectClassLabel()) {
                selectedMessages += errorMessages[2] + "\n";
                checkError = true;
            }
            if (!data.isCorrectSamplesClass()) {
                selectedMessages += errorMessages[4] + "\n";
                checkError = true;
            }
            //checks the status of the num selected features panel
            if (txtArea_feature.getText().equals("")) {
                selectedMessages += errorMessages[7] + "\n";
                checkError = true;
            } else {
                for (int i = 0; i < numSelectedSubsets.length; i++) {
                    if (numSelectedSubsets[i] > data.getNumFeature()) {
                        selectedMessages += errorMessages[6] + "\n";
                        checkError = true;
                        break;
                    }
                }
            }
        } else {
            selectedMessages += errorMessages[0] + "\n";
            checkError = true;
        }
        //checks the status of the feature selection panel
        if (cb_supervised.getSelectedItem().equals("none")
                && cb_unsupervised.getSelectedItem().equals("none")
                && cb_wrapper.getSelectedItem().equals("none")
                && cb_embedded.getSelectedItem().equals("none")
                && cb_hybrid.getSelectedItem().equals("none")) {
            selectedMessages += errorMessages[5] + "\n";
            checkError = true;
        }
        //checks the status of the classifier panel
        if (cb_classifier.getSelectedItem().equals("none")) {
            selectedMessages += errorMessages[8] + "\n";
            checkError = true;
        }
        //checks the status of the run configuration panel
        if (cb_start.getSelectedItem().equals("none")) {
            selectedMessages += errorMessages[9] + "\n";
            checkError = true;
        }
        if (checkError) {
            JOptionPane.showMessageDialog(null, selectedMessages, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * This method updates the value of progress bar
     *
     * @param totalSize the size of different values of feature subsets
     * @param currentRun the current runs of the algorithm
     * @param currentSize the index of the current subset
     *
     * @return the new value of progress bar
     */
    private int upProgValue(int totalSize, int currentRun, int currentSize) {
        return (currentRun * totalSize) + currentSize + 1;
    }

    /**
     * This method create and show the main panel of the project
     */
    public void createAndShow() {
        JFrame f = new JFrame();
        f.setTitle("UniFeat Main Panel");
        f.setIconImage(new ImageIcon(getClass().getResource("/unifeat/gui/icons/small_logo.png")).getImage());
        f.setLayout(new BorderLayout());
        f.add(this, BorderLayout.CENTER);
        f.add(menuBar, BorderLayout.NORTH);
        f.setSize(865, 660);
        rd_randSet.setSelected(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /**
     * This class is used to create a thread
     */
    class Counter implements Runnable {

        /**
         * This method takes any action whatsoever.
         */
        @Override
        public void run() {

            if (runCode == 0) {
                if (WeightedFilterType.isWeightedFilterMethod(cb_supervised.getSelectedItem().toString())
                        || WeightedFilterType.isWeightedFilterMethod(cb_unsupervised.getSelectedItem().toString())) {

                    boolean isSupervised = !cb_supervised.getSelectedItem().equals(FilterType.NONE.toString());
                    FilterType type = isSupervised ? FilterType.parse(cb_supervised.getSelectedItem().toString())
                            : FilterType.parse(cb_unsupervised.getSelectedItem().toString());

                    weightedFilterApproachPerform(type, isSupervised);
                } else if (NonWeightedFilterType.isNonWeightedFilterMethod(cb_supervised.getSelectedItem().toString())
                        || NonWeightedFilterType.isNonWeightedFilterMethod(cb_unsupervised.getSelectedItem().toString())) {

                    boolean isSupervised = !cb_supervised.getSelectedItem().equals(FilterType.NONE.toString());
                    FilterType type = isSupervised ? FilterType.parse(cb_supervised.getSelectedItem().toString())
                            : FilterType.parse(cb_unsupervised.getSelectedItem().toString());

                    filterApproachPerform(type, isSupervised);
                } else if (WrapperType.isWrapperMethod(cb_wrapper.getSelectedItem().toString())) {
                    wrapperApproachPerform(WrapperType.parse(cb_wrapper.getSelectedItem().toString()));
                } else if (EmbeddedType.isEmbeddedMethod(cb_embedded.getSelectedItem().toString())) {
                    embeddedApproachPerform(EmbeddedType.parse(cb_embedded.getSelectedItem().toString()));
                } else if (HybridType.isHybridMethod(cb_hybrid.getSelectedItem().toString())) {
                    hybridApproachPerform(HybridType.parse(cb_hybrid.getSelectedItem().toString()));
                }
            }
        }
    }

//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            System.out.println("Error setting native LAF: " + e);
//        }
//
//        SwingUtilities.invokeLater(() -> {
//            MainPanel ui1 = new MainPanel("C:\\Users\\ST\\Desktop");
//            ui1.createAndShow();
//        });
//
////        SwingUtilities.invokeLater(new Runnable() {
////
////            @Override
////            public void run() {
////                MainPanel ui = new MainPanel("C:\\Users\\ST\\Desktop");
////                ui.createAndShow();
////            }
////        });
//    }
}
