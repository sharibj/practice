package codejam.QR1.YCGYOW;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author sjafari
 *
 */
public class Solution {

	/**
	 * Accepts input from user and calls getPath(int n, String lydiaPath), to get
	 * the output
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Solution me = new Solution();
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			String lydiaPath = in.next();
			String path = me.getPath(n, lydiaPath);
			System.out.println(me.getOutputString(i, path));
		}
	}

	public String getPath(int n, String lydiaPath) {
		/*
		 * The first solution I implemented involved backtracking However, it quickly
		 * dawned upon me that there was a better and simpler solution available. Simply
		 * reversing lydia's path gives us a valid path as well. I'm still keeping the
		 * backtracking code but commented the code that calls it.
		 */
		// maze = tracePathOnMaze(n, lydiaPath);
		// return takeAStep(n, 0, 0, "");
		return reversePath(lydiaPath);
	}

	/**
	 * Simply reverse the given path and return it as String
	 * 
	 * @param path Path taken by Lydia
	 * @return
	 */
	public String reversePath(String path) {
		StringBuilder reversePath = new StringBuilder();
		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) == 'E') {
				reversePath.append("S");
			} else if (path.charAt(i) == 'S') {
				reversePath.append("E");
			}
		}
		return reversePath.toString();
	}

	/**
	 * Print the output as per the given output format
	 * 
	 * @param caseNumber
	 * @param path Path to be printed
	 * @return
	 */
	public String getOutputString(int caseNumber, String path) {
		return "Case #" + caseNumber + ": " + path;
	}

	// Backtracking Solution ......................................................
	private char[][] maze;

	/**
	 * Trace the path Lydia took inside the given maze This will later be used to
	 * decide the directions of our own steps, later.
	 * 
	 * @param n Maze Size
	 * @param path Path to be traced
	 * @return Maze with lydia's footsteps traced on it.
	 */
	public char[][] tracePathOnMaze(int n, String path) {
		char[][] maze = new char[n][n];
		int x = 0, y = 0;
		char east = 'E'; // Left
		char south = 'S'; // Down

		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) == east) {
				maze[x][y] = east;
				x++;
			} else if (path.charAt(i) == south) {
				maze[x][y] = south;
				y++;
			}
		}
		return maze;
	}

	public boolean canGoEast(int n, char mazeChar, int x, int y) {
		return x != n - 1 && mazeChar != 'E';
	}

	public boolean canGoSouth(int n, char mazeChar, int x, int y) {
		return y != n - 1 && mazeChar != 'S';
	}

	public static int counter = -1;

	/**
	 * Recursive function that explore all possible path until 
	 * 	1 - destination is reach
	 * 	2 - all possibilities are exhausted
	 * @param n Maze Size
	 * @param x Current x position
	 * @param y Current y position
	 * @param path Path taken so far
	 * @return
	 * 	1 - a valid path
	 * 	2 - 'X' if not path could be found
	 */
	public String takeAStep(int n, int x, int y, String path) {
		if (x == n - 1 && y == n - 1) {
			// Reached Destination
			return path;
		}
		if (canGoEast(n, maze[x][y], x, y)) {
			// Take a step in East
			String myPath = takeAStep(n, x + 1, y, path + "E");
			if (!myPath.equalsIgnoreCase("X")) {
				return myPath;
			}
		}
		if (canGoSouth(n, maze[x][y], x, y)) {
			// Take a step in East
			String myPath = takeAStep(n, x, y + 1, path + "S");
			if (!myPath.equalsIgnoreCase("X")) {
				return myPath;
			}
		}
		return "X";
	}
}
