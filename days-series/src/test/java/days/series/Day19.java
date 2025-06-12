package days.series;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Day19 {

    /*
     * 1️⃣ Find the Longest Subarray with Sum ≤ K
     * 📌 Question: Given an array and an integer K, find the longest subarray with
     * sum ≤ K.
     * 🔹 Input: {2, 3, 1, 2, 4, 3}, K = 7
     * 🔹 Output: 4 (subarray: [2, 3, 1, 2])
     */
    @Test
    public void findLongestSubArrayWithSum() {
        int start = 0, sum = 0, end = 0, maxLength = 0, bestStart = 0;
        int[] array = new int[] { 2, 3, 1, 1, 4, 3 };
        int k = 7;
        for (end = 0; end < array.length; end++) {
            sum += array[end];
            while (sum > k) {
                sum -= array[start++];
            }

            if (end - start + 1 > maxLength) {
                maxLength = end - start + 1;
                bestStart = start;
            }
        }
        System.out.println(Arrays.toString(Arrays.copyOfRange(array, bestStart, bestStart + maxLength)));

    }

    /*
     * 2️⃣ Find All Duplicates in an Array
     * 📌 Question: Given an array, find all elements that appear more than once.
     * 🔹 Input: {4, 3, 2, 7, 8, 2, 3, 1}
     * 🔹 Output: [2, 3]
     */
    @Test
    public void findDuplicatesInArray() {
        Set<Integer> seen = new HashSet<>();
        List<Integer> duplicates = new ArrayList<>();
        int[] array = new int[] { 4, 3, 2, 7, 8, 2, 3, 1 };
        // Solution 1;
        for (int i : array) {
            if (!seen.add(i)) {
                duplicates.add(i);
            }
        }
        System.out.println(duplicates);

        // Solution 2
        Set<Integer> set = new HashSet<>();
        duplicates = Arrays.stream(array).filter(n -> !set.add(n)).boxed().toList();
        System.out.println(duplicates);

    }

    /*
     * 3️⃣ Sort a Map by Values in Descending Order
     * 📌 Question: Given a map of employee salaries, sort it by salary in
     * descending order.
     * 🔹 Input: {Alice=5000, Bob=7000, John=6000}
     * 🔹 Output: {Bob=7000, John=6000, Alice=5000}
     */
    @Test
    public void sortMapbyValues() {
        Map<String, Integer> map = Map.of("Alice", 5000, "Bob", 7000, "John", 6000);
        map = map.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println(map);

        map = map.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
        System.out.println(map);
    }

    /*
     * 1️⃣ What Is API Versioning and Why Is It Important?
     * 📌 Question: What is API versioning, and why is it used?
     * 
     * ✅ Answer:
     * 
     * ### ✅ **Definition:**
     ** 
     * API versioning** is the practice of **managing changes to an API** over time
     * by assigning **version numbers** to different iterations of the API.
     * 
     * ---
     * 
     * ### 🎯 **Why It's Used:**
     * 
     * 1. **Backward Compatibility**
     * 
     * Allows existing clients to continue using older versions without breaking.
     * Ensures that updates or improvements don’t disrupt current users.
     * 
     * 2. **Controlled Evolution**
     * 
     * Developers can release new features, fix bugs, or redesign endpoints safely.
     * Enables experimentation without risking stable production systems.
     * 
     * 3. **Multiple Client Support**
     * 
     * Different apps or systems may rely on different API versions at the same
     * time.
     * API versioning allows them to co-exist.
     * 
     * 4. **Clear Communication**
     * 
     * Versioning provides a clear contract: clients know exactly what to expect
     * from a specific version.
     * 
     * ---
     * 
     * ### 🛠️ **Common Versioning Methods:**
     * 
     * | Type | Example | Notes |
     * | ----------------------- | ------------------------------------- |
     * -------------------------- |
     * | **URI versioning** | `/api/v1/users` | Most common and visible |
     * | **Header versioning** | `Accept: application/vnd.api.v1+json` | Clean URLs,
     * more RESTful |
     * | **Query parameter** | `/users?version=1` | Easy to implement |
     * | **Content negotiation** | via MIME types | Requires more client setup |
     * 
     * ---
     * 
     * ### 🚧 Without API Versioning:
     * 
     * Any change could break clients unexpectedly.
     * Teams would hesitate to evolve the API due to fear of disruptions.
     * 
     * ---
     * 
     * ### 📦 **In Summary:**
     * 
     * > API versioning is **critical** for maintaining stability, flexibility, and
     * compatibility as APIs evolve. It lets developers improve APIs without
     * disrupting users.
     * 
     * 
     */

    /*
     * 2️⃣ Validate API Redirects in Rest Assured
     * 📌 Question: How do you validate that an API correctly redirects to another
     * URL?
     */
    @Test
    public void validateAPIRedirects() {
        RestAssured.given().redirects().follow(false).get("https://httpbin.org/redirect-to?url=https://example.com")
                .then().statusCode(302).header("Location", "https://example.com");
    }

    /*
     * 3️⃣ Handle Dynamic Web Tables in Selenium
     * 📌 Question: How do you extract specific data from a dynamic web table?
     */

    public Comparator<Map<String, String>> getComparator(String header) {
        if (header.equals("Due")) {
            return Comparator.comparingDouble(
                    map -> Double.parseDouble(map.get(header).replaceAll("[^0-9\\.]", "")));
        } else {
            return Comparator.comparing(map -> map.get(header));
        }
    }

    @Test
    public void handleDynamicTable() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/tables");

        // Get Specific value
        String dues = driver.findElement(
                By.xpath("//table[@id='table2']//tr/td[text()='Jason']/following-sibling::td[@class='dues']"))
                .getText();
        System.out.println(Double.parseDouble(dues.replaceAll("[^0-9\\.]", "")));

        // Verify Sorting
        List<WebElement> rows = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#table1 tbody tr")));

        List<String> headers = driver.findElements(By.cssSelector("#table1 th span"))
                .stream().map(WebElement::getText).toList();
        List<Map<String, String>> tabeValues = rows.stream()
                .map(row -> {
                    List<String> cellTexts = row.findElements(By.cssSelector("td"))
                            .stream()
                            .map(WebElement::getText).toList();
                    return IntStream.range(0, cellTexts.size()).boxed().collect(
                            Collectors.toMap(headers::get, cellTexts::get));

                }).toList();
        System.out.println(tabeValues);

        // Verify Sorting
        driver.findElement(By.xpath("//*[@id='table1']//th/span[text()='Due']")).click();
        List<Map<String, String>> dueSortedTable = tabeValues.stream().sorted(getComparator("Due")).toList();
        System.out.println(dueSortedTable);
        for (int i = 0; i < dueSortedTable.size(); i++) {
            String locator = "//table//tr[" + (i + 1) + "]";
            List<String> values = List.copyOf(dueSortedTable.get(i).values());
            for (int j = 1; j < values.size(); j++) {
                locator += "[td[text()='" + values.get(j) + "']]";
            }
            System.out.println(locator);
            System.out.println(driver.findElement(By.xpath(locator)).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath(locator)).isDisplayed());
        }
    }

    /*
     * 4️⃣ What Are API Rate Limit Headers, and How Do You Use Them?
     * 📌 Question: What are rate limit headers, and how do they help prevent API
     * abuse?
     * 
     * **🔍 What Are API Rate Limit Headers?**
     * 
     * API **rate limit headers** are special HTTP response headers that provide
     * information about how many API requests a client can make within a certain
     * time period. They help both API providers and consumers manage and respect
     * request limits to prevent **abuse, overuse**, or accidental **denial of
     * service (DoS)**.
     * 
     * ---
     * 
     * ### 📊 Common Rate Limit Headers
     * 
     * Most APIs follow a common pattern for rate limit headers. These may include:
     * 
     * | Header | Description |
     * | ----------------------- |
     * -----------------------------------------------------------------------------
     * --------- |
     * | `X-RateLimit-Limit` | The total number of requests allowed in the current
     * window (e.g., per minute or hour). |
     * | `X-RateLimit-Remaining` | The number of requests remaining in the current
     * window. |
     * | `X-RateLimit-Reset` | The time (in UNIX timestamp or seconds) when the rate
     * limit will reset. |
     * 
     * Some APIs (like GitHub’s) may also include:
     * 
     * `Retry-After`: Tells you how long to wait before making another request if
     * you’re rate-limited.
     * 
     * ---
     * 
     * ### 🚫 How They Prevent API Abuse
     * 
     * Rate limit headers:
     * 
     * **Thwart abuse:** Prevent malicious or buggy clients from overwhelming the
     * API with too many requests.
     * **Protect server resources:** Avoid service degradation due to excessive
     * traffic.
     * **Ensure fairness:** Allow equal access to shared APIs among users.
     * **Guide clients:** Help developers understand and respect API usage policies.
     * 
     * ---
     * 
     * ### ✅ How to Use Them Effectively
     * 
     * 1. **Monitor the headers** in every response to track your usage.
     * 2. **Throttle requests** on your side when `X-RateLimit-Remaining` is low.
     * 3. **Back off gracefully** or pause requests if you hit the limit (using
     * `Retry-After` or `X-RateLimit-Reset`).
     * 4. **Log and alert** when nearing or exceeding rate limits to avoid service
     * disruption.
     * 
     * ---
     * 
     * ### 📦 Example (GitHub API)
     * 
     * ```http
     * HTTP/1.1 200 OK
     * X-RateLimit-Limit: 60
     * X-RateLimit-Remaining: 10
     * X-RateLimit-Reset: 1686419157
     * ```
     * 
     * This means:
     * 
     * You’re allowed 60 requests/hour.
     * You have 10 remaining.
     * The limit resets at the UNIX time `1686419157`.
     * 
     */
    @Test
    public void handleRateLimit() {
        int rateLimit = 60;
        for (int i = 7; i <= 10; i++) {
            RestAssured.given().get("https://api.github.com/users/octocat")
                    .then().statusCode(200)
                    .header("x-ratelimit-limit", equalTo(String.valueOf(rateLimit)))
                    .header("x-ratelimit-remaining", equalTo(String.valueOf(rateLimit - i)))
                    .header("x-ratelimit-used", equalTo(String.valueOf(i)))
                    .header("x-ratelimit-reset", not(empty()));
        }

    }

    /*
     * 5️⃣ Handle Element Visibility Issues in Selenium
     * 📌 Question: How do you handle elements that are not visible but present in
     * the DOM?
     */
    @Test
    public void handleElementVisibility() {
        // Element in DOM but not visible - can be located first
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        WebElement helloWordText = driver.findElement(By.xpath("//h4[text()='Hello World!']"));
        Assert.assertFalse(helloWordText.isDisplayed());
        driver.findElement(By.xpath("//button[text()='Start']")).click();
        helloWordText = new WebDriverWait(driver, Duration.ofSeconds(50)).until(
                ExpectedConditions.visibilityOf(helloWordText));
        Assert.assertTrue(helloWordText.isDisplayed());

        // Element not in DOM and not visible - can't be located first;
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
        try {
            helloWordText = driver.findElement(By.xpath("//*[text()='Hello World!']"));
        } catch (NoSuchElementException e) {
            System.out.println(e.getRawMessage());
            driver.findElement(By.xpath("//button[text()='Start']")).click();
            helloWordText = new WebDriverWait(driver, Duration.ofSeconds(50)).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Hello World!']")));
            Assert.assertTrue(helloWordText.isDisplayed());
        } finally {
            driver.quit();
        }

    }

}
