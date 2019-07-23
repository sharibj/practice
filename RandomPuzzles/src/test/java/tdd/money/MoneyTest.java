package tdd.money;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tdd.money.Bank.Currency;

public class MoneyTest {

	/**
	 * 1$ = 68₹
	 * 
	 * [done] 1 - 10$ = 5$+5$
	 * [done] 2 - 10$ = 5$*2
	 * [done] 3 - 10$ = 20$/2
	 * [done] 4 - 10$ = 15$-5$
	 * [done] 5 - 1$ = 68₹
	 * [done] 6 - 2$ = 1$+68₹
	 * 
	 * [done] viceVersa exchange rates
	 * [done] Money returning dollar ?
	 * [done] accept generic values?
	 * [done] Test for generics?
	 * [done] int 10 + double 5 = 200
	 * [done - thanks to autoboxing] accept primitive values
	 * [X]sum of Integer should return Integer
	 * [done] store rate in a better way thought of a design change ..
	 * [X] Any operation on a note should give me a wallet (Or just multicurrency maybe
	 * [done] decided to favour double over generics for value
	 * wallet as seperate class?
	 */

	@Test
	public void testAdditionOfSameCurrency() {
		Money dollar10 = new Dollar(Double.valueOf(10));
		Money dollar5 = new Dollar(Double.valueOf(5));
		assertTrue(dollar10.equals(dollar5.addition(dollar5)));
	}

	@Test
	public void testMultiplicationOfSameCurrency() {
		Money dollar10 = new Dollar(Double.valueOf(10));
		Money dollar5 = new Dollar(Double.valueOf(5));
		assertTrue(dollar10.equals(dollar5.multiply(Double.valueOf(2))));
	}

	@Test
	public void testDivisionOfSameCurrency() {
		Money dollar10 = new Dollar(Double.valueOf(10));
		Money dollar20 = new Dollar(Double.valueOf(20));
		assertTrue(dollar10.equals(dollar20.divide(Double.valueOf(2))));
	}

	@Test
	public void testGenericsUsage() {
		Money dollar10 = new Dollar(Integer.valueOf(10));
		Money dollar5 = new Dollar(Double.valueOf(5));
		assertTrue(dollar10.equals(dollar5.addition(dollar5)));
		// Integer value = (Integer) dollar10.multiply(2).getValue();
	}

	@Test
	public void testPrimitiveValues() {
		Money dollar10 = new Dollar(10);
		Money dollar5 = new Dollar(5);
		assertTrue(dollar10.equals(dollar5.addition(dollar5)));
	}

	@Test
	public void testSubtractionOfSameCurrency() {
		Money dollar10 = new Dollar(10);
		Money dollar15 = new Dollar(15);
		Money dollar5 = new Dollar(5);
		assertTrue(dollar10.equals(dollar15.subtract(dollar5)));
	}

	@Test
	public void testTwoDifferentCurrencyComparison() {
		Money dollar1 = new Dollar(1);
		Money rupee68 = new Rupee(68);
		Money rupee1 = new Rupee(1);
		Bank myBank = new Bank();
		myBank.addExchangeRate(Currency.INR, Currency.USD, 68);
		Money dollarConverted = rupee68.convert(myBank, Currency.USD);
		assertTrue(dollar1.equals(dollarConverted));
		assertFalse(dollar1.equals(rupee1));
		assertFalse(dollar1.equals(rupee68));
	}


	@Test
	public void testMultiCurrencyOperations() {
		Money dollar1 = new Dollar(1);
		Money dollar2 = new Dollar(2);
		Money rupee68 = new Rupee(68);
		Wallet wallet = new Wallet();
		wallet.addition(dollar1).addition(rupee68);
		Bank myBank = new Bank();
		myBank.addExchangeRate(Currency.INR, Currency.USD, 68);
		Money dollarConverted = wallet.convert(myBank, Currency.USD);
		assertTrue(dollar2.equals(dollarConverted));
		Money convertedRupee = dollar1.convert(myBank, Currency.INR);
		assertTrue(rupee68.equals(convertedRupee));
	}
	
	@Test
	public void testDifferentCurrenciesAreCreated() {
		Money dollar2 = new Dollar(2);
		Money rupee68 = new Rupee(68);
		Bank bank = new Bank();
		bank.addExchangeRate(Currency.INR, Currency.USD, 2);
		Money wallet = new Wallet();
		wallet.addition(dollar2).addition(rupee68);
		Money sum = wallet.convert(bank, Currency.INR);
		assertTrue(sum instanceof Rupee);
	}

}
