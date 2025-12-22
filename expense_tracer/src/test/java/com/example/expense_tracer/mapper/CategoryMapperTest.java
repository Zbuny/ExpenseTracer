package com.example.expense_tracer.mapper;

import com.example.expense_tracer.dto.CategoryDto;
import com.example.expense_tracer.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void toDtoTest() {
        Category ent = new Category();
        ent.setId(10L);
        ent.setName("Food");

        CategoryDto dto = categoryMapper.toDto(ent);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(ent.getId(), dto.getId());
        Assertions.assertEquals(ent.getName(), dto.getName());
    }

    @Test
    void toEntityTest() {
        CategoryDto dto = new CategoryDto();
        dto.setId(20L);
        dto.setName("Travel");

        Category ent = categoryMapper.toEntity(dto);

        Assertions.assertNotNull(ent);
        Assertions.assertEquals(dto.getId(), ent.getId());
        Assertions.assertEquals(dto.getName(), ent.getName());
    }

    @Test
    void toDtoListTest() {
        List<Category> ents = new ArrayList<>();
        Category c1 = new Category(); c1.setId(1L); c1.setName("C1");
        Category c2 = new Category(); c2.setId(2L); c2.setName("C2");
        ents.add(c1);
        ents.add(c2);

        List<CategoryDto> dtos = categoryMapper.toDtoList(ents);

        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(ents.size(), dtos.size());
        Assertions.assertEquals("C1", dtos.get(0).getName());
    }
}