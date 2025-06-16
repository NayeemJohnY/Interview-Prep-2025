package days.series;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.testng.annotations.Test;

public class Day47 {

    /*
     * 🅢4️⃣ Remove All Non-Alphabetic Characters
     * 📌 Scenario: Clean a test description or input string by removing special
     * symbols and digits.
     * 🔹 Input: "He@llo123!"
     * 🔹 Output: "Hello"
     */
    @Test
    public void removeAllNonAlphabeticChars() {
        String str = "He@llo123!";
        str = str.replaceAll("[^a-zA-Z]", "");
        System.out.println(str);
    }

    /*
     * 🅢5️⃣ Find the First Non-Repeating Character
     * 📌 Scenario: Identify the first unique character in a test label or username
     * string.
     * 🔹 Input: "automation"
     * 🔹 Output: 'u'
     */
    @Test
    public void findFirstNonRepeatingChar() {
        String str = "automation";
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : str.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        for (Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println(entry.getKey());
                return;
            }
        }
        System.out.println(-1);
    }

    /*
     * 🅢6️⃣ Find All Duplicate Characters in a String
     * 📌 Scenario: Detect repeated characters in a log message for debugging
     * pattern.
     * 🔹 Input: "performance"
     * 🔹 Output: [e, r]
     */
    @Test
    public void findDuplicateChars() {
        String str = "performance";
        HashSet<Character> hashSet = new HashSet<>();
        List<Character> list = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            if (!hashSet.add(ch))
                list.add(ch);
        }
        System.out.println(list);
    }
}
