package com.missplitty;

import java.util.List;
import java.util.Map;

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
import com.missplitty.logic.Delta;
import com.missplitty.logic.EventCalculationResult;
import com.missplitty.logic.EventStatistics;

public class LondonAndParisTest extends CommonTest {
	private Currency EUR = null;
	private Currency USD = null;
	private Currency GBP = null;
	
	private Event event = null;

	private Participant barney = null;
	private Participant ted = null;
	private Participant robin = null;
	private Participant lili = null;
	private Participant marshall = null;

	private Expense flightTicket = null;
	private Expense acdcAdapter = null;
	private Expense taxi = null;
	private Expense dinner = null;
	private Expense apartment = null;
	private Expense beers = null;
	private Expense suits = null;
	private Expense parisTour = null;
	private Expense souvenirs = null;
	private Expense shoes = null;

	@Before
	public void setUp() {
		EUR = new Currency(1, "EUR");
		USD = new Currency(2, "USD");
		GBP = new Currency(3, "GBP");
		
		event = new Event(EUR);

		barney = new Participant(1, "barney");
		ted = new Participant(2, "ted");
		robin = new Participant(3, "robin");
		lili = new Participant(4, "lili");
		marshall = new Participant(5, "marshall");

		event.addParticipant(barney);
		event.addParticipant(ted);
		event.addParticipant(robin);
		event.addParticipant(lili);
		event.addParticipant(marshall);

		flightTicket = new Expense(USD);
		flightTicket.setAmount(new Amount(2400F, USD));
		flightTicket.setName("flight ticket");
		flightTicket.addPayer(new Payer(barney, flightTicket.getAmount()));
		flightTicket.addSharer(barney);
		flightTicket.addSharer(ted);
		flightTicket.addSharer(robin);
		flightTicket.addSharer(lili);
		flightTicket.addSharer(marshall);
		flightTicket.setExpenseType(Expense.ExpenseType.EXPENSE);

		acdcAdapter = new Expense(USD);
		acdcAdapter.setAmount(new Amount(6F, USD));
		acdcAdapter.setName("ac dc adapter");
		acdcAdapter.addPayer(new Payer(ted, acdcAdapter.getAmount()));
		acdcAdapter.addSharer(barney);
		acdcAdapter.addSharer(ted);
		acdcAdapter.addSharer(robin);
		acdcAdapter.addSharer(lili);
		acdcAdapter.addSharer(marshall);
		acdcAdapter.setExpenseType(Expense.ExpenseType.EXPENSE);
		
		taxi = new Expense(GBP);
		taxi.setAmount(new Amount(45F, GBP));
		taxi.setName("taxi");
		taxi.addPayer(new Payer(robin, taxi.getAmount()));
		taxi.addSharer(barney);
		taxi.addSharer(ted);
		taxi.addSharer(robin);
		taxi.addSharer(lili);
		taxi.addSharer(marshall);
		taxi.setExpenseType(Expense.ExpenseType.EXPENSE);
		
		dinner = new Expense(GBP);
		dinner.setAmount(new Amount(32F, GBP));
		dinner.setName("dinner");
		dinner.addPayer(new Payer(marshall, dinner.getAmount()));
		dinner.addSharer(lili);
		dinner.addSharer(marshall);
		dinner.setExpenseType(Expense.ExpenseType.EXPENSE);
		
		apartment = new Expense(GBP);
		apartment.setAmount(new Amount(350F, GBP));
		apartment.setName("apartment");
		apartment.addPayer(new Payer(robin, apartment.getAmount()));
		apartment.addSharer(barney);
		apartment.addSharer(ted);
		apartment.addSharer(robin);
		apartment.addSharer(lili);
		apartment.addSharer(marshall);
		apartment.setExpenseType(Expense.ExpenseType.EXPENSE);
		
		beers = new Expense(GBP);
		beers.setAmount(new Amount(12F, GBP));
		beers.setName("beers");
		beers.addPayer(new Payer(ted, beers.getAmount()));
		beers.addSharer(barney);
		beers.addSharer(ted);
		beers.addSharer(robin);
		beers.addSharer(lili);
		beers.addSharer(marshall);
		beers.setExpenseType(Expense.ExpenseType.EXPENSE);
		
		suits = new Expense(GBP);
		suits.setAmount(new Amount(700F, GBP));
		suits.setName("suits");
		suits.addPayer(new Payer(barney, suits.getAmount()));
		suits.addSharer(barney);
		suits.addSharer(ted);
		suits.addSharer(robin);
		suits.addSharer(lili);
		suits.addSharer(marshall);
		suits.setExpenseType(Expense.ExpenseType.EXPENSE);
		
		parisTour = new Expense(EUR);
		parisTour.setAmount(new Amount(80F, EUR));
		parisTour.setName("paris tour");
		parisTour.addPayer(new Payer(barney, parisTour.getAmount()));
		parisTour.addSharer(barney);
		parisTour.addSharer(ted);
		parisTour.addSharer(robin);
		parisTour.addSharer(lili);
		parisTour.addSharer(marshall);
		parisTour.setExpenseType(Expense.ExpenseType.EXPENSE);
		
		souvenirs = new Expense(EUR);
		souvenirs.setAmount(new Amount(1000F, EUR));
		souvenirs.setName("souvenirs");
		souvenirs.addPayer(new Payer(lili, souvenirs.getAmount()));
		souvenirs.addSharer(barney);
		souvenirs.addSharer(ted);
		souvenirs.addSharer(robin);
		souvenirs.addSharer(lili);
		souvenirs.addSharer(marshall);
		souvenirs.setExpenseType(Expense.ExpenseType.EXPENSE);
		
		shoes = new Expense(EUR);
		shoes.setAmount(new Amount(320F, EUR));
		shoes.setName("shoes");
		shoes.addPayer(new Payer(lili, shoes.getAmount()));
		shoes.addSharer(robin);
		shoes.addSharer(lili);
		shoes.setExpenseType(Expense.ExpenseType.EXPENSE);

		event.addExpense(flightTicket);
		event.addExpense(acdcAdapter);
		event.addExpense(taxi);
		event.addExpense(dinner);
		event.addExpense(apartment);
		event.addExpense(beers);
		event.addExpense(suits);
		event.addExpense(parisTour);
		event.addExpense(souvenirs);
		event.addExpense(shoes);
	}

