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
import java.util.zip.ZipOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class ZipFilesController {
    
    DataSingleton data = DataSingleton.getInstance();
    
    public String userName = data.getUserName().toString();
    
    public String Directory = "/datastore1/userdir/" + userName + "/";
    
    @FXML
    private TextField filetobezippedName;
    @FXML
    private TextField zippedFileName;

    private void copyFileToApp(String filepath) throws IOException, InterruptedException, Exception {
        ProcessBuilder processb = new ProcessBuilder("sudo", "mv", filepath, "/home/ntu-user/NetBeansProjects/SystemSoftwareV5");
        processb.directory(new File(Directory));
        var process = processb.start();
        int exitCode = process.waitFor();
    }

    @FXML
    private void zipFile(ActionEvent event) throws IOException, InterruptedException, Exception {
        
        //File name of file inside datastore
        String File_source = String.valueOf(filetobezippedName.getText());
        
        //Name of the zipped folder to be
        String Zip_name = String.valueOf(zippedFileName.getText());

        String filepath = File_source;
        
        //Path to unzipped file in app container
        String unzippedFileInApp = "/home/ntu-user/NetBeansProjects/SystemSoftwareV5/" + File_source;
        
        //Path to zipped file in app container
        String zippedFileInApp = "/home/ntu-user/NetBeansProjects/SystemSoftwareV5/" + Zip_name;
        
        copyFileToApp(filepath);
        
        FileOutputStream fileos = new FileOutputStream(new File(zippedFileInApp));
        
        ZipOutputStream zipOut = new ZipOutputStream(fileos);

        File fileToZip = new File(unzippedFileInApp);
        
        FileInputStream fis = new FileInputStream(fileToZip);
        
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        
        zipOut.putNextEntry(zipEntry);
             
        byte[] bytes = new byte[1024];

        int length;
        
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        
        zipOut.close();
        fis.close();
        fileos.close();
        
       //Removes the unzipped file in the app container
        ProcessBuilder processb = new ProcessBuilder("sudo","rm", unzippedFileInApp);   
        var process = processb.start();
        int exitCode = process.waitFor();
      
        //Moves the zipped file to the app container
        ProcessBuilder processf = new ProcessBuilder("sudo", "mv", "/home/ntu-user/NetBeansProjects/SystemSoftwareV5/" + Zip_name, Directory);
        Process process2 = processf.start();
        int exitCode2 = process2.waitFor();

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