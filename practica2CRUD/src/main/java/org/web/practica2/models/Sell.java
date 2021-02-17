package org.web.practica2.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Sell {
    private int id;
    private Client client;
    private User user;
    private ArrayList<Product> products;
    private LocalDate date;

    public Sell(int id, Client client, User user, ArrayList<Product> products, LocalDate date) {
        this.id = id;
        this.client = client;
        this.user = user;
        this.products = products;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
