package days.series;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

public class Day01 {

    public void uniqueAndDuplicateCustomerIds() {
        List<Integer> customerIds = Arrays.asList(1123, 1101, 1456, 1101, 2000, 1987, 1456);
        HashSet<Integer> uniqueCustomerIds = new HashSet<>();
        HashSet<Integer> duplicateCustomerIds = new HashSet<>();
        for (Integer customerId : customerIds) {
            if (!uniqueCustomerIds.add(customerId))
                duplicateCustomerIds.add(customerId);
        }

        System.out.println("Unique Customer IDs: " + uniqueCustomerIds);
        System.out.println("Duplicate Customer IDs: " + duplicateCustomerIds);

    }

    public static void main(String[] args) {

        /*
         * 1️⃣ Data Handling Question (Similar to Excel Duplication)
         * 📌 Scenario: You are given a list of customer IDs from a CSV file. Some IDs
         * are duplicated. How would you ensure unique records while processing the file
         * in Java?
         */
        Day01 day01 = new Day01();
        day01.uniqueAndDuplicateCustomerIds();

        /*
         * 2️⃣ Debugging & Output Question
         * 📌 Question: What will be the output of the following Java code?
         * 
         */
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("apple", 3);
        System.out.println(map); // {apple:3, banana:2}

        /*
         * 3️⃣ Automation Framework Question
         * 📌 Scenario: Your Selenium test script sometimes fails because an element is
         * not visible in time. How would you handle this?
         */
        WebDriver driver = new ChromeDriver();
        driver.get("https://google.com");
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement element = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        element.sendKeys("Hello After Wait");
        driver.quit();

        /*
         * 4️⃣ API Testing Question (Rest Assured)
         * 📌 Scenario: You need to validate an API response to check if a particular
         * field exists and has a specific value.
         */
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.given()
                .when().header("x-api-key", "reqres-free-v1")
                .log().all()
                .get("/api/users/2")
                .then().statusCode(200)
                .body("data.email", endsWith("@reqres.in"));

    }
}
