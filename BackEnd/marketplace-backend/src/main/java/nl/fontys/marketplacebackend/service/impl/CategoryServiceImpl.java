package nl.fontys.marketplacebackend.service.impl;

import nl.fontys.marketplacebackend.dto.CategoryDto;
import nl.fontys.marketplacebackend.model.Category;
import nl.fontys.marketplacebackend.persistence.CategoryRepository;
import nl.fontys.marketplacebackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean addCategory(Category c) {
        this.categoryRepository.save(c);

        return true;
    }

    @Override
    public Category getCategoryById(String id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category getCategoryByName(String name) {
        return this.categoryRepository.findCategoryByName(name);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDto> categoryDTOS = new ArrayList<>();

        for (Category category : categories) {
            categoryDTOS.add(CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .build());
        }
        return categoryDTOS;
    }

    @Override
    public boolean updateCategory(Category c) {
        //not working yet
        this.categoryRepository.save(c);
        return true;
    }

    @Override
    public boolean deleteCategory(String id) {
        this.categoryRepository.deleteById(id);
        return true;
    }
}
