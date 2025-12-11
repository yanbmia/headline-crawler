package com.mycompany.app;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a news headline from any source
 */
public class Headline {
    private final String title;
    private final String url;
    private final String summary;
    private final String source;
    private final LocalDateTime timestamp;
    private final LocalDateTime timestamp;
    
    public Headline(String title, String url, String summary, String source) {
        this.title = title;
        this.url = url;
        this.summary = summary;
        this.source = source;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public String getSource() {
        return source;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Headline that = (Headline) o;
        return Objects.equals(title, that.title) && 
               Objects.equals(url, that.url);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] [%s] %s\n  URL: %s\n  Summary: %s\n", 
            timestamp, source, title, url, summary);
    }
}
