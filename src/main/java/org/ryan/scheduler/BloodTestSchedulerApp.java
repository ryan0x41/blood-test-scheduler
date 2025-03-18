package org.ryan.scheduler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.NoSuchElementException;
import org.ryan.models.SchedulerQueue;
import org.ryan.models.Patient;
import org.ryan.models.PriorityLevel;
import org.ryan.models.Appointment;

public class BloodTestSchedulerApp extends JFrame {

    private JTextField idField;
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

    private SchedulerQueue schedulerQueue;

    public BloodTestSchedulerApp() {
        setTitle("Blood Test Scheduler");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initComponents();

        schedulerQueue = new SchedulerQueue();

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

        formPanel.add(new JLabel("Patient ID:"));
        idField = new JTextField(20);
        formPanel.add(idField);

        formPanel.add(new JLabel("Patient Name:"));
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

        JButton addPatientButton = new JButton("Add Patient");
        formPanel.add(addPatientButton);

        JPanel queuePanel = new JPanel(new BorderLayout());
        queuePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Current Queue", TitledBorder.LEFT, TitledBorder.TOP));

        queueDisplay = new JTextArea(10, 30);
        queueDisplay.setEditable(false);
        queuePanel.add(new JScrollPane(queueDisplay), BorderLayout.CENTER);

        JPanel queueButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton processButton = new JButton("Process Next Patient");
        queueButtonPanel.add(processButton);

        JButton noShowButton = new JButton("Mark as No-Show");
        queueButtonPanel.add(noShowButton);

        JButton undoButton = new JButton("Undo Process");
        queueButtonPanel.add(undoButton);

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

        addPatientButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String gpDetails = gpDetailsField.getText().trim();
            boolean fromWard = wardYesRadio.isSelected();

            if (id.isEmpty() || name.isEmpty() || ageText.isEmpty() || gpDetails.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid age.");
                return;
            }

            Patient patient = new Patient(id, name, age, gpDetails, fromWard);
            schedulerQueue.addToWaitingQueue(patient);
            String priorityStr = (String) priorityComboBox.getSelectedItem();
            PriorityLevel priority;
            if ("Urgent".equalsIgnoreCase(priorityStr)) {
                priority = PriorityLevel.URGENT;
            } else if ("Medium".equalsIgnoreCase(priorityStr)) {
                priority = PriorityLevel.MEDIUM;
            } else {
                priority = PriorityLevel.LOW;
            }
            schedulerQueue.moveToPriorityQueue(priority);
            queueDisplay.setText(schedulerQueue.displayAppointments());
        });

        processButton.addActionListener(e -> {
            try {
                Appointment appt = schedulerQueue.processNextAppointment();
                JOptionPane.showMessageDialog(this, "Processed appointment: " + appt);
                queueDisplay.setText(schedulerQueue.displayAppointments());
            } catch (NoSuchElementException ex) {
                JOptionPane.showMessageDialog(this, "No appointments available.");
            }
        });

        undoButton.addActionListener(e -> {
            if (schedulerQueue.undoLastAction()) {
                JOptionPane.showMessageDialog(this, "Undo process successful.");
                queueDisplay.setText(schedulerQueue.displayAppointments());
            } else {
                JOptionPane.showMessageDialog(this, "Nothing to undo.");
            }
        });

        noShowButton.addActionListener(e -> {
            String appointmentId = JOptionPane.showInputDialog(this, "Enter appointment ID to mark as no-show:");
            if (appointmentId != null && !appointmentId.trim().isEmpty()) {
                String reason = JOptionPane.showInputDialog(this, "Enter reason for no-show:");
                if (reason != null && !reason.trim().isEmpty()) {
                    if (schedulerQueue.markNoShow(appointmentId.trim(), reason.trim())) {
                        JOptionPane.showMessageDialog(this, "Marked as no-show.");
                        queueDisplay.setText(schedulerQueue.displayAppointments());
                        noShowDisplay.setText(schedulerQueue.displayNoShows());
                    } else {
                        JOptionPane.showMessageDialog(this, "Appointment ID not found.");
                    }
                }
            }
        });

        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a search query.");
                return;
            }
            String results = schedulerQueue.recursiveSearchByName(query);
            searchResultsDisplay.setText(results);
        });

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
