package com.example.expense_tracer.controller;

import com.example.expense_tracer.dto.ExpenseDto;
import com.example.expense_tracer.dto.ExpenseEntitiesDto;
import com.example.expense_tracer.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expense/v1")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/all")
    public ResponseEntity<List<ExpenseDto>> getAll() {
        return ResponseEntity.ok(expenseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<ExpenseDto> add(@RequestBody ExpenseDto dto) {
        return ResponseEntity.ok(expenseService.add(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExpenseDto> update(@PathVariable Long id, @RequestBody ExpenseDto dto) {
        return ResponseEntity.ok(expenseService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/entities")
    public ExpenseEntitiesDto getAllEntities() {
        return expenseService.getAllEntities();
    }

    @GetMapping("/analytics/monthly")
    public ResponseEntity<Double> getMonthlyExpenses(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(expenseService.getMonthlyExpenses(year, month));
    }

    @GetMapping("/analytics/by-category")
    public ResponseEntity<Map<String, Double>> getExpensesByCategory() {
        return ResponseEntity.ok(expenseService.getExpensesByCategory());
    }
}
