package school.management.system.controller;

import school.management.system.model.CourseDAO;
import school.management.system.view.CourseView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class CourseController {
    private CourseView view;
    private CourseDAO model;

    public CourseController(CourseView view, CourseDAO model) {
        this.view = view;
        this.model = model;

        initialize();
        this.view.getAddButton().addActionListener((e) ->  addCourse());
        this.view.getBackButton().addActionListener((e) -> {
            this.view.dispose();
            this.view.mainFrame.setVisible(true);
                });
    }

    private void initialize() {
        try {
            // Load teachers into ComboBox
            for (String teacher : model.getTeachers()) {
                view.getTeacherComboBox().addItem(teacher);
            }

            // Load courses into table
            loadCoursesToTable();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void addCourse() {
        try {
            int id = Integer.parseInt(view.getCourseIdField().getText());
            String name = view.getCourseNameField().getText();
            String teacherName = (String) view.getTeacherComboBox().getSelectedItem();

            model.addCourse(id, name, teacherName);
            loadCoursesToTable();
            clearForm();
        } catch (Exception e) {
            showError("Error adding course: " + e.getMessage());
        }
    }

    private void loadCoursesToTable() throws SQLException {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (Object[] course : model.getCourses()) {
            tableModel.addRow(course);
        }
    }

    private void clearForm() {
        view.getCourseIdField().setText("");
        view.getCourseNameField().setText("");
        view.getTeacherComboBox().setSelectedIndex(0);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
