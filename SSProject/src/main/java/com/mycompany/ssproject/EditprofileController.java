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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import controllerControl.Controller;

/**
 *
 * @author ntu-user
 */
public class EditprofileController {

    @FXML
    private TextField usernameUpdateForm;
    @FXML
    private PasswordField updatePasswordForm;
    @FXML
    private Button updateButton;
    @FXML
    private Button backtomainButton;
    
    DataSingleton data = DataSingleton.getInstance();

    
    public boolean editform(String Username, String Password, int Id) {  
        
        if (Username.isBlank() && Password.isBlank()){
            System.out.println("Profile Update Failed");
            return false;
        }
        
        else{
            
        Controller Controller = new Controller();
        Controller.salt();
        String EncryptedPass = Controller.generateSecurePassword(Password);
            
            
        String sql = "UPDATE User " + "SET username = ? " + ",password = ? " + "WHERE id = ?";
        Connection conn = null;
       
        try{ 
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:systemSoftwareDatabase.db"); 
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, Username);  
            pstmt.setString(2, EncryptedPass);  
            pstmt.setInt(3, Id);  
            pstmt.executeUpdate();  
            System.out.println("User Updated Successfully");
            return true;
        } catch (SQLException e) {  
            System.out.println(e.getMessage()); 
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditprofileController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            }
        }
    }  
    
    @FXML
    private void updateForm(ActionEvent event) {
        String usernameupdate_value = String.valueOf(usernameUpdateForm.getText());
        String passwordupdate_value = String.valueOf(updatePasswordForm.getText());
        
        int storedid = data.getUserId();
        
        boolean edit = false;
        edit = editform(usernameupdate_value, passwordupdate_value, storedid);
        try {
               App.setRoot("main");     
        }
        catch(Exception e) {
            System.err.println(e);
        }  
    }

    @FXML
    private void switchToMain(ActionEvent event) {
        try {
               App.setRoot("main");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
}