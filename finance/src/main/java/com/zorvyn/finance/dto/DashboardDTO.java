package com.zorvyn.finance.dto;

import lombok.Data;

@Data
public class DashboardDTO {
	
	private double totalIncome;
	
	private double totalExpense;
	
	private double balance;

	public double getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(double totalIncome) {
		this.totalIncome = totalIncome;
	}

	public double getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(double totalExpense) {
		this.totalExpense = totalExpense;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	

}
