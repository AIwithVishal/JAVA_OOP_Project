package model;

public abstract class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String contact;

    public Person(int id, String firstName, String lastName, String contact) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
    }

    // Encapsulation: Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getContact() { return contact; }
}