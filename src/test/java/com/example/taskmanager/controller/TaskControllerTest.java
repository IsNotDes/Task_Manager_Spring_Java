package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setDueDate(LocalDate.of(2024, 1, 15));
        testTask.setStatus("pending");
    }

    @Test
    void createTask_ShouldReturnCreatedTask() throws Exception {
        // Given
        when(taskService.createTask(any(Task.class))).thenReturn(testTask);

        // When & Then
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.status").value("pending"));

        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() throws Exception {
        // Given
        List<Task> tasks = Arrays.asList(testTask);
        when(taskService.getAllTasks()).thenReturn(tasks);

        // When & Then
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Task"));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void getAllTasks_WithStatusFilter_ShouldReturnFilteredTasks() throws Exception {
        // Given
        List<Task> tasks = Arrays.asList(testTask);
        when(taskService.getTasksByStatus("pending")).thenReturn(tasks);

        // When & Then
        mockMvc.perform(get("/tasks").param("status", "pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].status").value("pending"));

        verify(taskService, times(1)).getTasksByStatus("pending");
        verify(taskService, never()).getAllTasks();
    }

    @Test
    void getTaskById_ShouldReturnTask() throws Exception {
        // Given
        when(taskService.getTaskById(1L)).thenReturn(testTask);

        // When & Then
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Task"));

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void getTaskById_WhenTaskNotFound_ShouldReturnNotFound() throws Exception {
        // Given
        when(taskService.getTaskById(999L)).thenThrow(new RuntimeException("Task not found"));

        // When & Then
        mockMvc.perform(get("/tasks/999"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).getTaskById(999L);
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() throws Exception {
        // Given
        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("Updated Description");
        updatedTask.setDueDate(LocalDate.of(2024, 1, 20));
        updatedTask.setStatus("done");

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updatedTask);

        // When & Then
        mockMvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.status").value("done"));

        verify(taskService, times(1)).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void updateTask_WhenTaskNotFound_ShouldReturnNotFound() throws Exception {
        // Given
        when(taskService.updateTask(eq(999L), any(Task.class)))
                .thenThrow(new RuntimeException("Task not found"));

        // When & Then
        mockMvc.perform(put("/tasks/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTask)))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).updateTask(eq(999L), any(Task.class));
    }

    @Test
    void deleteTask_ShouldReturnNoContent() throws Exception {
        // Given
        doNothing().when(taskService).deleteTask(1L);

        // When & Then
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void deleteTask_WhenTaskNotFound_ShouldReturnNotFound() throws Exception {
        // Given
        doThrow(new RuntimeException("Task not found")).when(taskService).deleteTask(999L);

        // When & Then
        mockMvc.perform(delete("/tasks/999"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).deleteTask(999L);
    }
}