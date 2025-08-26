package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    /**
     * Get all tasks
     * @return list of all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    /**
     * Get tasks by status
     * @param status the status to filter by
     * @return list of tasks with the specified status
     */
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }
    
    /**
     * Create a new task
     * @param task the task to create
     * @return the created task
     */
    public Task createTask(Task task) {
        // Ensure status is set to pending if not provided
        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            task.setStatus("pending");
        }
        return taskRepository.save(task);
    }
    
    /**
     * Update an existing task
     * @param id the ID of the task to update
     * @param updatedTask the updated task data
     * @return the updated task
     * @throws RuntimeException if task not found
     */
    public Task updateTask(Long id, Task updatedTask) {
        Optional<Task> existingTaskOpt = taskRepository.findById(id);
        if (existingTaskOpt.isPresent()) {
            Task existingTask = existingTaskOpt.get();
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setStatus(updatedTask.getStatus());
            return taskRepository.save(existingTask);
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }
    
    /**
     * Delete a task by ID
     * @param id the ID of the task to delete
     * @throws RuntimeException if task not found
     */
    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }
    
    /**
     * Get a task by ID
     * @param id the ID of the task
     * @return the task if found
     * @throws RuntimeException if task not found
     */
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }
}