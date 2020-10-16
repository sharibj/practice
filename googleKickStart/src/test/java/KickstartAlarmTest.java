import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class KickstartAlarmTest {

    @Test
    void getIthExpoPowerForArrayWith_OneElement_1() {
        //Given
        long[] A = {1};
        int expectedOutput = 1;
        int i = 2;
        //When
        long actualOutput = KickstartAlarm.getIthExpoPower(A, A.length, i);
        //Then
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getIthExpoPowerForArrayWith_OneElement_2() {
        //Given
        long[] A = {2};
        int expectedOutput = 2;
        int i = 2;
        //When
        long actualOutput = KickstartAlarm.getIthExpoPower(A, A.length, i);
        //Then
        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    void getIthExpoPowerForArrayWith_TwoElements() {
        //Given
        long[] A = {1, 4};
        int expectedOutput1 = 17;
        long[] B = {4, 2};
        int expectedOutput2 = 12;
        int i = 2;
        //When
        long actualOutput1 = KickstartAlarm.getIthExpoPower(A, A.length, i);
        long actualOutput2 = KickstartAlarm.getIthExpoPower(B, B.length, i);
        //Then
        assertEquals(expectedOutput1, actualOutput1);
        assertEquals(expectedOutput2, actualOutput2);
    }

    @Test
    void getIthExpoPowerForArrayWith_ThreeElements() {
        //Given
        long[] A = {1, 4, 2};
        int expectedOutput = 35;
        int i = 2;
        //When
        long actualOutput = KickstartAlarm.getIthExpoPower(A, A.length, i);
        //Then
        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    void getTotal_IthExpoPowerForArray() {
        //Given
        long[] A = {1, 4, 2};
        int expectedOutput = 71;
        int i = 2;
        //When
        long actualOutput = KickstartAlarm.getTotal_IthExpoPower(A, A.length, i);
        //Then
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testParamGenerationVariableReader() {
        //Given
        String input = "2 3 1 2 1 2 1 1 9";
        KickstartAlarm.ParamArrayGenerationVariables expectedPaGenVars = new KickstartAlarm.ParamArrayGenerationVariables();
        expectedPaGenVars.N = 2;
        expectedPaGenVars.K = 3;
        expectedPaGenVars.x1 = 1;
        expectedPaGenVars.y1 = 2;
        expectedPaGenVars.C = 1;
        expectedPaGenVars.D = 2;
        expectedPaGenVars.E1 = 1;
        expectedPaGenVars.E2 = 1;
        expectedPaGenVars.F = 9;
        expectedPaGenVars.K = 3;
        //When
        KickstartAlarm.ParamArrayGenerationVariables paGenVars = new KickstartAlarm.ParamArrayGenerationVariables(input);
        //Then
        assertEquals(expectedPaGenVars, paGenVars);
    }

    @Test
    void testParameterArrayGeneration() {
        //Given
        KickstartAlarm.ParamArrayGenerationVariables paGenVars = new KickstartAlarm.ParamArrayGenerationVariables("2 3 1 2 1 2 1 1 9");
        long[] expectedArray = {3, 2};
        //When
        long[] paramArray = KickstartAlarm.generateParamArray(paGenVars);
        //Then
        assertTrue(Arrays.equals(expectedArray, paramArray));
    }

    @Test
    void testTotalPowerForKCalls() {
        //Given
        int K = 3;
        long A[] = {3, 2};
        int N = 2;
        int expectedOutput = 52;
        //When
        long power = KickstartAlarm.getExpPowerForEveryCall(A, N, K);
        //Then
        assertEquals(expectedOutput, power);
    }

    @Test
    void testSampleInput1() {
        //Given
        String inputStr = "2 3 1 2 1 2 1 1 9";
        KickstartAlarm.ParamArrayGenerationVariables paGenVars = new KickstartAlarm.ParamArrayGenerationVariables(inputStr);
        int expectedOutput = 52;
        //When
        long power = KickstartAlarm.getExpPowerForEveryCall(KickstartAlarm.generateParamArray(paGenVars), paGenVars.N, paGenVars.K);
        //Then
        assertEquals(expectedOutput, power);
    }

    @Test
    void testSampleInput2() {
        //Given
        String inputStr = "10 10 10001 10002 10003 10004 10005 10006 89273";
        KickstartAlarm.ParamArrayGenerationVariables paGenVars = new KickstartAlarm.ParamArrayGenerationVariables(inputStr);
        int expectedOutput = 739786670;
        //When
        long power = KickstartAlarm.getExpPowerForEveryCall(KickstartAlarm.generateParamArray(paGenVars), paGenVars.N, paGenVars.K);
        //Then
        assertEquals(expectedOutput, power);
    }

    @Test
    void testImprovedSum() {
        //Given
        long[] A = {1, 4, 2};
        int expectedOutput = 71;
        int i = 3;
        //When
        long actualOutput = KickstartAlarm.improvedGetTotalIthExpo(A, A.length, i);
        //Then
        assertEquals(expectedOutput, actualOutput);
    }
}
