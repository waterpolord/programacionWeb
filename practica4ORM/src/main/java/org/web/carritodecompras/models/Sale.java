package org.web.carritodecompras.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne() // Relacion muchos a uno una venta tiene un cliente y un cliente aparece en muchas ventas
    private Client client;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_products")
    private List<Product> products;
    @Column()
    private LocalDate date;

    public Sale(Client client, List<Product> products, LocalDate date) {

        this.client = client;
        this.products = products;
        this.date = date;
    }

    public Sale(int id,Client client, ArrayList<Product> products, LocalDate date) {
        this.id = id;
        this.client = client;
        this.products = products;
        this.date = date;
    }
    public Sale(){}

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

    public List<Product> getProducts() {
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

    public void addProduct(Product product){
        products.add(product);
    }
}
