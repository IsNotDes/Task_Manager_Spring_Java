# Task Manager API - Complete Project Plan

## 📝 Project Overview
**Task Manager API** - A RESTful Spring Boot application for managing tasks with CRUD operations.

## 🏗️ Technical Stack
- **Framework**: Spring Boot 3.x
- **Build Tool**: Maven
- **Java Version**: 17
- **Database**: H2 (in-memory)
- **Dependencies**: Spring Web, Spring Data JPA, H2 Database, Lombok

## 📁 Project Structure
```
com.example.taskmanager/
├── controller/
│   └── TaskController.java     # REST endpoints
├── model/
│   └── Task.java              # JPA entity
├── repository/
│   └── TaskRepository.java    # Data access layer
├── service/
│   └── TaskService.java       # Business logic
└── TaskManagerApplication.java # Main application class
```

## 🗂️ Implementation Plan

### 1. Maven Configuration (pom.xml)
- Spring Boot starter parent
- Dependencies: web, data-jpa, h2, lombok
- Java 17 configuration

### 2. Entity Model (Task.java)
- Fields: id (Long, auto-generated), title (String), description (String), dueDate (LocalDate), status (String)
- Lombok annotations for boilerplate code
- JPA annotations for persistence

### 3. Repository Layer (TaskRepository.java)
- Extends JpaRepository<Task, Long>
- Custom query method: findByStatus(String status)

### 4. Service Layer (TaskService.java)
- getAllTasks() - retrieve all tasks
- getTasksByStatus(String status) - filter by status
- createTask(Task task) - create new task
- updateTask(Long id, Task updatedTask) - update existing task
- deleteTask(Long id) - delete task by ID

### 5. Controller Layer (TaskController.java)
- POST /tasks - create task
- GET /tasks - list all tasks
- GET /tasks?status=pending - filter by status
- PUT /tasks/{id} - update task
- DELETE /tasks/{id} - delete task

### 6. Configuration (application.properties)
- H2 database configuration
- JPA/Hibernate settings
- H2 console enablement

## 🎯 API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /tasks | Create a new task |
| GET | /tasks | Get all tasks |
| GET | /tasks?status=pending | Get tasks by status |
| PUT | /tasks/{id} | Update a task |
| DELETE | /tasks/{id} | Delete a task |

## 🚀 Next Steps
1. Create Maven project structure
2. Implement all Java classes
3. Configure application properties
4. Test the application
5. Optional: Add unit tests

## 📋 Task Status Values
- "pending" - Task not yet completed
- "done" - Task completed

This plan will result in a fully functional REST API for task management with all CRUD operations implemented.