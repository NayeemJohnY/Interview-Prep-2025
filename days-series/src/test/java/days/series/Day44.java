package days.series;

import java.util.Arrays;

import org.testng.annotations.Test;

import groovyjarjarpicocli.CommandLine.Parameters;

public class Day44 {

    /*
     * 4️⃣5️⃣ Find the Missing and Repeating Number in One Pass
     * 📌 Scenario: Given an array of size n with numbers from 1 to n, one number is
     * missing and one is repeated.
     * 🔹 Input: {4, 3, 6, 2, 1, 1}
     * 🔹 Output: Repeated: 1, Missing: 5
     * 4+3+6+2+1+1 => 17 <= (6*(6+1)/2) = 21-17 = 4
     * User sum + squresums
     * Difference between expected and actual sum: x - y = S - sum → Equation (1)
     * Difference between expected and actual square sum: x² - y² = (x+y)(x-y) = S²
     * - sumSq → Equation (2)
     * x + y => S² - sumSq / S - sum → Equation (3)
     * Equation (1) + Equation (3) => 2x = [S - sum] + [S² - sumSq / S - sum ]
     * x = [S - sum] + [S² - sumSq / S - sum ]/2
     * y = x - [S-sum]
     * S - expectedSum
     */
    @Test
    public void findMissingAndRepeatingNumber() {
        int[] arr = new int[] { 4, 3, 6, 2, 1, 1 };
        int n = 6;
        int sum = 0, squareSum = 0;
        for (int i : arr) {
            sum += i;
            squareSum += i * i;
        }
        int sumDiff = (n * (n + 1) / 2) - sum;
        int sqaureSumDiff = (n * (n + 1) * (2 * n + 1) / 6) - squareSum;

        // x = ([S-sum]+ [S² - sumSq]/[S-sum])/2
        int repeating = (sumDiff + (sqaureSumDiff / sumDiff)) / 2;
        // x-y = S-sum => y = x-[S-sum]
        int missing = repeating - sumDiff;
        System.out.println(missing);
        System.out.println(repeating);
    }

    /*
     * 4️⃣6️⃣ Find the Pivot Index
     * 📌 Scenario: Return the index where sum of numbers left = sum of numbers
     * right.
     * 🔹 Input: {1, 7, 3, 6, 5, 6}
     * 🔹 Output: 3
     */
    @Test
    public void findPivotIndex() {
        int[] arr = new int[] { 1, 7, 3, 6, 5, 6 };
        int totalSum = Arrays.stream(arr).sum();
        int leftSum = 0;
        for (int i = 0; i < arr.length; i++) {
            totalSum -= arr[i];
            if (leftSum == totalSum) {
                System.out.println(i);
                return;
            }
            leftSum += arr[i];
        }
        System.out.println(-1);
    }

    /*
     * 4️⃣7️⃣ Find the Number Occurring Odd Number of Times
     * 📌 Scenario: In a list where all numbers appear twice except one, find the
     * number that appears an odd number of times.
     * 🔹 Input: {2, 3, 2, 3, 5}
     * 🔹 Output: 5
     * 
     */
    @Test
    public void findNumberOddOccurence() {
        int[] arr = new int[] { 2, 3, 2, 3, 5 };
        int result = 0;
        for (int i : arr) {
            result ^= i;
        }
        System.out.println(result);
    }

    /*
     * 1️⃣ What Are Code/Test Coverage Tools and How Are They Used?
     * 📌 Question: What are some tools used for code/test coverage, and what
     * insights do they provide?
     * 
     * Code/Test Coverage Tools are tools used to measure how much of your source
     * code is exercised when your tests (unit, integration, or system) are run.
     * 
     * They help answer:
     * 🧠 "Are my tests actually covering all the important parts of my code?"
     * 
     * 🔍 Key Insights They Provide:
     * ✅ Line Coverage: % of code lines executed during testing
     * 🔁 Branch Coverage: Whether all if/else and control paths are covered
     * 🔄 Condition Coverage: Ensures all boolean conditions within expressions are
     * evaluated both ways
     * 🧩 Function/Method Coverage: Checks if all functions/methods were called
     * during testing
     * 🧪 Missed Areas: Highlights untested or skipped code blocks
     * 📊 Overall Coverage: % Gives a high-level coverage percentage (e.g., 85%)
     * 
     * 🛠️ Common Code/Test Coverage Tools
     * JaCoCo Java Popular for Java, integrates with Maven, Gradle, IntelliJ
     * Cobertura Java Measures line and branch coverage
     * Coverage.py Python Tracks code execution, often used with unittest or pytest
     * Istanbul (nyc) JavaScript/Node.js Code coverage for JS apps, commonly used
     * with Mocha/Jest
     */

    /*
     * 2️⃣ How Do You Pass Parameters from testng.xml to Tests?
     * 📌 Question: How do you use @Parameters in TestNG with values defined in
     * testng.xml?
     * ✅ Answer:
     * Define parameters in XML:
     * <parameter name="env" value="staging"/>
     * Consume them in the test:
     * 
     */
    @Test
    @Parameters("testEnv")
    public void testEnv(String env) {
        System.out.println("Running in: " + env);
    }

    /*
     * 3️⃣ How Can You Share Artifacts Across Jobs in GitLab CI?
     * 📌 Question: How do you preserve and reuse build or test outputs across
     * GitLab pipeline stages?
     * ✅ Answer:
     * Use artifacts + dependencies to share between jobs:
     * build:
     * stage: build
     * script: compile.sh
     * artifacts:
     * paths:
     * - dist/
     * 
     * test:
     * stage: test
     * dependencies:
     * - build
     * script: test.sh
     */

    /*
     * 4️⃣ What Is Negative Testing and How Do You Design It?
     * 📌 Question: What is negative testing, and why is it important?
     * 
     * Negative testing is the process of testing a system with invalid, unexpected,
     * or incorrect inputs to ensure it handles errors gracefully and doesn't crash
     * or behave unpredictably.
     * 
     * 🔍 It's also called error path testing or failure path testing.
     * 
     * | Scenario | Negative Test Case Example |
     * | ---------------------------- | ------------------------------------------ |
     * | Login with wrong credentials | Enter wrong username/password |
     * | Form input | Enter letters into a numeric field |
     * | File upload | Upload a file with unsupported format |
     * | API testing | Send malformed JSON or invalid HTTP method |
     * | Boundary testing | Enter values outside valid input range |
     * | Empty inputs | Submit form with missing required fields |
     * 
     * 🛠️ How to Design Negative Test Cases:
     * Understand Valid Inputs First
     * Identify Possible Invalid Scenarios
     * Use Equivalence Partitioning & Boundary Value Analysis
     * Consider Security & Permissions
     * 
     * ✅ Why Is Negative Testing Important?
     * | Reason | Benefit |
     * | ------------------------------ |
     * ---------------------------------------------------- |
     * | Catches unhandled exceptions | Prevents app crashes and improves stability
     * |
     * | Improves user experience | Graceful error messages make apps more
     * user-friendly |
     * | Increases test robustness | Ensures full test coverage, not just happy
     * paths |
     * | Helps meet compliance/security | Validates secure and predictable system
     * behavior |
     * 
     */
}
