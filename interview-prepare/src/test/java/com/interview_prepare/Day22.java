package com.interview_prepare;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasKey;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Day22 {

    /*
     * 1️⃣ Extract Valid Email Addresses from a List
     * 📌 Scenario: You are testing a user registration system where users enter
     * email addresses. Given a list of email inputs, extract and return only valid
     * emails.
     * 🔹 Input:
     * emails = ["test@domain.com", "invalid-email", "user123@site.org",
     * "admin@company", "valid.email@work.net"]
     * 🔹 Output:
     * ["test@domain.com", "user123@site.org", "valid.email@work.net"]
     */
    @Test
    public void extractValidEmailAddressFromList() {
        List<String> emails = List.of("test@domain.com", "invalid-email", "user123@site.org",
                "admin@company", "valid.email@work.net");
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        emails = emails.stream().filter(str -> pattern.matcher(str).matches()).toList();
        System.out.println(emails);
    }

    /*
     * 2️⃣ Count Words in a User Input String
     * 📌 Scenario: You are testing a comment box where users enter text. Count the
     * number of words in a given input string.
     * 
     * 🔹 Input: "SDET automation testing is important"
     * 🔹 Output: 5
     */
    @Test
    public void countWords() {
        String str = "   SDET automation testing is    important   ";
        System.out.println(str.trim().split("\\w+").length);
        System.out.println(str.trim().split("\\s+").length);
        System.out.println("".trim().split("\\s+").length); // 1 due to empty string
        System.out.println("      a       ".trim().split("\\s+").length);
        System.out.println("      ab       ".trim().split("\\w+").length); // 0 due to no word space
        System.out.println("      ab   cd    ".trim().split("\\w+").length); // 2
    }

    /*
     * 3️⃣ Remove Duplicate Entries from a List of Usernames
     * 📌 Scenario: Your application stores usernames, but users may register
     * multiple times. Remove duplicate usernames while keeping the order.
     * 
     * 🔹 Input:
     * ["alice", "bob", "alice", "charlie", "bob", "dave"]
     * 🔹 Output:
     * ["alice", "bob", "charlie", "dave"]
     */
    @Test
    public void removeDuplicateEntries() {
        List<String> usernames = List.of("alice", "bob", "alice", "charlie", "bob", "dave");

        // Solution 1
        System.out.println(new ArrayList<>(new LinkedHashSet<>(usernames)));

        // Solution 2
        usernames = usernames.stream().distinct().toList();
        System.out.println(usernames);

    }

    /*
     * 📌 Question: How do you verify that an API returns a response with a specific
     * number of JSON array elements?
     */
    @Test
    public void validateAPIResponseWithNumberOfJSONArrayElements() {
        RestAssured.given().get("https://dummyjson.com/posts?skip=10&limit=30")
                .then().body("posts.size()", equalTo(30))
                .body("posts.tags.size()", greaterThanOrEqualTo(2));

    }

    /*
     * 📌 Question: How do you handle a scenario where clicking a button opens a new
     * browser tab or window?
     */
    @Test
    public void handleNewTabWindow() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/windows");
        String mainWindowHandle = driver.getWindowHandle();
        driver.findElement(By.linkText("Click Here")).click();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindowHandle)) {
                driver.switchTo().window(windowHandle);
                Assert.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(30))
                        .until(ExpectedConditions
                                .visibilityOfElementLocated(By.xpath("//h3[text()='New Window']")))
                        .isDisplayed());
                break;
            }
        }
        driver.close();

        driver.switchTo().window(mainWindowHandle);
        Assert.assertTrue(driver.findElement(By.linkText("Elemental Selenium")).isDisplayed());
        driver.quit();
    }

    /*
     * 📌 Question: What is the difference between the HTTP methods PUT and PATCH,
     * and when should you use each?
     * ✅ Answer:
     * 🔄 1️⃣ PUT – Complete Update
     * ✅ Definition:
     * PUT replaces the entire resource with the new one sent in the request.
     * 🧠 Behavior:
     * Idempotent: Repeating the same request has the same effect.
     * If any field is missing, it may be overwritten or deleted.
     * Typically used to update or create a resource (upsert behavior).
     * 
     * 
     * 🧩 2️⃣ PATCH – Partial Update
     * ✅ Definition:
     * PATCH applies a partial modification to a resource.
     * 🧠 Behavior:
     * Also idempotent, in most proper implementations.
     * Only updates the fields you send.
     * More efficient for large resources when only a small change is needed.
     */
    @Test
    public void putPatchUpdate() {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"body\": \"Testing SDET PATCH\"}")
                .patch("https://dummyjson.com/comments/1").then()
                .body("body", equalTo("Testing SDET PATCH"));

        RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"body\": \"Testing SDET PATCH\", \"postId\": 4}")
                .put("https://dummyjson.com/comments/1").then()
                .body("body", equalTo("Testing SDET PATCH"))
                .body("postId", equalTo(4));
    }

    /*
     * 📌 Question: How do you wait for an element to be visible in Selenium without
     * using Thread.sleep()?
     * => Implicit Wait -> for all ELements
     * => Explicit Wait -> for specific element
     * => Fluent Wait -> For Granular control on specific condition
     */
    @Test
    public void useSeleniumWaits() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://dummyjson.com/docs");

        // 30 Seconds applicable for all elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        // Only specific to element
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='DummyJSON']")));
        Assert.assertTrue(ele.isDisplayed());
        FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(60));
        fluentWait.ignoring(NoSuchElementException.class);
        fluentWait.pollingEvery(Duration.ofMillis(200));

        ele = fluentWait.until(new Function<WebDriver, WebElement>() {
            int i = 0;

            @Override
            public WebElement apply(WebDriver t) {
                if (i++ < 3) {
                    System.out.println("i: " + i);
                    return null;
                }
                return t.findElement(By.linkText("Docs"));
            }

        });
        Assert.assertTrue(ele.isDisplayed());
        driver.quit();
    }

    /*
     * 📌 Question: How do you validate that a JSON response contains a specific
     * key?
     */
    @Test
    public void validateSpeicificKeyinAPIResponse(){
        RestAssured.given().get("https://dummyjson.com/users/1").then()
        .body("$", hasKey("birthDate"))
        .body("hair", hasKey("color"))
        .body("address.coordinates", hasKey("lat"));
    }

}
