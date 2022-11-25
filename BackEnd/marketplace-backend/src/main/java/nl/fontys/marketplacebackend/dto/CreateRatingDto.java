package nl.fontys.marketplacebackend.dto;

import lombok.Data;

@Data
public class CreateRatingDto {
    private String packageId;
    private String userId;
    private int stars;
    private String review;
}
