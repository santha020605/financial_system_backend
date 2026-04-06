package com.zorvyn.finance.dto;

import java.time.LocalDate;

import com.zorvyn.finance.models.Type;

import lombok.Data;

@Data
public class FinancialRecordRequestDTO {

	    private Double amount;
	    
	    private Type type;
	    
	    private String category;
	    
	    private LocalDate date;
	    
	    private String description;

	    private Long userId;
	    

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		

		public Type getType() {
			return type;
		}
		

		public void setType(Type type) {
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
