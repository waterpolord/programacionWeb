package org.web.practica2.Services.Connection;

import org.h2.tools.Server;

import java.sql.*;

public class DataBaseService {
    public static  DataBaseService dataBaseService;

    private String CONNECTION = "jdbc:h2:tcp://localhost/~/online_shop";
    public static DataBaseService getInstance(){
        if(dataBaseService == null ){
            dataBaseService = new DataBaseService();
        }
        return dataBaseService;
    }

    private  DataBaseService(){
        setDriver();
    }

    private void setDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("cannot register driver: "+e);
        }
    }

    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(CONNECTION, "sa", "1234");
        } catch (SQLException e) {
            System.out.println("cannot access database: "+e);
        }
        return con;
    }





}
