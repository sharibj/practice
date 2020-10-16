
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;

public class KickstartAlarm {

    private static final int MODULO = 1000000007;
    private static StringBuilder output = new StringBuilder();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int T = input.nextInt();
        input.nextLine();
        for (int ks = 1; ks <= T; ks++)
            handleTestCase(input, ks);
        printOutput();
    }

    private static void handleTestCase(Scanner input, int ks) {
        String inputStr = input.nextLine();
        ParamArrayGenerationVariables paGenVars = new ParamArrayGenerationVariables(inputStr);
        long power = getExpPowerForEveryCall(generateParamArray(paGenVars), paGenVars.N, paGenVars.K);
        output.append("Case #" + ks + ": " + power + "\n");
    }

    public static long[] generateParamArray(ParamArrayGenerationVariables paGenVars) {
        long[] paramArray = new long[paGenVars.N];
        long prevX = paGenVars.x1;
        long prevY = paGenVars.y1;
        paramArray[0] = (prevX + prevY) % paGenVars.F;
        for (int i = 1; i < paGenVars.N; i++) {
            long newX = ((paGenVars.C * prevX) + (paGenVars.D * prevY) + paGenVars.E1) % paGenVars.F;
            long newY = ((paGenVars.D * prevX) + (paGenVars.C * prevY) + paGenVars.E2) % paGenVars.F;
            paramArray[i] = (newX + newY) % paGenVars.F;
            prevX = newX;
            prevY = newY;
        }
        return paramArray;
    }

    //....................................................................................................

    public static long getExpPowerForEveryCall(long[] A, int N, int K) {
        long power = 0;
        for (int i = 1; i <= K; i++) {
            power += improvedGetTotalIthExpo(A, N, i);
            if (power > MODULO)
                power %= MODULO;
        }
        return power;
    }

    public static long getTotal_IthExpoPower(long[] A, int n, int i) {
        long totalExpoPower = 0;
        for (int j = 1; j <= n; j++) {
            for (int k = 0; k + j <= n; k++) {
                totalExpoPower += getIthExpoPower(Arrays.copyOfRange(A, k, k + j), j, i);
                if (totalExpoPower > MODULO)
                    totalExpoPower %= MODULO;
            }
        }
        return totalExpoPower;
    }

    public static long getIthExpoPower(long[] A, int n, int i) {
        long expoPower = 0;
        for (int j = 1; j <= n; j++) {
            expoPower += A[j - 1] * (pow(j, i));
            if (expoPower > MODULO)
                expoPower %= MODULO;
        }
        return expoPower;
    }

    public static long improvedGetTotalIthExpo(long[] A, int n, int i) {
        long expoPower = 0;
        long[] B = Arrays.copyOf(A, n);
        //first pass
        for (int j = 0; j < n; j++) {
            expoPower += A[j];
        }
        //remaining passes
        for (int step = 1; step < n; step++) {
            for (int pos = n - 1; pos - step >= 0; pos--) {
                B[pos] = B[pos-1]+A[pos] * pow(step + 1, i);
                expoPower += B[pos];
            }
        }
        return expoPower;
    }

    //....................................................................................................
    public static long pow(long a, long b) {
        long res = 1;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = res * a % MODULO;
            }
            a = a * a % MODULO;
            b = b >> 1;
        }
        return res;
    }

    private static void printOutput() {
        output.deleteCharAt(output.length() - 1);
        System.out.println(output);
    }

    //....................................................................................................
    static class ParamArrayGenerationVariables {
        public int N;
        public int K;
        public long x1;
        public long y1;
        public long C;
        public long D;
        public long E1;
        public long E2;
        public long F;

        public ParamArrayGenerationVariables(String input) {
            StringTokenizer tokenizer = new StringTokenizer(input, " ");
            //assert (tokenizer.countTokens() == 9);
            N = Integer.parseInt(tokenizer.nextToken());
            K = Integer.parseInt(tokenizer.nextToken());
            x1 = Long.parseLong(tokenizer.nextToken());
            y1 = Long.parseLong(tokenizer.nextToken());
            C = Long.parseLong(tokenizer.nextToken());
            D = Long.parseLong(tokenizer.nextToken());
            E1 = Long.parseLong(tokenizer.nextToken());
            E2 = Long.parseLong(tokenizer.nextToken());
            F = Long.parseLong(tokenizer.nextToken());
        }

        public ParamArrayGenerationVariables() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ParamArrayGenerationVariables that = (ParamArrayGenerationVariables) o;
            return N == that.N &&
                    K == that.K &&
                    x1 == that.x1 &&
                    y1 == that.y1 &&
                    C == that.C &&
                    D == that.D &&
                    E1 == that.E1 &&
                    E2 == that.E2 &&
                    F == that.F;
        }

        @Override
        public int hashCode() {
            return Objects.hash(N, K, x1, y1, C, D, E1, E2, F);
        }
    }
}
