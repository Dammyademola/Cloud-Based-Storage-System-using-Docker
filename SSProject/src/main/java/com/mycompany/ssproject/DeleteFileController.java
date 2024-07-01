/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ssproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class DeleteFileController {
    
    
    DataSingleton data = DataSingleton.getInstance();
    
    public String Directory = data.getDirectory();

    @FXML
    private TextField fileName;
    
    
    private void mkfolder() throws IOException, InterruptedException {
        
        String deletedDirectory = Directory + "/deleted";
        ProcessBuilder processBuilder = new ProcessBuilder("sudo", "mkdir",  deletedDirectory);
        processBuilder.redirectErrorStream(true);
        processBuilder.directory(new File(Directory));
        Process process = processBuilder.start();
    } 
    
    private void movefiletodelete() throws IOException, InterruptedException {
        String theFileName = String.valueOf(fileName.getText());
        String deletedDirectory = Directory + "/deleted";
        ProcessBuilder processb = new ProcessBuilder("sudo", "cp", theFileName, deletedDirectory);   
        processb.directory(new File(Directory));
        Process process = processb.start();
        int exitCode = process.waitFor();
    }

    private void check() throws IOException, InterruptedException {
        ProcessBuilder processb = new ProcessBuilder("sudo","ls", "-F",Directory);   
        var process = processb.start();
        int exitCode = process.waitFor();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        
        StringBuilder builderDir = new StringBuilder();
        
        String line = null;
        
        while ( (line = reader.readLine()) != null) {
            if (line.endsWith("/")){
                builderDir.append(line);
                builderDir.append(System.getProperty("line.separator"));
            }
        }  
        
        String resultDir = builderDir.toString();
        String deletedfilefolder = "deleted";
        
        if (resultDir.contains(deletedfilefolder)){
            movefiletodelete();
        }
        
        else{
            mkfolder();
            movefiletodelete();
        }
    }
    
 
    @FXML
    private void deleteFile(ActionEvent event) throws IOException, InterruptedException {
        check();
        
        String theFileName = String.valueOf(fileName.getText());
        ProcessBuilder processb = new ProcessBuilder("sudo","rm", theFileName, Directory);   
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