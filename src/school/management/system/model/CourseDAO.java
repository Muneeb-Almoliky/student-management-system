package school.management.system.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private Connection connection;

    public CourseDAO() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    public List<String> getTeachers() throws SQLException {
        List<String> teachers = new ArrayList<>();
        String query = "SELECT * FROM teacher";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            teachers.add(rs.getString("name"));
        }
        return teachers;
    }

    public List<Object[]> getCourses() throws SQLException {
        List<Object[]> courses = new ArrayList<>();
        String query = "SELECT course.id AS course_id, course.name AS course_name, teacher.name AS teacher_name " +
                       "FROM course INNER JOIN teacher ON course.teacher_id = teacher.id";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            courses.add(new Object[]{
                rs.getInt("course_id"),
                rs.getString("course_name"),
                rs.getString("teacher_name")
            });
        }
        return courses;
    }

    public void addCourse(int id, String name, String teacherName) throws SQLException {
        String query = "INSERT INTO course(id, name, teacher_id) " +
                       "SELECT ?,?,t.id FROM teacher t WHERE t.name = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.setString(2, name);
        stmt.setString(3, teacherName);
        stmt.executeUpdate();
    }
}
