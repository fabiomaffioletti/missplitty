package com.missplitty.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.missplitty.domain.Currency;
import com.missplitty.domain.Event;
import com.missplitty.domain.Expense;

public class Utils {
	
	public static NumberFormat numberFormat = new DecimalFormat("#0.00");
	
	public static Set<Currency> getCurrencies(Event event) {
		Set<Currency> currencies = Sets.newHashSet();
		for (Expense expense : event.getExpenses()) {
			currencies.add(expense.getAmount().getCurrency());
		}
		return currencies;
	}
	
	public static Float convertFloatToCurrency(Float amount, Currency expenseCurrency, Currency destinationCurrency) {
		return amount / expenseCurrency.getRatio() * destinationCurrency.getRatio();
	}
	
	public static Float convertMapToCurrency(Currency destinationCurrency, Map<Currency, Float> toConvert) {
		Float convertedAmount = 0F;
		for (Currency currency : toConvert.keySet()) {
			convertedAmount += convertFloatToCurrency(toConvert.get(currency), currency, destinationCurrency);
		}
		return convertedAmount;
	}
	
}
