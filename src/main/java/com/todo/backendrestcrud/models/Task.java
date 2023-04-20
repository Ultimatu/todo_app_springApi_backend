package com.todo.backendrestcrud.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//task model

@Data //lombok for getters and setters
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Tasks") //table name
public class Task {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long id; //id

    @ManyToOne (cascade = CascadeType.ALL) //many tasks can be assigned to one user
    @JoinColumn(name = "id_user", referencedColumnName = "id") //foreign key
    private User user; //user
    private String title; //title
    private String description; //description
    private LocalDateTime createDate = LocalDateTime.now(); //create date
    private LocalDateTime deadLine = null; //deadline with default value null
    private String progress; //progress
    private String priority; //priority

    //constructors
    public Task() {
    }

    //set user
    public void setUser(User user) {
        this.user = user;
    }

    //set id
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
