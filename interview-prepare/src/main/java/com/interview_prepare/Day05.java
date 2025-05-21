package com.interview_prepare;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;

import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Day05 {

    /*
     * 1️⃣ First Repeating Character
     * 📌 Question: Find the first repeating character in a string.
     * 🔹 Input: "swiss"
     * 🔹 Output: 's'
     */
    @Test
    public void findFirstRepeatingCharacter() {
        HashSet<Character> hashSet = new HashSet<>();
        String str = "JPvc Progarmming";
        char fistRepeatingChar = '_';
        for (Character character : str.toCharArray()) {
            if (!hashSet.add(character)) {
                fistRepeatingChar = character;
                break;
            }
        }
        System.out.println(fistRepeatingChar);
    }

    /*
     * 2️⃣ Rotate Array by K Positions
     * 📌 Question: Rotate an array to the right by k positions.
     * 🔹 Input: {1, 2, 3, 4, 5}, k=2
     * 🔹 Output: {4, 5, 1, 2, 3}
     * 
     * 1 2 3 4 5
     * k=1 => 5 1 2 3 4 => 0 4, 1 0, 2 1, 3 2, 4 3
     * k=2 => 4 5 1 2 3 => 0 3, 1 4, 2 0, 3 1, 4 2
     * k=3 => 3 4 5 1 2 => 0 2, 1 3, 2 4, 3 0, 4 1
     * 
     */
    public void reverse(int[] arr, int start, int limit) {
        int temp = 0;
        while (start < limit) {
            temp = arr[start];
            arr[start++] = arr[limit];
            arr[limit--] = temp;
        }
    }

    @Test
    public void rotateArrayByKPositions() {
        int k = 6;
        int[] arr = new int[] { 1, 2, 3, 4, 5, 10, 20, 1 };
        int length = arr.length;
        k %= length;

        // Solution 1
        for (int i = 0; i < k; i++) {
            int index = length - 1;
            int temp = arr[index];
            for (int j = length - 2; j >= 0; j--) {
                arr[index--] = arr[j];
            }
            arr[index] = temp;
        }
        System.out.println("Solution 1: " + Arrays.toString(arr));

        // Solution 2
        arr = new int[] { 1, 2, 3, 4, 5, 10, 20, 1 };
        reverse(arr, 0, length - 1);
        reverse(arr, 0, k - 1);
        reverse(arr, k, length - 1);
        System.out.println("Solution 2: " + Arrays.toString(arr));
    }

    /*
     * 3️⃣ Kth Largest Element in Array
     * 📌 Question: Return the kth largest element in an unsorted array.
     * 🔹 Input: {3, 2, 1, 5, 6, 4}, k=2
     * 🔹 Output: 5
     */
    @Test
    public void largestElementInArray() {
        int[] arr = new int[] { 1, 2, 3, 4, 5, 10, 20, 1 };
        int k = 4;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : arr) {
            minHeap.add(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        System.out.println(minHeap.peek());
    }

    /*
     * 4️⃣ Majority Element in Array
     * 📌 Question: Find the element appearing more than n/2 times.
     * 🔹 Input: {3, 3, 4, 2, 3, 3, 3}
     * 🔹 Output: 3
     */
    @Test
    public void majorityELementInArray() {
        int[] arr = new int[] { 1, 2, 3, 2, 1, 2, 2, 2, 8, 2, 9, 2 };
        int candidate = 0;
        int count = 0;
        for (int num : arr) {
            if (count == 0)
                candidate = num;
            count += (num == candidate) ? 1 : -1;
        }
        int ele = candidate;
        long frequency = Arrays.stream(arr)
                .filter(n -> n == ele)
                .count();

        if (frequency > arr.length / 2) {
            System.out.println("Majority element: " + candidate);
        } else {
            System.out.println("No majority element found.");
        }
    }

    /*
     * 5️⃣ Sort Employees by Salary Using Comparator
     * 📌 Question: Sort employees by salary.
     * 🔹 Input: {[John, 5000], [Doe, 7000], [Anna, 6000]}
     * 🔹 Output: {[John, 5000], [Anna, 6000], [Doe, 7000]}
     */
    class Employee {
        String name;
        int salary;

        public Employee(String name, int salary) {
            this.name = name;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "[ " + name + ", " + salary + "]";
        }
    }

    @Test
    public void sortEmployeesBySalray() {
        List<Employee> employees = Arrays.asList(
                new Employee("John", 5000),
                new Employee("Doe", 7000),
                new Employee("Anna", 6000));
        employees.sort((e1, e2) -> e1.salary - e2.salary);
        System.out.println(employees);
        employees.sort(Comparator.comparingInt(e -> e.salary));
        System.out.println(employees);
    }

    /*
     * 1️⃣ Validate Status Code and Response Body in Rest Assured
     * 📌 Question: How can you validate both the status code and a specific value
     * in the response body using Rest Assured?
     */
    @Test
    public void testStatusCodeAndResponseBody() {
        RestAssured.given().get("https://dummyjson.com/posts").then()
                .statusCode(200).body("posts[0].title", equalTo("His mother had always taught him"))
                .body("posts[0].tags.size()", greaterThanOrEqualTo(3))
                .body("posts[0].reactions", allOf(hasKey("likes"), hasKey("dislikes")));

    }

    /*
     * 2️⃣ Handle Dynamic Dropdowns in Selenium
     * 📌 Question: How do you handle a dynamic dropdown where values are loaded
     * asynchronously in Selenium?
     */
    @Test
    public void handleDynamicDropDown() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.spicejet.com/");
        driver.findElement(By.cssSelector("input[value=\'Select Destination']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[text()='Select a region and city below']")));
        String locator = "//*[text()='Cities']/parent::*/following-sibling::*/descendant::*";
        List<String> destinations = driver.findElements(
                By.xpath(locator + "[text()!='']"))
                .stream().map(WebElement::getText).toList();
        System.out.println("destinations: " + destinations);
        driver.findElement(By.xpath(locator + "[text()='Chennai']")).click();
        driver.quit();
    }

    /*
     * 3️⃣ Validate XML Response in Rest Assured
     * 📌 Question: How can you validate specific values inside an XML response
     * using Rest Assured?
     */
    @Test
    public void validateXMLResponse() {
        RestAssured.given()
                .header("Accept", "application/xml")
                .get("https://petstore.swagger.io/v2/pet/findByStatus?status=available")
                .then().body("pets.Pet.name", hasItem("Kaalia"));
    }

    /*
     * 4️⃣ Switch Between Multiple Browser Tabs in Selenium
     * 📌 Question: How do you switch between multiple browser tabs using Selenium?
     */
    @Test
    public void switchMultipleTabs() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/windows");
        System.out.println("Current handle " + driver.getWindowHandle());
        driver.findElement(By.linkText("Elemental Selenium")).click();
        System.out.println("Current handle " + driver.getWindowHandle());
        System.out.println("Windows Handles " + driver.getWindowHandles());
        driver.findElement(By.linkText("Click Here")).click();
        System.out.println("Current handle " + driver.getWindowHandle());
        System.out.println("Windows Handles " + driver.getWindowHandles());
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getTitle().contains("Elemental Selenium")) {
                break;
            }
        }
        System.out.println("Title: " + driver.getTitle());
        driver.quit();

    }
    /*
     * 5️⃣ Validate Response Time in Rest Assured
     * 📌 Question: How do you verify that an API responds within a specific time
     * threshold?
     */
    @Test
    public void testRestAssuredTimeThreshold(){
         RestAssured.given().get("https://reqres.in/users").then()
                .time(lessThan(3000L));
    }
}
