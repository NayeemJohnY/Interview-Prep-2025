package days.series;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.not;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Day06 {

    /*
     * 1️⃣ Reverse a String
     * 📌 Question: Given a string, reverse it without using built-in functions.
     * 🔹 Input: "SDET"
     * 🔹 Output: "TEDS"
     */
    @Test
    public void reverseString() {
        String str = "SDET";
        StringBuilder builder = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            builder.append(str.charAt(i));
        }
        System.out.println(builder.toString());
    }

    /*
     * 2️⃣ Remove Duplicates from a List
     * 📌 Question: Remove duplicates from an ArrayList of strings.
     * 🔹 Input: ["apple", "banana", "apple", "mango"]
     * 🔹 Output: ["apple", "banana", "mango"]
     * 
     */
    @Test
    public void removeDuplicateStringsFromList() {
        List<String> list = Arrays.asList("apple", "banana", "apple", "mango");
        // Solution 1
        System.out.println(new HashSet<>(list));

        // Solution 2 - to preseve order
        System.out.println(new LinkedHashSet<>(list));

        // Solution 3 - Using streams
        System.out.println(list.stream().distinct().toList());
    }

    /*
     * 3️⃣ Find Maximum Element in a List
     * 📌 Question: Find the maximum element in a list of integers.
     * 🔹 Input: [4, 12, 9, 5]
     * 🔹 Output: 12
     */
    @Test
    public void finMaxElementInIntegersList() {
        List<Integer> list = Arrays.asList(4, 12, 9, 5);
        // Solution 1
        System.out.println(Collections.max(list));

        // Solution 2 Sort and get Index
        list.sort(Collections.reverseOrder());
        System.out.println(list.get(0));

        // Solution 3 - Legacy using for loop
        int maxValue = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (maxValue < list.get(i)) {
                maxValue = list.get(i);
            }
        }
        System.out.println("Max value " + maxValue);
    }

    /*
     * 4️⃣ Check if a List is Palindromic
     * 📌 Question: Check if a list reads the same forward and backward.
     * 🔹 Input: [1, 2, 3, 2, 1]
     * 🔹 Output: true
     */
    @Test
    public void checkListISPalindromic() {
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 1);
        // Solution 1
        List<Integer> list2 = new ArrayList<>(list);
        Collections.reverse(list2);
        System.out.println(list.equals(list2));

        // Solution 2 - for loop
        boolean isPalindromic = true;
        int left = 0;
        int right = list.size();
        while (left < right) {
            if (!list.get(left++).equals(list.get(--right))) {
                isPalindromic = false;
                break;
            }
        }
        System.out.println(isPalindromic);
    }

    /*
     * 5️⃣ Merge Two Lists
     * 📌 Question: Merge two lists and sort the result.
     * 🔹 Input: [3, 1, 4], [6, 2, 5]
     * 🔹 Output: [1, 2, 3, 4, 5, 6]
     * 
     */
    @Test
    public void mergeTwoListAndSortResult() {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(3, 1, 4));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(6, 2, 5));
        list2.addAll(list1);
        Collections.sort(list2);
        System.out.println(list2);

    }

    /*
     * 1️⃣ Verify Content-Type in API Response Headers
     * 📌 Question: How do you ensure that the Content-Type of an API response is
     * correct using Rest Assured?
     */
    @Test
    public void verifyContentTypeInAPIResponse() {
        RestAssured.get("https://dummyjson.com/posts").then().contentType(ContentType.JSON);
    }

    /*
     * 2️⃣ Handle Disabled Elements in Selenium
     * 📌 Question: How do you handle a disabled input field or button in Selenium?
     * Solution is to - do Force Action using JS
     */
    @Test
    public void handleDisabledElements() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://seleniumpractise.blogspot.com/2016/09/how-to-work-with-disable-textbox-or.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement passwordElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("pass")));
        if (!passwordElement.isEnabled()) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) (driver);
            jsExecutor.executeScript("arguments[0].value='Password Test'", passwordElement);
        }
        driver.findElement(By.cssSelector("input[value='Show me']")).click();
        passwordElement = wait.until(d -> {
            WebElement passwordNew = d.findElement(By.id("passnew"));
            if (passwordNew.isEnabled()) {
                return passwordNew;
            }
            return null;
        });
        passwordElement.sendKeys("Hello New Password");
        driver.quit();
    }

    /*
     * 3️⃣ Validate API Query Parameters in Rest Assured
     * 📌 Question: How do you verify that API query parameters return the expected
     * response?
     */
    @Test
    public void validateAPIQueryParams() {
        RestAssured.given()
                .baseUri("https://dummyjson.com")
                .basePath("/users")
                .queryParam("key", "hair.color")
                .queryParam("value", "Brown")
                .get("/filter").then().body("users.hair.color", everyItem(equalTo("Brown")));
    }

    /*
     * 4️⃣ Handle Auto-Suggestions in Selenium
     * 📌 Question: How do you handle auto-suggestion dropdowns in Selenium?
     */
    @Test
    public void autoSuggestionDropDowns() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.cleartrip.com/flights");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector("input[placeholder='Where from?']")).sendKeys("che");
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//*[text()='Chevak, US - Chevak (VAK)']")))
                .click();
        driver.quit();

    }

    /*
     * 5️⃣ Validate API Response Contains a Specific List of Values
     * 📌 Question: How do you check if an API response contains multiple expected
     * values?
     */
    @Test
    public void testResponseHaveSpecificValues() {
        RestAssured.get("https://dummyjson.com/products/search?q=iphone").then()
                .body("""
                        products.findAll { p ->
                        p.title.toLowerCase().contains("iphone") || p.description.toLowerCase().contains("iphone")}
                        """, not(empty()));

    }
}
