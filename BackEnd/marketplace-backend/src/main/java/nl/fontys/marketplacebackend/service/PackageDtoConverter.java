package nl.fontys.marketplacebackend.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import nl.fontys.marketplacebackend.dto.GetRatingDto;
import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.model.Package;

import java.util.List;

@UtilityClass
public final class PackageDtoConverter {

    public static PackageDto convertToDto(Package packageModel, List<GetRatingDto> ratings)
    {
        return PackageDto.builder()
                .id(packageModel.getId())
                .title(packageModel.getTitle())
                .description(packageModel.getDescription())
                .image(packageModel.getImage())
                .categoryId(packageModel.getCategory().getId())
                .category(packageModel.getCategory().getName())
                .creatorId(packageModel.getContentCreator().getId())
                .creatorFirstName(packageModel.getContentCreator().getFirstName())
                .creatorLastName(packageModel.getContentCreator().getLastName())
                .averageStarRating(RatingCalculator.getAverageStarRatingDto(ratings))
                .ratingsAmount(RatingCalculator.getRatingDtosAmount(ratings))
                .isActive(packageModel.isActive())
                .build();
    }
}
