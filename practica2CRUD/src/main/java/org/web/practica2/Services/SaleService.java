package org.web.practica2.Services;

import org.web.practica2.Services.Connection.DataBaseService;
import org.web.practica2.models.Product;
import org.web.practica2.models.Sale;

import java.sql.*;
import java.util.ArrayList;

public class SaleService {

    public Boolean createSale(Sale sale){
        Boolean ok = false;
        Connection connection = DataBaseService.getInstance().getConexion();
        try{
            String query = "INSERT INTO sale(client_id,sell_id) VALUES(?,?)";

            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setInt(1,sale.getClient().getId());
            prepareStatement.setDate(2, Date.valueOf(sale.getDate()));
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;
        }
        catch (SQLException e){
            System.out.println("Cannot create the sale to database "+e);
        }

        try{
            String query = "INSERT INTO product_sale(sale_id,product_id,quantity) VALUES(?,?,?)";
            int ind = 0;
            for(Product product:sale.getProducts()){
                PreparedStatement prepareStatement = connection.prepareStatement(query);
                prepareStatement.setInt(1,sale.getId());
                prepareStatement.setInt(2, product.getId());
                prepareStatement.setDouble(3,product.getQuantity());
                int fila = prepareStatement.executeUpdate();
                ok = fila > 0 ;
            }

        }catch (SQLException e){
            System.out.println("Cannot add the lost of products to database "+e);
        }

        return ok;
    }

    public ArrayList<Sale> findAllSales(){
        ArrayList<Sale> sales = new ArrayList<>();
        Connection connection = DataBaseService.getInstance().getConexion();
        try {
            String query = "SELECT sale.id,client.id,client.name,client.mail, FROM sale";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()){

            }

        }catch (SQLException e){

        }
        return sales;
    }



}
