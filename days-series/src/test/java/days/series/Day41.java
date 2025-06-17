package days.series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

public class Day41 {

    /*
     * 3️⃣6️⃣ Find the Index of Equilibrium in an Array
     * 📌 Scenario: An equilibrium index is where the sum of elements before it
     * equals the sum after it.
     * 🔹 Input: {1, 3, 5, 2, 2}
     * 🔹 Output: 2
     */
    @Test
    public void findIndexOfEquilibrium() {
        int[] arr = new int[] { 1, 3, 3, 5, 2, 2, 2, 4, 4 };
        int leftSum = 0, totalSum = 0;
        for (int i : arr) {
            totalSum += i;
        }
        for (int i = 0; i < arr.length; i++) {
            totalSum -= arr[i];
            if (totalSum == leftSum) {
                System.out.println(i);
                return;
            }
            leftSum += arr[i];
        }
        System.out.println(-1);
    }

    /*
     * 3️⃣7️⃣ Maximum Sum Subarray of Size K
     * 📌 Scenario: Find the maximum sum of any subarray of size k.
     * 🔹 Input: {100, 200, 300, 400}, k = 2
     * 🔹 Output: 700
     */
    @Test
    public void maximumSumSubarrayofK() {
        int[] arr = new int[] { 100, 200, 300, 400, 400, 500, 1000 };
        int k = 2, bestStart = 0;
        int maxSum = 0, currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum += arr[i];
        }
        maxSum = currentSum;

        for (int j = k; j < arr.length; j++) {
            currentSum += arr[j] - arr[j - k];
            if (currentSum > maxSum) {
                maxSum = currentSum;
                bestStart = j - 1;
            }
        }

