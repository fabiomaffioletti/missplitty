package com.missplitty.logic;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.missplitty.domain.Amount;
import com.missplitty.domain.Currency;
import com.missplitty.domain.Expense;
import com.missplitty.domain.Participant;
import com.missplitty.domain.Payer;
import com.missplitty.utils.Utils;

public class ExpenseCalculationResult {
	private Map<Participant, Amount> calculationResult;
	private Expense expense;

	public ExpenseCalculationResult(List<Participant> participants, Expense expense) {
		this.expense = expense;
		calculationResult = Maps.newHashMap();
		for (Participant participant : participants) {
			calculationResult.put(participant, new Amount(expense.getAmount().getCurrency()));
		}
	}

	public Map<Participant, Amount> getCalculationResult() {
		return calculationResult;
	}
	
	public void calculate(Currency destinationCurrency) {
		Amount totalPerSharer = Amount.calculateAmountPerSharer(expense);

		for (Payer payer : expense.getPayers()) {
			this.incrementAmount(payer.getParticipant(), payer.getAmount(), destinationCurrency);
		}

		for (Participant sharer : expense.getSharers()) {
			this.incrementAmount(sharer, totalPerSharer, destinationCurrency);
		}
	}

	private void incrementAmount(Participant participant, Amount amount, Currency destinationCurrency) {
		Amount participantAmount = calculationResult.get(participant);
		if(destinationCurrency != null) {
			participantAmount.setCurrency(destinationCurrency);
			Float convertedAmount = Utils.convertFloatToCurrency(amount.getAmount(), amount.getCurrency(), destinationCurrency);
			participantAmount.setAmount(participantAmount.getAmount() + convertedAmount);
		} else {
			participantAmount.setAmount(participantAmount.getAmount() + amount.getAmount());
		}
		calculationResult.put(participant, participantAmount);
	}

	public Amount getExpenseAmount(Participant participant) {
		return calculationResult.get(participant);
	}

	@Override
	public String toString() {
		String out = "";
		for (Participant participant : calculationResult.keySet()) {
			out += participant + ": " + calculationResult.get(participant) + "\n";
		}
		return out;
	}
	
}