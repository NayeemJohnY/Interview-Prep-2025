package days.series;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day40 {

    /*
     * 3️⃣3️⃣ Find the Longest Subarray with Equal Number of 0s and 1s
     * 📌 Scenario: Convert all 0s to -1, then find the longest subarray with a
     * total sum of 0.
     * 
     * 🔹 Input: {0, 1, 0, 1, 0, 1, 1}
     * 🔹 Output: 6
     */
    @Test
    public void findLongestSubarrayWithEqual0s1s() {
        int[] arr = new int[] { 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1 };
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        int maxLength = 0, sum = 0, startIndex = 0;
        hashMap.put(0, -1);
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i] == 0 ? -1 : 1;
            if (sum == 0)
                maxLength = i + 1;
            if (hashMap.containsKey(sum)) {
                int len = i - hashMap.get(sum);
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
     * 3️⃣4️⃣ Sort an Array of 0s, 1s, and 2s Without Sorting Algo
     * 📌 Scenario: Sort an array containing only 0s, 1s, and 2s using constant
     * space. Dutch National Flag Algorithm
     * 🔹 Input: {2, 0, 2, 1, 1, 0}
     * 🔹 Output: {0, 0, 1, 1, 2, 2}
     */
    public void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] + arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }

    @Test
    public void sortArrayOf0s1s2s() {
        int[] arr = new int[] { 2, 0, 2, 1, 1, 0, 2, 1, 2, 1, 0, 0, 1 };
        int low = 0, mid = 0, high = arr.length - 1;
        while (mid <= high) {
            if (arr[mid] == 0) {
                swap(arr, low, mid);
                low++;
                mid++;
            } else if (arr[mid] == 1) {
                mid++;
            } else if (arr[mid] == 2) {
                swap(arr, mid, high);
                high--;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    /*
     * 3️⃣5️⃣ Segregate Even and Odd Numbers
     * 📌 Scenario: Move all even numbers to the beginning and odd numbers to the
     * end of the array.
     * 
     * 🔹 Input: {12, 34, 45, 9, 8, 90, 3}
     * 🔹 Output: {12, 34, 8, 90, 45, 9, 3}
     */
    @Test
    public void moveallEvenNumberstoBegining() {
        int[] arr = new int[] { 12, 34, 45, 9, 8, 90, 3 };
        int left = 0, right = arr.length - 1;
        while (left < right) {
            while (arr[left] % 2 == 0 && left < right) {
                left++;
            }
            while (arr[right] % 2 == 1 && left < right) {
                right--;
            }
            if (left < right) {
                swap(arr, left, right);
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    /*
     * 1️⃣ How Do You Skip a Test in TestNG?
     * 📌 Question: How do you skip test execution in TestNG conditionally or
     * unconditionally?
     * 
     * 1️⃣ Using the @Test(enabled = false) Annotation
     * 2️⃣ Using @Test with dependsOnMethods - if dependent test fails
     * 3️⃣ Using @BeforeMethod or @BeforeClass with Conditional Logic
     * 4️⃣ Using SkipException to Skip a Test
     * 5️⃣ Using @Test(groups = ...) with @BeforeMethod or @BeforeClass
     */
    @Test(enabled = false)
    public void testThatWillBeSkipped() {
        System.out.println("This test will be skipped.");
    }

    @Test
    public void testThatWillBeSkippedThrow() {
        throw new SkipException("Skipping this test because of a condition");
    }

    boolean skipTest = false; // Control flag to skip tests

    @BeforeMethod
    public void checkTestCondition() {
        if (skipTest) {
            throw new SkipException("Skipping the test based on condition");
        }
    }

    @Test
    public void testThatMayBeSkipped() {
        System.out.println("This test may be skipped.");
    }

    /*
     * 2️⃣ What Is Branch Protection in GitLab?
     * 📌 Question: What is branch protection in GitLab, and why is it important?
     * 
     * Protects branches like main or release from direct changes.
     * Enforces:
     * ✔️ Merge request approvals
     * ✔️ Pipeline success before merging
     * ✔️ Restricted push access
     */

    /*
     * 3️⃣ What Is Risk-Based Testing and When Should You Use It?
     * 📌 Question: What is risk-based testing, and how does it guide test
     * prioritization?
     * 
     * Risk-Based Testing (RBT) is a testing approach that prioritizes the testing
     * effort based on the risks associated with the application. In this approach,
     * risk refers to the likelihood and potential impact of a failure occurring in
     * different parts of the system. Rather than testing all functionalities
     * equally, RBT focuses on testing those areas that are most critical to the
     * system's success, based on their risk profile.
     * 
     * In RBT, the risk is typically assessed in terms of:
     * 
     * Probability of failure: How likely is it that a particular feature or part of
     * the system will fail?
     * 
     * Impact of failure: What is the severity of the failure if it occurs (e.g.,
     * does it affect a few users, or does it have major consequences like data loss
     * or security breaches)?
     * 
     * Prioritization?
     * Identify Risks: These can be technical risks (e.g., complexity of code,
     * integration points), business risks (e.g., critical business functions,
     * customer satisfaction), or environmental risks (e.g., hardware, third-party
     * services).
     * 
     * Assess and Prioritize Risks: Once risks are identified, each one is assessed
     * based on its likelihood of occurring and the impact it would have if it does
     * occur.
     * 
     * Prioritize Testing Based on Risk:
     * Allocate Resources Efficiently:
     */

    /*
     * 4️⃣ How Do You Handle Test Dependencies in TestNG?
     * 📌 Question: How can you create a test dependency in TestNG where one test
     * should run only after another passes?
     * 
     * In TestNG, you can manage test dependencies by using the dependsOnMethods or
     * dependsOnGroups attributes in the @Test annotation. This allows you to define
     * a relationship between tests so that one test will only execute if the
     * preceding one passes.
     * 
     * 
     * If a dependent test fails or is skipped, TestNG will skip any test that
     * depends on it. However, you can use the alwaysRun attribute to force a test
     * to run regardless of whether its dependencies pass or fail.
     * 
     * TestNG respects the order of test execution based on the dependsOnMethods or
     * dependsOnGroups, so dependent tests will always be executed in the correct
     * sequence.
     * 
     * Multiple depends @Test(dependsOnMethods = {"testLogin", "testSearch"})
     *  @Test(dependsOnMethods = {"testLogin", "testSearch"}, dependsOnGroups = "cart")
     */
    @Test(groups = "login")
    public void testLogin() {
        System.out.println("Running testLogin: Login functionality");
    }

    @Test(groups = "search")
    public void testSearch() {
        System.out.println("Running testSearch: Search functionality");
    }

    @Test(groups = "checkout")
    public void testAddToCart() {
        System.out.println("Running testAddToCart: Add to Cart functionality");
    }

    @Test(groups = "checkout")
    public void testPayment() {
        System.out.println("Running testPayment: Payment functionality");
    }

    @Test(dependsOnMethods = {"testLogin", "testSearch"}, dependsOnGroups = "checkout")
    public void testFinalizeOrder() {
        System.out.println("Running testFinalizeOrder: Finalize Order functionality");
        // This test depends on both login/search methods and the entire checkout group
    }

}
