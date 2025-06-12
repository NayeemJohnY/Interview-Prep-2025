package days.series;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasKey;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Predicate;
import java.util.stream.IntStream;

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
import io.restassured.module.jsv.JsonSchemaValidator;

public class Day07 {

    /*
     * 1️⃣ Count Vowels in a String
     * 📌 Question: Count the number of vowels (a, e, i, o, u) in a given string.
     * 🔹 Input: "automation"
     * 🔹 Output: 5
     */
    @Test
    public void countVowels() {
        Predicate<Character> isVowel = e -> "aeiou".indexOf(e) != -1;
        System.out.println("automation".chars().mapToObj(i -> (char) i).filter(isVowel).count());
        System.out.println("ZFBRS".chars().mapToObj(i -> (char) i).filter(isVowel).count());
        System.out.println("".chars().mapToObj(i -> (char) i).filter(isVowel).count());

    }

    /*
     * 2️⃣ Convert List of Strings to Uppercase
     * 📌 Question: Convert all elements in a list of strings to uppercase.
     * 🔹 Input: ["java", "selenium", "testing"]
     * 🔹 Output: ["JAVA", "SELENIUM", "TESTING"]
     */
    @Test
    public void convertToUppercase() {
        List<String> list = Arrays.asList("Java", "Python", "Umberlla", "abacus", "node", "orange");
        System.out.println(list.stream().map(String::toUpperCase).toList());
    }

    /*
     * 3️⃣ Find Second Largest Number in an Array
     * 📌 Question: Return the second largest number in an array.
     * 🔹 Input: {10, 5, 20, 8}
     * 🔹 Output: 10
     */

    @Test
    public void findSecondLargest() {
        int[] a = new int[] { 1, 21, 3, 50, 10, 51, 60, 99, 99 };
        // Solution 1
        int[] b = Arrays.stream(a).distinct().sorted().toArray();
        System.out.println(b[b.length - 2]);

        // Solution 2
        System.out.println(IntStream.of(a).distinct().boxed().sorted(Collections.reverseOrder()).toList().get(1));

        // Solution 3 - using PriorityQueue for kth largest
        int k = 2;
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer integer : a) {
            if (queue.contains(integer))
                continue;
            queue.add(integer);
            if (queue.size() > k) {
                queue.poll();
            }
        }
        System.out.println(queue.peek());
    }

    /*
     * 4️⃣ Remove a Specific Element from a List
     * 📌 Question: Remove all occurrences of a given element from a list.
     * 🔹 Input: ["apple", "banana", "apple", "mango"], "apple"
     * 🔹 Output: ["banana", "mango"]
     */
    @Test
    public void removeSpecificElementsInList() {
        List<String> list = new ArrayList<>(Arrays.asList("apple", "banana", "apple", "mango"));

        // Solution 1:
        System.out.println(list.stream().filter(e -> !e.equals("apple")).toList());

        // Solution 2
        while (list.indexOf("apple") != -1) {
            list.remove("apple");
        }
        System.out.println(list);

    }

    /*
     * 5️⃣ Implement a Simple Interface for Multiplication
     * 📌 Question: Create an interface for multiplication and implement it using
     * lambda expressions.
     * 🔹 Input: multiply(5, 4)
     * 🔹 Output: 20
     */
    @FunctionalInterface
    public interface Multiplication<T> {
        T multiply(T a, T b);
    }

    @Test
    public void testMultiplication() {
        Multiplication<Double> multiplication = (n1, n2) -> n1 * n2;
        System.out.println(multiplication.multiply(123.45, 321.45));
    }

    /*
     * 1️⃣ Validate API Response Body Contains a Specific Key
     * 📌 Question: How do you check if a key exists in an API response using Rest
     * Assured?
     */
    @Test
    public void validateAPIReponseBodyHasKey() {
        RestAssured.get("https://dummyjson.com/posts").then()
                .body("posts.reactions", everyItem(hasKey("likes")));
    }

    /*
     * 2️⃣ Handle Calendar Date Pickers in Selenium
     * 📌 Question: How do you select a specific date from a web-based calendar in
     * Selenium?
     */
    @Test
    public void handleCalender() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.makemytrip.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-cy='closeModal']"))).click();

        By depatureDateSelector = By.cssSelector("[data-cy='departureDate']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(depatureDateSelector)).click();
        LocalDate localDate = LocalDate.now();

        wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.xpath(
                "//div[contains(@class, 'DayPicker-Day') and @aria-disabled='false' and " +
                        "contains(@aria-label, '" + localDate.format(DateTimeFormatter.ofPattern("MMM")) + "')]" +
                        "//p[text() < " + localDate.format(DateTimeFormatter.ofPattern("d")) + "]"))));

        localDate = localDate.plusDays(2);
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("E MMM dd YYYY");
        WebElement dateToSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//div[contains(@class, 'DayPicker-Day') and @aria-label='" + localDate.format(dateformatter) + "']")));
        ((JavascriptExecutor) (driver)).executeScript("arguments[0].scrollIntoViewIfNeeded()", dateToSelect);
        dateToSelect.click();
        Assert.assertEquals(driver.findElement(
                depatureDateSelector).getText(), localDate.format(DateTimeFormatter.ofPattern("d MMMYY")));
        driver.quit();
    }

    /*
     * 3️⃣ Validate API Pagination in Rest Assured
     * 📌 Question: How do you verify if an API returns the correct paginated
     * results?
     */
    @Test
    public void validateAPIPagination() {
        RestAssured.given()
                .baseUri("https://dummyjson.com")
                .queryParam("skip", 10)
                .queryParam("limit", 30)
                .get("/todos").then()
                .body("todos.id", equalTo(IntStream.range(11, 41).boxed().toList()));

    }

    /*
     * 4️⃣ Handle Broken Images in Selenium
     * 📌 Question: How do you verify if images on a webpage are broken using
     * Selenium?
     */
    @Test
    public void verifyImagesAreBroken() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://the-internet.herokuapp.com/broken_images");
        List<WebElement> imageElements = new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("img")));

        imageElements.parallelStream().forEach(img -> {
            String src = img.getDomProperty("src");
            HttpURLConnection httpURLConnection;
            try {
                httpURLConnection = (HttpURLConnection) new URI(src).toURL().openConnection();
                httpURLConnection.setRequestMethod("HEAD");
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() != 200) {
                    System.out.println("Good -> Broken Image: " + src);
                }
                System.out.println("Broken -> Broken Image: " + src);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        driver.quit();
    }

    /*
     * 5️⃣ Validate JSON Schema with Additional Properties in Rest Assured
     * 📌 Question: How do you validate a JSON schema and ensure no additional
     * fields exist?
     * 
     * "additionalProperties": false is json schema
     */
    @Test
    public void validateJSONSchemaWIthAdditionalFields() {
        RestAssured.given().when()
                .get("https://jsonplaceholder.typicode.com/users/1").then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(
                        "json_schema.json"));
    }
}
