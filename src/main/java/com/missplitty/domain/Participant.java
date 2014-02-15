package com.missplitty.domain;

import java.util.Map;

import com.google.common.collect.Maps;

public class Participant {

	private Integer id;
	private String name;

	private Map<Currency, Float> totalSpent;
	private Integer totalPayed;
	private Integer totalShared;

	public Participant(Integer id, String name) {
		this.id = id;
		this.name = name;
		totalPayed = 0;
		totalShared = 0;
		totalSpent = Maps.newHashMap();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalPayed() {
		return totalPayed;
	}

	public void setTotalPayed(Integer totalPayed) {
		this.totalPayed = totalPayed;
	}

	public Integer getTotalShared() {
		return totalShared;
	}

	public void setTotalShared(Integer totalShared) {
		this.totalShared = totalShared;
	}

	public Map<Currency, Float> getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(Map<Currency, Float> totalSpent) {
		this.totalSpent = totalSpent;
	}
	
	public void incrementTotalSpent(Amount amount) {
		totalSpent.put(amount.getCurrency(), (totalSpent.get(amount.getCurrency()) == null ? 0 : totalSpent.get(amount.getCurrency())) + amount.getAmount());
	}

	public void incrementTotalPayed() {
		this.totalPayed++;
	}

	public void incrementTotalShared() {
		this.totalShared++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participant other = (Participant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Participant [id=" + id + ", name=" + name + "]";
	}

}
