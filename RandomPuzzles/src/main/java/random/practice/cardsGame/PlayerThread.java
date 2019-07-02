package random.practice.cardsGame;

/**
 * @author sjafari
 *
 */
public class PlayerThread implements Runnable {

	private Player player;
	private Integer numPlayers;
	private static final Object LOCK = new Object();
	private static Integer turn = 1;
	private static Boolean gameOver = false;

	public PlayerThread(Player player, Integer numPlayers) {
		this.player = player;
		this.numPlayers = numPlayers;
	}

	@Override
	public void run() {
		do {
			synchronized (LOCK) {
				if (turn != player.getId()) {
					// Wait for turn
					try {
						LOCK.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					// Turn to play
					player.playHand();

					// Check if game is over
					if (player.playerWon()) {
						Player.printLine(player.getPlayerStringIdentifier() + " Won");
						gameOver = true;
					}

					// Next turn
					turn++;
					turn = turn > numPlayers ? 1 : turn;
					LOCK.notifyAll();
				}
			}
		} while (!gameOver);

		if (!player.playerWon()) {
			Player.printLine(player.getPlayerStringIdentifier() + " Lost");
		}
	}
}
