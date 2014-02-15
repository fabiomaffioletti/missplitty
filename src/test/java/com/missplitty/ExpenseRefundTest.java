package com.missplitty;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.missplitty.domain.Amount;
import com.missplitty.domain.Currency;
import com.missplitty.domain.Event;
import com.missplitty.domain.Expense;
import com.missplitty.domain.Participant;
import com.missplitty.domain.Payer;
import com.missplitty.logic.EventCalculationResult;
import com.missplitty.logic.EventStatistics;

public class ExpenseRefundTest extends CommonTest {
	private Currency EUR = null;
	private Currency USD = null;
	
	private Event event = null;

	private Participant test1 = null;
	private Participant test2 = null;
	private Participant test3 = null;

	private Expense exp1 = null;
	private Expense exp2 = null;
	private Expense exp3 = null;

	@Before
	public void setUp() {
		EUR = new Currency(1, "EUR");
		EUR.setRatio(1F);
		USD = new Currency(2, "USD");
		USD.setRatio(1.5F);
		
		event = new Event(EUR);

		test1 = new Participant(1, "test1");
		test2 = new Participant(2, "test2");
		test3 = new Participant(3, "test3");

		event.addParticipant(test1);
		event.addParticipant(test2);
		event.addParticipant(test3);

		exp1 = new Expense(EUR);
		exp1.setAmount(new Amount(100F, EUR));
		exp1.setName("exp1");
		exp1.addPayer(new Payer(test1, exp1.getAmount()));
		exp1.addSharer(test1);
		exp1.addSharer(test2);
		exp1.addSharer(test3);
		exp1.setExpenseType(Expense.ExpenseType.EXPENSE);

		exp2 = new Expense(EUR);
		exp2.setAmount(new Amount(50F, EUR));
		exp2.setName("exp2");
		exp2.addPayer(new Payer(test2, exp2.getAmount()));
		exp2.addSharer(test1);
		exp2.setExpenseType(Expense.ExpenseType.REFUND);
		
		exp3 = new Expense(EUR);
		exp3.setAmount(new Amount(20F, EUR));
		exp3.setName("exp3");
		exp3.addPayer(new Payer(test3, exp3.getAmount()));
		exp3.addSharer(test1);
		exp3.setExpenseType(Expense.ExpenseType.REFUND);
		
		event.addExpense(exp1);
		event.addExpense(exp2);
		event.addExpense(exp3);
	}

	@Test
	public void testGetEventStats() {
		EventStatistics eventStatistics = event.getEventStatistics(null);
		
		Assert.assertEquals(3, eventStatistics.getTotalExpenses().intValue());
		Assert.assertEquals(3, eventStatistics.getTotalParticipants().intValue());
		
		Assert.assertEquals(100F, eventStatistics.getTotalSpentByCurrency(EUR));
		Assert.assertEquals(100F, test1.getTotalSpent().get(EUR));
		Assert.assertEquals(null, test2.getTotalSpent().get(EUR));
		Assert.assertEquals(null, test3.getTotalSpent().get(EUR));
	}
	
	@Test
	public void testCalculateAndConvertEvent() {
		EventCalculationResult eventCalculationResult = event.getCalculationResult(null);
		Assert.assertEquals(-3.33F, round(eventCalculationResult.getCalculation().get(test1).getDelta(EUR)));
		Assert.assertEquals(16.67F, round(eventCalculationResult.getCalculation().get(test2).getDelta(EUR)));
		Assert.assertEquals(-13.33F, round(eventCalculationResult.getCalculation().get(test3).getDelta(EUR)));
		
		eventCalculationResult = event.getCalculationResult(USD);
		Assert.assertEquals(-5F, round(eventCalculationResult.getCalculation().get(test1).getDelta(USD)));
		Assert.assertEquals(25F, round(eventCalculationResult.getCalculation().get(test2).getDelta(USD)));
		Assert.assertEquals(-20F, round(eventCalculationResult.getCalculation().get(test3).getDelta(USD)));
	}

}
