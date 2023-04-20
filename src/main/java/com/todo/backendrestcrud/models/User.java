package com.todo.backendrestcrud.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data //lombok for getters and setters
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Users") //table name
public class User {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long id; //id

    private String name; 
    private String surname; 
    @Column(unique=true) //unique email
    private String email;
    private String phone;

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    //profile picture
    private String photo_url = "no_file.png";

    //constructors
    public User() {
    }
    //setters and getters
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    private String password;
    private int onlineStatus;




    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String nom) {
        this.name = nom;
    }
    public String getName(){
        return name;
    }

    public void setSurname(String prenom){
        this.surname = prenom;
    }
    public String getSurname(){
        return surname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }



}