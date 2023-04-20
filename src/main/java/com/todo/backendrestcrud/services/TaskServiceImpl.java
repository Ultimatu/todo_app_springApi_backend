package com.todo.backendrestcrud.services;

import com.todo.backendrestcrud.models.Task;
import com.todo.backendrestcrud.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//service for task
@Service //this annotation is used to mark a class as a service provider
public class TaskServiceImpl implements TaskService{
    @Autowired //this annotation is used to inject the object dependency implicitly
    private TaskRepository taskRepo; //task repository

    //create task
    @Override //this annotation is used to override the method of parent class
    public Task createTask(Task task) { 
        return taskRepo.save(task);
    }
    //getAllTasks
    @Override
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    //getTaskByUserId
    @Override
    public List<Task> getTasksByUserId(Long user_id) {
        return taskRepo.findAllByUserId(user_id);
    }

    //getTaskById
    @Override
    public Task getTaskById(Long task_id) {
        return taskRepo.findById(task_id).get();
    }

    //getTask
    @Override
    public Task getTask(Task task) {
        return taskRepo.findById(task.getId()).get();
    }

    //updateTask
    @Override
    public String updateTask(Task task) {
        Optional<Task> optionalTask = taskRepo.findById(task.getId());
        if (optionalTask.isPresent()) {
            Task newTask = optionalTask.get();
            newTask.setTitle(task.getTitle());
            newTask.setDescription(task.getDescription());
            newTask.setCreateDate(task.getCreateDate());
            newTask.setPriority(task.getPriority());
            newTask.setDeadLine(task.getDeadLine());
            newTask.setProgress(task.getProgress());
            taskRepo.save(newTask);
            return "Task updated successfully";
        } else {
            return null;
        }
    }

    @Override
    public String deleteTask(Task task) {
        Optional<Task> optionalTask = taskRepo.findById(task.getId());
        if (optionalTask.isPresent()) {
            taskRepo.delete(task);
            return "Task deleted successfully";
        } else {
            return null;
        }
    }


}
