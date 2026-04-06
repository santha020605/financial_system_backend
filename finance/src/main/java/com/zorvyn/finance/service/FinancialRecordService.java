package com.zorvyn.finance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zorvyn.finance.dto.DashboardDTO;
import com.zorvyn.finance.dto.FinancialRecordDTO;
import com.zorvyn.finance.dto.FinancialRecordRequestDTO;
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
	
	
	
	
	
	public FinancialRecordDTO createRecord(FinancialRecordRequestDTO recordDTO, HttpServletRequest request) {

	    String role = (String) request.getAttribute("role");
	    checkAccess(role, Role.ADMIN.name());

	    User user = userRepo.findById(recordDTO.getUserId())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    FinancialRecord record = new FinancialRecord();

	    record.setAmount(recordDTO.getAmount());
	    record.setType(recordDTO.getType());
	    record.setCategory(recordDTO.getCategory());
	    record.setDate(recordDTO.getDate());
	    record.setDescription(recordDTO.getDescription());

	    record.setUser(user);

	    return mapToDTO(recordRepo.save(record));
	}
	
	
	
	
	
	

    public FinancialRecordDTO updateRecord(Long id, FinancialRecordRequestDTO recordDTO, HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        checkAccess(role, Role.ADMIN.name());

        FinancialRecord record = recordRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        User user = userRepo.findById(recordDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        record.setAmount(recordDTO.getAmount());
        record.setType(recordDTO.getType());
        record.setCategory(recordDTO.getCategory());
        record.setDate(recordDTO.getDate());
        record.setDescription(recordDTO.getDescription());
        record.setUser(user);

        return mapToDTO(recordRepo.save(record));
    }
    
    
    

    public List<FinancialRecordDTO> getAllRecords() {
        return recordRepo.findAll()
	            .stream()
	            .map(this::mapToDTO)
	            .toList();
    }
    
    
    
    
    
    // CONVERT DATA TRANSFER OBJECT
    
    public FinancialRecordDTO mapToDTO(FinancialRecord record) {

        FinancialRecordDTO dto = new FinancialRecordDTO();

        dto.setId(record.getId());
        dto.setAmount(record.getAmount());
        dto.setType(record.getType().name());
        dto.setCategory(record.getCategory());
        dto.setDate(record.getDate());
        dto.setDescription(record.getDescription());

        dto.setUserId(record.getUser().getId());

        return dto;
    }
    
    
    
    

    public void deleteRecord(Long id, HttpServletRequest request) {
    	
		String role = (String) request.getAttribute("role");

		checkAccess(role, Role.ADMIN.name());
		recordRepo.deleteById(id);
    }
    
    
    
    
    
	
	public List<FinancialRecordDTO> filterRecords(Type type, String category, LocalDate date, HttpServletRequest request) {

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

	    return records.stream()
	            .map(this::mapToDTO)
	            .toList();
	}
	
	
	
	
	
	
	//Dashboard summary for all users
	
	public double getTotalIncome(HttpServletRequest request) {
		
		String role = (String) request.getAttribute("role");

		checkAccess(role, Role.ADMIN.name(), Role.ANALYST.name());
		
	    return recordRepo.findAll().stream()
	            .filter(r -> r.getType().name().equals("INCOME"))
	            .mapToDouble(FinancialRecord::getAmount)
	            .sum();
	}
	
	public double getTotalExpense(HttpServletRequest request) {
		
		String role = (String) request.getAttribute("role");

		checkAccess(role, Role.ADMIN.name(), Role.ANALYST.name());
		
	    return recordRepo.findAll().stream()
	            .filter(r -> r.getType().name().equals("EXPENSES"))
	            .mapToDouble(FinancialRecord::getAmount)
	            .sum();
	}
	
	
	
	
	
	
	public Map<String, Double> getCategorySummary(HttpServletRequest request) {
		
		String role = (String) request.getAttribute("role");

		checkAccess(role, Role.ADMIN.name(), Role.ANALYST.name());
		
	    return recordRepo.findAll().stream()
	            .collect(Collectors.groupingBy(
	                    FinancialRecord::getCategory,
	                    Collectors.summingDouble(FinancialRecord::getAmount)
	            ));
	}
	
	public Map<Integer, Double> getMonthlySummary(HttpServletRequest request) {
		
		String role = (String) request.getAttribute("role");

		checkAccess(role, Role.ADMIN.name(), Role.ANALYST.name());
	    return recordRepo.findAll().stream()
	            .collect(Collectors.groupingBy(
	                    r -> r.getDate().getMonthValue(),
	                    Collectors.summingDouble(FinancialRecord::getAmount)
	            ));
	}
	
	
	
	
	
	
	// Dashboard summary for each users
	
	public DashboardDTO getDashboard(HttpServletRequest request) {
		
		String email = (String) request.getAttribute("email");
		User user = userRepo.findByEmail(email);
		
		double income = Optional.ofNullable(recordRepo.getTotalIncomeByUser(user)).orElse(0.0);
		double expense = Optional.ofNullable(recordRepo.getTotalExpenseByUser(user)).orElse(0.0);
		
		DashboardDTO dashboard = new DashboardDTO();
		
		dashboard.setTotalIncome(income);
		dashboard.setTotalExpense(expense);
		double balance = ((income - expense) < 0) ? 0.0 : (income - expense);
		dashboard.setBalance(balance);
		
		return dashboard;
		
	}


	

	// ROLE BASED CHECKING  FUNCTION
	
	 private void checkAccess(String role, String... allowedRoles) {
	    	for(String r : allowedRoles) {
	    		if(r.equals(role)) return;
	    	}
	    	throw new RuntimeException("Access Denied");
	    }


}
