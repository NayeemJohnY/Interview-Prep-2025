package days.series;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.io.Files;

import io.restassured.RestAssured;
import io.restassured.response.Response;

class MyQueue<T> {

    Stack<T> stack1;
    Stack<T> stack2;

    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void enqueue(T e) {
        stack1.add(e);
    }

    public T dequeue() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.add(stack1.pop());
            }
        }
        return stack2.pop();
    }
}

public class Day04 {

    /*
     * 1️⃣ Check if Two Strings are Anagrams
     * 📌 Question: Check if two strings are anagrams.
     * 🔹 Input: "listen", "silent"
     * 🔹 Output: true
     */
    public boolean isAnagramStrings(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;

        // Solution 1
        boolean validAllThree = true;
        HashSet<Character> charSet = new HashSet<>();
        for (Character character : s1.toCharArray()) {
            charSet.add(character);
        }
        for (Character character : s2.toCharArray()) {
            charSet.remove(character);
        }
        validAllThree &= charSet.isEmpty();

        // Solution 2
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        validAllThree &= Arrays.equals(arr1, arr2);

        // Solution 3
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (Character character : s1.toCharArray()) {
            hashMap.put(character, hashMap.getOrDefault(character, 0) + 1);
        }
        for (Character character : s2.toCharArray()) {
            if (!hashMap.containsKey(character)) {
                validAllThree &= false;
                break;
            }
            hashMap.put(character, hashMap.getOrDefault(character, 0) - 1);
            if (hashMap.get(character) == 0) {
                hashMap.remove(character);
            }
        }
        return validAllThree && hashMap.isEmpty();

    }

    @Test
    public void testAnagramString() {
        Assert.assertTrue(isAnagramStrings("slient", "listen"));
        Assert.assertFalse(isAnagramStrings("slient", "litten"));
        Assert.assertFalse(isAnagramStrings("ssted", "litten"));
    }

    /*
     * 2️⃣ Move Zeros to End of Array
     * 📌 Question: Move all zeros in an array to the end.
     * 🔹 Input: {0, 1, 0, 3, 12}
     * 🔹 Output: {1, 3, 12, 0, 0}
     */
    public int[] moveZerostoEndOfArray(int... arr) {
        int index = 0;
        for (int num : arr) {
            if (num != 0) {
                arr[index++] = num;
            }
        }
        while (index < arr.length) {
            arr[index++] = 0;
        }
        System.out.println(Arrays.toString(arr));
        return arr;
    }

    @Test
    public void testMoveZerostoEndOfArray() {
        Assert.assertTrue(Arrays.equals(moveZerostoEndOfArray(0, 1, 0, 3, 12), new int[] { 1, 3, 12, 0, 0 }));
        Assert.assertTrue(Arrays.equals(moveZerostoEndOfArray(1, 1, 0, 0, 0), new int[] { 1, 1, 0, 0, 0 }));
        Assert.assertFalse(Arrays.equals(moveZerostoEndOfArray(1, 1, 0, 0, 10), new int[] { 1, 1, 0, 0, 10 }));
    }

