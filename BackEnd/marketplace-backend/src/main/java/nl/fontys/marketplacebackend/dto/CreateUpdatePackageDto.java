package nl.fontys.marketplacebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;

@Builder
@Data
public class CreateUpdatePackageDto {
    private String image;
    private String title;
    private String description;
    private String categoryId;
    private String creatorId;


    public CreateUpdatePackageDto(@JsonProperty("image") String image,
                                  @JsonProperty("title") String title,
                                  @JsonProperty("description") String description,
                                  @JsonProperty("categoryId") String categoryId,
                                  @JsonProperty("creatorId") String creatorId) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.creatorId = creatorId;
    }
}
