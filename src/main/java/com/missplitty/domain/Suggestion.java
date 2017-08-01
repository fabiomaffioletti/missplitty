package com.missplitty.domain;

import com.missplitty.utils.Utils;
import lombok.Data;

@Data
public class Suggestion {

	private Participant from;
	private Participant to;
	private Float amount;
	
	public Suggestion() {}

	public Suggestion(Participant from, Participant to, Float amount) {
		super();
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public String formatAmount() {
		return Utils.numberFormat.format(getAmount());
	}

	@Override
	public String toString() {
		return "from=" + from.getName() + ", to=" + to.getName() + ", amount=" + formatAmount();
	}

}
