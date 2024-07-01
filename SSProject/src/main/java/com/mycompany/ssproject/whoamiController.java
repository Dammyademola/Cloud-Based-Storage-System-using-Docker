/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ssproject;



import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

/**
 *
 * @author ntu-user
 */
public class whoamiController implements Initializable {
    DataSingleton data = DataSingleton.getInstance();
    public String userName = data.getUserName().toString();
    @FXML
    private TreeView<String> whoami;
    private TreeItem<String> rootItem;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startProcess();
    }
    
    private void startProcess() {
        rootItem = new TreeItem<>("Root");
        TreeItem<String> usernameItem = new TreeItem<>(userName);
        rootItem.getChildren().add(usernameItem);
        whoami.setRoot(rootItem);
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