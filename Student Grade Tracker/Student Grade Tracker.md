# Student Grade Tracker

A GUI-based Java application developed using **Java Swing** and **Object-Oriented Programming (OOP)** principles. This project allows users to manage student records, store grades, and generate performance statistics such as average, highest, and lowest scores.

## Features

* Add student records
* Store student grades dynamically using ArrayList
* Display all student records
* Calculate average grade
* Find highest grade
* Find lowest grade
* Interactive GUI using Java Swing
* Generate summary reports

---

## Technologies Used

* Java
* Java Swing
* Object-Oriented Programming (OOP)
* ArrayList
* Event Handling

---

## OOP Concepts Implemented

### 1. Classes and Objects

The project is built around multiple classes such as:

* `Student`
* `GradeTracker`
* `GradeTrackerGUI`

Objects of these classes are created to manage data and application functionality.

### 2. Encapsulation

Student attributes are declared as private:

```java
private String name;
private int grade;
```

Data is accessed through getter methods.

### 3. Constructors

Constructors are used to initialize objects when they are created.

### 4. Method Overriding

The `toString()` method is overridden to provide a customized display of student information.

### 5. Object Composition

The `GradeTracker` class manages multiple `Student` objects using an ArrayList.

---

## Java Concepts Used

### ArrayList

Used to dynamically store and manage student records.

### Loops

Used to traverse student records and perform calculations.

### Conditional Statements

Used to determine highest and lowest grades.

### Exception Handling

Used to validate user input and prevent runtime errors.

### String Manipulation

Used for displaying student information and reports.

---

## GUI Components Used

### JFrame

Creates the main application window.

### JLabel

Displays labels such as Name and Grade.

### JTextField

Accepts user input.

### JButton

Triggers actions such as adding students and generating reports.

### JTextArea

Displays student records.

### JScrollPane

Provides scrolling functionality for the text area.

### JOptionPane

Displays report summaries and validation messages.

---

## Event Handling

The project uses:

* ActionListener
* ActionEvent

These are used to respond to button clicks and user interactions.

---

## Project Structure

```text
Student.java
GradeTracker.java
GradeTrackerGUI.java
Main.java
```

---

## Learning Outcomes

Through this project, I gained hands-on experience with:

* Object-Oriented Programming in Java
* GUI Development using Swing
* Event-Driven Programming
* Data Management using ArrayList
* Application Design and Code Organization
* Integrating Backend Logic with User Interfaces

---

## Author

Prem Vishal

Artificial Intelligence Student | Java & OOP Enthusiast
