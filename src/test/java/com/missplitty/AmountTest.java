package com.missplitty;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.missplitty.domain.Amount;
import com.missplitty.domain.Currency;
import com.missplitty.domain.Expense;
import com.missplitty.domain.Participant;

public class AmountTest {
	private Expense expense;
	private Currency EUR;
	private Currency USD;
	
	@Before
	public void setUp() {
		EUR = new Currency(1, "EUR");
		EUR.setRatio(1F);
		USD = new Currency(2, "USD");
		USD.setRatio(1.5F);
		
		expense = new Expense(EUR);
		expense.setAmount(new Amount(100F, EUR));
		List<Participant> sharers = Lists.newArrayList();
		sharers.add(new Participant(1, "test1"));
		sharers.add(new Participant(2, "test2"));
		sharers.add(new Participant(3, "test3"));
		sharers.add(new Participant(4, "test4"));
		expense.setSharers(sharers);
	}
	
	@Test
	public void testCalculateAmountPerSharer() {
		Amount amount = Amount.calculateAmountPerSharer(expense, null);
		Assert.assertEquals(-25F, amount.getAmount());
	}
	
	@Test
	public void testCalculateConvertedAmountPerSharer() {
		Amount amount = Amount.calculateAmountPerSharer(expense, USD);
		Assert.assertEquals(-37.5F, amount.getAmount());
	}

}
