package com.missplitty.logic;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.missplitty.domain.Amount;
import com.missplitty.domain.Currency;

public class Delta {
	private Map<Currency, Float> delta;

	public Delta(Set<Currency> currencies) {
		delta = Maps.newHashMap();
		for (Currency currency : currencies) {
			delta.put(currency, 0F);
		}
	}

	public Delta calculate(Amount amount) {
		delta.put(amount.getCurrency(), (delta.get(amount.getCurrency()) == null ? 0 : delta.get(amount.getCurrency())) + amount.getAmount());
		return this;
	}

	public Float getDelta(Currency currency) {
		return delta.get(currency);
	}

	public Map<Currency, Float> getDelta() {
		return delta;
	}

	@Override
	public String toString() {
		String out = "";
		for (Currency currency : delta.keySet()) {
			out += currency.toString() + " " + delta.get(currency) + "\n";
		}
		return out;
	}
	
}