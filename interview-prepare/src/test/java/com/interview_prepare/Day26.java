package com.interview_prepare;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Day26 {

    /*
     * 1️⃣ Detect and Remove Unused Feature Flags
     * 📌 Scenario: Your application has a feature toggle system with feature flags
     * stored in a map. Find and remove flags that haven't been used in the last 90
     * days.
     * 🔹 Input:
     * featureFlags = { "darkMode"="2023-11-10", "betaAccess"="2024-02-15",
     * "newUI"="2023-12-01" }
     * currentDate = "2024-03-01"
     * 🔹 Output:
     * { "betaAccess"="2024-02-15" }
     */
    @Test
    public void removeUnusedFeatureFlags() {
        HashMap<String, String> map = new HashMap<>(Map.of(
                "darkMode", "2023-11-10",
                "MongoDB", "2025-03-14",
                "RedisCahce", "2025-03-13",
                "ACI", "2025-03-12",
                "CDN", "2025-03-15",
                "betaAccess", "2025-04-15",
                "CosmosDB", "2025-06-11",
                "newUI", "2025-01-01"));
        Map<String, String> flags = new HashMap<>(Map.copyOf(map));
        // Solution 1
        map.entrySet()
                .removeIf(entry -> ChronoUnit.DAYS.between(LocalDate.parse(entry.getValue()), LocalDate.now()) > 90);
        System.out.println(map);

        // Solution 2
        flags.entrySet().removeIf(entry -> LocalDate.parse(entry.getValue()).isBefore(LocalDate.now().minusDays(90)));
        System.out.println(flags);
    }

    /*
     * 2️⃣ Find the Earliest Scheduled Meeting Slot for a Team
     * 📌 Scenario: Your company’s scheduling system receives meeting slot requests
     * in the format {start, end}. Find the earliest available time slot of at least
     * 30 minutes.
     * 🔹 Input:
     * meetings = [[9:00, 9:30], [10:00, 10:30], [11:00, 12:00]]
     * workingHours = [9:00, 17:00]
     * 🔹 Output:
     * "9:30 - 10:00"
     */
    @Test
    public void findEarliestScheduledMeeting() {
        List<LocalTime[]> meetings = List.of(
                new LocalTime[] { LocalTime.of(9, 0, 0), LocalTime.of(9, 30, 0) },
                new LocalTime[] { LocalTime.of(10, 0, 0), LocalTime.of(10, 30, 0) },
                new LocalTime[] { LocalTime.of(11, 0, 0), LocalTime.of(12, 30, 0) },
                new LocalTime[] { LocalTime.of(9, 0, 0), LocalTime.of(9, 15, 0) },
                new LocalTime[] { LocalTime.of(8, 0, 0), LocalTime.of(9, 15, 0) },
                new LocalTime[] { LocalTime.of(8, 0, 0), LocalTime.of(9, 40, 0) },
                new LocalTime[] { LocalTime.of(16, 30, 0), LocalTime.of(17, 00, 0) },
                new LocalTime[] { LocalTime.of(16, 30, 0), LocalTime.of(17, 15, 0) },
                new LocalTime[] { LocalTime.of(17, 0, 0), LocalTime.of(17, 15, 0) });

        LocalTime[] workingHours = new LocalTime[] { LocalTime.of(13, 15), LocalTime.of(19, 0) };
        LocalTime availableFrom = workingHours[0], endTime = workingHours[1];
        Duration minDuration = Duration.ofMinutes(30);
        meetings = new ArrayList<>(meetings);
        meetings.sort(Comparator.comparing(m -> m[0]));
        meetings.forEach(meeting -> System.out.println(Arrays.toString(meeting)));
        // for (LocalTime[] meeting : meetings){
        // if (Duration.between(availableFrom, meeting[0]).toMinutes()>=30){
        // System.out.println("Earliest available slot: " + availableFrom + " - " +
        // meeting[0]);
        // return;
        // }

        // if(meeting[1].isAfter(availableFrom)){
        // availableFrom = meeting[1];
        // }
        // }

        // if(Duration.between(availableFrom, endTime).toMinutes()>=30){
        // System.out.println("Earliest available slot: " + availableFrom + " - " +
        // endTime);
        // } else {
        // System.out.println("No available 30-minute slot.");
        // }

        for (LocalTime[] meeting : meetings) {
            // Check if gap before meeting is enough
            if (Duration.between(availableFrom, meeting[0]).compareTo(minDuration) >= 0) {
                System.out.println("Earliest available slot: " + availableFrom + " - " + meeting[0]);
                return;
            }
            // Move available pointer forward if meeting ends later
            if (meeting[1].isAfter(availableFrom)) {
                availableFrom = meeting[1];
            }

        }

        if (Duration.between(availableFrom, endTime).compareTo(minDuration) >= 0) {
            System.out.println("Earliest available slot: " + availableFrom + " - " + endTime);
        } else {
            System.out.println("No available 30-minute slot.");
        }
    }

    /*
     * 3️⃣ Anonymize User Data Before Exporting Reports
     * 📌 Scenario: Your system generates user reports that include sensitive
     * information. Replace last names with *** while keeping first names visible.
     * 🔹 Input:
     * ["Alice Johnson", "Bob Smith", "Charlie Brown"]
     * 🔹 Output:
     * ["Alice ***", "Bob ***", "Charlie ***"]
     */
    @Test
    public void anonymizeUserLastName() {
        List<String> names = List.of("Alice Johnson", "Bob Smith", "Charlie Brown");
        System.out.println(names.stream().map(name -> name.split("\s+")[0].concat(" ***")).toList());
    }

    /*
     * 1️⃣ How to Send Form-Encoded Data in Rest Assured?
     * 📌 Question: How do you send x-www-form-urlencoded data in an API request
     * using Rest Assured?
     */
    @Test
    public void sendFOrmEncodedDataAPIRequest() {
        RestAssured.given()
                .queryParam("param with space", "value with !@#$")
                .when()
                .get("https://postman-echo.com/get")
                .then()
                .statusCode(200)
                .log().all()
                .body("args.'param with space'", equalTo("value with !@#$"));

        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("my_param", "data with spaces & special characters")
                .when()
                .post("https://postman-echo.com/post")
                .then()
                .log().all()
                .statusCode(200)
                .body("form.my_param", equalTo("data with spaces & special characters"));

    }

    /*
     * 2️⃣ How to Capture Screenshots in Selenium on Test Failure?
     * 📌 Question: How do you capture a screenshot when a Selenium test case fails?
     */
    WebDriver driver;

    @Test
    public void captureScreenshotonTestFailure() {
        driver = new ChromeDriver();
        driver.get("https://dummyjson.com/");
        driver.findElement(By.xpath("//*[text()='Welcome Back!']")).isDisplayed();
    }

    @AfterMethod
    public void afterTest(ITestResult result) throws IOException {
        if (!result.isSuccess() && driver != null) {
            File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(result.getName() + ".png");
            FileHandler.copy(tempFile, destFile);
            System.out.println("Screenshot caputred and saved at Location: " + destFile.getAbsolutePath());
        }
        driver.quit();
    }

    /*
     * 3️⃣ What is the Role of Status Code 429 in APIs?
     * 📌 Question: What does HTTP status code 429 - Too Many Requests mean, and how
     * should you handle it in automation?
     * 
     * ✅ HTTP Status Code 429 - Too Many Requests
     * 🔍 What it Means:
     * HTTP status code 429 indicates that the client has sent too many requests in
     * a given amount of time, and the server is throttling or rate-limiting the
     * requests to prevent overload or abuse.
     * 
     * 📘 Typical Scenarios:
     * Exceeding API rate limits (e.g., 100 requests per minute).
     * Sending repeated requests in quick succession (often during test automation
     * or load tests).
     * Public APIs or services (like GitHub, Twitter, or Stripe) enforcing usage
     * limits per user/IP/token.
     * 
     * 1. Implement Retry Logic with Backoff:
     * Use an exponential backoff strategy when you receive a 429. Wait a bit before
     * retrying, and increase the wait time after each retry.
     * 
     * 2. Respect Retry-After Header (if present):
     * Some APIs include a Retry-After header telling you how many seconds to wait
     * before retrying.
     * 
     * 3. Throttle Your Test Cases:
     * If your tests are hitting the rate limit:
     * Add delays between requests.
     * Use data-driven tests cautiously to avoid spikes.
     * Coordinate tests to avoid simultaneous hits on the same endpoint.
     */

    /*
     * 4️⃣ How to Verify Tooltip Text in Selenium?
     * 📌 Question: How do you verify the text of a tooltip that appears when
     * hovering over a web element?
     */
    @Test
    public void verifyTootlTipText() {
        driver = new ChromeDriver();
        driver.get("https://demoqa.com/tool-tips");
        WebElement hyperlink = driver.findElement(By.linkText("1.10.32"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoViewIfNeeded()", hyperlink);
        new Actions(driver).moveToElement(hyperlink).perform();
        Assert.assertEquals(new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.visibilityOfElementLocated(By.className("tooltip-inner"))).getText(),
                "You hovered over the 1.10.32");
        driver.quit();
    }

    /*
     * 5️⃣ How to Send an Auth Token Dynamically in Rest Assured?
     * 📌 Question: How do you extract a token from a login API and use it in
     * subsequent requests?
     */
    @Test
    public void chainAPIRequestWithDynamicToken() {
        RestAssured.baseURI = "https://dummyjson.com";
        String accessToken = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .body("{\"username\": \"loganl\", \"password\": \"loganlpass\"}")
                .post("/auth/login")
                .then().log().all().extract().response().jsonPath().getString("accessToken");

        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken).get("/auth/me").then().log().all()
                .body("firstName", equalTo("Logan"))
                .body("username", equalTo("loganl"));
    }
}
