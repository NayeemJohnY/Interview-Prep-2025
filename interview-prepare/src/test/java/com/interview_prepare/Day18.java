package com.interview_prepare;

import static org.hamcrest.Matchers.hasItems;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Day18 {

    /*
     * 1️⃣ Find the Longest Common Substring
     * 📌 Question: Given two strings, find the longest common substring between
     * them.
     * 🔹 Input: "abcde", "abfce"
     * 🔹 Output: "ab"
     */
    @Test
    public void findLongestSubstring() {
        // Between 2 Strings
        String s1 = "Paragraph";
        String s2 = "geography";
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        int maxLength = 0;
        int endIndex = 0;
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        endIndex = i;
                    }
                }
            }
        }
        String longestSubstring = s1.substring(endIndex - maxLength, endIndex);
        System.out.println("Substring: " + longestSubstring);

        // Between list of Strings
        List<String> strings = List.of("paragraph", "ssddgrasdsdsw", "graphite", "intergraphical", "telegraph");
        String base = strings.get(0).toLowerCase();
        maxLength = base.length();
        for (int end = maxLength; end >= 0; end--) {
            for (int start = 0; start < end; start++) {
                String substr = base.substring(start, end);
                boolean allContain = true;
                for (int i = 1; i < strings.size(); i++) {
                    if (!strings.get(i).toLowerCase().contains(substr)) {
                        allContain = false;
                        break;
                    }
                }

                if (allContain) {
                    System.out.println("Substring: " + substr);
                    return;
                }
            }
        }
    }

    /*
     * 2️⃣ Find the Element That Appears Once in an Array
     * 📌 Question: Given an array where every element appears twice except one,
     * find the single element.
     * 🔹 Input: {2, 3, 5, 4, 5, 3, 2}
     * 🔹 Output: 4
     * 
     */
    @Test
    public void findUniqueElement() {
        int[] array = new int[] { 2, 3, 5, 4, 5, 3, 2 };
        HashMap<Integer, Integer> freqMap = new HashMap<>();
        for (int i : array) {
            freqMap.put(i, freqMap.getOrDefault(i, 0) + 1);
        }

        freqMap.entrySet().stream().filter(e -> e.getValue() == 1).findFirst()
                .ifPresent(e -> System.out.println(e.getKey()));

        // Solution 2
        int result = 0;
        for (int i : array) {
            result ^= i;
        }
        System.out.println(result);
    }

    /*
     * 3️⃣ Implement a Functional Interface to Convert List to Uppercase
     * 📌 Question: Implement an interface to convert a list of strings to
     * uppercase.
     * 🔹 Input: ["java", "automation", "testing"]
     * 🔹 Output: ["JAVA", "AUTOMATION", "TESTING"]
     * 
     */
    @FunctionalInterface
    interface TransformUpperCase {
        List<String> toUpper(List<String> s);
    }

    @Test
    public void testTransformUpperCase() {
        List<String> list = List.of("java", "automation", "testing");
        TransformUpperCase transform = li -> li.stream().map(String::toUpperCase).toList();
        System.out.println(transform.toUpper(list));
    }

    /*
     * 1️⃣ What Is API Caching and Why Is It Used?
     * 📌 Question: What is API caching, and how does it improve performance?
     * 
     * 
     * ✅ Answer:
     * 
     * ### 📌 **What is API Caching?**
     ** 
     * API caching** is the process of **storing copies of API responses**—either
     * fully or partially—so that future requests can be served **faster**, without
     * re-processing the same logic or fetching the same data again.
     * 
     * ---
     * 
     * ### 🧠 **Why Is It Used?**
     * 
     * API caching is used to:
     * 
     * 1. ✅ **Improve Performance**:
     * 
     * Cached responses are served **much faster** than regenerating data.
     * Reduces **latency**, especially for data that doesn't change often.
     * 
     * 2. ✅ **Reduce Server Load**:
     * 
     * Avoids repetitive database queries or complex calculations.
     * Helps scale by **minimizing redundant processing**.
     * 
     * 3. ✅ **Improve User Experience**:
     * 
     * Faster responses lead to **snappier apps** and lower time-to-interaction.
     * 
     * 4. ✅ **Save Bandwidth & Cost** (especially in cloud setups):
     * 
     * Fewer backend calls = **lower cloud service costs**.
     * 
     * ---
     * 
     * ### ⚙️ **Where Can API Caching Happen?**
     * 
     * | Level | Example |
     * | --------------- | -------------------------------- |
     * | **Client-side** | Browser stores GET responses |
     * | **Proxy/CDN** | Cloudflare, Akamai caches data |
     * | **Server-side** | Redis, Memcached in your backend |
     * 
     * ---
     * 
     * ### 🧪 Example:
     * 
     * Suppose your API returns weather data:
     * 
     * First request for `/weather/london` → API fetches from DB, returns response,
     * and caches it.
     * Next request for `/weather/london` → returns cached response in milliseconds.
     * 
     * ---
     * 
     * ### 🧩 Common Caching Techniques:
     * 
     * **In-Memory Caching**: Fastest (e.g., Redis, Guava, Caffeine)
     * **HTTP Caching**: Uses headers like `Cache-Control`, `ETag`
     * **Persistent Caching**: Filesystem, disk, or database cache
     * 
     * ---
     * 
     * ### ⚠️ When Not to Use Caching?
     * 
     * When the data changes frequently and must be real-time
     * For sensitive or user-specific data unless properly isolated
     * 
     * 
     * Common types:
     * 🔹 Client-side caching: Uses browser storage (e.g., ETag, Cache-Control).
     * 🔹 Server-side caching: Stores responses at the API level (e.g., Redis, CDN).
     * 🎯 Key Takeaway: Caching reduces API response time and improves scalability.
     */

    /*
     * 2️⃣ Validate Dynamic JSON Keys in Rest Assured
     * 📌 Question: How do you validate dynamic keys in a JSON API response?
     */
    @Test
    public void validateDynamicKeys() {
        RestAssured.given().get("https://dummyjson.com/posts").then()
                .body("posts[0].keySet()", hasItems("id", "title", "tags", "userId"));
    }

    /*
     * 3️⃣ Handle Web Page Scroll Using Selenium
     * 📌 Question: How do you scroll down a webpage in Selenium WebDriver?
     */
    @Test
    public void testWebScroll() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://dummyjson.com/posts");
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1300)");
        driver.quit();

    }

    /*
     * 4️⃣ What Is API Monitoring and How Is It Done?
     * 📌 Question: What is API monitoring, and why is it important?
     */

    /*
     * 5️⃣ Handle File Upload Using Selenium Without SendKeys
     * 📌 Question: How do you upload a file in Selenium without using sendKeys()?
     * 
     * **Not working**
     */
    @Test
    public void handleFileUploadWithoutSendKeys() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://practice.expandtesting.com/uploadd");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        WebElement fileUploadElement = driver.findElement(By.cssSelector("input[type='file']"));
        String filepath = new File("./src/main/resources/json_schema.json").getAbsolutePath();
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + filepath + "')",
                fileUploadElement);
        driver.findElement(By.id("fileSubmit")).click();
        driver.findElement(By.xpath("//*[text()='json_schema.json']"));
        driver.quit();
    }
}
