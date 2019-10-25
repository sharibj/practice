package tdd.shopping;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import tdd.shopping.offers.BuyTotalGreaterThanXGetYPercentOff;
import tdd.shopping.offers.BuyXgetY;
import tdd.shopping.offers.BuyXgetYpercentOffOnNextZ;

public class ShoppingTest {
	@Test
	public void when5DoveSoapsAreAdded_thenCartValueIs50() {
		// Given
		Cart myCart = new Cart();
		// When
		myCart.add(Inventory.DoveSoap, 5);
		// Then
		assertEquals(50.0, myCart.total(), 0);
	}

	@Test
	public void when5DoveSoapsAnd3AxeDeoAreAdded_thenCartValueIs95() {
		// Given
		Cart myCart = new Cart();
		// When
		myCart.add(Inventory.DoveSoap, 5);
		myCart.add(Inventory.AxeDeo, 3);
		// Then
		assertEquals(95.0, myCart.total(), 0);
	}

	@Test
	public void whenDoveSoapHasB2G1Offer_and_5DoveSoapsAreAdded_thenCartValueIs40() {
		// Given
		Cart myCart = new Cart();
		Offers.addOffer(Inventory.DoveSoap, new BuyXgetY(2, 1));
		// When
		myCart.add(Inventory.DoveSoap, 5);
		// Then
		assertEquals(40.0, myCart.total(), 0);
	}

	@Test
	public void whenDoveSoapHasB2G1Offer_and_axeHasB3G1Offer_and_5DoveSoapsAreAddedAnd3AxeDeoAreAdded_thenCartValueIs100() {
		// Given
		Cart myCart = new Cart();
		Offers.addOffer(Inventory.DoveSoap, new BuyXgetY(2, 1));
		Offers.addOffer(Inventory.AxeDeo, new BuyXgetY(3, 1));
		// When
		myCart.add(Inventory.DoveSoap, 5);
		myCart.add(Inventory.AxeDeo, 5);
		// Then
		assertEquals(100.0, myCart.total(), 0);
	}

	@Test
	public void whenColgateHasB2G10percentOffOnThirdOffer_and_5colgateAreAdded_thenCartValueIs49() {
		// Given
		Cart myCart = new Cart();
		Offers.addOffer(Inventory.Colgate, new BuyXgetYpercentOffOnNextZ(2, 10, 1));
		// When
		myCart.add(Inventory.Colgate, 5);
		// Then
		assertEquals(49.0, myCart.total(), 0);
	}

	@Test
	public void whenTotalCartValueIsGreaterThan30_thenAdditional10PercentDiscountIsApplied() {
		// Given
		Cart myCart = new Cart();
		Offers.addOffer(Inventory.Colgate, new BuyXgetYpercentOffOnNextZ(2, 10, 1));
		myCart.addOffer(new BuyTotalGreaterThanXGetYPercentOff());
		// When
		myCart.add(Inventory.Colgate, 5);
		// Then
		assertEquals(44.1, myCart.total(), 0);
	}
}
