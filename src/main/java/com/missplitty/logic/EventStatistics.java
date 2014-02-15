package com.missplitty.logic;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.missplitty.domain.Amount;
import com.missplitty.domain.Currency;
import com.missplitty.domain.Event;
import com.missplitty.domain.Expense;
import com.missplitty.domain.Participant;
import com.missplitty.domain.Payer;
import com.missplitty.utils.Utils;

public class EventStatistics {
	private Event event;

	private Integer totalParticipants;
	private Integer totalExpenses;
	private Integer totalCurrencies;

	private Map<Currency, Float> totalSpent;

	public EventStatistics(Event event) {
		this.event = event;
		
		Set<Currency> currencies = Utils.getCurrencies(event);
		
		totalParticipants = event.getParticipants().size();
		totalExpenses = event.getExpenses().size();
		totalCurrencies = currencies.size();
		totalSpent = Maps.newHashMap();

		for (Currency currency : currencies) {
			totalSpent.put(currency, 0F);
		}

	}

	public EventStatistics calculate(Currency destinationCurrency) {
		for (Expense expense : event.getExpenses()) {
			if (!expense.getExpenseType().equals(Expense.ExpenseType.REFUND)) {
				Amount amount = null;
				if(destinationCurrency != null) {
					amount = Amount.convert(expense.getAmount(), destinationCurrency);
				} else {
					amount = expense.getAmount();
				}
				
				this.incrementTotal(amount);

				for (Payer payer : expense.getPayers()) {
					payer.incrementPayer(amount);
				}
				
				for (Participant sharer : expense.getSharers()) {
					sharer.incrementTotalShared();
				}
			}
		}

		return this;
	}

	private void incrementTotal(Amount amount) {
		totalSpent.put(amount.getCurrency(), totalSpent.get(amount.getCurrency()) + amount.getAmount());
	}

	public Map<Currency, Float> getTotalSpent() {
		return totalSpent;
	}

	public Float getTotalSpentByCurrency(Currency currency) {
		return totalSpent.get(currency);
	}

	public Integer getTotalParticipants() {
		return totalParticipants;
	}

	public Integer getTotalExpenses() {
		return totalExpenses;
	}

	public Integer getTotalCurrencies() {
		return totalCurrencies;
	}
	
	@Override
	public String toString() {
		String out = "";
		for (Currency currency : totalSpent.keySet()) {
			out += currency + ": " + totalSpent.get(currency).toString() + "\n";
		}
		return out;
	}

}