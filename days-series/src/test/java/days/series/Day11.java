package days.series;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Day11 {

    /*
     * 1️⃣ Check for Valid Parentheses
     * 📌 Question: Given a string containing only ()[]{}, determine if it has valid
     * opening and closing brackets.
     * 🔹 Input: "({[]})"
     * 🔹 Output: true
     */
    @Test(dataProvider = "expressionProvider")
    public void validateParentheses(String str) {
        str = str.replaceAll("[^\\[\\](){}]", "").trim();
        if (str.isEmpty()) {
            System.out.println("Expression does not have Parentheses");
            return;
        }
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> mapping = new HashMap<>();
        boolean isValid = true;
        mapping.put('}', '{');
        mapping.put(']', '[');
        mapping.put(')', '(');
        for (char c : str.toCharArray()) {
            if (mapping.containsValue(c)) {
                stack.push(c);
            } else if (stack.isEmpty() || stack.pop() != mapping.get(c)) {
                isValid = false;
                break;
            }
        }
        System.out.println(isValid && stack.isEmpty());
    }

    @DataProvider(name = "expressionProvider")
    public Object[] dataProvider() {
        return new Object[] {
                "x * (a + b) - {y / [z - 3]}",
                "x * (a + b)) - {y / [z - 3]}",
                "x * (a + b) - {y / [z - 3]}",
                "a^2 + b^2 = c^2"
        };
    }

    /*
     * 2️⃣ Find the First Repeating Character in a String
     * 📌 Question: Given a string, return the first character that appears more
     * than once.
     * 🔹 Input: "swiss"
     * 🔹 Output: 's'
     */
    @Test(dataProvider = "StringProvider")
    public void findFristRepeatingChar(String str, char expected) {
        Set<Character> set = new HashSet<>();
        for (char ch : str.toLowerCase().toCharArray()) {
            if (!set.add(ch)) {
                Assert.assertEquals(ch, expected);
                break;
            }
        }
        if (set.isEmpty())
            Assert.assertNull(expected);
    }

    @DataProvider(name = "StringProvider")
    public Object[][] dataStringProvider() {
        return new Object[][] {
                { "java", 'a' },
                { "qwerty", '\0' },
                { "Go golang Ruby", 'g' },
                { "swiss", 's' }
        };
    }

    /*
     * 3️⃣ Implement an Interface to Find the Maximum of Two Numbers
     * 📌 Question: Implement a functional interface to return the maximum of two
     * numbers.
     * 🔹 Input: max(10, 20)
     * 🔹 Output: 20
     */
    @FunctionalInterface
    public interface Maximum {
        int max(int a, int b);
    }

    @Test
    public void finMaxFunctionalInterface() {
        Maximum findMax = (a, b) -> a > b ? a : b;
        System.out.println(findMax.max(10, 20));
        System.out.println(findMax.max(10, 10));
        System.out.println(findMax.max(0, 10));
        System.out.println(findMax.max(0, 0));
    }

    /*
     * 1️⃣ Read Data from an Excel File Using Apache POI
     * 📌 Question: How do you read data from an Excel file using Apache POI in
     * Selenium?
     */
    @Test(dataProvider = "ExcelDataProvider")
    public void testReadExcelData(String email, String password, String age) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        WebElement usernameElement = driver.findElement(By.id("user-name"));
        usernameElement.sendKeys(email);
        WebElement passwordElement = driver.findElement(By.id("password"));
        passwordElement.sendKeys(password);
        System.out.println(
                usernameElement.getDomAttribute("value") + " " + passwordElement.getDomAttribute("value") + " " + age);
        driver.quit();
    }

    @DataProvider(name = "ExcelDataProvider")
    public Object[][] excelDataProvider() {
        try (FileInputStream fileInputStream = new FileInputStream("./src/main/resources/userdata.xlsx")) {
            try (Workbook workbook = new XSSFWorkbook(fileInputStream)) {
                Sheet sheet = workbook.getSheetAt(0);
                List<List<String>> intStreamExcelData = IntStream.range(1, sheet.getPhysicalNumberOfRows())
                        .mapToObj(sheet::getRow)
                        .map(row -> IntStream.range(0, row.getLastCellNum())
                                .mapToObj(row::getCell).map(Day09::getCellValue).toList())
                        .toList();
                return intStreamExcelData.stream().map(list -> list.toArray(new Object[0])).toArray(Object[][]::new);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[0][0];
    }

    /*
     * 2️⃣ Validate JSON Response Keys in Rest Assured
     * 📌 Question: How do you check if an API response contains specific keys in
     * JSON format?
     */
    @Test
    public void validateJSONReponseKeys() {
        RestAssured.given().get("https://dummyjson.com/posts").then()
                .body("posts.reactions", everyItem(hasKey("likes")));
    }

    /*
     * 3️⃣ Handle File Download in Selenium
     * 📌 Question: How do you verify that a file is downloaded successfully using
     * Selenium?
     */
    @Test
    public void handleFileDownload() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("-start-maximized");
        String downloadDirectory = String.join(File.separator, System.getProperty("user.dir"), "src", "main",
                "resources");

        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("download.default_directory", downloadDirectory);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://the-internet.herokuapp.com/download");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        List<WebElement> links = driver.findElements(By.xpath("//*[@class='example']//a"));
        int linkIndex = new Random().nextInt(links.size());
        links.get(linkIndex).click();
        File file = new File(downloadDirectory + File.separator + links.get(linkIndex).getText());
        int i = 0;
        while (i < 5 && !file.exists()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        Assert.assertTrue(file.exists(), "Is File Exists: " + file.getAbsolutePath());
        file.delete();
        driver.quit();
        i = 0;
        while (i < 5 && file.exists()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        Assert.assertFalse(file.exists(), "Is File Deleted: " + file.getAbsolutePath());

    }

    /*
     * 4️⃣ Validate API Request with Path Parameters in Rest Assured
     * 📌 Question: How do you send path parameters in a Rest Assured API request?
     */
    @Test
    public void sendPathParamsInRequest() {
        int postId = 1;
        RestAssured.given().pathParam("postId", postId)
                .get("https://dummyjson.com/posts/{postId}/comments").then()
                .body("comments.postId.size()", greaterThan(0))
                .body("comments[0].postId", equalTo(postId)).log().all();
    }

    /*
     * 5️⃣ Handle Radio Buttons in Selenium
     * 📌 Question: How do you select a radio button dynamically in Selenium?
     */
    @Test
    public void handleRadioButton() {
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("-start-maximized"));
        driver.get("https://demoqa.com/radio-button");
        driver.findElements(By.cssSelector("input[type='radio']"))
                .forEach(ele -> {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    String id = ele.getDomAttribute("id");
                    WebElement labelElement = ele.findElement(
                            By.xpath("//input[@id='" + id + "']/following-sibling::label"));
                    js.executeScript("arguments[0].scrollIntoViewIfNeeded()", labelElement);
                    labelElement.click();
                    System.out.println("Radio Button clicked: " + labelElement.getText());
                    boolean isTextDisplayed = driver.findElements(
                            By.xpath("//*[normalize-space()='You have selected " + labelElement.getText() + "']"))
                            .size() != 0;
                    Assert.assertTrue(id.contains("no") ? !isTextDisplayed : isTextDisplayed);
                });
        driver.quit();

    }
}
