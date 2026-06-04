package ui;

import dao.MediSyncDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Doctor;
import model.Patient;

public class AppWindow extends JFrame {
    private MediSyncDAO dao;
    // 1. We moved patientBox up here so the whole class can see it
    private JComboBox<Patient> patientBox; 

    public AppWindow() {
        String dbUser = "root";
        String dbPass = "Aliraza80#"; // Your default password // IF WRONG THEN IT WILL ASK THE PASSWORD AGAIN
        boolean connected = false;

        // Keep trying to connect until successful or the user cancels
        while (!connected) {
            try {
                dao = new MediSyncDAO(dbUser, dbPass);
                connected = true; // Connection successful!
            } catch (Exception e) {
                // Connection failed! Ask the professor for their password
                JPasswordField pf = new JPasswordField();
                Object[] message = {
                    "Database connection failed.\nMake sure you ran the medisync.sql script.",
                    "Please enter YOUR local MySQL 'root' password:", pf
                };
                
                int option = JOptionPane.showConfirmDialog(null, message, "MySQL Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                
                if (option == JOptionPane.OK_OPTION) {
                    dbPass = new String(pf.getPassword()); // Try again with the new password
                } else {
                    System.exit(1); // Professor clicked cancel, close the app
                }
            }
        }

        patientBox = new JComboBox<>(); // Initialize the box

        setTitle("MediSync App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Add Patient", buildPatientPanel());
        tabs.addTab("Book Appointment", buildAppointmentPanel());
        tabs.addTab("View Appointments", buildViewPanel());

        add(tabs);
    }

    // Helper method to instantly refresh the dropdown from the database
    private void refreshPatientDropdown() {
        patientBox.removeAllItems(); // Clear old list
        try {
            List<Patient> patients = dao.getAllPatients();
            for (Patient p : patients) {
                patientBox.addItem(p); // Add updated list
            }
        } catch (Exception e) {
            System.out.println("Error reloading patients");
        }
    }

    private JPanel buildPatientPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField fNameField = new JTextField();
        JTextField lNameField = new JTextField();
        JTextField contactField = new JTextField();
        JButton btnAdd = new JButton("Add Patient");

        panel.add(new JLabel("First Name:")); panel.add(fNameField);
        panel.add(new JLabel("Last Name:")); panel.add(lNameField);
        panel.add(new JLabel("Contact:")); panel.add(contactField);
        panel.add(new JLabel("")); panel.add(btnAdd);

        btnAdd.addActionListener(e -> {
            try {
                if (fNameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields.");
                    return;
                }
                // Save to database
                dao.addPatient(fNameField.getText().trim(), lNameField.getText().trim(), contactField.getText().trim());
                JOptionPane.showMessageDialog(this, "Patient added successfully.");
                
                // Clear text boxes
                fNameField.setText(""); lNameField.setText(""); contactField.setText("");
                
                // 2. Refresh the dropdown instantly!
                refreshPatientDropdown(); 

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        return panel;
    }

    private JPanel buildAppointmentPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<Doctor> doctorBox = new JComboBox<>();
        JTextField dateField = new JTextField("2025-06-01 09:00:00");
        JButton btnBook = new JButton("Book Appointment");

        // Load initial data
        refreshPatientDropdown();
        try {
            List<Doctor> doctors = dao.getAllDoctors();
            for (Doctor d : doctors) doctorBox.addItem(d);
        } catch (Exception e) {
            System.out.println("Error loading doctors");
        }

        panel.add(new JLabel("Select Patient:")); panel.add(patientBox);
        panel.add(new JLabel("Select Doctor:")); panel.add(doctorBox);
        panel.add(new JLabel("Date & Time (YYYY-MM-DD HH:MM:SS):")); panel.add(dateField);
        panel.add(new JLabel("")); panel.add(btnBook);

        btnBook.addActionListener(e -> {
            try {
                Patient selectedP = (Patient) patientBox.getSelectedItem();
                Doctor selectedD = (Doctor) doctorBox.getSelectedItem();
                if (selectedP != null && selectedD != null) {
                    dao.bookAppointment(selectedP.getId(), selectedD.getId(), dateField.getText().trim());
                    JOptionPane.showMessageDialog(this, "Appointment booked successfully.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        return panel;
    }

    private JPanel buildViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JButton btnRefresh = new JButton("Load Appointments");

        btnRefresh.addActionListener(e -> {
            try {
                displayArea.setText(dao.getAppointmentsDisplay());
            } catch (Exception ex) {
                displayArea.setText("Error loading appointments.");
            }
        });

        panel.add(btnRefresh, BorderLayout.NORTH);
        panel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        return panel;
    }
}