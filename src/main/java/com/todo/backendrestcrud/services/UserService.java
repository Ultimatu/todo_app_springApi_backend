package com.todo.backendrestcrud.services;

import com.todo.backendrestcrud.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public String signup(User user);
    public  User getById(Long id);
    public User getByEmailAndPassword(String email, String password);

    public List<User> getAll();

    public String deleteById(Long id);

    public boolean getByEmail(String email);

    boolean getByEmail(String email, String password);

    User getUserById(Long id);

    String updateUser(Long id, User user);


    Optional<Object> findById(long userId);
}
