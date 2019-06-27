package random.practice.threadNumPrinter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sjafari
 *
 */
public class NumPrinter {
	private ExecutorService executer;

	public void print(int numThreads, int maxNum) {
		if (maxNum < numThreads) {
			throw new RuntimeException("Threads can't be greater than max number");
		}
		executer = Executors.newFixedThreadPool(numThreads);
		for (int counter = 1; counter <= numThreads; counter++) {
			executer.execute(new NumberGeneratorThread("t" + counter, numThreads, maxNum));
		}
	}

	public static void main(String[] args) {
		NumPrinter numPrinter = new NumPrinter();
		numPrinter.print(5, 10);
	}
}
