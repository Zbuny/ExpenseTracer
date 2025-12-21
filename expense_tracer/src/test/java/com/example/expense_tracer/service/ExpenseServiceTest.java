package com.example.expense_tracer.service;

import com.example.expense_tracer.dto.CategoryDto;
import com.example.expense_tracer.dto.ExpenseDto;
import com.example.expense_tracer.dto.UserDto;
import com.example.expense_tracer.service.CategoryService;
import com.example.expense_tracer.service.ExpenseService;
import com.example.expense_tracer.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

@SpringBootTest
public class ExpenseServiceTest {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    private ExpenseDto createOne() {
        UserDto userDto = new UserDto();
        userDto.setEmail("expUser" + new Random().nextInt(1000000) + "@mail.com");
        userDto.setPassword("123");
        userDto.setRole("USER");
        UserDto savedUser = userService.add(userDto);

        CategoryDto catDto = new CategoryDto();
        catDto.setName("ExpCategory" + new Random().nextInt(1000));
        catDto.setUserId(savedUser.getId());
        CategoryDto savedCat = categoryService.add(catDto);

        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAmount(100.0 + new Random().nextInt(500));
        expenseDto.setDescription("Test Expense");
        expenseDto.setDate(LocalDate.now());
        expenseDto.setUserId(savedUser.getId());
        expenseDto.setCategoryId(savedCat.getId());

        return expenseService.add(expenseDto);
    }

    @Test
    void getAllTest() {
        createOne();
        List<ExpenseDto> list = expenseService.getAll();
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void getByIdTest() {
        createOne();
        List<ExpenseDto> all = expenseService.getAll();
        Long id = all.get(new Random().nextInt(all.size())).getId();

        ExpenseDto dto = expenseService.getById(id);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(id, dto.getId());
        Assertions.assertNotNull(dto.getAmount());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            expenseService.getById(-1L);
        });
    }

    @Test
    void addTest() {
        ExpenseDto created = createOne();
        Assertions.assertNotNull(created.getId());
        Assertions.assertNotNull(created.getAmount());

        ExpenseDto fromDb = expenseService.getById(created.getId());
        Assertions.assertEquals(created.getDescription(), fromDb.getDescription());
    }

    @Test
    void updateTest() {
        ExpenseDto created = createOne();
        Long id = created.getId();

        ExpenseDto updateDto = new ExpenseDto();
        updateDto.setAmount(555.55);
        updateDto.setDescription("Updated Desc");
        updateDto.setDate(LocalDate.now().minusDays(1));
        updateDto.setUserId(created.getUserId());
        updateDto.setCategoryId(created.getCategoryId());

        ExpenseDto result = expenseService.update(id, updateDto);
        Assertions.assertEquals(555.55, result.getAmount());
        Assertions.assertEquals("Updated Desc", result.getDescription());
    }

    @Test
    void deleteTest() {
        ExpenseDto created = createOne();
        Long id = created.getId();
        expenseService.delete(id);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            expenseService.getById(id);
        });
    }

    @Test
    void getMonthlyExpensesTest() {
        ExpenseDto e1 = createOne();
        e1.setAmount(500.0);
        e1.setDate(LocalDate.of(2025, 12, 1));
        expenseService.update(e1.getId(), e1);

        ExpenseDto e2 = createOne();
        e2.setAmount(300.0);
        e2.setDate(LocalDate.of(2025, 12, 15));
        expenseService.update(e2.getId(), e2);

        ExpenseDto e3 = createOne();
        e3.setAmount(1000.0);
        e3.setDate(LocalDate.of(2026, 1, 1));
        expenseService.update(e3.getId(), e3);

        Double sum = expenseService.getMonthlyExpenses(2025, 12);

        Assertions.assertTrue(sum >= 800.0);
    }

    @Test
    void getExpensesByCategoryTest() {
        createOne();
        Map<String, Double> map = expenseService.getExpensesByCategory();

        Assertions.assertNotNull(map);
        Assertions.assertTrue(map.size() <= 3);
    }
}