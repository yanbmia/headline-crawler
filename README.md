# New York Times Headline Crawler

A Java web crawler that tracks and displays headlines from the New York Times website.

## Features

- Fetches current headlines from NYTimes.com
- Displays article titles, URLs, and summaries
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
│                   ├── App.java              # Main application entry point
│                   ├── NYTimesCrawler.java   # Web crawler implementation
│                   └── NYTimesHeadline.java  # Headline data model
└── test/
    └── java/
        └── com/
            └── mycompany/
                └── app/
                    └── AppTest.java
```

## How It Works

1. **NYTimesHeadline**: A data model class that represents a single headline with:
   - Title
   - URL
   - Summary
   - Timestamp

2. **NYTimesCrawler**: The main crawler class that:
   - Connects to NYTimes.com with a custom user agent
   - Parses HTML content using Jsoup
   - Filters links to find article URLs (based on date patterns)
   - Extracts headline text and summaries
   - Returns a list of headline objects

3. **App**: The main application that:
   - Creates a crawler instance
   - Fetches and displays headlines in a formatted output

## Output Example

```
Fetching headlines from New York Times...

Found 37 headlines:

================================================================================
1. U.S. Seizes Oil Tanker Off Venezuela, Escalating Pressure on Maduro
   https://www.nytimes.com/2025/12/10/us/politics/oil-tanker-seized-us-venezuela-trump.html

2. Fed Cuts Rates Again, but Is Divided Over Future Moves
   https://www.nytimes.com/2025/12/10/business/economy/fed-meeting-interest-rate-decision.html

...
================================================================================
```

## Notes

- Website structure may change over time, requiring updates to the selector logic


## License

This project was made for learning purposes.
