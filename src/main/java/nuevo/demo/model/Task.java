package nuevo.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private boolean completed = false;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_tags", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_mentions", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "mention")
    private List<String> mentions = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_emails", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "email")
    private List<String> emails = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_links", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "link")
    private List<String> links = new ArrayList<>();
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public List<String> getMentions() { return mentions; }
    public void setMentions(List<String> mentions) { this.mentions = mentions; }
    public List<String> getEmails() { return emails; }
    public void setEmails(List<String> emails) { this.emails = emails; }
    public List<String> getLinks() { return links; }
    public void setLinks(List<String> links) { this.links = links; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    @PrePersist
    protected void onCreate() { 
        createdAt = LocalDateTime.now(); 
    }
    
    @PreUpdate
    protected void onUpdate() { 
        updatedAt = LocalDateTime.now(); 
    }
}