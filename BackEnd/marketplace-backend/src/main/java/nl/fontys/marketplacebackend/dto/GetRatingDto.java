package nl.fontys.marketplacebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetRatingDto {
    private String id;
    private String packageId;
    private String packageName;
    private String userId;
    private String userFirstName;
    private String userLastName;
    private int stars;
    private String review;
}
