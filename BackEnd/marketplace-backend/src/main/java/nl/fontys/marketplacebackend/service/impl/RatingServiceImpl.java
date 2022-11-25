package nl.fontys.marketplacebackend.service.impl;

import lombok.AllArgsConstructor;
import nl.fontys.marketplacebackend.dto.CreateRatingDto;
import nl.fontys.marketplacebackend.dto.GetRatingDto;
import nl.fontys.marketplacebackend.dto.UpdateRatingDto;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.Rating;
import nl.fontys.marketplacebackend.model.User;
import nl.fontys.marketplacebackend.persistence.PackageRepository;
import nl.fontys.marketplacebackend.persistence.RatingRepository;
import nl.fontys.marketplacebackend.persistence.UserRepository;
import nl.fontys.marketplacebackend.service.RatingService;
import nl.fontys.marketplacebackend.service.exception.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {
    private RatingRepository repository;
    private final UserRepository userRepository;
    private final PackageRepository packageRepository;

    @Override
    public GetRatingDto getRatingById(String id) {
        Rating r = repository.findById(id).orElse(null);
if(r != null) {
    GetRatingDto getRatingDto = new GetRatingDto(
            r.getId(),
            r.getRatingPackage().getId(),
            r.getRatingPackage().getTitle(),
            r.getUser().getId(),
            r.getUser().getFirstName(),
            r.getUser().getLastName(),
            r.getStars(),
            r.getReview()
    );
    return getRatingDto;
}
else {
    return null;
}
    }

    @Override
    public Rating getRatingByRatingPackageAndUser(Package p, User u) {
        return repository.getRatingByRatingPackageAndUser(p, u);
    }

    @Override
    public List<GetRatingDto> getRatingsByUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new InvalidUserException();
        }

        List<Rating> ratings = repository.findRatingsByUser(user.get());
        if(ratings == null) {
            throw new InvalidNoResponseException();
        }

        List<GetRatingDto> getRatings = new ArrayList<>();
        for(Rating r : ratings) {
            GetRatingDto getRatingDto = new GetRatingDto(
                    r.getId(),
                    r.getRatingPackage().getId(),
                    r.getRatingPackage().getTitle(),
                    r.getUser().getId(),
                    r.getUser().getFirstName(),
                    r.getUser().getLastName(),
                    r.getStars(),
                    r.getReview()
            );

            getRatings.add(getRatingDto);
        }
        return getRatings;
    }

    @Override
    public List<Rating> getAllRatings() {
        return repository.findAll();
    }

    @Override
    public List<GetRatingDto> getRatingsByPackage(String id) {
        Optional<Package> p = packageRepository.findById(id);
        if(p.isEmpty()) {
            throw new InvalidPackageException();
        }
        List<Rating> ratings = repository.findRatingByRatingPackage(p.get());
        List<GetRatingDto> getRatings = new ArrayList<>();
        if(ratings.size() != 0) {
            for (Rating r : ratings) {
                String firstName = "User not found";
                String lastName = "";
                String userId = "";

                if (r.getUser() != null) {
                    firstName = r.getUser().getFirstName();
                    lastName = r.getUser().getLastName();
                    userId = r.getUser().getId();
                }
                GetRatingDto getRatingDto = new GetRatingDto(
                        r.getId(),
                        r.getRatingPackage().getId(),
                        r.getRatingPackage().getTitle(),
                        userId,
                        firstName,
                        lastName,
                        r.getStars(),
                        r.getReview()
                );

                getRatings.add(getRatingDto);
            }
        }
        return getRatings;
    }

    @Override
    public Rating createRating(CreateRatingDto createRatingDto) {
        Optional<Package> p = packageRepository.findById(createRatingDto.getPackageId());
        if(p.isEmpty()) {
            throw new InvalidPackageException();
        }

        Optional<User> u = userRepository.findById(createRatingDto.getUserId());
        if(u.isEmpty()) {
            throw new InvalidUserException();
        }
        Rating rating = new Rating(p.get(), u.get(), createRatingDto.getStars(), createRatingDto.getReview());
        Rating r = repository.save(rating);

        return r;
    }

    @Override
    public UpdateRatingDto updateRating(UpdateRatingDto updateRatingDto) {
        GetRatingDto r = this.getRatingById(updateRatingDto.getId());
        if(r == null) {
            throw new PleaseProvideAValidRatingException();
        }
        Optional<Package> updatePackage = packageRepository.findById(r.getPackageId());
        if(updatePackage.isEmpty()) {
            throw new InvalidPackageException();
        }
        Optional<User> updateUser = userRepository.findById(r.getUserId());
        if(updateUser.isEmpty()) {
            throw new InvalidUserException();
        }
        UpdateRatingDto updateRatingDto1 = new UpdateRatingDto();
        if(r != null) {
            Rating updatedRating = new Rating(updateRatingDto.getId(), updatePackage.get(), updateUser.get(), updateRatingDto.getStars(), updateRatingDto.getReview());
            Rating newRating = repository.save(updatedRating);

            updateRatingDto1.setStars(newRating.getStars());
            updateRatingDto1.setId(newRating.getId());
            updateRatingDto1.setReview(newRating.getReview());
        }
        else {
            updateRatingDto1.setStars(0);
            updateRatingDto1.setId("");
            updateRatingDto1.setReview("");
        }
        return updateRatingDto1;
    }

    @Override
    public boolean deleteRating(String id) {
        Optional<Rating> ratingOptional = repository.findById(id);
        if(ratingOptional.isEmpty()) {
            throw new InvalidNoResponseException();
        }
        Rating rating = ratingOptional.get();
         repository.delete(rating);
         return true;
    }
}
