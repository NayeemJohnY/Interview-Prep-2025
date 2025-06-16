package days.series;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class Day52 {

    /*
     * 🅢1️⃣9️⃣ Remove Extra Spaces Between Words
     * 📌 Scenario: Clean up test case logs or form inputs by trimming multiple
     * spaces.
     * 🔹 Input: " Hello World "
     * 🔹 Output: "Hello World"
     * 
     */
    @Test
    public void removeExtraSpacedBetweenWords() {
        String str = " Hello    World  Java         Langugage       ab cd  ef   ";
        System.out.println(str.trim().replaceAll("\\s+", " "));
    }

    /*
     * 🅢2️⃣0️⃣ Count Vowels and Consonants
     * 📌 Scenario: Validate or analyze user input for character types in form
     * validation.
     * 🔹 Input: "automation"
     * 🔹 Output: Vowels: 6, Consonants: 4
     */
    @Test
    public void countVowelsAndConsonants() {
        String str = "automation";
        int vowels = 0, consonants = 0;
        for (char ch : str.toLowerCase().toCharArray()) {
            if (!Character.isLetter(ch))
                continue;
            if ("aeiou".indexOf(ch) != -1) {
                vowels++;
            } else {
                consonants++;
            }
        }
        System.out.println(vowels);
        System.out.println(consonants);
    }

    /*
     * 🅢2️⃣1️⃣ Find All Palindromic Substrings
     * 📌 Scenario: Analyze all symmetrical patterns in a string – often used in log
     * pattern detection.
     * 🔹 Input: "ababa"
     * 🔹 Output: ["a", "b", "a", "b", "a", "aba", "bab", "aba", "ababa"]
     */
    public void expandFromCenter(String str, int left, int right, List<String> result) {
        while (left >= 0 && right < str.length() && str.charAt(left) == str.charAt(right)) {
            result.add(str.substring(left, right + 1));
            left--;
            right++;
        }

    }

    @Test
    public void findAllPalindromicsubStrings() {
        String str = "ababa";
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            expandFromCenter(str, i, i, list);
            expandFromCenter(str, i, i + 1, list);
        }
        System.out.println(list);
    }
}
