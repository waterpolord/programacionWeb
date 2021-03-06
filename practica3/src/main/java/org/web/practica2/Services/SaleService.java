package org.web.practica2.Services;

import org.web.practica2.Services.Connection.DataBaseService;
import org.web.practica2.models.Client;
import org.web.practica2.models.Product;
import org.web.practica2.models.Sale;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleService {

    public Boolean createSale(Sale sale){
        Boolean ok = false;
        Connection connection = DataBaseService.getInstance().getConexion();
        try{
            String query = "INSERT INTO sale(client_id,sell_date) VALUES(?,?)";

            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setInt(1,sale.getClient().getId());
            prepareStatement.setDate(2, Date.valueOf(sale.getDate()));

            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;
            ArrayList<Sale> sales = findAllSales();
            if(sales.size() > 0 ){
                sale.setId(sales.size());
            }
            else{
                sale.setId(1);
            }
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
            String query = "SELECT sale.id as sale_id ,sell_date,client.id as client_id,client.name,client.mail, FROM sale INNER JOIN client on client.id = client_id";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()){
                sales.add(
                        new Sale(
                                rs.getInt("sale_id"),
                                new Client(
                                        rs.getInt("client_id"),
                                        rs.getString("name"),
                                        rs.getString("mail"),
                                        new ArrayList<>()
                                ),
                                new ArrayList<>(),
                                 rs.getDate("sell_date").toLocalDate()
                        )
                );
            }


        }catch (SQLException e){
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            for(Sale sale:sales) {
                String query = "SELECT product_id,product_sale.quantity,name,price,sale.id FROM product INNER JOIN product_sale on product_sale.product_id = product_id INNER JOIN sale on sale.id = product_sale.sale_id WHERE sale.id = ? ";
                PreparedStatement prepareStatement = connection.prepareStatement(query);
                prepareStatement.setInt(1,sale.getId());
                ResultSet rs = prepareStatement.executeQuery();
                while (rs.next()) {

                   sale.addProduct(
                            new Product(
                                    rs.getInt("product_id"),
                                    rs.getString("name"),
                                    rs.getDouble("price"),
                                    rs.getInt("quantity")
                            )
                    );

                }
            }


        }catch (SQLException e){
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, e);
        }

        return sales;
    }



}
