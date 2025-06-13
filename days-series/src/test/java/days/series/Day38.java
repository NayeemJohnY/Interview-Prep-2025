package days.series;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

public class Day38 {

    /*
     * 2️⃣5️⃣ Find the Element that Appears Only Once
     * 📌 Scenario: In an array where every element appears twice except one, find
     * the element that appears only once.
     * 🔹 Input: {2, 3, 5, 4, 5, 3, 4}
     * 🔹 Output: 2
     */
    @Test
    public void findUniqueElement() {
        int[] arr = new int[] { 2, 3, 5, 4, 5, 3, 4 };
        int result = 0;
        for (int i : arr) {
            result ^= i;
        }
        System.out.println(result);
    }

    /*
     * 2️⃣6️⃣ Find the Minimum Distance Between Two Numbers
     * 📌 Scenario: Find the minimum distance between two given numbers in an array.
     * 🔹 Input: {3, 5, 4, 2, 6, 5, 6, 7, 4, 8, 3}, x = 3, y = 6
     * 🔹 Output: 4
     */
    @Test
    public void findMiminumDistance() {
        int[] arr = new int[] { 3, 5, 4, 2, 6, 5, 6, 7, 4, 8, 3, 1, 2, 4, 6, 11 };
        int x = 2, y = 3;
        int prev = -1, minDist = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x || arr[i] == y) {
                if (prev != -1 && arr[i] != arr[prev]) {
                    minDist = Math.min(minDist, i - prev);
                } else {
                    prev = i;
                }
            }
        }
        System.out.println(minDist);
    }

    /*
     * 2️⃣7️⃣ Find the Smallest Subarray with Sum Greater Than X
     * 📌 Scenario: Find the length of the smallest subarray whose sum is greater
     * than a given value.
     * 🔹 Input: {1, 4, 45, 6, 10, 19}, x = 51
     * 🔹 Output: 3 (Subarray: {4, 45, 6})
     */
    @Test
    public void findSmallestSubarrayWithSumGreaterThanX() {
        int[] arr = new int[] { 1, 2, 3, 1, 1, 3, 4, 2, 1, 3 };
        int x = 6, sum = 0, start = 0, minLength = Integer.MAX_VALUE, bestStart = 0;
        for (int end = 0; end < arr.length; end++) {
            sum += arr[end];
            while (sum > x) {
                if (minLength > end - start + 1) {
                    minLength = end - start + 1;
                    bestStart = start;
                }
                sum -= arr[start++];
            }

        }
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, bestStart, bestStart + minLength)));
    }

    /*
     * 2️⃣8️⃣ Check if Two Arrays are Equal
     * 📌 Scenario: Verify whether two arrays contain the same elements with the
     * same frequencies.
     * 🔹 Input: {1, 2, 3, 4}, {4, 3, 2, 1}
     * 🔹 Output: true
     */
    @Test
    public void checkIfTwoArraysAreEqual() {
        int[] arr1 = new int[] { 1, 2, 3, 4 };
        int[] arr2 = new int[] { 4, 3, 2, 1 };

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i : arr1) {
            hashMap.put(i, hashMap.getOrDefault(i, 0) + 1);
        }
        for (int i : arr2) {
            if (!hashMap.containsKey(i) || hashMap.get(i) == 0) {
                System.out.println(false);
                return;
            }
            hashMap.put(i, hashMap.get(i) - 1);
        }
        System.out.println(true);
    }

    /*
     * 1️⃣ What Is Test Independence and Why Does It Matter?
     * 📌 Question: What does test independence mean in automation, and how do you
     * achieve it?
     * 
     * Test independence means that each test case can be executed on its own, in
     * any order, without depending on:
     * The results or side effects of other tests.
     * Shared state or data created by previous tests.
     * External systems being in a specific state due to other tests.
     * 
     * 🧠 Why Test Independence Matters
     * Reliability & Consistency
     * Tests yield consistent results regardless of the execution order.
     * 
     * Debugging Simplicity
     * Failures are easier to trace since they're isolated to one test.
     * 
     * Parallel Execution
     * Independent tests can run simultaneously, speeding up test pipelines.
     * 
     * Maintainability
     * Easier to refactor and scale test suites when tests don’t rely on others.
     * 
     * Reduced Flakiness
     * Test outcomes aren’t affected by residual state (e.g., leftover data, open
     * sessions).
     * 
     * 🛠️ How to Achieve Test Independence
     * Isolate Test Data
     * Use fresh data for each test case, either by generating or mocking it.
     * Clean up (tear down) after each test run.
     * 
     * Avoid Shared State
     * Don't share static/global variables or objects between tests.
     * 
     * Mock or Stub External Dependencies
     * Replace external systems (APIs, DBs) with mocks so tests don't depend on
     * external behavior or timing.
     * 
     * Reset Environment Before/After Tests
     * Use setup (@BeforeEach) and teardown (@AfterEach) methods to ensure the
     * environment is clean.
     * 
     * Design Idempotent Tests
     * Re-running a test should not alter the outcome of any other test.
     * 
     * Avoid Test Order Dependency
     * Use test runners that randomize execution order to catch hidden dependencies.
     */

    /*
     * 2️⃣ How Do You Execute Tests in Parallel in TestNG?
     * 📌 Question: How do you run multiple test classes or methods in parallel
     * using TestNG?
     * 
     * TestNG provides built-in support for parallel test execution. You can run:
     * 
     * Multiple test methods
     * Multiple classes
     * Multiple test suites
     * …all in parallel, to reduce execution time and increase efficiency.
     * 
     * <suite name="ParallelTests" parallel="methods" thread-count="4">
     * <test name="TestMethods">
     * <classes>
     * <class name="com.example.tests.MyTestClass"/>
     * </classes>
     * </test>
     * </suite>
     * 
     * @DataProvider(name = "data", parallel = true)
     * 
     * 🧠 Best Practices
     * Make tests thread-safe (e.g., avoid shared state).
     * Use tools like ThreadLocal, especially with WebDriver (e.g., Selenium).
     * Use proper thread-count to match system capacity.
     * Use synchronization only if absolutely needed (it can reduce parallel
     * benefits).
     */

    /*
     * 3️⃣ What Are Artifacts in GitLab CI and Why Are They Used?
     * 📌 Question: What are artifacts in GitLab pipelines, and how do they help in
     * test automation?
     * 
     * ✅ Answer:
     * Artifacts are files generated during job execution (e.g., logs, reports).
     * Stored temporarily so they can be used by later stages or downloaded.
     * artifacts:
     * paths:
     * - reports/
     * expire_in: 1 hour
     */

    /*
     * 4️⃣ What Is Boundary Value Analysis (BVA) in Testing?
     * 📌 Question: What is boundary value analysis, and how is it applied in test
     * design?
     * 
     * ✅ Answer:
     * BVA is a test design technique focusing on values at the edge of input
     * ranges.
     * Boundary Value Analysis (BVA) is a black-box test design technique that
     * focuses on testing the edges or boundaries of input ranges, where defects are
     * more likely to occur.
     * 
     * 🧠 Why Use BVA?
     * Most bugs occur at the boundaries of input ranges.
     * BVA is cost-effective—you test fewer values but with high defect detection
     * potential.
     * Helps ensure input validation, range checking, and error handling work
     * correctly.
     * 
     * 📌 When to Use BVA
     * Numeric fields (e.g., age, quantity, price)
     * Date ranges (start/end dates)
     * Character limits (e.g., password length: 8–16 characters)
     * Any situation with defined min-max limits
     * 
     * Example: For age input allowed between 18-60 → test with:
     * ✔️ 17, 18, 19
     * ✔️ 59, 60, 61
     * 🎯 Key Takeaway: Most defects occur at boundaries — testing edges increases
     * the chances of catching them.
     * 
     */
}
