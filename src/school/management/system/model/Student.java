/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school.management.system.model;

/**
 *
 * @author pc
 */
// Model: Student.java

import java.util.ArrayList;

public class Student {
    private int id;
    private int age;
    private String name;
    private String gender;
    private String birthPlace;
    private String birthDate;

    public Student(int id, int age, String name, String gender, String birthPlace, String birthDate) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
    }

    public int getId() { return id; }
    public int getAge() { return age; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getBirthPlace() { return birthPlace; }
    public String getBirthDate() { return birthDate; }

    public void setAge(int age) { this.age = age; }
    public void setName(String name) { this.name = name; }
    public void setGender(String gender) { this.gender = gender; }
    public void setBirthPlace(String birthPlace) { this.birthPlace = birthPlace; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
}