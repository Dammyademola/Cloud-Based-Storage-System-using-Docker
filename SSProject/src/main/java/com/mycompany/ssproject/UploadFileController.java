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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;



/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class UploadFileController {
    
    DataSingleton data = DataSingleton.getInstance();
    
    public String Directory = data.getDirectory();
    
    
    @FXML
    private ListView listview;
    @FXML
    private Button choosebutton;
 
    
    @FXML
    private void uploadFile(ActionEvent event) throws IOException, InterruptedException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        
        if (selectedFile != null) {
            listview.getItems().add(selectedFile.getName());
            
            String filepath = selectedFile.getAbsolutePath(); 
            
            ProcessBuilder processb = new ProcessBuilder("sudo","cp", filepath, Directory);   
            processb.directory(new File(Directory));
            var process = processb.start();
            int exitCode = process.waitFor();
            
        }
        else{
            System.out.println("File not found");
        }
        
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