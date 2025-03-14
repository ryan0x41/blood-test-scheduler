package org.ryan.scheduler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class BloodTestSchedulerApp extends JFrame {

    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> priorityComboBox;
    private JTextField gpDetailsField;
    private JRadioButton wardYesRadio;
    private JRadioButton wardNoRadio;
    private JTextArea queueDisplay;
    private JTextArea noShowDisplay;
    private JTextArea searchResultsDisplay;
    private JTextField searchField;

    public BloodTestSchedulerApp() {
        setTitle("Blood Test Scheduler");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initComponents();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Add New Patient", TitledBorder.LEFT, TitledBorder.TOP));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField(3);
        formPanel.add(ageField);

        formPanel.add(new JLabel("Priority Level:"));
        String[] priorities = {"Urgent", "Medium", "Low"};
        priorityComboBox = new JComboBox<>(priorities);
        formPanel.add(priorityComboBox);

        formPanel.add(new JLabel("GP Details:"));
        gpDetailsField = new JTextField(30);
        formPanel.add(gpDetailsField);

        formPanel.add(new JLabel("From Hospital Ward:"));
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wardYesRadio = new JRadioButton("Yes");
        wardNoRadio = new JRadioButton("No", true);
        ButtonGroup wardGroup = new ButtonGroup();
        wardGroup.add(wardYesRadio);
        wardGroup.add(wardNoRadio);
        radioPanel.add(wardYesRadio);
        radioPanel.add(wardNoRadio);
        formPanel.add(radioPanel);

        formPanel.add(new JButton("Add Patient"));

        JPanel queuePanel = new JPanel(new BorderLayout());
        queuePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Current Queue", TitledBorder.LEFT, TitledBorder.TOP));

        queueDisplay = new JTextArea(10, 30);
        queueDisplay.setEditable(false);
        queuePanel.add(new JScrollPane(queueDisplay), BorderLayout.CENTER);

        JPanel queueButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        queueButtonPanel.add(new JButton("Process Next Patient"));
        queueButtonPanel.add(new JButton("Mark as No-Show"));
        queueButtonPanel.add(new JButton("Undo"));
        queuePanel.add(queueButtonPanel, BorderLayout.SOUTH);

        JPanel noShowPanel = new JPanel(new BorderLayout());
        noShowPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Recent No-Shows", TitledBorder.LEFT, TitledBorder.TOP));

        noShowDisplay = new JTextArea(5, 30);
        noShowDisplay.setEditable(false);
        noShowPanel.add(new JScrollPane(noShowDisplay), BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Search Patients", TitledBorder.LEFT, TitledBorder.TOP));

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JPanel searchInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchInputPanel.add(searchField);
        searchInputPanel.add(searchButton);
        searchPanel.add(searchInputPanel, BorderLayout.NORTH);

        searchResultsDisplay = new JTextArea(5, 30);
        searchResultsDisplay.setEditable(false);
        searchPanel.add(new JScrollPane(searchResultsDisplay), BorderLayout.CENTER);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.add(queuePanel);
        centerPanel.add(noShowPanel);
        centerPanel.add(searchPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new BloodTestSchedulerApp());
    }
}
