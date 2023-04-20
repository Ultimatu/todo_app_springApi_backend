package com.todo.backendrestcrud.controllers;

import com.todo.backendrestcrud.models.Task;
import com.todo.backendrestcrud.models.User;
import com.todo.backendrestcrud.services.TaskService;
import com.todo.backendrestcrud.services.UserService;
import com.todo.backendrestcrud.services.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// This controller is used to serve the index.html file
@ControllerAdvice
@RestController
//allow requests  from any origin
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class TaskController {
    //inject task service
    @Autowired
    private TaskService taskService;
    //inject user service
    @Autowired
    private UserService userService;


    //get all tasks
    @GetMapping("/tasks/all")
    public ResponseEntity<List<Task>> getAllTasks() {
       //s'il n'y a pas de tâches, renvoie une réponse d'erreur
        if (taskService.getAllTasks().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }
    //get task by id
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        //if there is no task, return an error response
        if (taskService.getTaskById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(taskService.getTaskById(id));
    }
    //get all tasks user_id
    @GetMapping("/tasks/user/{id}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable long id) {
        //if there is no task, return an error response
        if (taskService.getTasksByUserId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(taskService.getTasksByUserId(id));
    }

    //create task
    @PostMapping("/tasks/save/{userId}")
    public ResponseEntity<Task> createTask(@PathVariable Long userId, @RequestBody Task task) {
        //try to get user by id
        try {
            //get user by id
            User user = userService.getUserById(userId);
            //set user to task
            task.setUser(user);
            //save task
            taskService.createTask(task);
            //return task
            return ResponseEntity.ok(task);
        } catch (EntityNotFoundException ex) { //if user not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) { //if error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //update task
    @PutMapping("/tasks/{id}")
    public ResponseEntity<String> updateTask(@PathVariable long id, @RequestBody Task task) {
        //set id to task
        task.setId(id);
        //update task
        return ResponseEntity.ok().body(this.taskService.updateTask(task));
    }
    //delete task
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable long id) {
        //create task
        Task task = new Task();
        //set id to task
        task.setId(id);
        //delete task
        return ResponseEntity.ok().body(this.taskService.deleteTask(task));
    }
}
