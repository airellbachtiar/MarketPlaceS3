package nl.fontys.marketplacebackend.controller;

import lombok.RequiredArgsConstructor;
import nl.fontys.marketplacebackend.config.security.isauthenticated.IsAuthenticated;
import nl.fontys.marketplacebackend.dto.CreateRatingDto;
import nl.fontys.marketplacebackend.dto.GetRatingDto;
import nl.fontys.marketplacebackend.dto.UpdateRatingDto;
import nl.fontys.marketplacebackend.model.Rating;
import nl.fontys.marketplacebackend.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/ratings")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    // Get specific rating
    // GET /ratings/{id}
    @GetMapping("{id}")
    public ResponseEntity<GetRatingDto> getSpecificRating(@PathVariable("id") String id) {
        GetRatingDto getRatingDto = ratingService.getRatingById(id);
        if (getRatingDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(getRatingDto);
    }

    // Get ratings by package
    // GET /ratings/by-package-id/{id}
    @GetMapping("by-package-id/{id}")
    public ResponseEntity<List<GetRatingDto>> getRatingsByPackage(@PathVariable("id") String id) {
        List<GetRatingDto> ratings = ratingService.getRatingsByPackage(id);
        return ResponseEntity.ok().body(Objects.requireNonNullElseGet(ratings, ArrayList::new));
    }

    // Get ratings by user
    // GET /ratings/by-user-id/{id}
    @GetMapping("by-user-id/{id}")
    public ResponseEntity<List<GetRatingDto>> getRatingsByUser(@PathVariable("id") String id) {
        List<GetRatingDto> ratings = ratingService.getRatingsByUser(id);
        return ResponseEntity.ok().body(Objects.requireNonNullElseGet(ratings, ArrayList::new));
    }

    // Create a new rating
    // POST /ratings/
    @PostMapping("")
    @IsAuthenticated
    @Transactional
    public ResponseEntity<CreateRatingDto> createRating(@RequestBody CreateRatingDto createRatingDto) {
        Rating answer = ratingService.createRating(createRatingDto);
        CreateRatingDto createRatingDto1 = new CreateRatingDto();
        createRatingDto1.setUserId(answer.getUser().getId());
        createRatingDto1.setPackageId(answer.getRatingPackage().getId());
        createRatingDto1.setStars(answer.getStars());
        createRatingDto1.setReview(answer.getReview());
        return ResponseEntity.status(HttpStatus.CREATED).body(createRatingDto1);
    }

    // Update a rating
    // PUT /ratings/
    @PutMapping("")
    @IsAuthenticated
    public ResponseEntity<UpdateRatingDto> updateRating(@RequestBody UpdateRatingDto updateRatingDto) {
        UpdateRatingDto r = ratingService.updateRating(updateRatingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(r);
    }

    // Delete a rating
    // DELETE /ratings/{id}
    @DeleteMapping("{id}")
    @IsAuthenticated
    public ResponseEntity<String> deleteRating(@PathVariable String id) {
        if(ratingService.deleteRating(id)) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        }
    }
}
