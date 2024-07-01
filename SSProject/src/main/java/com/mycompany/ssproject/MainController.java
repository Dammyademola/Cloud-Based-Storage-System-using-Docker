/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ssproject;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.mycompany.ssproject.errorCatcher.setError;


public class MainController {
    
    public MainController() {
        deleteAfterThirtyDays();
    }
    
    DataSingleton data = DataSingleton.getInstance();
    
    public String userName = data.getUserName().toString();
    
    public String Directory = "/datastore1/userdir/" + userName + "/";
    
    @FXML
    private MenuItem editProfile;
    
    @FXML
    private MenuItem logOut;
    
    @FXML
    private MenuItem editProfile2;
    
    @FXML
    private TextArea directoryItems;
    
    @FXML
    private TextArea directoryItems1;
    
    @FXML
    private Text filePath;
    
    @FXML
    private TextField getPath;
    
    public void initialize() throws IOException, InterruptedException {
        initdirectory();
        configuration();
        directoryviewer();
    }
    
    private void initdirectory() throws IOException, InterruptedException {
        ProcessBuilder process = new ProcessBuilder("sudo","mkdir","/datastore1/userdir/");   
        var Theprocess = process.start();
        int exitCode = Theprocess.waitFor();
    }
    
    private void configuration() throws IOException, InterruptedException {
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
        String userName = data.getUserName().toString();
        
        if (resultDir.contains(userName)){
        }
        
        else{
            ProcessBuilder processc = new ProcessBuilder("sudo","mkdir",Directory);  
            var processC = processc.start();
            int exitCode2 = processC.waitFor();
        }
    }
    
    private void directoryviewer() throws IOException, InterruptedException {
        filePath.setText(Directory);
        data.setDirectory(Directory);
        ProcessBuilder processb = new ProcessBuilder("sudo","ls", "-F", Directory);   
        var process = processb.start();
        int exitCode = process.waitFor();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        
        StringBuilder builderDir = new StringBuilder();
        StringBuilder builderFiles = new StringBuilder();
        
        String line = null;
        
        while ( (line = reader.readLine()) != null) {
            if (line.endsWith("/")){
                builderDir.append(line);
                builderDir.append(System.getProperty("line.separator"));
            }
            else{
                builderFiles.append(line);
                builderFiles.append(System.getProperty("line.separator"));
            }
        }
  
        String resultDir = builderDir.toString();
        String resultFiles = builderFiles.toString();
           
        directoryItems.setText(resultDir);
        directoryItems1.setText(resultFiles);        
    };
    
    @FXML
    private void submitPath(ActionEvent event) throws IOException, InterruptedException {
        String thePath = String.valueOf(getPath.getText());
        
        this.Directory = Directory + thePath;
        data.setDirectory(Directory);
       
        directoryviewer();
    } 
    
    @FXML
    private void removePath(ActionEvent event) throws IOException, InterruptedException {
        
        String seperator = "/";
        
        this.Directory = Directory.substring(0, Directory.lastIndexOf(seperator));
        
        filePath.setText(Directory);
        data.setDirectory(Directory);
        ProcessBuilder processb = new ProcessBuilder("sudo","ls", "-F", Directory);   
        var process = processb.start();
        int exitCode = process.waitFor();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        
        StringBuilder builderDir = new StringBuilder();
        StringBuilder builderFiles = new StringBuilder();
        
        String line = null;
        
        while ( (line = reader.readLine()) != null) {
            if (line.endsWith("/")){
                builderDir.append(line);
                builderDir.append(System.getProperty("line.separator"));
            }
            else{
                builderFiles.append(line);
                builderFiles.append(System.getProperty("line.separator"));
            }
        }
  
        String resultDir = builderDir.toString();
        String resultFiles = builderFiles.toString();
           
        directoryItems.setText(resultDir);
        directoryItems1.setText(resultFiles);     
        
    }
    
    @FXML
    private void createFile(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("createFile");     
        }
        catch(Exception e) {
            System.err.println(e);
            String errorMessage = e.getMessage();
            setError(userName, errorMessage, "Central");
        }
    }

    @FXML
    private void uploadFile(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("uploadFile");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void renameFile(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("renameFile");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void deleteFile(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("deleteFile");     
        }
            catch(Exception e) {
            System.err.println(e);
        }

    }
    
    @FXML
    private void recoverFile(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("recoverFile");     
        }
            catch(Exception e) {
            System.err.println(e);
        }
    }
    
    @FXML
    private void movetheFile(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("moveaFile");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
    @FXML
    private void CopyFile(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("copyFile");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
    @FXML
    private void createDir(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("dirOperations");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void deleteDir(ActionEvent event) throws IOException, InterruptedException {
        try {
            App.setRoot("dirOperations");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void zipFiles(ActionEvent event) throws IOException, InterruptedException {
        try {
            App.setRoot("zipFiles");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
        
    }

    @FXML
    private void unzipFiles(ActionEvent event) throws IOException, InterruptedException {
        try {
            App.setRoot("unzipFiles");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void editProfile(ActionEvent event) throws IOException, InterruptedException {
        try {
            App.setRoot("editProfile");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
    @FXML
    private void deleteProfile(ActionEvent event) throws IOException, InterruptedException {
        String sql = "DELETE FROM user WHERE id = ?";
        Connection conn = null;
        
        int storedid = data.getUserId();
        
        try{ 
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:systemSoftwareDatabase.db"); 
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setInt(1, storedid);
            pstmt.executeUpdate();  
            System.out.println("User Deleted Successfully");
        } catch (SQLException e) {  
            System.out.println(e.getMessage()); 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            App.setRoot("login");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
    @FXML
    private void shareFile(ActionEvent event) throws IOException, InterruptedException {
        try {
             App.setRoot("shareFile");     
            }
            catch(Exception e) {
                System.err.println(e);
            }
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
    private void deleteAfterThirtyDays() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            String command = "sudo find " + Directory + "/deleted -type f -mtime +30 -exec rm {} +";
            try {
                Process process = Runtime.getRuntime().exec(command);
                int exitValue = process.waitFor();
                if (exitValue == 0) {
                    System.out.println("Deleted files older than 30 days.");
                } else {
                    System.out.println("Failed to delete files older than 30 days or may not have a deleted directory.");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 30, TimeUnit.DAYS);
    }

    @FXML
    private void treeStructure(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("treeStructure");     
            }
            catch(Exception e) {
                System.err.println(e);
            }
    }
    
    @FXML
    private void ps(ActionEvent event) throws IOException, InterruptedException {
        try {
               App.setRoot("ps");     
            }
            catch(Exception e) {
                System.err.println(e);
            }
    }
    
    @FXML
    private void whoami(ActionEvent event) throws IOException, InterruptedException {
        try {
             App.setRoot("whoami");     
            }
            catch(Exception e) {
                System.err.println(e);
            }
    }

}