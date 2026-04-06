package com.zorvyn.finance.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FinancialRecordDTO {
	
	private Long id;
    private Double amount;
    private String type;
    private String category;
    private LocalDate date;
    private String description;

    private Long userId;
    
    
  //DUE TO LOMBOK IMPLEMENTATION ISSUES ON MY PC ,SO I MANUALY ADDED THE GETTERS AND SETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    

}
