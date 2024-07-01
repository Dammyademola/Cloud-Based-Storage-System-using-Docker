/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ssproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class psController implements Initializable {
    
    DataSingleton data = DataSingleton.getInstance();
    
    public String Directory = data.getDirectory();

    @FXML
    private TreeView<String> ps;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startProcess();
    }

    private void startProcess() {
        try {
            Process process = new ProcessBuilder("ps").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            TreeItem<String> rootNode = new TreeItem<>("Processes");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+"); 
                String processName = parts[parts.length - 1]; 
                TreeItem<String> item = new TreeItem<>(processName);
                rootNode.getChildren().add(item);
            }
            reader.close();
            rootNode.setExpanded(true);
            ps.setRoot(rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toMain(ActionEvent event) {
        try {
            App.setRoot("main");     
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
}