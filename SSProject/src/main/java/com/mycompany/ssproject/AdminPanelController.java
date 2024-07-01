/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ssproject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;




/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class AdminPanelController implements Initializable {

    @FXML
    private TableView<DataSignletonAdmin> ErrorTable;
    
    @FXML
    private TableColumn<DataSignletonAdmin, String> username;
    @FXML
    private TableColumn<DataSignletonAdmin, String> Error;
    @FXML
    private TableColumn<DataSignletonAdmin, String> Location;
      
    
    public void initialize(URL location, ResourceBundle resources) {

        //make sure the property value factory should be exactly same as the e.g getStudentId from your model class
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        Error.setCellValueFactory(new PropertyValueFactory<>("Error"));
        Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        //add your data to the table here.
        ErrorTable.setItems(errorModel);
    }
    
    @FXML
    private void logOut(ActionEvent event) throws IOException, InterruptedException {
        String fileName =  "Session.txt";
        
        File file = new File(fileName);
        
        if (file.delete()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Logged Out");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Logged Out");
            alert.showAndWait();
            
            try {
               App.setRoot("login");     
            }
            catch(Exception e) {
                System.err.println(e);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed to Log Out");
            alert.setHeaderText(null);
            alert.setContentText("Unsuccessfully Logged Out");
            alert.showAndWait();
        } 
    }

    @FXML
    private void toMain(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("main");     
            }
            catch(Exception e) {
                System.err.println(e);
            }
    }
    
    private final ObservableList<DataSignletonAdmin> errorModel = FXCollections.observableArrayList();
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:systemSoftwareDatabase.db");
            var statement = conn.createStatement();
            String sql = "SELECT * FROM Admin";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                String username = rs.getString("Username");
                String error = rs.getString("Error");
                String location = rs.getString("Location");
                errorModel.add(new DataSignletonAdmin(username, error, location));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
  
}