package days.series;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class Day45 {

    /*
     * 4️⃣8️⃣ Find the Maximum Circular Subarray Sum
     * 📌 Scenario: The array wraps around—find the maximum sum of subarray (normal
     * or circular).
     * 🔹 Input: {8, -1, 3, 4}
     * 🔹 Output: 15
     */
    @Test
    public void findMaximumCircularSubArraySum() {
        int[] input = new int[] { 8, -1, 3, 4 };
        int totalSum = input[0];
        int maxCurrent = input[0], maxGlobal = input[0];
        int minCurrent = input[0], minGlobal = input[0];
        for (int i = 1; i < input.length; i++) {
            int num = input[i];
            totalSum += num;
            maxCurrent = Math.max(num, maxCurrent + num);
            maxGlobal = Math.max(maxGlobal, maxCurrent);

            minCurrent = Math.min(minCurrent + num, num);
            minGlobal = Math.min(minGlobal, minCurrent);
        }
        if (maxGlobal > 0)
            System.out.println(Math.max(maxGlobal, totalSum - minGlobal));
        else
            System.out.println(maxGlobal);

    }

    /*
     * 4️⃣9️⃣ Find All Elements That Appear More Than n/3 Times
     * 📌 Scenario: In an array of size n, return all elements that appear more than
     * n/3 times.
     * 🔹 Input: {3, 2, 3}
     * 🔹 Output: [3]
     * n/3 => 6 /3 => 2 => 3 => {1, 1, 1, ,3, 3, 3}
     * => 10 /3 => 3.33 => 4 => {1, 1, 1, 1, 2, 2, 2, 2, 3, 3}
     * So only 2 elements
     */
    @Test
    public void findMajorityOfElementNby3() {
        int[] nums = { 2, 2, 2, 2, 3, 2, 3, 3 };
        int count1 = 0, count2 = 0, candidate1 = 0, candidate2 = 1;
        for (int num : nums) {
            if (num == candidate1)
                count1++;
            else if (num == candidate2)
                count2++;
            else if (count1 == 0) {
                candidate1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }
        List<Integer> result = new ArrayList<>();
        count1 = count2 = 0;
        for (int num : nums) {
            if (num == candidate1)
                count1++;
            else if (num == candidate2)
                count2++;
        }
        int n = nums.length;
        if (count1 > n / 3)
            result.add(candidate1);
        if (count2 > n / 3)
            result.add(candidate2);
        System.out.println(result);
    }

    /*
     * 5️⃣0️⃣ Maximize Sum of i*arr[i] After Rotations
     * 📌 Scenario: Find the max value of sum(i * arr[i]) after rotating the array
     * in all possible ways.
     * 🔹 Input: {8, 3, 1, 2}
     * 🔹 Output: 29
     * 8, 3, 1, 2 => (8*0 + 3*1 + 1*2+ 2*3) => 0+3+2+6 => 11
     * 3, 1, 2, 8 => (3*0 + 1*1 + 2*2 + 8 *3) => 0+1+4+24 = 29
     * 1, 2, 8, 3 => 0*1 + 1*2 + 2*8 + 3*3 => 0+2+16+9 => 27
     * 2, 8, 3, 1 => 0*2 + 1*8 + 2*3 + 3*1 => 0+8+6+3 => 17
     * Rj-1 + Sum of all elements - n * arr[i];
     * 
     */
    @Test
    public void findMaxSumAfterRotations() {
        int[] input = new int[] { 8, 3, 1, 2 };
        int total = 0, rotationSum = 0;
        int n = input.length;
        for (int i = 0; i < n; i++) {
            total += input[i];
            rotationSum += i * input[i];
        }
        int maxSum = rotationSum;
        for (int i = 1; i < n; i++) {
            rotationSum += total - n * input[n - i];
            maxSum = Math.max(maxSum, rotationSum);
        }
        System.out.println(maxSum);
    }

    /*
     * 1️⃣ What Are Best Practices for Managing Test Environments?
     * 📌 Question: What are some key practices when working with multiple QA/test
     * environments?
     * 
     * ✅ Answer:
     * Use environment-specific config files
     * Always reset test data post-execution
     * Automate environment setup with tools like Docker or Ansible
     * Clearly label environments: dev, qa, uat, staging, prod
     * Maintain version parity between environments
     */

    /*
     * 2️⃣ How to Run Multiple Test Classes in Parallel in TestNG?
     * 📌 Question: How do you execute multiple test classes concurrently using
     * TestNG?
     * 
     * ✅ Answer:
     * Define parallel="classes" in your testng.xml:
     * <suite name="Suite" parallel="classes" thread-count="3">
     * <test name="Tests">
     * <classes>
     * <class name="tests.LoginTest"/>
     * <class name="tests.RegistrationTest"/>
     * <class name="tests.ProfileTest"/>
     * </classes>
     * </test>
     */

    /*
     * 3️⃣ How Does GitLab CI Handle Job Retries?
     * 📌 Question: How can you automatically retry failed jobs in GitLab pipelines?
     * 
     * ✅ Answer:
     * Use the retry keyword in your .gitlab-ci.yml:
     * test_job:
     * script: run-tests.sh
     * retry: 2
     * Retries are helpful for flaky jobs or network issues.
     */

    /*
     * 4️⃣ What Is Error Guessing in Software Testing?
     * 📌 Question: What is the error guessing technique and when is it used?
     * 
     * ✅ Answer:
     * Error Guessing is a test design technique where testers use their experience,
     * intuition, and knowledge of common mistakes to predict areas in the
     * application that are likely to have defects.
     * 
     * 🛠️ When Is Error Guessing Used?
     * ✅ Typically used after formal test cases are written, to find additional
     * hidden bugs.
     * ✅ Very helpful in exploratory testing, ad-hoc testing, or regression testing.
     * ✅ Also useful when there's limited documentation or tight timelines.
     * 
     * | Scenario | Possible Error Guessed |
     * | ------------- | ---------------------------------- |
     * | Login form | Leave username blank |
     * | Numeric input | Enter letters |
     * | File upload | Try uploading a large/invalid file |
     * | Date field | Enter past or invalid date |
     * | Combo box | Leave unselected |
     * 
     * 📋 Tips for Effective Error Guessing:
     * 🔁 Review past defect logs
     * 🔍 Think like a user and a developer
     * 🚨 Target boundary cases and edge scenarios
     * 🧩 Use exploratory sessions
     * 🧠 Leverage team knowledge (developers, analysts, senior testers)
     */

}
