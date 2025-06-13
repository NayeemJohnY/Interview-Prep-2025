package days.series;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

public class Day36 {

    /*
     * 1️⃣7️⃣ Find the Length of the Longest Subarray with Sum Zero
     * 📌 Scenario: In a test data set, find the length of the longest subarray that
     * sums up to zero.
     * 🔹 Input: {1, -1, 3, 2, -2, -3, 4}
     * 🔹 Output: 5 (subarray: {1, -1, 3, 2, -2})
     * 
     * If cumulative sum == 0 at any index i, then subarray from 0 to i sums to
     * zero.
     * If cumulative sum == x at two indices i and j, then the subarray between i+1
     * to j sums to 0.
     * | Index | arr\[i] | Cumulative Sum | Map (sum → first index) | Match? |
     * Subarray | Length |
     * | ----- | ------- | -------------- | ----------------------- | ------ |
     * ------------------- | ------ |
     * | 0 | 1 | 1 | {0:-1, 1:0} | ❌ | | |
     * | 1 | -1 | 0 | {0:-1, 1:0} | ✅ | \[0..1] → {1,-1} | 2 |
     * | 2 | 3 | 3 | {0:-1, 1:0, 3:2} | ❌ | | |
     * | 3 | 6 | 9 | {0:-1, 1:0, 3:2, 9:3} | ❌ | | |
     * | 4 | -2 | 7 | {… ,7:4} | ❌ | | |
     * | 5 | 7 | 14 | {… ,14:5} | ❌ | | |
     * | 6 | -5 | 9 | sum=9 found at index 3 | ✅ | \[4..6] → {-2,7,-5} | 3 |
     * | 7 | 2 | 11 | {… ,11:7} | ❌ | | |
     * 
     */
    @Test
    public void findLengthOfLongestSubArrayWithSumZero() {
        int[] arr = new int[] { 1, -1, 3, 6, -2, 7, -5, 2 };
        Map<Integer, Integer> sumIndexMap = new HashMap<>();
        int maxLen = 0, start = 0, sum = 0;
        sumIndexMap.put(0, -1); // base case: sum 0 at index -1

        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];

