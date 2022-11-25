//package nl.fontys.marketplacebackend.model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class RatingTest {
//    private User user;
//    private Category c;
//    private Package p;
//
//    @BeforeEach
//    void setup() {
//        user = new User(
//                "fe309b05-f32e-4ecc-8c81-a174e65e2df7",
//                "Borek",
//                "Bandell",
//                "b.bandell@student.fontys.nl",
//                "realPassword",
//                new ArrayList<>()
//        );
//
//        c = new Category(
//                UUID.randomUUID().toString(),
//                "Navigation",
//                "All apps related to navigation"
//        );
//
//        p = new Package(
//                "70a94f10-92a4-4e56-ab10-328dde2f04ab",
//                "Google Maps",
//                "The best navigation out there.",
//                c,
//                user
//        );
//
//        Rating r = new Rating(
//                "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
//                p,
//                user,
//                5,
//                "Awesome!"
//        );
//    }
//
//    @Test
//    void getAverageRatingTestThreeRatings() {
//        Rating r = new Rating(
//                "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
//                p,
//                user,
//                5,
//                "Awesome!"
//        );
//
//        Rating r2 = new Rating(
//                "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
//                p,
//                user,
//                1,
//                "Awesome!"
//        );
//
//        Rating r3 = new Rating(
//                "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
//                p,
//                user,
//                2,
//                "Awesome!"
//        );
//
//        List<Rating> ratings = List.of(r, r2, r3);
//        double expected = (double)8/3;
//
//        assertEquals(expected, Rating.getAverageStarRating(ratings));
//    }
//
//    @Test
//    void getAverageRatingTestZeroRatings() {
//        assertEquals(-1, Rating.getAverageStarRating(null));
//    }
//
//    @Test
//    void getRatingsAmountZeroRatings() {
//        assertEquals(-1, Rating.getRatingsAmount(null));
//    }
//}
