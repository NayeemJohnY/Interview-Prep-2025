package days.series;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Day23 {

    /*
     * 1️⃣ Validate Phone Numbers in a List
     * 📌 Scenario: Your system stores customer contact details. Given a list of
     * phone numbers, filter out invalid ones. A valid phone number should have
     * exactly 10 digits.
     * 
     * 🔹 Input:
     * ["9876543210", "12345", "9988776655", "98765abcd", "1122334455"]
     * 🔹 Output:
     * ["9876543210", "9988776655", "1122334455"]
     */
    @Test
    public void validatePhoneNumbers() {
        List<String> phoneNumbers = List.of("9876543210", "12345", "9988776655", "98765abcd", "1122334455");

        // Soltution 1
        Pattern pattern = Pattern.compile("^[0-9]{10}$");
        System.out.println(phoneNumbers.stream().filter(str -> pattern.matcher(str).matches()).toList());

        // Solution 2
        phoneNumbers = phoneNumbers.stream().filter(str -> str.length() == 10 && str.replaceAll("\\d+", "").isEmpty())
                .toList();
        System.out.println(phoneNumbers);

    }

    /*
     * 2️⃣ Find the Most Common Word in a Review
     * 📌 Scenario: You are analyzing customer feedback. Find the most frequently
     * used word in a given review.
     */
    @Test
    public void findMostCommonWord() {
        String str = "Great service, great experience. This service was excellent!";
        Arrays.stream(str.toLowerCase().split("\\s+")).collect(
                Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
                .ifPresent(System.out::println);
    }

    /*
     * 3️⃣ Find the First Failing Test Case in a List
     * 📌 Scenario: Your test suite logs test results in a list. Find the first test
     * case that failed.
     * 
     * 🔹 Input:
     * ["PASS", "PASS", "FAIL", "PASS", "FAIL"]
     * 🔹 Output: 2 (Index of first "FAIL")
     */
    @Test
    public void findFirstFailingTest() {
        List<String> testResults = List.of("PASS", "PASS", "FAIL", "PASS", "FAIL");
        System.out.println(testResults.indexOf("FAIL"));
    }

    /*
     * 1️⃣ How to Send a JSON Payload in Rest Assured?
     * 📌 Question: How do you send a JSON request body in a POST API request using
     * Rest Assured?
     */
    @Test
    public void sendJSONPayloadInAPIReqeust() {
        RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .body(new File("./src/main/resources/json_payload.json"))
                .post("https://dummyjson.com/users/add")
                .then().log().all().statusCode(201).body("firstName", equalTo("Muhammad"))
                .body("birthDate", equalTo(""));
    }

    /*
     * 2️⃣ How to Handle Dropdowns in Selenium?
     * 📌 Question: How do you select an option from a dropdown menu in Selenium?
     */
    @Test
    public void handleDropDowns() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/dropdown");
        Select select = new Select(driver.findElement(By.id("dropdown")));
        select.selectByVisibleText("Option 1");
        System.out.println(select.getFirstSelectedOption().getText());
        select.selectByValue("2");
        System.out.println(select.getFirstSelectedOption().getText());
        select.selectByIndex(1);
        System.out.println(select.getFirstSelectedOption().getText());
        driver.quit();
    }

    /*
     * 3️⃣ Difference Between GET and HEAD Requests in API Testing
     * 📌 Question: What is the difference between the HTTP methods GET and HEAD?
     * 
     * 🔍 1️⃣ GET – Retrieve Data
     * ✅ Definition:
     * The GET method is used to retrieve a resource from the server. It fetches the
     * full content of the resource.
     * 🧠 Behavior:
     * Returns the full response, including headers and body (content).
     * The data returned could be a webpage, a JSON object, an image, etc.
     * 
     * 🔒 2️⃣ HEAD – Retrieve Headers Only
     * ✅ Definition:
     * The HEAD method is similar to GET, but it only retrieves the headers of the
     * response and not the body.
     * 🧠 Behavior:
     * Used when you only need metadata (headers) about a resource, not the content
     * itself.
     * Useful for checking things like the status code, content type, last modified
     * date, or content length without downloading the entire resource.
     */
    @Test
    public void useGetAndHeadAPIRequest() {
        RestAssured.given().head("https://dummyjson.com/posts").then().log().all();
        System.out.println("==============================================");
        RestAssured.given().get("https://dummyjson.com/posts").then().log().all();
    }

    /*
     * 4️⃣ How to Handle Frames in Selenium?
     * 📌 Question: How do you switch to an iframe in Selenium?
     */
    @Test
    public void handleFrames() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/iframe");
        try {
            driver.findElement(By.xpath("//p[text()='Your content goes here.']"));
        } catch (NoSuchElementException e) {
            System.out.println(e.getRawMessage());
            new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                    ExpectedConditions.frameToBeAvailableAndSwitchToIt("mce_0_ifr"));
            Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Your content goes here.']")).isDisplayed());
        }
        driver.quit();
    }

    /*
     * 5️⃣ How to Verify Response Headers in Rest Assured?
     * 📌 Question: How do you validate the response headers in an API response?
     */
    @Test
    public void validateResponseHeaders() {
        RestAssured.given().get("https://dummyjson.com/posts").then()
                .header("Content-Type", containsString(ContentType.JSON.toString()))
                .header("X-Ratelimit-Limit", equalTo("100"));
    }
}
