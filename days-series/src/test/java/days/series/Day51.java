package days.series;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

public class Day51 {

    /*
     * 🅢1️⃣6️⃣ Check If a String Contains Only Unique Characters
     * 📌 Scenario: Validate test case IDs to ensure uniqueness in naming
     * conventions.
     * 🔹 Input: "abcdef"
     * 🔹 Output: true
     */
    @Test
    public void checkStringContainsUniqueChars() {
        String str = "abcdef";

        HashSet<Character> characters = new HashSet<>();
        for (char c : str.toCharArray()) {
            if (!characters.add(c)) {
                System.out.println(false);
                return;
            }
        }
        System.out.println(true);
    }

    /*
     * 🅢1️⃣7️⃣ Convert a Sentence to Title Case
     * 📌 Scenario: Standardize test case names or log labels into proper title case
     * format.
     * 🔹 Input: "java is awesome"
     * 🔹 Output: "Java Is Awesome"
     */
    @Test
    public void convertSentencetoTitleCase() {
        String str = "java is awesome Language";
        str = Arrays.stream(str.split("\\s+")).map(
                s -> Character.toUpperCase(s.charAt(0)) + s.substring(1)).collect(Collectors.joining(" "));
        System.out.println(str);
    }

    /*
     * 🅢1️⃣8️⃣ Check if a String is a Rotation of Another
     * 📌 Scenario: Validate cyclic shifts in text – useful in encoding or security
     * checks.
     * 🔹 Input: "abcd", "cdab"
     * 🔹 Output: true
     * 
     */
    @Test
    public void validateIfStringisRotationOfAnother() {
        String str1 = "abcd";
        String str2 = "cdab";
        System.out.println(str1.length() == str2.length() && str1.concat(str1).contains(str2));
    }
}


