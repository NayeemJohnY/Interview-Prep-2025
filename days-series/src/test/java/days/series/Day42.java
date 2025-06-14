package days.series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Day42 {

    /*
     * 3️⃣9️⃣ Count Pairs with Given Sum
     * 📌 Scenario: Count the number of pairs in an array whose sum equals a given
     * value.
     * 🔹 Input: {1, 5, 7, -1}, sum = 6
     * 🔹 Output: 2
     */
    @Test
    public void countParisWithGiveSum() {
        int[] arr = new int[] { 1, 5, 7, -1 };
        int sum = 6;
        int count = 0;
        // Solution 1
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            count += map.getOrDefault(sum - i, 0);
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        System.out.println(count);

        // Solution 2
        HashSet<Integer> hashset = new HashSet<>();
        List<List<Integer>> pairs = new ArrayList<>();
        for (int num : arr) {
            if (hashset.contains(sum - num)) {
                pairs.add(Arrays.asList(num, sum - num));
            }
            hashset.add(num);
        }
        System.out.println(pairs.size());
        System.out.println(pairs);
    }

    /*
     * 4️⃣0️⃣ Find Maximum Length Subarray with Equal Number of 1s and 0s
     * 📌 Scenario: Convert 0s to -1s and find the largest subarray with sum = 0.
     * 🔹 Input: {0, 1, 0, 1, 1, 1, 0}
     * 🔹 Output: 6
     */
    @Test
    public void findMaximumLengthSubarrayWithEqual1s0s() {
        int[] arr = new int[] { 0, 1, 0, 1, 1, 1, 0 };
        int sum = 0, maxLength = 0, startIndex = 0;
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(0, -1);
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i] == 0 ? -1 : 1;
            if (sum == 0)
                maxLength = i + 1;
            if (hashMap.containsKey(sum)) {
                int len = hashMap.get(sum);
                if (len > maxLength) {
                    maxLength = len;
                    startIndex = hashMap.get(sum) + 1;
                }
            } else {
                hashMap.put(sum, i);
            }
        }
        System.out.println(maxLength);
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, startIndex, startIndex + maxLength)));
    }

    /*
     * 4️⃣1️⃣ Replace Each Element with the Next Greater Element on Right
     * 📌 Scenario: Replace every element with the next greater element on its
     * right. If none, put -1.
     * 🔹 Input: {4, 5, 2, 10}
     * 🔹 Output: {5, 10, 10, -1}
     */
    @Test
    public void replaeEachElementWithNextGreaterElementOnRight() {
        int[] arr = new int[] { 17, 18, 5, 4, 6, 1 };
        int maxRight = -1;
        for (int i = arr.length - 2; i >= 0; i--) {
            int temp = arr[i];
            arr[i] = maxRight;
            maxRight = Math.max(maxRight, temp);
        }
        System.out.println(Arrays.toString(arr));
    }

    /*
     * 1️⃣ What Is the Use of @Parameters Annotation in TestNG?
     * 📌 Question: How does @Parameters work in TestNG, and how is it different
     * from @DataProvider?
     * 
     * @Parameters is used to pass values from testng.xml into test methods.
     * It works with external parameterization, typically for configuration or
     * environment-specific values.
     * <!-- testng.xml -->
     * <suite name="Suite">
     * <test name="Test">
     * <parameter name="browser" value="chrome" />
     * <classes>
     * <class name="tests.MyTestClass"/>
     * </classes>
     * </test>
     * </suite>
     * 
     * @DataProvider is used for data-driven testing.
     * It provides multiple sets of data to the same test method — great for
     * repetitive testing with varied inputs.
     */
    @Test
    @Parameters("browser")
    public void launchBrowser(String browserName) {
        System.out.println("Launching browser: " + browserName);
        // Use the browserName in your test logic
    }

    /*
     * 2️⃣ What Is Caching in GitLab CI/CD and Why Is It Useful?
     * 📌 Question: How does caching work in GitLab CI/CD and how does it speed up
     * pipelines?
     * 
     * ✅ Answer:
     * GitLab cache stores dependencies or build outputs to avoid redundant
     * downloads in future jobs.
     * cache:
     * paths:
     * - .m2/repository/
     * Common use cases:
     * ✔️ Maven dependencies
     * ✔️ Node modules
     * ✔️ Docker layers
     */

    /*
     * 3️⃣ What Is a Requirement Traceability Matrix (RTM)?
     * 📌 Question: What is RTM and how do you create it?
     * 
     * RTM is a document that maps and traces user requirements with test cases.
     * Its purpose is to ensure that all requirements are covered by tests and to
     * track their validation throughout the project lifecycle.
     * 
     * 🧩 Purpose of RTM:
     * Verify 100% test coverage of requirements
     * Track which requirement has been tested, not tested, or failed
     * Identify missing or unimplemented requirements
     * Helps in impact analysis during change requests
     * Ensures transparency and traceability from requirements to testing
     * 
     * 🛠️ How to Create RTM:
     * Gather Requirements:
     * From SRS (Software Requirement Specification), BRD, or User Stories
     * List All Requirements:
     * Assign each a unique Requirement ID
     * Link to Test Cases:
     * Map which test case(s) validate each requirement
     * Track Execution Status:
     * As tests are executed, update the status (Passed/Failed/Not Run)
     * Maintain Regularly:
     * Keep RTM updated with changes in scope or tests
     * 
     * | Requirement ID | Requirement Description | Test Case ID(s) | Status |
     * Comments |
     * | -------------- | ----------------------------- | --------------- | -------
     * | ----------------- |
     * | REQ-001 | User should be able to log in | TC-001, TC-002 | Passed | Covered
     * |
     * | REQ-002 | Password reset functionality | TC-003 | Failed | Needs bug fix |
     * | REQ-003 | Profile update option | TC-004 | Not Run | Feature not ready |
     * 
     * 
     * | Type | Description |
     * | ------------------------- | ------------------------------------------ |
     * | **Forward Traceability** | Requirement → Test Cases |
     * | **Backward Traceability** | Test Cases → Requirement |
     * | **Bidirectional** | Tracks both ways (ideal for full coverage) |
     * 
     */

    /*
     * 4️⃣ What Is the Difference Between Static and Dynamic Testing?
     * 📌 Question: How do static testing and dynamic testing differ in QA practice?
     * 
     * ✅ Static Testing
     * Done without executing the code
     * Involves reviews, walkthroughs, or static analysis tools
     * Performed early in development
     * Detects issues like syntax errors, design flaws
     * Examples: Code review, requirement review, SonarQube
     * 
     * ✅ Dynamic Testing
     * Done by running the application
     * Validates functional behavior and runtime issues
     * Performed after code is built
     * Finds bugs, crashes, logical errors
     * Examples: Unit testing, UI testing, Selenium tests
     * 🎯 Key Takeaway: Static = preventive; Dynamic = detective. Both are crucial
     * to quality assurance.
     */
}
