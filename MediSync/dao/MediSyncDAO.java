package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Doctor;
import model.Patient;

public class MediSyncDAO implements IDatabaseOperations {
    private Connection con;

    // We now pass the username and password into the constructor
    public MediSyncDAO(String dbUser, String dbPass) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Use the variables instead of the hardcoded string
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/medisync", dbUser, dbPass);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database Driver not found!");
        }
    }

    @Override
    public void addPatient(String fName, String lName, String contact) throws SQLException {
        String query = "INSERT INTO patients (patient_id, first_name, last_name, contact) VALUES ((SELECT COALESCE(MAX(patient_id), 0) + 1 FROM patients p), ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, fName);
            pst.setString(2, lName);
            pst.setString(3, contact);
            pst.executeUpdate();
        }
    }

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>(); // Collections requirement
        String query = "SELECT patient_id, first_name, last_name, contact FROM patients";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                patients.add(new Patient(rs.getInt("patient_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("contact")));
            }
        }
        return patients;
    }

    @Override
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT doctor_id, first_name, last_name, contact, specialization FROM doctors";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                doctors.add(new Doctor(rs.getInt("doctor_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("contact"), rs.getString("specialization")));
            }
        }
        return doctors;
    }

    @Override
    public void bookAppointment(int patientId, int doctorId, String dateTime) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND date_time = ?";
        try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, doctorId);
            checkStmt.setString(2, dateTime);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                throw new SQLException("Rejected. This doctor already has an appointment at that time.");
            }
        }

        String query = "INSERT INTO appointments (apointment_id, patient_id, doctor_id, date_time, status) VALUES ((SELECT COALESCE(MAX(apointment_id), 0) + 1 FROM appointments a), ?, ?, ?, 'Scheduled')";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, patientId);
            pst.setInt(2, doctorId);
            pst.setString(3, dateTime);
            pst.executeUpdate();
        }
    }

    @Override
    public String getAppointmentsDisplay() throws SQLException {
        StringBuilder sb = new StringBuilder();
        String query = "SELECT a.apointment_id, CONCAT(p.first_name,' ',p.last_name) AS patient, CONCAT(d.first_name,' ',d.last_name) AS doctor, a.date_time, a.status FROM appointments a JOIN patients p ON a.patient_id = p.patient_id JOIN doctors d ON a.doctor_id = d.doctor_id ORDER BY a.date_time";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("apointment_id"))
                  .append(" | Patient: ").append(rs.getString("patient"))
                  .append(" | Doctor: ").append(rs.getString("doctor"))
                  .append(" | Time: ").append(rs.getString("date_time"))
                  .append(" | Status: ").append(rs.getString("status")).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString() : "No appointments found.";
    }
}