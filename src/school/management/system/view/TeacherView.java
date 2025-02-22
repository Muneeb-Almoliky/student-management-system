/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school.management.system.view;

/**
 *
 * @author ameen
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherView extends JFrame {
    private JTable teacherTable;
    private DefaultTableModel tableModel;
    public JFrame mainFrame;
    
    private Connection connection;
    
    private JTextField teacherIdField,teacherNameField, teacherSpecialtyField;
    private JButton addTeacherButton, editTeacherButton, deleteTeacherButton, backButton;


    public TeacherView(JFrame mainFrame) throws SQLException {
        this.mainFrame = mainFrame;
        setupUI();
    }

    private void setupUI() throws SQLException {
        setTitle("Teacher Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI components
        teacherNameField = new JTextField();
        teacherSpecialtyField = new JTextField();
        teacherIdField = new JTextField();

        addTeacherButton = new JButton("Add Teacher");
        editTeacherButton = new JButton("Edit Teacher");
        deleteTeacherButton = new JButton("Delete Teacher");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Back Button
        backButton = new JButton("Back to Main");
        
        panel.add(backButton);


        // Create table for teachers
        teacherTable = new JTable();
        teacherTable.setRowHeight(40);
        tableModel = new DefaultTableModel(new Object[][] {}, new String[] {"ID","Name", "Specialty"});
        teacherTable.setModel(tableModel);
       
        teacherNameField.setPreferredSize(new Dimension(200, 30));
        teacherSpecialtyField.setPreferredSize(new Dimension(200, 30));
        
        // Set layout
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(3,2,0,0));
                inputPanel.setPreferredSize(new Dimension(10,50));
                
        inputPanel.add(new JLabel("ID:")) ;
        inputPanel.add(teacherIdField);
                
        JLabel label = new JLabel("Name:");
        label.setPreferredSize(new Dimension(10,20));
        inputPanel.add(label);
        
//        inputPanel.add(new JLabel("Name:"));
                teacherNameField.setSize(10,20);

        inputPanel.add(teacherNameField);
        
        
        
        inputPanel.add(new JLabel("Specialty:"));
        inputPanel.add(teacherSpecialtyField);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addTeacherButton);
        buttonPanel.add(editTeacherButton);
        buttonPanel.add(deleteTeacherButton);
        buttonPanel.add(panel);
        
        
       
                
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(teacherTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
        
    }
    
    
    public JTextField getTeacherIdField() { return teacherIdField; }
    public JTextField getTeacherNameField() { return teacherNameField; }
    public JTextField getTeacherSpecialtyField() { return teacherSpecialtyField; }
    public JButton getAddButton() { return addTeacherButton; }
    public JButton getUpdateButton() { return editTeacherButton; }
    public JButton getDeleteButton() { return deleteTeacherButton; }
    public JButton getBackButton() { return backButton; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTeacherTable() { return teacherTable; }


    
    public void clearForm(){
        teacherIdField.setText("");
        teacherNameField.setText("");
        teacherSpecialtyField.setText("");
    }
}

    


    
    



