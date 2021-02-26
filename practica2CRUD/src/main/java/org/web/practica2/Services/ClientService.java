package org.web.practica2.Services;

import org.web.practica2.Services.Connection.DataBaseService;
import org.web.practica2.models.Client;
import org.web.practica2.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientService {

    public Boolean createClient(Client client){
        Boolean ok = false;
        Connection connection = DataBaseService.getInstance().getConexion();
        try{
            String query = "INSERT INTO client(name,mail) VALUES(?,?)";

            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1,client.getName());
            prepareStatement.setString(2,client.getMail());
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;
        }
        catch (SQLException e){

        }

        try{
            String query = "INSERT INTO cart(client_id,product_id,quantity) VALUES(?,?,?)";

            PreparedStatement prepareStatement = connection.prepareStatement(query);
            for(Product product:client.getKart()){
                prepareStatement.setInt(1,client.getId());
                prepareStatement.setInt(2,product.getId());
                prepareStatement.setInt(3,product.getQuantity());
                int fila = prepareStatement.executeUpdate();
                ok = fila > 0 ;
            }

        }
        catch (SQLException e){

        }


        return ok;
    }

    public Boolean updateClient(Client client){
        Boolean ok = false;
        Connection connection = DataBaseService.getInstance().getConexion();
        try{
            String query = "UPDATE client SET name=?,mail=? WHERE client.id = ?";

            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1,client.getName());
            prepareStatement.setString(2,client.getMail());
            prepareStatement.setInt(3,  client.getId());
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;
        }
        catch (SQLException e){

        }

        try{
            String query = "UPDATE cart SET client_id=?,product_id=?,quantity =? WHERE client_id = ? AND product_id = ?";

            PreparedStatement prepareStatement = connection.prepareStatement(query);
            for(Product product:client.getKart()){
                prepareStatement.setInt(1,client.getId());
                prepareStatement.setInt(2,product.getId());
                prepareStatement.setInt(3,product.getQuantity());
                int fila = prepareStatement.executeUpdate();
                ok = fila > 0 ;
            }
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;
        }
        catch (SQLException e){

        }



        return ok;
    }

    public Client findClientByMail(String mail) {
        Client client = null;
        ArrayList<Product> cart = new ArrayList<>();
        Connection connection = null;
        try{
            String query = "SELECT * FROM client WHERE client.mail = ?";
            String query2 = "SELECT id,name,price,cart.quantity FROM cart WHERE client.id = ?";
            connection = DataBaseService.getInstance().getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(query),
            preparedStatement1 = connection.prepareStatement(query2);
            preparedStatement.setString(1,mail);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){

                client = new Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("mail"),
                        cart
                );
            }
            preparedStatement1.setInt(1,client.getId());

            rs = preparedStatement1.executeQuery();
            while (rs.next()){
                client.addToKart(
                        new Product(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getDouble("price"),
                                rs.getInt("quantity")
                        )
                );
            }




        } catch (SQLException e){
            System.out.println("cannot access database");
        }
        finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println("cannot close database");
            }

        }
        return client;
    }
    

}
