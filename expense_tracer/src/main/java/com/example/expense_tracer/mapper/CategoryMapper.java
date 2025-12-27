package com.example.expense_tracer.mapper;

import com.example.expense_tracer.dto.CategoryDto;
import com.example.expense_tracer.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "user.id", target = "userId")
    CategoryDto toDto(Category category);
    @Mapping(source = "userId", target = "user.id")
    Category toEntity(CategoryDto dto);
    List<CategoryDto> toDtoList(List<Category> categories);
}
