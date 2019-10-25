package tdd.shopping.offers;

import tdd.shopping.Inventory;

public class BuyXgetY implements Offer {

	private int x;
	private int y;

	public BuyXgetY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double applyOffer(Inventory item, int quantity) {
		double total = 0;
		while (quantity > 0) {
			if (quantity >= x) {
				total += item.getPrice() * x;
				quantity -= x;
				if (quantity >= y) {
					// Free item for every two items bought
					quantity -= y;
				}
			} else {
				total += item.getPrice() * quantity;
				quantity = 0;
			}
		}
		return total;
	}

}
