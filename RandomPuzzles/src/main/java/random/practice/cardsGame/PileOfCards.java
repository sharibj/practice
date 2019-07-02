package random.practice.cardsGame;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author sjafari
 *
 */
public class PileOfCards {
	private Queue<Integer> cards;

	public PileOfCards() {
		cards = new LinkedList<Integer>();
	}

	public PileOfCards(Collection<Integer> cards) {
		this.cards = new LinkedList<Integer>();
		this.cards.addAll(cards);
	}

	// Draw card from the top of the pile
	public Integer draw() {
		if (cards.isEmpty()) {
			return null;
		}
		return cards.poll();
	}

	// Discard card to the bottom of the pile
	public void discard(Integer card) {
		cards.add(card);
	}

	// Draw the specified card
	public Integer draw(Integer card) {
		if (cards.remove(card)) {
			return card;
		} else {
			return null;
		}
	}

	// Get all cards
	// @TODO : Probably not a good idea to return actual cards.
	// Clone should be returned.
	public Queue<Integer> getCards() {
		return cards;
	}

	private boolean same;

	public boolean areAllSame() {
		same = true;
		Integer firstCard = cards.peek();
		cards.forEach((card) -> same &= card.equals(firstCard));
		return same;
	}

	@Override
	public String toString() {
		StringBuilder cardsStr = new StringBuilder();
		cards.forEach((card) -> cardsStr.append(card.toString() + " "));
		return cardsStr.toString();
	}

}