    /*
     * 3️⃣ Find Intersection of Two Arrays
     * 📌 Question: Return common elements from two arrays.
     * 🔹 Input: {1, 2, 2, 3}, {2, 2, 4}
     * 🔹 Output: {2, 2}
     */
    public List<Integer> findCommonElementsIntersection(int[] arr1, int[] arr2) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        for (int i : arr1) {
            hashMap.put(i, hashMap.getOrDefault(i, 0) + 1);
        }
        for (int i : arr2) {
            if (hashMap.getOrDefault(i, 0) > 0) {
                result.add(i);
                hashMap.put(i, hashMap.getOrDefault(i, 0) - 1);
            }
        }
        return result;
    }

    @Test
    public void testFindCommonElementsIntersection() {
        Assert.assertEquals(findCommonElementsIntersection(
                new int[] { 1, 2, 2, 3 }, new int[] { 2, 2, 4 }), Arrays.asList(2, 2));
        Assert.assertEquals(findCommonElementsIntersection(
                new int[] { 1, 2, 3, 4 }, new int[] { 2, 1, 4 }), Arrays.asList(2, 1, 4));
    }

    /*
     * 4️⃣ Implement Queue Using Stacks
     * 📌 Question: Implement a queue using two stacks.
     * 🔹 Operations: enqueue(10), enqueue(20), dequeue() → 10
     */
    @Test
    public void testQueueUsingStacks() {
        MyQueue<String> myQueue = new MyQueue<>();
        myQueue.enqueue("10");
        myQueue.enqueue("20");
        myQueue.enqueue("30");
        Assert.assertEquals(myQueue.dequeue(), "10");
        myQueue.enqueue("10");
        Assert.assertEquals(myQueue.dequeue(), "20");
    }

    /*
     * 5️⃣ Count Character Occurrences Using Functional Interface
     * 📌 Question: Count occurrences of a character in a string using a lambda
     * function.
     * 🔹 Input: "automation", 'a'
     * 🔹 Output: 2
     */
    @FunctionalInterface
    interface CharCounter {
        int count(String s, char c);
    }

    @Test
    public void testCountCharactersUsingFunctionalInterface() {
        String str = "Learn Java Programming";
        // Map<Character, Long> freqMap = str.chars().mapToObj(ch -> (char) ch)
        // .collect(Collectors.groupingBy(ch->ch, Collectors.counting()));
        // System.out.println(freqMap);

        CharCounter charCounter = (s, c) -> (int) s.chars().filter(ch -> ch == c).count();
        Assert.assertEquals(charCounter.count(str, '1'), 0);
        Assert.assertEquals(charCounter.count(str, 'a'), 4);
    }

    /*
     * 1️⃣ Retry Failed API Requests in Rest Assured
     * 📌 Question: How can you retry an API request if it fails due to transient
     * errors (e.g., 500 status code)?
     */
    public static Response retryRequest(
            Supplier<Response> request, Predicate<Response> shouldRetry, int maxRetries, int delayInMills) {
        int attempts = 0;
        Response response;
        do {
            response = request.get();
            if (!shouldRetry.test(response))
                return response;
            attempts++;
            System.out.println("Retry Attempt : " + attempts);
            if (attempts < maxRetries) {
                try {
                    Thread.sleep(delayInMills);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } while (attempts < maxRetries);

        return response;
    }

    @Test
    public void testRetryRequest() {
        Supplier<Response> request = () -> RestAssured
                .given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/1");

        Predicate<Response> shouldRetry = res -> {
            res.then().log().all();
            String username = res.jsonPath().getString("username");
            return username.equals("Bret");
        };
        Response response = retryRequest(request, shouldRetry, 3, 5000);
        response.then().statusCode(200);
    }

    /*
     * 2️⃣ Capture Screenshot on Selenium Test Failure
     * 📌 Question: How do you take a screenshot when a Selenium test fails?
     */
    @Test
    public void testTakeScreenshotOnTestFailure() throws IOException {
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
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            Files.copy(srcFile, new File("scrrenshot.png"));
        }
        driver.quit();
    }

    /*
     * 3️⃣ Extract Nested JSON Data in Rest Assured
     * 📌 Question: How do you extract nested JSON values from an API response?
     * ➡ Example Response:
     */
    @Test
    public void testNestedJSONData() {
        Response response = RestAssured.given()
                .get("https://dummyjson.com/posts");
        Assert.assertEquals(response.jsonPath().getString("posts[0].userId"), "121");

        RestAssured.given()
                .get("https://dummyjson.com/posts").then().body("posts[0].tags", hasItem("history"))
                .body("posts[1].reactions", hasKey("likes"));
    }

    /*
     * 4️⃣ Handle iFrames in Selenium
     * 📌 Question: How do you interact with elements inside an iframe using
     * Selenium?
     */
    @Test
    public void testHandleIframes() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://letcode.in/frame");
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt("firstFr"));
        By emailInput = By.name("email");
        try {
            driver.findElement(emailInput).sendKeys("test@test.com");
        } catch (NoSuchElementException e) {
            System.out.println("Element in Inner Frame: " + e.getRawMessage());
            driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[src='innerframe']")));
            driver.findElement(emailInput).sendKeys("test@test.com");
        }
        By fnameInput = By.cssSelector("input[placeholder='Enter name']");
        try {
            driver.findElement(fnameInput).sendKeys("First Name");
        } catch (NoSuchElementException e) {
            System.out.println("Element in Parent Frame: " + e.getRawMessage());
            driver.switchTo().parentFrame();
            driver.findElement(fnameInput).sendKeys("First Name");
        }

        By videoLinkText = By.linkText("Watch tutorial");
        try {
            driver.findElement(videoLinkText).click();
        } catch (NoSuchElementException e) {
            System.out.println("Element in Default Content: " + e.getRawMessage());
            driver.switchTo().defaultContent();
            driver.findElement(videoLinkText).click();
        }
    }

    /*
     * 5️⃣ Validate JSON Response Array in Rest Assured
     * 📌 Question: How do you validate an array of JSON responses in Rest Assured?
     */
    @Test
    public void validateJSONArrayRestAssurred() {
        RestAssured.get("https://dummyjson.com/comments")
                .then().body("comments.size()", equalTo(30));

        RestAssured.get("https://dummyjson.com/posts")
                .then().body("posts.tags.flatten()", anyOf(
                        hasItem("history"), hasItem("french"), hasItem("magical"), hasItem("love"),
                        hasItem("mistery"), hasItem("american"), hasItem("crime"), hasItem("fiction"),
                        hasItem("classic"), hasItem("english")));
    }
}
