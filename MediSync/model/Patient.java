package model;

public class Patient extends Person {
    public Patient(int id, String firstName, String lastName, String contact) {
        super(id, firstName, lastName, contact);
    }

    // Polymorphism: Overriding toString for the UI Dropdowns
    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}