package com.interview_prepare;

import static org.hamcrest.Matchers.array;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.lang3.Validate;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Day14 {

    /*
     * 1️⃣ Reverse Vowels in a String
     * 📌 Question: Given a string, reverse only the vowels while keeping other
     * characters unchanged.
     * 🔹 Input: "automation"
     * 🔹 Output: "oitanamaut"
     */
    @Test
    public void reverseVowelsInString() {
        String str = "Education";
        String output = "odicatuEn";
        char[] arr = str.toCharArray();
        String vowels = "aeiouAEIOU";
        int left = 0;
        int right = arr.length - 1;

        // Solution 1
        while (left < right) {
            if (vowels.indexOf(arr[left]) != -1) {
                if (vowels.indexOf(arr[right]) != -1) {
                    char temp = arr[left];
                    arr[left] = arr[right];
                    arr[right] = temp;
                    left++;
                    right--;
                } else {
                    right--;
                }
            } else {
                left++;
            }
        }

        Assert.assertEquals(new String(arr), output);
        arr = str.toCharArray();
        left = 0;
        right = arr.length - 1;
        // Solution 2
        while (left < right) {
            while (left < right && vowels.indexOf(arr[left]) == -1)
                left++;
            while (left < right && vowels.indexOf(arr[right]) == -1)
                right--;

            char temp = arr[left];
            arr[left++] = arr[right];
            arr[right--] = temp;
        }
        Assert.assertEquals(new String(arr), output);
    }

    /*
     * 2️⃣ Find the Longest Consecutive Sequence in an Array
     * 📌 Question: Find the longest sequence of consecutive numbers in an unsorted
     * array.
     * 🔹 Input: {100, 4, 200, 1, 3, 2}
     * 🔹 Output: 4 (sequence: [1, 2, 3, 4])
     */
    @Test
    public void longestConsectiveSeq() {
        int[] array = new int[] { 100, 4, 200, 1, 3, 2, 201, 202, 203, 204, 205 };
        // Solution 1
        Arrays.sort(array);
        int maxStart = 0;
        int start = 0;
        int maxCount = 1;
        int currentCount = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i - 1]) {
                continue; // skip duplicate
            }
            if (array[i] == array[i - 1] + 1) {
                currentCount++;
            } else {
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                    maxStart = start;
                }
                start = i;
                currentCount = 1;
            }
        }
        if (currentCount > maxCount) {
            maxCount = currentCount;
            maxStart = start;
        }
        System.out.println(Arrays.toString(Arrays.copyOfRange(array, maxStart, maxStart + maxCount)));

        // Solution 2 Using HashSet
        array = new int[] { 100, 4, 200, 1, 3, 2, 201, 202, 203, 204, 205 };
        HashSet<Integer> hashSet = new HashSet<>();
        List<Integer> longestConsectiveSeqList = new ArrayList<>();
        for (int i : array) {
            hashSet.add(i);
        }

        int currentNumber = 0;
        int maxSize = 0;
        for (Integer num : hashSet) {
            List<Integer> currentSeq = new ArrayList<>();
            if (!hashSet.contains(num - 1)) {
                currentNumber = num;
                currentSeq.add(num);

                while (hashSet.contains(currentNumber + 1)) {
                    currentNumber++;
                    currentSeq.add(currentNumber);
                }

                if (currentSeq.size() > maxSize) {
                    maxSize = currentSeq.size();
                    longestConsectiveSeqList = currentSeq;
                }
            }
        }
        System.out.println(longestConsectiveSeqList);

    }

    /*
     * 3️⃣ Implement a Functional Interface for String Reversal
     * 📌 Question: Implement an interface to reverse a string using a lambda
     * expression.
     * 🔹 Input: "SDET"
     * 🔹 Output: "TEDS"
     */
    @FunctionalInterface
    interface Reverse {
        String reverse(String s);
    }

    @Test
    public void reverseStringFunctionalInterface() {
        Reverse reverse = s -> new StringBuilder(s).reverse().toString();
        System.out.println(reverse.reverse("SDET"));
    }

    /*
     * 1️⃣ What Is the Difference Between API Testing and UI Testing?
     * 📌 Question: How does API testing differ from UI testing?
     * 
     * ✅ Answer:
     * API Testing:
     * Testing the backend services or endpoints directly
     * Focuses on verifying data exchange, request/response handling and business
     * logic.
     * Validates how components interact under the hood.
     * Speed Fast – No UI rendering involved
     * Integration or system level Testing
     * 
     * UI Testing:
     * Testing the user interface of an application
     * Ensures elements like Layout, buttons, text fields, and navigation function
     * correctly.
     * Ensures the user experience works as expected.
     * Speed Slower – Involves rendering and UI event handling
     * End-to-end or functional level Testing
     * 
     * 🎯 Key Takeaway: API testing is faster and more stable, while UI testing is
     * needed for end-to-end validation.
     */

    /*
     * 2️⃣ Validate Response Headers in Rest Assured
     * 📌 Question: How do you validate specific response headers in an API request?
     * 
     */
    @Test
    public void valiateResposneHeaders() {
        RestAssured.get("https://dummyjson.com/posts").then()
                .assertThat().header("Content-Type", "application/json; charset=utf-8");
    }

    /*
     * 3️⃣ Read Excel Data Using Apache POI Without Looping
     * 📌 Question: How do you read a specific cell value from an Excel sheet
     * without iterating?
     */
    @Test
    public void readExcelDataSpecificCellWithoutLoop() {
        try (FileInputStream fileInputStream = new FileInputStream("./src/main/resources/userdata.xlsx");
                Workbook workbook = new XSSFWorkbook(fileInputStream);) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.getCell(0);
            System.out.println(Day09.getCellValue(cell));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 4️⃣ Handle Dropdowns in Selenium Without Select Class
     * 📌 Question: How do you select a dropdown option without using the Select
     * class?
     */
    @Test
    public void handleDropDownWithoutSelectClass(){
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized"));
        driver.get("https://www.jquery-az.com/bootstrap4/demo.php?ex=79.0_1");
        driver.findElement(By.id("btnDropdownDemo")).click();;
        driver.findElement(By.xpath(("//*[text()='Bootstrap 4']"))).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("bootstrap-4"));
        driver.quit();

    }


    /*
     * 5️⃣ How to Handle 500 Internal Server Error in API Testing?
     * 📌 Question: What should you do when an API returns a 500 Internal Server
     * Error?
     * 
     * ✅ Answer:
     * Check API Logs to identify the issue.
     * Validate Input Data to ensure proper request format.
     * Retry Mechanism if the error is intermittent.
     */
    Response retryRequest(Supplier<Response> request, Predicate<Response> shouldRetry, int maxattempts,
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
                    e.printStackTrace();
                }
            }
        } while (attempts < maxattempts);

        return response;
    }

    @Test
    public void handle500APIErrors(){
        Response finalResponse = retryRequest(() -> RestAssured.get("http://localhost:3000/test500"), 
        ((Predicate<Response>) res -> res.getStatusCode()>=500), 3, 2000);

        finalResponse.then().statusCode(200);
    }
}
