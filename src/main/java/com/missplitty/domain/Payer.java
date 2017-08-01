package com.missplitty.domain;

import lombok.Data;

@Data
public class Payer {

	private Participant participant;
	private Amount amount;

	public Payer(Participant participant, Amount amount) {
		super();
		this.participant = participant;
		this.amount = amount;
	}

	public void incrementPayer(Amount amount) {
		incrementTotalPayed();
		incrementTotalSpent(amount);
	}
	
	private void incrementTotalPayed() {
		this.participant.incrementTotalPayed();
	}
	
	private void incrementTotalSpent(Amount amount) {
		this.participant.incrementTotalSpent(amount);
	}

}