            if (sumIndexMap.containsKey(sum)) {
                int prevIndex = sumIndexMap.get(sum);
                int length = i - prevIndex;
                if (length > maxLen) {
                    maxLen = length;
                    start = prevIndex + 1;
                }
            } else {
                sumIndexMap.put(sum, i);
            }
        }
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, start, start + maxLen)));
    }

    /*
     * 1️⃣8️⃣ Find the Maximum Sum of K Consecutive Elements
     * 📌 Scenario: Calculate the maximum sum of any K consecutive elements in an
     * array (sliding window technique).
     * 🔹 Input: {2, 1, 5, 2, 8, 1, 5}, k = 3
     * 🔹 Output: 15 (subarray: {5, 2, 8})
     * 
     * 📦 Example:
     * For array: [2, 1, 5, 1, 3, 2], and K = 3
     * Initial window: 2 + 1 + 5 = 8
     * Slide window:
     * 
     * Remove 2, add 1 → window is 1 + 5 + 1 = 7
     * Remove 1, add 3 → window is 5 + 1 + 3 = 9 ✅
     * Remove 5, add 2 → window is 1 + 3 + 2 = 6
     * 
     * Max sum = 9
     */
    @Test
    public void findMaximumSumOfKConsectiveEleements() {
        int[] arr = new int[] { 2, 1, 5, 2, 8, 1, 5, 10, 20, 12, 3, 4 };
        int k = 3;
        int maxSum = 0, currentSum = 0, start = 0;

        for (int i = 0; i < k; i++) {
            currentSum += arr[i];
        }
        maxSum = currentSum;
        for (int j = k; j < arr.length; j++) {
            currentSum += arr[j] - arr[j - k];
            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = j - k + 1;
            }
        }
        System.out.println(maxSum);
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, start, start + k)));
    }

    /*
     * 1️⃣9️⃣ Count Subarrays with Given Sum
     * 📌 Scenario: In a test case list, count subarrays that sum up to a specific
     * value.
     * 🔹 Input: {1, 1, 1}, sum = 2
     * 🔹 Output: 2 (subarrays: {1, 1}, {1, 1})
     */
    @Test
    public void countSubArraysWithGivenSum() {
        int[] arr = new int[] { 1, 2, 3, 4, -5, -1, 7, 2, 2, 1, 1, 7 };
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0, count = 0, targetSum = 6;
        map.put(0, 1); // base case
        for (int i : arr) {
            sum += i;
            count += map.getOrDefault(sum - targetSum, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        System.out.println(count);
    }

    /*
     * 2️⃣0️⃣ Find the Maximum Length of Even-Odd Subarray
     * 📌 Scenario: Find the longest subarray where elements strictly alternate
     * between even and odd.
     * 🔹 Input: {5, 10, 20, 6, 3, 8}
     * 🔹 Output: 3 (subarray: {6, 3, 8})
     */
    public boolean isEven(int num) {
        return num % 2 == 0;
    }

    public boolean isOdd(int num) {
        return num % 2 != 0;
    }

    @Test
    public void findMaximumLengthOfEvenOddSubarray() {
        int[] arr = new int[] { 5, 10, 20, 6, 3, 8, 1, 1, 4, 3, 2, 5, 6 };
        int currentLen = 1, maxLength = 1;
        for (int i = 1; i < arr.length; i++) {
            if ((isEven(arr[i]) && isOdd(arr[i - 1])) || (isOdd(arr[i]) && isEven(arr[i - 1]))) {
                currentLen++;
                maxLength = Math.max(currentLen, maxLength);
            } else {
                currentLen = 1;
            }
        }
        System.out.println(maxLength);
    }

    /*
     * 1️⃣ What Is Test Case Traceability and Why Is It Important?
     * 📌 Question: What does traceability mean in testing, and how do you achieve
     * it?
     * 
     * 🔍 What Is Traceability in Testing?
     * Traceability in testing refers to the ability to link test cases to their
     * corresponding requirements, design documents, user stories, or defects. It's
     * a way to ensure that every requirement is covered by one or more test cases,
     * and vice versa
     * 
     * ✅ Types of Traceability:
     * Forward Traceability
     * From requirements → test cases
     * Ensures all requirements have tests
     * 
     * Backward Traceability
     * From test cases → requirements
     * Ensures all tests are traceable to a valid requirement
     * 
     * Bi-directional Traceability
     * Both forward and backward
     * Ideal for full coverage and accountability
     * 
     * | Benefit | Description |
     * | ---------------------------- |
     * ------------------------------------------------------------------------- |
     * | ✅ Requirement Coverage | Ensures all business needs are tested |
     * | ✅ Impact Analysis | Easily identify what tests to update when a requirement
     * changes |
     * | ✅ Defect Root Cause Analysis | Helps trace a defect back to a specific
     * requirement or missed test |
     * | ✅ Compliance & Audit | Essential for regulated industries (e.g., finance,
     * healthcare, aerospace) |
     * | ✅ Test Optimization | Avoids redundant or obsolete tests |
     * 
     */

    /*
     * 2️⃣ What Are Stages in GitLab CI/CD Pipelines?
     * 📌 Question: What are pipeline stages in GitLab, and how are they useful?
     * 
     * Stages define the execution flow of a CI/CD pipeline.
     * Common stages:
     * ✔️ build
     * ✔️ test
     * ✔️ deploy
     * stages:
     * - build
     * - test
     * - deploy
     */

    /*
     * 3️⃣ How Do You Handle Exceptions in TestNG Tests?
     * 📌 Question: How can you validate that a method throws an expected exception
     * in TestNG?
     */
    @Test(expectedExceptions = ArithmeticException.class)
    public void divideByZeroTest() {
        int result = 10 / 0;
    }

    /*
     * 4️⃣ What Are the Key States in the Bug Life Cycle?
     * 📌 Question: What is the typical bug life cycle followed in tools like JIRA?
     * 
     * Common states include:
     * New → Open → In Progress → Fixed → Retest → Closed
     * Optional states: Rejected, Deferred, Duplicate
     */
}
