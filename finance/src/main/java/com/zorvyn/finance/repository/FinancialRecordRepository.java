package com.zorvyn.finance.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zorvyn.finance.models.FinancialRecord;
import com.zorvyn.finance.models.Type;
import com.zorvyn.finance.models.User;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long>{
	
	List<FinancialRecord> findByType(Type type);
	
	List<FinancialRecord> findByCategory(String category);
	
	List<FinancialRecord> findByDate(LocalDate date);
	
	List<FinancialRecord> findByUser(User user);
	
	void deleteByUser(User user);
	
	@Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.user = :user AND r.type = 'INCOME'")
	Double getTotalIncomeByUser(User user);
	
	@Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.user = :user AND r.type = 'EXPENSES'")
	Double getTotalExpenseByUser(User user);

}
