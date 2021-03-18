package org.web.carritodecompras.models;

import org.web.carritodecompras.Services.Principal;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 30)
    private String name;
    @Column()
    private String mail;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Employee_Project",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "project_id") }
    )
    private List<Product> kart;

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

    public List<Product> getKart() {
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
