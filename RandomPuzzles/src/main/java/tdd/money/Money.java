package tdd.money;

import tdd.money.Bank.Currency;

public class Money {

	private final double value;
	private static final String USE_WALLET_MESSAGE = "Can't operate on different currencies. Use a wallet";

	public Money(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public Money addition(Money addend) {
		if (this.getClass() == addend.getClass()) {
			double value = this.getValue() + addend.getValue();
			return getCloneWithGivenValue(value);
		}
		throw new RuntimeException(USE_WALLET_MESSAGE);
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			Money money = (Money) obj;
			return getValue() == money.getValue();
		} else {
			return false;
		}
	}

	public Money multiply(double by) {
		double value = this.value * by;
		return getCloneWithGivenValue(value);
	}

	public Money divide(double by) {
		double value = this.value / by;
		return getCloneWithGivenValue(value);
	}

	public Money subtract(Money Subtrahend) {
		if (this.getClass() == Subtrahend.getClass()) {
			double value = this.getValue() - Subtrahend.getValue();
			return getCloneWithGivenValue(value);
		}
		throw new RuntimeException(USE_WALLET_MESSAGE);
	}

	private Money getCloneWithGivenValue(double value) {
		Money money;
		if (this instanceof Dollar) {
			money = new Dollar(value);
		} else if (this instanceof Rupee) {
			money = new Rupee(value);
		} else {
			money = new Money(value);
		}
		return money;
	}

	public Money convert(Bank bank, Currency targetCurrency) {
		Double exchangeRate = bank.getExchangeRate(bank.getCurrencyForMoney(this), targetCurrency);
		if (exchangeRate == null) {
			throw new RuntimeException("Couldn't get excehange rate for this conversion");
		}
		double value = this.getValue() / exchangeRate;
		return bank.getMoneyForCurrency(targetCurrency, value);
	}
}
