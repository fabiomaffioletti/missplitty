package com.missplitty;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.missplitty.domain.Amount;
import com.missplitty.domain.Currency;
import com.missplitty.domain.Event;
import com.missplitty.domain.Expense;
import com.missplitty.domain.Participant;
import com.missplitty.domain.Payer;
import com.missplitty.domain.Suggestion;
import com.missplitty.logic.EventCalculationResult;
import com.missplitty.logic.EventStatistics;
import com.missplitty.logic.ExpenseCalculationResult;

public class MultipleCurrenciesTest extends CommonTest {
	private Currency eur = null;
	private Currency gbp = null;
	private Currency usd = null;
	
	private Event event = null;

	private Participant test1 = null;
	private Participant test2 = null;
	private Participant test3 = null;

	private Expense exp1 = null;
	private Expense exp2 = null;
	private Expense exp3 = null;
	private Expense exp4 = null;
	private Expense exp5 = null;

	@Before
	public void setUp() {
		eur = new Currency(1, "EUR");
		gbp = new Currency(2, "GBP");
		usd = new Currency(3, "USD");
		
		eur.setRatio(1F);
		gbp.setRatio(0.5F);
		usd.setRatio(1.5F);
		
		event = new Event(eur);

		test1 = new Participant(1, "test1");
		test2 = new Participant(2, "test2");
		test3 = new Participant(3, "test3");

		event.addParticipant(test1);
		event.addParticipant(test2);
		event.addParticipant(test3);

		exp1 = new Expense(eur);
		exp1.setAmount(new Amount(100F, eur));
		exp1.setName("exp1");
		exp1.addPayer(new Payer(test1, exp1.getAmount()));
		exp1.addSharer(test1);
		exp1.addSharer(test2);
		exp1.addSharer(test3);
		exp1.setExpenseType(Expense.ExpenseType.EXPENSE);

		exp2 = new Expense(eur);
		exp2.setAmount(new Amount(200F, eur));
		exp2.setName("exp2");
		exp2.addPayer(new Payer(test2, exp2.getAmount()));
		exp2.addSharer(test1);
		exp2.addSharer(test2);
		exp2.addSharer(test3);
		exp2.setExpenseType(Expense.ExpenseType.EXPENSE);
		
		exp3 = new Expense(gbp);
		exp3.setAmount(new Amount(300F, gbp));
		exp3.setName("exp3");
		exp3.addPayer(new Payer(test2, exp3.getAmount()));
		exp3.addSharer(test1);
		exp3.addSharer(test2);
		exp3.addSharer(test3);
		exp3.setExpenseType(Expense.ExpenseType.EXPENSE);
		
		exp4 = new Expense(usd);
		exp4.setAmount(new Amount(400F, usd));
		exp4.setName("exp4");
		exp4.addPayer(new Payer(test3, exp4.getAmount()));
		exp4.addSharer(test1);
		exp4.addSharer(test2);
		exp4.addSharer(test3);
		exp4.setExpenseType(Expense.ExpenseType.EXPENSE);
		
		exp5 = new Expense(usd);
		exp5.setAmount(new Amount(500F, usd));
		exp5.setName("exp5");
		exp5.addPayer(new Payer(test1, exp5.getAmount()));
		exp5.addSharer(test1);
		exp5.addSharer(test2);
		exp5.addSharer(test3);
		exp5.setExpenseType(Expense.ExpenseType.EXPENSE);

		event.addExpense(exp1);
		event.addExpense(exp2);
		event.addExpense(exp3);
		event.addExpense(exp4);
		event.addExpense(exp5);
	}

