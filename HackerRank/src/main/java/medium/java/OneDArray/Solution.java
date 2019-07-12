package medium.java.OneDArray;

import java.util.*;

public class Solution {
	public static boolean visited[];

	public static boolean traverse(int currentLoc, int leap, int[] game) {

		if (visited[currentLoc]) {
			return false;
		} else {
			visited[currentLoc] = true;
		}
		int n = game.length;

		if (currentLoc + leap >= n || currentLoc + 1 >= n) {
			return true;
		}
		int next = currentLoc + 1;
		int nextLeap = currentLoc + leap;
		int prev = currentLoc - 1;
		if (nextLeap < n && game[nextLeap] == 0 && traverse(nextLeap, leap, game)) {
			return true;
		}
		if (next < n && game[next] == 0 && traverse(next, leap, game)) {
			return true;
		}
		if (prev >= 0 && game[prev] == 0 && traverse(prev, leap, game)) {
			return true;
		}
		return false;
	}

	public static boolean canWin(int leap, int[] game) {
		int n = game.length;
		if (leap > 0) {
			int l = leap;
			for (int i = 0; i < n && l != 0; i++) {
				if (game[i] == 0) {
					l = leap;
				} else {
					l--;
				}
			}

			if (l == 0) {
				return false;
			}
		}
		return traverse(0, leap, game);
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int q = scan.nextInt();
		while (q-- > 0) {
			int n = scan.nextInt();
			int leap = scan.nextInt();

			int[] game = new int[n];
			visited = new boolean[n];
			for (int i = 0; i < n; i++) {
				visited[i] = false;
				game[i] = scan.nextInt();
			}
			System.out.println((canWin(leap, game)) ? "YES" : "NO");
		}
		scan.close();
	}
}
