package codejam.QR1.YCGYOW;

import org.junit.*;

public class SolutionTest {

	Solution solution;

	@Before
	public void init() {
		solution = new Solution();
	}

	private boolean doesPathLeadsToDestincation(int n, String path) {
		int x = 0, y = 0;
		char East = 'E'; // Left
		char South = 'S'; // Down

		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) == East) {
				x++;
			} else if (path.charAt(i) == South) {
				y++;
			}
		}
		return x==n-1 && y==x;
	}

	private boolean twoPathsAreUnique(int n, String path1, String path2) {
		int x = 0, y = 0;
		char East = 'E'; // Left
		char South = 'S'; // Down
		char[][] maze = solution.tracePathOnMaze(n, path1);
		//Trace path2
		for (int i = 0; i < path2.length(); i++) {
			if (path2.charAt(i) == East) {
				if(maze[x][y] == East) {
					return false;
				}

				x++;
			} else if (path2.charAt(i) == South) {
				if(maze[x][y] == South) {
					return false;
				}

				y++;
			}
		}
		
		return true;
	}
	
	@Test
	public void testUtilityFunction_tracePathOnMaze() {
		char[][] maze = solution.tracePathOnMaze(2, "ES");
		Assert.assertTrue(maze[0][0]=='E');
		Assert.assertTrue(maze[1][0]=='S');
	}
	@Test
	public void testUtilityFunction_getOutputString() {
		Assert.assertTrue(solution.getOutputString(1, "ES").equals("Case #1: ES"));
	}
	@Test
	public void testUtilityFunction_canGoEast() {
		Assert.assertTrue(solution.canGoEast(2, 'S', 0, 0));
		Assert.assertFalse(solution.canGoEast(2, 'E', 0, 0));
		Assert.assertFalse(solution.canGoEast(2, 'S', 1, 0));
		Assert.assertTrue(solution.canGoEast(2, 'S', 0, 1));
	}
	@Test
	public void testUtilityFunction_canGoSouth() {
		Assert.assertFalse(solution.canGoSouth(2, 'S', 0, 0));
		Assert.assertTrue(solution.canGoSouth(2, 'E', 0, 0));
		Assert.assertTrue(solution.canGoSouth(2, 'E', 1, 0));
		Assert.assertFalse(solution.canGoSouth(2, 'E', 0, 1));
	}
	private void testSetForGivenRange(int n, int range) {
		for(int i=0;i<n;i++) {
			double rand =  Math.random();
			int num = (int) (rand*range);
			if(num==0) {
				num ++;
			}
			//Just checking if it works with large inputs. Output isn't verified
			String path = solution.getPath(num, "");
		}
	}

	@Test
	public void testSample1() {
		String path = solution.getPath(2, "SE");
		Assert.assertTrue(doesPathLeadsToDestincation(2,path));
		Assert.assertTrue(twoPathsAreUnique(2, "SE", path));
	}
	@Test
	public void testSample2() {
		String path = solution.getPath(5, "EESSSESE");
		Assert.assertTrue(doesPathLeadsToDestincation(5,path));
		Assert.assertTrue(twoPathsAreUnique(5, "EESSSESE", path));
	}
	@Test
	public void testSet1() {
		testSetForGivenRange(10, 10);
	}
	@Test
	public void testSet2() {
		testSetForGivenRange(10, 1000);
	}
	@Test
	public void testSet3() {
		try {
		Solution.counter=0;
		testSetForGivenRange(1, 10000);
		}finally {
			Solution.counter=-1;
		}
	}
	@Test
	public void testSet4() {
		testSetForGivenRange(10, 50000);
	}
}
