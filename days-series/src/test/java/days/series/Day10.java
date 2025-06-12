package days.series;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Day10 {

    /*
     * 1️⃣ Find Longest Common Prefix
     * 📌 Question: Find the longest common prefix from an array of strings.
     * 🔹 Input: ["automation", "auto", "autonomous"]
     * 🔹 Output: "auto"
     */
    @Test
    public void longestCommonPrefix() {
        String[] arr = new String[] { "automation", "auto", "autonomous" };
        String commonPrefix = arr[0];
        for (String string : arr) {
            while (!string.startsWith(commonPrefix)) {
                commonPrefix = commonPrefix.substring(0, commonPrefix.length() - 1);
                if (commonPrefix.isEmpty()) {
                    return;
                }
            }

        }
        System.out.println(commonPrefix);
    }

    /*
     * 2️⃣ Find Unique Pairs That Sum to Target
     * 📌 Question: Find all unique pairs that sum to a given target.
     * 🔹 Input: {2, 7, 4, 3, 5, 1}, target = 6
     * 🔹 Output: [(2,4), (1,5)]
     */
    @Test
    public void uniquePairsSumToTarget() {
        int[] arr = new int[] { 2, 7, 4, 3, 5, 1 };
        List<Integer> list = new ArrayList<>(Arrays.stream(arr).boxed().toList());
        int sum = 6;
        int num1, num2 = 0;
        // Solution 1
        Set<Set<Integer>> pairsList = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            num1 = list.get(i);
            num2 = sum - num1;
            if (num1 != num2 && list.indexOf(num2) != -1) {
                HashSet<Integer> set = new HashSet<>();
                set.add(num1);
                set.add(num2);
                pairsList.add(set);
            }
        }
        System.out.println(pairsList);

        // Solution 2
        Set<List<Integer>> pairs = new LinkedHashSet<>();
        Set<Integer> numberSet = new LinkedHashSet<>();
        int complement = 0;
        for (Integer num : arr) {
            complement = sum - num;
            if (numberSet.contains(complement)) {
                pairs.add(Arrays.asList(Math.min(num, complement), Math.max(num, complement)));
            }
            numberSet.add(num);
        }
        System.out.println(pairs);
    }

    /*
     * 3️⃣ Remove Duplicates from a List
     * 📌 Question: Remove duplicate elements while maintaining order.
     * 🔹 Input: [3, 5, 3, 2, 5, 1]
     * 🔹 Output: [3, 5, 2, 1]
     */
    @Test
    public void removeDUplicatesMaintainOrder() {
        int[] arr = new int[] { 3, 5, 3, 2, 5, 1 };
        // Solution 1
        HashSet<Integer> hashSet = new LinkedHashSet<>();
        for (Integer num : arr) {
            hashSet.add(num);
        }
        System.out.println(hashSet);

        // Solution 2
        System.out.println(Arrays.toString(IntStream.of(arr).distinct().toArray()));
    }

    /*
     * 4️⃣ Sort Logs (Letter before Digit)
     * 📌 Question: Sort logs where letter-logs appear before digit-logs.
     * 🔹 Input: ["log1 8 1 5", "log2 act car", "log3 own kit", "log4 2 3 4"]
     * 🔹 Output: ["log2 act car", "log3 own kit", "log1 8 1 5", "log4 2 3 4"]
     */
    @Test
    public void sortLogsLetterBeforeDigit() {
        String[] arr = new String[] { "log1 8 1 5", "log2 act car", "log3 own kit", "log4 2 3 4" };
        Comparator<String> comparator = (a, b) -> {
            String[] logA = a.split(" ", 2);
            String[] logB = b.split(" ", 2);
            boolean isdigitA = Character.isDigit(logA[1].charAt(0));
            boolean isdigitB = Character.isDigit(logB[1].charAt(0));
            if (isdigitA && !isdigitB)
                return 1;
            else if (!isdigitA && isdigitB)
                return -1;
            else
                return logA[1].compareTo(logB[1]);
        };
        Arrays.sort(arr, comparator);
        System.out.println(Arrays.toString(arr));
    }

    /*
     * 5️⃣ Implement Functional Interface for Formatting Strings
     * 📌 Question: Implement an interface to format strings (lowercase + no
     * spaces).
     * 🔹 Input: "Hello World"
     * 🔹 Output: "helloworld"
     */
    @FunctionalInterface
    public interface StringFormatter {
        String format(String s);
    }

    @Test
    public void impleemntFunctionalInterFaceFormatting() {
        String str = "Hello World";
        StringFormatter formatter = s -> s.replaceAll("\\s+", "").toLowerCase();
        System.out.println(formatter.format(str));
    }

    /*
     * 1️⃣ Write Data to an Excel File Using Apache POI
     * 📌 Question: How do you write data to an Excel file in Selenium using Apache
     * POI?
     */
    @Test
    public void writeTOExcel() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/data.xlsx")) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Test");
                List<List<String>> userdata = Arrays.asList(
                        Arrays.asList("user1@gmail.com", "password1", "25"),
                        Arrays.asList("user2@gmail.com", "password2", "30"),
                        Arrays.asList("user3@gmail.com", "password3", "34"));
                // Way 1
                for (int rowNum = 0; rowNum < userdata.size(); rowNum++) {
                    Row row = sheet.createRow(rowNum);
                    for (int i = 0; i < userdata.get(rowNum).size(); i++) {
                        Cell cell = row.createCell(i);
                        cell.setCellValue(userdata.get(rowNum).get(i));
                    }
                }
                int filledRowNum = userdata.size();
                // Way 2
                IntStream.range(0, userdata.size()).forEach(rowNum -> {
                    Row row = sheet.createRow(filledRowNum + rowNum);
                    IntStream.range(0, userdata.get(rowNum).size()).forEach(cellNum -> {
                        Cell cell = row.createCell(cellNum);
                        cell.setCellValue(userdata.get(rowNum).get(cellNum));
                    });
                });
                workbook.write(fileOutputStream);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 2️⃣ Validate XML Attribute in Rest Assured
     * 📌 Question: How do you validate attributes inside an XML API response?
     */
    @Test
    public void validateXMLAttributeInResponse() {
        String city = "London";
        RestAssured.given().baseUri("https://api.openweathermap.org")
                .basePath("data/2.5/weather")
                .queryParam("q", city)
                .queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea")
                .queryParam("mode", "xml")
                .get().then()
                .body("current.city.@name", equalTo(city))
                .body("current.temperature.@unit", equalTo("kelvin"))
                .body("current.lastupdate.@value", containsString(LocalDate.now().toString()));
    }

    /*
     * 3️⃣ Handle Drag and Drop in Selenium
     * 📌 Question: How do you automate drag and drop functionality in Selenium
     * WebDriver?
     */
    @Test
    public void handleDragAndDrop() {
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized"));
        driver.get("https://blockly.games/maze?lang=en");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        Actions actions = new Actions(driver);

        // Maze 1
        WebElement moveForward = driver.findElement(By.xpath(
                "//*[name()='svg' and @class='blocklyFlyout']/descendant::*[@class='blocklyDraggable' and contains(., 'forward')]"));
        WebElement blockyCanvas = driver
                .findElement(By.xpath("//*[name()='svg']/descendant::*[@class='blocklyBlockCanvas']"));
        actions.dragAndDrop(moveForward, blockyCanvas).perform();
        driver.findElement(By.id("runButton")).click();
        Assert.assertTrue(driver.findElements(By.xpath("//*[text()='Congratulations!']")).size() > 0);
        driver.findElement(By.id("doneOk")).click();

        // Maze 2
        List<String> actionsList = Arrays.asList("left", "forward", "right", "forward");
        for (int i = 0; i < actionsList.size(); i++) {
            WebElement turnLeft = driver.findElement(By.xpath(
                    "//*[name()='svg' and @class='blocklyFlyout']/descendant::*[@class='blocklyDraggable' and contains(., '"
                            + actionsList.get(i) + "')]"));
            blockyCanvas = driver.findElement(By.xpath("//*[name()='svg']/descendant::*[@class='blocklyBlockCanvas']"));
            actions.clickAndHold(turnLeft).pause(2).moveToElement(
                    blockyCanvas, 20, 20 * (i + 1)).pause(2).release().perform();
        }

        driver.findElement(By.id("runButton")).click();
        Assert.assertTrue(driver.findElements(By.xpath("//*[text()='Congratulations!']")).size() > 0);
        driver.findElement(By.id("doneOk")).click();
        driver.quit();
    }

    /*
     * 4️⃣ Validate API Response Count in Rest Assured
     * 📌 Question: How do you validate that an API response contains exactly N
     * elements?
     */
    @Test
    public void validateAPIResponseElementCount() {
        RestAssured.get("https://dummyjson.com/posts").then()
                .body("posts.size()", equalTo(30));
    }

    /*
     * 5️⃣ Handle Alerts in Selenium
     * 📌 Question: How do you handle JavaScript alerts in Selenium WebDriver?
     */
    @Test
    public void handleAlerts() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.findElement(By.xpath("//*[text()='Click for JS Alert']")).click();
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You successfully clicked an alert");

        driver.findElement(By.xpath("//*[text()='Click for JS Confirm']")).click();
        alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.dismiss();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked: Cancel");

        driver.findElement(By.xpath("//*[text()='Click for JS Prompt']")).click();
        alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.sendKeys("Java Selenium Alert");
        alert.accept();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You entered: Java Selenium Alert");
        driver.quit();

    }
}
