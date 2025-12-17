package com.example.expense_tracer.service;

import com.example.expense_tracer.dto.ExpenseDto;
import com.example.expense_tracer.dto.ExpenseEntitiesDto;
import com.example.expense_tracer.entity.Expense;
import com.example.expense_tracer.entity.User;
import com.example.expense_tracer.entity.Category;
import com.example.expense_tracer.mapper.ExpenseMapper;
import com.example.expense_tracer.mapper.UserMapper;
import com.example.expense_tracer.mapper.CategoryMapper;
import com.example.expense_tracer.repository.ExpenseRepository;
import com.example.expense_tracer.repository.UserRepository;
import com.example.expense_tracer.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseMapper expenseMapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;

    public List<ExpenseDto> getAll() {
        return expenseMapper.toDtoList(expenseRepository.findAll());
    }

    public ExpenseDto getById(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow();
        return expenseMapper.toDto(expense);
    }

    public ExpenseDto add(ExpenseDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
        Expense expense = expenseMapper.toEntity(dto);
        expense.setUser(user);
        expense.setCategory(category);
        Expense saved = expenseRepository.save(expense);
        return expenseMapper.toDto(saved);
    }

    public ExpenseDto update(Long id, ExpenseDto dto) {
        Expense expense = expenseRepository.findById(id).orElseThrow();
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
        expense.setAmount(dto.getAmount());
        expense.setDescription(dto.getDescription());
        expense.setDate(dto.getDate());
        expense.setUser(user);
        expense.setCategory(category);
        Expense updated = expenseRepository.save(expense);
        return expenseMapper.toDto(updated);
    }

    public void delete(Long id) {
        expenseRepository.deleteById(id);
    }

    public ExpenseEntitiesDto getAllEntities() {
        ExpenseEntitiesDto dto = new ExpenseEntitiesDto();
        dto.setUsers(userMapper.toDtoList(userRepository.findAll()));
        dto.setCategories(categoryMapper.toDtoList(categoryRepository.findAll()));
        dto.setExpenses(expenseMapper.toDtoList(expenseRepository.findAll()));
        return dto;
    }

    public Double getMonthlyExpenses(int year, int month) {
        return expenseRepository.findAll().stream()
                .filter(e -> e.getDate().getYear() == year && e.getDate().getMonthValue() == month)
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Map<String, Double> getExpensesByCategory() {
        List<Expense> allExpenses = expenseRepository.findAll();

        Map<String, Double> sumByCategory = allExpenses.stream()
                .collect(Collectors.groupingBy(e -> e.getCategory().getName(),
                        Collectors.summingDouble(Expense::getAmount)));

        return sumByCategory.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
