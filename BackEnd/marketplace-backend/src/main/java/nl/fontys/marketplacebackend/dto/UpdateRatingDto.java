package nl.fontys.marketplacebackend.dto;

import lombok.Data;

@Data
public class UpdateRatingDto {
    private String id;
    private int stars;
    private String review;
}
