package org.web.carritodecompras.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column()
    private String name;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private Boolean active = true;
    @Column(name = "date_comment")
    private LocalDate date;

    public Comment() {
    }

    public Comment(String message,String name){
        this.message = message;
        date = LocalDate.now();
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
