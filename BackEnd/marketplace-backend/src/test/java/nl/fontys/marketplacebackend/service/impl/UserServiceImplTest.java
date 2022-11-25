package nl.fontys.marketplacebackend.service.impl;

import nl.fontys.marketplacebackend.dto.GetRatingDto;
import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.dto.UserDto;
import nl.fontys.marketplacebackend.dto.userdtos.CreateUserRequestDTO;
import nl.fontys.marketplacebackend.dto.userdtos.UpdateUserRequestDTO;
import nl.fontys.marketplacebackend.model.Category;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.Rating;
import nl.fontys.marketplacebackend.model.User;
import nl.fontys.marketplacebackend.persistence.PackageRepository;
import nl.fontys.marketplacebackend.persistence.RatingRepository;
import nl.fontys.marketplacebackend.persistence.UserRepository;
import nl.fontys.marketplacebackend.service.PackageDtoConverter;
import nl.fontys.marketplacebackend.service.UserDtoConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PackageRepository packageRepositoryMock;

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PackageServiceImpl packageService;

    @Mock
    private RatingServiceImpl ratingService;


    @Test
    void getUserById_shouldReturnUserWithGivenId() {
        String userId = UUID.randomUUID().toString();

        User user = User.builder()
                .id(userId)
                .firstName("Nikola")
                .lastName("Stankovic")
                .email("n.stankov01@gmail.com")
                .password("1234")
                .build();

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));

        UserDto expectedUserDto = UserDtoConverter.convertToDto(user);
        UserDto actualUserDto = userService.getUserById(userId);

        assertEquals(expectedUserDto, actualUserDto);
        verify(userRepositoryMock).findById(userId);
    }

    @Test
    void addUser_shouldReturnTrueWhenUserIsAdded() {
        CreateUserRequestDTO createUserRequestDTO = CreateUserRequestDTO.builder()
                .firstName("Nikola")
                .lastName("Stankovic")
                .email("n.stankov01@gmail.com")
                .password("1234")
                .build();

        boolean isAdded = userService.addUser(createUserRequestDTO);

        assertEquals(isAdded, true);
    }

    @Test()
    void updateUser() {
        String userId = UUID.randomUUID().toString();

        User expectedUser = User.builder()
                .id(userId)
                .firstName("Nikola")
                .lastName("Stankovic")
                .email("n.stankov01@gmail.com")
                .build();

        UpdateUserRequestDTO updateUserRequestDTO = UpdateUserRequestDTO.builder()
                .userID(userId)
                .firstName("Nikolaos")
                .lastName("Stankovic")
                .email("n.stankov01@gmail.com")
                .build();

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(expectedUser));

        userService.updateUser(updateUserRequestDTO);

        assertEquals(UserDtoConverter.convertToDto(expectedUser), userService.getUserById(userId));
        verify(userRepositoryMock, times(2)).findById(userId);
    }

    @Test
    void getAllUsers() {
        List<User> userList = List.of(
                User.builder()
                        .id(UUID.randomUUID().toString())
                        .firstName("Nikolaos")
                        .lastName("Stankovic")
                        .email("n.stankov01@gmail.com")
                        .password("1234")
                        .build(),
                User.builder()
                        .id(UUID.randomUUID().toString())
                        .firstName("Nick")
                        .lastName("Meer")
                        .email("n.meer@gmail.com")
                        .password("3344")
                        .build()
        );

        when(userRepositoryMock.findAll()).thenReturn(userList);

        List<UserDto> expectedUserDtoList = userList
                .stream().map(user -> UserDtoConverter.convertToDto(user))
                .collect(Collectors.toList());
        List<UserDto> actualUserDtoList = userService.getAllUsers();

        assertEquals(expectedUserDtoList, actualUserDtoList);
        verify(userRepositoryMock).findAll();
    }

    @Test
    void addDownloadedPackage() {
        String userId = UUID.randomUUID().toString();
        String packageId = UUID.randomUUID().toString();

        Package expectedPackageToGet = Package.builder()
                .id(packageId)
                .title("Test Package")
                .description("This is for testing purposes.")
                .image("")
                .isActive(true)
                .build();

        User expectedUserToGet = User.builder()
                .id(userId)
                .firstName("Nikolaos")
                .lastName("Stankovic")
                .email("n.stankov01@gmail.com")
                .password("1234")
                .build();

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(expectedUserToGet));
        when(packageRepositoryMock.findById(packageId)).thenReturn(Optional.of(expectedPackageToGet));

        boolean isAdded = userService.addDownloadedPackage(userId, packageId);

        assertEquals(isAdded, true);
    }

