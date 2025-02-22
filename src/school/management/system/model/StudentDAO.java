

package school.management.system.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    public StudentDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            students.add(new Student(
                rs.getInt("id"),
                rs.getInt("age"),
                rs.getString("name"),
                rs.getString("gender"),
                rs.getString("birth_place"),
                rs.getString("birth_date")
            ));
        }
        return students;
    }

    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO student (id, age, name, gender, birth_place, birth_date) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, student.getId());
        stmt.setInt(2, student.getAge());
        stmt.setString(3, student.getName());
        stmt.setString(4, student.getGender());
        stmt.setString(5, student.getBirthPlace());
        stmt.setString(6, student.getBirthDate());
        stmt.executeUpdate();
    }

    public void updateStudent(Student student) throws SQLException {
        String query = "UPDATE student SET age = ?, name = ?, gender = ?, birth_place = ?, birth_date = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, student.getAge());
        stmt.setString(2, student.getName());
        stmt.setString(3, student.getGender());
        stmt.setString(4, student.getBirthPlace());
        stmt.setString(5, student.getBirthDate());
        stmt.setInt(6, student.getId());
        stmt.executeUpdate();
    }

    public void deleteStudent(int id) throws SQLException {
        String query = "DELETE FROM student WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public void enrollStudentInCourse(String courseName, int id) throws SQLException {
        String query = "INSERT INTO student_course (student_id, course_id) SELECT ?, c.id FROM course c WHERE c.name = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.setString(2, courseName);
        stmt.executeUpdate();
    }

    // NEW: Search students by name
    public List<Student> searchStudents(String searchInput) throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student WHERE name LIKE ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, "%" + searchInput + "%");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            students.add(new Student(
                rs.getInt("id"),
                rs.getInt("age"),
                rs.getString("name"),
                rs.getString("gender"),
                rs.getString("birth_place"),
                rs.getString("birth_date")
            ));
        }
        return students;
    }

    // NEW: Get courses available for enrollment
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

    // NEW: Get courses and grades for a student
    public ArrayList<Object[]> getStudentCoursesAndGrades(int studentId) throws SQLException {
        ArrayList<Object[]> data = new ArrayList<>();
        String query = "SELECT c.name AS course_name, sc.grade AS grade FROM student_course sc "
                     + "JOIN course c ON sc.course_id = c.id WHERE sc.student_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, studentId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            data.add(new Object[]{rs.getString("course_name"), rs.getInt("grade")});
        }
        return data;
    }
}
