package com.example.expense_tracer.mapper;

import com.example.expense_tracer.dto.UserDto;
import com.example.expense_tracer.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void toDtoTest() {
        User ent = new User();
        ent.setId(1L);
        ent.setEmail("test@mail.com");
        ent.setUsername("TestUser");
        ent.setRole("USER");
        ent.setPassword("123");

        UserDto dto = userMapper.toDto(ent);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(ent.getId(), dto.getId());
        Assertions.assertEquals(ent.getEmail(), dto.getEmail());
        Assertions.assertEquals(ent.getUsername(), dto.getUsername());
        Assertions.assertEquals(ent.getRole(), dto.getRole());
    }

    @Test
    void toEntityTest() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setEmail("dto@mail.com");
        dto.setUsername("DtoUser");
        dto.setRole("ADMIN");

        User ent = userMapper.toEntity(dto);

        Assertions.assertNotNull(ent);
        Assertions.assertEquals(dto.getId(), ent.getId());
        Assertions.assertEquals(dto.getEmail(), ent.getEmail());
        Assertions.assertEquals(dto.getUsername(), ent.getUsername());
    }

    @Test
    void toDtoListTest() {
        List<User> ents = new ArrayList<>();
        User u1 = new User(); u1.setId(1L); u1.setUsername("U1");
        User u2 = new User(); u2.setId(2L); u2.setUsername("U2");
        ents.add(u1);
        ents.add(u2);

        List<UserDto> dtos = userMapper.toDtoList(ents);

        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(ents.size(), dtos.size());

        Assertions.assertEquals(ents.get(0).getId(), dtos.get(0).getId());
        Assertions.assertEquals(ents.get(1).getUsername(), dtos.get(1).getUsername());
    }
}