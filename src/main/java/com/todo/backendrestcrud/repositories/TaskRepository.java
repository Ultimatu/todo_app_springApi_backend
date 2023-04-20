package com.todo.backendrestcrud.repositories;

import com.todo.backendrestcrud.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

//repository for task
@Repository
public interface TaskRepository extends JpaRepository<Task, Serializable> {

    //find all tasks by user id
    List<Task> findAllByUserId(Long userId);

    //find task by id
    Task findById(long id);

    //delete task by id
    void deleteById(long id);

    //delete all tasks by user id
    void deleteAllByUserId(Long userId);
}
