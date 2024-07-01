/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ssproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class TreeStructureController implements Initializable {
    
    DataSingleton data = DataSingleton.getInstance();
    
    public String Directory = data.getDirectory();

    @FXML
    private TreeView<String> treeView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startProcess();
    }    
    
    
    private void startProcess() {
        String directoryName = Directory;
        TreeItem<String> rootNode = new TreeItem<>(directoryName);
        rootNode.setExpanded(true);

        // Run ls command and populate tree with results
        try {
            Process process = new ProcessBuilder("sudo","ls", Directory).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            String[] files = output.split("\\R");
            for (String file : files) {
                TreeItem<String> childNode = new TreeItem<>(file);
                rootNode.getChildren().add(childNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Set root node as root of tree
        treeView.setRoot(rootNode);
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