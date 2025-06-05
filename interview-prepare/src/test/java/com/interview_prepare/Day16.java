package com.interview_prepare;

import static org.hamcrest.Matchers.equalTo;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Day16 {

    /*
     * 1️⃣ Check If Two Strings are Anagrams
     * 📌 Question: Given two strings, check if they are anagrams (contain the same
     * characters in any order).
     * 🔹 Input: "listen", "silent"
     * 🔹 Output: true
     */
    @Test
    public void checkTwoStringAnagram() {
        String s1 = "listen";
        String s2 = "silent";

        if (s1.length() != s2.length()) {
            System.out.println(false);
            return;
        }

        // Solution 1
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        System.out.println(Arrays.equals(c1, c2));

        // Solution 2
        Set<Character> characterSet = s1.chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet());
        s2.chars().forEach(ch -> characterSet.remove((char) ch));
        System.out.println(characterSet.isEmpty());

        // Solution 3
        Map<Character, Long> freqMap = s1.chars().mapToObj(ch -> (char) ch)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Character character : s2.toCharArray()) {
            if (!freqMap.containsKey(character)) {
                System.out.println(false);
                return;
            }

            freqMap.put(character, freqMap.get(character) - 1);
            if (freqMap.get(character) == 0) {
                freqMap.remove(character);
            }
        }
        System.out.println(freqMap.isEmpty());
    }

    /*
     * 2️⃣ Find the Missing Number in an Array
     * 📌 Question: Given an array containing numbers from 1 to n (with one
     * missing), find the missing number.
     * 🔹 Input: {1, 2, 4, 5, 6}
     * 🔹 Output: 3
     * n(n+1)/2 => 6(6+1)/2 => 21 => 11+4+2+1 = 18 =>3
     */
    @Test
    public void findMissingNumber() {
        int[] arr = new int[] { 1, 2, 4, 5, 6 };
        int n = 6;
        int expectedSum = n * (n + 1) / 2;
        System.out.println(expectedSum - Arrays.stream(arr).sum());
    }

    /*
     * 3️⃣ Implement a Functional Interface for String Transformation
     * 📌 Question: Implement an interface to transform a string to uppercase and
     * remove spaces.
     * 🔹 Input: "hello world"
     * 🔹 Output: "HELLOWORLD"
     */
    @FunctionalInterface
    interface TransformString {
        String transform(String s);
    }

    @Test
    public void testStringTransform() {
        TransformString transformString = s -> s.replaceAll("\\s+", "").toUpperCase();
        System.out.println(transformString.transform("hello world"));
    }

    /*
     * 1️⃣ What Is the Difference Between API Gateway and Load Balancer?
     * 📌 Question: How does an API Gateway differ from a Load Balancer in API
     * architecture?
     * 
     * ✅ 1. Purpose
     * API Gateway
     * Manages and orchestrates API requests
     * 
     * Acts as a front door for all client requests to a backend system. It handles
     * request routing, transformation, authentication, rate limiting, and logging,
     * caching.
     * 
     * Operates at the application layer (Layer 7). It understands the API structure
     * (like HTTP verbs, endpoints, headers).
     * 
     * Load Balancer
     * Distributes traffic across servers
     * 
     * Balances traffic to ensure availability and performance
     * 
     * Distributes incoming network traffic across multiple servers to ensure no
     * single server becomes overwhelmed, thus improving availability and
     * reliability.
     * 
     * Can operate at different layers:
     * 
     * Layer 4 (Transport layer): TCP/UDP-level routing
     * Layer 7 (Application layer): HTTP-level routing
     */

    /*
     * 2️⃣ Validate API Pagination in Rest Assured
     * 📌 Question: How do you verify that an API correctly implements pagination?
     */
    @Test
    public void validateAPIPagination() {
        RestAssured.given()
                .baseUri("https://dummyjson.com")
                .queryParam("skip", "10")
                .queryParam("limit", "10")
                .get("/posts")
                .then()
                .body("posts.size()", equalTo(10))
                .body("posts.id", equalTo(IntStream.rangeClosed(11, 20).boxed().toList()));
    }

    /*
     * 3️⃣ Handle Hover Actions in Selenium
     * 📌 Question: How do you perform a mouse hover action in Selenium?
     */
    @Test
    public void handleHoverActions() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.cleartrip.com/");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//*[text()='Business']"))).perform();
        driver.quit();
    }

    /*
     * 4️⃣ What Is the Difference Between OAuth 1.0 and OAuth 2.0?
     * 📌 Question: How does OAuth 1.0 differ from OAuth 2.0, and which one is
     * preferred?
     * 
     * Auth 1.0 and OAuth 2.0 are both protocols for authorization, but they differ
     * significantly in security models, complexity, and usage.
     * 
     * OAuth 1.0: “More secure by itself, but complicated and outdated.”
     * OAuth 2.0: “Secure with HTTPS, simpler, modern, and widely supported.”
     * 
     * Sure! Here's the **difference between OAuth 1.0 and OAuth 2.0** presented as
     * a **simple list**:
     * 
     * ---
     * 
     * ### 🔁 **OAuth 1.0 vs OAuth 2.0 – Differences as a List**
     * 
     * 1. **Security Method**
     * 
     * OAuth 1.0: Uses **digital signatures** (HMAC-SHA1)
     * OAuth 2.0: Uses **bearer tokens** with **HTTPS**
     * 
     * 2. **Token Handling**
     * 
     * OAuth 1.0: Requires **access token + secret key**
     * OAuth 2.0: Uses **access token**, optionally **refresh token**
     * 
     * 3. **Complexity**
     * 
     * OAuth 1.0: More **complex** (requires cryptographic signing, nonces)
     * OAuth 2.0: **Simpler** and easier to implement
     * 
     * 4. **Transport Security**
     * 
     * OAuth 1.0: Works **without HTTPS**
     * OAuth 2.0: **Requires HTTPS** for security
     * 
     * 5. **Client Support**
     * 
     * OAuth 1.0: Mostly for **server-side apps**
     * OAuth 2.0: Supports **web, mobile, desktop, IoT apps**
     * 
     * 6. **Extensibility**
     * 
     * OAuth 1.0: **Limited** extensibility
     * OAuth 2.0: **Highly extensible** (scopes, grant types, custom flows)
     * 
     * 7. **Industry Adoption**
     * 
     * OAuth 1.0: **Deprecated**, rarely used
     * OAuth 2.0: **Widely adopted and standard**
     * 
     * 8. **Ease of Use**
     * 
     * OAuth 1.0: **Harder to implement**
     * OAuth 2.0: **Easier for developers**
     * 
     */

    /*
     * 5️⃣ Handle Element Not Interactable Exception in Selenium
     * 📌 Question: How do you resolve ElementNotInteractableException in Selenium?
     */
        @Test
    public void handleElementNotInteractableException() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://seleniumpractise.blogspot.com/2016/09/how-to-work-with-disable-textbox-or.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement passwordElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("pass")));
        try {
            passwordElement.sendKeys("Password Test");
        } catch (ElementNotInteractableException e) {
                System.out.println(e.getRawMessage());
                JavascriptExecutor jsExecutor = (JavascriptExecutor) (driver);
            jsExecutor.executeScript("arguments[0].value='Password Test JS'", passwordElement);
        }
        driver.quit();
    }
}
