package org.web.practica2.Services;

import org.web.practica2.Services.Connection.DataBaseService;
import org.web.practica2.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public Boolean createUser(User user){
        Boolean ok = false;
        Connection connection = null;
        try{
            String query = "INSERT INTO user_app(name,username,password) VALUES(?,?,?)";
            connection = DataBaseService.getInstance().getConexion();
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1,user.getName());
            prepareStatement.setString(2,user.getUsername());
            prepareStatement.setString(3,  user.getPassword());
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;
        }
        catch (SQLException e){

        }
        return ok;
    }

    public User findUserByUsername(String username) {
        User user = null;
        Connection connection = null;
        try{
            String query = "SELECT * FROM user_app WHERE user_app.username = ?";
            connection = DataBaseService.getInstance().getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(2,username);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                user = new User(
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password")
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
        return user;
    }

    public User loginRequest(String username, String password){
        User user = findUserByUsername(username);
        if(password.equalsIgnoreCase(user.getPassword()) && !user.getLogged()){
            user.setLogged(true);
            return user;
        }
        return null;
    }
}
