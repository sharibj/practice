package random.practice.threadNumPrinter;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author sjafari
 *
 */
public class NumberGeneratorThread implements Runnable {
	private String id;
	private static int maxNum;
	private static Integer numThreads = -1;
	private static Set<Integer> numbers = new HashSet();

	NumberGeneratorThread(String id, int numThreads, int maxNum) {
		this.id = id;
		this.maxNum = maxNum;
		// Do not overwrite
		this.numThreads = this.numThreads == -1 ? numThreads : this.numThreads;
	}

	public void run() {
		do {
			// Generate new random number
			int randNumber = getRandomNumber(maxNum);
			// Acquire lock on numbers list
			synchronized (numbers) {
				if (!numbers.contains(randNumber)) {
					// Generated random number is unique
					// Add it to the list and print the output
					numbers.add(randNumber);
					print(randNumber);
					if (numThreads > 0) {
						// At least one thread hasn't printed a number yet
						numThreads--;
						if (numThreads == 0) {
							// This was the last thread to print at least one number
							// Wake up all threads to continue printing remaining numbers
							numbers.notifyAll();
						} else {
							// Wait for other threads to print at least one number
							try {
								numbers.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} while (numbers.size() < maxNum);
	}

	private static Random random = new Random();

	/**
	 * Generate a random number within the giver range
	 * 
	 * @param range
	 * @return
	 */
	public int getRandomNumber(int range) {
		return random.nextInt(range);
	}

	/**
	 * Print output
	 * 
	 * @param num
	 */
	public void print(int num) {
		System.out.println(id + " " + num);
	}
}
