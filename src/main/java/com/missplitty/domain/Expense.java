package com.missplitty.domain;

import java.util.List;

import lombok.Data;
import lombok.Singular;
import org.joda.time.DateTime;

import com.google.common.collect.Lists;
import com.missplitty.logic.ExpenseCalculationResult;

@Data
public class Expense {

	public enum ExpenseType {
		EXPENSE, REFUND
	}

	private Integer id;
	private String name;
	private DateTime datetime;
	private ExpenseType expenseType;
	private Amount amount;
	private List<Payer> payers;
	private List<Participant> sharers;

	public Expense(Currency defaultCurrency) {
		this.amount = new Amount(defaultCurrency);
		this.expenseType = ExpenseType.EXPENSE;
		this.payers = Lists.newArrayList();
		this.sharers = Lists.newArrayList();
	}

	public void addPayer(Payer p) {
		payers.add(p);
	}

	public void addSharer(Participant p) {
		sharers.add(p);
	}

	public ExpenseCalculationResult getCalculationResult(List<Participant> participants, Currency destinationCurrency) {
		ExpenseCalculationResult expenseCalculationResult = new ExpenseCalculationResult(participants, this);
		expenseCalculationResult.calculate(destinationCurrency);
		return expenseCalculationResult;
	}
	
}
