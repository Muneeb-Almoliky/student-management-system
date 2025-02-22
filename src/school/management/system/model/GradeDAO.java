package school.management.system.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class GradeDAO {
    private Connection connection;

    public GradeDAO() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    public List<String> getCourses() throws SQLException {
        List<String> courses = new ArrayList<>();
        String query = "SELECT name FROM course";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            courses.add(rs.getString("name"));
        }
        return courses;
    }

    public List<String> getStudentsByCourse(String courseName) throws SQLException {
        List<String> students = new ArrayList<>();
        String query = "SELECT s.name " +
                       "FROM student s " +
                       "INNER JOIN student_course sc ON s.id = sc.student_id " +
                       "INNER JOIN course c ON sc.course_id = c.id " +
                       "WHERE c.name = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, courseName);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            students.add(rs.getString("name"));
        }
        return students;
    }

    public List<Object[]> getGrades() throws SQLException {
        List<Object[]> grades = new ArrayList<>();
        String query = "SELECT s.name AS student_name, c.name AS course_name, sc.grade AS grade " +
                       "FROM student_course sc " +
                       "INNER JOIN student s ON sc.student_id = s.id " +
                       "INNER JOIN course c ON sc.course_id = c.id";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            grades.add(new Object[]{
                rs.getString("student_name"),
                rs.getString("course_name"),
                rs.getInt("grade")
            });
        }
        return grades;
    }

    public void assignGrade(String studentName, String courseName, int grade) throws SQLException {
        String query = "INSERT INTO student_course(student_id, course_id, grade) " +
                       "SELECT s.id, c.id, ? FROM student s, course c " +
                       "WHERE s.name = ? AND c.name = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, grade);
        stmt.setString(2, studentName);
        stmt.setString(3, courseName);
        stmt.executeUpdate();
    }
    
    public void updateGrade(String studentName, String courseName, int newGrade) throws SQLException {
    String query = "UPDATE student_course sc " +
                   "JOIN student s ON sc.student_id = s.id " +
                   "JOIN course c ON sc.course_id = c.id " +
                   "SET sc.grade = ? " +
                   "WHERE s.name = ? AND c.name = ?";

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, newGrade);          // New grade value
        stmt.setString(2, studentName);    // Student's name
        stmt.setString(3, courseName);     // Course's name

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Grade has been assigned successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Error assigning the grade!");
        }
    }
}

}