//    @Test
//    void removeDownloadedPackage() {
//        String userId = UUID.randomUUID().toString();
//        String packageId = UUID.randomUUID().toString();
//
//        Package packageToDelete = Package.builder()
//                .id(packageId)
//                .title("Test Package")
//                .description("This is for testing purposes.")
//                .image("")
//                .isActive(true)
//                .category(new Category("Test", "Category for testing"))
//                .build();
//
//        User user = User.builder()
//                .id(userId)
//                .firstName("Nikola")
//                .lastName("Stankovic")
//                .email("n.stankov01@gmail.com")
//                .password("1234")
//                .downloadedPackages(List.of(packageToDelete))
//                .build();
//
//        packageToDelete.setContentCreator(user);
//
//        PackageDto packageDtoToDelete = PackageDto.builder()
//                .id(packageToDelete.getId())
//                .title(packageToDelete.getTitle())
//                .description(packageToDelete.getDescription())
//                .image(packageToDelete.getImage())
//                .isActive(packageToDelete.isActive())
//                .category(packageToDelete.getCategory().getName())
//                .creatorId(userId)
//                .creatorFirstName(user.getFirstName())
//                .creatorLastName(user.getLastName())
//                .build();
//
//        Rating rating = new Rating(packageToDelete, user, 4, "It's very good");
//
//        when(ratingRepository.getRatingByRatingPackageAndUser(packageToDelete, user)).thenReturn(rating);
//        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
//        when(packageRepositoryMock.findById(packageId)).thenReturn(Optional.of(packageToDelete));
//        when(packageService.getPackageById(packageId)).thenReturn(packageDtoToDelete);
//
//        userService.removeDownloadedPackage(userId, packageId);
//
//        assertEquals(ratingRepository.getRatingByRatingPackageAndUser(packageToDelete, user), null);
//        assertEquals(userRepositoryMock.findById(userId).get().getDownloadedPackages(), null);
//
//        verify(ratingRepository).getRatingByRatingPackageAndUser(packageToDelete, user);
//        verify(userRepositoryMock).findById(userId);
//        verify(packageRepositoryMock).findById(packageId);
//        verify(packageService).getPackageById(packageId);
//    }

    @Test
    void getDownloadedPackagesPerUser() {
        String packageId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        Package packageExample = Package.builder()
                .id(packageId)
                .title("Test Package")
                .description("This is for testing purposes.")
                .image("")
                .category(new Category("Test", "Category for testing"))
                .isActive(true)
                .build();

        User user = User.builder()
                .id(userId)
                .firstName("Nikola")
                .lastName("Stankovic")
                .email("n.stankov01@gmail.com")
                .password("1234")
                .downloadedPackages(List.of(packageExample))
                .build();

        packageExample.setContentCreator(user);

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));

        List<Package> expectedDownloadedPackages = user.getDownloadedPackages();
        List<PackageDto> actualResponse = userService.getDownloadedPackagesPerUser(userId);

        List<Package> actualDownloadedPackages = new ArrayList<>();

        for (PackageDto packageDto : actualResponse) {
            Package p = Package.builder()
                    .id(packageDto.getId())
                    .title(packageDto.getTitle())
                    .description(packageDto.getDescription())
                    .image(packageDto.getImage())
                    .category(packageExample.getCategory())
                    .isActive(packageDto.isActive())
                    .contentCreator(user)
                    .build();

            actualDownloadedPackages.add(p);
        }

        assertEquals(expectedDownloadedPackages, actualDownloadedPackages);
        verify(userRepositoryMock).findById(userId);
    }

//    @Test
//    void getDownloadedPackagePerUser() {
//        String packageId = UUID.randomUUID().toString();
//        String userId = UUID.randomUUID().toString();
//
//        Package packageExample = Package.builder()
//                .id(packageId)
//                .title("Test Package")
//                .description("This is for testing purposes.")
//                .image("")
//                .category(new Category("Test", "Category for testing"))
//                .isActive(true)
//                .build();
//
//        User user = User.builder()
//                .id(userId)
//                .firstName("Nikola")
//                .lastName("Stankovic")
//                .email("n.stankov01@gmail.com")
//                .password("1234")
//                .downloadedPackages(List.of(packageExample))
//                .build();
//
//        Rating ratingExample = new Rating("22", packageExample, user, 5, "Extremely good");
//
//        packageExample.setContentCreator(user);
//
//        PackageDto expectedResponse = PackageDtoConverter.convertToDto(packageExample, List.of(GetRatingDto.builder()
//                .id(packageExample.getId())
//                .packageId(packageId)
//                .packageName(packageExample.getTitle())
//                .userId(userId)
//                .userFirstName(packageExample.getContentCreator().getFirstName())
//                .userLastName(packageExample.getContentCreator().getLastName())
//                .build()));
//
//        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
//        when(packageRepositoryMock.findById(packageId)).thenReturn(Optional.of(packageExample));
//        when(ratingRepository.findRatingByRatingPackage(packageExample)).thenReturn(List.of(ratingExample));
//        when(packageService.getPackageById(packageId)).thenReturn(expectedResponse);
//
//        PackageDto actualResponse = userService.getDownloadedPackagePerUser(userId, packageId);
//
//        assertEquals(expectedResponse, actualResponse);
//        verify(userRepositoryMock).findById(userId);
//        verify(packageRepositoryMock).findById(packageId);
//        verify(ratingRepository).findRatingByRatingPackage(packageExample);
//        verify(packageService).getPackageById(packageId);
//    }
}