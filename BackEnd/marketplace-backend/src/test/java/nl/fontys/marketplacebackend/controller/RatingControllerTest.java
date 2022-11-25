package nl.fontys.marketplacebackend.controller;

import nl.fontys.marketplacebackend.dto.*;
import nl.fontys.marketplacebackend.model.Category;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.Rating;
import nl.fontys.marketplacebackend.model.User;
import nl.fontys.marketplacebackend.service.PackageService;
import nl.fontys.marketplacebackend.service.RatingService;
import nl.fontys.marketplacebackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
public class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private PackageService packageService;

    @MockBean
    private UserService userService;

    @Test
    void getRatingById_shouldReturn200ResponseWithRating() throws Exception {
        User user = new User(
                "fe309b05-f32e-4ecc-8c81-a174e65e2df7",
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
                "70a94f10-92a4-4e56-ab10-328dde2f04ab",
                "Google Maps",
                "The best navigation out there.",
                null,
       true,

      c,
      user
        );

        Rating r = new Rating(
                "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p,
                user,
                5,
                "Awesome!"
        );
        GetRatingDto getRatingDto = new GetRatingDto(      "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p.getId(),
                p.getTitle(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                5,
                "Awesome!");
        when(ratingService.getRatingById(Mockito.any(String.class))).thenReturn(getRatingDto);

        mockMvc.perform(get("/ratings/" + r.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {"id":"ba92e441-0ecd-4965-9050-b6cb491fc3a8","packageId":"70a94f10-92a4-4e56-ab10-328dde2f04ab","packageName":"Google Maps","userId":"fe309b05-f32e-4ecc-8c81-a174e65e2df7","userFirstName":"Borek","userLastName":"Bandell","stars":5,"review":"Awesome!"}
                        """));

        verify(ratingService).getRatingById(r.getId());
    }

    @Test
    void getRatingById_shouldReturn404Response() throws Exception {
        when(ratingService.getRatingById("hello")).thenReturn(null);

        mockMvc.perform(get("/ratings/" + "hello"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(ratingService).getRatingById("hello");
    }

    @Test
    void getRatingsByPackage_shouldReturn200ResponseWithRatings () throws Exception {
        User user = new User(
                "fe309b05-f32e-4ecc-8c81-a174e65e2df7",
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
        );

        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getEmail());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );

        Package p = new Package(
                "70a94f10-92a4-4e56-ab10-328dde2f04ab",
                "Google Maps",
                "The best navigation out there.",
                null,
                true,

                c,
                user
        );
        PackageDto packageDto = new PackageDto();
        packageDto.setId(p.getId());
        packageDto.setTitle(p.getTitle());
        packageDto.setDescription(p.getDescription());
        packageDto.setImage(p.getImage());
        packageDto.setActive(p.isActive());
        packageDto.setCategoryId(p.getCategory().getId());
        packageDto.setCategory(p.getCategory().getName());
        packageDto.setCreatorId(p.getContentCreator().getId());
        packageDto.setCreatorFirstName(p.getContentCreator().getFirstName());
        packageDto.setCreatorLastName(p.getContentCreator().getLastName());
        packageDto.setAverageStarRating(5);

        GetRatingDto getRatingDto = new GetRatingDto(      "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p.getId(),
                p.getTitle(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                5,
                "Awesome!");
        List<GetRatingDto> ratingsDTO = List.of(getRatingDto);

        when(packageService.getPackageById(Mockito.any(String.class))).thenReturn(packageDto);
        when(ratingService.getRatingsByPackage(p.getId())).thenReturn(ratingsDTO);
        when(userService.getUserById(Mockito.any(String.class))).thenReturn(userDTO);

        mockMvc.perform(get("/ratings/by-package-id/" + packageDto.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            [{"id":"ba92e441-0ecd-4965-9050-b6cb491fc3a8","packageId":"70a94f10-92a4-4e56-ab10-328dde2f04ab","packageName":"Google Maps","userId":"fe309b05-f32e-4ecc-8c81-a174e65e2df7","userFirstName":"Borek","userLastName":"Bandell","stars":5,"review":"Awesome!"}]
                        """));

        verify(ratingService).getRatingsByPackage(p.getId());
    }

    @Test
    void getRatingsByUser_shouldReturn200ResponseWithRatings() throws Exception {
        User user = new User(
                "fe309b05-f32e-4ecc-8c81-a174e65e2df7",
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
        );

        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getEmail());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );

        Package p = new Package(
                "70a94f10-92a4-4e56-ab10-328dde2f04ab",
                "Google Maps",
                "The best navigation out there.",
                null,
                true,

                c,
                user
        );

        Rating r = new Rating(
                "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p,
                user,
                5,
                "Awesome!"
        );

        GetRatingDto getRatingDto = new GetRatingDto(      "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p.getId(),
                p.getTitle(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                5,
                "Awesome!");
        List<GetRatingDto> ratingsDTO = List.of(getRatingDto);

        when(userService.getUserById(Mockito.any(String.class))).thenReturn(userDTO);
        when(ratingService.getRatingsByUser(user.getId())).thenReturn(ratingsDTO);

        mockMvc.perform(get("/ratings/by-user-id/" + user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            [{"id":"ba92e441-0ecd-4965-9050-b6cb491fc3a8","packageId":"70a94f10-92a4-4e56-ab10-328dde2f04ab","packageName":"Google Maps","userId":"fe309b05-f32e-4ecc-8c81-a174e65e2df7","userFirstName":"Borek","userLastName":"Bandell","stars":5,"review":"Awesome!"}]
                        """));

        verify(ratingService).getRatingsByUser(user.getId());
    }

    @Test
    void createRating_shouldReturn201WithRatingId() throws Exception {
        User user = new User(
                "fe309b05-f32e-4ecc-8c81-a174e65e2df7",
                "Borek",
                "Bandell",
                "b.bandell@student.fontys.nl",
                "realPassword",
                new ArrayList<>()
        );

        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getEmail());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());

        Category c = new Category(
                UUID.randomUUID().toString(),
                "Navigation",
                "All apps related to navigation"
        );

        Package p = new Package(
                "70a94f10-92a4-4e56-ab10-328dde2f04ab",
                "Google Maps",
                "The best navigation out there.",
                c,
                user
        );
        p.setId("70a94f10-92a4-4e56-ab10-328dde2f04ab");

        PackageDto packageDto = new PackageDto();
        packageDto.setId("70a94f10-92a4-4e56-ab10-328dde2f04ab");
        packageDto.setTitle(p.getTitle());
        packageDto.setDescription(p.getDescription());
        packageDto.setImage(p.getImage());
        packageDto.setActive(p.isActive());
        packageDto.setCategoryId(p.getCategory().getId());
        packageDto.setCategory(p.getCategory().getName());
        packageDto.setCreatorId(p.getContentCreator().getId());
        packageDto.setCreatorFirstName(p.getContentCreator().getFirstName());
        packageDto.setCreatorLastName(p.getContentCreator().getLastName());
        packageDto.setAverageStarRating(5);

        Rating r = new Rating(
                "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p,
                user,
                5,
                "Awesome!"
        );
        GetRatingDto getRatingDto = new GetRatingDto(      "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p.getId(),
                p.getTitle(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                5,
                "Awesome!");
        List<GetRatingDto> ratingsDTO = List.of(getRatingDto);

        CreateRatingDto createRatingDto = new CreateRatingDto();
        createRatingDto.setUserId(user.getId());
        createRatingDto.setPackageId(p.getId());
        createRatingDto.setStars(5);
        createRatingDto.setReview("Awesome!");

        List<Rating> ratings = List.of(r);

        when(ratingService.createRating(Mockito.any(CreateRatingDto.class))).thenReturn(r);

        mockMvc.perform(post("/ratings").contentType(APPLICATION_JSON_VALUE).content("""
                        {
                           "userId": "fe309b05-f32e-4ecc-8c81-a174e65e2df7",
                            "packageId": "70a94f10-92a4-4e56-ab10-328dde2f04ab",
                            "stars": 5,
                            "review": "Awesome!"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {
                            "userId": "fe309b05-f32e-4ecc-8c81-a174e65e2df7",
                            "packageId": "70a94f10-92a4-4e56-ab10-328dde2f04ab",
                            "stars": 5,
                            "review": "Awesome!" }
                       """));

        verify(ratingService).createRating(createRatingDto);
    }

    @Test
    void updateRating_shouldReturn204() throws Exception {
        User user = new User(
                "fe309b05-f32e-4ecc-8c81-a174e65e2df7",
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
                "70a94f10-92a4-4e56-ab10-328dde2f04ab",
                "Google Maps",
                "The best navigation out there.",
                c,
                user
        );
        p.setId("70a94f10-92a4-4e56-ab10-328dde2f04ab");

        Rating r = new Rating(
                "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p,
                user,
                5,
                "Awesome!"
        );

        Rating r2 = new Rating(
                "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p,
                user,
                4,
                "Awesome"
        );

        GetRatingDto getRatingDto = new GetRatingDto(      "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p.getId(),
                p.getTitle(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                5,
                "Awesome!");

        UpdateRatingDto updateRatingDto1 = new UpdateRatingDto();
        updateRatingDto1.setStars(4);
        updateRatingDto1.setId(r.getId());
        updateRatingDto1.setReview("Awesome");

        when(ratingService.updateRating(updateRatingDto1)).thenReturn(updateRatingDto1);

        mockMvc.perform(put("/ratings").contentType(APPLICATION_JSON_VALUE).content("""
                        {
                            "id": "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                            "stars": 4,
                            "review": "Awesome"
                        }
                        """))
                .andDo(print())
                .andExpect(content().json("""
                            {
                                "id": "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                                "stars": 4,
                                "review": "Awesome"
                            }
                       """));
        verify(ratingService).updateRating(Mockito.any(UpdateRatingDto.class));
    }

    @Test
    void updateRating_notExistingRatingShouldReturn404WithMessage() throws Exception {
        UpdateRatingDto updateRatingDto1 = new UpdateRatingDto();
        updateRatingDto1.setStars(5);
        updateRatingDto1.setId("hello");
        updateRatingDto1.setReview("Awesome!");

        UpdateRatingDto updateRatingDto2 = new UpdateRatingDto();
        updateRatingDto2.setStars(0);
        updateRatingDto2.setId("");
        updateRatingDto2.setReview("");

        when(ratingService.updateRating(updateRatingDto1)).thenReturn(updateRatingDto2);
        mockMvc.perform(put("/ratings").contentType(APPLICATION_JSON_VALUE).content("""
                        {
                            "id": "hello",
                            "stars": 5,
                            "review": "Awesome!"
                        }
                        """))
                .andDo(print())
                .andExpect(content().json("""
                            {
                                "id": "",
                                "stars": 0,
                                "review": ""
                            }
                       """));
        verify(ratingService).updateRating(updateRatingDto1);
    }

    @Test
    void deleteRating_shouldReturn201() throws Exception {
        User user = new User(
                "fe309b05-f32e-4ecc-8c81-a174e65e2df7",
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
                "70a94f10-92a4-4e56-ab10-328dde2f04ab",
                "Google Maps",
                "The best navigation out there.",
                c,
                user
        );
        p.setId("70a94f10-92a4-4e56-ab10-328dde2f04ab");

        Rating r = new Rating(
                "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p,
                user,
                5,
                "Awesome!"
        );

        GetRatingDto getRatingDto = new GetRatingDto(
                "ba92e441-0ecd-4965-9050-b6cb491fc3a8",
                p.getId(),
                p.getTitle(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                5,
                "Awesome!"
        );
        when(ratingService.deleteRating(getRatingDto.getId())).thenReturn(true);

        mockMvc.perform(delete("/ratings/ba92e441-0ecd-4965-9050-b6cb491fc3a8"))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(ratingService).deleteRating(getRatingDto.getId());
    }

    @Test
    void deleteRating_notExistingRatingShouldReturnEmptyBody() throws Exception {
        when(ratingService.deleteRating("ba92e441-0ecd-4965-9050-b6cb491fc3a8")).thenReturn(false);

        mockMvc.perform(delete("/ratings/ba92e441-0ecd-4965-9050-b6cb491fc3a8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", "text/plain;charset=UTF-8"))
                .andExpect(content().string(""));

        verify(ratingService).deleteRating("ba92e441-0ecd-4965-9050-b6cb491fc3a8");
    }
}
