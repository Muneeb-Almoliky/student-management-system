/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school.management.system.view;

/**
 *
 * @author ameen
 */
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import school.management.system.model.DatabaseConnection;

/**
 *
 * @author Mohammed Hidarah
 */
public class StudentView extends JFrame {

    private JTextField idField, ageField, nameField, searchField;
    private JRadioButton maleButton, femaleButton;
    private JButton addButton, editButton, deleteButton, enrollButton, backButton, showAddDialogBtn, showEditDialogBtn,
            showEnrollDialogBtn, viewCoursesAndGradesBtn;
    private JComboBox<String> birthPlaceComboBox;
    private JSpinner birthDateSpinner;
    private JCheckBox isActiveCheckBox;
    private JTable studentTable, enrollInCoursesTable;
    private DefaultTableModel studentTableModel, enrollInCoursesTableModel;
    private int selectedRow = -1;
    private JFrame mainFrame;

    private Connection connection;

    public StudentView(JFrame maiFrame) throws SQLException {
        this.mainFrame = maiFrame;
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        enrollButton = new JButton("Enroll");
        backButton = new JButton("Back To Main");
        showEditDialogBtn = new JButton("Edit");
        viewCoursesAndGradesBtn = new JButton("View Courses and Grades");
        showEnrollDialogBtn = new JButton("Enroll in Course");

        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon plusIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/images/addicon.jpg"));
        Image scaledImage = plusIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);

        showAddDialogBtn = new JButton("Add A Student", new ImageIcon(scaledImage));
        showAddDialogBtn.setBounds(40, 10, 5, 10);
        JPanel addButtonPanel = new JPanel();
        addButtonPanel.setSize(200, 200);
        addButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        showAddDialogBtn.setFocusPainted(false); // Prevents focus border from being painted

        addButtonPanel.add(showAddDialogBtn);

        searchField = new JTextField(10);
        JLabel searchLabel = new JLabel("Search by name: ");

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        Border border = BorderFactory.createTitledBorder("");
        searchPanel.setBorder(border);

        // Create table
        studentTableModel = new DefaultTableModel(
                new String[] { "ID", "Age", "Name", "Gender", "Birth Place", "Birth Date", "Actions" }, 0);
        studentTable = new JTable(studentTableModel);
        studentTable.setDefaultRenderer(Object.class, new CustomCellRenderer());

        studentTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        studentTable.setRowHeight(40);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // JButton addButton = new JButton("Add Student");

        showEnrollDialogBtn.addActionListener((e) -> {
            int selectedStudent = studentTable.getSelectedRow();
            if (selectedStudent != -1) {

                try {
                    showEnrollCoursesPanel();
                } catch (SQLException ex) {
                    Logger.getLogger(StudentView.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "You Have To Select A Student");
            }
        });

        buttonPanel.add(showEnrollDialogBtn);
        buttonPanel.add(viewCoursesAndGradesBtn);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        showAddDialogBtn.addActionListener((e) -> showAddDialog());
        showEditDialogBtn.addActionListener(e -> showEditDialog());

        backButton.addActionListener(e -> {
            dispose();
            mainFrame.setVisible(true);
        });

        JPanel panel = new JPanel(new BorderLayout());

        panel.add(searchPanel, BorderLayout.EAST);
        panel.add(addButtonPanel, BorderLayout.WEST);

        add(panel, BorderLayout.NORTH);
        setVisible(true);
    }

    public String getIdFieldText() {
        return idField.getText();
    }

    public String getNameFieldText() {
        return nameField.getText();
    }

    public String getAgeFieldText() {
        return ageField.getText();
    }

    public String getSelectedGender() {
        return maleButton.isSelected() ? "Male" : "Female";
    }

    public String getSelectedBirtPlace() {
        return (String) birthPlaceComboBox.getSelectedItem();
    }

    public String getBirthDate() {
        return ((JSpinner.DateEditor) birthDateSpinner.getEditor()).getFormat().format(birthDateSpinner.getValue());
    }

    public JTable getStudentTable() {
        return studentTable;
    }

    public JTable getEnrollInCoursesTable() {
        return enrollInCoursesTable;
    }

    public int getSelectedStudentRow() {
        return studentTable.getSelectedRow();
    }

    public String getSelectedCourseName() {
        int selectedCourse = enrollInCoursesTable.getSelectedRow();
        return (String) enrollInCoursesTableModel.getValueAt(selectedCourse, 0);
    }

