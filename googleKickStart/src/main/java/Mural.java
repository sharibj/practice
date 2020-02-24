import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//https://codingcompetitions.withgoogle.com/kickstart/round/0000000000051060/0000000000058b89
class Mural {
    private static StringBuilder output = new StringBuilder();

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int T = input.nextInt();
        for (int ks = 1; ks <= T; ks++)
            handleTestCase(input, ks);
        printOutput();
    }

    private static void handleTestCase(Scanner input, int ks) {
        int n = input.nextInt();
        int[] wallSections = stringToNumArray(n, input.next());
        solve(ks, n, wallSections);
    }

    private static int[] stringToNumArray(int n, String numbers) {
        int[] wallSections = new int[n];
        for (int i = 0; i < n; i++)
            wallSections[i] = numbers.charAt(i) - '0';
        return wallSections;
    }

    private static void solve(int ks, int n, int[] wallSections) {
        int subArraySize = getMiddleCeil(n);
        int largestSum = getLargestSumForSize(wallSections, n, subArraySize);
        appendToOutput(ks, largestSum);
    }

    private static int getMiddleCeil(double n) {
        return (int) Math.ceil(n / 2);
    }

    private static int getLargestSumForSize(int[] wallSections, int n, int subArraySize) {
        int largestSum, currentSum;
        largestSum = currentSum = getSumForRange(wallSections, 0, subArraySize);
        for (int i = 1; i <= n - subArraySize; i++) {
            currentSum = currentSum - wallSections[i - 1] + wallSections[i + subArraySize - 1];
            if (largestSum < currentSum) largestSum = currentSum;
        }
        return largestSum;
    }

    private static int getSumForRange(int[] wallSections, int startPos, int endPos) {
        int sum = 0;
        for (int i = startPos; i < endPos; i++)
            sum += wallSections[i];
        return sum;
    }

    private static void appendToOutput(int ks, int largestSum) {
        output.append("Case #" + ks + ": " + largestSum + "\n");
    }

    private static void printOutput() {
        output.deleteCharAt(output.length() - 1);
        System.out.println(output);
    }
}
