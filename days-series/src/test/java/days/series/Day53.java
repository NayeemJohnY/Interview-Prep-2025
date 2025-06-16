package days.series;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Day53 {

    /*
     * 🅢2️⃣2️⃣ Check If Two Strings are One Edit Distance Apart
     * 📌 Scenario: Validate whether two strings differ by just one insert, delete,
     * or replace.
     * 🔹 Input: "test", "tent"
     * 🔹 Output: true
     */
    @Test(dataProvider = "testdata")
    public void checkTwoStringsAreWithOneChange(String s, String t) {
        if (Math.abs(s.length() - t.length()) > 1) {
            System.out.println(false);
            return;
        }
        int i = 0, j = 0, edits = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) != t.charAt(j)) {
                if (++edits > 1) {
                    System.out.println(false);
                    return;
                }
                if (s.length() > t.length())
                    i++;
                else if (t.length() > s.length())
                    j++;
                else {
                    i++;
                    j++;
                }
            } else {
                i++;
                j++;
            }
        }
        System.out.println(true);
    }

    @DataProvider(name = "testdata")
    public Object[][] testdata() {
        return new Object[][] {
                { "test", "tent" },
                { "test", "tests" },
                { "test", "tst" },
                { "test", "test" },
                { "test", "toast" },
                { "test", "feet" }
        };
    }

    /*
     * 🅢2️⃣3️⃣ Count Occurrences of a Word in a Sentence
     * 📌 Scenario: Useful in test logs or counting API calls.
     * 🔹 Input: "API testing requires API validation", "API"
     * 🔹 Output: 2
     */
    @Test
    public void countOccurrencesOfWordInSentence() {
        String str = "API testing requires API validation";
        String word = "API";
        // Solution 1
        System.out.println(Arrays.stream(str.split("\\s+")).filter(s -> s.equals(word)).count());
        // Solution 2
        int count = 0;
        for (String string : str.split("\\s+")) {
            if (string.equals(word))
                count++;
        }
        System.out.println(count);

    }

    /*
     * 🅢2️⃣4️⃣ Run-Length Encoding
     * 📌 Scenario: Compress log text: "aaabbcccc" → "a3b2c4"
     * 🔹 Input: "aaabbcccc"
     * 🔹 Output: "a3b2c4"
     */
    @Test
    public void runLengthEncoding() {
        String str = "aaabbcccc";
        StringBuilder builder = new StringBuilder();
        int count = 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1))
                count++;
            else {
                builder.append(str.charAt(i - 1)).append(count);
                count = 1;
            }
        }
        builder.append(str.charAt(str.length() - 1)).append(count);
        System.out.println(builder.toString());
    }

    /*
     * 🅢2️⃣5️⃣ Check if Two Strings are Isomorphic
     * 📌 Scenario: Validate if two strings follow same pattern (e.g., "egg" and
     * "add").
     * 🔹 Input: "foo", "bar"
     * 🔹 Output: false
     */
    @Test
    public void checkTwoStringsAreIsomorphic() {
        HashMap<Character, Character> hashMap = new HashMap<>();
        HashSet<Character> hashSet = new HashSet<>();
        String s1 = "add";
        String s2 = "egg";
        if (s1.length() != s2.length()) {
            System.out.println(false);
            return;
        }
        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i), c2 = s2.charAt(i);
            if (hashMap.containsKey(c1)) {
                if (hashMap.get(c1) != c2) {
                    System.out.println(false);
                    return;
                }
            } else {
                if (hashSet.contains(c2)) {
                    System.out.println(false);
                    return;
                }
                hashMap.put(c1, c2);
                hashSet.add(c2);
            }
        }
        System.out.println(true);
    }
}
