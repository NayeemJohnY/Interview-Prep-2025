package com.interview_prepare;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasKey;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Day27 {

    /*
     * 1️⃣ Generate User Initials for Dashboard Display
     * 📌 Scenario: Your application displays user initials in avatars. Given a list
     * of full names, extract initials for display.
     * 🔹 Input:
     * ["Alice Johnson", "Bob Marley", "Charlie Puth"]
     * 🔹 Output:
     * ["AJ", "BM", "CP"]
     */
    @Test
    public void generateUserInitials() {
        List<String> list = List.of("Alice Johnson", "Bob Marley", "Charlie Puth");
        list = list.stream()
                .map(str -> Arrays.stream(str.split(" "))
                        .map(s -> s.substring(0, 1).toUpperCase()).collect(Collectors.joining()))
                .toList();
        System.out.println(list);
    }

    /*
     * 2️⃣ Find the Day with Maximum Logins from a Report
     * 📌 Scenario: You are analyzing login patterns. Given a list of login dates,
     * return the date with the highest frequency.
     * 🔹 Input:
     * ["2024-03-01", "2024-03-02", "2024-03-01", "2024-03-03", "2024-03-01"]
     * 🔹 Output:
     * "2024-03-01"
     */
    @Test
    public void findDayWithMaximumLoginsFromReport() {
        List<String> list = List.of("2024-03-01", "2024-03-02", "2024-03-01", "2024-03-03", "2024-03-01");
        // Solution 1
        list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(entry -> System.out.println(entry.getKey()));

        // Soltuion 2
        Map<String, Integer> map = new HashMap<>();
        for (String string : list) {
            map.put(string, map.getOrDefault(string, 0) + 1);
        }
        System.out.println(Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey());
    }

    /*
     * 3️⃣ Clean Product Titles by Removing Extra Spaces & Capitalizing First
     * Letters
     * 📌 Scenario: You're formatting product titles before exporting to a catalog.
     * Normalize strings by trimming, removing extra spaces, and capitalizing each
     * word.
     * 🔹 Input:
     * [" wireless mouse ", "bluetooth speaker", " gaming KEYboard "]
     * 🔹 Output:
     * ["Wireless Mouse", "Bluetooth Speaker", "Gaming Keyboard"]
     */
    @Test
    public void normalizeProductTitles() {
        List<String> list = List.of(" wireless mouse ", "bluetooth speaker", " gaming KEYboard ");
        list = list.stream().map(str -> Arrays.stream(str.trim().toLowerCase().split("\\s+"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1)).collect(Collectors.joining(" "))).toList();
        System.out.println(list);
    }

    /*
     * 1️⃣ How to Validate Nested JSON Data in Rest Assured?
     * 📌 Question: How do you assert a nested value inside a complex JSON structure
     * in Rest Assured?
     */
    @Test
    public void validateNestJSONResponse() {
        RestAssured.given().get("https://dummyjson.com/users").then()
                .log().all()
                .body("users.find{it.id == 23}.address.city", equalTo("Dallas"))
                .body("users.company.address.coordinates", everyItem(hasKey("lat")));
    }

    /*
     * 2️⃣ How to Handle Auto-Suggest Dropdowns in Selenium?
     * 📌 Question: How do you select an option from an auto-suggest dropdown (e.g.,
     * Google search suggestions)?
     */
    @Test
    public void handleAutoSuggestDropDowns() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.makemytrip.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-cy='closeModal']"))).click();
        By toCitySelector = By.id("toCity");
        driver.findElement(toCitySelector).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='toCity'][ancestor::*[contains(@class, 'activeWidget')]]")));
        driver.findElement(By.cssSelector("[placeholder='To']")).sendKeys("Che");
        WebElement toCity = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Chengde, China']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoViewIfNeeded();", toCity);
        toCity.click();
        Assert.assertEquals(driver.findElement(toCitySelector).getAttribute("value"), "Chengde");
        driver.quit();

    }

    /*
     * 3️⃣ What Is a Pre-Signed URL in API Testing?
     * 📌 Question: What is a pre-signed URL, and how do you test it via automation?
     * 
     * 🔍 Definition:
     * A pre-signed URL is a time-limited URL generated by a server (often using a
     * secret key or token) that grants temporary, secure access to a private
     * resource, such as a file stored on Amazon S3, Azure Blob, or other cloud
     * storage.
     * This URL contains:
     * The file/resource location
     * Access permissions
     * An expiration timestamp
     * A signature (proof the URL is authorized)
     * 💡 You can use a pre-signed URL to upload, download, or view a file without
     * needing direct authentication (like an API key or token).
     * 
     * Real-Life Use Cases:
     * Users uploading profile images without exposing credentials.
     * Secure, expiring links for downloading reports or invoices.
     * Sharing temporary access to private media files (e.g., videos, documents).
     */

    /*
     * 4️⃣ How to Scroll to a Specific Element in Selenium?
     * 📌 Question: How do you scroll to an element that’s not visible in the
     * current viewport?
     */
    @Test
    public void scrollToSpecificElement() {
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized"));
        driver.get("https://dummyjson.com/docs");
        WebElement element = driver.findElement(By.partialLinkText("Buy me a coffee"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
        driver.quit();
    }

    /*
     * 5️⃣ How to Validate Presence of Cookies in Rest Assured?
📌 Question: How do you verify if a specific cookie is present in the API response?
     */
    @Test
    public void validatePresenceOfCookies(){
        RestAssured.baseURI = "https://dummyjson.com";
        RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .body("{\"username\": \"loganl\", \"password\": \"loganlpass\"}")
                .post("/auth/login")
                .then().log().all()
                .cookie("accessToken");
    }
}
