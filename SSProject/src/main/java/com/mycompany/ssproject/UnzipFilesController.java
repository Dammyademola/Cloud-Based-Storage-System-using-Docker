/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ssproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class UnzipFilesController {   
    
    DataSingleton data = DataSingleton.getInstance();
    public String userName = data.getUserName().toString();
    public String Directory = "/datastore1/userdir/" + userName + "/";

    @FXML
    private TextField zippedFileName;
    
    //Copies the zipped file to the app container
    private void copyFileToApp(String filepath) throws IOException, InterruptedException, Exception {
        ProcessBuilder processb = new ProcessBuilder("sudo", "mv", filepath, "/home/ntu-user/NetBeansProjects/SystemSoftwareV5");
        var process = processb.start();
        int exitCode = process.waitFor();
    }

    @FXML
    private void unzipFile(ActionEvent event) throws IOException, InterruptedException, Exception {
        
        //File name of file inside datastore
        String File_source = String.valueOf(zippedFileName.getText());
        
        //File path to Zipped File inside datastore
        String filepath = Directory + File_source;
       
        //Path to zipped file in app container
        String zippedFileInApp = "/home/ntu-user/NetBeansProjects/SystemSoftwareV5/" + File_source;
        
        //Path to unzipped file in app container
        String destinationOfUnzippedFile = "/home/ntu-user/NetBeansProjects/SystemSoftwareV5/";
        
        //Copies the zipped file in datastore to app container
        copyFileToApp(filepath);
        
        byte[] buffer = new byte[1024];
        
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zippedFileInApp));
        
        ZipEntry zipEntry = zis.getNextEntry();
        
        while (zipEntry != null){
            File newFile = new File(destinationOfUnzippedFile, zipEntry.getName());
            String filename = zipEntry.getName();
            
            if (zipEntry.isDirectory()){
                newFile.mkdirs();
            }
            else{
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            
            //Moves the unzipped file to the users private directory
            ProcessBuilder processf = new ProcessBuilder("sudo", "mv", "/home/ntu-user/NetBeansProjects/SystemSoftwareV5/" + filename, Directory);
            Process process2 = processf.start();
            int exitCode2 = process2.waitFor();
            
            zis.closeEntry();
            zipEntry = zis.getNextEntry();
        }
        
        //Removes the zipped file that was copied to the App
        ProcessBuilder processb = new ProcessBuilder("sudo","rm", zippedFileInApp);   
        processb.directory(new File(Directory));
        var process = processb.start();
        int exitCode = process.waitFor();
        
        zis.closeEntry();
        zis.close();
     
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