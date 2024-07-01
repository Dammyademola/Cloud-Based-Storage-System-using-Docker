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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class RecoverFileController {

    @FXML
    private TextField thefolder; 
   
    
    DataSingleton data = DataSingleton.getInstance();
    
    public String Directory = data.getDirectory();
    
    public String userName = data.getUserName().toString();

    @FXML
    private void recoverfile(ActionEvent event) throws IOException, InterruptedException {
        
        
        String theFile = String.valueOf(thefolder.getText());
        
        String DestinDirectory = "/datastore1/userdir/" + userName + "/";
        String FileDirectory = Directory + "/" + theFile;
       
        ProcessBuilder processb = new ProcessBuilder("sudo","mv", FileDirectory, DestinDirectory);   
        processb.directory(new File(Directory));
        Process process = processb.start();
        int exitCode = process.waitFor();;
        
        try {
               App.setRoot("main");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
        
    }
    
    @FXML
    private void backtoMain(ActionEvent event) {
        try {
               App.setRoot("main");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }

}