package net.blog.springbootrestapi.service;


import net.blog.springbootrestapi.entity.Category;
import net.blog.springbootrestapi.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(Long categoryId);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
    void deleteCategory(Long categoryId);
}
