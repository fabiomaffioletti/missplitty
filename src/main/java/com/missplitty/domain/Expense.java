package com.missplitty.domain;

import java.util.List;

import org.joda.time.DateTime;

import com.google.common.collect.Lists;
import com.missplitty.logic.ExpenseCalculationResult;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(DateTime datetime) {
		this.datetime = datetime;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public List<Payer> getPayers() {
		return payers;
	}

	public void setPayers(List<Payer> payers) {
		this.payers = payers;
	}

	public List<Participant> getSharers() {
		return sharers;
	}

	public void setSharers(List<Participant> sharers) {
		this.sharers = sharers;
	}

	public void addPayer(Payer payer) {
		this.payers.add(payer);
	}

	public void addSharer(Participant sharer) {
		this.sharers.add(sharer);
	}

	public ExpenseCalculationResult getCalculationResult(List<Participant> participants, Currency destinationCurrency) {
		ExpenseCalculationResult expenseCalculationResult = new ExpenseCalculationResult(participants, this);
		expenseCalculationResult.calculate(destinationCurrency);
		return expenseCalculationResult;
	}
	
}
