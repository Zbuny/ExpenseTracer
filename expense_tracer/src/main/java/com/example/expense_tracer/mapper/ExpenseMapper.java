package com.example.expense_tracer.mapper;

import com.example.expense_tracer.dto.ExpenseDto;
import com.example.expense_tracer.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "category.id", target = "categoryId")
    ExpenseDto toDto(Expense expense);

    List<ExpenseDto> toDtoList(List<Expense> expenses);
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    Expense toEntity(ExpenseDto dto);

    List<Expense> toEntityList(List<ExpenseDto> dtos);
}