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
import com.missplitty.logic.ExpenseCalculationResult;

public class ItalianVacationTest extends CommonTest {
	private Currency eur = null;
	
	private Event event = null;

	private Participant obiwan = null;
	private Participant marty = null;
	private Participant tarzan = null;
	private Participant fonzie = null;
	private Participant jane = null;
	private Participant chunli = null;
	private Participant zelda = null;
	private Participant uga = null;

	private Expense pizza = null;
	private Expense hotel = null;
	private Expense colosseum = null;
	private Expense icecream = null;
	private Expense car = null;
	private Expense fuel = null;
	private Expense highway = null;
	private Expense sandwiches = null;
	private Expense restaurant = null;
	private Expense coffee = null;

	@Before
	public void setUp() {
		eur = new Currency(1, "EUR");
		
		event = new Event(eur);

		obiwan = new Participant(1, "obiwan");
		marty = new Participant(2, "marty");
		tarzan = new Participant(3, "tarzan");
		fonzie = new Participant(4, "fonzie");
		jane = new Participant(5, "jane");
		chunli = new Participant(6, "chunli");
		zelda = new Participant(7, "zelda");
		uga = new Participant(8, "uga");

		event.addParticipant(obiwan);
		event.addParticipant(marty);
		event.addParticipant(tarzan);
		event.addParticipant(fonzie);
		event.addParticipant(jane);
		event.addParticipant(chunli);
		event.addParticipant(zelda);
		event.addParticipant(uga);

		pizza = new Expense(eur);
		pizza.setAmount(new Amount(247F, eur));
		pizza.setName("pizza");
		pizza.addPayer(new Payer(marty, pizza.getAmount()));
		pizza.addSharer(obiwan);
		pizza.addSharer(marty);
		pizza.addSharer(tarzan);
		pizza.addSharer(fonzie);
		pizza.addSharer(jane);
		pizza.addSharer(chunli);
		pizza.addSharer(zelda);
		pizza.addSharer(uga);
		pizza.setExpenseType(Expense.ExpenseType.EXPENSE);

		hotel = new Expense(eur);
		hotel.setAmount(new Amount(400F, eur));
		hotel.setName("hotel");
		hotel.addPayer(new Payer(tarzan, hotel.getAmount()));
		hotel.addSharer(obiwan);
		hotel.addSharer(marty);
		hotel.addSharer(tarzan);
		hotel.addSharer(fonzie);
		hotel.addSharer(jane);
		hotel.addSharer(chunli);
		hotel.addSharer(zelda);
		hotel.addSharer(uga);
		hotel.setExpenseType(Expense.ExpenseType.EXPENSE);

		colosseum = new Expense(eur);
		colosseum.setAmount(new Amount(88F, eur));
		colosseum.setName("colosseum");
		colosseum.addPayer(new Payer(chunli, colosseum.getAmount()));
		colosseum.addSharer(obiwan);
		colosseum.addSharer(marty);
		colosseum.addSharer(tarzan);
		colosseum.addSharer(fonzie);
		colosseum.addSharer(jane);
		colosseum.addSharer(chunli);
		colosseum.addSharer(zelda);
		colosseum.addSharer(uga);
		colosseum.setExpenseType(Expense.ExpenseType.EXPENSE);

		icecream = new Expense(eur);
		icecream.setAmount(new Amount(3.8F, eur));
		icecream.setName("icecream");
		icecream.addPayer(new Payer(marty, icecream.getAmount()));
		icecream.addSharer(obiwan);
		icecream.addSharer(jane);
		icecream.setExpenseType(Expense.ExpenseType.EXPENSE);

		car = new Expense(eur);
		car.setAmount(new Amount(115F, eur));
		car.setName("car");
		car.addPayer(new Payer(obiwan, car.getAmount()));
		car.addSharer(obiwan);
		car.addSharer(fonzie);
		car.addSharer(zelda);
		car.addSharer(uga);
		car.setExpenseType(Expense.ExpenseType.EXPENSE);

		fuel = new Expense(eur);
		fuel.setAmount(new Amount(30F, eur));
		fuel.setName("fuel");
		fuel.addPayer(new Payer(zelda, fuel.getAmount()));
		fuel.addSharer(obiwan);
		fuel.addSharer(fonzie);
		fuel.addSharer(zelda);
		fuel.addSharer(uga);
		fuel.setExpenseType(Expense.ExpenseType.EXPENSE);

		highway = new Expense(eur);
		highway.setAmount(new Amount(5F, eur));
		highway.setName("highway");
		highway.addPayer(new Payer(uga, highway.getAmount()));
		highway.addSharer(obiwan);
		highway.addSharer(fonzie);
		highway.addSharer(zelda);
		highway.addSharer(uga);
		highway.setExpenseType(Expense.ExpenseType.EXPENSE);

		sandwiches = new Expense(eur);
		sandwiches.setAmount(new Amount(9F, eur));
		sandwiches.setName("sandwiches");
		sandwiches.addPayer(new Payer(uga, sandwiches.getAmount()));
		sandwiches.addSharer(obiwan);
		sandwiches.addSharer(fonzie);
		sandwiches.addSharer(zelda);
		sandwiches.addSharer(uga);
		sandwiches.setExpenseType(Expense.ExpenseType.EXPENSE);

		restaurant = new Expense(eur);
		restaurant.setAmount(new Amount(225F, eur));
		restaurant.setName("restaurant");
		restaurant.addPayer(new Payer(uga, restaurant.getAmount()));
		restaurant.addSharer(obiwan);
		restaurant.addSharer(marty);
		restaurant.addSharer(tarzan);
		restaurant.addSharer(fonzie);
		restaurant.addSharer(jane);
		restaurant.addSharer(chunli);
		restaurant.addSharer(zelda);
		restaurant.addSharer(uga);
		restaurant.setExpenseType(Expense.ExpenseType.EXPENSE);

		coffee = new Expense(eur);
		coffee.setAmount(new Amount(2F, eur));
		coffee.setName("coffee");
		coffee.addPayer(new Payer(marty, coffee.getAmount()));
		coffee.addSharer(obiwan);
		coffee.addSharer(marty);
		coffee.setExpenseType(Expense.ExpenseType.EXPENSE);

		event.addExpense(pizza);
		event.addExpense(hotel);
		event.addExpense(colosseum);
		event.addExpense(icecream);
		event.addExpense(car);
		event.addExpense(fuel);
		event.addExpense(highway);
		event.addExpense(sandwiches);
		event.addExpense(restaurant);
		event.addExpense(coffee);
	}

