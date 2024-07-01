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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class DirOperationsController {
    
    DataSingleton data = DataSingleton.getInstance();
    
    public String Directory = data.getDirectory();

    @FXML
    private TextField dirName;
    
 
    @FXML
    private void createDir(ActionEvent event) throws IOException, InterruptedException {
        
        String directoryName = String.valueOf(dirName.getText());
        
        ProcessBuilder processb = new ProcessBuilder("sudo","mkdir",directoryName, Directory);
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
    private void deleteDir(ActionEvent event) throws IOException, InterruptedException {
        
        String directoryName = String.valueOf(dirName.getText());
        
        System.out.println(Directory);
        
        
        ProcessBuilder processb = new ProcessBuilder("sudo","rmdir",directoryName, Directory);
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