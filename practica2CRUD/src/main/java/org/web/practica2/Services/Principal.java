package org.web.practica2.Services;

import org.web.practica2.models.Client;
import org.web.practica2.models.Product;
import org.web.practica2.models.Sell;
import org.web.practica2.models.User;

import java.util.ArrayList;
import java.util.List;

public class Principal {
    private static Principal principal;
    private ArrayList<User> users =  new ArrayList<>();
    private ArrayList<Client> clients =  new ArrayList<>();
    private ArrayList<Product> products =  new ArrayList<>();
    private ArrayList<Sell> sells =  new ArrayList<>();

    public Principal(){
        users.add(new User("Robert","Admin","admin"));

    }

    public static Principal getInstance(){
        if(principal == null){
            principal = new Principal();
        }
        return principal;
    }

    // metodos del usuario

    public User loginRequest(String username, String password){
        User user = findUserByUsername(username);
        if(password.equalsIgnoreCase(user.getPassword()) && !user.getLogged()){
            user.setLogged(true);
            return user;
        }
        return null;
    }



    public Boolean userExistByUsername(String username){
        for (User user:users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }

    public User findUserByUsername(String username){
        for (User user:users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }

    public void addUser(User user){
        if(!userExistByUsername(user.getUsername())){
            users.add(user);
        }
    }





    // getters

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Sell> getSells() {
        return sells;
    }

}
