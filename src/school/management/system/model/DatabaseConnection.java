/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school.management.system.model;

import java.sql.*;

/**
 *
 * @author ameen
 */
public class DatabaseConnection {
    
    private static final String url = "jdbc:mysql://localhost:3306/school";
    private static final  String username = "user";
    private static final String passwd = "user";
    
    
    public static  Connection getConnection() throws SQLException{
        return  DriverManager.getConnection(url, username, passwd);
    }
    
}
