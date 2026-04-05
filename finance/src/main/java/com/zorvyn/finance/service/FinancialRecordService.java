package com.zorvyn.finance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zorvyn.finance.models.FinancialRecord;
import com.zorvyn.finance.models.Role;
import com.zorvyn.finance.models.Type;
import com.zorvyn.finance.models.User;
import com.zorvyn.finance.repository.FinancialRecordRepository;
import com.zorvyn.finance.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FinancialRecordService {
	
	private final FinancialRecordRepository recordRepo;
	private final UserRepository userRepo;
	
	public FinancialRecordService(FinancialRecordRepository recordRepo, UserRepository userRepo) {
		this.recordRepo = recordRepo;
		this.userRepo = userRepo;
	}
	
	public FinancialRecord createRecord(FinancialRecord record,HttpServletRequest request) {

		String role = (String) request.getAttribute("role");

		checkAccess(role, Role.ADMIN.name());
		return recordRepo.save(record);
    }

    public List<FinancialRecord> getAllReocrds() {
        return recordRepo.findAll();
    }

    public FinancialRecord updateRecord(Long id, FinancialRecord updated, HttpServletRequest request) {
    	
    	String role = (String) request.getAttribute("role");

		checkAccess(role, Role.ADMIN.name(), Role.ANALYST.name());
		
        FinancialRecord record = recordRepo.findById(id).orElseThrow();

        record.setAmount(updated.getAmount());
        record.setType(updated.getType());
        record.setCategory(updated.getCategory());
        record.setDate(updated.getDate());
        record.setDescription(updated.getDescription());

        return recordRepo.save(record);
    }

    public void deleteRecord(Long id, HttpServletRequest request) {
    	
		String role = (String) request.getAttribute("role");

		checkAccess(role, Role.ADMIN.name());
		recordRepo.deleteById(id);
    }
	
	public List<FinancialRecord> filterReocrds(Type type, String category, LocalDate date, HttpServletRequest request) {

		String role = (String) request.getAttribute("role");

		checkAccess(role, Role.ADMIN.name(), Role.ANALYST.name());
		
	    List<FinancialRecord> records = recordRepo.findAll();

	    if (type != null) {
	        records = records.stream()
	                .filter(r -> r.getType() == type)
	                .toList();
	    }

	    if (category != null) {
	        records = records.stream()
	                .filter(r -> r.getCategory().equalsIgnoreCase(category))
	                .toList();
	    }

	    if (date != null) {
	        records = records.stream()
	                .filter(r -> r.getDate().equals(date))
	                .toList();
	    }

	    return records;
	}
	
	//Dashboard summary for all users
	
	public double getTotalIncome() {
	    return recordRepo.findAll().stream()
	            .filter(r -> r.getType().name().equals("INCOME"))
	            .mapToDouble(FinancialRecord::getAmount)
	            .sum();
	}
	
	public double getTotalExpense() {
	    return recordRepo.findAll().stream()
	            .filter(r -> r.getType().name().equals("EXPENSES"))
	            .mapToDouble(FinancialRecord::getAmount)
	            .sum();
	}
	
	public Map<String, Double> getCategorySummary() {
	    return recordRepo.findAll().stream()
	            .collect(Collectors.groupingBy(
	                    FinancialRecord::getCategory,
	                    Collectors.summingDouble(FinancialRecord::getAmount)
	            ));
	}
	
	public Map<Integer, Double> getMonthlySummary() {
	    return recordRepo.findAll().stream()
	            .collect(Collectors.groupingBy(
	                    r -> r.getDate().getMonthValue(),
	                    Collectors.summingDouble(FinancialRecord::getAmount)
	            ));
	}
	
	// Dashboard summary for each users
	
	public double getTotalIncomeByUser(HttpServletRequest request) {

	    String email = (String) request.getAttribute("email");
	    User user = userRepo.findByEmail(email);

	    return recordRepo.getTotalIncomeByUser(user);
	}
	
	public double getTotalExpenseByUser(HttpServletRequest request) {

	    String email = (String) request.getAttribute("email");
	    User user = userRepo.findByEmail(email);

	    return recordRepo.getTotalExpenseByUser(user);
	}
	

	// ROLE BASED CHECKING  FUNCTION
	
	 private void checkAccess(String role, String... allowedRoles) {
	    	for(String r : allowedRoles) {
	    		if(r.equals(role)) return;
	    	}
	    	throw new RuntimeException("Access Denied");
	    }


}
