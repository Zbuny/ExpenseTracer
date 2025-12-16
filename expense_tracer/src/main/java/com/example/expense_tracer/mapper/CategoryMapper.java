package com.example.expense_tracer.mapper;

import com.example.expense_tracer.dto.CategoryDto;
import com.example.expense_tracer.entity.Category;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto dto);
    List<CategoryDto> toDtoList(List<Category> categories);
}
