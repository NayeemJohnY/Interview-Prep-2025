package days.series;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Day50 {

    /*
     * 🅢1️⃣3️⃣ Validate if a String is Numeric Only
     * 📌 Scenario: Validate if the input string contains only digits (e.g., for
     * phone number, OTP).
     * 🔹 Input: "123456"
     * 🔹 Output: true
     */
    @Test(dataProvider = "StringNum")
    public void validateIfStringIsNumericOnly(String str) {
        // Solution 1
        try {
            Long.parseLong(str);
            System.out.println(true);
        } catch (NumberFormatException e) {
            System.out.println(false);
        }

        // Solution 2
        System.out.println(str.replaceAll("\\d+", "").isEmpty());

        // Solution 3
        System.out.println(str.matches("\\d+"));
    }

    @DataProvider(name = "StringNum")
    public Object[] phoneNumbers() {
        return new Object[] {
                "123456", "9876543210", "asdfdsfssa", "3456eedw@"
        };
    }

    /*
     * 🅢1️⃣4️⃣ Find the Longest Palindromic Substring
     * 📌 Scenario: Frequently asked in interviews – identify the longest substring
     * that’s a palindrome.
     * 🔹 Input: "babad"
     * 🔹 Output: "bab" or "aba"
     */
    public String expandFromCenter(String str, int left, int right) {
        while (left >= 0 && right < str.length() && str.charAt(left) == str.charAt(right)) {
            left--;
            right++;
        }
        return str.substring(left + 1, right);
    }

    @Test
    public void findLongestPalindromicSubString() {
        String str = "banana";
        String longestSubstring = "";
        for (int i = 0; i < str.length(); i++) {
            String substring1 = expandFromCenter(str, i, i);
            String substring2 = expandFromCenter(str, i, i + 1);
            String subString = substring1.length() > substring2.length() ? substring1 : substring2;
            if (subString.length() > longestSubstring.length()) {
                longestSubstring = subString;
            }
        }
        System.out.println(longestSubstring);
    }

    /*
     * 🅢1️⃣5️⃣ Remove Duplicate Words from a Sentence
     * 📌 Scenario: Filter out repeating words in test summaries or logs.
     * 🔹 Input: "Java is is great great"
     * 🔹 Output: "Java is great"
     */
    @Test
    public void removeDuplicateWordsFromSentence() {
        String str = "Java is is great great";
        // Solution 1
        System.out.println(Arrays.stream(str.split("\\s+")).distinct().collect(Collectors.joining(" ")));

        // Solution 2
        System.out.println(String.join(" ", new LinkedHashSet<>(Arrays.asList(str.split("\\s+")))));
    }

}

