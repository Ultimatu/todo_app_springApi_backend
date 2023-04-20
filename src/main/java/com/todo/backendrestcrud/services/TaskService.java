package com.todo.backendrestcrud.services;

import com.todo.backendrestcrud.models.Task;

import java.util.List;

//service for task
public interface TaskService{

    //create task
    public Task createTask(Task task);

    //all tasks
    public List<Task> getAllTasks();

    //get by user_id
    public List<Task> getTasksByUserId(Long user_id);

    //get by task id
    public Task getTaskById(Long task_id);

    //existing task
    public Task getTask(Task task);

    //update task
    public String updateTask(Task task);

    //delete task
    public String deleteTask(Task task);
    

}
