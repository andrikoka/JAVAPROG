package hu.uni.miskolc.iit.java.technologies.maintenance.api.model;

import java.util.Currency;

public class Price {

	private double amount;
	private Currency currency;

	public Price() {
		super();
	}

	public Price(double amount, Currency currency) {
		super();
		this.amount = amount;
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	@Override
	public String toString() {
		return "Price [amount=" + amount + ", currency=" + currency + "]";
	}

}
