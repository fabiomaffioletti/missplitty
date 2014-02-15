package com.missplitty.logic;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.missplitty.domain.Amount;
import com.missplitty.domain.Currency;
import com.missplitty.domain.Event;
import com.missplitty.domain.Expense;
import com.missplitty.domain.Participant;
import com.missplitty.domain.Suggestion;
import com.missplitty.utils.Utils;

public class EventCalculationResult {

	private Map<Participant, Delta> calculation;
	private Map<Currency, List<Suggestion>> suggestions;

	private List<Participant> participants;
	private List<Expense> expenses;
	private Set<Currency> currencies;

	public EventCalculationResult() {
	}

	public EventCalculationResult(Event event) {
		calculation = Maps.newHashMap();
		suggestions = Maps.newHashMap();
		this.currencies = Utils.getCurrencies(event);
		this.participants = event.getParticipants();
		this.expenses = event.getExpenses();
	}

	public void calculate(Currency destinationCurrency) {
		for (Participant participant : participants) {
			calculation.put(participant, new Delta(currencies));
		}
		
		for (Expense expense : expenses) {
			ExpenseCalculationResult expenseCalculation = expense.getCalculationResult(participants, destinationCurrency);

			for (Participant participant : expenseCalculation.getCalculationResult().keySet()) {
				Amount amount = expenseCalculation.getCalculationResult().get(participant);
				calculation.put(participant, calculation.get(participant).calculate(amount));
			}
		}
		
		Map<Currency, Map<Participant, Float>> credits = Maps.newHashMap();
		Map<Currency, Map<Participant, Float>> debits = Maps.newHashMap();

		for (Currency currency : currencies) {
			Map<Participant, Float> creditors = Maps.newHashMap();
			Map<Participant, Float> debitors = Maps.newHashMap();
			List<Suggestion> suggests = Lists.newArrayList();
			credits.put(currency, creditors);
			debits.put(currency, debitors);
			suggestions.put(currency, suggests);

			for (Participant participant : participants) {
				Float amount = calculation.get(participant).getDelta(currency);
				if (amount > 0) {
					creditors.put(participant, amount);
				} else if (amount < 0) {
					debitors.put(participant, -amount);
				}
			}

//			System.out.println(credits);
//			System.out.println(debits);

			for (Participant creditor : creditors.keySet()) {
				for (Participant debitor : debitors.keySet()) {
					if (creditors.get(creditor) > 0 && debitors.get(debitor) > 0) {
						if (debitors.get(debitor) <= creditors.get(creditor)) {
							creditors.put(creditor, creditors.get(creditor) - debitors.get(debitor));
							suggests.add(new Suggestion(debitor, creditor, debitors.get(debitor)));
							debitors.put(debitor, 0F);
						} else {
							debitors.put(debitor, debitors.get(debitor) - creditors.get(creditor));
							suggests.add(new Suggestion(debitor, creditor, creditors.get(creditor)));
							creditors.put(creditor, 0F);
						}
					}
				}
			}

			suggestions.put(currency, suggests);
		}

//		System.out.println(suggestions);
	}

	public Map<Participant, Delta> getCalculation() {
		return calculation;
	}

	public Map<Currency, List<Suggestion>> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(Map<Currency, List<Suggestion>> suggestions) {
		this.suggestions = suggestions;
	}

}
