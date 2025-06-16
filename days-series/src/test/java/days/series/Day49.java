package days.series;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

public class Day49 {

    /*
     * 🅢🔟 Check if Two Strings are Anagrams
     * 📌 Scenario: Useful in validating dynamic identifiers or comparing test
     * labels with same letters.
     * 🔹 Input: "listen", "silent"
     * 🔹 Output: true
     */
    @Test
    public void checkIfTWoStringsAreAnagram() {
        String str1 = "listen";
        String str2 = "silent";

        if (str1.length() != str2.length()) {
            System.out.println(false);
            return;
        }

        // Soltuion 1
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();

        Arrays.sort(ch1);
        Arrays.sort(ch2);

        System.out.println(Arrays.equals(ch1, ch2));

        // Solution 2
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (char ch : str1.toCharArray()) {
            hashMap.put(ch, hashMap.getOrDefault(ch, 0) + 1);
        }

        for (char ch : str2.toCharArray()) {
            if (!hashMap.containsKey(ch)) {
                System.out.println(false);
                return;
            }

            hashMap.put(ch, hashMap.get(ch) - 1);
            if (hashMap.get(ch) == 0)
                hashMap.remove(ch);
        }
        System.out.println(hashMap.isEmpty());
    }

    /*
     * 🅢1️⃣1️⃣ Capitalize First Letter of Each Word
     * 📌 Scenario: Normalize test case titles or labels for UI readability.
     * 🔹 Input: "automate your testing journey"
     * 🔹 Output: "Automate Your Testing Journey"
     */
    @Test
    public void capitalizeFirstLetter() {
        String str = "automate your testing journey";
        String[] words = str.split("\\s+");
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            builder.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        System.out.println(builder.toString());
    }

    /*
     * 🅢1️⃣2️⃣ Compress a String Using Character Counts
     * 📌 Scenario: Useful in summary logs: convert "aabcccccaaa" to "a2b1c5a3".
     * 🔹 Input: "aabcccccaaa"
     * 🔹 Output: "a2b1c5a3"
     */
    @Test
    public void compressStringWithCharCount() {
        String str = "aabcccccaaa";
        StringBuilder builder = new StringBuilder();
        int count = 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                count++;
            } else {
                builder.append(str.charAt(i - 1)).append(count);
                count = 1;
            }
        }
        builder.append(str.charAt(str.length() - 1)).append(count);
        System.out.println(builder.toString());
    }
}
