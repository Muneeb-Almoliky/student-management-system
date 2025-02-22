package school.management.system.controller;

import school.management.system.model.GradeDAO;
import school.management.system.view.GradeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GradeController {
    private GradeView view;
    private GradeDAO model;

    public GradeController(GradeView view, GradeDAO model) {
        this.view = view;
        this.model = model;

        initialize();
        this.view.getCourseComboBox().addActionListener((e) -> loadStudents());
        this.view.getAssignGradeButton().addActionListener((e) -> assignGrade());
        this.view.getBackButton().addActionListener((e) -> {
            this.view.dispose();
            this.view.mainFrame.setVisible(true);
        });
    }

    private void initialize() {
        try {
            // Load courses into ComboBox
            for (String course : model.getCourses()) {
                view.getCourseComboBox().addItem(course);
            }
            
            loadStudents();

            // Load grades into the table
            loadGradesToTable();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }


    private void loadStudents() {
        try {
            view.getStudentComboBox().removeAllItems();
            String selectedCourse = (String) view.getCourseComboBox().getSelectedItem();
            for (String student : model.getStudentsByCourse(selectedCourse)) {
                view.getStudentComboBox().addItem(student);
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void loadGradesToTable() {
        try {
            view.getTableModel().setRowCount(0); // Clear table
            for (Object[] grade : model.getGrades()) {
                view.getTableModel().addRow(grade);
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void assignGrade() {
        try {
            String student = (String) view.getStudentComboBox().getSelectedItem();
            String course = (String) view.getCourseComboBox().getSelectedItem();
            int grade = Integer.parseInt(view.getGradeField().getText());

            if (student == null || course == null) {
                JOptionPane.showMessageDialog(view, "Please select a student and a course.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            model.updateGrade(student, course, grade);
            loadGradesToTable();
            view.getGradeField().setText("");
        } catch (NumberFormatException e) {
            showError("Please enter a valid grade.");
        }catch(SQLIntegrityConstraintViolationException e){
                 JOptionPane.showMessageDialog(view, "This student have been assigned a grade for this course.");

        }
        catch (SQLException e) {
            
            showError(e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
