package com.mainsoft.backend.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Report {
	
	private String title;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Europe/Minsk")
	private Date startWeek;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Europe/Minsk")
	private Date endWeek;
	
	private Double averageSpeed;
	
	private Double averageTime;
	
	private Double totalDistance;
		
	
	public Report() {
		
	}

	public Report(Date startWeek, Date endWeek, Double averageSpeed, Double averageTime,
			Double totalDistance) {
	
		this.startWeek = startWeek;
		this.endWeek = endWeek;
		this.averageSpeed = averageSpeed;
		this.averageTime = averageTime;
		this.totalDistance = totalDistance;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(Date startWeek) {
		this.startWeek = startWeek;
	}

	public Date getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(Date endWeek) {
		this.endWeek = endWeek;
	}

	public Double getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(Double averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public Double getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(Double averageTime) {
		this.averageTime = averageTime;
	}

	public Double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Double totalDistance) {
		this.totalDistance = totalDistance;
	}
	
	
	

}
