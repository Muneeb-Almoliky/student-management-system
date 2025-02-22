
package school.management.system.controller;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import school.management.system.model.StudentDAO;
import school.management.system.model.Student;
import school.management.system.view.StudentView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class StudentController {
    private StudentDAO model;
    private StudentView view;

    public StudentController(StudentDAO model, StudentView view) {
        this.model = model;
        this.view = view;

        loadStudents();

        // Add action listeners
        view.getAddButton().addActionListener(e -> addStudent());
        view.getEditButton().addActionListener(e -> editStudent());
        view.getDeleteButton().addActionListener(e -> deleteStudent());
        view.getEnrollButton().addActionListener(e -> enrollStudentInCourse());
        view.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            

            @Override
            public void insertUpdate(DocumentEvent e) {
                filterStudents();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterStudents();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterStudents();
            }
        });
        view.getViewCoursesAndGradesBtn().addActionListener(e -> showStudentCoursesAndGrades());
    }

    private void loadStudents() {
        try {
            List<Student> students = model.getAllStudents();
            updateStudentTable(students);
        } catch (SQLException e) {
            showError("Error loading students: " + e.getMessage());
        }
    }

    private void filterStudents() {
        String query = view.getSearchField().getText().trim();
        try {
            List<Student> students = model.searchStudents(query);
            updateStudentTable(students);
        } catch (SQLException e) {
            showError("Error filtering students: " + e.getMessage());
        }
    }

    private void updateStudentTable(List<Student> students) {
        DefaultTableModel tableModel =(DefaultTableModel) view.getStudentTable().getModel();
        tableModel.setRowCount(0); // Clear table
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                student.getId(), student.getAge(), student.getName(),
                student.getGender(), student.getBirthPlace(), student.getBirthDate()
            });
            view.fillButtons();
        }
    }

    private void showStudentCoursesAndGrades() {
        int selectedRow = view.getSelectedStudentRow();
        if (selectedRow == -1) {
            showError("Please select a student first.");
            return;
        }

        int studentId = (int) view.getStudentTable().getValueAt(selectedRow, 0);
        try {
            ArrayList<Object[]> coursesAndGrades = model.getStudentCoursesAndGrades(studentId);
            view.showCoursesAndGradesDialog(coursesAndGrades);
        } catch (SQLException e) {
            showError("Error loading courses and grades: " + e.getMessage());
        }
    }

    private void enrollStudentInCourse() {
        int selectedStudent = view.getSelectedStudentRow();
        if (selectedStudent == -1) {
            showError("Please select a student first.");
            return;
        }

        String courseName = view.getSelectedCourseName();
        int studentId = (int) view.getStudentTable().getValueAt(selectedStudent, 0);

        try {
            model.enrollStudentInCourse(courseName, studentId);
            showMessage("Student enrolled successfully.");
        }catch(SQLIntegrityConstraintViolationException e){
                 JOptionPane.showMessageDialog(view, "This student has already been registered in this course!");

        }
        catch (SQLException e) {
            showError("Error enrolling student: " + e.getMessage());
        }
    }

        private void addStudent() {
        // Example logic for adding a student (hardcoded for simplicity)
        int id = Integer.parseInt(this.view.getIdFieldText());
        int age = Integer.parseInt(this.view.getAgeFieldText());
        String name = this.view.getNameFieldText();
        String gender = this.view.getSelectedGender();
        String birthPlace = this.view.getSelectedBirtPlace();
        String birthDate = this.view.getSelectedBirtPlace();
        
        try {
            Student newStudent = new Student(id, age, name, gender, birthPlace, birthDate);
            model.addStudent(newStudent);
            JOptionPane.showMessageDialog(view, "Student added successfully.");
            loadStudents(); // Refresh the table
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error adding student.");
        }
    }
        private void editStudent(){
        
        int id = Integer.parseInt(this.view.getIdFieldText());
        int age = Integer.parseInt(this.view.getAgeFieldText());
        String name = this.view.getNameFieldText();
        String gender = this.view.getSelectedGender();
        String birthPlace = this.view.getSelectedBirtPlace();
        String birthDate = this.view.getSelectedBirtPlace();
        
        try {
            Student newStudent = new Student(id, age, name, gender, birthPlace, birthDate);
            model.updateStudent(newStudent);
            JOptionPane.showMessageDialog(view, "Student updated successfully.");
            loadStudents(); // Refresh the table
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error updating student.");
        }
        
    }

        private void deleteStudent(){
        int selectedStudent = this.view.getStudentTable().getSelectedRow();
        
        int id = (int) this.view.getStudentTable().getValueAt(selectedStudent, 0);
        
        try {
            this.model.deleteStudent(id);
            loadStudents();
        } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Error Deleting A Student!.");
        }  
        
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(view, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