	@Test
	public void testGetEventStats() {
		EventStatistics eventStatistics = event.getEventStatistics(null);
		
		Assert.assertEquals(10, eventStatistics.getTotalExpenses().intValue());
		Assert.assertEquals(5, eventStatistics.getTotalParticipants().intValue());
		Assert.assertEquals(1400F, eventStatistics.getTotalSpentByCurrency(EUR));
		Assert.assertEquals(2406F, eventStatistics.getTotalSpentByCurrency(USD));
		Assert.assertEquals(1139F, eventStatistics.getTotalSpentByCurrency(GBP));

		// USD
		Assert.assertEquals(2400F, barney.getTotalSpent().get(USD));
		Assert.assertEquals(6F, ted.getTotalSpent().get(USD));
		
		// GBP
		Assert.assertEquals(700F, barney.getTotalSpent().get(GBP));
		Assert.assertEquals(12F, ted.getTotalSpent().get(GBP));
		Assert.assertEquals(395F, robin.getTotalSpent().get(GBP));
		Assert.assertEquals(32F, marshall.getTotalSpent().get(GBP));
		
		// EUR
		Assert.assertEquals(80F, barney.getTotalSpent().get(EUR));
		Assert.assertEquals(1320F, lili.getTotalSpent().get(EUR));
	}
	
	@Test
	public void testCalculateOnEvent() {
		EventCalculationResult calculationResult = event.getCalculationResult(null);

		Map<Participant, Delta> calculation = calculationResult.getCalculation();
		Assert.assertEquals(1918.8F, calculation.get(barney).getDelta(USD));
		Assert.assertEquals(-475.2F, calculation.get(ted).getDelta(USD));
		Assert.assertEquals(-481.2F, calculation.get(robin).getDelta(USD));
		Assert.assertEquals(-481.2F, calculation.get(marshall).getDelta(USD));
		Assert.assertEquals(-481.2F, calculation.get(lili).getDelta(USD));
		
		Assert.assertEquals(478.6F, calculation.get(barney).getDelta(GBP));
		Assert.assertEquals(-209.4F, calculation.get(ted).getDelta(GBP));
		Assert.assertEquals(173.6F, calculation.get(robin).getDelta(GBP));
		Assert.assertEquals(-205.4F, calculation.get(marshall).getDelta(GBP));
		Assert.assertEquals(-237.4F, calculation.get(lili).getDelta(GBP));
		
		Assert.assertEquals(-136F, calculation.get(barney).getDelta(EUR));
		Assert.assertEquals(-216F, calculation.get(ted).getDelta(EUR));
		Assert.assertEquals(-376F, calculation.get(robin).getDelta(EUR));
		Assert.assertEquals(-216F, calculation.get(marshall).getDelta(EUR));
		Assert.assertEquals(944F, calculation.get(lili).getDelta(EUR));
		
		List<Suggestion> suggestionsEUR = calculationResult.getSuggestions().get(EUR);
		List<Suggestion> suggestionsGBP = calculationResult.getSuggestions().get(GBP);
		List<Suggestion> suggestionsUSD = calculationResult.getSuggestions().get(USD);
		
		Float sumBarneyUSD = 0F;
		Float sumBarneyGBP = 0F;
		Float sumRobinGBP = 0F;
		Float sumLiliEUR = 0F;
		
		for (Suggestion suggestion : suggestionsUSD) {
			if(suggestion.getTo().equals(barney)) {
				sumBarneyUSD += suggestion.getAmount();
			}
		}
		
		for (Suggestion suggestion : suggestionsGBP) {
			if(suggestion.getTo().equals(barney)) {
				sumBarneyGBP += suggestion.getAmount();
			}
			
			if(suggestion.getTo().equals(robin)) {
				sumRobinGBP += suggestion.getAmount();
			}
		}
		
		for (Suggestion suggestion : suggestionsEUR) {
			if(suggestion.getTo().equals(lili)) {
				sumLiliEUR += suggestion.getAmount();
			}
		}
		
		Assert.assertEquals(1918.8F, round(sumBarneyUSD));
		Assert.assertEquals(478.6F, round(sumBarneyGBP));
		Assert.assertEquals(173.6F, round(sumRobinGBP));
		Assert.assertEquals(944F, round(sumLiliEUR));
	}

}
