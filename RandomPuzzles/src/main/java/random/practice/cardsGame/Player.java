package random.practice.cardsGame;

import java.util.Optional;

/**
 * @author sjafari
 *
 */
public class Player {

	private final Integer id;
	private final String playerStringIdentifier;
	private PileOfCards holdingPile;
	private PileOfCards drawPile;
	private PileOfCards discardPile;
	private final Integer pref;

	public Integer getId() {
		return id;
	}

	public String getPlayerStringIdentifier() {
		return playerStringIdentifier;
	}

	public PileOfCards getHoldingPile() {
		return holdingPile;
	}

	public PileOfCards getDrawPile() {
		return drawPile;
	}

	public PileOfCards getDiscardPile() {
		return discardPile;
	}

	public Player(Integer id, PileOfCards holdingPile, PileOfCards drawPile, PileOfCards discardPile) {
		this.id = id;
		this.holdingPile = holdingPile;
		this.drawPile = drawPile;
		this.discardPile = discardPile;
		pref = 2 * (id - 1);
		playerStringIdentifier = "Player " + id;
	}

	public void playHand() {
		// Draw a card from draw pile
		Integer drawnCard = drawPile.draw();
		if (drawnCard == null) {
			throw new RuntimeException(playerStringIdentifier + " couldn't draw a card from draw pile");
		}

		// Add drawn card to holding pile
		holdingPile.discard(drawnCard);

		// Select a card to discard
		Integer discardedCard = selectCardToBeDiscarded(holdingPile);

		// Draw discarded card from holding pile
		discardedCard = holdingPile.draw(discardedCard);
		if (discardedCard == null) {
			throw new RuntimeException(playerStringIdentifier + " couldn't draw a card holding pile");
		}

		// Add discarded card to discard pile
		discardPile.discard(discardedCard);

		// Print current hand
		printHand(drawnCard, discardedCard, holdingPile);

	}

	private void printHand(Integer drawnCard, Integer discardedCard, PileOfCards holdingPile) {
		printLine(playerStringIdentifier + " draws a " + drawnCard.toString());
		printLine(playerStringIdentifier + " discards a " + discardedCard.toString());
		printLine(playerStringIdentifier + " Hand " + holdingPile.toString());
	}

	private Integer selectCardToBeDiscarded(PileOfCards holdingPile) {
		Optional<Integer> selectedCard;
		selectedCard = holdingPile.getCards().stream().filter((Integer card) -> card < pref || card > pref + 1)
				.findFirst();
		if (!selectedCard.isPresent()) {
			selectedCard = holdingPile.getCards().stream().sorted().findFirst();
		}
		if (!selectedCard.isPresent()) {
			throw new RuntimeException(playerStringIdentifier + " couldn't select a card to discard");
		}
		return selectedCard.get();
	}

	public boolean playerWon() {
		return holdingPile.areAllSame();
	}

	public static void printLine(String msg) {
		System.out.println(msg);
	}
}
