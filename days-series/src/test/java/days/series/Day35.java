package days.series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

public class Day35 {

    /*
     * 1️⃣3️⃣ Remove Duplicates from a Sorted Array
     * 📌 Scenario: Your test data contains duplicate entries in a sorted array.
     * Remove duplicates in-place and return the new length.
     * 🔹 Input: {1, 1, 2, 3, 3, 4}
     * 🔹 Output: 4 (Array becomes {1, 2, 3, 4})
     */
    @Test
    public void removeDuplicatesFromSortedArray() {
        int[] arr = new int[] { 1, 1, 2, 3, 3, 4 };
        // Solution 1
        System.out.println(Arrays.toString(Arrays.stream(arr).distinct().toArray()));

        // Solution 2
        int index = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1])
                arr[index++] = arr[i];
        }
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 0, index)));
    }

    /*
     * 1️⃣4️⃣ Find Intersection of Two Arrays
     * 📌 Scenario: You are analyzing user data from two systems and need to find
     * common user IDs.
     * 🔹 Input: {1, 2, 2, 1}, {2, 2}
     * 🔹 Output: {2}
     */
    @Test
    public void findInterSectionOfArrays() {
        int[] arr1 = new int[] { 1, 2, 2, 1 };
        int[] arr2 = new int[] { 2, 2 };
        HashSet<Integer> numberSet = new HashSet<>();
        HashSet<Integer> intersectionSet = new HashSet<>();

        for (int i : arr1) {
            numberSet.add(i);
        }

        for (int i : arr2) {
            if (numberSet.contains(i))
                intersectionSet.add(i);
        }

        System.out.println(intersectionSet);

        // Solution 2
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        List<Integer> result = new ArrayList<>();

        for (int i : arr1) {
            hashMap.put(i, hashMap.getOrDefault(i, 0) + 1);
        }

        for (int i : arr2) {
            if (hashMap.getOrDefault(i, 0) > 0) {
                result.add(i);
                hashMap.put(i, hashMap.getOrDefault(i, 0) - 1);
            }
        }
        System.out.println(result);
    }

    /*
     * 1️⃣5️⃣ Find the Majority Element
     * 📌 Scenario: In survey data, find the element that appears more than n/2
     * times.
     * 🔹 Input: {3, 3, 4, 2, 3, 3, 3}
     * 🔹 Output: 3
     */
    @Test
    public void findMajorityOfElement() {
        int[] arr = new int[] { 3, 3, 4, 2, 3, 3, 3 };
        int count = 0, candidate = 0;
        // Solution 1
        for (int i : arr) {
            if (count == 0) {
                candidate = i;
            }
            count = i == candidate ? 1 : -1;
        }
        System.out.println(candidate);

        // Solution 2
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i : arr) {
            hashMap.put(i, hashMap.getOrDefault(i, 0) + 1);
        }
        hashMap.entrySet().stream()
                .filter(entry -> entry.getValue() > arr.length / 2).map(Map.Entry::getKey).findAny()
                .ifPresentOrElse(System.out::println, () -> System.out.println("No Majority Element"));

    }

    /*
     * 1️⃣6️⃣ Find the Peak Element in an Array
     * 📌 Scenario: Identify a peak element which is greater than its neighbors.
     * 🔹 Input: {1, 3, 20, 4, 1, 0}
     * 🔹 Output: 20
     */
    @Test
    public void findPeakElement() {
        int[] arr = new int[] { 1, 3, 20, 4, 1, 0 };
        // int[] arr = new int[] { 111, 13, 12, 4, 1, 0 };
        // int[] arr = new int[] { 111, 13, 12, 4, 1, 124 };
        // int[] arr = new int[] { 111, 13, 12, 4, 1, 111 };
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
                System.out.println(arr[i]);
                return;
            }
        }
        int peak = arr[0] > arr[arr.length - 1] ? arr[0] : arr[arr.length - 1];
        System.out.println(peak);
    }

    /*
     * 1️⃣ What Is a Flaky Test and How Do You Handle It?
     * 📌 Question: What is a flaky test, and what are some strategies to handle
     * them in automation?
     * 
     * A flaky test is an automated test that sometimes passes and sometimes fails
     * without any changes to the codebase, environment, or test logic. These
     * inconsistencies make flaky tests particularly frustrating because they reduce
     * confidence in the test suite and can obscure real issues.
     * 
     * 🔍 Why Do Tests Become Flaky?
     * Common causes include:
     * Timing issues: e.g., tests that run before a page is fully loaded or a
     * response is received.
     * Concurrency problems: e.g., race conditions in asynchronous code.
     * External dependencies: e.g., third-party APIs, network latency, or database
     * states.
     * Environment inconsistencies: e.g., different OS, browser versions, or test
     * data.
     * 
     * ✅ Strategies to Handle Flaky Tests
     * Identify and Isolate
     * Use test analytics or flakiness detection tools (like Jenkins Test Flaky
     * plugin, CircleCI Insights, or custom logs).
     * Tag flaky tests and run them separately to avoid blocking CI/CD.
     * 
     * Add Waits/Delays Wisely
     * Replace hard-coded sleeps with explicit waits or polling mechanisms (e.g.,
     * WebDriverWait in Selenium).
     * Use retry logic carefully for asynchronous operations.
     * 
     * Stabilize the Environment
     * Ensure test environments are consistent (same OS, browser, DB state).
     * Mock external dependencies wherever possible.
     * 
     * Improve Test Data Management
     * Use deterministic and isolated test data.
     * Clean up and reset data between tests.
     * 
     * Reduce Test Dependencies
     * Avoid relying on other tests or shared resources.
     * Make tests atomic and idempotent.
     * 
     * Parallelization Best Practices
     * Use thread-safe operations.
     * Avoid shared mutable state across test threads.
     */

    /*
     * 2️⃣ What Is the Purpose of Listeners in TestNG?
     * 📌 Question: What are TestNG Listeners, and how are they used in automation?
     * 
     * TestNG Listeners are interfaces in the TestNG framework that allow you to
     * customize the behavior of your test execution. They "listen" to specific
     * events—such as when a test starts, passes, fails, or is skipped—and let you
     * define what should happen when those events occur.
     * 
     * 📸 Capture screenshots on failure
     * 📝 Log custom test reports
     * 🔁 Implement retry logic dynamically
     * 🔍 Track test execution status
     * ⏱️ Measure test duration
     * 📂 Integrate with reporting tools like ExtentReports or Allure
     * 
     * | Listener Interface | Description |
     * | ------------------------ |
     * ------------------------------------------------------------------------- |
     * | `ITestListener` | Tracks test method execution: onStart, onTestSuccess,
     * onTestFailure, etc. |
     * | `ISuiteListener` | Tracks suite-level events: onStart and onFinish of the
     * test suite. |
     * | `IReporter` | Helps create custom test reports. |
     * | `IAnnotationTransformer` | Used to modify test annotations at runtime. |
     * | `IInvokedMethodListener` | Monitors before/after method invocations. |
     * 
     * 
     * @Listeners(CustomListener.class)
     * public class MyTestClass {
     * // test methods
     * }
     * 
     * <listeners>
     * <listener class-name="your.package.CustomListener"/>
     * </listeners>
     */

    /*
     * 3️⃣ What Is the Difference Between Master and Feature Branches in GitLab?
     * 📌 Question: How are master/main and feature branches used in GitLab
     * workflows?
     * 
     * ✅ Answer:
     * master / main: The production-ready or stable branch.
     * feature/*: Used for new functionality or bug fixes.
     * 👉 Merged to main via Merge Requests after code review and tests.
     */

    /*
     * 4️⃣ What Are the Different Test Data Management Techniques?
     * 📌 Question: What are some strategies to manage test data effectively in
     * automation?
     * 
     * 🧠 Test Data Management (TDM) is crucial in automation because the accuracy
     * and reliability of your tests depend heavily on having the right data.
     * Effective TDM ensures your tests are repeatable, reliable, and independent
     * 
     * ✅ Common Test Data Management Techniques:
     * 1. Static Test Data
     * Data is hard-coded within the test scripts or config files.
     * 🟢 Pros: Simple, quick to implement.
     * 🔴 Cons: Not flexible or reusable; difficult to maintain for large test
     * suites.
     * 
     * 2. Externalized Test Data (Data-Driven Testing)
     * Store data in external files like Excel, CSV, JSON, XML, or DB.
     * 🛠 Tools: Apache POI, Jackson, Gson, JDBC
     * 🟢 Pros: Separation of test logic and data; easy to scale and maintain.
     * 🔴 Cons: Needs proper data parsing and validation logic.
     *
     * 3. Database Test Data
     * Pull or insert test data from a database (SQL/NoSQL) during test setup and
     * teardown.
     * 🟢 Pros: Closely simulates production scenarios.
     * 🔴 Cons: Needs careful cleanup; potential data pollution
     * 
     * 4. API-Based Test Data Setup
     * Use internal APIs to create, read, or reset test data dynamically before
     * running tests.
     * 🟢 Pros: Fast, flexible, often mirrors real-world data flows.
     * 🔴 Cons: Requires stable API contracts and access.
     * 
     * 5. Mocking & Stubbing
     * Use tools like WireMock, Mockito, or MockServer to simulate services or data
     * responses.
     * 🟢 Pros: Isolates test from unstable or unavailable dependencies.
     * 🔴 Cons: Doesn’t test full end-to-end integration.
     * 
     */

}