	@Test
	public void testGetEventStatistics() {
		EventStatistics eventStatistics = event.getEventStatistics(null);
		
		Assert.assertEquals(10, eventStatistics.getTotalExpenses().intValue());
		Assert.assertEquals(8, eventStatistics.getTotalParticipants().intValue());
		Assert.assertEquals(1124.8F, eventStatistics.getTotalSpentByCurrency(eur));
		
		Assert.assertEquals(115F, obiwan.getTotalSpent().get(eur));
		Assert.assertEquals(400F, tarzan.getTotalSpent().get(eur));
		Assert.assertEquals(88F, chunli.getTotalSpent().get(eur));
		Assert.assertEquals(252.8F, marty.getTotalSpent().get(eur));
		Assert.assertEquals(30F, zelda.getTotalSpent().get(eur));
		Assert.assertEquals(239F, uga.getTotalSpent().get(eur));
		
		Assert.assertEquals(1, obiwan.getTotalPayed().intValue());
		Assert.assertEquals(10, obiwan.getTotalShared().intValue());
		Assert.assertEquals(0, fonzie.getTotalPayed().intValue());
		Assert.assertEquals(8, fonzie.getTotalShared().intValue());
		Assert.assertEquals(3, marty.getTotalPayed().intValue());
		Assert.assertEquals(5, marty.getTotalShared().intValue());
	}

	@Test
	public void testCalculateOnSingleExpense() {
		ExpenseCalculationResult calculation = event.getExpenses().get(0).getCalculationResult(event.getParticipants(), null);
		Assert.assertEquals(216.125F, calculation.getExpenseAmount(marty).getAmount());
		Assert.assertEquals(-30.875F, calculation.getExpenseAmount(obiwan).getAmount());
		Assert.assertEquals(-30.875F, calculation.getExpenseAmount(uga).getAmount());
		Assert.assertEquals(-30.875F, calculation.getExpenseAmount(jane).getAmount());

		calculation = event.getExpenses().get(1).getCalculationResult(event.getParticipants(), null);
		Assert.assertEquals(350F, calculation.getExpenseAmount(tarzan).getAmount());
		Assert.assertEquals(-50F, calculation.getExpenseAmount(obiwan).getAmount());
		Assert.assertEquals(-50F, calculation.getExpenseAmount(uga).getAmount());
		Assert.assertEquals(-50F, calculation.getExpenseAmount(jane).getAmount());

		calculation = event.getExpenses().get(2).getCalculationResult(event.getParticipants(), null);
		Assert.assertEquals(77F, calculation.getExpenseAmount(chunli).getAmount());
		Assert.assertEquals(-11F, calculation.getExpenseAmount(obiwan).getAmount());
		Assert.assertEquals(-11F, calculation.getExpenseAmount(uga).getAmount());
		Assert.assertEquals(-11F, calculation.getExpenseAmount(jane).getAmount());

		calculation = event.getExpenses().get(3).getCalculationResult(event.getParticipants(), null);
		Assert.assertEquals(3.8F, calculation.getExpenseAmount(marty).getAmount());
		Assert.assertEquals(-1.9F, calculation.getExpenseAmount(obiwan).getAmount());
		Assert.assertEquals(-1.9F, calculation.getExpenseAmount(jane).getAmount());
	}

	@Test
	public void testCalculateOnEvent() {
		EventCalculationResult calculationResult = event.getCalculationResult(null);
		
		Map<Participant, Delta> calculation = calculationResult.getCalculation();
		Assert.assertEquals(-47.65F, calculation.get(obiwan).getDelta(eur));
		Assert.assertEquals(280F, calculation.get(tarzan).getDelta(eur));
		Assert.assertEquals(-32F, calculation.get(chunli).getDelta(eur));
		Assert.assertEquals(-159.75F, calculation.get(fonzie).getDelta(eur));
		Assert.assertEquals(131.80F, calculation.get(marty).getDelta(eur));
		Assert.assertEquals(-121.90F, calculation.get(jane).getDelta(eur));
		Assert.assertEquals(-129.75F, calculation.get(zelda).getDelta(eur));
		Assert.assertEquals(79.25F, calculation.get(uga).getDelta(eur));

		List<Suggestion> suggestions = calculationResult.getSuggestions().get(eur);
		Float sumMarty = 0F;
		Float sumTarzan = 0F;
		Float sumUga = 0F;
		for (Suggestion suggestion : suggestions) {
			if(suggestion.getTo().equals(marty)) {
				sumMarty += suggestion.getAmount();
			}
			
			if(suggestion.getTo().equals(tarzan)) {
				sumTarzan += suggestion.getAmount();
			}
			
			if(suggestion.getTo().equals(uga)) {
				sumUga += suggestion.getAmount();
			}
		}
		
		Assert.assertEquals(131.8F, round(sumMarty));
		Assert.assertEquals(280F, round(sumTarzan));
		Assert.assertEquals(79.25F, round(sumUga));
	}

}
