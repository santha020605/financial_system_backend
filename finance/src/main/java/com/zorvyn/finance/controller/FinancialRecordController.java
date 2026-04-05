package com.zorvyn.finance.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zorvyn.finance.models.FinancialRecord;
import com.zorvyn.finance.models.Type;
import com.zorvyn.finance.service.FinancialRecordService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Record APIs", description = "Related to records")
@RestController
@RequestMapping("/records")
public class FinancialRecordController {
	
	private final FinancialRecordService recordService;
	
	public FinancialRecordController(FinancialRecordService recordService) {
		this.recordService = recordService;
		
	}
	
	
	//CRUD OPERATIONS APIs
	
	
	@PostMapping("/create")
	public FinancialRecord createRecord(@RequestBody FinancialRecord record, HttpServletRequest request) {
		return recordService.createRecord(record, request);
		
	}
	
	@GetMapping("/all")
    public List<FinancialRecord> getAllRecords() {
		 return recordService.getAllReocrds();
		
    }
	
	@PutMapping("/update/{id}")
    public FinancialRecord updateRecord(@PathVariable Long id,
                                  @RequestBody FinancialRecord record, HttpServletRequest request) {
        return recordService.updateRecord(id, record, request);
    }
	
	@DeleteMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Long id, HttpServletRequest request) {
		recordService.deleteRecord(id,request);
        return "Record deleted";
        
    }
	
	@GetMapping("/filter")
	public List<FinancialRecord> filterRecords(@RequestParam(required = false) Type type,
			@RequestParam(required = false) String category, @RequestParam(required = false) String strDate, HttpServletRequest  request) {
		LocalDate date = (strDate != null) ? LocalDate.parse(strDate) : null;
		return recordService.filterReocrds(type, category, date, request);
	}
	
	
	//DASHBOARD SUMMARY APIs
	
	
	@GetMapping("/income/total")
	public double totalIncome() {
	    return recordService.getTotalIncome();
	}
	
	@GetMapping("/expense/total")
	public double totalExpense() {
	    return recordService.getTotalExpense();
	}
	
	@GetMapping("/summary/category")
	public Map<String, Double> categorySummary() {
	    return recordService.getCategorySummary();
	}
	
	@GetMapping("/summary/month")
	public Map<Integer, Double> monthlySummary() {
	    return recordService.getMonthlySummary();
	}
	
	
	// APIs FOR EACH USER SUMMARY
	
	@GetMapping("/income/user")
	public double totalIncomeByUser(HttpServletRequest request) {
	    return recordService.getTotalIncomeByUser(request);
	}
	
	@GetMapping("/expense/user")
	public double totalExpenseByUser(HttpServletRequest request) {
	    return recordService.getTotalExpenseByUser(request);
	}
}
