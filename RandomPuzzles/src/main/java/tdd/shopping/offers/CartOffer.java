package tdd.shopping.offers;

import tdd.shopping.Cart;

public interface CartOffer {
	public double applyOffer(Cart cart, double totalValue);
}
