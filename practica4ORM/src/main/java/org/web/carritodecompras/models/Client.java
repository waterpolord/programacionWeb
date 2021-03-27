package org.web.carritodecompras.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = "Client.findClientByMail", query = "select p from Client  p where p.mail like :mail")})
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 30)
    private String name;
    @Column()
    private String mail;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_products")
    private List<Product> kart;


    public Client(String name, String mail, ArrayList<Product> kart) {

        this.name = name;
        this.mail = mail;
        this.kart = kart;
    }

    public Client(){}

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
