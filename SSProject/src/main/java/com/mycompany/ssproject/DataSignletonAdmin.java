package com.mycompany.ssproject;

import javafx.beans.property.SimpleStringProperty;

public class DataSignletonAdmin {

    private SimpleStringProperty username;
    private SimpleStringProperty Error;
    private SimpleStringProperty Location;
    
    public DataSignletonAdmin(String username, String Error, String Location) {
        this.username = new SimpleStringProperty(username);
        this.Error = new SimpleStringProperty(Error);
        this.Location = new SimpleStringProperty(Location);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username = new SimpleStringProperty(username);
    }

    public String getError() {
        return Error.get();
    }

    public void setError(String Error) {
        this.Error = new SimpleStringProperty(Error);
    }

    public String getLocation() {
        return Location.get();
    }

    public void setLocation(String Location) {
        this.Location = new SimpleStringProperty(Location);
    }
}