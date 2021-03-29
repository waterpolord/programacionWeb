package org.web.carritodecompras.models;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.web.carritodecompras.Services.CommentService;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 55)
    private String name;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private int quantity;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private String description;

    public Product(){}

    public Product(String name, Double price, int quantity,String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        /*Comment comment = new Comment("que mmg ete producto en velda");
        CommentService.getInstance().create(comment);
        comments.add(comment);*/

    }

    /*public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }*/

    public Product(int id, String name, Double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(Product product,int quantity){
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = quantity;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void deleteCommentById(Comment comment){


        int num = -1;
        for(Comment aux:comments){
            num++;
            if(aux.getId() == comment.getId() )
                break;

        }
        if(num > -1)
            comments.remove(num);


    }

}
