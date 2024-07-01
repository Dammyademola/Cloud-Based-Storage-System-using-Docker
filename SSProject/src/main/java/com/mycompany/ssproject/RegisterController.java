package com.mycompany.ssproject;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import controllerControl.Controller;

public class RegisterController {
    @FXML
    private TextField usernameRegisterForm;
    @FXML
    private PasswordField passwordRegisterForm;
    @FXML
    private Button backtologinButton;
    @FXML
    private Button registerButton;

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
    
    public boolean insert(String Username, String Password) {
    if (Username.isBlank() || Password.isBlank()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter both username and password");
        alert.showAndWait();
        return false;
    } else {
        String selectSql = "SELECT COUNT(*) AS count FROM User WHERE Username = ?";
        String insertSql = "INSERT INTO User(Username, Password) VALUES(?,?)";
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:systemSoftwareDatabase.db");

            // Check if the username already exists in the database
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, Username);
            ResultSet resultSet = selectStmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("count");
            if (count > 0) {
                // Username already exists in the database
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error");
                alert.setHeaderText(null);
                alert.setContentText("Username already registered");
                alert.showAndWait();
                return false;
            }

            // Insert the new user into the database
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, Username);
            insertStmt.setString(2, Password);
            insertStmt.executeUpdate();
            System.out.println("User Registered Successfully");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
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
}

         
    @FXML
    private void registerForm(ActionEvent event) throws IOException {
        //Getting values from register form.
        String username_value = String.valueOf(usernameRegisterForm.getText());
        String password_value = String.valueOf(passwordRegisterForm.getText());
        
        if (username_value.isBlank() && !password_value.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a username");
            alert.showAndWait();
            return;
        }
        
        if (!username_value.isBlank() && password_value.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a password");
            alert.showAndWait();
            return;
        }
        
        Controller Controller = new Controller();
        //Encryption of password.
        Controller.salt();
        String EncryptedPass = Controller.generateSecurePassword(password_value);
        //Storing username and password to database.
        boolean register = insert(username_value,EncryptedPass);
        
        if (register == true){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Registration Success");
            alert.setHeaderText(null);
            alert.setContentText("User Registered Successfully");
            alert.showAndWait();
            App.setRoot("login");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText(null);
            alert.setContentText("Registration Failed, Please try again.");
            alert.showAndWait();
        }
    }
}