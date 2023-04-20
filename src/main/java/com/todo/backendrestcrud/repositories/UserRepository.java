package com.todo.backendrestcrud.repositories;

import com.todo.backendrestcrud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Serializable> {

    //find user by email and password
    Optional<User> findByEmailAndPassword(String email, String password);

    //find user by email
    Optional<User> findByEmail(String email);

    //find user by id
    Optional<User> findByPassword(String password);
}
