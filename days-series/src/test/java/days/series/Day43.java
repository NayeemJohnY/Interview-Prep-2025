package days.series;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class Day43 {

    /*
     * 4️⃣2️⃣ Find the Element with Maximum Frequency
     * 📌 Scenario: Return the number that occurs most frequently in the array.
     * 🔹 Input: {1, 3, 2, 1, 4, 1, 3}
     * 🔹 Output: 1
     */
    @Test
    public void findElementWithMaxFreqeuncy() {
        int[] arr = new int[] { 1, 3, 2, 1, 4, 1, 3 };
        HashMap<Integer, Integer> freMap = new HashMap<>();
        int maxFreqency = 0, result = -1;
        for (int i : arr) {
            freMap.put(i, freMap.getOrDefault(i, 0) + 1);
            if (freMap.get(i) > maxFreqency) {
                maxFreqency = freMap.get(i);
                result = i;
            }
        }
        System.out.println(result);

        // Solution 2
        freMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
                .ifPresent(System.out::println);
    }

    /*
     * 4️⃣3️⃣ Check If a Subarray with Zero Sum Exists
     * 📌 Scenario: Return true if there's a subarray with sum = 0.
     * 🔹 Input: {4, 2, -3, 1, 6}
     * 🔹 Output: true
     */
    @Test
    public void checkIfSubarrayWithZeroSumExists() {
        int[] arr = new int[] { 4, 2, -3, 1, 6 };
        int sum = 0;
        HashSet<Integer> prefixSums = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (sum == 0 || prefixSums.contains(sum)) { // prefix sums{4, 6, 3, 4}
                System.out.println(true);
                return;
            }
            prefixSums.add(sum);
        }
        System.out.println(false);
    }

    /*
     * 4️⃣4️⃣ Find Two Repeating Elements in an Array of Size N+2
     * 📌 Scenario: In an array of size n+2, where elements are from 1 to n, find
     * the two repeating numbers.
     * 🔹 Input: {4, 2, 4, 5, 2, 3, 1}
     * 🔹 Output: 2, 4
     */
    @Test
    public void findRepetingElements() {
        int[] arr = new int[] { 4, 2, 4, 5, 2, 3, 1 };
        HashSet<Integer> uniqueElements = new HashSet<>();
        List<Integer> duplicateElements = new ArrayList<>();
        for (int i : arr) {
            if (!uniqueElements.add(i))
                duplicateElements.add(i);
        }
        System.out.println(duplicateElements);
    }

    /*
     * 1️⃣ What Are Test Case Prioritization Techniques and Why Are They Used?
     * 📌 Question: How do you prioritize test cases when you have limited time or
     * resources?
     * 
     * ✅ Answer:
     * Test Case Prioritization is the process of ordering test cases so that the
     * most important ones run first — especially when time or resources are
     * limited.
     * 
     * 🎯 Why Is It Used?
     * To detect critical defects early
     * To maximize test coverage within constraints
     * To focus on business-critical features
     * To save cost and time in tight deadlines
     * 
     * Techniques include:
     * ✔️ Risk-Based Prioritization – Focus on high-impact areas
     * ✔️ Requirement-Based – Based on business criticality
     * ✔️ Recent Code Changes – Focus on modules that were updated
     * ✔️ Usage Frequency – Most-used features are tested first
     * 
     * | Technique | Description |
     * | -------------------------------- |
     * ----------------------------------------------------------------- |
     * | **1. Risk-Based Prioritization** | Focus on areas with **high risk** of
     * failure or impact |
     * | **2. Business Priority** | Run tests for **core business functions** first
     * |
     * | **3. Frequency of Use** | Prioritize tests for **frequently used features**
     * |
     * | **4. Recent Changes** | Focus on areas that were **recently modified or
     * bug-fixed** |
     * | **5. Severity of Past Failures** | Give priority to areas with **known
     * defect history** |
     * | **6. Customer Requirements** | Test based on **customer-defined critical
     * paths** |
     * | **7. Requirements Coverage** | Ensure **early coverage of must-have
     * requirements** |
     * | **8. Test Case Execution Time** | Run **shorter and high-value tests
     * first** to get faster feedback |
     * 
     * 
     * 🎯 Key Takeaway: Prioritization ensures that high-value test cases run first,
     * maximizing impact with minimal time.
     */

    /*
     * 2️⃣ What Is a Factory in TestNG and When Should You Use It?
     * 📌 Question: What is the @Factory annotation in TestNG and how does it help?
     * 
     * @Factory is a TestNG annotation used to create test instances dynamically at
     * runtime.
     * 
     * Instead of letting TestNG create test classes automatically, @Factory gives
     * manual control over object creation — especially useful when running the same
     * test class with different sets of data.
     * 
     * 🔧 How It Helps:
     * Enables data-driven test class instantiation
     * Useful when you want to run the same test with different constructor
     * parameters
     * Improves flexibility when handling complex test object setups
     * 
     * ✅ When to Use @Factory
     * Run same test with different configurations
     * Customize object creation before test run
     * Avoid using @DataProvider at method level
     * 
     * | Rule | Explanation |
     * | ----------------------------------------------- |
     * -------------------------------------------- |
     * | ✅ Must use **constructor** | Factory injects parameters via constructor |
     * | ✅ Factory must return `Object[]` | It returns instances of the test class |
     * | ✅ Factory and test must be in different methods | Otherwise, TestNG won’t
     * treat them correctly |
     * 
     */
    public static class LoginTest {
        private String role;

        public LoginTest(String role) {
            this.role = role;
        }

        @Test
        public void testLogin() {
            System.out.println("Testing login for: " + role);
            // actual test logic for this role
        }

        @Factory
        public Object[] createInstances() {
            return new Object[] {
                    new LoginTest("admin"),
                    new LoginTest("user"),
                    new LoginTest("guest")
            };
        }
    }

    /*
     * 3️⃣ What Is Exploratory Testing and When Do You Use It?
     * 📌 Question: What is exploratory testing and how does it differ from scripted
     * testing?
     * 
     * ✅ Exploratory Testing
     * Definition:
     * An unscripted, informal testing approach where testers actively explore the
     * application without predefined test cases.
     * Testers design, execute, and learn simultaneously, adapting tests based on
     * findings.
     * Focuses on discovering unknown issues and understanding application behavior.
     * 
     * ✅ Scripted Testing
     * Definition:
     * Testing that follows predefined test cases or scripts with clear steps and
     * expected results.
     * Executed as per plan, usually documented beforehand.
     * Focuses on validation against requirements and regression.
     * 
     * 🔑 Key Differences
     * Test Design:
     * Exploratory: Created and executed at the same time
     * Scripted: Designed before execution
     * 
     * Documentation:
     * Exploratory: Minimal or informal notes
     * Scripted: Detailed test cases and steps
     * 
     * Flexibility:
     * Exploratory: Highly flexible and adaptive
     * Scripted: Rigid, follows predefined steps
     * 
     * Focus:
     * Exploratory: Find unexpected defects and learn the system
     * Scripted: Verify specific requirements and expected behavior
     * 
     * Best Use Case:
     * Exploratory: When requirements are incomplete or changing, new features
     * Scripted: Regression, compliance, acceptance testing
     * 
     * ✅ When to Use Exploratory Testing
     * When requirements are incomplete or rapidly changing
     * To quickly uncover defects without heavy documentation
     * When testing new features or complex workflows
     * For ad hoc or session-based testing
     * When tester’s intuition and experience are valuable
     */

    /*
     * 4️⃣ How Does Parallel Job Execution Work in GitLab CI?
     * 📌 Question: How do you run multiple jobs at the same time in GitLab CI/CD?
     * 
     * ✅ Answer:
     * Jobs in the same stage are executed in parallel (if runners are available).
     * stages:
     * - test
     * 
     * unit_tests:
     * stage: test
     * script: run-unit.sh
     * 
     * api_tests:
     * stage: test
     * script: run-api.sh
     */
}
