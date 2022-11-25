package nl.fontys.marketplacebackend.persistence;

import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.Rating;
import nl.fontys.marketplacebackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {
    Rating getRatingByRatingPackageAndUser(Package p, User u);
    List<Rating> findRatingsByUser(User u);
    List<Rating> findRatingByRatingPackage(Package p);
}
