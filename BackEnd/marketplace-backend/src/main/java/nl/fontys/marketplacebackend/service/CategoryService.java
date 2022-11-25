package nl.fontys.marketplacebackend.service;

import nl.fontys.marketplacebackend.dto.CategoryDto;
import nl.fontys.marketplacebackend.model.Category;

import java.util.List;

public interface CategoryService {
    boolean addCategory(Category c);

    Category getCategoryById(String id);

    Category getCategoryByName(String name);

    List<CategoryDto> getAllCategories();

    boolean updateCategory(Category c);

    boolean deleteCategory(String id);

}
