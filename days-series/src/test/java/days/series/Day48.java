package days.series;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

import org.testng.annotations.Test;

public class Day48 {

    /*
     * 🅢7️⃣ Convert CamelCase to Snake_Case
     * 📌 Scenario: Standardize field names from "camelCase" to "snake_case" for
     * report outputs.
     * 
     * 🔹 Input: "testCaseName"
     * 🔹 Output: "test_case_name"
     */
    @Test
    public void convertCamelCasetoSnakeCase() {
        String str = "testCaseName";
        // Solution 1
        StringBuilder builder = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (Character.isUpperCase(ch))
                builder.append("_").append(Character.toLowerCase(ch));
            else
                builder.append(ch);
        }
        System.out.println(builder.toString());

        // Solution 2 - regex
        str = str.replaceAll("([A-Z])", "_$1").toLowerCase();
        System.out.println(str);
    }

    /*
     * 🅢8️⃣ Find the Longest Word in a Sentence
     * 📌 Scenario: Extract the longest word from a test case title or scenario
     * description.
     * 
     * 🔹 Input: "Automation testing improves code stabilities"
     * 🔹 Output: "stabilities"
     */
    @Test
    public void findLongestWord() {
        String input = "Automation testing improves code stabilities";
        String[] words = input.split("\\s+");
        // Solution 1
        System.out.println(Arrays.stream(words).max(Comparator.comparingInt(String::length)).orElse("None"));

        // Solution 2
        String longest = "";
        for (String word : words) {
            if (word.length() > longest.length())
                longest = word;
        }
        System.out.println(longest);
    }

    /*
     * 🅢9️⃣ Replace All Spaces with Hyphens
     * 📌 Scenario: Prepare strings for URL-safe slugs or file names.
     * 🔹 Input: "test case failed due to error"
     * 🔹 Output: "test-case-failed-due-to-error"
     */
    @Test
    public void replaceAllSpacesWithHypens() {
        String str = "test case failed due      to error    ";
        str = str.trim().replaceAll("\\s+", "-");
        System.out.println(str);
    }

}
