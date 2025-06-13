package days.series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Day33 {
    /*
     * 9️⃣ Find the Difference Between Maximum and Minimum Elements
     * 📌 Scenario: You want to find the spread of values in test execution times.
     * 🔹 Input: {10, 3, 5, 6, 8}
     * 🔹 Output: 7 (Max = 10, Min = 3 → 10 - 3)
     */
    @Test
    public void findDifferenceBetweenMaxAndMinElement() {
        int[] array = new int[] { 10, 3, 5, 6, 8 };
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int i : array) {
            if (i > max) {
                max = i;
            } else if (i < min) {
                min = i;
            }
        }
        System.out.println(max - min);
    }

    /*
     * 🔟 Replace Each Element with the Greatest Element on Right
     * 📌 Scenario: For dashboards, display future potential values by replacing
     * each element with the max on its right.
     * 🔹 Input: {17, 18, 5, 4, 6, 1}
     * 🔹 Output: {18, 6, 6, 6, 1, -1}
     */
    @Test
    public void replaceEachElementWithGreatestElementOnRight() {
        int[] array = new int[] { 17, 18, 5, 4, 6, 1 };
        int max = -1;
        for (int i = array.length - 1; i >= 0; i--) {
            int temp = array[i];
            array[i] = max;
            max = Math.max(max, temp);
        }
        System.out.println(Arrays.toString(array));
    }

    /*
     * 1️⃣1️⃣ Find All Pairs with a Given Sum
     * 📌 Scenario: During order analysis, identify product prices that together sum
     * to a target value.
     * 🔹 Input: {1, 5, 7, -1, 5}, Sum = 6
     * 🔹 Output: [(1,5), (7,-1), (1,5)]
     */
    @Test
    public void findAllParisWithSum() {
        int[] array = new int[] { 1, 5, 7, -1, 5 };
        int sum = 6;

        // Solution 1
        List<List<Integer>> pairs = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] + array[j] == sum)
                    pairs.add(Arrays.asList(Math.max(array[i], array[j]), Math.min(array[i], array[j])));
            }
        }
        System.out.println(pairs);

        // Solution 2 - Single for loop
        pairs = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();
        for (int num : array) {
            int diff = sum - num;
            if (numberList.contains(diff)) {
                pairs.add(Arrays.asList(Math.max(diff, num), Math.min(diff, num)));
            }
            numberList.add(num);
        }
        System.out.println(pairs);
    }

    /*
     * 1️⃣2️⃣ Count Elements Strictly Greater Than X
     * 📌 Scenario: In performance results, count how many test cases took longer
     * than a given threshold.
     * 🔹 Input: {2, 5, 8, 1, 10}, x = 5
     * 🔹 Output: 2
     */
    @Test
    public void countElementGreaterThanX() {
        int[] array = new int[] { 2, 5, 8, 1, 10 };
        int x = 5;
        int count = 0;
        for (int i : array) {
            if (i > x)
                count++;
        }
        System.out.println(count);

        // Solution 2 - One liner
        System.out.println(Arrays.stream(array).filter(n -> n > x).count());
    }

    /*
     * 1️⃣ What Is Defect Severity vs Defect Priority?
     * 📌 Question: What’s the difference between severity and priority in defect
     * reporting?
     * 
     * ✅ Answer:
     * Severity: Indicates impact on the application (technical aspect).
     * 👉 Example: App crash = High severity.
     * Priority: Reflects urgency to fix the bug (business aspect).
     * 👉 Example: Typo on homepage = Low severity but High priority.
     * 🎯 Key Takeaway: Severity is about how severe the bug is, priority is about
     * how soon it should be fixed.
     */

    /*
     * 2️⃣ What Is @DataProvider in TestNG and Why Is It Useful?
     * 📌 Question: How do you use @DataProvider in TestNG for data-driven testing?
     * 
     * @DataProvider is a TestNG annotation used to pass multiple sets of data to a
     * test method.
     * It enables data-driven testing, where the same test runs multiple times with
     * different inputs.
     * 
     * ✅ Why Is It Useful?
     * 🧪 Automates repetitive test cases with different data
     * 🔄 Reduces code duplication
     * 📈 Improves test coverage
     * 📊 Perfect for testing forms, calculations, and APIs with various inputs
     */
    @DataProvider(name = "dataName")
    public Object[][] dataMethod() {
        return new Object[][] {
                { "username1", "password1" },
                { "username2", "password2" },
        };
    }

    @Test(dataProvider = "dataName")
    public void loginTest(String username, String password) {
        System.out.println("Testing with: " + username + " / " + password);
    }

    /*
     * 3️⃣ What Is a GitLab Pipeline and What Is Its Purpose?
     * 📌 Question: What is a GitLab CI/CD pipeline and how does it help in test
     * automation?
     * 
     * A GitLab CI/CD pipeline is a series of automated steps (jobs) that run in
     * response to code changes, typically defined in a .gitlab-ci.yml file.
     * 
     * These steps can include:
     * Building the code
     * Running tests
     * Deploying to environments
     * Sending notifications
     * 
     * | Goal | Description |
     * | ------------------------ |
     * ------------------------------------------------------------------------- |
     * | ✅ **Automation** | Automates the software development lifecycle from build
     * to deployment |
     * | ✅ **Consistency** | Ensures all code changes go through the same quality
     * gates |
     * | ✅ **Speed & Feedback** | Gives quick feedback on code quality and
     * functionality via test results |
     * | ✅ **Continuous Testing** | Automatically runs unit, integration, or UI
     * tests with every commit/push |
     * | ✅ **Deployment Ready** | Enables continuous deployment to dev, staging, or
     * production environments |
     * 
     * 
     * ⚙️ How It Helps in Test Automation
     * 🧪 Automatically runs your test suites every time you push or merge code
     * 🧾 Reports test results in GitLab interface
     * 🛡️ Prevents bad code from being merged if tests fail
     */

    /*
     * 4️⃣ What Is Regression Testing and When Should You Do It?
     * 📌 Question: What is regression testing, and when should it be performed?
     * 
     * Regression testing is a type of software testing that ensures new code
     * changes haven’t broken existing functionality.
     * Regression testing ensures that new changes didn’t break existing
     * functionality.
     * 
     * ✅ When Should You Do Regression Testing?
     * | Situation | Why It’s Needed |
     * | ------------------------------------------- |
     * --------------------------------------------- |
     * | 🔧 After bug fixes | Ensure the fix didn’t break something else |
     * | ✨ After adding new features | Make sure existing modules still work |
     * | 🔁 After performance or refactoring changes | Verify unchanged logic still
     * behaves the same |
     * | 📦 Before major releases | Confirm the system is stable |
     * 
     * 🔄 How Is It Performed?
     * 🔹 Run existing test cases (manual or automated)
     * 🔹 Focus on impacted areas and core functionalities
     * 🔹 Often done via automated regression suites
     * 
     * Benefits of Regression Testing
     * ✅ Prevents unexpected bugs from reaching production
     * ✅ Ensures stability after updates
     * ✅ Builds confidence in code changes
     */
}
