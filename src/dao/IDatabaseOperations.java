package dao;
import model.Patient;
import model.Doctor;
import java.util.List;

public interface IDatabaseOperations {
    void addPatient(String fName, String lName, String contact) throws Exception;
    List<Patient> getAllPatients() throws Exception;
    List<Doctor> getAllDoctors() throws Exception;
    void bookAppointment(int patientId, int doctorId, String dateTime) throws Exception;
    String getAppointmentsDisplay() throws Exception;
}