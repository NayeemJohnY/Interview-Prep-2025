package days.series;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

public class Day32 {

    /*
     * 5️⃣ Find All Elements That Appear More Than Once
     * 📌 Scenario: Identify duplicate values in an integer array.
     * 🔹 Input: {4, 3, 2, 7, 8, 2, 3, 1}
     * 🔹 Output: [2, 3]
     */
    @Test
    public void findDuplocateValues() {
        int[] array = new int[] { 4, 3, 2, 7, 8, 2, 3, 1 };
        HashSet<Integer> uniqueSet = new HashSet<>();
        // Solution 1 using Hashset + Stream
        System.out.println(Arrays.stream(array).filter(num -> !uniqueSet.add(num)).boxed().toList());
        // Solution 2 using Map + Stream
        System.out.println(
                Arrays.stream(array).boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream().filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey).toList());
    }

    /*
     * 6️⃣ Left Rotate an Array by 1 Position
     * 📌 Scenario: Shift all elements to the left by 1 and place the first element
     * at the end.
     * 🔹 Input: {1, 2, 3, 4, 5}
     * 🔹 Output: {2, 3, 4, 5, 1}
     */
    public void reverse(int[] array, int start, int limit) {
        while (start < limit) {
            int temp = array[start];
            array[start++] = array[limit];
            array[limit--] = temp;
        }
    }

    @Test
    public void leftRotateArray() {
        int[] array = new int[] { 1, 2, 3, 4, 5 };
        int k = 1;
        // Solution 1
        k %= array.length;
        for (int i = 0; i < k; i++) {
            int temp = array[0];
            int j = 0;
            for (j = 1; j < array.length; j++) {
                array[j - 1] = array[j];
            }
            array[array.length - 1] = temp;
        }
        System.out.println(Arrays.toString(array));

        // Solution 2
        array = new int[] { 1, 2, 3, 4, 5 };
        reverse(array, 0, array.length - 1);
        reverse(array, 0, array.length - k - 1);
        reverse(array, array.length - k, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    /*
     * 7️⃣ Count Number of Even and Odd Integers
     * 📌 Scenario: Determine how many even and odd numbers exist in the array.
     * 🔹 Input: {2, 5, 8, 7, 4}
     * 🔹 Output: Even: 3, Odd: 2
     */
    @Test
    public void CountEvenOddNUmbers() {
        // Solution 1
        int[] array = new int[] { 2, 5, 8, 7, 4 };
        int evenCount = 0;
        for (int i : array) {
            if (i % 2 == 0)
                evenCount++;
        }
        System.out.println("Even: " + evenCount + ", Odd: " + (array.length - evenCount));

        // Solution 2
        long countEven = Arrays.stream(array).filter(n -> n % 2 == 0).count();
        System.out.println("Even: " + countEven + ", Odd: " + (array.length - countEven));
    }

    /*
     * 8️⃣ Reverse an Array In-Place
     * 📌 Scenario: Reverse the elements of an array without using extra space.
     * 🔹 Input: {1, 2, 3, 4}
     * 🔹 Output: {4, 3, 2, 1}
     * 
     * a = a+b; 10+20
     * b = a-b = 30 -20
     * a = a -b ; 30-10
     */
    @Test
    public void reverseArrayInPlace() {
        int[] array = new int[] { 1, 2, 3, 4, 5 };
        int left = 0, right = array.length - 1;
        while (left < right) {
            // int temp = array[left];
            // array[left++] = array[right];
            // array[right--] = temp;
            array[left] += array[right];
            array[right] = array[left] - array[right];
            array[left] -= array[right];
            left++;
            right--;
        }
        System.out.println(Arrays.toString(array));
    }

    /*
     * 1️⃣ What Are Entry and Exit Criteria in Software Testing?
     * 📌 Question: What do you mean by entry and exit criteria in a test plan?
     * 
     * In software testing, Entry Criteria and Exit Criteria are essential
     * components of the Test Plan. They define the conditions that must be met
     * before starting a test and the conditions that determine when the test can be
     * considered complete, respectively.
     * 
     * Entry Criteria refers to the conditions that must be satisfied before testing
     * can begin. Essentially, it’s the checklist of prerequisites that need to be
     * in place to ensure that testing is performed under the best conditions. It
     * serves as a "gate" or "pre-test condition".
     * 
     * Examples of Entry Criteria:
     * Test environment is configured and ready.
     * The build version to be tested is stable (e.g., no critical issues).
     * Test cases are designed, reviewed, and signed off.
     * The test data is available for execution.
     * Dependencies (third-party services, tools, etc.) are ready.
     * 
     * Exit Criteria refers to the conditions that must be satisfied before the
     * testing phase is concluded. These criteria help determine when a test or
     * testing phase can be considered complete, ensuring that the testing has been
     * thorough and meets the defined objectives
     * 
     * Examples of Exit Criteria:
     * All planned test cases have been executed.
     * All high-priority defects are resolved or mitigated.
     * The test coverage meets the predefined targets (e.g., 90% of the requirements
     * have been tested).
     * The software meets quality standards or acceptance criteria.
     * The agreed-upon pass/fail criteria for test cases have been met.
     * No critical or major defects remain unresolved.
     * Test reports are completed, including defect logs, coverage reports, and any
     * other documentation.
     */

    /*
     * 2️⃣ What Is the Purpose of groups and priority in TestNG?
     * 📌 Question: How are groups and priority used in TestNG to manage test
     * execution?
     * 
     * Groups: Categorize tests logically (e.g., regression, smoke).
     * Priority: Determines the order of execution. Lower priority runs first.
     */
    @Test(groups = "smoke", priority = 5)
    public void testLogin() {
        System.out.println("Running smoke test: Login");
    }

    @Test(groups = "regression", priority = 2)
    public void testSearch() {
        System.out.println("Running regression test: Search");
    }

    @Test(groups = "smoke", priority = 3)
    public void testAddToCart() {
        System.out.println("Running smoke test: Add to Cart");
    }

    @Test(groups = "integration", priority = 4)
    public void testPayment() {
        System.out.println("Running integration test: Payment");
    }

    /*
     * 3️⃣ What Is a Merge Request in GitLab?
     * 📌 Question: How does a merge request differ from a push in GitLab?
     * 
     * ✅ Answer:
     * Push: Directly sends your code to the remote branch.
     * Merge Request (MR): A collaborative review before merging code into the main
     * branch.
     * 👉 MRs include code review, CI checks, and approvals.
     * 🎯 Key Takeaway: Merge requests improve code quality and reduce defects
     * through collaboration.
     */

    /*
     * 4️⃣ What Is Smoke vs Sanity Testing?
     * 📌 Question: How is smoke testing different from sanity testing?
     * 
     * ✅ Answer:
     * Smoke Testing: Basic health check to ensure the build is stable.
     * 👉 Example: App launches, main pages load.
     * 🔥 Smoke Testing
     * Purpose: To verify the basic functionality of a software build.
     * When: Conducted after a new build is received.
     * Goal: To check whether the most critical functionalities of the application
     * are working and if the build is stable enough for further testing.
     * 
     * ✅ Broad coverage, shallow testing
     * ✅ Done before detailed functional or regression testing
     * ✅ Example: Does the app launch? Can users log in?
     * 🧪 Think of it as: "Does the app even turn on?"
     * 
     * 🧠 Sanity Testing: Quick test of specific functionality after minor changes.
     * 👉 Example: Verifying just the login module after a login bug fix.
     * urpose: To verify specific functionality or bug fixes after changes.
     * When: Performed after receiving a slightly modified build, especially after
     * bug fixes or minor changes.
     * Goal: To confirm that the changes work as expected and didn’t break anything
     * important.
     * 
     * ✅ Narrow coverage, deep testing
     * ✅ Focused on particular modules or features
     * ✅ Example: Was the password reset bug fixed properly?
     * 🧪 Think of it as: "Does this specific feature behave correctly now?"
     * 
     * 🎯 Key Takeaway: Smoke = Build verification. Sanity = Bug fix validation.
     */

}
