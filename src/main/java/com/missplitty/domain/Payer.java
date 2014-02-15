package com.missplitty.domain;

public class Payer {

	private Participant participant;
	private Amount amount;

	public Payer(Participant participant, Amount amount) {
		super();
		this.participant = participant;
		this.amount = amount;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
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
