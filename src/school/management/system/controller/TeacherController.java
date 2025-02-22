/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school.management.system.controller;

/**
 *
 * @author pc
 */

import school.management.system.controller.TeacherController;
import school.management.system.model.TeacherDAO;
import school.management.system.model.Teacher;
import school.management.system.view.TeacherView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class TeacherController {
    private TeacherDAO teacherDAO;
    private TeacherView teacherView;

    public TeacherController(TeacherDAO teacherDAO, TeacherView teacherView) {
        this.teacherDAO = teacherDAO;
        this.teacherView = teacherView;

        // Set up action listeners
        this.teacherView.getTeacherTable().getSelectionModel().addListSelectionListener((e) -> loadSelectedTeacher());
        this.teacherView.getAddButton().addActionListener((e) -> addTeacher());
        this.teacherView.getUpdateButton().addActionListener((e) -> editTeacher());
        this.teacherView.getDeleteButton().addActionListener((e) -> deleteTeacher());
        this.teacherView.getBackButton().addActionListener(e -> {
            teacherView.dispose();
            teacherView.mainFrame.setVisible(true);
        });

        loadTeachers();
    }

    private void loadTeachers() {
        try {
            List<Teacher> teachers = teacherDAO.getAllTeachers();
            DefaultTableModel tableModel = teacherView.getTableModel();
            tableModel.setRowCount(0); // Clear table
            for (Teacher teacher : teachers) {
                tableModel.addRow(new Object[]{teacher.getId(), teacher.getName(), teacher.getSpecialty()});
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(teacherView, "Error loading teachers.");
        }
    }
    
    private void loadSelectedTeacher(){
        int selectedTeacher = this.teacherView.getTeacherTable().getSelectedRow();
       
        if(selectedTeacher == -1){
            return;
        }
        else {
        DefaultTableModel teacherModel = this.teacherView.getTableModel();
        
        this.teacherView.getTeacherIdField().setText(String.valueOf(teacherModel.getValueAt(selectedTeacher, 0)) );
        this.teacherView.getTeacherNameField().setText(String.valueOf(teacherModel.getValueAt(selectedTeacher, 1)) );
        this.teacherView.getTeacherSpecialtyField().setText(String.valueOf(teacherModel.getValueAt(selectedTeacher, 2)) );
        }
    }
    
    void addTeacher(){
        try {
                int id = Integer.parseInt(teacherView.getTeacherIdField().getText());
                String name = teacherView.getTeacherNameField().getText();
                String specialty = teacherView.getTeacherSpecialtyField().getText();
                teacherDAO.addTeacher(new Teacher(id, name, specialty));
                loadTeachers();
                this.teacherView.clearForm();
                JOptionPane.showMessageDialog(teacherView, "Teacher added successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(teacherView, "Error adding teacher.");
            }
    }
    
    
    private void editTeacher(){
                int selectedTeacher = this.teacherView.getTeacherTable().getSelectedRow();
        
            if(selectedTeacher != -1){
                try {
                int id = Integer.parseInt(String.valueOf(teacherView.getTeacherTable().getValueAt(selectedTeacher, 0)) );
                String name = teacherView.getTeacherNameField().getText();
                String specialty = teacherView.getTeacherSpecialtyField().getText();
                teacherDAO.updateTeacher(new Teacher(id, name, specialty));
                loadTeachers();
                this.teacherView.clearForm();
                JOptionPane.showMessageDialog(teacherView, "Teacher updated successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(teacherView, "Error updating teacher.");
            }
            }else{
                JOptionPane.showMessageDialog(teacherView, "You have to select a teacher first!");
            }
            
    }
    
   private void deleteTeacher() {
    int selectedTeacher = this.teacherView.getTeacherTable().getSelectedRow();
    if (selectedTeacher == -1) {
        JOptionPane.showMessageDialog(teacherView, "Please select a teacher to delete.");
    } else {
        try {
            int id = (Integer) teacherView.getTeacherTable().getValueAt(selectedTeacher, 0);
            teacherDAO.deleteTeacher(id);
            this.teacherView.clearForm();
            loadTeachers();
            JOptionPane.showMessageDialog(teacherView, "Teacher deleted successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(teacherView, "Invalid teacher ID.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(teacherView, "Error deleting teacher: " + e.getMessage());
            // Log the exception for debugging
            e.printStackTrace();
        }
    }
}

    

    

   
}

