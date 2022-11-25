package nl.fontys.marketplacebackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "Category")
public class Category {

    @Id
    private String id;
    private String name;
    private String description;


    public Category(String name, String description)
    {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }

}
