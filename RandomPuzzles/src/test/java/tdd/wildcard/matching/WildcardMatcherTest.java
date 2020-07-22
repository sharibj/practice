package tdd.wildcard.matching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WildcardMatcherTest {
    WildcardMatcher matcher = null;

    @BeforeEach
    void setUp() {
        matcher = new WildcardMatcher();
    }

    @Test
    void testGetHeadOfPattern() {
        assertEquals("", matcher.getHeadOfPattern(""));
        assertEquals("abc", matcher.getHeadOfPattern("abc"));
        assertEquals("xyz", matcher.getHeadOfPattern("xyz*"));
        assertEquals("abc", matcher.getHeadOfPattern("abc*def"));
        assertEquals("xyz", matcher.getHeadOfPattern("xyz*def*"));
        assertEquals("abc", matcher.getHeadOfPattern("abc*xyz*def"));
        assertEquals(null, matcher.getHeadOfPattern("*xyz*qwe"));
    }

    @Test
    void testGetBodyOfPattern() {
        assertEquals(null, matcher.getBodyOfPattern(""));
        assertEquals(null, matcher.getBodyOfPattern("abc"));
        assertEquals("*", matcher.getBodyOfPattern("xyz*"));
        assertEquals("*", matcher.getBodyOfPattern("abc*def"));
        assertEquals("*def*", matcher.getBodyOfPattern("xyz*def*"));
        assertEquals("*xyz*", matcher.getBodyOfPattern("abc*xyz*def"));
        assertEquals("*xyz*", matcher.getBodyOfPattern("*xyz*qwe"));
        assertEquals("*xyz*qwe*", matcher.getBodyOfPattern("abc*xyz*qwe*def"));
    }

    @Test
    void testGetTailOfPattern() {
        assertEquals(null, matcher.getTailOfPattern(""));
        assertEquals(null, matcher.getTailOfPattern("abc"));
        assertEquals(null, matcher.getTailOfPattern("xyz*"));
        assertEquals("def", matcher.getTailOfPattern("abc*def"));
        assertEquals(null, matcher.getTailOfPattern("xyz*def*"));
        assertEquals("def", matcher.getTailOfPattern("abc*xyz*def"));
        assertEquals("qwe", matcher.getTailOfPattern("*xyz*qwe"));
    }

    @Test
    void testVerifyHeadAndGetRemainderString() {
        assertEquals("", matcher.verifyHeadAndGetRemainderString("", ""));
        assertEquals("", matcher.verifyHeadAndGetRemainderString("abc", "abc"));
        assertEquals("xyz", matcher.verifyHeadAndGetRemainderString("abcxyz", "abc"));
        assertEquals(null, matcher.verifyHeadAndGetRemainderString("xyz", "abc"));
        assertEquals(null, matcher.verifyHeadAndGetRemainderString("aab", "b"));
    }

    @Test
    void testVerifyTailAndGetRemainderString() {
        assertEquals("", matcher.verifyTailAndGetRemainderString("", ""));
        assertEquals("", matcher.verifyTailAndGetRemainderString("abc", "abc"));
        assertEquals("abc", matcher.verifyTailAndGetRemainderString("abcxyz", "xyz"));
        assertEquals(null, matcher.verifyTailAndGetRemainderString("xyz", "abc"));
        assertEquals(null, matcher.verifyTailAndGetRemainderString("baa", "b"));
    }

    @Test
    void testUtilityMethod_replaceMutipleStarOccurenceWithJustOne() {
        List<String> inputs = new ArrayList<>();
        List<String> outputs = new ArrayList<>();
        inputs.add("");
        outputs.add("");
        inputs.add("*");
        outputs.add("*");
        inputs.add("**");
        outputs.add("*");
        inputs.add("a**b");
        outputs.add("a*b");
        inputs.add("**a**b**");
        outputs.add("*a*b*");
        inputs.add("*a*****bc**d*****");
        outputs.add("*a*bc*d*");

        for (int index = 0; index < inputs.size(); index++) {
            assertEquals(outputs.get(index),
                         matcher.replaceMultipleStarOccurrenceWithJustOne(inputs.get(index)));
        }

    }
/*
    @Test
    void testUtilityMethod_isMatchWhenStartsWithStar() {
        List<String> inputStrings = new ArrayList<>();
        List<String> inputPatterns = new ArrayList<>();
        List<Boolean> outputs = new ArrayList<>();

        inputStrings.add("");
        inputPatterns.add("*");
        outputs.add(true);

        inputStrings.add("abcd");
        inputPatterns.add("*");
        outputs.add(true);

        inputStrings.add("aa");
        inputPatterns.add("*a?");
        outputs.add(true);

        inputStrings.add("aa");
        inputPatterns.add("*b");
        outputs.add(false);

        for (int index = 0; index < inputStrings.size(); index++) {
            assertEquals(outputs.get(index),
                         matcher.isMatchWhenStartsWithStar(inputStrings.get(index),
                                                           inputPatterns.get(index))
                        );
        }

    }*/

    @Test
    void testUtilityMethod_isMatchWhenStartsWithStarAndHasMoreStars() {
        List<String> inputStrings = new ArrayList<>();
        List<String> inputPatterns = new ArrayList<>();
        List<Boolean> outputs = new ArrayList<>();
        inputStrings.add("a");
        inputPatterns.add("*a*");
        outputs.add(true);
        inputStrings.add("ab");
        inputPatterns.add("*a?*");
        outputs.add(true);
        inputStrings.add("a");
        inputPatterns.add("*?*");
        outputs.add(true);
        inputStrings.add("ab");
        inputPatterns.add("*ab*");
        outputs.add(true);
        inputStrings.add("cdgiescdfimde");
        inputPatterns.add("*cd?i*de");
        outputs.add(true);
        inputStrings.add("abcdefg");
        inputPatterns.add("*de*");
        outputs.add(true);
        inputStrings.add("abcdefg");
        inputPatterns.add("*b*e*g*");
        outputs.add(true);

        inputStrings.add("aaba");
        inputPatterns.add("*a*ba");
        outputs.add(true);

        for (int index = 0; index < inputStrings.size(); index++) {
            assertEquals(outputs.get(index),
                         matcher.isMatchWhenPatternStartsAndEndsWithStar(
                                 inputStrings.get(index),
                                 inputPatterns.get(index))
                        );
        }

    }

    @Test
    void testUtilityMethod_trimStringTillNextStar() {
        assertEquals("ab", matcher.trimStringTillNextStar("ab*c", 0));
        assertEquals("ab", matcher.trimStringTillNextStar("ab*", 0));
        assertEquals("ab", matcher.trimStringTillNextStar("ab", 0));
        assertEquals("b", matcher.trimStringTillNextStar("ab*c", 1));
    }
/*
    @Test
    void testUtilityMethod_getStringAfterNextStar() {
        assertEquals("*c", matcher.getStringAfterNextStar("ab*c", 0));
        assertEquals("*", matcher.getStringAfterNextStar("ab*", 0));
        assertEquals("ab", matcher.getStringAfterNextStar("ab", 0));
        assertEquals("*c", matcher.getStringAfterNextStar("*b*c", 1));
    }*/

    @Test
    void whenNothingMatches_thenFalse() {
        // given
        String str = "aa";
        String pattern = "a";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertFalse(result);
    }

    @Test
    void whenEntireStringMatches_thenTrue() {
        // given
        String str = "aa";
        String pattern = "aa";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertTrue(result);
    }


    @Test
    void whenStar_thenTrue() {
        // given
        String str = "aa";
        String pattern = "*";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertTrue(result);
    }

    @Test
    void whenQuestion_thenTrue() {
        // given
        String str = "a";
        String pattern = "?";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertTrue(result);
    }


    @Test
    void whenQuestion_withMatchingTail_thenTrue() {
        // given
        String str = "cb";
        String pattern = "?b";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertTrue(result);
    }


    @Test
    void whenQuestion_withMatchingHead_thenTrue() {
        // given
        String str = "cb";
        String pattern = "c?";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertTrue(result);
    }

    @Test
    void whenQuestion_withUnmatchingTail_thenFalse() {
        // given
        String str = "cb";
        String pattern = "?a";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertFalse(result);
    }


    @Test
    void whenQuestion_withUnmatchingHead_thenFalse() {
        // given
        String str = "cb";
        String pattern = "a?";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertFalse(result);
    }

    @Test
    void whenMultipleStars_withMatchingString_thenReturnTrue() {
        // given
        String str = "adceb";
        String pattern = "*a*b";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertTrue(result);
    }


    @Test
    void whenMultipleQuestionmarks_withMatchingString_thenReturnTrue() {
        // given
        String str = "adceb";
        String pattern = "?dc?b";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertTrue(result);
    }

    @Test
    void whenMultipleWildcards_withUnmatchingString_thenReturnFalse() {
        // given
        String str = "acdcb";
        String pattern = "a*c?b";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertFalse(result);
    }

    @Test
    void whenOnlyQuestionmarks_withMatchingString_thenReturnTrue() {
        // given
        String str = "acdcb";
        String pattern = "????b";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertTrue(result);
    }

    @Test
    void whenOnlyQuestionmarks_withNonMatchingString_thenReturnFalse() {
        // given
        String str = "b";
        String pattern = "??";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertFalse(result);
    }

    @Test
    void testLargeInput() {
        // given
        String str =
                "abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb";
        String pattern =
                "**aa*****ba*a*bb**aa*ab****a*aaaaaa***a*aaaa**bbabb*b*b**aaaaaaaaa*a********ba*bbb***a*ba*bb*bb**a*b*bb";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertFalse(result);
    }

    @Test
    void test1() {
        // given
        String str =
                "aa";
        String pattern =
                "a*";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertTrue(result);
    }

    @Test
    void test2() {
        // given
        String str =
                "abce";
        String pattern =
                "abc*??";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertFalse(result);

        // given
        str = "abaaaa";
        pattern = "*?***b";
        // when
        result = matcher.isMatch(str, pattern);
        // then
        assertFalse(result);

        // given
        str = "aaaa";
        pattern = "***a";
        // when
        result = matcher.isMatch(str, pattern);
        // then
        assertTrue(result);

        // given
        str = "aaab";
        pattern = "b**";
        // when
        result = matcher.isMatch(str, pattern);
        // then
        assertFalse(result);
    }
}