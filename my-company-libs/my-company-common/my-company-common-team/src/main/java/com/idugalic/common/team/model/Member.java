package com.idugalic.common.team.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Member implements Serializable{

	private static final long serialVersionUID = 6792479223967255410L;
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
}
