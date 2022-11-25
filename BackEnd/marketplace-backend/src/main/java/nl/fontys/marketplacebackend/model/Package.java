package nl.fontys.marketplacebackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Package")
@Builder
public class Package {

    @Id
    private String id;
    private String title;
    private String description;
    private String image;
    private boolean isActive;

    @DBRef
    private Category category;
    @DBRef
    private User contentCreator;

    public Package(String image,
                   String title,
                   String description,
                   Category category,
                   User contentCreator) {
        this.id = UUID.randomUUID().toString();
        this.image = image;
        this.title = title;
        this.description = description;
        this.category = category;
        this.contentCreator = contentCreator;
        this.isActive = true;
    }

//    public String toString() {
//        return this.title + " " + this.description;
//    }
}
