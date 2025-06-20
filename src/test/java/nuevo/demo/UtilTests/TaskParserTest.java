package nuevo.demo.UtilTests;

import nuevo.demo.model.Task;
import nuevo.demo.util.TaskParser;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TaskParserTest {

    private final TaskParser taskParser = new TaskParser();

    @Test
    void parseTaskDescription_ShouldExtractMentions() {
        String description = "Task with @mention and @another";
        Task task = taskParser.parseTaskDescription(description);
        
        assertEquals(List.of("@mention", "@another"), task.getMentions());
        assertEquals(description, task.getDescription());
    }

    @Test
    void parseTaskDescription_ShouldExtractTags() {
        String description = "Important #task and #urgent";
        Task task = taskParser.parseTaskDescription(description);
        
        assertEquals(List.of("#task", "#urgent"), task.getTags());
    }

    @Test
    void parseTaskDescription_ShouldExtractEmails() {
        String description = "Contact user@example.com and admin@test.com";
        Task task = taskParser.parseTaskDescription(description);
        
        assertEquals(List.of("user@example.com", "admin@test.com"), task.getEmails());
    }

    @Test
    void parseTaskDescription_ShouldExtractLinks() {
        String description = "Visit https://example.com and www.test.com";
        Task task = taskParser.parseTaskDescription(description);
        
        assertEquals(List.of("https://example.com", "https://www.test.com"), task.getLinks());
    }

    @Test
    void parseTaskDescription_WithMixedContent() {
        String description = "@team Please review #PR-123. Docs at https://docs.com";
        Task task = taskParser.parseTaskDescription(description);
        
        assertAll(
            () -> assertEquals(List.of("@team"), task.getMentions()),
            () -> assertEquals(List.of("#PR-123"), task.getTags()),
            () -> assertEquals(List.of("https://docs.com"), task.getLinks()),
            () -> assertTrue(task.getEmails().isEmpty())
        );
    }

    @Test
    void parseTaskDescription_WithEmptyString() {
        Task task = taskParser.parseTaskDescription("");
        
        assertAll(
            () -> assertTrue(task.getMentions().isEmpty()),
            () -> assertTrue(task.getTags().isEmpty()),
            () -> assertTrue(task.getEmails().isEmpty()),
            () -> assertTrue(task.getLinks().isEmpty())
        );
    }
}