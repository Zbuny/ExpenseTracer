package com.example.expense_tracer.service;

import com.example.expense_tracer.dto.CategoryDto;
import com.example.expense_tracer.dto.UserDto;
import com.example.expense_tracer.service.CategoryService;
import com.example.expense_tracer.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    private CategoryDto createOne() {
        UserDto userDto = new UserDto();
        userDto.setEmail("catUser" + new Random().nextInt(100000) + "@test.com");
        userDto.setPassword("pass");
        userDto.setRole("USER");
        UserDto savedUser = userService.add(userDto);

        CategoryDto dto = new CategoryDto();
        dto.setName("Groceries " + new Random().nextInt(1000));
        dto.setUserId(savedUser.getId());

        return categoryService.add(dto);
    }

    @Test
    void getAllTest() {
        createOne();
        List<CategoryDto> dtos = categoryService.getAll();
        Assertions.assertNotNull(dtos);
        Assertions.assertNotEquals(0, dtos.size());
    }

    @Test
    void getByIdTest() {
        createOne();
        List<CategoryDto> all = categoryService.getAll();
        Long someId = all.get(new Random().nextInt(all.size())).getId();

        CategoryDto dto = categoryService.getById(someId);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(someId, dto.getId());
        Assertions.assertNotNull(dto.getName());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            categoryService.getById(-1L);
        });
    }

    @Test
    void addTest() {
        UserDto userDto = new UserDto();
        userDto.setEmail("addTestUser" + new Random().nextInt(100000) + "@test.com");
        userDto.setPassword("pass");
        UserDto savedUser = userService.add(userDto);

        CategoryDto dto = new CategoryDto();
        dto.setName("New Category");
        dto.setUserId(savedUser.getId());

        CategoryDto created = categoryService.add(dto);

        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals(dto.getName(), created.getName());
    }

    @Test
    void updateTest() {
        CategoryDto created = createOne();
        Long id = created.getId();

        CategoryDto updateDto = new CategoryDto();
        updateDto.setName("Updated Name");
        updateDto.setUserId(created.getUserId());

        CategoryDto result = categoryService.update(id, updateDto);
        Assertions.assertEquals("Updated Name", result.getName());
    }

    @Test
    void deleteTest() {
        CategoryDto created = createOne();
        Long id = created.getId();

        categoryService.delete(id);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            categoryService.getById(id);
        });
    }
}