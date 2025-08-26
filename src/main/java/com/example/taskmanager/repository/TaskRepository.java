package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    /**
     * Find tasks by status
     * @param status the status to filter by ("pending" or "done")
     * @return list of tasks with the specified status
     */
    List<Task> findByStatus(String status);
}