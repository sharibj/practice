package tdd.wildcard.matching;

public class WildcardMatcher {
    private static final String STAR = "*";

    public boolean isMatch(String s, String p) {
        if (!p.contains(STAR)) {
            return equalStrings(s, p);
        }
        p = replaceMultipleStarOccurrenceWithJustOne(p);
        String head = getHeadOfPattern(p);
        String body = getBodyOfPattern(p);
        String tail = getTailOfPattern(p);

        if (head != null && s != null) {
            s = verifyHeadAndGetRemainderString(s, head);
        }
        if (tail != null && s != null) {
            s = verifyTailAndGetRemainderString(s, tail);
        }
        if (body != null && s != null) {
            return body.equals(STAR) ?
                   true : isMatchWhenPatternStartsAndEndsWithStar(s, body);
        }
        return "".equals(s);
    }

    public boolean isMatchWhenPatternStartsAndEndsWithStar(String str, String pattern) {
        int head = 1;
        while (head < pattern.length()) {
            String patternBetweenTwoStars = trimStringTillNextStar(pattern, head);
            int index = getIndexOfSubstring(str, patternBetweenTwoStars);
            if (index == -1) {
                return false;
            }
            str = str.substring(index + patternBetweenTwoStars.length());
            head += patternBetweenTwoStars.length() + 1;
        }
        return true;
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
        int index = pattern.indexOf(STAR + STAR);
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

    public String trimStringTillNextStar(String str, int beginIndex) {
        int starCharIndex = str.indexOf(STAR, beginIndex);
        if (starCharIndex < 0) {
            return str.substring(beginIndex);
        } else {
            return str.substring(beginIndex, starCharIndex);
        }
    }

    public String getHeadOfPattern(String pattern) {
        if (pattern.contains(STAR)) {
            if (pattern.startsWith(STAR)) {
                return null;
            } else {
                return pattern.substring(0, pattern.indexOf(STAR));
            }
        } else {
            return pattern;
        }
    }

    public String getTailOfPattern(String pattern) {
        if (pattern.contains(STAR)) {
            if (pattern.endsWith(STAR)) {
                return null;
            } else {
                return pattern.substring(pattern.lastIndexOf(STAR) + 1);
            }
        } else {
            return null;
        }
    }

    public String getBodyOfPattern(String pattern) {
        if (pattern.contains(STAR)) {
            int start = pattern.indexOf(STAR);
            int end = pattern.lastIndexOf(STAR);
            if (start == end) {
                return STAR;
            } else {
                return pattern.substring(start, end + 1);
            }
        } else {
            return null;
        }
    }

    public String verifyHeadAndGetRemainderString(String str, String pattern) {
        int remainderLength = str.length() - pattern.length();
        if (remainderLength < 0) {
            return null;
        }
        String beginString = str.substring(0, pattern.length());
        if (equalStrings(beginString, pattern)) {
            return str.substring(pattern.length());
        } else {
            return null;
        }
    }

    public String verifyTailAndGetRemainderString(String str, String pattern) {
        int remainderLength = str.length() - pattern.length();
        if (remainderLength < 0) {
            return null;
        }
        String endString = str.substring(remainderLength);
        if (equalStrings(endString, pattern)) {
            return str.substring(0, remainderLength);
        } else {
            return null;
        }
    }
}
