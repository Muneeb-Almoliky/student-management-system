package school.management.system.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CourseView extends JFrame {
    private JTextField courseIdField, courseNameField;
    private JComboBox<String> teacherComboBox;
    private JButton addButton, backButton;
    private JTable courseTable;
    private DefaultTableModel tableModel;
    
    public JFrame mainFrame;

    public CourseView(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        setTitle("Course Management");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Course ID:"));
        courseIdField = new JTextField();
        inputPanel.add(courseIdField);

        inputPanel.add(new JLabel("Course Name:"));
        courseNameField = new JTextField();
        inputPanel.add(courseNameField);

        inputPanel.add(new JLabel("Select Teacher:"));
        teacherComboBox = new JComboBox<>();
        inputPanel.add(teacherComboBox);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Course");
        backButton = new JButton("Back to Main");
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        // Table for displaying courses
        tableModel = new DefaultTableModel(new Object[]{"Course ID", "Course Name", "Teacher"}, 0);
        courseTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(courseTable);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(tableScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public JTextField getCourseIdField() {
        return courseIdField;
    }

    public JTextField getCourseNameField() {
        return courseNameField;
    }

    public JComboBox<String> getTeacherComboBox() {
        return teacherComboBox;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getCourseTable() {
        return courseTable;
    }
}
