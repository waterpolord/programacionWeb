package org.web.carritodecompras.Services;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.web.carritodecompras.Services.Connection.DataBaseRepository;
import org.web.carritodecompras.models.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
        User user = findUserByUsername(username);
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        if(user == null) return null;
        if(passwordEncryptor.checkPassword(password,user.getPassword())){
            return user;
        }
        return null;
    }

    public User findUserByUsername(String username){
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("User.findUserByUsername");
        query.setParameter("username", "%"+username+"%");
        List<User> lista = query.getResultList();

        if(lista.isEmpty()){
            System.out.println("Usuario no encontrado");
            return null;
        }
        return lista.get(0);

    }
}
