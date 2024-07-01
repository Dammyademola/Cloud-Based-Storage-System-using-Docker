/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ssproject;

import com.mycompany.ssproject.App;
import com.mycompany.ssproject.DataSingleton;
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
public class RenameFileController {
    
    DataSingleton data = DataSingleton.getInstance();
    
    public String Directory = data.getDirectory();

    @FXML
    private TextField fileName;
    
    @FXML
    private TextField newFileName;
    
 
    @FXML
    private void renameFile(ActionEvent event) throws IOException, InterruptedException {
        
        String theFileName = String.valueOf(fileName.getText());
        String theNewFileName = String.valueOf(newFileName.getText());
        
        
        
        ProcessBuilder processb = new ProcessBuilder("sudo","mv", theFileName, theNewFileName);   
        processb.directory(new File(Directory));
        var process = processb.start();
        int exitCode = process.waitFor();
        
        try {
               App.setRoot("main");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }    

    @FXML
    private void backtoMain(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("main");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
}