package tdd.money;

import java.util.HashMap;
import java.util.Map;

import tdd.money.Bank.Currency;

public class Bank {
	// Supported currency enum
	public static enum Currency {
		USD, INR;
	}

	// Exchange rate map
	private Map<ExchangeRateKey, Double> exchangeRates;
	private static final String UNSUPPORTED_CURRENCY = "Unsupported Currency Type";

	// Key for Exchange rate map
	private class ExchangeRateKey {
		private Currency from;
		private Currency to;

		ExchangeRateKey(Currency from, Currency to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public boolean equals(Object arg0) {
			if (arg0 instanceof ExchangeRateKey) {
				ExchangeRateKey key = (ExchangeRateKey) arg0;
				return key.from == this.from && key.to == this.to;
			}
			return false;
		}

		@Override
		public int hashCode() {
			// TODO Improve hashcode
			return 0;
		}
	}

	public Bank() {
		exchangeRates = new HashMap<>();
	}

	public Double getExchangeRate(Currency from, Currency to) {
		Double rate;
		if (to == from) {
			rate = 1.0;
		} else {
			ExchangeRateKey key = new ExchangeRateKey(from, to);
			rate = exchangeRates.get(key);
			if (rate == null) {
				// try to get reverse exchange
				key = new ExchangeRateKey(to, from);
				rate = exchangeRates.get(key);
				if (rate != null) {
					rate = 1 / rate;
				} else {
					throw new RuntimeException("No exchange rate found for "+from+" to "+to);
				}
			}
		}
		return rate;
	}

	public void addExchangeRate(Currency currencyFrom, Currency currencyTo, double rate) {
		ExchangeRateKey key = new ExchangeRateKey(currencyFrom, currencyTo);
		exchangeRates.put(key, rate);
	}

	public Money getMoneyForCurrency(Currency currency, double value) {
		switch (currency) {
		case USD:
			return new Dollar(value);
		case INR:
			return new Rupee(value);
		default:
			throw new RuntimeException(UNSUPPORTED_CURRENCY);
		}
	}

	public Currency getCurrencyForMoney(Money money) {
		if (money instanceof Dollar) {
			return Currency.USD;
		} else if (money instanceof Rupee) {
			return Currency.INR;
		} else {
			throw new RuntimeException(UNSUPPORTED_CURRENCY);
		}
	}

}
