package nl.fontys.marketplacebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Rating")
public class Rating {

    public Rating(Package ratingPackage, User user, int stars, String review) {
        this.id = UUID.randomUUID().toString();
        this.ratingPackage = ratingPackage;
        this.user = user;
        this.stars = stars;
        this.review = review;
    }

    @Id
    private String id;
    @DBRef
    private Package ratingPackage;
    @DBRef
    private User user;
    private int stars;
    private String review;
}
