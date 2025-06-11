package com.interview_prepare;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Day25 {

    /*
     * 1️⃣ Identify and Remove Expired API Keys
     * 📌 Scenario: Your system maintains a list of API keys with expiration dates.
     * Remove expired API keys from the list.
     * 
     * 🔹 Input:
     * apiKeys = { "key1"="2025-01-10", "key2"="2023-12-15", "key3"="2024-06-30" }
     * currentDate = "2024-02-01"
     * 🔹 Output:
     * { "key1"="2025-01-10", "key3"="2024-06-30" }
     */
    @Test
    public void removeExpiredAPIKeys() {
        Map<String, String> apiKeys = new HashMap<>(Map.of(
                "key1", "2025-11-10",
                "key2", "2023-12-15",
                "key3", "2024-06-30",
                "key4", "2026-06-30"));

        apiKeys.entrySet().removeIf(entry -> LocalDate.parse(entry.getValue()).isAfter(LocalDate.now()));
        System.out.println(apiKeys);
    }

    /*
     * 2️⃣ Find the Most Ordered Item in an E-commerce System
     * 📌 Scenario: Your system tracks product orders in a list. Find the most
     * frequently ordered item.
     * 
     * 🔹 Input:
     * orders = ["Laptop", "Phone", "Laptop", "Tablet", "Phone", "Laptop"]
     * 🔹 Output:
     * "Laptop"
     */
    @Test
    public void findMostOrderedItem() {
        List<String> items = List.of("Laptop", "Phone", "Laptop", "Tablet", "Phone", "Laptop");
        Map<String, Integer> countMap = new HashMap<>();
        for (String item : items)
            countMap.put(item, countMap.getOrDefault(item, 0) + 1);
        System.out.println(Collections.max(countMap.entrySet(), Map.Entry.comparingByValue()).getKey());
    }

    /*
     * 3️⃣ Merge Customer Feedback from Multiple Sources
     * 📌 Scenario: Your company collects customer feedback from different sources.
     * Merge and remove duplicates while keeping the order.
     * 
     * 🔹 Input:
     * feedback1 = ["Great service", "Fast delivery", "Excellent support"]
     * feedback2 = ["Fast delivery", "Affordable pricing", "Great service"]
     * 🔹 Output:
     * ["Great service", "Fast delivery", "Excellent support", "Affordable pricing"]
     */
    @Test
    public void mergeCustomerFeedback() {
        LinkedHashSet<String> set = new LinkedHashSet<>(List.of("Great service", "Fast delivery", "Excellent support"));
        set.addAll(List.of("Fast delivery", "Affordable pricing", "Great service"));
        System.out.println(set);
    }

    /*
     * 1️⃣ How to Validate XML Response in Rest Assured?
     * 📌 Question: How do you verify that an API returns a valid XML response with
     * specific node values?
     */
    @Test
    public void validateXMLResponse() {
        String city = "London";
        RestAssured.given().baseUri("https://api.openweathermap.org")
                .basePath("data/2.5/weather")
                .queryParam("q", city)
                .queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea")
                .queryParam("mode", "xml")
                .get().then().log().all()
                .body("current.city.country", equalTo("GB"))
                .body("current.city.timezone", equalTo("3600"));
    }

    /*
     * 2️⃣ How to Handle Hover Actions in Selenium?
     * 📌 Question: How do you perform mouse hover actions in Selenium WebDriver?
     */
    @Test
    public void handleHoverActions() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/tool-tips");
        WebElement hoverbutton = driver.findElement(By.xpath("//button[text()='Hover me to see']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoViewIfNeeded()", hoverbutton);
        new Actions(driver).moveToElement(hoverbutton).perform();
        Assert.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//*[text()='You hovered over the Button']")))
                .isDisplayed());
        driver.quit();
    }

    /*
     * 3️⃣ Difference Between SOAP and REST APIs?
     * 📌 Question: What are the key differences between SOAP and REST APIs?
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
     * 4️⃣ How to Perform File Upload in Selenium?
     * 📌 Question: How do you automate file uploads in Selenium WebDriver?
     */
    @Test
    public void handleFileUpload() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/upload-download");
        WebElement uploadFile = driver.findElement(By.id("uploadFile"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoViewIfNeeded()", uploadFile);
        uploadFile.sendKeys(
                new File("./src/main/resources/json_schema.json").getAbsolutePath());
        Assert.assertTrue(
                driver.findElement(By.xpath("//*[@id='uploadedFilePath'][contains(text(), 'json_schema.json')]"))
                        .isDisplayed());
        driver.quit();
    }

    /*
     * 5️⃣ How to Validate API Headers in Rest Assured?
     * 📌 Question: How do you verify that an API response contains a specific
     * header value?
     */
    @Test
    public void validateAPIHeaders() {
        RestAssured.given().get("https://dummyjson.com/products").then()
                .header("x-ratelimit-limit", equalTo("100"))
                .header("content-type", containsString(ContentType.APPLICATION_JSON.toString().toLowerCase()))
                .header("content-type", equalToIgnoringCase(ContentType.APPLICATION_JSON.toString()));
    }
}