	@Test
	public void testGetEventStats() {
		EventStatistics eventStatistics = event.getEventStatistics(null);
		
		Assert.assertEquals(5, eventStatistics.getTotalExpenses().intValue());
		Assert.assertEquals(3, eventStatistics.getTotalParticipants().intValue());
		
		// EUR
		Assert.assertEquals(300F, eventStatistics.getTotalSpentByCurrency(eur));
		Assert.assertEquals(100F, test1.getTotalSpent().get(eur));
		Assert.assertEquals(200F, test2.getTotalSpent().get(eur));
		Assert.assertEquals(null, test3.getTotalSpent().get(eur));
		
		// GBP
		Assert.assertEquals(300F, eventStatistics.getTotalSpentByCurrency(gbp));
		Assert.assertEquals(null, test1.getTotalSpent().get(gbp));
		Assert.assertEquals(300F, test2.getTotalSpent().get(gbp));
		Assert.assertEquals(null, test3.getTotalSpent().get(gbp));
		
		// USD
		Assert.assertEquals(900F, eventStatistics.getTotalSpentByCurrency(usd));
		Assert.assertEquals(500F, test1.getTotalSpent().get(usd));
		Assert.assertEquals(null, test2.getTotalSpent().get(usd));
		Assert.assertEquals(400F, test3.getTotalSpent().get(usd));
	}
	
	@Test
	public void testGetConvertedEventStats() {
		EventStatistics eventStatistics = event.getEventStatistics(eur);
		Assert.assertEquals(1500F, eventStatistics.getTotalSpent().get(eur));
		Assert.assertEquals(433.33F, round(test1.getTotalSpent().get(eur)));
		Assert.assertEquals(800F, round(test2.getTotalSpent().get(eur)));
		Assert.assertEquals(266.67F, round(test3.getTotalSpent().get(eur)));
		
		eventStatistics = event.getEventStatistics(usd);
		Assert.assertEquals(2250F, eventStatistics.getTotalSpent().get(usd));
		Assert.assertEquals(650F, round(test1.getTotalSpent().get(usd)));
		Assert.assertEquals(1200F, round(test2.getTotalSpent().get(usd)));
		Assert.assertEquals(400F, round(test3.getTotalSpent().get(usd)));
		
		eventStatistics = event.getEventStatistics(gbp);
		Assert.assertEquals(750F, eventStatistics.getTotalSpent().get(gbp));
		Assert.assertEquals(216.67F, round(test1.getTotalSpent().get(gbp)));
		Assert.assertEquals(400F, round(test2.getTotalSpent().get(gbp)));
		Assert.assertEquals(133.33F, round(test3.getTotalSpent().get(gbp)));
	}
	
	@Test
	public void testConvertExpenseToOriginalCurrency() {
		ExpenseCalculationResult expenseCalculationResult = exp1.getCalculationResult(event.getParticipants(), eur);
		
		Assert.assertEquals(66.67F, round(expenseCalculationResult.getExpenseAmount(test1).getAmount()));
		Assert.assertEquals(-33.33F, round(expenseCalculationResult.getExpenseAmount(test2).getAmount()));
		Assert.assertEquals(-33.33F, round(expenseCalculationResult.getExpenseAmount(test3).getAmount()));
		
		Assert.assertEquals(eur, expenseCalculationResult.getExpenseAmount(test1).getCurrency());
		Assert.assertEquals(eur, expenseCalculationResult.getExpenseAmount(test2).getCurrency());
		Assert.assertEquals(eur, expenseCalculationResult.getExpenseAmount(test3).getCurrency());
	}
	
	@Test
	public void testConvertEURExpenseToUSD() {
		ExpenseCalculationResult expenseCalculationResult = exp1.getCalculationResult(event.getParticipants(), usd);
		
		Assert.assertEquals(100F, round(expenseCalculationResult.getExpenseAmount(test1).getAmount()));
		Assert.assertEquals(-50F, round(expenseCalculationResult.getExpenseAmount(test2).getAmount()));
		Assert.assertEquals(-50F, round(expenseCalculationResult.getExpenseAmount(test3).getAmount()));
		
		Assert.assertEquals(usd, expenseCalculationResult.getExpenseAmount(test1).getCurrency());
		Assert.assertEquals(usd, expenseCalculationResult.getExpenseAmount(test2).getCurrency());
		Assert.assertEquals(usd, expenseCalculationResult.getExpenseAmount(test3).getCurrency());
	}
	
