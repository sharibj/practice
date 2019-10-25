package tdd.shopping.offers;

import tdd.shopping.Cart;

public class BuyTotalGreaterThanXGetYPercentOff implements CartOffer {

	@Override
	public double applyOffer(Cart cart, double totalValue) {
		return totalValue - totalValue * (10d / 100d);
	}
}
