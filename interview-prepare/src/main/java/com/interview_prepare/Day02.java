package com.interview_prepare;

import static org.hamcrest.Matchers.lessThan;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class Day02 {

    public Character findFirstNonRepeatingCharacter(String str) {
        LinkedHashMap<Character, Integer> linkedHashMap = new LinkedHashMap<>();
        for (Character ch : str.toCharArray()) {
            linkedHashMap.put(ch, linkedHashMap.getOrDefault(ch, 0) + 1);
        }
        for (Entry<Character, Integer> entry : linkedHashMap.entrySet()) {
            if (entry.getValue() == 1)
                return entry.getKey();
        }
        return '_';
    }

    public void reverseWordsInString(String str) {
        String[] words = str.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int index = words.length - 1; index >= 0; index--) {
            result.append(words[index]).append(" ");
        }
        System.out.println(result.toString().trim());
    }

    public void removeDuplicatesInArrayRetainOrder(int... arr) {
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        for (int i : arr) {
            linkedHashSet.add(i);
        }
        System.out.println(linkedHashSet);

        // Solution 2
        System.out.println(Arrays.toString(IntStream.of(arr).distinct().toArray()));

    }

    public void isAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) {
            System.out.println("Is Anagram: false");
            return;
        }

        // Solution 1
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        System.out.println("ArraySort Is Anagram: " + Arrays.equals(arr1, arr2));

        // Solution 2
        HashMap<Character, Integer> frequencyHashMap = new HashMap<>();
        for (Character ch : s1.toCharArray()) {
            frequencyHashMap.put(ch, frequencyHashMap.getOrDefault(ch, 0) + 1);
        }

        for (Character ch : s2.toCharArray()) {
            if (!frequencyHashMap.containsKey(ch)) {
                System.out.println("HashMap Is Anagram: false");
                return;
            }
            frequencyHashMap.put(ch, frequencyHashMap.get(ch) - 1);
            if (frequencyHashMap.get(ch) == 0) {
                frequencyHashMap.remove(ch);
            }
        }
        System.out.println("HashMap Is Anagram: " + frequencyHashMap.isEmpty());

        // Solution 3
        HashSet<Character> hashSet = new HashSet<>();
        for (Character ch : s1.toCharArray()) {
            hashSet.add(ch);
        }

        for (Character ch : s2.toCharArray()) {
            hashSet.remove(ch);
        }

        System.out.println("Hashset Is Anagram: " + hashSet.isEmpty());
    }

    public void findMissingNumber(int n, int... arr) {
        int missing = (n * (n + 1) / 2) - IntStream.of(arr).sum();
        if (missing != 0)
            System.out.println("Missing Number: " + missing);
        else
            System.out.println("No number is missing");
    }

    public void validateJSONScehma() {
        RestAssured.given().when()
                .get("https://jsonplaceholder.typicode.com/users/1").then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("json_schema.json"));

        try {
            RestAssured.given().when()
                    .get("https://jsonplaceholder.typicode.com/posts/1").then().statusCode(200)
                    .body(JsonSchemaValidator.matchesJsonSchema(
                            "{ \"type\": \"object\",  \"required\": [\"email\"], \"properties\": {\"email\": {\"type\": \"string\"}}}"));
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    public void extactDynamicTableData() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.nyse.com/ipo-center/recent-ipo");
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        List<WebElement> rowsPriceDeals = webDriverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("table[data-testid='priced-deals'] tbody tr")));
        List<List<String>> tableList = new ArrayList<>();
        for (WebElement row : rowsPriceDeals) {
            List<String> columnList = new ArrayList<>();
            for (WebElement td : row.findElements(By.cssSelector("td"))) {
                columnList.add(td.getText());
            }
            tableList.add(columnList);
        }
        System.out.println("Table: \n" + tableList);
        driver.quit();
    }

    public void apiChaining() {
        Response response = RestAssured.given().when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then().statusCode(200).extract().response();
        int userId = response.path("[0].id");
        response = RestAssured.given()
                .queryParam("userId", userId)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then().statusCode(200).extract().response();

        Assert.assertEquals((int) response.path("[0].id"), userId);
    }

    public void handleJavascriptAlerts() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("alert('This is Simple Alert with Ok button')");
        Alert alert = driver.switchTo().alert();
        System.out.println("Simple Alert: " + alert.getText());
        alert.accept();

        jsExecutor.executeScript("confirm('This is Confirm Alert with Ok/Cancel Button')");
        WebDriverWait webDriverWait = new WebDriverWait(driver,
                Duration.ofSeconds(30));
        alert = webDriverWait.until(ExpectedConditions.alertIsPresent());
        System.out.println("Confirm Alert: " + alert.getText());
        alert.dismiss();

        jsExecutor.executeScript("prompt('This is Prompt Alert with Input Field')");
        alert = webDriverWait.until(ExpectedConditions.alertIsPresent());
        alert = driver.switchTo().alert();
        System.out.println("Prompt Alert: " + alert.getText());
        alert.sendKeys("Keys Sending to Prompt");
        alert.accept();

        driver.quit();
    }

    public void apiResponseThreshold() {
        RestAssured.given().get("https://reqres.in/users").then()
                .time(lessThan(2000L));
    }

    public static void main(String[] args) {
        /*
         * 1️⃣ Find the First Non-Repeating Character in a String
         * 📌 Question: Given a string, return the first non-repeating character. If all
         * characters repeat, return '_ '
         * ➡ Example:
         * Input: "automation"
         * Output: 'u'
         * 
         */
        Day02 day02 = new Day02();
        System.out.println(day02.findFirstNonRepeatingCharacter("HelloWelcomeHi"));
        System.out.println(day02.findFirstNonRepeatingCharacter("aabbccddee"));

        /*
         * 2️⃣ Reverse Words in a String
         * 📌 Question: Given a sentence, reverse the order of words while keeping the
         * words themselves unchanged.
         * ➡ Example:
         * Input: "SDET interviews are tricky"
         * Output: "tricky are interviews SDET"
         */
        day02.reverseWordsInString("SDET interviews are tricky");
        /*
         * 3️⃣ Remove Duplicates from an Array
         * 📌 Question: Given an integer array, remove duplicate elements while
         * maintaining order.
         * ➡ Example:
         * Input: [4, 2, 9, 4, 2, 8]
         * Output: [4, 2, 9, 8]
         */
        day02.removeDuplicatesInArrayRetainOrder(4, 2, 9, 4, 2, 8);

        /*
         * 4️⃣ Check If Two Strings Are Anagrams
         * 📌 Question: Given two strings, check if they are anagrams (contain the same
         * characters in any order).
         * ➡ Example:
         * Input: "listen", "silent"
         * Output: true
         */
        day02.isAnagram("listen", "silent");
        day02.isAnagram("abcde", "cdeds");
        day02.isAnagram("listen", "tenil");

        /*
         * 5️⃣ Find the Missing Number in an Array
         * 📌 Question: Given an array containing numbers from 1 to n, with one number
         * missing, find the missing number.
         * ➡ Example:
         * Input: [1, 2, 4, 5] (n=5)
         * Output: 3
         * n(n+1)/ 2 => 5*6/2 = 15 => 30 => 9+2= 11 +1 = 12
         */
        day02.findMissingNumber(5, 1, 2, 4, 5);
        day02.findMissingNumber(10, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        day02.findMissingNumber(10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        /*
         * 1️⃣ Validate JSON Schema in Rest Assured
         * 📌 Question: How would you validate the JSON schema of an API response?
         */
        day02.validateJSONScehma();

        /*
         * 2️⃣ Extract Data from Dynamic Web Tables in Selenium
         * 📌 Question: How would you extract data from a dynamic table in Selenium?
         */
        day02.extactDynamicTableData();

        /*
         * 3️⃣ API Chaining in Rest Assured
         * 📌 Question: How do you chain API requests where the response from one
         * request is used in another?
         */
        day02.apiChaining();

        /*
         * 4️⃣ Handle JavaScript Alerts in Selenium
         * 📌 Question: How do you handle JavaScript alerts in Selenium?
         */
        day02.handleJavascriptAlerts();
        /*
         * 5️⃣ Verify API Response Time in Rest Assured
         * 📌 Question: How do you ensure an API responds within a given time threshold?
         */
        day02.apiResponseThreshold();
    }
}
