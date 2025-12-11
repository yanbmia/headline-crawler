# Multi-Source News Headline Crawler

A Java web crawler that tracks and displays headlines from multiple major news sources including The New York Times, Fox News, and AP News.

## Features

- Fetches current headlines from multiple news sources simultaneously
  - **NY Times** (nytimes.com) - Top 25 headlines
  - **Fox News** (foxnews.com) - Top 25 headlines
  - **AP News** (apnews.com) - Top 25 headlines
- Displays article titles, URLs, summaries, and source attribution
- Uses Jsoup for HTML parsing
- Built with Maven for easy dependency management

## Prerequisites

- Java 17 or higher
- Maven 3.x

## Dependencies

- **Jsoup 1.17.2**: HTML parsing library for web scraping
- **Gson 2.10.1**: JSON processing library (for potential future enhancements)
- **JUnit 5.11.0**: Testing framework

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd headline-crawler
```

2. Compile the project:
```bash
mvn clean compile
```

## Usage

Run the crawler:
```bash
mvn exec:java -Dexec.mainClass="com.mycompany.app.App"
```

Or package and run as a JAR:
```bash
mvn clean package
java -jar target/my-app-1.0-SNAPSHOT.jar
```

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── mycompany/
│               └── app/
│                   ├── App.java           # Main application entry point
│                   ├── NewsCrawler.java   # Multi-source web crawler implementation
│                   └── Headline.java      # Headline data model
└── test/
    └── java/
        └── com/
            └── mycompany/
                └── app/
                    └── AppTest.java
```

## How It Works

1. **Headline**: A data model class that represents a single headline with:
   - Title
   - URL
   - Summary
   - Source (NY Times, Fox News, or AP News)
   - Timestamp

2. **NewsCrawler**: The main crawler class that:
   - Connects to multiple news websites with a custom user agent
   - Parses HTML content using Jsoup with site-specific selectors
   - Extracts headline text, URLs, and summaries from each source
   - Handles errors gracefully (continues crawling other sources if one fails)
   - Returns a consolidated list of headlines from all sources

3. **App**: The main application that:
   - Creates a crawler instance
   - Fetches headlines from all sources
   - Displays results in a formatted, organized output

## Output Example

```
Fetching headlines from multiple news sources...

Crawling NY Times...
✓ Found 25 NY Times headlines

Crawling Fox News...
✓ Found 25 Fox News headlines

Crawling AP News...
✓ Found 25 AP News headlines

================================================================================
TOTAL: 75 headlines from all sources
================================================================================

1. [NY Times] U.S. Seizes Oil Tanker Off Venezuela, Escalating Pressure on Maduro
   https://www.nytimes.com/2025/12/10/us/politics/oil-tanker-seized-us-venezuela-trump.html

2. [Fox News] Trump warns Colombian President Gustavo Petro he will 'be next' amid drug trafficking rift
   https://www.foxnews.com/politics/trump-warns-colombias-president-gustavo-petro-he-will-next-drug-trafficking-rift

3. [AP News] Supreme Court weighs Tennessee ban on transgender care for minors
   https://apnews.com/article/supreme-court-transgender-youth-care

...
================================================================================
```

## Notes

- The crawler collects the **top 25 headlines** from each news source (75 total)
- The crawler uses site-specific selectors tailored for each news source
- Website structures may change over time, requiring updates to the selector logic
- The crawler handles failures gracefully - if one source fails, it continues with others
- Each headline is tagged with its source for easy identification
- Duplicate detection prevents the same article from appearing multiple times

## Supported News Sources

### NY Times (nytimes.com)
- Extracts articles with date-based URL patterns
- Filters for meaningful headline text (>20 characters)

### Fox News (foxnews.com)  
- Targets article headlines using title and header selectors
- Captures a wide range of news categories

### AP News (apnews.com)
- Extracts articles with /article/ URL pattern
- Clean, straightforward article identification


## License

This project was made for learning purposes.
