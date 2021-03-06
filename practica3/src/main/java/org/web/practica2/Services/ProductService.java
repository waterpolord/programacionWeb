package org.web.practica2.Services;

import org.web.practica2.Services.Connection.DataBaseService;
import org.web.practica2.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductService {

    public Boolean createProduct(Product product){
        Boolean ok = false;
        Connection connection = null;
        try{
            String query = "INSERT INTO product(name,price,quantity) VALUES(?,?,?)";
            connection = DataBaseService.getInstance().getConexion();
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1,product.getName());
            prepareStatement.setDouble(2,product.getPrice());
            prepareStatement.setInt(3,  product.getQuantity());
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;
        }
        catch (SQLException e){
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, e);
        }
        return ok;
    }

    public Boolean updateProduct(Product product){
        Boolean ok = false;
        Connection connection = null;
        try{
            String query = "UPDATE product SET name=?,price=?,quantity=? WHERE product.id = ?";
            connection = DataBaseService.getInstance().getConexion();
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1,product.getName());
            prepareStatement.setDouble(2,product.getPrice());
            prepareStatement.setInt(3,  product.getQuantity());
            prepareStatement.setInt(4,  product.getId());
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;
        }
        catch (SQLException e){
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, e);
        }
        return ok;
    }



    public Product findProductById(int id) {
        Product product = null;
        Connection connection = null;
        try{
            String query = "SELECT * FROM product WHERE product.id = ?";
            connection = DataBaseService.getInstance().getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
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
        return product;
    }

    public ArrayList<Product> findAllProducts(){
        ArrayList<Product> products = new ArrayList<>();
        Connection connection = DataBaseService.getInstance().getConexion();
        try {
            String query = "SELECT * FROM product";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()){
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                products.add(product);
            }

        }catch (SQLException e){
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, e);
        }
        return products;
    }


    public boolean deleteProductById(int id){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "delete from product where id = ?";
            con = DataBaseService.getInstance().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setInt(1, id);
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }





}
