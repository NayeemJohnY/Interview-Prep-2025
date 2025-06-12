package days.series;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Day30 {

    /*
     * 1️⃣ Convert JSON-like Strings into Key-Value Pairs
     * 📌 Scenario: Your logging system writes data in pseudo-JSON format. Parse it
     * into a map for easy access and filtering.
     * 🔹 Input:
     * "user=alice;action=login;status=success"
     * 🔹 Output:
     * {user=alice, action=login, status=success}
     */
    @Test
    public void convertJSONLikeStingsToKeyValuePair() {
        String str = "user=alice;action=login;status=success";
        HashMap<String, String> hashMap = new HashMap<>();
        Arrays.stream(str.split(";")).forEach(s -> {
            String[] keyValue = s.split("=");
            hashMap.put(keyValue[0], keyValue[1]);
        });
        System.out.println(hashMap);
    }

    /*
     * 2️⃣ Redact Confidential Words from User Notes
     * 📌 Scenario: Users write internal notes containing words like "password",
     * "token", or "secret". Replace such words with "***" for display.
     * 🔹 Input:
     * "The password must not be shared. Token is generated."
     * 🔹 Output:
     * "The *** must not be shared. *** is generated."
     */
    @Test
    public void redactConfidentialWords() {
        String str = "The password must not be shared. Token is generated.";
        List<String> blackList = List.of("password", "token", "secret");
        for (String string : blackList) {
            str = str.replaceAll("(?i)" + string, "***");
        }
        System.out.println(str);
    }

    /*
     * 3️⃣ Extract Domain Names from Email Addresses
     * 📌 Scenario: You want to analyze which domains users are signing up from.
     * Extract and return domain names from a list of emails.
     * 🔹 Input:
     * ["alice@gmail.com", "bob@yahoo.com", "carol@gmail.com"]
     * 🔹 Output:
     * ["gmail.com", "yahoo.com"]
     */
    @Test
    public void extractDomainNames() {
        List<String> list = List.of("alice@gmail.com", "bob@yahoo.com", "carol@gmail.com");
        System.out.println(list.stream().map(str -> str.substring(str.indexOf("@") + 1)).distinct().toList());
    }

    /*
     * 1️⃣ How to Send Multipart/Form-Data in Rest Assured (File + Text)?
     * 📌 Question: How do you send a multipart API request that includes both a
     * file and form fields in Rest Assured?
     */
    @Test
    public void sendMultiPartFormData() {
        File file = new File("./src/main/resources/json_schema.json");
        RestAssured.given().multiPart("file", file)
                .multiPart("description", "Sample file upload")
                .post("https://httpbin.org/post")
                .then().log().all()
                .statusCode(200);

    }

    /*
     * 2️⃣ How to Perform Double Click Action in Selenium WebDriver?
     * 📌 Question: How do you perform a double-click operation on a web element
     * using Selenium?
     */
    @Test
    public void performDubcleClick() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("force-device-scale-factor=0.8");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://demoqa.com/buttons");
        new Actions(driver).doubleClick(driver.findElement(By.id("doubleClickBtn"))).perform();
        Assert.assertTrue(driver.findElement(By.xpath("//*[text()='You have done a double click']")).isDisplayed());
        driver.quit();

    }

    /*
     * 3️⃣ What Is HATEOAS in REST APIs?
     * 📌 Question: What is HATEOAS and how do you validate it in automation
     * testing?
     * 
     * HATEOAS stands for:
     * Hypermedia As The Engine Of Application State
     * It’s a key constraint of REST architecture where:
     * Clients interact with a REST API entirely through hyperlinks provided
     * dynamically by server responses.
     * It’s a constraint of REST where responses include navigational links.
     * 
     */
    @Test
    public void handleHATEOASInAPIResponse() {
        RestAssured.useRelaxedHTTPSValidation();
        String url = "https://swapi.dev/api/people/1/";
        String nextAPI = RestAssured.given().get(url).then().log().all()
                .extract().response().jsonPath().getString("homeworld");

        RestAssured.given().get(nextAPI).then().log().all().body("residents", hasItem(url));
    }

    /*
     * 4️⃣ How to Switch Between Tabs in Selenium?
     * 📌 Question: How do you automate tab switching when a new tab is opened after
     * clicking a link?
     */
    @Test
    public void switchBetweenTabs() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("force-device-scale-factor=0.9");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.numberOfWindowsToBe(2));
        Assert.assertEquals(driver.getWindowHandles().size(), 2);
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getCurrentUrl().equals("https://demoqa.com/sample"))
                break;
        }
        driver.quit();
    }

    /*
     * 5️⃣ How to Validate Empty JSON Array Response in Rest Assured?
     * 📌 Question: How do you assert that a particular field in the JSON response
     * is an empty array?
     */
    @Test
    public void validateEmptyJSONArrayInAPIResponse() {
        RestAssured.given().get("https://dummyjson.com/posts/200/comments").then().log().all()
                .body("comments", empty());
    }
}
