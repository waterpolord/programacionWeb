package org.web.practica2.models;

import java.util.ArrayList;

public class Client {
    private String name;
    private String mail;
    private ArrayList<Product> kart;

    public Client(String name, String mail, ArrayList<Product> kart) {
        this.name = name;
        this.mail = mail;
        this.kart = kart;
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

    public ArrayList<Product> getKart() {
        return kart;
    }

    public void addToKart(Product product) {
        this.kart.add(product);
    }


}
