import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MuralTest {
    private static final InputStream originalInputStream = System.in;
    private static final PrintStream originalOutputStream = System.out;

    @Test
    void testSampleInput() {
        String expectedOutput = "Case #1: 6\n" +
                "Case #2: 14\n" +
                "Case #3: 7\n" +
                "Case #4: 31\n";

        String input = "4\n" +
                "4\n" +
                "1332\n" +
                "4\n" +
                "9583\n" +
                "3\n" +
                "616\n" +
                "10\n" +
                "1029384756";

        testOutputForInput(expectedOutput, input);
    }

    @Test
    void largeInput() {
        String expectedOutput = "Case #1: 6\n" +
                "Case #2: 14\n" +
                "Case #3: 7\n" +
                "Case #4: 31\n";
        int n = 5 * (10 ^ 6);
        String wallSectionsStr = getRandomWallSections(n);
        String input = "1\n" +
                n + "\n" +
                wallSectionsStr;

        printOutputForInput(expectedOutput, input);
    }

    private String getRandomWallSections(int n) {
        List<Integer> wallSections = new ArrayList<>();
        for (int i = 0; i < n; i++)
            wallSections.add((int) ((Math.random() * 10) - 1));
        return wallSections.stream().map(x -> x.toString()).reduce("", (x, y) -> x + "" + y).toString();
    }

    private void testOutputForInput(String expectedOutput, String input) {
        setStringAsSystemInput(input);
        ByteArrayOutputStream actualOutput = getSystemOutputAsByteStream();
        Mural.main(null);
        assertTrue(expectedOutput.equalsIgnoreCase(actualOutput.toString()));
    }

    //For manual testing
    private void printOutputForInput(String expectedOutput, String input) {
        setStringAsSystemInput(input);
        ByteArrayOutputStream actualOutput = getSystemOutputAsByteStream();
        Mural.main(null);
        originalOutputStream.println(actualOutput.toString());
    }

    private ByteArrayOutputStream getSystemOutputAsByteStream() {
        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
        PrintStream myOut = new PrintStream(actualOutput);
        System.setOut(myOut);
        return actualOutput;
    }

    private void setStringAsSystemInput(String input) {
        ByteArrayInputStream myInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(myInput);
    }

    static private void restoreSystemInputOutputStream(InputStream originalInputStream, PrintStream originalOutputStream) {
        System.setIn(originalInputStream);
        System.setOut(originalOutputStream);
    }

    @AfterAll
    static void afterAll() {
        restoreSystemInputOutputStream(originalInputStream, originalOutputStream);
    }
}