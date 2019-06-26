package random.puzzle.monkeyBoat;

/**
 * @author sjafari
 *
 */
public class Solution {
	/**
	 * Problem Statement: There are N groups of monkeys on one side of the river.
	 * A boat with M seats is needed to make all of them cross the river.
	 * 
	 * Crossing the river has the following criteria:
	 * 
	 * 1 - Groups can't be broken. You can't take a monkey from one group and add it
	 * to another.
	 * 
	 * 2 - Order of the group must be maintained. The 2nd group can go with or after the 1st
	 * group, not before it.
	 * 
	 * 3 - The boat should always be full. There can't be any empty seats.
	 * 
	 * Output all the possible boat sizes which can be used to take all the monkeys
	 * across the river.
	 * 
	 * e.g
	 * Input :
	 * N = 6
	 * 1, 2, 3, 3, 2, 1
	 * 
	 * Output :
	 * 3
	 * 6
	 * 12
	 */
	// Input
	int[] monkeyGroups = { 1, 2, 3, 3, 2, 1 };

	int len = monkeyGroups.length;

	public static void main(String[] args) {
		new Solution().solve();
	}

	/**
	 * All possible solutions are multiple of the total sum. For each multiple,
	 * check if the array can be successfully grouped. If yes, that multiple is one
	 * of the possible solutions.
	 */
	private void solve() {
		int totalSum = sum();
		for (int i = totalSum; i > 0; i--) {
			boolean isMultipleOfSum = (totalSum % i == 0);
			if (isMultipleOfSum) {
				int multiple = totalSum / i;
				if (isGroupingPossible(multiple)) {
					System.out.println(multiple);
				}
			}
		}
	}

	/**
	 * Return total sum of monkeyGroups array elements
	 * 
	 * @return
	 */
	int sum() {
		int totalSum = 0;
		for (int i = 0; i < len; i++) {
			totalSum += monkeyGroups[i];
		}
		return totalSum;
	}

	/**
	 * Returns true if all array elements can be grouped such that sum of each group
	 * equals groupWeight
	 * 
	 * @param groupWeight
	 * @return
	 */
	private boolean isGroupingPossible(int groupWeight) {
		int sum = 0;
		for (int i = 0; i < len; i++) {
			sum += monkeyGroups[i];
			if (sum == groupWeight && i != len - 1) {
				sum = 0;
			}
		}
		return sum == groupWeight;
	}

}
