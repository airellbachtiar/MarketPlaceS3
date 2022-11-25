package nl.fontys.marketplacebackend.service.impl;

import nl.fontys.marketplacebackend.dto.CreateRatingDto;
import nl.fontys.marketplacebackend.dto.GetRatingDto;
import nl.fontys.marketplacebackend.dto.UpdateRatingDto;
import nl.fontys.marketplacebackend.model.Category;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.Rating;
import nl.fontys.marketplacebackend.model.User;
import nl.fontys.marketplacebackend.persistence.PackageRepository;
import nl.fontys.marketplacebackend.persistence.RatingRepository;
import nl.fontys.marketplacebackend.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingServiceImplTest {
    @Mock
    private RatingRepository mockRatingRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PackageRepository mockPackageRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Captor
    private ArgumentCaptor<Rating> ratingCaptor;

    @Test
    void getRatingByIdTest() {
        User user = new User(
                UUID.randomUUID().toString(),
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
        );

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );
        Package p = new Package( UUID.randomUUID().toString(),
                "Google Maps",
                "The best navigation out there.",
                c,
                user);

        String ratingId = UUID.randomUUID().toString();

        Rating r = new Rating(
                ratingId,
                p,
                user,
                5,
                "Awesome!"
        );
        GetRatingDto testRating = new GetRatingDto(      r.getId(),
                p.getId(),
                p.getTitle(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                5,
                "Awesome!");

        when(mockRatingRepository.findById(ratingId)).thenReturn(Optional.ofNullable(r));

        //Rating responseRating = ratingService.getRatingById(ratingId);
        GetRatingDto responseRating = ratingService.getRatingById(ratingId);
        assertEquals(testRating, responseRating);
        //assertEquals(r, responseRating);
        verify(mockRatingRepository).findById(ratingId);
    }

    @Test
    void getRatingByInvalidIdTest() {
        when(mockRatingRepository.findById("hello")).thenReturn(Optional.empty());

        GetRatingDto responseRating = ratingService.getRatingById("hello");

        assertEquals(null, responseRating);
        verify(mockRatingRepository).findById("hello");
    }

    @Test
    void getRatingByRatingPackageAndUserTest() {
        User user = new User(
                UUID.randomUUID().toString(),
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
        );

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );

        Package p = new Package(
                UUID.randomUUID().toString(),
                "Google Maps",
                "The best navigation out there.",
                c,
                user
        );

        Rating r = new Rating(
                UUID.randomUUID().toString(),
                p,
                user,
                5,
                "Awesome!"
        );

        when(mockRatingRepository.getRatingByRatingPackageAndUser(p, user)).thenReturn(r);
        Rating responseRating = ratingService.getRatingByRatingPackageAndUser(p, user);
        assertEquals(r, responseRating);
        verify(mockRatingRepository).getRatingByRatingPackageAndUser(p, user);
    }

    @Test
    void getRatingByWrongRatingPackageAndUserTest() {
        when(mockRatingRepository.getRatingByRatingPackageAndUser(null, null)).thenReturn(null);
        Rating responseRating = ratingService.getRatingByRatingPackageAndUser(null, null);
        assertEquals(null, responseRating);
        verify(mockRatingRepository).getRatingByRatingPackageAndUser(null, null);
    }

    @Test
    void getRatingsByUserTest() {
        User user = new User(
                UUID.randomUUID().toString(),
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
        );

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );

        Package p = new Package(
                UUID.randomUUID().toString(),
                "Google Maps",
                "The best navigation out there.",
                c,
                user
        );

        Rating r1 = new Rating(
                "8dfec0a8-a4c0-47df-9861-31acd9764c83",
                p,
                user,
                5,
                "Awesome!"
        );

        GetRatingDto r2 = new GetRatingDto(
                "8dfec0a8-a4c0-47df-9861-31acd9764c83",
                p.getId(),
       p.getTitle(),
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
       5,
                "Awesome!"
        );
when(mockUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> u2 = mockUserRepository.findById(user.getId());

        List<GetRatingDto> expectedRatings = List.of(r2);
        List<Rating> ratingList = List.of(r1);
        when(mockRatingRepository.findRatingsByUser(user)).thenReturn(ratingList);
        List<GetRatingDto> responseRatings = ratingService.getRatingsByUser(user.getId());

        assertEquals(expectedRatings, responseRatings);
        verify(mockRatingRepository).findRatingsByUser(user);
    }

    @Test
    void getAllRatingsTest() {
        User user = new User(
                UUID.randomUUID().toString(),
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
        );

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );

        Package p = new Package(
                UUID.randomUUID().toString(),
                "Google Maps",
                "The best navigation out there.",
                c,
                user
        );

        Rating r = new Rating(
                UUID.randomUUID().toString(),
                p,
                user,
                5,
                "Awesome!"
        );

        List<Rating> expectedRatings = List.of(r);
        when(mockRatingRepository.findAll()).thenReturn(expectedRatings);

        List<Rating> responseRatings = ratingService.getAllRatings();
        assertEquals(expectedRatings, responseRatings);
        verify(mockRatingRepository).findAll();
    }

    @Test
    void getRatingsByPackageTest() {
        User user = new User(
                UUID.randomUUID().toString(),
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
        );

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );

        Package p = new Package(
                UUID.randomUUID().toString(),
                "Google Maps",
                "The best navigation out there.",
                c,
                user
        );

        Rating r = new Rating(
                UUID.randomUUID().toString(),
                p,
                user,
                5,
                "Awesome!"
        );
        GetRatingDto getRatingDto = new GetRatingDto(
        r.getId(),
        p.getId(),
        p.getTitle(),
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        5,
                "Awesome!"
        );
        List<GetRatingDto> expectedRatings2 = List.of(getRatingDto);