        System.out.println(maxSum);
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, bestStart, bestStart + k)));
    }

    /*
     * 3️⃣8️⃣ Count the Number of Subarrays with Product Less Than K
     * 📌 Scenario: Count how many contiguous subarrays have a product less than the
     * target k.
     * 🔹 Input: {10, 5, 2, 6}, k = 100
     * 🔹 Output: 8
     */
    @Test
    public void countNumberOfArraysWithProductLessThanK() {
        int[] arr = new int[] { 10, 5, 2, 6 };
        int left = 0, count = 0, prod = 1, k = 100;
        List<String> subarrays = new ArrayList<>();
        // which is product is less than 1 => 0 or -negative
        if (k <= 1)
            return;
        for (int right = 0; right < arr.length; right++) {
            prod *= arr[right];
            while (prod >= k) {
                prod /= arr[left++];
            }
            count += right - left + 1; // 10 => 0 - 0 +1=> 1; 5=> [5], [10], [5,10] => 1-0+1 => 2 => 1(prev 10) + 2=> 3
            for (int i = right; i >= left; i--) {
                subarrays.add(Arrays.toString(Arrays.copyOfRange(arr, i, right + 1)));
            }
        }
        System.out.println(count);
        System.out.println(subarrays);
    }

    /*
     * 1️⃣ How Do You Generate HTML Test Reports in TestNG?
     * 📌 Question: How does TestNG generate reports, and how can you customize
     * them?
     * 
     * 🔍 1. Default Report Generation in TestNG
     * TestNG automatically generates HTML and XML reports after each test run.
     * 
     * 🗂️ Location of Reports:
     * When you run TestNG tests (from IntelliJ, Eclipse, Maven, etc.), the
     * following folders are created in your project directory:
     * 
     * test-output/index.html → Main HTML report
     * test-output/emailable-report.html → Email-friendly summary report
     * test-output/testng-results.xml → XML report (useful for CI tools like
     * Jenkins)
     * 
     * 📊 Default HTML Report Includes:
     * Passed, failed, skipped tests
     * Execution time
     * Exceptions and stack traces (if any)
     * Grouping by classes, methods, and test suites
     * 
     * 🔧 2. Customizing TestNG Reports
     * ✅ A. Using IReporter Interface (Custom Reporters)
     * Override generateReport(...) method.
     * <listeners>
     * <listener class-name="your.package.CustomReporter" />
     * </listeners>
     * 
     * ✅ B. Using IReportListener / ITestListener (Event-Based Listeners)
     * Implement ITestListener to track individual test start/success/failure.
     * Implement ISuiteListener to track test suite start/end.
     * You can log information and generate custom reports (e.g., in Excel, HTML,
     * PDF).
     * 
     * ✅ C. Third-Party Reporting Tools (for Advanced Needs)
     * ExtentReports
     * Rich HTML reports with screenshots, logs, and themes.
     * Supports TestNG listeners like ExtentTestNGReporter.
     * 
     * Allure Reports
     * Visual, flexible, CI-friendly.
     * Requires extra setup but integrates well with TestNG.
     * 
     * ReportNG
     * Minimalistic HTML reports.
     * Needs configuration to replace default TestNG reports.
     */

    /*
     * 2️⃣ What Is the Role of ITestListener in TestNG?
     * 📌 Question: What is ITestListener, and how can it help in automation?
     * 
     * ITestListener is an interface in TestNG that allows you to listen to and
     * respond to events in the test execution lifecycle.
     * It's a listener interface in the org.testng package.
     * It provides callback methods that get triggered on specific events during the
     * execution of tests.
     * You can implement this interface to execute custom code when tests start,
     * pass, fail, skip, etc.
     * 
     * | Method | When It Is Called |
     * | ----------------------------------------------------- |
     * -------------------------------------------------- |
     * | `onTestStart(ITestResult)` | Before a test method starts |
     * | `onTestSuccess(ITestResult)` | When a test method passes successfully |
     * | `onTestFailure(ITestResult)` | When a test method fails |
     * | `onTestSkipped(ITestResult)` | When a test method is skipped |
     * | `onTestFailedButWithinSuccessPercentage(ITestResult)` | When a test fails
     * but is within success % |
     * | `onStart(ITestContext)` | Before any test starts in the current `<test>`
     * tag |
     * | `onFinish(ITestContext)` | After all tests in the current `<test>` tag
     * finish |
     * 
     * 
     * ✅ How Can ITestListener Help in Automation?
     * Custom Logging: Automatically log test start, success, failure details.
     * Screenshot Capture: Take screenshots on test failures and attach to reports.
     * Retry Logic: Combine with retry analyzers to rerun failed tests.
     * Report Enhancement: Add additional info or generate custom reports.
     * Notification Triggers: Send emails or notifications when tests fail.
     * Test Metrics Collection: Track execution time, count failures, etc.
     */

    // To use with onTestFailedButWithinSuccessPercentage
    @Test(successPercentage = 80, invocationCount = 5)
    public void flakyTest() {
        if (Math.random() < 0.5) {
            throw new RuntimeException("Intermittent failure");
        }
    }

    /*
     * 3️⃣ What Is the Purpose of .gitlab-ci.yml and What Are Best Practices for
     * Writing It?
     * 📌 Question: What does .gitlab-ci.yml do and how can you write it
     * effectively?
     * 
     * t defines your CI/CD pipeline stages and jobs.
     * Best Practices:
     * ✔️ Use clear naming for jobs
     * ✔️ Keep jobs modular and reusable
     * ✔️ Store sensitive data in CI variables
     * ✔️ Use caching to speed up builds
     * ✔️ Include only: changes to trigger selective pipelines
     */

    /*
     * 4️⃣ What Is the Difference Between Alpha and Beta Testing?
     * 📌 Question: How do alpha and beta testing differ in the software development
     * lifecycle?
     * 
     * Alpha Testing
     * Conducted in-house by developers or QA team
     * Happens early in the testing phase
     * Focuses on finding bugs and issues internally
     * High control over test environment and cases
     * 
     * Beta Testing
     * Performed by real users/customers outside the organization
     * Happens after alpha testing, closer to release
     * Focuses on user feedback and real-world usage
     * Less control over test environments
     */
}
