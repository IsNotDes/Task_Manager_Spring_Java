package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setDueDate(LocalDate.now().plusDays(7));
        testTask.setStatus("pending");
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        // Given
        List<Task> expectedTasks = Arrays.asList(testTask);
        when(taskRepository.findAll()).thenReturn(expectedTasks);

        // When
        List<Task> actualTasks = taskService.getAllTasks();

        // Then
        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTasksByStatus_ShouldReturnFilteredTasks() {
        // Given
        String status = "pending";
        List<Task> expectedTasks = Arrays.asList(testTask);
        when(taskRepository.findByStatus(status)).thenReturn(expectedTasks);

        // When
        List<Task> actualTasks = taskService.getTasksByStatus(status);

        // Then
        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).findByStatus(status);
    }

    @Test
    void createTask_ShouldSaveAndReturnTask() {
        // Given
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        // When
        Task createdTask = taskService.createTask(testTask);

        // Then
        assertEquals(testTask, createdTask);
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    void createTask_ShouldSetDefaultStatusWhenNull() {
        // Given
        Task taskWithoutStatus = new Task();
        taskWithoutStatus.setTitle("Test");
        taskWithoutStatus.setStatus(null);
        
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Task createdTask = taskService.createTask(taskWithoutStatus);

        // Then
        assertEquals("pending", createdTask.getStatus());
        verify(taskRepository, times(1)).save(taskWithoutStatus);
    }

    @Test
    void updateTask_ShouldUpdateExistingTask() {
        // Given
        Long taskId = 1L;
        Task updatedTaskData = new Task();
        updatedTaskData.setTitle("Updated Title");
        updatedTaskData.setDescription("Updated Description");
        updatedTaskData.setDueDate(LocalDate.now().plusDays(10));
        updatedTaskData.setStatus("done");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        // When
        Task updatedTask = taskService.updateTask(taskId, updatedTaskData);

        // Then
        assertEquals("Updated Title", testTask.getTitle());
        assertEquals("Updated Description", testTask.getDescription());
        assertEquals("done", testTask.getStatus());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    void updateTask_ShouldThrowExceptionWhenTaskNotFound() {
        // Given
        Long taskId = 999L;
        Task updatedTaskData = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> taskService.updateTask(taskId, updatedTaskData));
        
        assertEquals("Task not found with id: " + taskId, exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask_ShouldDeleteExistingTask() {
        // Given
        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(true);

        // When
        taskService.deleteTask(taskId);

        // Then
        verify(taskRepository, times(1)).existsById(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void deleteTask_ShouldThrowExceptionWhenTaskNotFound() {
        // Given
        Long taskId = 999L;
        when(taskRepository.existsById(taskId)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> taskService.deleteTask(taskId));
        
        assertEquals("Task not found with id: " + taskId, exception.getMessage());
        verify(taskRepository, times(1)).existsById(taskId);
        verify(taskRepository, never()).deleteById(taskId);
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(testTask));

        // When
        Task foundTask = taskService.getTaskById(taskId);

        // Then
        assertEquals(testTask, foundTask);
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void getTaskById_ShouldThrowExceptionWhenTaskNotFound() {
        // Given
        Long taskId = 999L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> taskService.getTaskById(taskId));
        
        assertEquals("Task not found with id: " + taskId, exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
    }
}