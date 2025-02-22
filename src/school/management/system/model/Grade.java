/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school.management.system.model;

/**
 *
 * @author pc
 */
public class Grade {
    private int studentId;
    private int courseId;
    private double grade;

    public Grade(int studentId, int courseId, double grade) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
    }
    
    public Grade(double grade){
        this.grade = grade;
    }

    // Getters
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public double getGrade() { return grade; }

    public void setScore(double grade) {
        this.grade = grade;
    }
}
