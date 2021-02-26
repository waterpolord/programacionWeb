package org.web.practica2.models;

import org.web.practica2.Services.Principal;

import java.util.ArrayList;

public class Client {
    private int id;
    private String name;
    private String mail;
    private ArrayList<Product> kart;

    public Client(int id,String name, String mail, ArrayList<Product> kart) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.kart = kart;
    }

    public Client(String name, String mail, ArrayList<Product> kart) {

        this.name = name;
        this.mail = mail;
        this.kart = kart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setKart(ArrayList<Product> kart) {
        this.kart = kart;
    }

    public ArrayList<Product> getKart() {
        return kart;
    }

    public void addToKart(Product product) {
        this.kart.add(product);
    }

    public void deleteProductFromKartById(int id){


        int num = -1;
        for(Product aux:kart){
            num++;
            if(aux.getId() == id )
                break;

        }
        if(num > -1)
            kart.remove(num);


    }

    public Product getProductById(Product product){
        for (Product aux:kart){
            if(product.getId() == aux.getId()){
                return aux;
            }
        }
        return null;
    }


}
