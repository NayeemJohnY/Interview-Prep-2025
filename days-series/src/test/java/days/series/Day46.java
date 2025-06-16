package days.series;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

public class Day46 {

    /*
     * 🅢1️⃣ Reverse a String Without Using Inbuilt Reverse
     * 📌 Scenario: Build a custom reverse logic for an input string without using
     * library methods.
     * 🔹 Input: "automation"
     * 🔹 Output: "noitamotua"
     */
    @Test
    public void reverString() {
        String str = "automation";
        StringBuilder builder = new StringBuilder();
        for (int index = str.length() - 1; index >= 0; index--) {
            builder.append(str.charAt(index));
        }
        System.out.println(builder.toString());
    }

    /*
     * 🅢2️⃣ Check if a String is a Palindrome (Case-Insensitive)
     * 📌 Scenario: Validate if a test case name or identifier reads the same
     * forward and backward.
     * 🔹 Input: "Madam"
     * 🔹 Output: true
     */
    @Test
    public void checkStringisPalindrome() {
        String str = "Madam";
        str = str.toLowerCase();
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(right--) != str.charAt(left++)) {
                System.out.println(false);
                return;
            }
        }
        System.out.println(true);
    }

    /*
     * 🅢3️⃣ Count Occurrence of Each Character
     * 📌 Scenario: Analyze a test string to identify how frequently each character
     * appears.
     * 
     * 🔹 Input: "hello"
     * 🔹 Output: {h=1, e=1, l=2, o=1}
     * 
     */
    @Test
    public void countOccurrence() {
        String str = "hello";

        Map<Character, Long> map = str.chars().mapToObj(ch -> (char) ch)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
        System.out.println(map);

        // Solution 2
        LinkedHashMap<Character, Integer> freqMap = new LinkedHashMap<>();
        for (char ch : str.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }
        System.out.println(freqMap);

    }
}
