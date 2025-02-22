package school.management.system.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GradeView extends JFrame {
    private JComboBox<String> studentComboBox;
    private JComboBox<String> courseComboBox;
    private JTextField gradeField;
    private JButton assignGradeButton, backButton;
    private JTable gradeTable;
    private DefaultTableModel tableModel;
    
    public JFrame mainFrame;

    public GradeView(JFrame mainFrame) {
        
        this.mainFrame = mainFrame;
        
        setTitle("Grade Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        
        inputPanel.add(new JLabel("Select Course:"));
        courseComboBox = new JComboBox<>();
        inputPanel.add(courseComboBox);
        
        inputPanel.add(new JLabel("Select Student:"));
        studentComboBox = new JComboBox<>();
        inputPanel.add(studentComboBox);

        

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        assignGradeButton = new JButton("Assign Grade");
        backButton = new JButton("Back to Main");
        buttonPanel.add(assignGradeButton);
        buttonPanel.add(backButton);

        // Grade Table
        tableModel = new DefaultTableModel(new Object[]{"Student", "Course", "Grade"}, 0);
        gradeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(gradeTable);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(tableScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public JComboBox<String> getStudentComboBox() {
        return studentComboBox;
    }

    public JComboBox<String> getCourseComboBox() {
        return courseComboBox;
    }

    public JTextField getGradeField() {
        return gradeField;
    }

    public JButton getAssignGradeButton() {
        return assignGradeButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getGradeTable() {
        return gradeTable;
    }
}
