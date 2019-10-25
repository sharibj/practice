package tdd.shopping.offers;

import tdd.shopping.Inventory;

public class BuyXgetYpercentOffOnNextZ implements Offer {

	private int x;
	private int y;
	private int z;

	public BuyXgetYpercentOffOnNextZ(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public double applyOffer(Inventory item, int quantity) {
		double total = 0;
		while (quantity > 0) {
			if (quantity >= x) {
				total += item.getPrice() * x;
				quantity -= x;
				if (quantity >= z) {
					// apply percentage discount
					double totalValue = item.getPrice() * z;
					double percentageToReduce = totalValue * (y / 100d);
					total += totalValue - percentageToReduce;
					quantity -= z;
				}
			} else {
				total += item.getPrice() * quantity;
				quantity = 0;
			}
		}
		return total;
	}
}
