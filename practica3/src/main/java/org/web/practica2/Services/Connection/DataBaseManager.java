package org.web.practica2.Services.Connection;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    private static Server server;

    public static void startDb()  {
        try {
            server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-ifNotExists").start();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
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



    public static void createTables() throws  SQLException{
        String USER = "CREATE TABLE IF NOT EXISTS user_app\n" +
                "(\n" +
                "  id BIGINT auto_increment PRIMARY KEY NOT NULL,\n" +
                " name VARCHAR(100) NOT NULL,\n" +
                " username VARCHAR(100) NOT NULL UNIQUE,\n" +
                " password VARCHAR(100) NOT NULL\n" +
                ");";
        String PRODUCT = "CREATE TABLE IF NOT EXISTS product\n" +
                "(\n" +
                "  id BIGINT auto_increment PRIMARY KEY NOT NULL,\n" +
                " name VARCHAR(100) NOT NULL,\n" +
                " price BIGINT NOT NULL,\n" +
                "  quantity BIGINT NOT NULL\n" +
                ");";

        String CART = "CREATE TABLE IF NOT EXISTS cart\n" +
                "(\n" +
                " client_id BIGINT NOT NULL ,\n" +
                " product_id BIGINT NOT NULL ,\n" +
                " quantity BIGINT NOT NULL,\n" +
                " FOREIGN KEY (client_id) REFERENCES client(id),\n" +
                " FOREIGN KEY (product_id) REFERENCES product(id)\n" +
                ");";

        String CLIENT = "CREATE TABLE IF NOT EXISTS client\n" +
                "(\n" +
                "  id BIGINT auto_increment PRIMARY KEY NOT NULL,\n" +
                " name VARCHAR(100) NOT NULL,\n" +
                " mail VARCHAR(100) NOT NULL UNIQUE\n" +
                ");";

        String SALE = "CREATE TABLE IF NOT EXISTS sale\n" +
                "(\n" +
                "  id BIGINT auto_increment PRIMARY KEY NOT NULL,\n" +
                " client_id BIGINT NOT NULL ,\n" +
                " sell_date DATE NOT NULL,\n" +
                " FOREIGN KEY (client_id) REFERENCES client(id)\n" +
                ");";
        String PRODUCT_SALE = "CREATE TABLE IF NOT EXISTS product_sale\n" +
                "(\n" +
                " sale_id BIGINT NOT NULL ,\n" +
                " product_id BIGINT NOT NULL ,\n" +
                " quantity BIGINT NOT NULL, \n" +
                "  FOREIGN KEY (sale_id) REFERENCES sale(id),\n" +
                " FOREIGN KEY (product_id) REFERENCES product(id)\n" +
                ");";




        Connection con = DataBaseService.getInstance().getConexion();
        Statement statement = con.createStatement();
        statement.execute(USER);
        statement.execute(PRODUCT);
        statement.execute(CLIENT);
        statement.execute(CART);
        statement.execute(SALE);
        statement.execute(PRODUCT_SALE);
        statement.close();
        con.close();
    }
}
