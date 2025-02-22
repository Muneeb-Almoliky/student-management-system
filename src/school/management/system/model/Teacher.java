/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school.management.system.model;

/**
 *
 * @author pc
 */
import java.util.ArrayList;

public class Teacher {
    private int id;
    private String name;
    private String specialty;
    private ArrayList<Course> courses;

    public Teacher(int id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.courses = new ArrayList<>();
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public ArrayList<Course> getCourses() { return courses; }

    public void addCourse(Course course) {
        courses.add(course);
    }

    void setName(String text) {
        this.name=text; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setSpecialty(String text) {
        this.specialty = text; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
