package com.missplitty.domain;

import java.util.List;

import com.google.common.collect.Lists;
import com.missplitty.logic.EventCalculationResult;
import com.missplitty.logic.EventStatistics;

public class Event {

	private Integer id;
	private Currency defaultCurrency;
	private List<Participant> participants;
	private List<Expense> expenses;

	public Event(Currency defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
		this.participants = Lists.newArrayList();
		this.expenses = Lists.newArrayList();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Currency getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(Currency defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public void addExpense(Expense expense) {
		this.expenses.add(expense);
	}

	public void addParticipant(Participant participant) {
		this.participants.add(participant);
	}
	
	public EventStatistics getEventStatistics(Currency destinationCurrency) {
		EventStatistics eventStatistics = new EventStatistics(this);
		eventStatistics.calculate(destinationCurrency);
		return eventStatistics;
	}
	
	public EventCalculationResult getCalculationResult(Currency destinationCurrency) {
		EventCalculationResult calculationResult = new EventCalculationResult(this);
		calculationResult.calculate(destinationCurrency);
		return calculationResult;
	}

}
