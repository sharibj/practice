package tdd.shopping;

import java.util.LinkedHashMap;
import java.util.Map;

import tdd.shopping.offers.CartOffer;

public class Cart {
	private double totalValue;
	private Map<Inventory, Integer> items;
	private CartOffer cartOffer;

	public Cart() {
		// [4]totalValue = 0;
		items = new LinkedHashMap<>();
		cartOffer = null;
	}
	/*
	 * [2] public void add(Item i) { totalValue += i.getPrice() * i.getQuantity(); }
	 */

	public double total() {
		totalValue = 0;
		items.entrySet().stream().forEach(entry -> {
			Inventory item = entry.getKey();
			int quantity = entry.getValue();
			if (Offers.getOffers().containsKey(item)) {
				totalValue += Offers.getOffers().get(item).applyOffer(item, quantity);
			} else {
				totalValue += item.getPrice() * quantity;
			}
		});
		if (cartOffer != null) {
			totalValue = cartOffer.applyOffer(this, totalValue);
		}
		return totalValue;
	}

	public void add(Inventory item, int quantity) {
		Integer oldQuantity = items.get(item);
		if (oldQuantity != null) {
			// Product exists in cart. Just increase quantity
			items.put(item, oldQuantity + quantity);
		} else {
			items.put(item, quantity);
		}

		/*
		 * [4] Map<Inventory, String> offers = Offers.getOffers(); if
		 * (offers.containsKey(item)) { if
		 * (offers.get(item).equalsIgnoreCase("Buy2Get1")) { totalValue +=
		 * getTotalByApplyingBXGYOffer(2, 1, item, quantity); } else if
		 * (offers.get(item).equalsIgnoreCase("Buy3Get1")) { totalValue +=
		 * getTotalByApplyingBXGYOffer(3, 1, item, quantity); } else { // No recognized
		 * offer totalValue += item.getPrice() * quantity; } } else { totalValue +=
		 * item.getPrice() * quantity; }
		 */
	}

	public Cart addOffer(CartOffer offer) {
		this.cartOffer = offer;
		return this;

	}

	/*	
		*//**
			 * utility function to apply Buy X get Y offers
			 * 
			 * @param x
			 * @param y
			 * @param item
			 * @param quantity
			 * @return
			 *//*
				 * [4] private double getTotalByApplyingBXGYOffer(int x, int y, Inventory item,
				 * int quantity) { double total = 0; while (quantity > 0) { if (quantity >= x) {
				 * total += item.getPrice() * x; quantity -= x; if (quantity >= y) { // Free
				 * item for every two items bought quantity--; } } else { total +=
				 * item.getPrice() * quantity; quantity = 0; } } return total; }
				 */
	/*
	 * [4] public void add(Inventory item, int quantity) { Map<Inventory, String>
	 * offers = Offers.getOffers(); if (offers.containsKey(item)) { if
	 * (offers.get(item).equalsIgnoreCase("Buy2Get1")) { while (quantity > 0) { if
	 * (quantity >= 2) { totalValue += item.getPrice() * 2; quantity -= 2; if
	 * (quantity >= 1) { // Free item for every two items bought quantity--; } }
	 * else { totalValue+=item.getPrice() *quantity; quantity=0; } } } else
	 * if(offers.get(item).equalsIgnoreCase("Buy3Get1")) { while (quantity > 0) { if
	 * (quantity >= 3) { totalValue += item.getPrice() * 3; quantity -= 3; if
	 * (quantity >= 1) { // Free item for every two items bought quantity--; } }
	 * else { totalValue+=item.getPrice() *quantity; quantity=0; } }
	 * 
	 * }else { // No recognized offer totalValue += item.getPrice() * quantity; }
	 * 
	 * } else { totalValue += item.getPrice() * quantity; } }
	 */

	/*
	 * [3] public void add(Inventory item, int quantity) { Map<Inventory, String>
	 * offers = Offers.getOffers(); if (offers.containsKey(item)) { if
	 * (offers.get(item).equalsIgnoreCase("Buy2Get1")) { while (quantity > 0) { if
	 * (quantity >= 2) { totalValue += item.getPrice() * 2; quantity -= 2; if
	 * (quantity >= 1) { // Free item for every two items bought quantity--; } }
	 * else { totalValue+=item.getPrice() *quantity; quantity=0; } } } } else {
	 * totalValue += item.getPrice() * quantity; } }
	 */

}
