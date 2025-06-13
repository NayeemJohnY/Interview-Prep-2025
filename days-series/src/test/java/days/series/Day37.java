package days.series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Day37 {

    /*
     * 2️⃣1️⃣ Find the Maximum Difference Between Two Elements
     * 📌 Scenario: Find the maximum difference between any two elements where the
     * larger element appears after the smaller one.
     * 🔹 Input: {2, 3, 10, 6, 4, 8, 1}
     * 🔹 Output: 8 (Difference between 10 and 2)
     */
    @Test
    public void findMaximumDifferenceBetween2Elements() {
        int[] arr = new int[] { 2, 3, 10, 6, 4, 8, 1 };
        int minElement = arr[0], maxDiff = Integer.MIN_VALUE;
        for (int i = 1; i < arr.length; i++) {
            maxDiff = Math.max(maxDiff, arr[i] - minElement);
            minElement = Math.min(minElement, arr[i]);
        }
        System.out.println(maxDiff);
    }

    /*
     * 2️⃣2️⃣ Merge Two Sorted Arrays into One Sorted Array
     * 📌 Scenario: Combine two pre-sorted arrays into a single sorted array.
     * 🔹 Input: {1, 3, 5}, {2, 4, 6}
     * 🔹 Output: {1, 2, 3, 4, 5, 6}
     */
    @Test
    public void mergeTwoSortedArrays() {
        int[] arr1 = new int[] { 1, 3, 5, 9 };
        int[] arr2 = new int[] { 2, 4, 6, 7, 10 };
        int[] result = new int[arr1.length + arr2.length];
        int i = 0, j = 0, index = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j])
                result[index++] = arr1[i++];
            else
                result[index++] = arr2[j++];

        }
        while (i < arr1.length)
            result[index++] = arr1[i++];
        while (j < arr2.length)
            result[index++] = arr2[j++];
        System.out.println(Arrays.toString(result));
    }

    /*
     * 2️⃣3️⃣ Find the First Repeating Element
     * 📌 Scenario: Identify the first element that repeats in the array.
     * 🔹 Input: {1, 2, 3, 2, 1}
     * 🔹 Output: 2
     */
    @Test
    public void findFirstRepeatingElement() {
        int[] arr = new int[] { 1, 2, 3, 4, 11 };
        HashSet<Integer> hashSet = new HashSet<>();
        for (int num : arr) {
            if (!hashSet.add(num)) {
                System.out.println(num);
                return;
            }
        }
        System.out.println("All Unique");
    }

    /*
     * 2️⃣4️⃣ Find the Longest Consecutive Sequence
     * 📌 Scenario: Given an unsorted array, find the length of the longest sequence
     * of consecutive integers.
     * 🔹 Input: {100, 4, 200, 1, 3, 2}
     * 🔹 Output: 4 (Sequence: {1, 2, 3, 4})
     */
    @Test
    public void findLongestConsecutiveSequence() {
        int[] arr = new int[] { 100, 4, 200, 1, 3, 2, 201, 202, 203, 204, 111, 112, 113, 114 };
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i : arr) {
            hashSet.add(i);
        }

        int currentNum = 0;
        List<List<Integer>> allSequences = new ArrayList<>();
        List<Integer> longestSequences = new ArrayList<>();
        for (Integer num : hashSet) {
            if (!hashSet.contains(num - 1)) {
                currentNum = num;
                List<Integer> seq = new ArrayList<>();
                seq.add(currentNum);

                while (hashSet.contains(currentNum + 1)) {
                    currentNum++;
                    seq.add(currentNum);
                }

                allSequences.add(seq);
                if (seq.size() > longestSequences.size()) {
                    longestSequences = seq;
                }
            }
        }
        System.out.println(allSequences);
        System.out.println(longestSequences);
    }

    /*
     * 1️⃣ What Is Code Coverage vs Test Coverage?
     * 📌 Question: What’s the difference between code coverage and test coverage in
     * QA?
     * 
     * ✅ Answer:
     * Code Coverage: Measures how much of the code is executed during testing.
     * 👉 Example: statements, branches, functions.
     * | Metric Type | Description |
     * | ---------------------- | --------------------------------------------- |
     * | **Line coverage** | % of code lines executed during testing |
     * | **Branch coverage** | % of branches (if/else) exercised by tests |
     * | **Function coverage** | % of functions/methods invoked |
     * | **Condition coverage** | % of boolean expressions evaluated true/false |
     * 
     * Test Coverage: Measures how much of the requirements or scenarios are tested.
     * 👉 Example: login flows, edge cases.
     * % of requirements covered by test cases
     * % of user stories that have at least one test
     * % of risk areas covered
     * Test case coverage for use cases or decision paths
     */

    /*
     * 2️⃣ How Do You Automatically Retry Failed TestNG Tests?
     * 📌 Question: How do you rerun failed tests automatically in TestNG without
     * manual intervention?
     */
    int retryflag = 0;

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void retryTest() {
        System.out.println("Retry : " + retryflag++);
        Assert.assertFalse(retryflag < 2);
        System.out.println("Passed in Retry " + retryflag);
    }

    /*
     * 3️⃣ What Is a GitLab Runner?
     * 📌 Question: What is a GitLab Runner and how does it work in CI pipelines?
     * 
     * ✅ Answer:
     * A GitLab Runner is an agent that executes CI/CD jobs defined in
     * .gitlab-ci.yml.
     * Types:
     * ✔️ Shared runners (provided by GitLab)
     * ✔️ Specific runners (self-hosted for your project)
     */

    /*
     * 4️⃣ What Are the Different Types of Software Testing?
     * 📌 Question: Can you name and explain a few types of software testing?
     * 
     * Sure! Here's a concise list of common types of software testing with one-line
     * descriptions:
     * 
     * ---
     * 
     * ### ✅ **Functional Testing**
     * 
     * 1. **Unit Testing** – Tests individual functions or components in isolation.
     * 2. **Integration Testing** – Tests interactions between integrated components
     * or systems.
     * 3. **System Testing** – Tests the complete and fully integrated application.
     * 4. **User Acceptance Testing (UAT)** – Validates the system with end-user
     * requirements.
     * 
     * ---
     * 
     * ### ✅ **Non-Functional Testing**
     * 
     * 5. **Performance Testing** – Measures the speed, responsiveness, and
     * stability under load.
     * 6. **Security Testing** – Identifies vulnerabilities and ensures data
     * protection.
     * 7. **Usability Testing** – Assesses how user-friendly and intuitive the
     * application is.
     * 8. **Compatibility Testing** – Verifies application behavior across devices,
     * OS, or browsers.
     * 
     * ---
     * 
     * ### ✅ **Maintenance Testing**
     * 
     * 9. **Regression Testing** – Confirms new changes haven't broken existing
     * features.
     * 10. **Smoke Testing** – Quick checks to validate basic and critical
     * functionality.
     * 11. **Sanity Testing** – Focused testing to confirm a specific issue or fix
     * works.
     * 
     * ---
     * 
     * ### ✅ **Other Testing Types**
     * 
     * 12. **Exploratory Testing** – Simultaneous learning and testing without
     * predefined cases.
     * 13. **Ad-hoc Testing** – Informal, unstructured testing without planning or
     * documentation.
     * 14. **A/B Testing** – Compares two versions of a product to determine which
     * performs better.
     * 
     */
}
