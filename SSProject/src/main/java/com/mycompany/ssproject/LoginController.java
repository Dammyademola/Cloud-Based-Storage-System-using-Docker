package com.mycompany.ssproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

import controllerControl.Controller;
import java.io.BufferedReader;
import java.util.Map;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;



public class LoginController {
    
    public static Map<String, UserSession> sessions = new HashMap<>();
    
    private static final int PORT = 6969;
    private static ServerSocket serverSocket;
    
    static{
        try{
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
        }
    }
    
    DataSingleton data = DataSingleton.getInstance();

    @FXML
    private TextField usernamelogin;
    @FXML
    private PasswordField passwordlogin;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerpageButton;


    @FXML
    private void switchToRegister() throws IOException, InterruptedException {
        App.setRoot("register");
    }
    
    public void sessionSave(String Username){
        String fileName = "Session.txt";
        try{
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(Username);
            
            oos.close();
           
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean sessionQuery(String Username) throws IOException, InterruptedException {
        
        String fileName = "Session.txt";
        
        String searchString = Username;
        
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            
            String line;
            
            while (( line = reader.readLine()) != null){
                if (line.contains(searchString)) {
                   return true;
                }
                else{
                    return false;
                } 
            }
            
           reader.close();
        }
        catch(IOException e){
            return false;
        }
        
        return false;
    }
    
    

    public boolean validateUser(String user, String pass, String tabName) throws IOException, InterruptedException {
        Connection conn = null;
        Controller controller = new Controller();
        // Query database by username pull up data
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:systemSoftwareDatabase.db");
            var statement = conn.createStatement();
            statement.setQueryTimeout(controller.timeout);
            ResultSet rs = statement.executeQuery("select id, username, password from " + tabName + " where username='" + user + "'");
            // Encrypt the inputted password
            controller.salt();
            String EncryptedLoginPass = controller.generateSecurePassword(pass);
            
            // Let's iterate through the java ResultSet
            while (rs.next()) {
                // Compare the username and the unencrypted password with the inputted password
                if (rs.getString("password").equals(EncryptedLoginPass) && rs.getString("username").equals(user)) {
                    
                    boolean sessionCheck = sessionQuery(user);
                    
                    if (sessionCheck == true){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid authorisation");
                        alert.setHeaderText(null);
                        alert.setContentText("User currently logged in.");
                        alert.showAndWait();
                        return false;
                    }
                    
                    else {
                    String sessionId = user + System.currentTimeMillis();
                    UserSession session = new UserSession(sessionId, user);
                    sessions.put(sessionId, session);
                    sessionSave(user);
                    data.setUserId(rs.getInt("id"));
                    data.setPassword(rs.getString("password"));
                    data.setUserName(rs.getString("username"));
                    return true;
                    }
                    
                }
                
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Login");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password.");
                    alert.showAndWait();
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return false;
    }

   @FXML
    private void ProcessLogin(ActionEvent event) throws IOException, InterruptedException {
        String username_value = String.valueOf(usernamelogin.getText());
        String password_value = String.valueOf(passwordlogin.getText());

        // Check if username or password is empty
        if (username_value.trim().isEmpty() || password_value.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please enter both a username and a password.");
            alert.showAndWait();
            return;
        } else {
            String tabName = "User";
            Boolean login = validateUser(username_value, password_value, tabName);
            
            
            if(login == true && username_value.equals("Admin")){
                try {
                    App.setRoot("adminPanel");
                } catch (IOException e) {
                    System.err.println(e);
                }   
            } 
            else if (login == true != username_value.equals("Admin")) {
                try {
                    App.setRoot("main");
                } catch (IOException e) {
                    System.err.println(e);
                }
            } 
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Login");
                alert.setHeaderText(null);
                alert.setContentText("Username or password is wrong.");
                alert.showAndWait();
            }
        }
    }

    
    public static void listen(){
        while (true) {
            try{
                Socket clientSocket = serverSocket.accept();
                
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                String sessionId = (String) in.readObject();
                
                if (sessions.containsKey(sessionId)) {
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    out.writeObject("Success");
                } else {
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    out.writeObject("Failure");
                }
                
                clientSocket.close();
                in.close();
                
            } catch (IOException ex){
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
    }
    
    static class UserSession {
        private String sessionId;
        private String username;
        
        public UserSession(String sessionId, String username){
            this.sessionId = sessionId;
            this.username = username;
        }
        
        public String getSessionId(){
            return sessionId;
        }
        
        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
        
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
    }
}