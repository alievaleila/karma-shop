package az.edu.itbrains.karma_shop.service;

import az.edu.itbrains.karma_shop.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories();
}
