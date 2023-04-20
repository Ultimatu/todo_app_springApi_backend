package com.todo.backendrestcrud.controllers;

import com.todo.backendrestcrud.exception.UserNotFoundException;
import com.todo.backendrestcrud.services.UserService;
import com.todo.backendrestcrud.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import org.springframework.util.StringUtils;

// This controller is used to serve the index.html file
@ControllerAdvice
@RestController
//allow requests  from any origin
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class UserController {
    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir; // Chemin du dossier d'upload des fichiers
    //inject user service
    @Autowired
    private UserService userService;
    private MultipartFile file;
    //signup
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody User user) {
        //check if user already exists
        if (!userService.getByEmail(user.getEmail())) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "user already exists");
            //if user already exists, return an error response
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        //if user doesn't exist, create a new user
        String status = userService.signup(user);
        Map<String, String> response = new HashMap<>();
        response.put("status", status);
        //return a response with status 201
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        //check if user exists
        User foundUser = userService.getByEmailAndPassword(user.getEmail(), user.getPassword());
        if (foundUser == null) {
            //if user doesn't exist, return an error response
            HttpHeaders headers = new HttpHeaders();
            headers.set("WWW-Authenticate", "Bearer realm=\"myrealm\"");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).build();
        }
        //if user exists, return the user
        return ResponseEntity.ok().body(foundUser);
    }

    // save user profile picture
    @PostMapping("/user/profile_pic/{id}")
    public ResponseEntity<String> uploadProfilePic(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
        try {
            // Récupérer l'utilisateur connecté
            User user = userService.getUserById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
            }
            // Vérifier si le fichier est bien une image
            if (!file.getContentType().startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le fichier doit être une image");
            }

            // Enregistrer le fichier dans le dossier d'upload des fichiers
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            //
            String filePath = Paths.get(uploadDir, "assets").toString();
            //
            Path path = Paths.get(filePath, "user_" + user.getId(), "profile_pic");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            // Renommer le fichier pour éviter les doublons
            String fileExtension = FilenameUtils.getExtension(fileName);
            String newFileName = "profile_pic." + fileExtension;

            // Enregistrer le fichier sur le serveur
            Path targetLocation = Paths.get(path.toString(), newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Mettre à jour le chemin d'accès de l'image de profil de l'utilisateur
            user.setPhoto_url("/assets/user_" + user.getId() + "/profile_pic/" + newFileName);
            userService.updateUser(user.getId(), user);

            return ResponseEntity.ok().body("Image de profil mise à jour avec succès");

        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de l'enregistrement de l'image de profil");
        }
    }
    @GetMapping("/user/profile_pic/{id}")
    public ResponseEntity<byte[]> getProfilePic(@PathVariable Long id) {
        try {
            // Récupérer l'utilisateur connecté
            User user = userService.getUserById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Récupérer le chemin d'accès de l'image de profil de l'utilisateur
            String photoUrl = user.getPhoto_url();
            if (photoUrl == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Récupérer le fichier de l'image de profil
            Path path = Paths.get(uploadDir, photoUrl.substring(1));
            byte[] imageBytes = Files.readAllBytes(path);

            // Créer la réponse HTTP avec le contenu de l'image
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageBytes.length);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //get all users
    @GetMapping("/users/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        //if no users are found, return an error response
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(users);
    }

    //get user by id
    @GetMapping("/found_user/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return ResponseEntity.ok().body(user);
    }

    //update user
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        //check if user exists
        if (userService.getById(id) == null) {
            throw new UserNotFoundException(id);
        }
        //if user exists, update the user
        String status = userService.updateUser(id, user);
        if (status == null) {
            throw new UserNotFoundException(id);
        }
        //return the updated user
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("status", status);

        return ResponseEntity.status(HttpStatus.OK).body((User) successResponse);
    }

    //get userByemail
    @GetMapping("/users/email/{email}")
    public ResponseEntity<Boolean> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.getByEmail(email));
    }

    //delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        String status = userService.deleteById(id);
        if (status == null) {
            throw new UserNotFoundException(id);
        }
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("status", status);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}

