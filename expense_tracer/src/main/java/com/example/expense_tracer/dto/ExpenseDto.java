package com.example.expense_tracer.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {
    private Long id;
    private Double amount;
    private String description;
    private LocalDate date;
    private Long userId;
    private Long categoryId;
}