package nl.fontys.marketplacebackend.persistence;

import nl.fontys.marketplacebackend.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findCategoryByName(String name);
}
