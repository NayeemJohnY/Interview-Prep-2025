package com.interview_prepare;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Day09 {

    /*
     * 1️⃣ Generate All Substrings of a String
     * 📌 Question: Given a string, generate all possible substrings.
     * 🔹 Input: "API"
     * 🔹 Output: ["A", "AP", "API", "P", "PI", "I"]
     */
    @Test
    public void generateAllSubstrings() {
        // Solution 1
        String str = "Automation";
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j <= str.length(); j++) {
                list.add(str.substring(i, j));
            }
        }
        System.out.println(list);
    }

    /*
     * 2️⃣ Check If Two Lists Have No Common Elements
     * 📌 Question: Verify if two lists are disjoint.
     * 🔹 Input: [1, 3, 5], [2, 4, 6]
     * 🔹 Output: true
     * 
     */
    @Test(dataProvider = "ListDataProvider")
    public void checkIfListsHaveNoCommon(int[] arr1, int[] arr2) {
        // Solution 1
        List<Integer> list1 = new ArrayList<>(Arrays.stream(arr1).boxed().toList());
        List<Integer> list2 = new ArrayList<>(Arrays.stream(arr2).boxed().toList());
        if (!list1.isEmpty() && !list2.isEmpty()) {
            boolean hasCommon = false;
            for (Integer integer : list1) {
                if (list2.indexOf(integer) != -1) {
                    hasCommon = true;
                    break;
                }
            }
            System.out.println(!hasCommon);
        } else {
            System.out.println("List is Empty");
        }

        // Solution 2
        System.out.println("Uisng Disjoint: " + Collections.disjoint(list1, list2));

        // Solution 3
        int prevSizeOfList1 = list1.size();
        if (!list1.isEmpty() && !list2.isEmpty()) {
            list1.removeAll(list2);
            System.out.println("Using RetainAll " + (prevSizeOfList1 == list1.size()));
        } else {
            System.out.println("Using RetainAll List is Empty");
        }

    }

    @DataProvider(name = "ListDataProvider")
    public Object[][] listCommon() {
        return new Object[][] {
                { new int[] { 1, 3, 5 }, new int[] { 2, 4, 6 } },
                { new int[] { 1, 3, 5 }, new int[] { 2, 5, 6 } },
                { new int[] { 1, 3, 5 }, new int[] { 1, 3, 5 } },
                { new int[] { 1, 3, 5 }, new int[] {} },
                { new int[] {}, new int[] { 1, 3, 5 } },
                { new int[] {}, new int[] {} },

        };
    }

    /*
     * 3️⃣ Find Longest Consecutive Sequence in an Array
     * 📌 Question: Find the longest sequence of consecutive numbers in an array.
     * 🔹 Input: [100, 4, 200, 1, 3, 2]
     * 🔹 Output: 4 ([1,2,3,4])
     */
    @Test
    public void longestConsecutiveSeq() {
        int[] arr = new int[] { 100, 4, 200, 1, 3, 2, 201, 202, 203, 204, 205 };

        // Solution 1
        Arrays.sort(arr);
        int maxCount = 1, currentCount = 1;
        int start = 0, maxStart = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] + 1 == arr[i + 1]) {
                currentCount++;
            } else {
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                    maxStart = start;
                }
                currentCount = 1;
                start = i;
            }
        }
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, maxStart, maxStart + maxCount)));

        // Solution 2
        Set<Integer> set = new HashSet<>();
        for (Integer num : arr) {
            set.add(num);
        }
        maxCount = 0;
        List<Integer> longestSeq = new ArrayList<>();
        int currentNum = 0;
        for (Integer num : set) {
            List<Integer> currentSeq = new ArrayList<>();
            if (!set.contains(num - 1)) {
                currentNum = num;
                currentSeq.add(num);

                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentSeq.add(currentNum);
                }

                if (currentSeq.size() > maxCount) {
                    maxCount = currentSeq.size();
                    longestSeq = currentSeq;
                }
            }
        }
        // Integer[] longestArray = longestSequence.toArray(new Integer[0]);
        // System.out.println(Arrays.toString(longestArray));
        System.out.println(longestSeq);

    }

    /*
     * 4️⃣ Sort Words by Length in Descending Order
     * 📌 Question: Sort a list of words based on length.
     * 🔹 Input: ["SDET", "Automation", "Java"]
     * 🔹 Output: ["Automation", "SDET", "Java"]
     */
    @Test
    public void sortWordsByLengthOfDescendingOrder() {
        List<String> list = Arrays.asList("SDET", "Automation", "Java");
        list.sort((a, b) -> Integer.compare(b.length(), a.length()));
        System.out.println(list);
    }

    /*
     * 5️⃣ Implement Functional Interface to Reverse a Number
     * 📌 Question: Reverse an integer using a functional interface.
     * 🔹 Input: 1234
     * 🔹 Output: 4321
     */
    @FunctionalInterface
    public interface ReverseNumber {
        int apply(int a);
    }

    @Test
    public void reverseNumber() {
        // Solution 1
        ReverseNumber rev = num -> Integer.parseInt(new StringBuilder(String.valueOf(num)).reverse().toString());
        System.out.println(rev.apply(1234));
        // Solution 2
        rev = (num -> {
            int res = 0;
            while (num != 0) {
                res = res * 10 + num % 10;
                num /= 10;
            }
            return res;
        });
        System.out.println(rev.apply(1234));
    }

    /*
     * 1️⃣ Read Data from Excel in Selenium
     * 📌 Question: How do you read data from an Excel file using Selenium with
     * Apache POI?
     */
    @Test(dataProvider = "excelDataProvider")
    public void testDataFromExcel(String usernmae, String password, String age) {
        System.out.println(usernmae + " " + password + " " + age);
    }

    public String getCellValue(Cell cell) {
        if (cell.getCellType() == CellType.FORMULA)
            return cell.getCellFormula();
        if (cell.getCellType() == CellType.BOOLEAN)
            return String.valueOf(cell.getBooleanCellValue());
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell))
                return cell.getDateCellValue().toString();
            else
                return String.valueOf(cell.getNumericCellValue());
        }
        return cell.getStringCellValue();
    }

    public List<List<String>> getExcelDataUsingIterator(Sheet sheet) {
        return Stream.iterate(1, i -> i < sheet.getPhysicalNumberOfRows(), i -> i + 1)
                .map(sheet::getRow)
                .map(row -> Stream.iterate(0, j -> j < row.getLastCellNum(), j -> j + 1)
                        .map(row::getCell).map(this::getCellValue).toList())
                .toList();
    }

    public List<List<String>> getExcelDataUsingIntStream(Sheet sheet) {
        return IntStream.range(1, sheet.getPhysicalNumberOfRows())
                .mapToObj(sheet::getRow)
                .map(row -> IntStream.range(0, row.getLastCellNum())
                        .mapToObj(row::getCell).map(this::getCellValue).toList())
                .toList();
    }

    public List<List<String>> getExcelDataUsingForLoop(Sheet sheet) {
        List<List<String>> excelData = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            List<String> rowData = new ArrayList<>();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                rowData.add(getCellValue(cell));
            }
            excelData.add(rowData);
        }
        return excelData;
    }

    @DataProvider(name = "excelDataProvider")
    public Object[][] excelDataProvider() {
        try (FileInputStream fileInputStream = new FileInputStream("./src/main/resources/userdata.xlsx")) {
            try (Workbook workbook = new XSSFWorkbook(fileInputStream)) {
                Sheet sheet = workbook.getSheetAt(0);
                List<List<String>> loopExcelData = getExcelDataUsingForLoop(sheet);
                List<List<String>> iteratorExcelData = getExcelDataUsingIterator(sheet);
                Assert.assertEquals(loopExcelData, iteratorExcelData);
                List<List<String>> intStreamExcelData = getExcelDataUsingIntStream(sheet);
                Assert.assertEquals(loopExcelData, intStreamExcelData);
                return intStreamExcelData.stream().map(list ->list.toArray(new Object[0])).toArray(Object[][]::new);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Object[0][0];
    }

    /*
     * 2️⃣ Validate XML Response in Rest Assured
     * 📌 Question: How do you validate a specific node value in an XML API response
     * using Rest Assured?
     */
    @Test
    public void validateXMLResponse() {
        RestAssured.given().accept(ContentType.XML)
                .get("https://petstore.swagger.io/v2/pet/findByStatus?status=available")
                .then().body("pets.Pet.category.name", hasItem("Dogs"));
    }

    /*
     * 3️⃣ Extract Data from a Web Table in Selenium
     * 📌 Question: How do you extract all values from a web table dynamically using
     * Selenium?
     */
    @Test
    public void extactDynamicTableData() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.nyse.com/ipo-center/recent-ipo");
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        List<WebElement> rowsPriceDeals = webDriverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("table[data-testid='priced-deals'] tbody tr")));

        List<List<String>> tableList = rowsPriceDeals.stream().map(
                row -> row.findElements(By.cssSelector("td")).stream().map(WebElement::getText).toList()).toList();
        System.out.println("Table: \n" + tableList);
        driver.quit();
    }

    /*
     * 4️⃣ Validate JSON Response with Dynamic Keys in Rest Assured
     * 📌 Question: How do you validate an API response with dynamic key values?
     */
    @Test
    public void validateDynamicJSONResponse() {
        RestAssured.given().get("https://dummyjson.com/recipes/meal-type/snack")
                .then().body("recipes.find {it.name == 'Chocolate Chip Cookies'}.tags", hasItem("Cookies"))
                .body("recipes.findAll {it.mealType.contains('Snack')}.size()", greaterThan(1));
    }

    /*
     * 5️⃣ Handle Dropdown Selection in Selenium
     * 📌 Question: How do you select an option from a dropdown using Selenium?
     */
    @Test
    public void handleLegacyDropDown() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/dropdown");
        Select select = new Select(driver.findElement(By.id("dropdown")));
        System.out.println(select.getOptions().stream().map(WebElement::getText).toList());
        select.selectByVisibleText("Option 2");
        select.selectByIndex(1);
        select.selectByValue("2");
        System.out.println(select.getAllSelectedOptions().stream().map(WebElement::getText).toList());
        driver.quit();

    }
}
