package com.missplitty.domain;

import com.missplitty.utils.Utils;
import lombok.Data;

@Data
public class Amount {
	private Float amount;
	private Currency currency;
	
	public Amount(Currency currency) {
		this.amount = 0F;
		this.currency = currency;
	}

	public Amount(Float amount, Currency currency) {
		super();
		this.amount = amount;
		this.currency = currency;
	}

	public static Amount convert(Amount amount, Currency destinationCurrency) {
		return new Amount(Utils.convertFloatToCurrency(amount.getAmount(), amount.getCurrency(), destinationCurrency), destinationCurrency);
	}
	
	public static Amount calculateAmountPerSharer(Expense expense, Currency destinationCurrency) {
		if(destinationCurrency != null) {
			Float convertedAmount = Utils.convertFloatToCurrency(expense.getAmount().getAmount(), expense.getAmount().getCurrency(), destinationCurrency);
			return new Amount(- convertedAmount / expense.getSharers().size(), destinationCurrency);
		} else {
			return new Amount(- expense.getAmount().getAmount() / expense.getSharers().size(), expense.getAmount().getCurrency());
		}
	}
	
	public static Amount calculateAmountPerSharer(Expense expense) {
		return new Amount(- expense.getAmount().getAmount() / expense.getSharers().size(), expense.getAmount().getCurrency());
	}
	
	@Override
	public String toString() {
		return "ExpenseAmount [amount=" + amount + ", currency=" + currency + "]";
	}
	
}