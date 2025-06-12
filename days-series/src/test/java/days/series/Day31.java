package days.series;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Day31 {

    /*
     * 1️⃣ Find the Second Largest Number in an Array
     * 📌 Scenario: You want to find the second largest number in an unsorted array
     * without sorting it.
     * 🔹 Input: {5, 7, 1, 9, 4}
     * 🔹 Output: 7
     */
    @Test
    public void findSecndLargestNumber() {
        int[] array = new int[] { 5, 7, 1, 9, 4 };

        // Solution 1
        int max = Integer.MIN_VALUE, secondMax = Integer.MIN_VALUE;
        for (int num : array) {
            if (num > max) {
                secondMax = max;
                max = num;
            } else if (num > secondMax) {
                secondMax = num;
            }
        }

        System.out.println(secondMax);

        // Solution 2
        int k = 2;
        Arrays.sort(array);
        System.out.println(array[array.length - k]);

        // Solution 3
        Arrays.stream(array).distinct().boxed()
                .sorted(Collections.reverseOrder()).skip(k - 1).findFirst().ifPresent(System.out::println);

        // Solution 4
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int num : array) {
            priorityQueue.add(num);
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        }
        System.out.println(priorityQueue.peek());
    }

    /*
     * 2️⃣ Move All Zeros to the End of the Array
     * 📌 Scenario: Rearrange the array so that all 0s are moved to the end while
     * maintaining the order of non-zero elements.
     * 🔹 Input: {0, 1, 0, 3, 12}
     * 🔹 Output: {1, 3, 12, 0, 0}
     */
    @Test
    public void moveAllZerostoEnd() {
        int[] array = new int[] { 0, 1, 0, 3, 12 };
        int index = 0;
        // Solution 1
        for (int num : array) {
            if (num != 0) {
                array[index++] = num;
            }
        }
        while (index < array.length) {
            array[index++] = 0;
        }

        System.out.println(Arrays.toString(array));
    }

    /*
     * 3️⃣ Check if Array is Sorted (Ascending)
     * 📌 Scenario: Validate if the input array is sorted in non-decreasing order.
     * 🔹 Input: {1, 2, 3, 4, 5}
     * 🔹 Output: true
     */
    @Test
    public void checkIsArraySorted() {
        List<int[]> list = List.of(new int[] { 1, 2, 3, 4, 5 }, new int[] { 4, 3, 8, 9, 5 });
        for (int[] array : list) {
            boolean isSorted = true;
            for (int i = 1; i < array.length; i++) {
                if (array[i] < array[i - 1]) {
                    isSorted = false;
                    break;
                }
            }
            System.out.println(isSorted);
        }
    }

    /*
     * 4️⃣ Find Missing Number in a Range from 1 to N
     * 📌 Scenario: An array contains numbers from 1 to N with one missing. Find the
     * missing number.
     * 🔹 Input: {1, 2, 4, 5}
     * 🔹 Output: 3
     */
    @Test
    public void findMissingNumberInRange() {
        int[] array = new int[] { 1, 2, 4, 5 };
        int n = 5;
        int sum = 0;
        for (int i : array)
            sum += i;

        System.out.println((n * (n + 1) / 2) - sum);

        // 1-liner using stream
        System.out.println((n * (n + 1) / 2) - Arrays.stream(array).sum());
    }

    /*
     * 1️⃣ What Is the Difference Between Verification and Validation in Software
     * Testing?
     * 📌 Question: How do you differentiate verification from validation in the
     * testing process?
     * 
     * 🔍 Verification – “Are we building the product right?”
     * Definition: It is the process of evaluating work products (documents, design,
     * code, etc.) to check whether the software meets the specified requirements.
     * Focus: Process-oriented
     * Activities Include:
     * Reviews
     * Inspections
     * Walkthroughs
     * Static analysis
     * Timing: Done early in the development lifecycle (before actual testing
     * starts)
     * Objective: Ensure the product is being developed according to the
     * requirements and standards.
     * 
     * ✅ Validation – “Are we building the right product?”
     * Definition: It is the process of evaluating the actual software product to
     * check whether it meets the business needs and expectations.
     * 
     * Focus: Product-oriented
     * Activities Include:
     * Functional testing
     * Integration testing
     * System testing
     * User acceptance testing (UAT)
     * Timing: Done after verification, usually during or after development
     * Objective: Ensure the final product is fit for purpose.
     */

    /*
     * 2️⃣ What Are the Common TestNG Annotations and Their Execution Order?
     * 📌 Question: Explain a few TestNG annotations and their order of execution.
     * 
     * | Annotation | Description |
     * | --------------- |
     * -------------------------------------------------------------- |
     * | `@BeforeSuite` | Runs **once** before all tests in the suite |
     * | `@BeforeTest` | Runs before any test method under `<test>` tag in
     * `testng.xml` |
     * | `@BeforeClass` | Runs **once** before the first method in the current class
     * |
     * | `@BeforeMethod` | Runs **before every** `@Test` method |
     * | `@Test` | The actual **test case** method |
     * | `@AfterMethod` | Runs **after every** `@Test` method |
     * | `@AfterClass` | Runs **once** after all test methods in the current class |
     * | `@AfterTest` | Runs after all test methods under `<test>` tag in
     * `testng.xml` |
     * | `@AfterSuite` | Runs **once** after all tests in the suite |
     */
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method");
    }

    @Test
    public void testOne() {
        System.out.println("Test One");
    }

    @Test
    public void testTwo() {
        System.out.println("Test Two");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite");
    }

    /*
     * 3️⃣ How Is GitLab Different from Git?
     * 📌 Question: What is the core difference between Git and GitLab?
     * 
     * Git is a distributed version control system that tracks changes in source
     * code.
     * GitLab is a web-based DevOps platform that uses Git for version control and
     * adds tools like CI/CD, issue tracking, and project management.
     * Git is the engine; GitLab is the full-featured interface and workflow built
     * around it.
     */

    /*
     * 4️⃣ How Do You Clone a GitLab Repo and Create a New Branch?
     * 📌 Question: How do you clone a GitLab project and switch to a new feature
     * branch?
     * 
     * git clone https://gitlab.com/nayeemjohny-group/my-first-gitlab-project.git
     * cd my-first-gitlab-project
     * it checkout -b "feature-branch-1"
     */

}
