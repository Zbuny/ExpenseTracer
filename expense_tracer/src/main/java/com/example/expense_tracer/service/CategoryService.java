package com.example.expense_tracer.service;

import com.example.expense_tracer.dto.CategoryDto;
import com.example.expense_tracer.entity.Category;
import com.example.expense_tracer.mapper.CategoryMapper;
import com.example.expense_tracer.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAll() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return categoryMapper.toDto(category);
    }

    public CategoryDto add(CategoryDto dto) {
        Category saved = categoryRepository.save(categoryMapper.toEntity(dto));
        return categoryMapper.toDto(saved);
    }

    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(dto.getName());
        Category updated = categoryRepository.save(category);
        return categoryMapper.toDto(updated);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
