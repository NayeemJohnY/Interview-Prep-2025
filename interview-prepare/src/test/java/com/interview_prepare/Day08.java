package com.interview_prepare;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.HasCdp;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v136.network.Network;
import org.openqa.selenium.devtools.v136.network.model.Headers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;;

public class Day08 {

    /*
     * 1️⃣ Validate Balanced Parentheses
     * 📌 Question: Check if a string containing ()[]{} is balanced.
     * 🔹 Input: "({[]})"
     * 🔹 Output: true
     */
    @Test
    public void validatbalancedParantheses() {
        Stack<Character> stack = new Stack<>();
        String str = "(x * (a + b) - {y / [z - 3]}";
        str = str.replaceAll("[^()\\[\\]{}]", "");
        boolean isValid = true;
        for (Character ch : str.toCharArray()) {
            if ("({[".indexOf(ch) != -1) {
                stack.push(ch);
            } else if (stack.isEmpty() ||
                    ch == ']' && stack.pop() != '[' ||
                    ch == ')' && stack.pop() != '(' ||
                    ch == '}' && stack.pop() != '{')
                isValid = false;

        }
        System.out.println(isValid && stack.isEmpty());
    }

    /*
     * 2️⃣ Find the Longest Word in a Sentence
     * 📌 Question: Return the longest word from a given sentence.
     * 🔹 Input: "Java is great for automation"
     * 🔹 Output: "automation"
     */
    @Test
    public void findLongestWordInSentence() {
        String str = "Java is great for automation";
        Arrays.stream(str.split("\\s+")).max(Comparator.comparingInt(String::length)).ifPresent(System.out::println);
    }

    /*
     * 3️⃣ Count Character Frequency in a String
     * 📌 Question: Count occurrences of each character in a string.
     * 🔹 Input: "SDET"
     * 🔹 Output: {S=1, D=1, E=1, T=1}
     */
    @Test
    public void freqMap() {
        String str = "sdet automation";
        System.out.println(str.chars().mapToObj(e -> (char) e)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
    }

    /*
     * 4️⃣ Find Common Words in Two Sentences
     * 📌 Question: Find words that appear in both sentences.
     * 🔹 Input: "Java is fun", "Python is fun"
     * 🔹 Output: ["is", "fun"]
     */
    @Test
    public void findCommonWordsInSentences() {
        String str1 = "Java is fun";
        String str2 = "Python is fun";

        // Solution 1
        List<String> commonWords = new ArrayList<>();
        for (String string : str1.split("\\s+")) {
            if (str2.indexOf(string) != -1) {
                commonWords.add(string);
            }
        }
        System.out.println(commonWords);

        // Solution 2
        HashSet<String> hashSet = new HashSet<>(Arrays.asList(str1.split("\\s+")));
        hashSet.retainAll(Arrays.asList(str2.split("\\s+")));
        System.out.println(hashSet);

    }

    /*
     * 5️⃣ Implement a Functional Interface for Exponentiation
     * 📌 Question: Implement an interface to calculate power using lambdas.
     * 🔹 Input: power(2, 3)
     * 🔹 Output: 8
     */
    @FunctionalInterface
    public interface Power {
        int apply(int num1, int pow);
    }

    @Test
    public void powerOfnUmber() {
        Power power = (num, pow) -> IntStream.range(0, pow).reduce(1, (res, i) -> res * num);
        System.out.println(power.apply(2, 3));
    }

    /*
     * 1️⃣ Extract Value from Response Headers in Rest Assured
     * 📌 Question: How do you extract and validate a specific header value from an
     * API response?
     */
    @Test
    public void validateResponseHeader() {
        RestAssured.get("https://dummyjson.com/recipes/meal-type/snack").then()
                .header("Content-Type", containsString(ContentType.JSON.toString()));
    }

    /*
     * 2️⃣ Handle File Upload in Selenium
     * 📌 Question: How do you automate file uploads in Selenium without using a
     * third-party tool?
     */
    @Test
    public void fileUpload() {
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized"));
        driver.get("https://filebin.net/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.findElement(By.cssSelector("input[type='file']"))
                .sendKeys(new File("./src/main/resources/json_schema.json").getAbsolutePath());
        driver.findElement(By.xpath("//*[text()='json_schema.json']"));
        driver.quit();
    }

    /*
     * 3️⃣ Validate API Response Contains Dynamic Values
     * 📌 Question: How do you validate if an API response contains dynamically
     * changing values?
     */
    @Test
    public void validateDynamicValues() {
        Random random = new Random();
        int skip = random.nextInt(30);
        int limit = random.nextInt(5, 30);
        RestAssured.given()
                .queryParam("skip", skip)
                .queryParam("limit", limit)
                .get("https://dummyjson.com/posts").then()
                .body("limit", equalTo(limit))
                .body("skip", equalTo(skip))
                .body("posts.size()", equalTo(limit)).log().all();

    }

    /*
     * 4️⃣ Handle Authentication Popups in Selenium
     * 📌 Question: How do you handle basic authentication popups in Selenium?
     */
    @Test
    public void handlebasicAuth() {
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized"));

        // Solution 1
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

        String encodedString = Base64.getEncoder().encodeToString("admin:admin".getBytes());
        HashMap<String, Object> headersMap = new HashMap<>();
        headersMap.put("Authorization", "Basic " + encodedString);

        // Solution 2
        HashMap<String, Object> params = new HashMap<>();
        params.put("headers", headersMap);
        ((HasCdp) driver).executeCdpCommand("Network.enable", new HashMap<>());
        ((HasCdp) driver).executeCdpCommand("Network.setExtraHTTPHeaders", params);

        // Solution 3
        // Step 1 - Get Dev Tools
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        // Step 2 - Create Session
        devTools.createSession();
        // Step 3 - Enable Network Tracking
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        // step 4 - Set Extra HTTP headers
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headersMap)));
        driver.get("http://the-internet.herokuapp.com/basic_auth");
        driver.findElement(By.xpath("//h3[text()='Basic Auth']"));
        driver.quit();

    }

    /*
     * 5️⃣ Validate Response Structure Using JSON Schema in Rest Assured
     * 📌 Question: How do you ensure an API response structure matches a predefined
     * JSON schema?
     */
    @Test
    public void validateResponseSchema() {
        RestAssured.given().when()
                .get("https://jsonplaceholder.typicode.com/users/1").then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("./src/main/resources/json_schema.json")));
    }
}
