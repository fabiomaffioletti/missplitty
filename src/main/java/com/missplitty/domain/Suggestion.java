package com.missplitty.domain;

import com.missplitty.utils.Utils;

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

	public Participant getFrom() {
		return from;
	}

	public void setFrom(Participant from) {
		this.from = from;
	}

	public Participant getTo() {
		return to;
	}

	public void setTo(Participant to) {
		this.to = to;
	}

	public Float getAmount() {
		return Math.round(amount * 100) / 100F;
	}
	
	public String formatAmount() {
		return Utils.numberFormat.format(getAmount());
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "from=" + from.getName() + ", to=" + to.getName() + ", amount=" + formatAmount();
	}

}
