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
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class ShareFileController {    

    @FXML
    private TextField fileName;
    
    @FXML
    private TextField usernameRecipients;
    
    DataSingleton data = DataSingleton.getInstance();
    
    public String Directory = data.getDirectory();
    
    public String userName = data.getUserName().toString();
    
    
    private boolean check() throws IOException, InterruptedException {
        ProcessBuilder processb = new ProcessBuilder("sudo","ls", "-F","/datastore1/userdir/");   
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
        
        String theRecipientsUsername = String.valueOf(usernameRecipients.getText());
        String RecipientsDirectory = Directory + "/" + theRecipientsUsername;
        
        if (resultDir.contains(theRecipientsUsername)){
            return true;
        }
        else{
            return false;
        }
    }

    @FXML
    private void sharetheReadAccessFile(ActionEvent event) throws IOException, InterruptedException {
        boolean checkifuserexists = check();
        
        if (checkifuserexists == true){
            
            String theFileName = String.valueOf(fileName.getText());
             
            String theRecipientsUsername = String.valueOf(usernameRecipients.getText());
                
            String DestinDirectory = "/datastore1/userdir/" + theRecipientsUsername + "/";
            String FileDirectory = Directory + "/" + theFileName;
               
            ProcessBuilder processb = new ProcessBuilder("sudo","cp", FileDirectory, DestinDirectory);   
            //processb.directory(new File(Directory));
            var process = processb.start();
            int exitCode = process.waitFor();
            
            String[] ReadOnlyCommand = {"sudo", "chmod", "444", DestinDirectory + "/" + theFileName};
            ProcessBuilder ReadOnlyProcessBuilder = new ProcessBuilder(ReadOnlyCommand);
            Process RealOnlyProcess = ReadOnlyProcessBuilder.start();
            int chmodExitCode = RealOnlyProcess.waitFor();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User not found");
            alert.setHeaderText(null);
            alert.setContentText("User is not found.");
            alert.showAndWait();
        }
      
        try {
               App.setRoot("main");     
        }
        catch(Exception e) {
            System.err.println(e);
        }

    }
    
    
    @FXML
    private void sharetheFile(ActionEvent event) throws IOException, InterruptedException {     
     
        boolean checkifuserexists = check();
        
        if (checkifuserexists == true){
            
            String theFileName = String.valueOf(fileName.getText());
             
            String theRecipientsUsername = String.valueOf(usernameRecipients.getText());
                
            String DestinDirectory = "/datastore1/userdir/" + theRecipientsUsername + "/";
            String FileDirectory = Directory + "/" + theFileName;
               
            ProcessBuilder processb = new ProcessBuilder("sudo","cp", FileDirectory, DestinDirectory);   
            //processb.directory(new File(Directory));
            var process = processb.start();
            int exitCode = process.waitFor();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User not found");
            alert.setHeaderText(null);
            alert.setContentText("User is not found.");
            alert.showAndWait();
        }
      
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