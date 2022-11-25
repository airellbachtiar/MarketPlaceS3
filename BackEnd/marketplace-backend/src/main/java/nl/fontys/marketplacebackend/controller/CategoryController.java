package nl.fontys.marketplacebackend.controller;

import lombok.RequiredArgsConstructor;
import nl.fontys.marketplacebackend.dto.CategoryDto;
import nl.fontys.marketplacebackend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoryDTOS = this.categoryService.getAllCategories();
        if (!categoryDTOS.isEmpty()) {
            return ResponseEntity.ok().body(categoryDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
