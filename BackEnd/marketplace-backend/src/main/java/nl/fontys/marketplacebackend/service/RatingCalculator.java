package nl.fontys.marketplacebackend.service;

import nl.fontys.marketplacebackend.dto.GetRatingDto;
import nl.fontys.marketplacebackend.model.Rating;

import java.util.List;

public final class RatingCalculator {
    public static double getAverageStarRating(List<Rating> ratings) {
        if(ratings == null) {
            return -1;
        }
        if(ratings.size() == 0) {
            return 0;
        }

        int rating = 0;
        int count = 0;
        for(Rating r : ratings) {
            count++;
            rating += r.getStars();
        }

        return (double)rating/count;
    }

    public static double getAverageStarRatingDto(List<GetRatingDto> ratings)
    {
        if(ratings == null){
            return -1;
        }
        if(ratings.size() == 0)
        {
            return 0;
        }

        int rating = 0;
        int count = 0;
        for(GetRatingDto r : ratings) {
            count++;
            rating += r.getStars();
        }
        return (double)rating/count;
    }

    public static int getRatingsAmount(List<Rating> ratings) {
        if(ratings == null) {
            return -1;
        }

        return ratings.size();
    }

    public static int getRatingDtosAmount(List<GetRatingDto> ratings) {
        if(ratings == null) {
            return -1;
        }

        return ratings.size();
    }
}
