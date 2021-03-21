package org.web.carritodecompras.Services;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.web.carritodecompras.Services.Connection.DataBaseRepository;
import org.web.carritodecompras.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService extends DataBaseRepository<User> {

    private static UserService userService;
    public UserService() {
        super(User.class);
    }
    public static UserService getInstance(){
        if(userService == null){
            userService = new UserService();
        }
        return userService;
    }

    public User loginRequest(String username, String password){
        User user = find(username);
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        if(user == null) return null;
        if(passwordEncryptor.checkPassword(password,user.getPassword())){
            return user;
        }
        return null;
    }
}
