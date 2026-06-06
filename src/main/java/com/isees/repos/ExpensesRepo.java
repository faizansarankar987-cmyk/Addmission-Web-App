package com.isees.repos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isees.entities.Expenses;

@Repository
public interface ExpensesRepo extends JpaRepository<Expenses, Long> {
	@Query("""
			SELECT COALESCE(SUM(e.amount),0)
			FROM Expenses e
			WHERE e.date BETWEEN :start AND :end
			""")
			Double getMonthlyExpense(LocalDateTime start, LocalDateTime end);
	
	
	@Query("SELECT COALESCE(SUM(e.amount),0) FROM Expenses e")
	Double getTotalExpense();


    List<Expenses> findByDateBetween(LocalDateTime from, LocalDateTime to);
}
