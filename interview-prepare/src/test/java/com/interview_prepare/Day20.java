package com.interview_prepare;

import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class Day20 {

    /*
     * 1️⃣ Find the Subarray with Maximum Sum (Kadane’s Algorithm)
     * 📌 Question: Given an array, find the contiguous subarray with the largest
     * sum.
     * 🔹 Input: { -2, 1, -3, 4, -1, 2, 1, -5, 4 }
     * 🔹 Output: 6 (subarray: [4, -1, 2, 1])
     */
    public void findSubArrayWithMaxSum() {
        int[] array = new int[] { -2, 1, -3, 4, -1, 2, 1, -5, -6, 3, 7 };
        int start = 0, tempStart = 0, maxSum = array[0], currentSum = array[0], end = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > currentSum + array[i]) {
                currentSum = array[i];
                tempStart = i;
            } else {
                currentSum += array[i];
            }

            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
        }
        System.out.println(maxSum);
        System.out.println(Arrays.toString(Arrays.copyOfRange(array, start, end + 1)));
    }

    /*
     * 2️⃣ Find the First Missing Positive Integer
     * 📌 Question: Given an unsorted array, find the smallest missing positive
     * integer.
     * 🔹 Input: {3, 4, -1, 1}
     * 🔹 Output: 2
     */
    @Test(dataProvider = "InputArrays")
    public void findFirstMissingPostiveInteger(int[] array, int expected) {
        for (int i = 0; i < array.length; i++) {
            while (array[i] > 0 && array[i] <= array.length && array[array[i] - 1] != array[i]) {
                int temp = array[array[i] - 1];
                array[array[i] - 1] = array[i];
                array[i] = temp;
            }
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] != i + 1) {
                Assert.assertEquals(i + 1, expected);
                return;
            }
        }

        // If all numbers 1..n are in place, return n + 1
        Assert.assertEquals(array.length + 1, expected);
    }

    @DataProvider(name = "InputArrays")
    public Object[][] inputArrays() {
        return new Object[][] {
                { new int[] { 3, 4, -1, 1 }, 2 },
                { new int[] { 1, 3, 4, 0 }, 2 },
                { new int[] { 7, 8, 9, 12, 17 }, 1 },
                { new int[] { 1, 2, 3, 4 }, 5 }
        };
    }

    /*
     * 3️⃣ Sort a List of Employees by Salary and Name
     * 📌 Question: Sort a list of employees first by salary (ascending) and then by
     * name (alphabetically).
     * 🔹 Input: {[Alice, 5000], [Bob, 7000], [John, 5000]}
     * 🔹 Output: {[Alice, 5000], [John, 5000], [Bob, 7000]}
     */
    @Test
    public void sortListOfEmployeesBySalarayAndName() {
        List<Employee> employeeList = List.of(
                new Employee("Alice", 5000),
                new Employee("Bob", 7000),
                new Employee("John", 5000));

        employeeList = employeeList.stream().sorted(
                Comparator.comparing(Employee::getSalary).thenComparing(Employee::getName)).toList();
        System.out.println(employeeList);
    }

    /*
     * 1️⃣ What Is the Difference Between API Security Testing and Penetration
     * Testing?
     * 📌 Question: How does API security testing differ from penetration testing,
     * and why is each important?
     * 
     * Got it! Here’s the **difference between API Security Testing and Penetration
     * Testing** as a simple list:
     * 
     * ---
     * 
     * ### Differences Between API Security Testing and Penetration Testing
     * 
     * 1. **Scope:**
     * 
     * API Security Testing: Focuses only on APIs.
     * Penetration Testing: Covers entire systems, including APIs, networks,
     * applications.
     * 
     * 2. **Purpose:**
     * 
     * API Security Testing: Finds vulnerabilities specific to APIs like
     * authentication, input validation.
     * Penetration Testing: Simulates real attacker behavior to find exploitable
     * flaws.
     * 
     * 3. **Techniques:**
     * 
     * API Security Testing: Uses automated tools and manual API-specific checks.
     * Penetration Testing: Uses a mix of automated tools and manual hacking
     * techniques.
     * 
     * 4. **Frequency:**
     * 
     * API Security Testing: Often continuous, integrated into development cycles.
     * Penetration Testing: Usually periodic or scheduled engagements.
     * 
     * 5. **Outcome:**
     * 
     * API Security Testing: Identifies API vulnerabilities for developers.
     * Penetration Testing: Provides comprehensive risk and security posture
     * assessment.
     * 
     * 6. **Tools:**
     * 
     * API Security Testing: Postman, OWASP ZAP, SoapUI.
     * Penetration Testing: Metasploit, Burp Suite, Nessus.
     * 
     * 7. **Focus on Attack Vectors:**
     * 
     * API Security Testing: Focuses on API endpoints and data flows.
     * Penetration Testing: Focuses on broader attack vectors (network, app, social
     * engineering).
     * 
     * ✅ Answer:
     * API Security Testing: Ensures that APIs are protected against unauthorized
     * access, injection attacks, and data exposure.
     * Penetration Testing: Simulates real-world attacks to identify vulnerabilities
     * in the system.
     * Example:
     * 🔹 Security Testing: Validating token-based authentication
     * 🔹 Penetration Testing: Testing SQL injection via API endpoints
     * 🎯 Key Takeaway: Security testing ensures compliance, while penetration
     * testing identifies real vulnerabilities.
     */

    /*
     * 2️⃣ Validate XML Response Contains a Specific Node Using Rest Assured
     * 📌 Question: How do you check if an API’s XML response contains a specific
     * node value?
     */
    @Test
    public void validateXMLResponseNodeValue() {
        Response response = RestAssured.given()
                .header("Accept", "application/xml")
                .header("User-Agent", "Thunder Client (https://www.thunderclient.com)")
                .get("https://petstore.swagger.io/v2/pet/findByStatus?status=available")
                .then().extract().response();
        XmlPath xmlPath = new XmlPath(response.asString()
                .replaceAll("[^\\x09\\x0A\\x0D\\x20-\\xFFFD]", ""));
        System.out.println(xmlPath.getString("pets.Pet.find {it.category =='Dogs'}.name"));
    }

    /*
     * 3️⃣ Handle StaleElementReferenceException in Selenium
     * 📌 Question: How do you resolve StaleElementReferenceException in Selenium
     * WebDriver?
     */
    @Test
    public void StaleElementReferenceException() {
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
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")))
                    .sendKeys("From Exception");
        }
        driver.quit();
    }

    /*
     * 4️⃣ What Is an API Payload and How Is It Used?
     * 📌 Question: What is an API payload, and how does it differ from request
     * parameters?
     * 
     * ✅ Answer:
     * API Payload: The actual data sent in an API request body (e.g., JSON, XML).
     * 
     * What Is an API Payload?
     * The API payload refers to the data sent in the body of an API request.
     *
     * It is commonly used with POST, PUT, or PATCH requests to send structured data
     * (like JSON or XML) to the server.
     * 
     * Think of the payload as the main content you are sending to the server to
     * create or update a resource.
     * 
     * 
     * 
     * Request Parameters: Values sent in the URL query string or headers.
     * Request parameters are key-value pairs sent as part of the URL or headers,
     * often used with GET requests to filter or identify resources.
     * 🎯 Key Takeaway: Payloads contain structured request data, while parameters
     * modify request behavior.
     */

    /*
     * 5️⃣ Handle Checkbox Selection in Selenium
     * 📌 Question: How do you check and uncheck a checkbox dynamically in Selenium?
     */
    @Test
    public void handleCheckBoxes() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        Assert.assertFalse(driver.findElement(By.cssSelector("[type='checkbox']")).isSelected());
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("[type='checkbox']"));
        Assert.assertTrue(checkboxes.get(1).isSelected());
        for (WebElement checkbox : checkboxes) {
            checkbox.click();
        }
        Assert.assertTrue(checkboxes.get(0).isSelected());
        Assert.assertFalse(checkboxes.get(1).isSelected());
        driver.quit();
    }
}
