package nuevo.demo.TaskServicesTest;

import nuevo.demo.model.Task;
import nuevo.demo.repository.TaskRepository;
import nuevo.demo.services.TaskService;
import nuevo.demo.util.TaskParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock private TaskRepository taskRepository;
    @Mock private TaskParser taskParser;
    
    @InjectMocks private TaskService taskService;
    
    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setDescription("Sample task");
    }

    @Test
    void getAllTasks_ShouldReturnAllTasksOrderedByDate() {
        Task olderTask = new Task();
        olderTask.setDescription("Older task");
        
        when(taskRepository.findAllByOrderByCreatedAtDesc()).thenReturn(Arrays.asList(sampleTask, olderTask));
        
        List<Task> result = taskService.getAllTasks();
        
        assertEquals(2, result.size());
        assertEquals("Sample task", result.get(0).getDescription());
    }

    @Test
    void createTask_ShouldParseAndSaveTask() {
        when(taskParser.parseTaskDescription("New task")).thenReturn(sampleTask);
        when(taskRepository.save(sampleTask)).thenReturn(sampleTask);
        
        Task result = taskService.createTask("New task");
        
        assertNotNull(result);
        verify(taskRepository).save(sampleTask);
    }

    @Test
    void updateTask_ShouldUpdateExistingTask() {
        Task updatedTask = new Task();
        updatedTask.setDescription("Updated task");
        
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskParser.parseTaskDescription("Updated task")).thenReturn(updatedTask);
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        
        Task result = taskService.updateTask(1L, "Updated task");
        
        assertEquals("Updated task", result.getDescription());
    }

    @Test
    void updateTask_WithNonExistingId_ShouldThrowException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            taskService.updateTask(99L, "Task");
        });
    }

    @Test
    void deleteTask_ShouldDeleteExistingTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        doNothing().when(taskRepository).delete(sampleTask);
        
        taskService.deleteTask(1L);
        
        verify(taskRepository).delete(sampleTask);
    }

    @Test
    void toggleTaskCompletion_ShouldToggleStatus() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Task result = taskService.toggleTaskCompletion(1L);
        
        assertTrue(result.isCompleted());
        
        // Second toggle
        Task secondResult = taskService.toggleTaskCompletion(1L);
        assertFalse(secondResult.isCompleted());
    }
}