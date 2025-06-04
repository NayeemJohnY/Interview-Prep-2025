package com.interview_prepare;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Day15 {

    /*
     * 1️⃣ Find the Longest Palindromic Substring
     * 📌 Question: Given a string, find the longest palindromic substring.
     * 🔹 Input: "babad"
     * 🔹 Output: "bab" (or "aba")
     */

    public boolean isPalindrome(String str, int left, int right) {
        while (left < right) {
            if (str.charAt(left++) != str.charAt(right--))
                return false;
        }
        return true;
    }

    public int expandFromCenter(String str, int left, int right) {
        while (left >= 0 && right < str.length() && str.charAt(left) == str.charAt(right)) {
            left--;
            right++;
        }

        return right - left - 1;
    }

    @Test
    public void findLongestPalindromiceSubstring() {

        // Solution 1
        String input = "banana";
        String longestPalindromicString = null;
        for (int i = 0; i < input.length(); i++) {
            for (int j = input.length(); j > i && j - i >= 2; j--) {
                String subString = input.substring(i, j);
                if (new StringBuilder(subString).reverse().toString().equals(subString)) {
                    if (longestPalindromicString == null || subString.length() > longestPalindromicString.length()) {
                        longestPalindromicString = subString;
                    }
                }
            }
        }
        System.out.println(longestPalindromicString);
        // Solution 2
        for (int i = 0; i < input.length(); i++) {
            for (int j = input.length(); j > i && j - i >= 2; j--) {
                if (isPalindrome(input, i, j - 1)) {
                    int len = j - i;
                    if (longestPalindromicString == null || len > longestPalindromicString.length()) {
                        longestPalindromicString = input.substring(i, j);
                    }
                }
            }
        }
        System.out.println(longestPalindromicString);

        // Solution 3
        int end = 0;
        int start = 0;
        for (int i = 0; i < input.length(); i++) {
            // Expand for odd-length palindrome
            int len1 = expandFromCenter(input, i, i);
            // Expand for even-length palindrome
            int len2 = expandFromCenter(input, i, i + 1);

            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        System.out.println(input.substring(start, end + 1));
    }
    /*
     * 2️⃣ Find Pairs with Given Difference
     * 📌 Question: Given an array and a difference k, find all unique pairs (a, b)
     * such that a - b = k.
     * 🔹 Input: {1, 5, 2, 2, 7, 3, 10}, k = 3
     * 🔹 Output: [(5,2), (7,4), (10,7)]
     */

    @Test
    public void findPairsForDifference() {
        int[] array = new int[] { 1, 5, 2, 2, 4, 7, 3, 10 };
        int k = 3;

        // Solution 1
        HashSet<Integer> hashSet = new HashSet<>();
        Collections.addAll(hashSet, Arrays.stream(array).boxed().toArray(Integer[]::new));
        for (int i : array) {
            hashSet.add(i);
        }
        Set<List<Integer>> pairs = new HashSet<>();
        for (Integer integer : hashSet) {
            int sum = integer + k;
            if (hashSet.contains(sum)) {
                List<Integer> pair = new ArrayList<>();
                pair.add(Math.max(sum, integer));
                pair.add(Math.min(sum, integer));
                pairs.add(pair);
            }
        }
        System.out.println(pairs);
    }

    /*
     * 3️⃣ Implement a Functional Interface for Summation
     * 📌 Question: Implement an interface to sum two numbers using a lambda
     * expression.
     * 🔹 Input: sum(10, 20)
     * 🔹 Output: 30
     */
    @FunctionalInterface
    interface Summation {
        int sum(int a, int b);
    }

    @Test
    public void testFuntionalInterfaceSum() {
        Summation summation = (a, b) -> a + b;
        System.out.println(summation.sum(10, 20));
    }

    /*
     * 1️⃣ What Is API Contract Testing and Why Is It Important?
     * 📌 Question: What is API contract testing, and how does it differ from
     * functional API testing?
     * 
     * ✅ Answer:
     * API Contract Testing ensures APIs follow the agreed-upon request-response
     * structure.
     * Unlike functional API testing, it doesn’t validate business logic but ensures
     * stability in API integrations.
     * Tools like Postman, Pact, and OpenAPI help enforce API contracts.
     * ✅ Prevents breaking changes for consumers
     * ✅ Helps teams integrate safely in microservices or client-server
     * architectures
     * ✅ Catches mismatches early (before functional or UI tests)
     * Here's the table converted into a **bullet-point list** format for easy
     * reading:
     * 
     * ---
     * 
     * ### ✅ Difference Between Contract Testing and Functional API Testing
     * 
     * **What it tests:**
     * 
     * Contract Testing: Structure, schema, required fields
     * Functional API Testing: Behavior, logic, business rules
     * 
     * **Focus:**
     * 
     * Contract Testing: "Is the API correctly defined?"
     * Functional API Testing: "Does the API do what it should?"
     * 
     * **Input/output format:**
     * 
     * Contract Testing: Strictly enforced
     * Functional API Testing: May allow variations or edge cases
     * 
     * **Tools:**
     * 
     * Contract Testing: Pact, Postman schema validation, Dredd
     * Functional API Testing: REST Assured, Postman, Karate, JUnit, etc.
     * 
     * **Use case:**
     * 
     * Contract Testing: Validates contract compliance (especially between teams or
     * services)
     * Functional API Testing: Validates feature behavior, bug fixes, and business
     * rules
     *
     * 
     * 🎯 Key Takeaway: Contract testing prevents breaking changes when multiple
     * teams rely on an API.
     */

    /*
     * 2️⃣ Validate API Response Contains an Exact List of Values in Rest Assured
     * 📌 Question: How do you validate that an API response contains only specific
     * values?
     */
    @Test
    public void validateAPIRespnseContainsExcatList() {
        RestAssured.given().get("https://dummyjson.com/posts").then()
                .body("posts[1].tags", contains("french", "fiction", "english"))
                .body("posts.find{ it.id == 10 }.tags", containsInAnyOrder("magical", "history", "fiction"));
    }

    /*
     * 3️⃣ How to Verify Broken Links Using Selenium?
     * 📌 Question: How do you validate broken links in Selenium WebDriver?
     */
    @Test
    public void validateBrokenLinks() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://freecrm.com/");

        List<String> links = driver.findElements(By.tagName("a"))
                .parallelStream().map(e -> e.getDomProperty("href")).distinct()
                .filter(href -> !(href.startsWith("tel:") || href.startsWith("mailto:"))).toList();

        links.forEach(link -> {
            HttpURLConnection httpURLConnection;
            try {
                httpURLConnection = (HttpURLConnection) new URI(link).toURL().openConnection();
                httpURLConnection.setRequestMethod("HEAD");
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    System.out.println("Good -> Link: " + link);
                } else {
                    System.err.println(
                            "Broken -> Link:  " + link + " Status Code: " + httpURLConnection.getResponseCode());
                }
            } catch (Exception e) {
                System.err.println(" Exception : " + e.getMessage() + " for Link: " + link);
            }

        });
    }

    /*
     * 4️⃣ What Is the Difference Between API Mocking and Stubbing?
     * 📌 Question: What is the difference between API mocking and stubbing, and
     * when should you use them?
     * 
     * Sure! Here are the **key differences between API mocking and stubbing** as a
     * list:
     * 
     * ---
     * 
     * ### 🔑 **Key Differences – Mocking vs Stubbing**
     * 
     * 1. **Purpose**
     * 
     * **Stubbing**: Provides predefined responses.
     * **Mocking**: Simulates and verifies behavior and interaction.
     * 
     * 2. **Behavior**
     * 
     * **Stubbing**: Passive — only returns static data.
     * **Mocking**: Active — can verify calls, parameters, and frequency.
     * 
     * 3. **Validation**
     * 
     * **Stubbing**: Does **not** validate how the stub was used.
     * **Mocking**: Can check if methods were called correctly.
     * 
     * 4. **Complexity**
     * 
     * **Stubbing**: Simple to set up.
     * **Mocking**: Slightly more complex; involves expectations or spies.
     * 
     * 5. **Focus**
     * 
     * **Stubbing**: Focuses on **what is returned**.
     * **Mocking**: Focuses on **how it's used**.
     * 
     * 6. **Use Case**
     * 
     * **Stubbing**: Isolate your code from external dependencies.
     * **Mocking**: Test integration and ensure correct behavior.
     * 
     * 7. **Response Type**
     * 
     * **Stubbing**: Static/fixed response.
     * **Mocking**: Dynamic and conditional behavior.
     * 
     * 8. **Tools Examples**
     * 
     * **Stubbing**: WireMock, MockServer, TestNG @MockBean.
     * **Mocking**: Mockito, REST Assured with MockMvc, EasyMock.
     * 
     * ---
     * 
     * Let me know if you want this as a printable table or a slide-ready format!
     * 
     */

    /*
     * 5️⃣ How to Handle Element Click Intercepted Exception in Selenium?
     * 📌 Question: How do you resolve ElementClickInterceptedException in Selenium?
     * ✅ Solution:
     * 1) Using scrollIntoView to element
     * 2) Closing any overlays before click
     * 3) Using force click - js click()
     */
    @Test
    public void handleClickInterceptedException() {
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("-start-maximized"));
        driver.get("https://demoqa.com/radio-button");
        driver.findElements(By.cssSelector("input[type='radio']"))
                .forEach(ele -> {
                    JavascriptExecutor js = null;
                    String id = null;
                    WebElement labelElement = null;
                    try {
                        js = (JavascriptExecutor) driver;
                        id = ele.getDomAttribute("id");
                        labelElement = ele.findElement(
                                By.xpath("//input[@id='" + id + "']/following-sibling::label"));
                        labelElement.click();
                    } catch (ElementClickInterceptedException e) {
                        System.err.println("error 1: " + e.getLocalizedMessage());
                        js.executeScript("arguments[0].scrollIntoViewIfNeeded()", labelElement);
                        try {
                            labelElement.click();
                        } catch (ElementClickInterceptedException e1) {
                            System.err.println("error 2: " + e.getLocalizedMessage());
                            js.executeScript("arguments[0].click()", labelElement);
                        }

                    }

                    System.out.println("Radio Button clicked: " + labelElement.getText());
                    boolean isTextDisplayed = driver.findElements(
                            By.xpath("//*[normalize-space()='You have selected " + labelElement.getText() + "']"))
                            .size() != 0;
                    Assert.assertTrue(id.contains("no") ? !isTextDisplayed : isTextDisplayed);
                });
        driver.quit();

    }
}
