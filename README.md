# MediSync - Clinic Management System

## Purpose
MediSync is a streamlined, desktop-based management system designed for healthcare clinics. It solves the problem of manual appointment tracking by allowing clinic receptionists to easily register new patients, view doctor availability, and schedule appointments without double-booking conflicts.

## Group Members
* Prem Vishal 023-25-0227
* Ali Raza - 023-25-0202
* Muhammad Ehsan - 023-25-0206
* Karan Kumar 023-25-0215

*Program:* BS Artificial Intelligence (Semester 2) - Sukkur IBA University

## Core Modules
* `model`: Contains the entity classes (`Person`, `Patient`, `Doctor`) representing the core data structures.
* `dao`: The Data Access Object layer (`IDatabaseOperations`, `MediSyncDAO`) which isolates all JDBC MySQL logic from the UI.
* `ui`: Contains the Java Swing components (`AppWindow`) for user interaction.

## Key OOP Features Implemented
1. **Inheritance & Abstract Classes:** Created an abstract `Person` class which both `Patient` and `Doctor` inherit from, promoting code reuse.
2. **Encapsulation:** All model fields are `private` and accessed strictly via public getter/setter methods.
3. **Interfaces & Polymorphism:** Utilized an `IDatabaseOperations` interface. The `MediSyncDAO` implements this, meaning the UI only cares about the *contract*, not the SQL implementation. Also overrode the `toString()` method in models to neatly display objects in GUI dropdowns.
4. **Collections:** Fetched database records are stored in `ArrayList<Patient>` and `ArrayList<Doctor>` before being passed to the View layer.
5. **Exception Handling:** Applied standard `try-catch` blocks and `throws SQLException` to gracefully handle database connection errors and duplicate booking attempts.

## How to Run
1. **Prerequisites:** JDK 8+ installed, MySQL Server running.
2. **Database Setup:** * Open MySQL Workbench and execute the provided `medisync.sql` script to create the database and tables.
---ENTER Your local Mysql root password to proceed.
3. **Compile and Run:** * Compile the project ensuring the MySQL JDBC Connector `.jar` is in your build path.
   * Run `Main.java` to launch the application.

   -- VIDEO LINK : 

   https://drive.google.com/file/d/1pA6D4nSl2ztMRyFkkHI1n7jR2heFQzfT/view?usp=drive_link
 

 --- Github Repo Link : 
 https://github.com/AIwithVishal/JAVA_OOP_Project

 
