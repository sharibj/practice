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

    }

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
                         matcher.isMatchWhenStartsWithStarAndHasMoreStars(
                                 inputStrings.get(index),
                                 inputPatterns.get(index))
                        );
        }

    }

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

    //@Test
    void testLargeInput() {
        // given
        String str =
                "abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb";
        String pattern =
                "**aa*****ba*a*bb**aa*ab****a*aaaaaa***a*aaaa**bbabb*b*b**aaaaaaaaa*a********ba*bbb***a*ba*bb*bb**a*b*bb";
        // when
        boolean result = matcher.isMatch(str, pattern);
        // then
        assertTrue(result);
    }
}