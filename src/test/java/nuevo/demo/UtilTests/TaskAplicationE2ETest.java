package nuevo.demo.UtilTests;

import nuevo.demo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
class TaskApplicationE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void cleanUp() {
        taskRepository.deleteAll();
    }

    @Test
    void whenCreateTask_thenGetTaskReturnsIt() {
        // Create task
        given()
            .port(port)
            .contentType("application/json")
            .body("{\"description\":\"E2E Test Task\"}")
        .when()
            .post("/api/tasks")
        .then()
            .statusCode(200)
            .body("description", equalTo("E2E Test Task"));

        // Get all tasks
        given()
            .port(port)
        .when()
            .get("/api/tasks")
        .then()
            .statusCode(200)
            .body("size()", equalTo(1))
            .body("[0].description", equalTo("E2E Test Task"));
    }

    @Test
    void whenCreateAndUpdateTask_thenTaskIsUpdated() {
        // Create task
        Long taskId = given()
            .port(port)
            .contentType("application/json")
            .body("{\"description\":\"Original Task\"}")
        .when()
            .post("/api/tasks")
        .then()
            .extract().path("id");

        // Update task
        given()
            .port(port)
            .contentType("application/json")
            .body("{\"description\":\"Updated Task\"}")
        .when()
            .put("/api/tasks/" + taskId)
        .then()
            .statusCode(200)
            .body("description", equalTo("Updated Task"));

        // Verify update
        given()
            .port(port)
        .when()
            .get("/api/tasks")
        .then()
            .body("[0].description", equalTo("Updated Task"));
    }

    @Test
    void whenCreateAndDeleteTask_thenTaskIsRemoved() {
        // Create task
        Long taskId = given()
            .port(port)
            .contentType("application/json")
            .body("{\"description\":\"To be deleted\"}")
        .when()
            .post("/api/tasks")
        .then()
            .extract().path("id");

        // Delete task
        given()
            .port(port)
        .when()
            .delete("/api/tasks/" + taskId)
        .then()
            .statusCode(204);

        // Verify deletion
        given()
            .port(port)
        .when()
            .get("/api/tasks")
        .then()
            .body("size()", equalTo(0));
    }

    @Test
    void whenToggleTask_thenCompletionStatusChanges() {
        // Create task
        Long taskId = given()
            .port(port)
            .contentType("application/json")
            .body("{\"description\":\"Toggle test\"}")
        .when()
            .post("/api/tasks")
        .then()
            .extract().path("id");

        // Toggle task
        given()
            .port(port)
        .when()
            .patch("/api/tasks/" + taskId + "/toggle")
        .then()
            .statusCode(200)
            .body("completed", equalTo(true));

        // Verify toggle
        given()
            .port(port)
        .when()
            .get("/api/tasks")
        .then()
            .body("[0].completed", equalTo(true));
    }
}