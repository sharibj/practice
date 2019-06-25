package codejam.QR1.ForegoneSolution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author sjafari
 *
 */
public class Solution {

	/**
	 * Accepts input from user and calls getFractionWithout4(String number), to get
	 * the output
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Solution me = new Solution();
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			try {
				/*
				 * This was my first approach. Here number is treated as an Integer.
				 * Doesn't work for 10^100
				 * 
				 * int[] numbers = me.getFractionWithout4(in.nextInt());
				 * System.out.println(me.getOutputString(i, numbers[0], numbers[1]));
				 */

				/*This is the 2nd, more efficient approach.
				Here, number is processed as String.*/
				String[] numbers = me.getFractionWithout4(in.next());
				System.out.println(me.getOutputString(i, numbers[0], numbers[1]));
			} catch (RuntimeException re) {
				System.err.println(re.getMessage());
			}
		}
		in.close();
	}

	/**
	 * To get fractions without 4, we can substitute 2 in place of 4 in each
	 * fraction. Eg: 142 will become 122 and 020
	 * 
	 * @param number
	 * @return
	 */
	public String[] getFractionWithout4(String number) {
		String[] output = new String[2];
		StringBuilder a = new StringBuilder();
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < number.length(); i++) {
			int num = Integer.parseInt("" + number.charAt(i));
			if (num == 4) {
				a.append(2);
				b.append(2);
			} else {
				a.append(num);
				b.append(0);
			}
		}
		output[0] = trimLeadingZeros(a.toString());
		output[1] = trimLeadingZeros(b.toString());
		return output;
	}

	// Inefficient. Might cause stack overflow
	public String trimLeadingZeros(String str) {
		if (str.startsWith("0")) {
			str = str.substring(1);
			return trimLeadingZeros(str);
		} else {
			return str;
		}
	}

	/**
	 * Print the output as per the given output format
	 * 
	 * @param caseNumber
	 * @param a          - First fraction
	 * @param b          - Second fraction
	 * @return
	 */
	public String getOutputString(int caseNumber, String a, String b) {
		return "Case #" + caseNumber + ": " + a + " " + b;
	}

	// Integer Solution ......................................................
	public int[] getFractionWithout4(int number) {
		int[] output = new int[2];
		int counter = 0;
		int a = 0, b = 0;
		do {
			int rem = number % 10;
			int pow = (int) Math.pow(10, counter);
			if (rem == 4) {
				// contains 4
				a += (2 * pow);
				b += (2 * pow);
			} else {
				// Doesn't contain 4
				a += (rem * pow);
			}
			number /= 10;
			counter++;

		} while (number > 0);
		output[0] = a;
		output[1] = b;
		return output;
	}

	public String getOutputString(int caseNumber, int a, int b) {
		return "Case #" + caseNumber + ": " + a + " " + b;
	}

}
