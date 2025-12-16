package com.example.expense_tracer.repository;

import com.example.expense_tracer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
