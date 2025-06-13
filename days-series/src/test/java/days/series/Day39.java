package days.series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

public class Day39 {

    /*
     * 2️⃣9️⃣ Find the Leaders in an Array
     * 📌 Scenario: An element is a leader if it is greater than all the elements to
     * its right. Print all leaders.
     * 🔹 Input: {16, 17, 4, 3, 5, 2}
     * 🔹 Output: {17, 5, 2}
     */
    @Test
    public void findLeadersInArray() {
        int[] arr = new int[] { 16, 17, 4, 3, 5, 2 };
        int index = 0;
        int[] leaders = new int[arr.length];
        leaders[index] = arr[arr.length - 1];
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] > leaders[index]) {
                leaders[++index] = arr[i];
            }
        }
        System.out.println(Arrays.toString(Arrays.copyOfRange(leaders, 0, index + 1)));
    }

    /*
     * 3️⃣0️⃣ Rearrange Array Alternating High-Low
     * 📌 Scenario: Rearrange the array such that elements are in high-low-high-low
     * order.
     * 🔹 Input: {1, 3, 2, 2, 5, 7}
     * 🔹 Output: {7, 1, 5, 2, 3, 2}
     */
    @Test
    public void rearrageArrayAlternatingHighLow() {
        int[] arr = new int[] { 1, 3, 2, 2, 5, 7 };
        int[] result = new int[arr.length];

        Arrays.sort(arr);
        int left = 0, right = arr.length - 1;
        for (int i = 0; i < result.length; i++) {
            result[i] = i % 2 == 0 ? arr[right--] : arr[left++];
        }
        System.out.println(Arrays.toString(result));
    }

    /*
     * 3️⃣1️⃣ Find Triplets that Sum to Zero
     * 📌 Scenario: Find all unique triplets in the array that sum up to zero.
     * 🔹 Input: {-1, 0, 1, 2, -1, -4}
     * 🔹 Output: [[-1, -1, 2], [-1, 0, 1]]
     */
    @Test
    public void findTripletsThatSumtoZero() {
        int[] arr = new int[] { -1, 0, 1, 2, -1, -4 };
        Arrays.sort(arr);
        List<List<Integer>> triplets = new ArrayList<>();
        for (int i = 0; i < arr.length - 2; i++) {
            // Skip duplicates for the first number
            if (i > 0 && arr[i] == arr[i - 1])
                continue;

            int left = i + 1;
            int right = arr.length - 1;

            while (left < right) {
                int sum = arr[i] + arr[left] + arr[right];
                if (sum == 0) {
                    triplets.add(Arrays.asList(arr[i], arr[left], arr[right]));

                    while (left < right && arr[left] == arr[left + 1]) {
                        left++;
                    }

                    while (left < right && arr[right] == arr[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        System.out.println(triplets);
    }

    /*
     * 3️⃣2️⃣ Ascending order arrangement
     * 📌 Scenario: Rearrange in ascending order
     * 🔹 Input: {1, -1, 3, -2, -3, 5}
     * 🔹 Output: {-1, -2, -3, 1, 3, 5}
     */
    @Test
    public void rearrangeAscendingOrder() {
        int[] arr = new int[] { 1, -1, 3, -2, -3, 5 };
        int[] result = new int[arr.length];
        int index = 0;
        for (int i : arr) {
            if (i < 0)
                result[index++] = i;
        }

        for (int i : arr) {
            if (i > 0)
                result[index++] = i;
        }
        System.out.println(Arrays.toString(result));
    }

    /*
     * 1️⃣ What Are the Types of Assertions in TestNG?
     * 📌 Question: What types of assertions are available in TestNG, and when
     * should you use them?
     * 
     * Hard Assertions (Assert): Stops execution when a condition fails.
     * 👉 Used when the test should fail immediately on error.
     * Soft Assertions (SoftAssert): Collects all failures and reports them at the
     * end.
     * 👉 Useful when you want to validate multiple fields in one test.
     */

    /*
     * 2️⃣ What Are GitLab CI/CD Variables and How Are They Used?
     * 📌 Question: What are GitLab environment variables, and how do you use them
     * in pipelines?
     * 
     * ✅ Answer:
     * Used to store dynamic/configurable data like API keys, URLs, credentials.
     * Set at:
     * ✔️ Project Level
     * ✔️ Group Level
     * ✔️ Inside .gitlab-ci.yml
     * script:
     * - echo "Running tests on $ENV"
     * 🎯 Key Takeaway: Variables help you write dynamic, reusable, and secure
     * pipelines.
     */

    /*
     * 3️⃣ What Is Equivalence Partitioning in Testing?
     * 📌 Question: Explain equivalence partitioning and how it improves test case
     * design.
     * 
     * ✅ Answer:
     * Equivalence Partitioning (EP) is a black-box test design technique used to
     * divide input data into logical partitions (called equivalence classes), where
     * all values in a class are expected to be treated the same by the system.
     * 
     * ✅ Valid partitions — Input values that the system should accept.
     * ❌ Invalid partitions — Input values that the system should reject or handle
     * as errors.
     * 
     * 🧠 Why Use Equivalence Partitioning?
     * Reduces redundancy — You don’t test every input, just one from each class.
     * Improves coverage — Ensures both valid and invalid inputs are considered.
     * Speeds up testing — Fewer test cases with the same effectiveness.
     * Promotes clean, structured test design — Easy to understand and maintain.
     * 
     * Technique that divides input data into valid and invalid partitions.
     * You test one value per partition instead of testing every input.
     * 👉 For an input range of 1–100:
     * Valid: 10, 50, 75
     * Invalid: -1, 101, "abc"
     * 🎯 Key Takeaway: Reduces the number of test cases without compromising
     * coverage.
     */

    /*
     * 4️⃣ Why Is Test Documentation Important in QA Projects?
     * 📌 Question: What types of test documentation are commonly maintained, and
     * why are they important?
     * 
     * ✅ Why Is Test Documentation Important?
     * Test documentation is critical in QA because it provides a clear, organized
     * record of the testing process. It helps ensure that:
     * Testing is structured, traceable, and repeatable
     * Team members and stakeholders are aligned
     * QA efforts are auditable and maintainable
     * Product quality is measurable and defensible
     * 📌 In short: Good documentation enables consistency, clarity, accountability,
     * and improvement.
     * 
     * | 📄 **Document** | 📌 **Purpose** |
     * | -------------------------------------------- |
     * ------------------------------------------------------------------------ |
     * | **1. Test Plan** | Outlines scope, approach, resources, and schedule of
     * testing activities. |
     * | **2. Test Strategy** | High-level document describing overall testing goals
     * and methodologies. |
     * | **3. Test Case Specification** | Describes individual test cases: inputs,
     * steps, expected results. |
     * | **4. Test Suite/Test Scenarios** | Groups of related test cases for
     * features or flows. |
     * | **5. Requirement Traceability Matrix (RTM)** | Maps test cases to
     * requirements to ensure full coverage. |
     * | **6. Bug/Defect Report** | Details bugs found during testing: steps,
     * severity, environment, etc. |
     * | **7. Test Data Document** | Defines the input data needed for test cases. |
     * | **8. Test Execution Report** | Logs actual test results and status
     * (pass/fail/blocked). |
     * | **9. Test Summary Report** | Summarizes testing outcomes at the end of a
     * test cycle or release. |
     * 
     * 
     * 🧠 Why These Documents Matter
     * ✅ Communication: Makes QA processes transparent to developers, managers, and
     * stakeholders.
     * ✅ Coverage & Traceability: Ensures all requirements are tested (via RTM).
     * ✅ Consistency: Testers can repeat and reproduce test scenarios with accuracy.
     * ✅ Audit Readiness: Useful for regulated industries (finance, healthcare,
     * etc.).
     * ✅ Knowledge Transfer: Onboarding new team members becomes easier.
     * ✅ Risk Reduction: Helps identify gaps and improve test planning.
     */
}
