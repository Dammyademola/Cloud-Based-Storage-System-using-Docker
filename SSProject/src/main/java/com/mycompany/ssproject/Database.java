package com.mycompany.ssproject;

import java.sql.*;

public class Database {
    public static void createDatabase() {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:systemSoftwareDatabase.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public static void createUserTable() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:systemSoftwareDatabase.db");
            System.out.println("Connected to database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS User " +
                    "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " Username           VARCHAR(50)    NOT NULL, " +
                    " Password            VARCHAR(150)     NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
    public static void createAdminTable() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:systemSoftwareDatabase.db");
            System.out.println("Connected to database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Admin " +
                    "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " Username           VARCHAR(50)    NOT NULL, " +
                    " Location           VARCHAR(50)    NOT NULL, "+
                    " Error              VARCHAR(150)     NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static void main( String args[] ){
        createDatabase();
        createUserTable();
        createAdminTable();
    }
}