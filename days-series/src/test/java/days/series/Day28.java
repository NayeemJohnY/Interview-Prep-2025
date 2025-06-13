package days.series;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;

public class Day28 {

    /*
     * 1️⃣ Detect Users with Consecutive Login Failures
     * 📌 Scenario: Your system logs failed login attempts as "username - status".
     * Find users who had 3 consecutive "FAIL" entries.
     * 🔹 Input:
     * ["alice - FAIL", "alice - FAIL", "bob - FAIL", "alice - FAIL", "bob - PASS"]
     * 🔹 Output:
     * ["alice"]
     */
    @Test
    public void detectUserWithConseutiveLoginFailures() {
        List<String> loginLogs = List.of(
                "alice - FAIL", "alice - FAIL", "bob - FAIL", "alice - FAIL", "bob - PASS",
                "tom - FAIL", "tomy - PASS", "tom - FAIL", "bob - FAIL", "tom - FAIL");
        HashMap<String, Integer> consectiveFailureAttempts = new HashMap<>();
        for (String log : loginLogs) {
            String[] nameStatus = log.split(" - ");
            if (nameStatus[1].equals("FAIL")) {
                consectiveFailureAttempts.put(nameStatus[0],
                        consectiveFailureAttempts.getOrDefault(nameStatus[0], 0) + 1);
            } else {
                consectiveFailureAttempts.put(nameStatus[0], 0);
            }
        }
        System.out.println(consectiveFailureAttempts.entrySet().stream().filter(entry -> entry.getValue() == 3)
                .map(Map.Entry::getKey).toList());
    }

    /*
     * 2️⃣ Shorten Large Error Messages for UI Display
     * 📌 Scenario: Your UI should show only the first 50 characters of an error
     * message, followed by "..." if it’s too long.
     * 🔹 Input:
     * "java.lang.NullPointerException at com.project.module.ClassA.line23()"
     * 🔹 Output:
     * "java.lang.NullPointerException at com.project.mod..."
     */
    @Test
    public void shortenErrorMessage() {
        String str = "java.lang.NullPointerException at com.project.module.ClassA.line23()";
        System.out.println(str.length() > 50 ? str.substring(0, 50) + "..." : str);
    }

    /*
     * 3️⃣ Group Usernames by First Letter
     * 📌 Scenario: Your application groups users alphabetically for display. Return
     * a map where each key is the first letter and the value is a list of usernames
     * starting with that letter.
     * 🔹 Input:
     * []
     * 🔹 Output:
     * { A=[alice, adam], B=[bob, bruce], C=[carol] }
     */
    @Test
    public void groupUserNamesByFirstLetter() {
        List<String> list = List.of("alice", "bob", "adam", "bruce", "carol");
        System.out.println(list.stream().collect(Collectors.groupingBy(str -> str.charAt(0))));
        System.out.println(list.stream().collect(Collectors.groupingBy(
                str -> Character.toUpperCase(str.charAt(0)), (Supplier<TreeMap<Character, List<String>>>)TreeMap::new, Collectors.toList())));
    }

    /*
     * 1️⃣ How to Extract a List of Values from JSON Response in Rest Assured?
     * 📌 Question: How do you extract a list of all product names from a JSON
     * response and loop through them?
     */
    @Test
    public void extractListOfValues() {
        List<String> products = RestAssured.given().get("https://dummyjson.com/products").jsonPath()
                .getList("products.title");
        products.forEach(System.out::println);
    }

    /*
     * 2️⃣ How to Bypass SSL Certificate Errors in Rest Assured?
     * 📌 Question: You're testing a staging environment secured with a self-signed
     * certificate. How can you bypass SSL errors in Rest Assured?
     */
    @Test
    public void bypassSSLCertificateErrors() {
        try {
            RestAssured.given().get("https://self-signed.badssl.com/").then().log().all()
                    .statusCode(200)
                    .body(containsString("self-signed.badssl.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.given().get("https://self-signed.badssl.com/").then().log().all()
                .statusCode(200)
                .body(containsString("self-signed.badssl.com"));

    }

    /*
     * 3️⃣ How to Work with Read-Only Fields in Selenium?
     * 📌 Question: How do you validate or extract the value of a read-only input
     * field in Selenium?
     */
    @Test
    public void handleReadOnlyFields() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/automation-practice-form");
        WebElement fname = driver.findElement(By.id("firstName"));
        fname.sendKeys("John");
        ((JavascriptExecutor) driver).executeScript("arguments[0].readOnly=true", fname);
        Assert.assertEquals(fname.getAttribute("value"), "John");
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='Tom'", fname);
        Assert.assertEquals(fname.getAttribute("value"), "Tom");
        driver.quit();
    }

    /*
     * 4️⃣ What is the Difference Between Assert and Verify in Automation Testing?
     * 📌 Question: What’s the difference between assertions and verifications in
     * automation frameworks?
     * 
     * ✅ Answer:
     * Assert: Stops test execution when it fails (Hard assertion).
     * Verify: Logs the failure but continues execution (Soft assertion).
     */
    @Test
    public void hardAndSortAssertion() {
        try {
            Assert.assertTrue(false);
            Assert.assertEquals(new String("String1"), "String1");
        } catch (AssertionError e) {
            e.printStackTrace();
        }

        SoftAssert softassert = new SoftAssert();
        softassert.assertTrue(false, "Assert True 1 Fail");
        softassert.assertEquals(true, 3 % 2 == 0, "Assert Equals 2 Fail");
        softassert.assertAll();
    }

    /*
     * 5️⃣ How to Validate API Response Time (SLA) in Rest Assured?
     * 📌 Question: Your API has an SLA of 1 second. How do you validate that in
     * automation?
     */
    @Test
    public void validateAPIResponseTimeSLA(){
        RestAssured.given().get("https://dummyjson.com/posts?limit=100").then().time(lessThan(5000L));
    }
}
