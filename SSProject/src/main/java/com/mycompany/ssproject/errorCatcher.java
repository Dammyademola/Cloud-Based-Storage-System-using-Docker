/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ssproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class errorCatcher {
    
    private String username;
    private String Error;
    private String Location;
    
    public static void insertintoError(String Username, String Error, String Location) {
        
        String insertSql = "INSERT INTO Admin(Username, Error, Location) VALUES(?,?,?)";
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:systemSoftwareDatabase.db");
            // Insert the new user into the database
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, Username);
            insertStmt.setString(2, Error);
            insertStmt.setString(3, Location);
            insertStmt.executeUpdate();
        }catch (SQLException e) {
           System.out.println(e.getMessage());
           
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(errorCatcher.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static void setError(String Username, String Error, String Location) {
        insertintoError(Username, Error, Location);
    } 
    
    
       
}