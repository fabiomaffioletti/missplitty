package com.missplitty.domain;

import com.google.common.collect.Lists;
import com.missplitty.logic.EventCalculationResult;
import com.missplitty.logic.EventStatistics;
import lombok.Data;

import java.util.List;

@Data
public class Event {

	private Integer id;
	private String name;
	private Currency defaultCurrency;
	private List<Participant> participants;
	private List<Expense> expenses;

	public Event(Currency defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
		this.participants = Lists.newArrayList();
		this.expenses = Lists.newArrayList();
	}

	public void addParticipant(Participant p) {
		participants.add(p);
	}

	public void addExpense(Expense e) {
		expenses.add(e);
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
