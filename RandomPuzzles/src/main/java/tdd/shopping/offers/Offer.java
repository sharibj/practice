package tdd.shopping.offers;

import tdd.shopping.Inventory;

public interface Offer {
	double applyOffer(Inventory item, int quantity);
}
