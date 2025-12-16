package com.example.expense_tracer.repository;

import com.example.expense_tracer.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
