/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school.management.system.model;

/**
 *
 * @author pc
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherDAO {
    private Connection connection;

    public TeacherDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Teacher> getAllTeachers() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM teacher";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            teachers.add(new Teacher(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("specialty")
            ));
        }
        return teachers;
    }

    public void addTeacher(Teacher teacher) throws SQLException {
        String query = "INSERT INTO teacher (id, name, specialty) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, teacher.getId());
        stmt.setString(2, teacher.getName());
        stmt.setString(3, teacher.getSpecialty());
        stmt.executeUpdate();
    }

    public void updateTeacher(Teacher teacher) throws SQLException {
        String query = "UPDATE teacher SET name = ?, specialty = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, teacher.getName());
        stmt.setString(2, teacher.getSpecialty());
        stmt.setInt(3, teacher.getId());
        stmt.executeUpdate();
    }

    public void deleteTeacher(int id) throws SQLException {
        String query = "DELETE FROM teacher WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
