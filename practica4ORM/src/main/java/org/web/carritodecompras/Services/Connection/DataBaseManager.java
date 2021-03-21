package org.web.carritodecompras.Services.Connection;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    private static Server server;
    private static DataBaseManager dataBaseManager;

    public static void startDb()  {
        try {
            server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-ifNotExists").start();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public static DataBaseManager getInstance(){
        if(dataBaseManager == null){
            dataBaseManager = new DataBaseManager();
        }
        return dataBaseManager;
    }

    /**
     *
     * @throws SQLException
     */
    public static void stopDb() throws SQLException {
        if(server!=null) {
            server.stop();
        }
    }

}
