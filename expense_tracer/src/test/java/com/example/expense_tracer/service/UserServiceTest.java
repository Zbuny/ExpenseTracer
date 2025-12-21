package com.example.expense_tracer.service;

import com.example.expense_tracer.dto.UserDto;
import com.example.expense_tracer.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private UserDto createOne() {
        UserDto dto = new UserDto();
        dto.setEmail("user" + new Random().nextInt(1000000) + "@example.com");
        dto.setPassword("password123");
        dto.setRole("USER");
        dto.setUsername("User" + new Random().nextInt(1000));
        return userService.add(dto);
    }

    @Test
    void getAllTest() {
        createOne();
        List<UserDto> dtos = userService.getAll();
        Assertions.assertNotNull(dtos);
        Assertions.assertNotEquals(0, dtos.size());
    }

    @Test
    void getByIdTest() {
        createOne();
        Random random = new Random();
        List<UserDto> all = userService.getAll();
        int ind = random.nextInt(all.size());
        Long someId = all.get(ind).getId();

        UserDto dto = userService.getById(someId);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(someId, dto.getId());
        Assertions.assertNotNull(dto.getEmail());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userService.getById(-1L);
        });
    }

    @Test
    void addTest() {
        UserDto dto = new UserDto();
        dto.setEmail("newuser" + new Random().nextInt(100000) + "@mail.com");
        dto.setPassword("pass");
        dto.setUsername("NewUser");

        UserDto created = userService.add(dto);
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals("USER", created.getRole());
    }

    @Test
    void updateTest() {
        UserDto created = createOne();
        Long id = created.getId();

        UserDto updateDto = new UserDto();
        updateDto.setEmail("updated" + new Random().nextInt(10000) + "@mail.com");
        updateDto.setRole("ADMIN");
        updateDto.setUsername("UpdatedUser");

        UserDto result = userService.update(id, updateDto);
        Assertions.assertEquals(updateDto.getEmail(), result.getEmail());
        Assertions.assertEquals("ADMIN", result.getRole());
    }

    @Test
    void deleteTest() {
        UserDto created = createOne();
        Long id = created.getId();

        userService.delete(id);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userService.getById(id);
        });
    }
}