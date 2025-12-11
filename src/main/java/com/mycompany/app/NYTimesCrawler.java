package com.mycompany.app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Web crawler for tracking New York Times headlines
 */
public class NYTimesCrawler {
    private static final String NY_TIMES_URL = "https://www.nytimes.com";
    private static final int TIMEOUT_MS = 10000;
    
    /**
     * Fetches the current headlines from the New York Times homepage
     * @return List of NYTimesHeadline objects
     * @throws IOException if there's an error connecting to the website
     */
    public List<NYTimesHeadline> fetchHeadlines() throws IOException {
        List<NYTimesHeadline> headlines = new ArrayList<>();
        
        // Connect to NY Times and parse the HTML
        Document doc = Jsoup.connect(NY_TIMES_URL)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36")
                .timeout(TIMEOUT_MS)
                .get();
        
        // Find all links on the page and filter for article links
        Elements links = doc.select("a[href]");
        
        for (Element link : links) {
            try {
                String href = link.attr("href");
                String text = link.text().trim();
                
                // Filter for article links (usually contain /YYYY/MM/DD/ pattern or section names)
                // Also check if the link has meaningful text
                if (text.length() > 20 && 
                    (href.contains("/2024/") || href.contains("/2025/") || 
                     href.matches(".*/\\d{4}/\\d{2}/\\d{2}/.*"))) {
                    
                    // Make URL absolute if it's relative
                    String fullUrl = href.startsWith("http") ? href : NY_TIMES_URL + href;
                    
                    // Try to find summary in parent or sibling elements
                    String summary = "";
                    Element parent = link.parent();
                    if (parent != null) {
                        Element summaryElement = parent.selectFirst("p");
                        if (summaryElement != null) {
                            summary = summaryElement.text();
                        }
                    }
                    
                    // Avoid duplicates
                    boolean isDuplicate = headlines.stream()
                        .anyMatch(h -> h.getUrl().equals(fullUrl));
                    
                    if (!isDuplicate) {
                        headlines.add(new NYTimesHeadline(text, fullUrl, summary));
                    }
                }
            } catch (Exception e) {
                // Skip this link if there's an error parsing it
                continue;
            }
        }
        
        return headlines;
    }
    
    /**
     * Fetches headlines and prints them to the console
     */
    public void printHeadlines() {
        try {
            System.out.println("Fetching headlines from New York Times...\n");
            List<NYTimesHeadline> headlines = fetchHeadlines();
            
            if (headlines.isEmpty()) {
                System.out.println("No headlines found. The website structure may have changed.");
            } else {
                System.out.println("Found " + headlines.size() + " headlines:\n");
                System.out.println("=".repeat(80));
                
                for (int i = 0; i < headlines.size(); i++) {
                    NYTimesHeadline headline = headlines.get(i);
                    System.out.println((i + 1) + ". " + headline.getTitle());
                    System.out.println("   " + headline.getUrl());
                    if (!headline.getSummary().isEmpty()) {
                        System.out.println("   " + headline.getSummary());
                    }
                    System.out.println();
                }
                
                System.out.println("=".repeat(80));
            }
        } catch (IOException e) {
            System.err.println("Error fetching headlines: " + e.getMessage());
            System.err.println("Please check your internet connection and try again.");
        }
    }
}
