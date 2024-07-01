/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ssproject;

/**
 *
 * @author ntu-user
 */
public class DataSingleton {
    
    private static final DataSingleton instance = new DataSingleton();
    
    private int id;
    private String username;
    private String Directory;
    private String password;
    
    private DataSingleton(){}
    
    public static DataSingleton getInstance(){
        return instance;
    }
    
    public int getUserId(){
        return id;
    }
    
    public void setUserId(int id) {
        this.id = id;
    }
    
    public String getUserName(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password) {
        this.username = password;
    }
    
    
    public void setUserName(String username) {
        this.username = username;
    }
    
    public String getDirectory(){
        return Directory;
    }
    
    public void setDirectory(String Directory) {
        this.Directory = Directory;
    }
    
}