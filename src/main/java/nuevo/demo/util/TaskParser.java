package nuevo.demo.util;

import nuevo.demo.model.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TaskParser {
    private static final Pattern MENTION_PATTERN = Pattern.compile("@(\\w+)");
    private static final Pattern TAG_PATTERN = Pattern.compile("#(\\w+)");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b");
    private static final Pattern LINK_PATTERN = Pattern.compile("(https?://|www\\.)\\S+");
    
    public Task parseTaskDescription(String description) {
        Task task = new Task();
        task.setDescription(description);
        
        List<String> mentions = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        List<String> emails = new ArrayList<>();
        List<String> links = new ArrayList<>();
        
        //(@)
        Matcher mentionMatcher = MENTION_PATTERN.matcher(description);
        while (mentionMatcher.find()) {
            mentions.add(mentionMatcher.group());
        }
        
        //(#)
        Matcher tagMatcher = TAG_PATTERN.matcher(description);
        while (tagMatcher.find()) {
            tags.add(tagMatcher.group());
        }
        
        //emails
        Matcher emailMatcher = EMAIL_PATTERN.matcher(description);
        while (emailMatcher.find()) {
            emails.add(emailMatcher.group());
        }
        
        //links
        Matcher linkMatcher = LINK_PATTERN.matcher(description);
        while (linkMatcher.find()) {
            String link = linkMatcher.group();
            if (!link.startsWith("http")) {
                link = "https://" + link;
            }
            links.add(link);
        }
        
        task.setMentions(mentions);
        task.setTags(tags);
        task.setEmails(emails);
        task.setLinks(links);
        
        return task;
    }
}