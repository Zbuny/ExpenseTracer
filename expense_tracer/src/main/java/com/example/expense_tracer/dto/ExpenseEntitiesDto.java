package com.example.expense_tracer.dto;

import lombok.Data;
import java.util.List;

@Data
public class ExpenseEntitiesDto {
    private List<UserDto> users;
    private List<ExpenseDto> expenses;
    private List<CategoryDto> categories;
}