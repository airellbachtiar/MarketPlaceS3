package nl.fontys.marketplacebackend.service;

import nl.fontys.marketplacebackend.dto.CreateRatingDto;
import nl.fontys.marketplacebackend.dto.GetRatingDto;
import nl.fontys.marketplacebackend.dto.UpdateRatingDto;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.Rating;
import nl.fontys.marketplacebackend.model.User;

import java.util.List;

public interface RatingService {
    GetRatingDto getRatingById(String id);
    Rating getRatingByRatingPackageAndUser(Package p, User u);
    List<GetRatingDto> getRatingsByUser(String id);
    List<Rating> getAllRatings();
    List<GetRatingDto> getRatingsByPackage(String id);
    Rating createRating(CreateRatingDto createRatingDto);
    UpdateRatingDto updateRating(UpdateRatingDto updateRatingDto);
    boolean deleteRating(String id);
}
