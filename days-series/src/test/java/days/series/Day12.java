package days.series;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.XmlConfig;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;

public class Day12 {

    /*
     * 1️⃣ Remove Consecutive Duplicates from a String
     * 📌 Question: Given a string, remove consecutive duplicate characters while
     * maintaining the order.
     * 🔹 Input: "aaabbcddd"
     * 🔹 Output: "abcd"
     */
    @Test
    public void removeConsectiveDuplicates() {
        // Solution 1
        String str = "aaabbcddd";
        StringBuilder builder = new StringBuilder();
        char lastChar = '\0';
        for (char c : str.toCharArray()) {
            if (c != lastChar) {
                lastChar = c;
                builder.append(c);
            }
        }
        System.out.println(builder.toString());

        // Solution 2
        Stack<Character> stack = new Stack<>();
        StringBuilder builder2 = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (stack.isEmpty() || stack.peek() != c) {
                stack.add(c);
                builder2.append(c);
            }
        }
        System.out.println(builder2.toString());

        // Solution 3
        StringBuilder builder3 = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (i == 0 || str.charAt(i) != str.charAt(i - 1)) {
                builder3.append(str.charAt(i));
            }
        }
        System.out.println(builder3.toString());
    }

    /*
     * 2️⃣ Find the Kth Smallest Element in an Array
     * 📌 Question: Given an unsorted array, find the kth smallest element.
     * 🔹 Input: {7, 10, 4, 3, 20, 15}, k = 3
     * 🔹 Output: 7
     */
    @Test
    public void findKthSmallest() {
        int[] arr = new int[] { 7, 10, 4, 3, 20, 15 };
        int k = 3;
        // Solution 1
        System.out.println(Arrays.stream(arr).boxed().sorted().skip(k - 1).limit(1).toList());

        // Solution 2
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (Integer num : arr) {
            minHeap.add(num);
        }
        while (--k > 0) {
            minHeap.poll();
        }
        System.out.println(minHeap.peek());
    }

    /*
     * 3️⃣ Implement a Functional Interface for String Concatenation
     * 📌 Question: Implement an interface to concatenate two strings using a lambda
     * expression.
     * 🔹 Input: "SDET", "Automation"
     * 🔹 Output: "SDETAutomation"
     */
    @FunctionalInterface
    public interface StringConcat {
        String concat(String s1, String s2);
    }

    @Test
    public void testStringConcatWithFUnctionalInterface() {
        StringConcat stringConcat = (s1, s2) -> s1.concat(s2);
        System.out.println(stringConcat.concat("SDET", "Automation"));
    }

    /*
     * 1️⃣ Handle Response Compression in Rest Assured
     * 📌 Question: How do you validate that an API response is compressed (gzip or
     * deflate) in Rest Assured?
     */
    @Test
    public void validateResponseCompression() {
        RestAssured.given().header("Accept-Encoding", "gzip, deflate")
                .get("https://api.github.com/users/octocat/repos")
                .then().header("Content-Encoding",
                        anyOf(containsString("gzip"), containsString("deflate")));
    }

    /*
     * 2️⃣ Read Data from an Encrypted Excel File in Selenium
     * 📌 Question: How do you read an encrypted Excel file using Apache POI?
     */
    @Test
    public void handleEnryptedExcelFile() {
        String baseFileName = "./src/main/resources/dataEncrypted.xlsx";
        String password = "FilePassword@123";

        try {
            // --- 1. Create and Write the initial unencrypted Excel file ---
            try (Workbook workbook = new XSSFWorkbook();
                    FileOutputStream fileOut = new FileOutputStream(baseFileName)) {

                Sheet sheet = workbook.createSheet("dataEncrypt");
                Row row = sheet.createRow(0);
                Cell cell = row.createCell(0);
                cell.setCellValue("Setting Secret Cell Value");
                workbook.write(fileOut); // Write the unencrypted content
            }
            System.out.println("Initial Excel file created successfully at: " + baseFileName);

            // --- 2. Encrypt the Excel file (for .xlsx, this wraps it in a POIFS container)
            // ---
            // Create a new POIFSFileSystem for encryption
            POIFSFileSystem fsEncrypted = new POIFSFileSystem();
            EncryptionInfo infoEnc = new EncryptionInfo(EncryptionMode.agile); // Use Agile encryption mode
            Encryptor encryptor = infoEnc.getEncryptor();
            encryptor.confirmPassword(password);

            try (OPCPackage opcPackage = OPCPackage.open(new FileInputStream(baseFileName))) {
                // Write the existing OPC Package content into the encrypted output stream
                try (OutputStream os = encryptor.getDataStream(fsEncrypted)) {
                    opcPackage.save(os);
                }
            }

            // Save the POIFSFileSystem which now contains the encrypted .xlsx content
            try (FileOutputStream fos = new FileOutputStream(baseFileName)) {
                fsEncrypted.writeFilesystem(fos);
            }
            System.out.println("File encrypted successfully at: " + baseFileName);

            // --- 3. Decrypt the Excel file ---
            // When decrypting, we need to open the file as a POIFSFileSystem
            try (POIFSFileSystem fsDecrypted = new POIFSFileSystem(new FileInputStream(baseFileName))) {

                // Get encryption info from the POIFSFileSystem
                EncryptionInfo infoDec = new EncryptionInfo(fsDecrypted);
                Decryptor decryptor = Decryptor.getInstance(infoDec);

                if (!decryptor.verifyPassword(password)) {
                    throw new RuntimeException("Unable to decrypt: Incorrect password");
                }

                // Get the decrypted data stream. This stream contains the original OPC Package
                // content.
                try (InputStream decryptedDataStream = decryptor.getDataStream(fsDecrypted);
                        Workbook decryptedWorkbook = WorkbookFactory.create(decryptedDataStream)) { // Create workbook
                                                                                                    // from decrypted
                                                                                                    // stream

                    Sheet sheet = decryptedWorkbook.getSheetAt(0);
                    Row row = sheet.getRow(0);
                    Cell cell = row.getCell(0);
                    System.out.println("Cell Value from decrypted file: " + cell.getStringCellValue());
                }
            }
            System.out.println("File decrypted and content read successfully!");

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * 3️⃣ Extract Values from a Namespaced XML Response in Rest Assured
     * 📌 Question: How do you extract XML values when an API response contains
     * namespaces?
     */
    @Test
    public void handleNamesapcedXMLResponse() {
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("soap", "http://schemas.xmlsoap.org/wsdl/soap/");
        namespaces.put("xs", "http://www.w3.org/2001/XMLSchema");
        Response response = RestAssured.given()
                .config(RestAssured.config().xmlConfig(
                        new XmlConfig().namespaceAware(true).declareNamespaces(namespaces)))
                .accept(ContentType.XML)
                .when()
                .get("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL")
                .then().statusCode(200).extract().response();
        XmlPath xmlPath = new XmlPath(response.asString());
        System.out.println(xmlPath.getString("//soap:operation"));

    }

    /*
     * 4️⃣ Verify Disabled Elements in Selenium
     * 📌 Question: How do you check if an element is disabled on a webpage in
     * Selenium?
     */
    @Test
    public void verifyDisabledElements() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://seleniumpractise.blogspot.com/2016/09/how-to-work-with-disable-textbox-or.html");
        Assert.assertFalse(driver.findElement(By.id("pass")).isEnabled());

        driver.get("https://demoqa.com/radio-button");
        WebElement disbaledElement = driver.findElement(
                By.xpath("//label[text()='No']/preceding-sibling::input[@type='radio']"));
        Assert.assertFalse(disbaledElement.isEnabled());

        JavascriptExecutor jsExecutor = (JavascriptExecutor) (driver);
        jsExecutor.executeScript("arguments[0].click()", disbaledElement);
        driver.quit();
    }

    /*
     * 5️⃣ Send GraphQL Query Requests Using Rest Assured
     * 📌 Question: How do you send a GraphQL query request using Rest Assured?
     */
    @Test
    public void testGraphQLRequests() {
        String query = "{ countries { code name }}";
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"query\": \"" + query + "\"}")
                .post("https://countries.trevorblades.com/").then()
                .statusCode(200)
                .body("data.countries", not(empty()))
                .body("data.countries[0].name", notNullValue())
                .body("data.countries[0].code", notNullValue());

    }
}
