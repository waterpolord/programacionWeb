package org.web.carritodecompras.Services;

import org.web.carritodecompras.models.Client;
import org.web.carritodecompras.models.Product;
import org.web.carritodecompras.models.Sale;
import org.web.carritodecompras.models.User;

import java.util.ArrayList;

public class Principal {
    private static Principal principal;
    private ArrayList<User> users =  new ArrayList<>();
    private ArrayList<Client> clients =  new ArrayList<>();
    private ArrayList<Product> products =  new ArrayList<>();
    private ArrayList<Sale> sales =  new ArrayList<>();

    public Principal(){
        products.add(new Product(1,"Leche",200.0,5));
        products.add(new Product(2,"Queso",225.0,50));
        products.add(new Product(3,"Jamon",242.0,4));
        products.add(new Product(4,"Lechuga",2.0,15));
        users.add(new User("Robert","Admin","admin"));


    }
/*
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

    //metodos de producto

    public Product findProductByID(int id){
        if(products.size() > 0) {
            for (Product product : products) {
                if (product.getId() == id) {
                    return product;
                }
            }
        }
        return null;
    }

    public Product updateProduct(Product product){
        Product old = findProductByID(product.getId());
        if(product != null){
            old.setName(product.getName());
            old.setPrice(product.getPrice());
            old.setQuantity(product.getQuantity());
        }
        return old;
    }

    public void deleteProductById(int id){

            Product product = findProductByID(id);
            int num = -1;
            for(Product aux:products){
                num++;
                if(aux.getId() == product.getId() )
                    break;

            }
            if(num > -1)
                products.remove(num);


    }


    public void addProduct(Product product){
        if(products.isEmpty()){
            product.setId(1);
        }
        else{
            product.setId(products.size()+1);
        }
        products.add(product);
    }

    // metodos de cliente
    public void addClient(Client client){
        clients.add(client);
    }

    public Boolean clientExistByEmail(String email){
        for (Client client:clients){
            if(client.getMail().equalsIgnoreCase(email)){
                return true;
            }
        }
        return false;
    }

    public Client findClientByEmail(String email){
        for (Client client:clients){
            if(client.getMail().equalsIgnoreCase(email)){
                return client;
            }
        }
        return null;
    }

    public Client updateClient(Client client){
        Client old = findClientByEmail(client.getMail());
        if(client != null){
            old.setName(client.getName());
            old.setMail(client.getMail());
            old.setKart(client.getKart());
        }
        return old;
    }







    // metodos de ventas

    public void addSell(Sale sale){
        if(sales.isEmpty()){
            sale.setId(1);
        }
        else{
            sale.setId(sales.size()+1);
        }
        sales.add(sale);
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

    public ArrayList<Sale> getSales() {
        return sales;
    }*/

}
