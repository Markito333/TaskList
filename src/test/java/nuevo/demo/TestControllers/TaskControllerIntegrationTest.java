package nuevo.demo.TestControllers;

import nuevo.demo.controller.TaskController;
import nuevo.demo.model.Task;
import nuevo.demo.services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void getAllTasks_ShouldReturnTasks() throws Exception {
        Task task = new Task();
        task.setDescription("Test task");
        List<Task> allTasks = Arrays.asList(task);

        given(taskService.getAllTasks()).willReturn(allTasks);

        mockMvc.perform(get("/api/tasks"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].description").value("Test task"));
    }

    @Test
    void createTask_ShouldReturnCreatedTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("New task");

        when(taskService.createTask(any(String.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"New task\""))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.description").value("New task"));
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() throws Exception {
        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setDescription("Updated task");

        when(taskService.updateTask(anyLong(), any(String.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"Updated task\""))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.description").value("Updated task"));
    }

    @Test
    void deleteTask_ShouldReturnNoContent() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
               .andExpect(status().isNoContent());
    }

    @Test
    void toggleTaskCompletion_ShouldReturnToggledTask() throws Exception {
        Task toggledTask = new Task();
        toggledTask.setId(1L);
        toggledTask.setCompleted(true);

        when(taskService.toggleTaskCompletion(1L)).thenReturn(toggledTask);

        mockMvc.perform(patch("/api/tasks/1/toggle"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void createTask_WithEmptyDescription_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"\""))
               .andExpect(status().isBadRequest());
    }
}