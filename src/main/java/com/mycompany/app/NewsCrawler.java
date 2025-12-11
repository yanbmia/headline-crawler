package com.mycompany.app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Generic web crawler for tracking news headlines from various sources
 */
public class NewsCrawler {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";
    private static final int MAX_HEADLINES_PER_SOURCE = 25;

    /**
     * Fetches headlines from New York Times
     */
    public List<Headline> fetchNYTimesHeadlines() throws IOException {
        List<Headline> headlines = new ArrayList<>();
        Set<String> seenUrls = new HashSet<>();
        String url = "https://www.nytimes.com";
        
        Document doc = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .timeout(10000)
                .get();
        
        Elements links = doc.select("a[href]");
        
        for (Element link : links) {
            if (headlines.size() >= MAX_HEADLINES_PER_SOURCE) {
                break;
            }
            
            String href = link.attr("abs:href");
            String title = link.text().trim();
            
            if (href.contains("/2024/") || href.contains("/2025/")) {
                if (href.matches(".*/(politics|world|business|technology|sports|opinion)/.*") 
                    && !seenUrls.contains(href) 
                    && title.length() > 20) {
                    
                    headlines.add(new Headline(title, href, "", "NY Times"));
                    seenUrls.add(href);
                }
            }
        }
        
        return headlines;
    }
    
    /**
     * Fetches headlines from Fox News
     */
    public List<Headline> fetchFoxNewsHeadlines() throws IOException {
        List<Headline> headlines = new ArrayList<>();
        Set<String> seenUrls = new HashSet<>();
        String url = "https://www.foxnews.com";
        
        Document doc = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .timeout(10000)
                .get();
        
        // Try multiple selectors for Fox News
        Elements articleLinks = doc.select("article a, h2.title a, h3 a");
        
        for (Element link : articleLinks) {
            if (headlines.size() >= MAX_HEADLINES_PER_SOURCE) {
                break;
            }
            
            String href = link.attr("abs:href");
            String title = link.text().trim();
            
            if (href.contains("foxnews.com") 
                && !seenUrls.contains(href) 
                && title.length() > 20
                && !href.contains("/video/")
                && !href.contains("/media/")) {
                
                headlines.add(new Headline(title, href, "", "Fox News"));
                seenUrls.add(href);
            }
        }
        
        return headlines;
    }
    
    /**
     * Fetches headlines from AP News
     */
    public List<Headline> fetchAPNewsHeadlines() throws IOException {
        List<Headline> headlines = new ArrayList<>();
        Set<String> seenUrls = new HashSet<>();
        String url = "https://apnews.com";
        
        Document doc = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .timeout(10000)
                .get();
        
        // AP News - look for article links
        Elements links = doc.select("a[href]");
        
        for (Element link : links) {
            if (headlines.size() >= MAX_HEADLINES_PER_SOURCE) {
                break;
            }
            
            String href = link.attr("abs:href");
            String title = link.text().trim();
            
            // AP News article URLs - typically contain /article/
            if (href.contains("apnews.com/article/") 
                && !seenUrls.contains(href) 
                && title.length() > 20) {
                
                headlines.add(new Headline(title, href, "", "AP News"));
                seenUrls.add(href);
            }
        }
        
        return headlines;
    }
    
    /**
     * Fetches headlines from all sources
     */
    public List<Headline> fetchAllHeadlines() {
        List<Headline> allHeadlines = new ArrayList<>();
        
        System.out.println("Fetching headlines from multiple news sources...\n");
        
        // NY Times
        try {
            System.out.println("Crawling NY Times...");
            List<Headline> nyTimes = fetchNYTimesHeadlines();
            allHeadlines.addAll(nyTimes);
            System.out.println("✓ Found " + nyTimes.size() + " NY Times headlines\n");
        } catch (IOException e) {
            System.err.println("✗ Error fetching NY Times headlines: " + e.getMessage() + "\n");
        }
        
        // Fox News
        try {
            System.out.println("Crawling Fox News...");
            List<Headline> foxNews = fetchFoxNewsHeadlines();
            allHeadlines.addAll(foxNews);
            System.out.println("✓ Found " + foxNews.size() + " Fox News headlines\n");
        } catch (IOException e) {
            System.err.println("✗ Error fetching Fox News headlines: " + e.getMessage() + "\n");
        }
        
        // AP News
        try {
            System.out.println("Crawling AP News...");
            List<Headline> apNews = fetchAPNewsHeadlines();
            allHeadlines.addAll(apNews);
            System.out.println("✓ Found " + apNews.size() + " AP News headlines\n");
        } catch (IOException e) {
            System.err.println("✗ Error fetching AP News headlines: " + e.getMessage() + "\n");
        }
        
        return allHeadlines;
    }
    
    /**
     * Fetches headlines and prints them to the console
     */
    public void printAllHeadlines() {
        List<Headline> headlines = fetchAllHeadlines();
        
        if (headlines.isEmpty()) {
            System.out.println("No headlines found from any source.");
        } else {
            System.out.println("=".repeat(80));
            System.out.println("TOTAL: " + headlines.size() + " headlines from all sources");
            System.out.println("=".repeat(80));
            System.out.println();
            
            for (int i = 0; i < headlines.size(); i++) {
                Headline headline = headlines.get(i);
                System.out.println((i + 1) + ". [" + headline.getSource() + "] " + headline.getTitle());
                System.out.println("   " + headline.getUrl());
                if (!headline.getSummary().isEmpty()) {
                    System.out.println("   " + headline.getSummary());
                }
                System.out.println();
            }
            
            System.out.println("=".repeat(80));
        }
    }
}
