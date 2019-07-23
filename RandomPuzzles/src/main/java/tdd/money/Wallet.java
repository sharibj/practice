package tdd.money;

import java.util.ArrayList;
import java.util.List;

import tdd.money.Bank.Currency;

public class Wallet extends Money {

	private List<Money> cashWad;
	private static final String UNSUPPORTED_OPERATION = "This operation is unsupported by wallet";

	public Wallet() {
		super(0);
		cashWad = new ArrayList<Money>();
	}
	
	@Override
	public double getValue() {
		throw new RuntimeException(UNSUPPORTED_OPERATION);
	}

	@Override
	public Money addition(Money addend) {
		cashWad.add(addend);
		return this;
	}

	@Override
	public Money subtract(Money subtrahend) {
		cashWad.remove(subtrahend);
		return this;
	}

	@Override
	public Money multiply(double by) {
		throw new RuntimeException(UNSUPPORTED_OPERATION);
	}

	@Override
	public Money divide(double by) {
		throw new RuntimeException(UNSUPPORTED_OPERATION);
	}

	public List<Money> getAllNotes() {
		return cashWad;
	}

	@Override
	public Money convert(Bank bank, Currency targetCurrency) {
		double value = 0;
		for (Money money : getAllNotes()) {
			value += money.convert(bank, targetCurrency).getValue();
		}
		return bank.getMoneyForCurrency(targetCurrency, value);
	}
}
