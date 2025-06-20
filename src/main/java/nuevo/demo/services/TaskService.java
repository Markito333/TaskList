package nuevo.demo.services;

import nuevo.demo.model.Task;
import nuevo.demo.repository.TaskRepository;
import nuevo.demo.util.TaskParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final TaskParser taskParser;
    
    @Autowired
    public TaskService(TaskRepository taskRepository, TaskParser taskParser) {
        this.taskRepository = taskRepository;
        this.taskParser = taskParser;
    }
    
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAllByOrderByCreatedAtDesc();
    }
    
    @Transactional
    public Task createTask(String description) {
        Task task = taskParser.parseTaskDescription(description);
        return taskRepository.save(task);
    }
    
    @Transactional
    public Task updateTask(Long id, String description) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        Task updatedTask = taskParser.parseTaskDescription(description);
        task.setDescription(updatedTask.getDescription());
        task.setTags(updatedTask.getTags());
        task.setMentions(updatedTask.getMentions());
        task.setEmails(updatedTask.getEmails());
        task.setLinks(updatedTask.getLinks());
        
        return taskRepository.save(task);
    }
    
    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        taskRepository.delete(task);
    }
    
    @Transactional
    public Task toggleTaskCompletion(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }
}