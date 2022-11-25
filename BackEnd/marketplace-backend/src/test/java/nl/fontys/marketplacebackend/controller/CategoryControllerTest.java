package nl.fontys.marketplacebackend.controller;

import nl.fontys.marketplacebackend.dto.CategoryDto;
import nl.fontys.marketplacebackend.model.Category;
import nl.fontys.marketplacebackend.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private Category createFakeCategory() {
        return Category.builder()
                .id("22")
                .name("Test category")
                .description("Testing the category service")
                .build();
    }

    @Test
    void getAllCategories_shouldReturnAllCategoriesFromRepo() throws Exception {
        Category category = createFakeCategory();

        List<CategoryDto> expectedCategories = List.of(CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build());

        when(categoryService.getAllCategories()).thenReturn(expectedCategories);

        mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        [{"id":  "22", "name":  "Test category", "description":  "Testing the category service"}]
                          """));

        verify(categoryService).getAllCategories();
    }

    @Test
    void getAllCategories_shouldReturn404WhenNotFound() throws Exception {
        List<CategoryDto> expectedCategories = new ArrayList<>();

        when(categoryService.getAllCategories()).thenReturn(expectedCategories);

        mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(categoryService).getAllCategories();
    }
}