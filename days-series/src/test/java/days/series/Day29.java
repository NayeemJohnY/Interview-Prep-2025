package days.series;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Day29 {

    /*
     * 1️⃣ Clean Up User-Submitted Tags from Frontend
     * 📌 Scenario: Your app collects tags from users that may contain leading #,
     * extra spaces, or duplicates. Clean the list by:
     * removing #,
     * trimming whitespace,
     * converting to lowercase,
     * removing duplicates.
     * 🔹 Input:
     * [" #Java ", "#java", " Selenium ", " selenium ", "#REST "]
     * 🔹 Output:
     * ["java", "selenium", "rest"]
     */
    @Test
    public void cleanUpUserSubmittedTags() {
        List<String> tags = List.of(" #Java ", "#java", " Selenium ", " selenium ", " #REST ");
        System.out.println(tags.stream().map(tag -> tag.replaceAll("#", "")
                .trim().toLowerCase()).distinct().toList());
    }

    /*
     * 2️⃣ Convert Status Codes to Readable Messages
     * 📌 Scenario: You receive test case status codes from APIs like "P", "F", "S"
     * and want to display readable labels on the dashboard.
     * 🔹 Input:
     * ["P", "F", "S", "P", "F"]
     * 🔹 Output:
     * ["Passed", "Failed", "Skipped", "Passed", "Failed"]
     * 
     */
    @Test
    public void convertStatusCodeToReadbaleMessages() {
        List<String> codes = List.of("P", "F", "S", "P", "F", "A");
        Map<String, String> mapStatusCodes = Map.of("P", "Passed", "S", "Skipped", "F", "Failed");
        System.out.println(codes.stream().map(code -> mapStatusCodes.getOrDefault(code, "Unknown")).toList());
    }

    /*
     * 3️⃣ Detect Duplicate File Names in Upload Batch
     * 📌 Scenario: Users upload files in bulk. Detect duplicate file names
     * (case-insensitive) before saving.
     * 🔹 Input:
     * ["Report.pdf", "summary.docx", "report.PDF", "data.csv"]
     * 🔹 Output:
     * ["report.pdf"]
     */
    @Test
    public void detectDuplicateFileNamesInUploadBatch() {
        List<String> files = List.of("Report.pdf", "summary.docx", "report.PDF", "data.csv");
        HashSet<String> uniqueFiles = new HashSet<>();
        System.out.println(files.stream().map(String::toLowerCase).filter(file -> !uniqueFiles.add(file)).toList());

    }

    /*
     * 1️⃣ How to Send a JSON File as Request Body in Rest Assured?
     * 📌 Question: How do you send the contents of a .json file as the body of a
     * POST request in Rest Assured?
     */
    @Test
    public void sendJSONPayloadInAPIReqeust() {
        RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .body(new File("./src/main/resources/json_payload.json"))
                .post("https://dummyjson.com/users/add")
                .then().log().all().statusCode(201).body("firstName", equalTo("Muhammad"))
                .body("birthDate", equalTo(""));
    }

    /*
     * 2️⃣ How to Handle File Downloads in Selenium?
     * 📌 Question: How do you handle a file download scenario in Selenium
     * automation?
     */
    @Test
    public void handleFileDownload() {
        String downloadDirectory = String.join(File.separator, System.getProperty("user.dir"), "src", "main",
                "resources");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("download.default_directory", downloadDirectory);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.get("https://demoqa.com/upload-download");
        WebElement downloadButton = driver.findElement(By.linkText("Download"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='Tom'", downloadButton);
        downloadButton.click();

        File file = new File(downloadDirectory + File.separator + "sampleFile.jpeg");
        int i = 0;
        while (i++ < 5 && !file.exists()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Assert.assertTrue(file.exists());
        file.delete();
        driver.quit();

    }

    /*
     * 3️⃣ What is Content Negotiation in REST APIs?
     * 📌 Question: What is content negotiation, and how do you test it in
     * automation?
     * 
     * | Concept | Description |
     * | ----------------------- |
     * --------------------------------------------------------------------------- |
     * | **Content Negotiation** | The process of selecting the best response format
     * (JSON, XML, etc.) |
     * | **Driven by** | `Accept` request header |
     * | **Test it by** | Sending different `Accept` values and validating
     * `Content-Type` in response |
     * | **Common types** | `application/json`, `application/xml`, `text/html` |
     */
    @Test
    public void contentNegotiation() {
        String city = "London";
        // Content Negotiation in Query param
        RestAssured.given().baseUri("https://api.openweathermap.org")
                .basePath("data/2.5/weather")
                .queryParam("q", city)
                .queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea")
                .get().then().log().all().header("Content-Type", containsString(ContentType.JSON.toString()));

        RestAssured.given().baseUri("https://api.openweathermap.org")
                .basePath("data/2.5/weather")
                .queryParam("q", city)
                .queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea")
                .queryParam("mode", "xml")
                .get().then().log().all().header("Content-Type", containsString(ContentType.XML.toString()));

        System.out.println("=====================================");
        // Content Negotiation using accept
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.given().accept(ContentType.JSON).get("https://swapi.dev/api/people/1/").then().log().all()
                .header("Content-Type", containsString(ContentType.JSON.toString()));

        RestAssured.given().accept(ContentType.HTML).get("https://swapi.dev/api/people/1/").then().log().all()
                .header("Content-Type", containsString(ContentType.HTML.toString()));
    }

    /*
     * 4️⃣ How to Assert CSS Property in Selenium?
     * 📌 Question: How do you verify that a button has a specific background color
     * or font using Selenium?
     */
    @Test
    public void assertCSSPropertyOfElement() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/challenging_dom");
        WebElement foo = driver.findElement(By.xpath("//*[normalize-space()='foo']"));
        System.out.println(foo.getCssValue("color"));
        System.out.println(foo.getCssValue("background-color"));
        driver.quit();
    }

    /*
     * 5️⃣ How to Chain API Requests Using Extracted Data in Rest Assured?
     * 📌 Question: How do you extract a user ID from one API response and use it in
     * the next API call?
     */
    @Test
    public void chainAPIRequests() {
        int userId = RestAssured.given().get("https://dummyjson.com/users/search?q=logan").then().log().all()
                .extract().response().jsonPath().getInt("users.find{it.bank.currency == 'EUR'}.id");

        int postId = RestAssured.given().pathParam("userId", userId).get("https://dummyjson.com/users/{userId}/posts")
                .then().log().all()
                .body("posts.userId", everyItem(equalTo(userId))).extract().response().jsonPath().getInt("posts[0].id");

        RestAssured.given().pathParam("postId", postId).get("https://dummyjson.com/posts/{postId}/comments").then()
                .log().all()
                .body("comments.postId", everyItem(equalTo(postId)));
    }

}