	@Test
	public void testConvertUSDToEUR() {
		ExpenseCalculationResult expenseCalculationResult = exp5.getCalculationResult(event.getParticipants(), null);
		
		Assert.assertEquals(333.33F, round(expenseCalculationResult.getExpenseAmount(test1).getAmount()));
		Assert.assertEquals(-166.67F, round(expenseCalculationResult.getExpenseAmount(test2).getAmount()));
		Assert.assertEquals(-166.67F, round(expenseCalculationResult.getExpenseAmount(test3).getAmount()));
		Assert.assertEquals(usd, expenseCalculationResult.getExpenseAmount(test1).getCurrency());
		Assert.assertEquals(usd, expenseCalculationResult.getExpenseAmount(test2).getCurrency());
		Assert.assertEquals(usd, expenseCalculationResult.getExpenseAmount(test3).getCurrency());
		
		expenseCalculationResult = exp5.getCalculationResult(event.getParticipants(), eur);
		Assert.assertEquals(222.22F, round(expenseCalculationResult.getExpenseAmount(test1).getAmount()));
		Assert.assertEquals(-111.11F, round(expenseCalculationResult.getExpenseAmount(test2).getAmount()));
		Assert.assertEquals(-111.11F, round(expenseCalculationResult.getExpenseAmount(test3).getAmount()));
		Assert.assertEquals(eur, expenseCalculationResult.getExpenseAmount(test1).getCurrency());
		Assert.assertEquals(eur, expenseCalculationResult.getExpenseAmount(test2).getCurrency());
		Assert.assertEquals(eur, expenseCalculationResult.getExpenseAmount(test3).getCurrency());
	}
	
	@Test
	public void testConvertEvent() {
		EventCalculationResult eventCalculationResult = event.getCalculationResult(null);
		
		Assert.assertEquals(0F, round(eventCalculationResult.getCalculation().get(test1).getDelta(eur)));
		Assert.assertEquals(100F, round(eventCalculationResult.getCalculation().get(test2).getDelta(eur)));
		Assert.assertEquals(-100F, round(eventCalculationResult.getCalculation().get(test3).getDelta(eur)));
		
		Assert.assertEquals(-100F, round(eventCalculationResult.getCalculation().get(test1).getDelta(gbp)));
		Assert.assertEquals(200F, round(eventCalculationResult.getCalculation().get(test2).getDelta(gbp)));
		Assert.assertEquals(-100F, round(eventCalculationResult.getCalculation().get(test3).getDelta(gbp)));
		
		Assert.assertEquals(200F, round(eventCalculationResult.getCalculation().get(test1).getDelta(usd)));
		Assert.assertEquals(-300F, round(eventCalculationResult.getCalculation().get(test2).getDelta(usd)));
		Assert.assertEquals(100F, round(eventCalculationResult.getCalculation().get(test3).getDelta(usd)));
		
		eventCalculationResult = event.getCalculationResult(eur);
		Assert.assertEquals(-66.67F, round(eventCalculationResult.getCalculation().get(test1).getDelta(eur)));
		Assert.assertEquals(300F, round(eventCalculationResult.getCalculation().get(test2).getDelta(eur)));
		Assert.assertEquals(-233.33F, round(eventCalculationResult.getCalculation().get(test3).getDelta(eur)));
		
		List<Suggestion> suggestions = eventCalculationResult.getSuggestions().get(eur);
		Assert.assertEquals(2, suggestions.size());
		for (Suggestion suggestion : suggestions) {
			if(suggestion.getFrom().equals(test1) && suggestion.getTo().equals(test2)) {
				Assert.assertEquals(66.67F, round(suggestion.getAmount()));
			}
			if(suggestion.getFrom().equals(test3) && suggestion.getTo().equals(test2)) {
				Assert.assertEquals(233.33F, round(suggestion.getAmount()));
			}
		}
	}
	
}