    public JButton getViewCoursesAndGradesBtn() {
        return viewCoursesAndGradesBtn;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getEnrollButton() {
        return enrollButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void showCoursesAndGradesDialog(ArrayList<Object[]> coursesAndGrades) {
        // Create a dialog
        JDialog dialog = new JDialog(this, "Courses and Grades", true);
        dialog.setSize(250, 300);
        dialog.setLocationRelativeTo(this);

        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "Course", "Grade" }, 0);

        // Populate the table model with the data
        for (Object[] row : coursesAndGrades) {
            tableModel.addRow(row);
        }

        // Create a table with the model
        JTable table = new JTable(tableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Set up the layout for the dialog
        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Add a close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Display the dialog
        dialog.setVisible(true);
    }

    private void showAddDialog() {

        JDialog addStudent = new JDialog(this, "Add Student", true);
        addStudent.setSize(330, 370);
        addStudent.setLocationRelativeTo(this);
        addStudent.setBackground(Color.black);
        addStudent.setForeground(Color.white);
        addStudent.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel();

        formPanel.setForeground(Color.white);

        formPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Gender:"));
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        formPanel.add(genderPanel);

        formPanel.add(new JLabel("Birth Place:"));
        birthPlaceComboBox = new JComboBox<>(new String[] { "City A", "City B", "City C" });
        formPanel.add(birthPlaceComboBox);

        formPanel.add(new JLabel("Birth Date:"));
        birthDateSpinner = new JSpinner(new SpinnerDateModel());
        birthDateSpinner.setEditor(new JSpinner.DateEditor(birthDateSpinner, "dd/MM/yyyy"));
        formPanel.add(birthDateSpinner);

        isActiveCheckBox = new JCheckBox("Active");
        formPanel.add(isActiveCheckBox);
        formPanel.add(new JLabel()); // Empty space

        JPanel paddedPanel = new JPanel();
        paddedPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        paddedPanel.setLayout(new BorderLayout());
        formPanel.setLayout(new GridLayout(7, 2, 0, 5));
        formPanel.setBackground(Color.white);
        paddedPanel.add(formPanel);
        paddedPanel.setBackground(Color.white);

        addStudent.add(paddedPanel, BorderLayout.NORTH);
        addStudent.add(addButton, BorderLayout.SOUTH);
        addStudent.setVisible(true);

    }

    private void showEditDialog() {
        JDialog addStudent = new JDialog(this, "Add Student", true);
        addStudent.setSize(330, 350);
        addStudent.setLocationRelativeTo(this);

        addStudent.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel();

        formPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        idField.setEditable(false);
        formPanel.add(idField);

        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Gender:"));
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        formPanel.add(genderPanel);

        formPanel.add(new JLabel("Birth Place:"));
        birthPlaceComboBox = new JComboBox<>(new String[] { "City A", "City B", "City C" });
        formPanel.add(birthPlaceComboBox);

        formPanel.add(new JLabel("Birth Date:"));
        birthDateSpinner = new JSpinner(new SpinnerDateModel());
        birthDateSpinner.setEditor(new JSpinner.DateEditor(birthDateSpinner, "dd/MM/yyyy"));
        formPanel.add(birthDateSpinner);

        isActiveCheckBox = new JCheckBox("Active");
        formPanel.add(isActiveCheckBox);
        formPanel.add(new JLabel()); // Empty space

        JPanel paddedPanel = new JPanel();
        paddedPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        paddedPanel.setLayout(new BorderLayout());
        formPanel.setLayout(new GridLayout(7, 2));
        paddedPanel.add(formPanel);

        addStudent.add(paddedPanel, BorderLayout.NORTH);
        addStudent.add(editButton, BorderLayout.SOUTH);
        loadSelectedStudent();
        addStudent.setVisible(true);

    }

    private void loadSelectedStudent() {
        selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {

            // Student student = students.get(selectedRow);
            idField.setText(studentTable.getValueAt(selectedRow, 0).toString());
            ageField.setText(studentTable.getValueAt(selectedRow, 1).toString());
            nameField.setText(studentTable.getValueAt(selectedRow, 2).toString());
            if (studentTable.getValueAt(selectedRow, 3).toString().equals("Male")) {
                maleButton.setSelected(true);
            } else {
                femaleButton.setSelected(true);
            }
            birthPlaceComboBox.setSelectedItem(studentTable.getValueAt(selectedRow, 5).toString());
            // birthDateSpinner.setValue(student.getBirthDate());
        } else {
            JOptionPane.showMessageDialog(this, "Hi");
        }
    }

    private void clearForm() {
        idField.setText("");
        ageField.setText("");
        nameField.setText("");
        maleButton.setSelected(false);
        femaleButton.setSelected(false);
        birthPlaceComboBox.setSelectedIndex(0);
        birthDateSpinner.setValue(new java.util.Date());
        isActiveCheckBox.setSelected(false);
    }

    private void showEnrollCoursesPanel() throws SQLException {

        JDialog enrollInCoursesDialog = new JDialog();
        enrollInCoursesDialog.setTitle("Enroll in a course");

        enrollInCoursesDialog.setSize(200, 300);
        enrollInCoursesDialog.setLocationRelativeTo(null);

        int selectedStudent = studentTable.getSelectedRow();
        if (selectedStudent == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student first.");
            return;
        }

        // Create a dialog or new panel to show available courses
        // JPanel enrollPanel = new JPanel(new GridLayout(0, 1));
        enrollInCoursesTableModel = new DefaultTableModel(new Object[] { "Course" }, 0);
        enrollInCoursesTable = new JTable(enrollInCoursesTableModel);
        // enrollInCoursesTable.setDefaultRenderer(Object.class, new
        // CustomCellRenderer());

        JScrollPane enrollInCoursesScrollPane = new JScrollPane(enrollInCoursesTable);
        enrollInCoursesScrollPane.setPreferredSize(new Dimension(150, 230));

        // enrollButton.setPreferredSize(new Dimension(20, 30));

        // Add each course to the enrollment panel
        connection = DatabaseConnection.getConnection();
        String query = "SELECT name from course";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {

            final String courseName = rs.getString("name");
            enrollInCoursesTableModel.addRow(new Object[] { courseName });
        }

        enrollInCoursesDialog.add(enrollInCoursesScrollPane, BorderLayout.NORTH);
        enrollInCoursesDialog.add(enrollButton, BorderLayout.CENTER);
        enrollInCoursesDialog.setVisible(true);

        // Show the enrollment panel in a dialog
        // JOptionPane.showMessageDialog(this, enrollInCoursesScrollPane, "Enroll in a
        // Course", JOptionPane.PLAIN_MESSAGE);
    }

    public void fillButtons() {
        studentTable.getColumnModel().getColumn(6).setCellRenderer(new StudentView.ButtonRenderer());
        studentTable.getColumnModel().getColumn(6).setCellEditor(new StudentView.ButtonEditor(new JCheckBox()));
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {

        public ButtonRenderer() {
            setLayout(new FlowLayout());
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int col) {
            removeAll(); // Clear previous buttons

            showEditDialogBtn = new RoundedButton("Edit", 10);
            showEditDialogBtn.setBackground(new Color(167, 255, 139));
            deleteButton = new RoundedButton("Delete", 10);
            deleteButton.setBackground(new Color(234, 26, 58));

            // Add action listeners for buttons
            showEditDialogBtn.addActionListener(e -> {
                showEditDialog();
            });

            if (row % 2 == 0) {
                this.setBackground(new Color(255, 247, 229));
            } else {
                this.setBackground(Color.WHITE);
            }
            if (isSelected) {
                this.setBackground(new Color(48, 158, 248));
                this.setForeground(Color.WHITE);
            } else {
                this.setForeground(Color.BLACK); // Default text color
            }

            add(showEditDialogBtn);
            add(deleteButton);
            return this;
        }
    }

    class CustomCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row % 2 == 0) {
                cell.setBackground(new Color(255, 247, 229));
            } else {
                cell.setBackground(Color.WHITE);
            }
            if (isSelected) {
                cell.setBackground(new Color(48, 158, 248));
                cell.setForeground(Color.WHITE);
            } else {
                cell.setForeground(Color.BLACK); // Default text color
            }

            return cell;
        }
    }

    // Button Editor
    class ButtonEditor extends DefaultCellEditor {

        private JPanel panel;

        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout());
            panel.setSize(200, 200);

            showEditDialogBtn = new JButton("Edit");
            showEditDialogBtn.setSize(10, 10);
            deleteButton = new JButton("Delete");
            deleteButton.setSize(10, 10);

            // Add action listeners for buttons
            showEditDialogBtn.addActionListener(e -> {
                showEditDialog();
                fireEditingStopped(); // Stop editing
            });

            panel.add(showEditDialogBtn);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int col) {
            this.row = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

    class RoundedButton extends JButton {

        private int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false); // Make the button transparent
            setFocusPainted(false); // Remove focus border
            setBorderPainted(false); // Remove border
            setOpaque(false); // Make button background transparent
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Set background color
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            // Call the superclass method to paint the button text and icon
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }

}