when(mockPackageRepository.findById(p.getId())).thenReturn(Optional.of(p));
Optional<Package> returnPackage = mockPackageRepository.findById(p.getId());

        List<Rating> expectedRatings = List.of(r);
        when(mockRatingRepository.findRatingByRatingPackage(p)).thenReturn(expectedRatings);

        List<GetRatingDto> responseRatings = ratingService.getRatingsByPackage(p.getId());
        assertEquals(expectedRatings2, responseRatings);
        verify(mockRatingRepository).findRatingByRatingPackage(p);
    }

    @Test
    void getRatingsByInvalidPackageTest() {
        List<Rating> expectedRatings = new ArrayList<>();
        User user = new User(
                UUID.randomUUID().toString(),
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
        );

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );
        Package p = new Package(
                UUID.randomUUID().toString(),
                "Google Maps",
                "The best navigation out there.",
                c,
                user
        );
        when(mockPackageRepository.findById(p.getId())).thenReturn(Optional.of(p));
        Optional<Package> returnPackage = mockPackageRepository.findById(p.getId());

        when(mockRatingRepository.findRatingByRatingPackage(p)).thenReturn(expectedRatings);
        List<GetRatingDto> responseRatings = ratingService.getRatingsByPackage(p.getId());

        assertEquals(expectedRatings, responseRatings);
        verify(mockRatingRepository).findRatingByRatingPackage(p);
    }

    @Test
    void updateRatingTest() {
        User user = new User(
                UUID.randomUUID().toString(),
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
        );

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );

        Package p = new Package(
                UUID.randomUUID().toString(),
                "Google Maps",
                "The best navigation out there.",
                c,
                user
        );

        Rating r = new Rating(
                UUID.randomUUID().toString(),
                p,
                user,
                5,
                "Awesome!"
        );

        UpdateRatingDto updateRatingDto = new UpdateRatingDto();
        updateRatingDto.setId(r.getId());
        updateRatingDto.setReview(r.getReview());
        updateRatingDto.setStars(r.getStars());
        when(mockPackageRepository.findById(p.getId())).thenReturn(Optional.of(p));
        Optional<Package> returnPackage = mockPackageRepository.findById(p.getId());

        when(mockUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> returnUser = mockUserRepository.findById(user.getId());

        when(mockRatingRepository.findById(r.getId())).thenReturn(Optional.of(r));
        Optional<Rating> returnRating = mockRatingRepository.findById(r.getId());

        when(mockRatingRepository.save(r)).thenReturn(r);
        UpdateRatingDto responseRating = ratingService.updateRating(updateRatingDto);

        assertEquals(updateRatingDto, responseRating);
        verify(mockRatingRepository).save(r);
    }

    @Test
    void createRatingTest() {
        User user = new User(
                UUID.randomUUID().toString(),
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
                );

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );

        Package p = new Package(
                UUID.randomUUID().toString(),
                "Google Maps",
                "The best navigation out there.",
                c,
                user
        );

        Rating r = new Rating(
                p,
                user,
                5,
                "Awesome!"
        );
        CreateRatingDto createRatingDto = new CreateRatingDto();
        createRatingDto.setPackageId(p.getId());
        createRatingDto.setUserId(user.getId());
        createRatingDto.setStars(r.getStars());
        createRatingDto.setReview(r.getReview());
        when(mockPackageRepository.findById(createRatingDto.getPackageId())).thenReturn(Optional.of(p));
        Optional<Package> returnPackage = mockPackageRepository.findById(createRatingDto.getPackageId());
        assertEquals(returnPackage.get(), p);

        when(mockUserRepository.findById(createRatingDto.getUserId())).thenReturn(Optional.of(user));
        Optional<User> returnUser = mockUserRepository.findById(createRatingDto.getUserId());
        assertEquals(returnUser.get(), user);

       /* ArgumentCaptor<Rating> captor = ArgumentCaptor.forClass(Rating.class);
        when(mockRatingRepository.save(captor.capture())).thenReturn(captor.capture());*/

        //when(mockRatingRepository.save(any())).thenReturn(r);

        when(mockRatingRepository.save(ratingCaptor.capture())).thenReturn(r);
        Rating checkRating = ratingService.createRating(createRatingDto);

        assertEquals(r, checkRating);
        verify(mockRatingRepository).save(ratingCaptor.capture());

        //verify(mockRatingRepository).save(captor.capture());
    }

    @Test
    void deleteRatingTest() {
        User user = new User(
                UUID.randomUUID().toString(),
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
        );

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );

        Package p = new Package(
                UUID.randomUUID().toString(),
                "Google Maps",
                "The best navigation out there.",
                c,
                user
        );

        Rating r = new Rating(
                UUID.randomUUID().toString(),
                p,
                user,
                5,
                "Awesome!"
        );
        when(mockRatingRepository.findById(r.getId())).thenReturn(Optional.of(r));
        Optional<Rating> returnRating = mockRatingRepository.findById(r.getId());
        assertEquals(r, returnRating.get());
        assertTrue(ratingService.deleteRating(r.getId()));
    }
}
