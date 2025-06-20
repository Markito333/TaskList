package nuevo.demo.controller;

import nuevo.demo.model.Task;
import nuevo.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    private final TaskService taskService;
    
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }
    
   @PostMapping
public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
    return ResponseEntity.ok(taskService.createTask(request.getDescription()));
}
    
   @PutMapping("/{id}")
public ResponseEntity<Task> updateTask(
        @PathVariable Long id,
        @RequestBody TaskRequest request) {
    return ResponseEntity.ok(taskService.updateTask(id, request.getDescription()));
}
public static class TaskRequest {
    private String description;
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Task> toggleTaskCompletion(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.toggleTaskCompletion(id));
    }
}
