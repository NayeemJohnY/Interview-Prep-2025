package days.series;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasKey;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import io.restassured.module.jsv.JsonSchemaValidator;

public class Day24 {

    /*
     * 1️⃣ Detect and Remove Invalid URLs from a List
     * 📌 Scenario: Your application processes user-submitted URLs. Remove invalid
     * URLs from the list based on a given pattern. A valid URL must start with
     * http:// or https://.
     * 
     * 🔹 Input:
     * ["https://google.com", "ftp://invalid.com", "http://example.com", "abcd.com"]
     * 🔹 Output:
     * ["https://google.com", "http://example.com"]
     */
    @Test
    public void removeInvalidURLs() {
        List<String> list = List.of("https://google.com", "ftp://invalid.com", "http://example.com", "abcd.com");
        Pattern pattern = Pattern.compile("^https?://.*$");
        System.out.println(list.stream().filter(str -> pattern.matcher(str).matches()).toList());
    }

    /*
     * 2️⃣ Find the Most Frequent Error Code in Logs
     * 📌 Scenario: Your application generates logs in the format
     * "TIMESTAMP - ERROR_CODE - MESSAGE". Find the most common error code from a
     * given log list.
     * 🔹 Input:
     * logs = ["2024-03-01 10:00 - 404 - Page Not Found",
     * "2024-03-01 10:05 - 500 - Internal Server Error",
     * "2024-03-01 10:10 - 404 - Resource Not Found",
     * "2024-03-01 10:15 - 500 - Server Crash",
     * "2024-03-01 10:20 - 404 - Bad Request"]
     * 🔹 Output:
     * "404"
     */
    @Test
    public void findMostFrequentErrorCodeInLogs() {
        List<String> logs = List.of(
                "2024-03-01 10:00 - 404 - Page Not Found",
                "2024-03-01 10:05 - 500 - Internal Server Error",
                "2024-03-01 10:10 - 404 - Resource Not Found",
                "2024-03-01 10:15 - 500 - Server Crash",
                "2024-03-01 10:20 - 404 - Bad Request");
        HashMap<Integer, Integer> errorCodeFreq = new HashMap<>();
        for (String log : logs) {
            int code = Integer.parseInt(log.split(" - ")[1]);
            errorCodeFreq.put(code, errorCodeFreq.getOrDefault(code, 0) + 1);
        }

        errorCodeFreq.entrySet().stream()
                .max(Map.Entry.comparingByValue()).ifPresent(entry -> System.out.println(entry.getKey()));

    }

    /*
     * 3️⃣ Merge Employee Attendance Records from Multiple Sources
     * 📌 Scenario: Your company has attendance data from multiple departments in
     * the format {EmployeeID, Days Present}. Merge the records and sum attendance
     * for duplicate employees.
     * 
     * 🔹 Input:
     * dept1 = { "E101"=20, "E102"=22, "E103"=18 }
     * dept2 = { "E102"=5, "E104"=15, "E101"=3 }
     * 🔹 Output:
     * { "E101"=23, "E102"=27, "E103"=18, "E104"=15 }
     */
    @Test
    public void mergeEmployeeAttendance() {
        LinkedHashMap<String, Integer> map1 = new LinkedHashMap<>();
        map1.put("E101", 20);
        map1.put("E102", 22);
        map1.put("E103", 18);
        LinkedHashMap<String, Integer> map2 = new LinkedHashMap<>();
        map2.put("E102", 5);
        map2.put("E104", 15);
        map2.put("E101", 3);

        LinkedHashMap<String, Integer> result = new LinkedHashMap<>(map1);
        map2.forEach((key, value) -> result.merge(key, value, Integer::sum));
        System.out.println(result);
    }

    /*
     * 1️⃣ How to Perform Query Parameter Validation in Rest Assured?
     * 📌 Question: How do you verify that an API correctly handles query
     * parameters?
     */
    @Test
    public void handleQueryParam() {
        int limit = 10, skip = 10;
        RestAssured.given().when()
                .queryParam("skip", 10)
                .queryParam("limit", 10)
                .queryParam("select", "title,price")
                .get("https://dummyjson.com/products")
                .then()
                .statusCode(200)
                .body("products.size()", equalTo(limit))
                .body("products.id", equalTo(IntStream.rangeClosed(skip + 1, skip + limit).boxed().toList()))
                .body("products", everyItem(hasKey("title")))
                .body("products", everyItem(hasKey("price")));

    }

    /*
     * 2️⃣ How to Handle Checkboxes in Selenium?
     * 📌 Question: How do you verify and interact with checkboxes in Selenium
     * WebDriver?
     */
    @Test
    public void handleCheckBoxes() {
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized"));
        driver.get("https://demoqa.com/checkbox");
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='Expand all']")))
                .click();

        WebElement ele = driver.findElement(By.xpath("//*[text()='Office']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoViewIfNeeded()", ele);
        ele.click();
        for (WebElement item : driver
                .findElements(By.xpath("//*[text()='Office']/ancestor::li[1]/descendant::li//input"))) {
            Assert.assertTrue(item.isSelected());
        }
        driver.quit();
    }

    /*
     * 3️⃣ What Is Response Caching in APIs?
     * 📌 Question: What is API response caching, and how does it improve
     * performance?
     * 
     * API response caching refers to the practice of storing the results of
     * expensive or frequently requested API calls for a certain period of time.
     * When the same request is made again, the cached response is returned instead
     * of recalculating or retrieving the data from the original source (such as a
     * database or external service). This can significantly improve the performance
     * of your API by reducing the time and resources needed to generate responses.
     * 
     * Common Caching Strategies:
     * Cache-Control Header: This HTTP header is used by APIs to define caching
     * behavior.
     * max-age, public, private, no-cache, s-maxage
     * 
     * ETags (Entity Tags): An ETag is a unique identifier (often a hash) associated
     * with the content of a resource. When a client makes a request, the server can
     * compare the ETag to check if the resource has changed.
     * 
     * If the ETag is unchanged, the server can respond with a 304 Not Modified
     * status code, telling the client to use its cached copy instead of fetching
     * the data again.
     *
     * Last-Modified Header:
     * 
     * Time-to-Live (TTL):
     * 
     * Benefits of API Response Caching:
     * Improved Performance: Reduced Latency, Less Load on Backend Systems, Reduced
     * API Traffic, Scalability, Cost Efficiency
     */

    /*
     * 4️⃣ How to Perform Drag-and-Drop in Selenium?
     * 📌 Question: How do you handle drag-and-drop actions in Selenium WebDriver?
     */
    @Test
    public void dragAndDrop() {
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized"));
        driver.get("https://demoqa.com/droppable");
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("droppable"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoViewIfNeeded()", droppable);
        new Actions(driver).clickAndHold(draggable).dragAndDrop(draggable, droppable).release(draggable).build()
                .perform();
        Assert.assertTrue(
                driver.findElement(By.xpath("//*[@id='droppable' and descendant::text()='Dropped!']")).isDisplayed());

        driver.quit();
    }

    /*
     * 
     * 5️⃣ How to Validate JSON Schema in Rest Assured?
     * 📌 Question: How do you validate if an API response matches the expected JSON
     * schema?
     */
    @Test
    public void validateJSONSchema() throws FileNotFoundException {
        RestAssured.given().get("https://jsonplaceholder.typicode.com/users/1").then()
                .body(JsonSchemaValidator
                        .matchesJsonSchema(new FileInputStream("./src/main/resources/json_schema.json")));

    }
}
