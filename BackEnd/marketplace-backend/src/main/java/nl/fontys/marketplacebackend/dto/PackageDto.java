package nl.fontys.marketplacebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.annotation.sql.DataSourceDefinitions;

@NoArgsConstructor
@Builder
@Data
public class PackageDto {
    private String id;
    private String title;
    private String description;
    private String image;
    private boolean isActive;
    private String categoryId;
    private String category;
    private String creatorId;
    private String creatorFirstName;
    private String creatorLastName;
    private double averageStarRating;
    private int ratingsAmount;

    public PackageDto(@JsonProperty("id") String id,
                      @JsonProperty("title") String title,
                      @JsonProperty("description") String description,
                      @JsonProperty("image") String image,
                      @JsonProperty("is_active") boolean isActive,
                      @JsonProperty("categoryId") String categoryId,
                      @JsonProperty("category") String category,
                      @JsonProperty("creatorId") String creatorId,
                      @JsonProperty("creatorFirstName") String creatorFirstName,
                      @JsonProperty("creatorLastName") String creatorLastName,
                      @JsonProperty("averageStarRating") double averageStarRating,
                      @JsonProperty("ratingsAmount") int ratingsAmount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.isActive = isActive;
        this.categoryId = categoryId;
        this.category = category;
        this.creatorId = creatorId;
        this.creatorFirstName = creatorFirstName;
        this.creatorLastName = creatorLastName;
        this.averageStarRating = averageStarRating;
        this.ratingsAmount = ratingsAmount;
    }
}
