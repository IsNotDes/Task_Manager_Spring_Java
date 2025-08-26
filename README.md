# Task Manager API

A RESTful API built with Spring Boot for managing tasks with CRUD operations.

## 🚀 Features

- Create, read, update, and delete tasks
- Filter tasks by status (pending/done)
- H2 in-memory database for easy development
- RESTful API endpoints
- Lombok for reduced boilerplate code

## 🛠️ Technology Stack

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Maven**

## 📋 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/tasks` | Create a new task |
| GET | `/tasks` | Get all tasks |
| GET | `/tasks?status=pending` | Get tasks by status |
| GET | `/tasks/{id}` | Get a specific task |
| PUT | `/tasks/{id}` | Update a task |
| DELETE | `/tasks/{id}` | Delete a task |

## 🏃‍♂️ Running the Application

1. **Prerequisites**: Java 21 and Maven installed

2. **Clone and run**:
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application**:
   - API: http://localhost:8080/tasks
   - H2 Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:taskdb`
     - Username: `sa`
     - Password: (leave empty)

## 📝 Task Model

```json
{
  "id": 1,
  "title": "Complete project",
  "description": "Finish the Spring Boot task manager",
  "dueDate": "2024-01-15",
  "status": "pending"
}
```

### Status Values
- `pending` - Task not yet completed
- `done` - Task completed

## 🧪 Example API Calls

### Create a Task
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Spring Boot",
    "description": "Complete the tutorial",
    "dueDate": "2024-01-20",
    "status": "pending"
  }'
```

### Get All Tasks
```bash
curl http://localhost:8080/tasks
```

### Get Pending Tasks
```bash
curl http://localhost:8080/tasks?status=pending
```

### Update a Task
```bash
curl -X PUT http://localhost:8080/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Spring Boot",
    "description": "Complete the tutorial",
    "dueDate": "2024-01-20",
    "status": "done"
  }'
```

### Delete a Task
```bash
curl -X DELETE http://localhost:8080/tasks/1
```

## 🏗️ Project Structure

```
src/main/java/com/example/taskmanager/
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

## 🔮 Future Enhancements

- Add authentication with Spring Security
- Replace H2 with PostgreSQL/MySQL for production
- Add input validation and custom exception handling
- Implement pagination for large datasets
- Add frontend (React/Angular/Vue)
- Deploy to cloud platforms (Heroku, AWS, Azure)