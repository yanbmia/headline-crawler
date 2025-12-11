package com.mycompany.app;

/**
 * Multi-Source News Headline Crawler
 */
public class App {
    public static void main(String[] args) {
        NewsCrawler crawler = new NewsCrawler();
        crawler.printAllHeadlines();
    }
}
