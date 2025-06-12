package days.series;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;

import java.time.Duration;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Day17 {

    /*
     * 1️⃣ Find All Unique Permutations of a String
     * 📌 Question: Given a string, return all unique permutations.
     * 🔹 Input: "aba"
     * 🔹 Output: ["aba", "aab", "baa"]
     */

    /*
     * 2️⃣ Find the First Non-Repeating Character in a String
     * 📌 Question: Given a string, return the first character that appears only
     * once.
     * 🔹 Input: "swiss"
     * 🔹 Output: 'w'
     */
    @Test
    public void firstNonRepeatingCharacter() {
        String str = "swiss";
        LinkedHashMap<Character, Integer> linkedHashMap = new LinkedHashMap<>();
        for (char ch : str.toCharArray()) {
            linkedHashMap.put(ch, linkedHashMap.getOrDefault(ch, 0) + 1);
        }

        linkedHashMap.entrySet().stream().filter(entry -> entry.getValue() == 1).findFirst()
                .ifPresent(entry -> System.out.println(entry.getKey()));
    }

    /*
     * 3️⃣ Sort a List of Strings by Length Using Lambda
     * 📌 Question: Sort a list of strings based on their length.
     * 🔹 Input: ["apple", "kiwi", "banana", "grape"]
     * 🔹 Output: ["kiwi", "grape", "apple", "banana"]
     */
    @Test
    public void sortListBasedOnLength() {
        List<String> words = List.of("apple", "kiwi", "banana", "grape");
        words = words.stream().sorted(Comparator.comparingInt(String::length)).toList();
        System.out.println(words);
    }

    /*
     * 1️⃣ What Is the Difference Between SOAP and REST APIs?
     * 📌 Question: How does SOAP differ from REST in API architecture?
     * 
     * ✅ Answer:
     * SOAP (Simple Object Access Protocol): Uses XML, follows strict structure,
     * supports WS-Security.
     * REST (Representational State Transfer): Uses JSON/XML, lightweight, supports
     * multiple methods (GET, POST, etc.).
     * Protocol
     * SOAP: Strict protocol (uses XML-based messaging)
     * REST: Architectural style (uses HTTP and flexible formats)
     * 
     * Message Format
     * SOAP: XML only
     * REST: XML, JSON, HTML, plain text (commonly JSON)
     * 
     * Transport Protocol
     * SOAP: Can use HTTP, SMTP, TCP, etc.
     * REST: Uses only HTTP
     * 
     * Standards
     * SOAP: Follows strict standards (WSDL, WS-Security, WS-ReliableMessaging)
     * REST: No official standards; relies on HTTP methods (GET, POST, PUT, DELETE)
     * 
     * Performance
     * SOAP: Heavier, more overhead due to XML and envelope wrapping
     * REST: Lightweight, faster due to minimal formatting (often JSON)
     * 
     * Security
     * SOAP: Built-in standards like WS-Security
     * REST: Depends on underlying transport (HTTPS + OAuth/JWT)
     * 
     * Statefulness
     * SOAP: Can be stateful or stateless
     * REST: Always stateless
     * 
     * Use Cases
     * SOAP: Enterprise apps, legacy systems, banking, contracts
     * REST: Web APIs, mobile apps, microservices, public APIs
     * 
     * 
     * 
     * Use SOAP when you need strict standards, formal contracts, and robust
     * security.
     * Use REST when you want simplicity, speed, and flexibility.
     * 🎯 Key Takeaway: REST is more flexible & scalable, while SOAP is better for
     * secure transactions.
     */

    /*
     * 2️⃣ Validate JSON Array Contains a Specific Object in Rest Assured
     * 📌 Question: How do you verify if a JSON array contains a specific object?
     */
    @Test
    public void validateJSONArray() {
        RestAssured.given()
                .baseUri("https://dummyjson.com")
                .get("/recipes")
                .then()
                .body("recipes.find {it.name == 'South Indian Masala Dosa'}.ingredients",
                        hasItem("Potatoes, boiled and mashed"))
                .body("recipes.find {it.name == 'South Indian Masala Dosa'}.cuisine", equalTo("Indian"))
                .body("recipes.find {it.difficulty == 'Medium'}.size()", greaterThanOrEqualTo(16));
    }

    /*
     * 3️⃣ Handle Browser Zoom Level Issues in Selenium
     * 📌 Question: How do you reset browser zoom to 100% in Selenium WebDriver?
     * 
     * via BrowserOptions
     * via document.body.style.zoom='200%' this will change the css and zoom the
     * element in DOM (i.e., body)
     */
    @Test
    public void handleZoomLevel() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("force-device-scale-factor=2");
        options.addArguments("high-dpi-support=2");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://dummyjson.com");

        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='200%'");
        driver.quit();
    }

    /*
     * 4️⃣ What Is Rate Limiting in APIs and Why Is It Used?
     * 📌 Question: What is rate limiting, and why do APIs enforce it?
     * 
     * ✅ Answer:
     * Rate Limiting controls the number of API requests allowed within a timeframe.
     * Helps prevent DDoS attacks and ensure fair usage.
     * Rate limiting is a mechanism used to control the number of requests a client
     * can make to an API within a specific time frame (e.g., 100 requests per
     * minute).
     * 
     * 🔒 Prevent Abuse or Misuse
     * Stops users from spamming the API (intentional or accidental)
     * 
     * ⚖️ Ensure Fair Usage
     * Distributes access fairly among users or apps
     * 
     * 💸 Control Costs
     * Especially in paid APIs, it limits usage based on pricing tiers
     * 
     * Protect Against DDoS Attacks
     * Limits potential damage from denial-of-service or bot attacks
     * 
     * If an API allows 60 requests per minute, and you send 61, the 61st request
     * may:
     * Return HTTP 429 Too Many Requests
     * Be delayed or rejected
     * 
     * Retry-After header: tells when you can try again
     * Rate-Limit headers: indicate usage (limit, remaining, reset time)
     * Headers like X-RateLimit-Limit, X-RateLimit-Remaining provide usage details.
     * 🎯 Key Takeaway: APIs enforce rate limits to protect resources & ensure fair
     * access.
     */

    /*
     * 5️⃣ Handle Auto-Suggestions in Selenium
     * 📌 Question: How do you select a specific value from an auto-suggestion
     * dropdown?
     */
    @Test
    public void handleAutoSuggestions() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.makemytrip.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-cy='closeModal']"))).click();

        By fromCitySelector = By.id("fromCity");
        driver.findElement(fromCitySelector).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='fromCity'][ancestor::*[contains(@class, 'activeWidget')]]")));
        driver.findElement(By.cssSelector("[placeholder='From']")).sendKeys("Ban");
        WebElement fromElement = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Bandung, Indonesia']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoViewIfNeeded();", fromElement);
        fromElement.click();
        Assert.assertEquals(driver.findElement(fromCitySelector).getAttribute("value"), "Bandung");
        driver.quit();

    }
}
