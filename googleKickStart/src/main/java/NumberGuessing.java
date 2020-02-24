import java.util.Scanner;

//https://codingcompetitions.withgoogle.com/kickstart/round/0000000000051060/00000000000588f4
//Class name must be renamed to "Solution" before posting it on google
public class NumberGuessing {

    public static final String TOO_SMALL = "TOO_SMALL";
    public static final String TOO_BIG = "TOO_BIG";
    public static final String WRONG_ANSWER = "WRONG_ANSWER";

    public static void solve(Scanner input, int a, int b) {
        int mid = getMid(a, b);
        System.out.println(mid);
        String result = input.next();
        processResult(input, a, b, mid, result);
    }

    private static void processResult(Scanner input, int a, int b, int mid, String result) {
        if (TOO_SMALL.equalsIgnoreCase(result))
            solve(input, mid + 1, b);
        else if (TOO_BIG.equalsIgnoreCase(result))
            solve(input, a, mid - 1);
        else if (WRONG_ANSWER.equalsIgnoreCase(result))
            System.exit(0);
    }

    private static int getMid(int a, int b) {
        return a + ((b - a) / 2);
    }

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int T = input.nextInt();
        for (int ks = 1; ks <= T; ks++) {
            handleTestCase(input);
        }
    }

    private static void handleTestCase(Scanner input) {
        int a = input.nextInt();
        int b = input.nextInt();
        int n = input.nextInt();
        solve(input, a + 1, b);
    }
}

