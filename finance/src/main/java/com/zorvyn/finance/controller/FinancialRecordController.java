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

import com.zorvyn.finance.dto.DashboardDTO;
import com.zorvyn.finance.dto.FinancialRecordDTO;
import com.zorvyn.finance.dto.FinancialRecordRequestDTO;
import com.zorvyn.finance.models.Type;
import com.zorvyn.finance.service.FinancialRecordService;

import io.swagger.v3.oas.annotations.Operation;
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
	
	@Operation(summary = "CREATE RECORD")
	@PostMapping("/create")
	public FinancialRecordDTO createRecord(@RequestBody FinancialRecordRequestDTO recordDTO, HttpServletRequest request) {
		return recordService.createRecord(recordDTO, request);
		
	}
	
	@Operation(summary = "GET ALL RECORDS")
	@GetMapping("/all")
	public List<FinancialRecordDTO> getAll() {
	    return recordService.getAllRecords();
	}
	
	
	@Operation(summary = "UPDATE RECORD")
	@PutMapping("/update/{id}")
    public FinancialRecordDTO updateRecord(@PathVariable Long id,
                                  @RequestBody FinancialRecordRequestDTO recordDTO, HttpServletRequest request) {
        return recordService.updateRecord(id, recordDTO, request);
    }
	
	
	@Operation(summary = "DELETE RECORD")
	@DeleteMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Long id, HttpServletRequest request) {
		recordService.deleteRecord(id,request);
        return "Record deleted";
        
    }
	
	
	@Operation(summary = "FILTER RECORDS")
	@GetMapping("/filter")
	public List<FinancialRecordDTO> filterRecords(@RequestParam(required = false) Type type,
			@RequestParam(required = false) String category, @RequestParam(required = false) String strDate, HttpServletRequest  request) {
		LocalDate date = (strDate != null) ? LocalDate.parse(strDate) : null;
		return recordService.filterRecords(type, category, date, request);
	}
	
		
	
	
	//DASHBOARD SUMMARY APIs
	
	@Operation(summary = "TOTAL INCOME")
	@GetMapping("/income/total")
	public double totalIncome(HttpServletRequest request) {
	    return recordService.getTotalIncome(request);
	}
	
	
	@Operation(summary = "TOTAL EXPENSE")
	@GetMapping("/expense/total")
	public double totalExpense(HttpServletRequest request) {
	    return recordService.getTotalExpense(request);
	}
	
	
	@Operation(summary = "TOTAL SUMMARY BY CATEGORY")
	@GetMapping("/summary/category")
	public Map<String, Double> categorySummary(HttpServletRequest request) {
	    return recordService.getCategorySummary(request);
	}
	
	@Operation(summary = "TOTAL SUMMARY BY MONTH")
	@GetMapping("/summary/month")
	public Map<Integer, Double> monthlySummary(HttpServletRequest request) {
	    return recordService.getMonthlySummary(request);
	}
	
	
	
	
	
	
	// APIs FOR EACH USER SUMMARY
	
	@Operation(summary = "VIEWERS DASHBOARD")
	@GetMapping("/dashboard")
	public DashboardDTO totalIncomeByUser(HttpServletRequest request) {
	    return recordService.getDashboard(request);
	}
}
