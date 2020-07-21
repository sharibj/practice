package tdd.wildcard.matching;

public class WildcardMatcher {
    public boolean isMatch(String s, String p) {
        if (!p.contains("*")) {
            return equalStrings(s, p);
        }
        p = replaceMultipleStarOccurrenceWithJustOne(p);
        if (p.startsWith("*")) {
            return isMatchWhenStartsWithStar(s, p);
        }
        if (s.isEmpty() && !p.isEmpty()) {
            return false;
        }
        if (p.startsWith("?")) {
            return isMatch(s.substring(1), p.substring(1));
        }
        if (s.charAt(0) != p.charAt(0)) {
            return false;
        } else {
            return isMatch(s.substring(1), p.substring(1));
        }
    }

    public boolean isMatchWhenStartsWithStar(String str, String pattern) {
        if (pattern.length() == 1) {
            return true;
        }
        String patternSubstring = pattern.substring(1);
        if (patternSubstring.contains("*")) {
            return isMatchWhenStartsWithStarAndHasMoreStars(str, pattern);
        }
        return equalStrings(str.substring(str.length() - patternSubstring.length()),
                            patternSubstring);
    }

    public boolean isMatchWhenStartsWithStarAndHasMoreStars(String str, String pattern) {
        boolean matchFound = false;
        int head = 1;
        int tail = pattern.indexOf("*", head);
        String subPattern = pattern.substring(head, tail);

        int index = getIndexOfSubstring(str, subPattern);
        while (index >= 0 && !matchFound) {
            matchFound = matchFound || isMatch(str.substring(index + subPattern.length()),
                                               pattern.substring(tail));
            index = str.indexOf(subPattern);
        }
        return matchFound;
    }

    private int getIndexOfSubstring(String str, String subString) {
        if (str.length() < subString.length()) {
            return -1;
        }
        for (int counter = 0; counter + subString.length() <= str.length(); counter++) {
            if (equalStrings(str.substring(counter, counter + subString.length()), subString)) {
                return counter;
            }
        }
        return -1;
    }

    private boolean equalStrings(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        for (int counter = 0; counter < str1.length(); counter++) {
            char str1Char = str1.charAt(counter);
            char str2char = str2.charAt(counter);
            if (str1Char != str2char && str1Char != '?' & str2char != '?') {
                return false;
            }
        }
        return true;
    }

    public String replaceMultipleStarOccurrenceWithJustOne(String pattern) {
        int index = pattern.indexOf("**");
        if (index == -1) {
            return pattern;
        }
        if (index == 0) {
            return replaceMultipleStarOccurrenceWithJustOne(pattern.substring(1));
        }
        if (index == pattern.length() - 1) {
            return pattern.substring(0, index);
        }
        return pattern.substring(0, index) + replaceMultipleStarOccurrenceWithJustOne(
                pattern.substring(index + 1));
    }
}
