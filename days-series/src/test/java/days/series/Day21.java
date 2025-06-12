package days.series;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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
import io.restassured.http.ContentType;

public class Day21 {

    /*
     * 1️⃣ Implement a Custom Comparator for Sorting Strings with Numbers
     * 📌 Question: Given a list of strings containing numbers, sort them based on
     * numerical values.
     * 🔹 Input: ["file20", "file1", "file3", "file10"]
     * 🔹 Output: ["file1", "file3", "file10", "file20"]
     */
    @Test
    public void sortStringsWithNumericValues() {
        List<String> list = List.of("file20", "file1", "file3", "file10");
        list = list.stream().sorted(Comparator.comparing(str -> Integer.parseInt(str.replaceAll("[\\D]", ""))))
                .toList();
        System.out.println(list);
    }

    /*
     * 2️⃣ Find the Maximum Product of Three Numbers in an Array
     * 📌 Question: Given an array of integers, find the maximum product of any
     * three numbers.
     * 🔹 Input: {1, 10, 2, 6, 5, 3}
     * 🔹 Output: 300 (from 10 × 6 × 5)
     */
    @Test
    public void findMaximumProductOf3Numbers() {
        int[] array = new int[] { 1, 10, 2, 6, 5, 3, -11, -7 };
        // Solution 1 - No suitable with negative numbers -10 x -6 * 7
        int product = Arrays.stream(array).boxed()
                .sorted(Collections.reverseOrder()).limit(3).reduce(1, (a, b) -> a * b);
        System.out.println(product);

        // Solution 2
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        for (int n : array) {
            if (n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (n > max2) {
                max3 = max2;
                max2 = n;
            } else if (n > max3) {
                max3 = n;
            }

            if (n < min1) {
                min2 = min1;
                min1 = n;
            } else if (n < min2) {
                min2 = n;
            }
        }

        System.out.println(Math.max(min1 * min2 * max1, max3 * max2 * max1));
    }

    /*
     * 3️⃣ Merge Two Sorted Maps Without Overwriting Common Keys
     * 📌 Question: Given two maps with integer values, merge them while summing
     * values for common keys.
     * 🔹 Input: {A=10, B=20, C=30}, {B=5, C=15, D=25}
     * 🔹 Output: {A=10, B=25, C=45, D=25}
     */
    @Test
    public void mergeTwoSortedMaps() {
        LinkedHashMap<String, Integer> map1 = new LinkedHashMap<>();
        map1.put("A", 10);
        map1.put("B", 20);
        map1.put("C", 30);
        LinkedHashMap<String, Integer> map2 = new LinkedHashMap<>();
        map2.put("B", 5);
        map2.put("C", 15);
        map2.put("D", 25);

        // Soltution 1
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>(map1);
        map2.forEach((key, value) -> result.merge(key, value, Integer::sum));
        System.out.println(result);

        // Soltution 2
        LinkedHashMap<String, Integer> result1 = Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum, LinkedHashMap::new));
        System.out.println(result1);

    }

    /*
     * 1️⃣ What Are the Different Types of API Authentication?
     * 📌 Question: What are the different authentication mechanisms used in API
     * testing?
     * 
     * 🔐 1️⃣ Basic Authentication
     * Sends a username and password encoded in Base64 with each API request.
     * Authorization: Basic <base64encoded(username:password)>
     * Pros: Simple to implement
     * Cons: Not secure unless used over HTTPS
     * 
     * 🔑 2️⃣ Bearer Token (Token-Based Authentication)
     * Sends a token (usually a JWT or opaque token) in the Authorization header:
     * Authorization: Bearer <token>
     * Token is usually obtained via login/auth endpoint and used for subsequent
     * requests.
     * Pros: Stateless, secure
     * Cons: Token expiration must be managed
     * 
     * 🛡️ 3️⃣ OAuth 2.0
     * Industry-standard protocol for authorization, not authentication.
     * Used widely in enterprise and social login APIs.
     * Involves access tokens and refresh tokens.
     * Supports multiple flows: Authorization Code, Client Credentials, etc.
     * Pros: Secure, flexible, supports scopes
     * Cons: More complex to implement
     * 
     * 🧩 4️⃣ API Key
     * A unique key (string) passed in the header, query param, or body:
     * GET /data?api_key=your_api_key
     * Identifies the calling project/application, not the user.
     * Pros: Easy to implement
     * Cons: Less secure than OAuth or token-based methods
     * 
     * 🔐 5️⃣ Digest Authentication
     * Hash-based authentication. Password is never sent in plaintext.
     * Uses a challenge-response mechanism (more secure than Basic Auth).
     * Rarely used today due to complexity and better alternatives.
     * 
     * 👁️ 6️⃣ HMAC (Hash-Based Message Authentication Code)
     * Uses a shared secret and cryptographic hashing to verify request integrity.
     * Common in APIs that require tamper-proof requests.
     * Example: AWS Signature authentication
     * 
     * 🧠 7️⃣ Mutual TLS (mTLS)
     * Both client and server present certificates to authenticate each other.
     * Very secure, often used in banking or enterprise-grade APIs.
     * Cons: Complex setup and certificate management
     */

    /*
     * 2️⃣ Validate Response Content Type in Rest Assured
     * 📌 Question: How do you verify that an API returns the expected content type?
     */
    @Test
    public void validateContentType() {
        RestAssured.get("https://dummyjson.com/posts").then().contentType(ContentType.JSON);
    }

    /*
     * 3️⃣ Handle Keyboard Actions in Selenium
     * 📌 Question: How do you perform keyboard actions like Tab or Enter in
     * Selenium WebDriver?
     */
    @Test
    public void handleKeyBoardActions() {
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized"));
        driver.get("https://www.timeanddate.com/");
        Actions actions = new Actions(driver);
        actions
                .moveToElement(driver.findElement(By.xpath("//a[text()='Calculators']")))
                .moveToElement(driver.findElement(By.xpath("//a[text()='Weekday Calculator']")))
                .click().perform();
        actions.click(driver.findElement(By.id("day"))).sendKeys("10")
                .sendKeys(Keys.TAB).sendKeys("06").sendKeys(Keys.TAB)
                .sendKeys("2025").sendKeys(Keys.TAB).perform();
        Assert.assertEquals(driver.findElement(By.id("longDate")).getText(), "10 June 2025");
        driver.quit();

    }

    /*
     * 4️⃣ What Is API Error Handling and Why Is It Important?
     * 📌 Question: What are common API error status codes, and how should they be
     * handled?
     * 
     * ✅ Answer:
     * API error handling is the process of detecting, communicating, and resolving
     * problems that occur when an API request fails. It ensures that clients (like
     * web apps or mobile apps) understand why something went wrong and how to
     * possibly recover from it
     * 
     * | Reason | Description |
     * | ----------------------------------- |
     * ------------------------------------------------------------------ |
     * | ✅ **Improves Developer Experience** | Clear errors help developers fix
     * problems faster. |
     * | ✅ **Boosts API Reliability** | Prevents clients from crashing on unexpected
     * errors. |
     * | ✅ **Enables Debugging** | Provides logs and messages for support and
     * diagnostics. |
     * | ✅ **Enhances Security** | Masks sensitive system internals with proper
     * error codes/messages. |
     * 
     * 
     * | Status Code | Meaning | Example Reason |
     * | ---------------------------- | ------------------------------------ |
     * -------------------------------------------------------- |
     * | `400 Bad Request` | Invalid input from client | Missing or malformed
     * parameters |
     * | `401 Unauthorized` | Authentication failed | Invalid or missing token/API
     * key |
     * | `403 Forbidden` | Access denied | Authenticated but not authorized |
     * | `404 Not Found` | Resource doesn't exist | Invalid endpoint or missing
     * resource |
     * | `405 Method Not Allowed` | Wrong HTTP method used | `POST` used instead of
     * `GET`, for example |
     * | `409 Conflict` | Request conflicts with current state | Duplicate data
     * (e.g., user already exists) |
     * | `415 Unsupported Media Type` | Wrong content type | `Content-Type:
     * text/plain` instead of `application/json` |
     * | `429 Too Many Requests` | Rate limit exceeded | Too many API calls in a
     * short period |
     * | `500 Internal Server Error` | Server-side bug or crash | Null pointer
     * exception, DB failure, etc. |
     * | `502/503/504` | Gateway/Service errors | Issues with upstream services |
     * 
     */

    /*
     * 5️⃣ Handle JavaScript Popups in Selenium
     * 📌 Question: How do you handle JavaScript alerts and popups in Selenium?
     */
    @Test
    public void handleAlerts() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.findElement(By.xpath("//*[text()='Click for JS Prompt']")).click();
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.sendKeys("Java Selenium Alert");
        alert.accept();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You entered: Java Selenium Alert");
        driver.quit();

    }

}
