package com.infoman;

import java.sql.Connection;
import java.sql.DriverManager;

public class databaseConnection {
    private String url = "jdbc:mysql://localhost:3306/infoman_fxapp_db";
    private String user = "app";
    private String password = "asho123";
    public Connection connection;

    public databaseConnection() {
        try{
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("Database connection established");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
