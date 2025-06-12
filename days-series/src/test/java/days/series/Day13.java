package days.series;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Day13 {

    /*
     * 1️⃣ Check if Two Strings are Rotations of Each Other
     * 📌 Question: Given two strings, check if one is a rotation of the other.
     * 🔹 Input: "waterbottle", "erbottlewat"
     * 🔹 Output: true
     * four rfou => rfourofu => r-four-ofu
     * four urfo => urfourfo => ur-four-fo
     * four ourf =? ourfourf => our-four-f
     */
    @Test(dataProvider = "StringProvider")
    public void rotationOfStrings(String s1, String s2) {
        Assert.assertTrue(s1.length() == s2.length() && (s1 + s1).contains(s2), "Strings: " + s1 + " " + s2);
    }

    @DataProvider(name = "StringProvider")
    public Object[][] stringData() {
        return new Object[][] {
                { "waterbottle", "erbottlewat" },
                { "four", "ourf" },
                { "madam", "damam" },
                { "madam", "four" }
        };
    }

    /*
     * 2️⃣ Find the Majority Element in an Array
     * 📌 Question: Find the element that appears more than n/2 times in an array.
     * 🔹 Input: {3, 3, 4, 2, 3, 3, 3}
     * 🔹 Output: 3
     */
    @Test
    public void findMajorityElement() {
        int[] arr = { 3, 3, 4, 2, 1, 3, 1 };
        int count = 0, candidate = 0;
        for (int num : arr) {
            if (count == 0)
                candidate = num;
            count += (num == candidate) ? 1 : -1;
        }
        int ele = candidate;
        long occurrences = Arrays.stream(arr).filter(n -> n == ele).count();
        if (occurrences >= arr.length / 2)
            System.out.println("Majority Element: " + candidate);
        else
            System.out.println("No Majority Element");
    }

    /*
     * 3️⃣ Implement a Functional Interface to Find Maximum in a List
     * 📌 Question: Implement an interface to find the maximum number in a list.
     * 🔹 Input: [10, 25, 32, 8]
     * 🔹 Output: 32
     */
    @FunctionalInterface
    interface MaxFinder {
        int findMax(List<Integer> list);
    }

    @Test
    public void findMaxWithFunctionalInterface() {
        MaxFinder maxFinder = list -> list.stream().max(Integer::compareTo).orElse(0);
        System.out.println(maxFinder.findMax(Arrays.asList(12, 12, 3, 45, 78, 123, 109, 87)));
    }

    /*
     * 1️⃣ What Are Idempotent and Safe Methods in REST APIs?
     * 📌 Question: What are idempotent and safe methods in REST APIs?
     * ✅ Answer:
     * Idempotent Methods: Produce the same result no matter how many times they are
     * called.
     * ✅ Examples: GET, PUT, DELETE
     * Safe Methods: Do not modify server data and are used for retrieval.
     * ✅ Examples: GET, HEAD, OPTIONS.
     */
    @Test
    public void testIdempotentSafeMethods() {
        // Dummy Delete API - Idempotent due to same result
        for (int i = 0; i < 3; i++) {
            RestAssured.given().pathParam("userId", "1")
                    .delete("http://localhost:3000/dummy/users/{userId}").then().statusCode(202)
                    .body("deletedUserId", is("1"));
        }

        // Safe API - GET
        for (int i = 0; i < 3; i++) {
            RestAssured.given().pathParam("userId", "1")
                    .head("http://localhost:3000/dummy/users/{userId}").then().statusCode(200)
                    .header("ContentType", equalTo("application/json"));
        }
    }

    /*
     * 2️⃣ Generate and Use Dynamic Authentication Tokens in Rest Assured
     * 📌 Question: How do you generate a dynamic authentication token and use it in
     * API requests?
     */
    @Test
    public void testDynamicAuthToken() {
        String token = RestAssured.given()
                .body("{\"username\": \"user1\", \"password\": \"pass456\"}")
                .header("Content-Type", "application/json")
                .when().post("http://localhost:3000/auth")
                .then().extract().path("token");

        RestAssured.given().header("authorization", "Bearer " + token)
                .when().get("http://localhost:3000/users").then().statusCode(200).log().all();
    }

    /*
     * 3️⃣ Read Excel Data and Store It in a HashMap Using Apache POI
     * 📌 Question: How do you read key-value pairs from Excel and store them in a
     * HashMap?
     */
    @Test
    public void readExcelDataAndStoreInHashMap() {
        try (FileInputStream fileInputStream = new FileInputStream("./src/main/resources/userdata.xlsx");
                Workbook workbook = new XSSFWorkbook(fileInputStream);) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                throw new IllegalStateException("Header row is missing");
            }

            List<Map<String, String>> listMap = new ArrayList<>();
            for (int i =1; i < sheet.getPhysicalNumberOfRows(); i ++){
                Row row = sheet.getRow(i);
                Map<String, String> rowMap = new HashMap<>();
                for (int j =0; j < row.getLastCellNum(); j++){
                    rowMap.put(
                        Day09.getCellValue(headerRow.getCell(j)),
                        Day09.getCellValue(row.getCell(j))
                    );
                }
                listMap.add(rowMap);
            }
            // IntStream.range(1, sheet.getPhysicalNumberOfRows())
            //         .mapToObj(sheet::getRow)
            //         .forEach(row -> {
            //             Map<String, String> rowMap = new HashMap<>();
            //             IntStream.range(0, row.getLastCellNum())
            //                     .forEach(cellIndex -> {
            //                         rowMap.put(
            //                                 Day09.getCellValue(headerRow.getCell(cellIndex)),
            //                                 Day09.getCellValue(row.getCell(cellIndex)));
            //                     });
            //             listMap.add(rowMap);
            //         });
            System.out.println(listMap);

            // Access Data from HashMap
            listMap.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 4️⃣ How to Handle HTTP 429 (Too Many Requests) in API Testing?
     * 📌 Question: What does HTTP status 429 (Too Many Requests) mean, and how do
     * you handle it?
     * ✅ Answer:
     * Reason: API rate limit exceeded.
     * Handling Strategies:
     * 🔹 Implement retry mechanisms
     * 🔹 Respect rate limit headers (Retry-After)
     * 🔹 Use API keys with higher quota
     */
    public Response retryRequest(Supplier<Response> request, Predicate<Response> shouldRetry, int maxattempts,
            int delayInMills) {
        int attempts = 0;
        Response response;
        do {
            response = request.get();
            if (!shouldRetry.test(response)) {
                return response;
            }
            attempts++;
            System.out.println("Retry Attempt " + attempts);
            if (attempts < maxattempts) {
                try {
                    Thread.sleep(delayInMills);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } while (attempts < maxattempts);
        return response;
    }

    @Test
    public void testTooManyRequests() {
        Supplier<Response> request = () -> RestAssured.get("http://localhost:3000/test429");
        Predicate<Response> shouldRetry = response -> response.getStatusCode() == 429;
        for (int i = 0; i < 4; i++) {
            try {
                Response response = retryRequest(request, shouldRetry, 3, 4000);
                response.then().statusCode(200).body("message", equalTo("Your request is processed"));
                System.out.println("Recieved Success Reponse i: " + i);
            } catch (AssertionError error) {
                System.out.println("Exception " + error.getMessage());
            }

        }
    }

    /*
     * 5️⃣ How to Delete Browser Cookies in Selenium?
     * 📌 Question: How do you delete browser cookies in Selenium before running
     * tests?
     */
    @Test
    public void testDeleteBrowserCookies() {
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized"));
        driver.get("https://www.saucedemo.com/");
        WebElement usernameElement = driver.findElement(By.id("user-name"));
        usernameElement.sendKeys("standard_user");
        WebElement passwordElement = driver.findElement(By.id("password"));
        passwordElement.sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        boolean isLoggedIn = wait.until(ExpectedConditions.urlContains("/inventory.html"));
        if (!isLoggedIn) {
            throw new IllegalStateException("User Not Logged In");
        }
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/inventory.html")));
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@data-test='error']")));
        Assert.assertEquals(error.getText(),
                "Epic sadface: You can only access '/inventory.html' when you are logged in.");
        driver.quit();
    }

}
