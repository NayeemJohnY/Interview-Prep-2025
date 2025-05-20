package com.interview_prepare;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v136.network.Network;
import org.openqa.selenium.devtools.v136.network.model.Request;
import org.openqa.selenium.devtools.v136.network.model.ResourceType;
import org.openqa.selenium.devtools.v136.network.model.Response;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

class MyStack<T> {

    Queue<T> queue;

    public MyStack() {
        queue = new LinkedList<>();
    }

    public void push(T e) {
        queue.add(e);
        for (int i = 0; i < queue.size() - 1; i++)
            queue.add(queue.poll());
    }

    public T pop() {
        return queue.poll();
    }
}

public class Day03 {

    public void findLongestCommonPrefix(String... words) {
        // Solution 1 - Legacy
        StringBuilder prefixBuilder = new StringBuilder();
        int index = 0;
        for (Character ch : words[0].toCharArray()) {
            boolean IscharMatch = true;
            for (String string : words) {
                if (index >= string.length() || string.charAt(index) != ch) {
                    IscharMatch = false;
                    break;
                }
            }
            if (IscharMatch) {
                prefixBuilder.append(ch);
                index++;
            } else {
                break;
            }
        }
        System.out.println("LCP: " + prefixBuilder.toString());

        // Solution 2 - Using Substring
        String prefix = words[0];
        for (String word : words) {
            while (!word.startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return;
                }
            }
        }
        System.out.println("Using Substring: " + prefix);
    }

    public void removeDuplicatesFromSortedArray(int[] arr) {
        int index = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1]) {
                arr[index++] = arr[i];
            }
        }
        System.out.println(Arrays.toString(Arrays.copyOf(arr, index)));
    }

    public void findMissingNumber(int n, int... arr) {
        int missing = (n * (n + 1) / 2) - IntStream.of(arr).sum();
        if (missing != 0)
            System.out.println("Missing Number: " + missing);
        else
            System.out.println("No Number is missig");
    }

    public void sortArrayByStringLength(String... arr) {
        // Solution 1
        List<String> strList = Stream.of(arr).sorted((s1, s2) -> s1.length() - s2.length()).toList();
        System.out.println("Sorted: " + strList);

        // Solution 2
        Arrays.sort(arr, (s1, s2) -> s1.length() - s2.length());
        System.out.println("Sorted using comparator: " + Arrays.toString(arr));
        Arrays.sort(arr, Comparator.comparingInt(String::length));
        System.out.println("Sorted using comparator: " + Arrays.toString(arr));
    }

    /*
     * 1️⃣ Find Longest Common Prefix
     * 📌 Question: Given an array of strings, find the longest common prefix.
     * 🔹 Input: {"automation", "auto", "autonomous"}
     * 🔹 Output: "auto"
     */
    @Test
    public void testFindLocngestCommonPrefix() {
        findLongestCommonPrefix("automation", "auto", "autonomous");
        findLongestCommonPrefix("sadasd", "rreee", "ssssa");
    }

    /*
     * 2️⃣ Remove Duplicates from Array
     * 📌 Question: Given a sorted array, remove duplicates and return the new
     * length.
     * 🔹 Input: {1, 1, 2, 3, 3, 4}
     * 🔹 Output: {1, 2, 3, 4}
     */
    @Test
    public void tesRemoveDuplicateInArray() {
        removeDuplicatesFromSortedArray(new int[] { 1, 2, 2, 3, 3, 4, 5, 6, 6, 7, 7, 7 });
    }

    /*
     * 3️⃣ Find Missing Number in Array
     * 📌 Question: Given an array from 1 to n (with one missing), find the missing
     * number.
     * 🔹 Input: {1, 2, 4, 5}
     * 🔹 Output: 3
     */
    @Test
    public void testFindMissingNumber() {
        findMissingNumber(5, 1, 2, 4, 5);
        findMissingNumber(10, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        findMissingNumber(10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    /*
     * 4️⃣ Sort Array of Strings by Length
     * 📌 Question: Given an array of strings, sort them by length.
     * 🔹 Input: {"apple", "kiwi", "banana"}
     * 🔹 Output: {"kiwi", "apple", "banana"}
     */
    @Test
    public void testSortArrayByStringLength() {
        sortArrayByStringLength("apple", "kiwi", "banana");
    }

    /*
     * 5️⃣ Implement Stack Using Queue
     * 📌 Question: Implement a stack using a queue.
     * 🔹 Operations: push(10), push(20), pop() → 20
     */
    @Test
    public void testMyStackUsingQueue() {
        MyStack<String> myStack = new MyStack<>();
        myStack.push("10");
        myStack.push("20");
        myStack.push("30");
        System.out.println(myStack.pop());
    }

    /*
     * 1️⃣ Validate Response Headers in Rest Assured
     * 📌 Question: How do you verify specific response headers in an API response?
     */
    public void validateResponseHeaders(String headerName, String headerValue) {
        RestAssured.given().get("https://jsonplaceholder.typicode.com/users/1").then()
                .assertThat().header(headerName, headerValue);
    }

    @Test
    public void testValidateResponseHeaders() {
        validateResponseHeaders("Content-Type", "application/json; charset=utf-8");
        validateResponseHeaders("Content-Type", "application/xml");
    }

    /*
     * 2️⃣ Capture Network Logs in Selenium WebDriver
     * 📌 Question: How can you capture network logs (API calls) during a Selenium
     * test?
     */
    @Test
    public void testCaptureNetworkLogs() throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        // Step 1 - Get Dev Tools
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        // Step 2 - Create Session
        devTools.createSession();
        // Step 3 - Enable Network Tracking
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        BufferedWriter writer = new BufferedWriter(new FileWriter("network-log.csv"));
        writer.write("URL,Status Code,Response time\n");
        // Step 4 - Request Listener
        devTools.addListener(Network.requestWillBeSent(), request -> {
            if (request.getType().equals(ResourceType.XHR)) {
                Request req = request.getRequest();
                System.out.println("Request URL " + req.getUrl());
            }
        });
        // Step 5 - Response Listener
        devTools.addListener(Network.responseReceived(), response -> {
            if (response.getType().equals(ResourceType.XHR)) {
                Response res = response.getResponse();
                System.out.println("Response URL " + res.getUrl());
                System.out.println("Response Status " + res.getStatus());
                try {
                    writer.write(String.format(
                            "\"%s\",%s,\"%s\"%n", res.getUrl(), res.getStatus(), res.getResponseTime().get()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        driver.get("https://google.com");
        driver.get("https://amazon.in");
        Thread.sleep(30000);
        writer.flush();
        writer.close();
        driver.quit();
    }

    /*
     * 3️⃣ Perform OAuth 2.0 Authentication in Rest Assured
     * 📌 Question: How do you authenticate API requests using OAuth 2.0 in Rest
     * Assured?
     */
    @Test
    public void testOAuthRestAssurred() {
        String clientId = "0oaosystbx88ivsbX5d7";
        String clientSecret = "mzsj4wv_oHJob3risVjVfdLXeEGkDU7kfq5uy-VbzluXnY7OxqhRsHcrTiJ9dgwt";
        io.restassured.response.Response tokenResponse = RestAssured.given()
                .auth()
                .preemptive()
                .basic(clientId, clientSecret)
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "dummy.read")
                .when()
                .post("https://dev-86087318.okta.com/oauth2/default/v1/token");

        System.out.println(tokenResponse.asPrettyString());
        String accessToken = tokenResponse.jsonPath().getString("access_token");

        io.restassured.response.Response userResponse = RestAssured.given()
                .auth()
                .oauth2(accessToken)
                .accept(ContentType.JSON)
                .when()
                .get("http://localhost:3000/dummy/users");

        System.out.println(userResponse.asPrettyString());

    }

    /*
     * 4️⃣ Handle Stale Element Exceptions in Selenium
     * 📌 Question: How do you handle StaleElementReferenceException in Selenium?
     * 1. Avoid Reference store and relocate everytime
     * 2. Try catch and Expilcit Wait with Conditions
     */
    @Test
    public void testHandleStaleElementReferenceException() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://login.microsoftonline.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        try {
            WebElement emailInput = driver.findElement(By.cssSelector("input[type='email']"));
            emailInput.sendKeys("Test SDET");
            driver.findElement(By.cssSelector("div[data-test-id='signinOptions']")).click();
            driver.findElement(By.cssSelector("input[value='Back']")).click();
            emailInput.sendKeys("After Enter");
        } catch (StaleElementReferenceException e) {
            System.out.println(e.getRawMessage());
            driver.findElement(By.cssSelector("input[type='email']")).sendKeys("From Exception");
        }
        driver.quit();
    }

    /*
     * 5️⃣ Validate Response Body with Multiple Fields in Rest Assured
     * 📌 Question: How do you validate multiple fields in a JSON response?
     *
     */
    @Test
    public void testValidateMultipleResponseBodyFields() {
        RestAssured.given().get("https://jsonplaceholder.typicode.com/users/1").then()
                .body("id", equalTo(1))
                .body("email", containsString("@april.biz"))
                .body("company", hasEntry("name", "Romaguera-Crona"));
    }
}
