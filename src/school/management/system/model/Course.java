/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school.management.system.model;


/**
 *
 * @author pc
 */
public class Course {
    private int id;
    private String name;
//    private Teacher teacher;
    private int teacherId;
    public Course(int id, String name, int teacherId) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }
    public int getId(){
        return id;
    }

    public int getTeacherId() {
        return teacherId;
    }

}