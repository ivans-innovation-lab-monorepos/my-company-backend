package com.idugalic.commandside.team.aggregate;

import java.time.LocalDate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

class Member {

	private String userId;
	private LocalDate startDate;
	private LocalDate endDate;
	private Long weeklyHours;

	public Member(String userId, LocalDate startDate, LocalDate endDate, Long weeklyHours) {
		this.userId = userId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.weeklyHours = weeklyHours;
	}

	public String getUserId() {
		return userId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Long getWeeklyHours() {
		return weeklyHours;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
