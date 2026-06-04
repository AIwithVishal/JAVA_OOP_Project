package model;

public class Doctor extends Person {
    private String specialization;

    public Doctor(int id, String firstName, String lastName, String contact, String specialization) {
        super(id, firstName, lastName, contact);
        this.specialization = specialization;
    }

    public String getSpecialization() { return specialization; }

    @Override
    public String toString() {
        return "Dr. " + getFirstName() + " " + getLastName() + " (" + specialization + ")";
    }
}