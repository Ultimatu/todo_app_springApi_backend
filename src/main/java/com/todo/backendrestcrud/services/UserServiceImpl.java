package com.todo.backendrestcrud.services;

import com.todo.backendrestcrud.models.User;
import com.todo.backendrestcrud.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public String signup(User user) {
        userRepo.save(user);
        return "signup successfully";
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public User getByEmailAndPassword(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmailAndPassword(email, password);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            // handle the case when the user is not found
            return null;
        }
    }




    @Override
    public boolean getByEmail(String email, String password) {
        Optional<User> user = userRepo.findByEmailAndPassword(email, password);
        if (!user.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }


    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        return optionalUser
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }
    @Override
    public String updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepo.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(user.getName());
            existingUser.setSurname(user.getSurname());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setPassword(user.getPassword());
            existingUser.setPhoto_url(user.getPhoto_url());
            userRepo.save(existingUser);
            return "Utilisateur modifié avec succès";
        } else {
            return "Impossible de trouver l'utilisateur avec l'ID " + id;
        }
    }

    @Override
    public Optional<Object> findById(long userId) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public String deleteById(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return "User removed successfully";
        } else {
            return "Use not found";
        }
    }

    @Override
    public boolean getByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (!user.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
