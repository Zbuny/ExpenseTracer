package com.example.expense_tracer.mapper;

import com.example.expense_tracer.dto.ExpenseDto;
import com.example.expense_tracer.entity.Category;
import com.example.expense_tracer.entity.Expense;
import com.example.expense_tracer.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ExpenseMapperTest {

    @Autowired
    private ExpenseMapper expenseMapper;

    @Test
    void toDtoTest() {
        User user = new User();
        user.setId(55L);

        Category category = new Category();
        category.setId(77L);

        Expense ent = new Expense();
        ent.setId(1L);
        ent.setAmount(100.0);
        ent.setDescription("Lunch");
        ent.setDate(LocalDateTime.now());
        ent.setUser(user);
        ent.setCategory(category);

        ExpenseDto dto = expenseMapper.toDto(ent);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(ent.getId(), dto.getId());
        Assertions.assertEquals(ent.getAmount(), dto.getAmount());

        Assertions.assertEquals(55L, dto.getUserId());
        Assertions.assertEquals(77L, dto.getCategoryId());
    }

    @Test
    void toEntityTest() {
        ExpenseDto dto = new ExpenseDto();
        dto.setId(2L);
        dto.setAmount(500.0);
        dto.setDescription("Taxi");
        dto.setUserId(10L);
        dto.setCategoryId(20L);

        Expense ent = expenseMapper.toEntity(dto);

        Assertions.assertNotNull(ent);

        Assertions.assertNull(ent.getUser());
        Assertions.assertNull(ent.getCategory());
    }

    @Test
    void toDtoListTest() {
        List<Expense> ents = new ArrayList<>();

        User u = new User(); u.setId(1L);
        Category c = new Category(); c.setId(2L);

        Expense e1 = new Expense();
        e1.setId(100L);
        e1.setUser(u);
        e1.setCategory(c);

        ents.add(e1);

        List<ExpenseDto> dtos = expenseMapper.toDtoList(ents);

        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(1, dtos.size());
        Assertions.assertEquals(100L, dtos.get(0).getId());
        Assertions.assertEquals(1L, dtos.get(0).getUserId());
    }
}