package random.practice.cardsGame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sjafari
 *
 */
public class Main {

	private Player[] players;

	public static void main(String[] args) {
		Main main = new Main();
		int[] cards = { 2, 3, 4, 0, 7, 1, 6, 5, 6, 5, 0, 2, 1, 7, 4, 3, 3, 6, 1, 5, 4, 2, 0, 7, 1, 2, 3, 0, 5, 6, 7, 4 };
		main.init(4, cards);
		main.start();
	}

	private void init(int numPlayers, int[] cards) {
		// Check if sufficient cards are available
		int totalCards = numPlayers * numPlayers * 2;
		if (totalCards != cards.length) {
			throw new RuntimeException("Insuffecient Cards");
		}

		// Create players
		players = new Player[numPlayers];
		PileOfCards[] playerHandPiles = new PileOfCards[numPlayers];
		PileOfCards[] piles = new PileOfCards[numPlayers];

		// Distribute Hand cards
		for (int counter = 0; counter < numPlayers; counter++) {
			playerHandPiles[counter] = new PileOfCards();
			int startCounter = counter * numPlayers;
			for (int i = startCounter; i < startCounter + numPlayers; i++) {
				playerHandPiles[counter].discard(cards[i]);
			}
		}

		// Divide cards in piles
		for (int counter = 0; counter < numPlayers; counter++) {
			piles[counter] = new PileOfCards();
			int startCounter = (counter * numPlayers) + (numPlayers * numPlayers);
			for (int i = startCounter; i < startCounter + numPlayers; i++) {
				piles[counter].discard(cards[i]);
			}
		}

		// Set Draw and Discard piles for players
		for (int counter = 0; counter < numPlayers; counter++) {
			players[counter] = new Player(counter + 1, playerHandPiles[counter], piles[counter],
					piles[(counter + 1) % numPlayers]);
		}

	}

	private void start() {
		if (players == null) {
			throw new RuntimeException("Can't start without players");
		}
		ExecutorService executer = Executors.newCachedThreadPool();
		for (int counter = 0; counter < players.length; counter++) {
			PlayerThread thread = new PlayerThread(players[counter], players.length);
			executer.execute(thread);
		}
		executer.shutdown();
	}
}
