package codejam.QR1.ForegoneSolution;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sjafari
 *
 */
public class SolutionTest {
	private Solution qr1;

	@Before
	public void init() {
		qr1 = new Solution();
	}

	/**
	 * Return true if the given number contains digit 4
	 * 
	 * @param number
	 * @return
	 */
	private boolean doesNumberContains4(int number) {
		do {
			int rem = number % 10;
			number /= 10;
			if (rem == 4) {
				return true;
			}
		} while (number > 0);
		return false;
	}

	@Test
	public void testUtilityFinction_doesNumberContains4() {
		Assert.assertTrue(doesNumberContains4(4));
		Assert.assertTrue(doesNumberContains4(141));
		Assert.assertTrue(doesNumberContains4(4001));
		Assert.assertTrue(doesNumberContains4(1004));
		Assert.assertFalse(doesNumberContains4(3));
		Assert.assertFalse(doesNumberContains4(131));
		Assert.assertFalse(doesNumberContains4(3001));
		Assert.assertFalse(doesNumberContains4(1003));
	}

	// Check Return type and size
	@Test
	public void whenNumberWith4IsProvidedThenReturnsArrayWithTwoNumbers() {
		int[] output = qr1.getFractionWithout4(4);
		Assert.assertNotNull(output);
		Assert.assertEquals(2, output.length);
		Assert.assertNotNull(output[0]);
		Assert.assertNotNull(output[1]);
	}

	private void testSum(int number) {
		int[] output = qr1.getFractionWithout4(number);
		Assert.assertFalse(doesNumberContains4(output[0]));
		Assert.assertFalse(doesNumberContains4(output[1]));
		int sum = output[0] + output[1];
		Assert.assertEquals(number, sum);
	}

	@Test
	public void whenNumberWith4IsProvidedThenReturnTwoNumbersWithout4WhoseSumEqualsNumber() {
		int number = 4;
		testSum(number);
	}

	@Test
	public void testOutputStringToMatchWithFormat() {
		String output = qr1.getOutputString(5, 2, 2);
		Assert.assertTrue(output.equals("Case #5: 2 2"));
	}

	@Test
	public void testSet1() {
		int number = (int) Math.pow(10, 5);
		number--;
		testSum(number);
	}

	@Test
	public void testSet2() {
		int number = (int) Math.pow(10, 9);
		number--;
		testSum(number);
	}

	@Test
	public void testSet3() {
		int max = (int) Math.pow(10, 1000000);
		for (int i = 0; i < 10; i++) {
			double randomDouble = Math.random();
			randomDouble = randomDouble * max;
			int number = (int) randomDouble;
			testSum(number);
		}
	}
}
