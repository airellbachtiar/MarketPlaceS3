package nl.fontys.marketplacebackend.service.impl;

import nl.fontys.marketplacebackend.dto.CategoryDto;
import nl.fontys.marketplacebackend.model.Category;
import nl.fontys.marketplacebackend.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category createFakeCategory(){
        return Category.builder()
                .id("22")
                .name("Test category")
                .description("Testing the category service")
                .build();
    }

    @Test
    void addCategory() {
        when(categoryRepositoryMock.save(this.createFakeCategory())).thenReturn(createFakeCategory());

        assertTrue(categoryService.addCategory(createFakeCategory()));
        verify(categoryRepositoryMock).save(this.createFakeCategory());
    }

    @Test
    void getCategoryById() {
        when(categoryRepositoryMock.findById("22")).thenReturn(Optional.of(createFakeCategory()));

        Category expectedCategory = createFakeCategory();
        Category actualCategory = categoryService.getCategoryById("22");

        assertEquals(expectedCategory, actualCategory);
        verify(categoryRepositoryMock).findById("22");
    }

    @Test
    void getCategoryByName() {
        when(categoryRepositoryMock.findCategoryByName("Test category")).thenReturn(this.createFakeCategory());

        Category expectedCategory = createFakeCategory();
        Category actualCategory = categoryService.getCategoryByName("Test category");

        assertEquals(expectedCategory, actualCategory);
        verify(categoryRepositoryMock).findCategoryByName("Test category");
    }

    @Test
    void getAllCategories() {
        List<Category> expectedCategories = List.of(this.createFakeCategory());

        when(categoryRepositoryMock.findAll()).thenReturn(expectedCategories);

        List<CategoryDto> expectedResponse = expectedCategories.stream().map(c -> CategoryDto.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .build()).toList();
        List<CategoryDto> actualResponse = categoryService.getAllCategories();

        assertEquals(expectedResponse, actualResponse);
        verify(categoryRepositoryMock).findAll();
    }

    @Test
    void updateCategory() {
        //not implemented
    }

    @Test
    void deleteCategory() {
        boolean isDeleted = categoryService.deleteCategory("22");

        assertTrue(isDeleted);
    }
}