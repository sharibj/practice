package tdd.shopping;

import java.util.HashMap;
import java.util.Map;

import tdd.shopping.offers.Offer;

public enum Offers {
	;
	private static HashMap<Inventory, Offer> offers = new HashMap<>();

	public static Map<Inventory, Offer> getOffers() {
		return (HashMap) offers.clone();
	}

	public static void addOffer(Inventory item, Offer offer) {
		offers.put(item, offer);
	}
}
