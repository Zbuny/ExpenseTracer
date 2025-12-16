package com.example.expense_tracer.repository;

import com.example.expense_tracer.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
