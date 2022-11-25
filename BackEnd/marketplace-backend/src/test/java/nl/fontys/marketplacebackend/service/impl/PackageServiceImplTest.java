package nl.fontys.marketplacebackend.service.impl;

import net.bytebuddy.build.ToStringPlugin;
import nl.fontys.marketplacebackend.dto.CreatePackageDTO;
import nl.fontys.marketplacebackend.dto.CreateUpdatePackageDto;
import nl.fontys.marketplacebackend.dto.GetRatingDto;
import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.model.Category;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.User;
import nl.fontys.marketplacebackend.persistence.PackageRepository;
import nl.fontys.marketplacebackend.persistence.UserRepository;
import nl.fontys.marketplacebackend.service.CategoryService;
import nl.fontys.marketplacebackend.service.PackageDtoConverter;
import nl.fontys.marketplacebackend.service.RatingService;
import nl.fontys.marketplacebackend.service.exception.InvalidCategoryException;
import nl.fontys.marketplacebackend.service.exception.InvalidPackageException;
import nl.fontys.marketplacebackend.service.exception.InvalidUserException;
import nl.fontys.marketplacebackend.service.exception.PackageNotFoundException;
import nl.fontys.marketplacebackend.service.loginservices.exception.InvalidLoginException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.swing.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PackageServiceImplTest {

    @Mock
    private PackageRepository repo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private PackageServiceImpl packageServiceImpl;


    //@Test
    void addPackage_ShouldReturnAddedPackage()
    {
        String categoryID = "categoryID";
        String userID = "userID";
        String title = "title";

        Category category = Category.builder().id(categoryID).build();
        CreatePackageDTO dto = CreatePackageDTO.builder().categoryId(categoryID).creatorId(userID).image("image").title(title).description("description").build();
        User user = User.builder().id(userID).build();

        Package packageToAdd = Package.builder().image("image").title(title).description("description").category(category).contentCreator(user).build();

        when(categoryService.getCategoryById(categoryID)).thenReturn(category);
        when(userRepository.findById(userID)).thenReturn(Optional.of(user));
        when(repo.save(packageToAdd)).thenReturn(packageToAdd);

        Package actualPackage = packageServiceImpl.addPackage(dto);

        assertEquals(actualPackage.getTitle(), packageToAdd.getTitle());

        verify(categoryService).getCategoryById(categoryID);
        verify(userRepository).findById(userID);
        verify(repo).save(packageToAdd);
    }

    @Test
    void addPackage_ShouldReturnInvalidCategoryException()
    {
        String categoryID = "categoryID";
        String userID = "userID";

        Category category = Category.builder().id(categoryID).build();
        CreatePackageDTO dto = CreatePackageDTO.builder().categoryId(categoryID).creatorId(userID).image("image").title("title").description("description").build();


        when(categoryService.getCategoryById(categoryID)).thenReturn(null);

        InvalidCategoryException exception = assertThrows(InvalidCategoryException.class, () -> packageServiceImpl.addPackage(dto));
        String expectedMessage = "404 NOT_FOUND \"Invalid Category\"";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void addPackage_ShouldReturnInvalidUserException()
    {
        String categoryID = "categoryID";
        String userID = "userID";

        Category category = Category.builder().id(categoryID).build();
        CreatePackageDTO dto = CreatePackageDTO.builder().categoryId(categoryID).creatorId(userID).image("image").title("title").description("description").build();


        when(categoryService.getCategoryById(categoryID)).thenReturn(category);
        when(userRepository.findById(userID)).thenReturn(Optional.empty());

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> packageServiceImpl.addPackage(dto));
        String expectedMessage = "400 BAD_REQUEST \"USER_INVALID\"";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getPackageById_ShouldReturnPackageDTO()
    {
        String packageID = "id";
        String userID = "userID";
        String categoryID = "categoryID";
        String title = "title";
        String description = "description";
        String image = "Image";

        Category category = Category.builder().id(categoryID).build();
        User user = User.builder().id(userID).build();

        Package p = Package.builder().id("id1").image(image).contentCreator(user).category(category).isActive(true).title(title).description(description).build();

        List<GetRatingDto> dtos = new ArrayList<>();

        dtos.add(GetRatingDto.builder().id("id1").build());
        dtos.add(GetRatingDto.builder().id("id2").build());

        when(repo.findById(packageID)).thenReturn(Optional.of(p));
        when(ratingService.getRatingsByPackage(p.getId())).thenReturn(dtos);

        PackageDto expectedResult = PackageDtoConverter.convertToDto(p, dtos);

        PackageDto actualResult = packageServiceImpl.getPackageById(packageID);

        assertEquals(expectedResult, actualResult);
           
        verify(repo).findById(packageID);
        verify(ratingService).getRatingsByPackage(p.getId());
    }



    @Test
    void getPackageById_ShouldInvokePackageNotFoundException()
    {
        String packageID = "id";

        when(repo.findById(packageID)).thenReturn(Optional.empty());

        PackageNotFoundException exception = assertThrows(PackageNotFoundException.class, () -> packageServiceImpl.getPackageById(packageID));
        String expectedMessage = "404 NOT_FOUND \"Package not found\"";
        String actualMessage = exception.getMessage();

         assertTrue(actualMessage.contains(expectedMessage));

         verify(repo).findById(packageID);

    }

    @Test
    void getAllPackages_shouldReturnAllPackagesWithActivePackages()
    {
        String packageID = "id";
        String userID = "userID";
        String categoryID = "categoryID";
        String title = "title";
        String description = "description";
        String image = "Image";

        List<GetRatingDto> ratingDtos = new ArrayList<>();

        User user = User.builder().id("id").build();
        Category category = Category.builder().id(categoryID).build();

        List<PackageDto> packageDtos = new ArrayList<>();

        packageDtos.add(PackageDto.builder().id("id1").isActive(true).build());
        packageDtos.add(PackageDto.builder().id("id2").isActive(true).build());
        packageDtos.add(PackageDto.builder().id("id3").isActive(true).build());

        List<Package> p = new ArrayList<>();

        //p.add(Package.builder().id("id1").image(image).contentCreator(user).category(category).isActive(true).title(title).description(description).build());
        p.add(Package.builder().id("id1").image(image).contentCreator(user).category(category).isActive(true).title(title).description(description).build());

        List<GetRatingDto> getRatingDtos = new ArrayList<>();

        getRatingDtos.add(GetRatingDto.builder().id("id1").build());
        getRatingDtos.add(GetRatingDto.builder().id("id2").build());

        when(repo.findAll()).thenReturn(p);
        when(ratingService.getRatingsByPackage("id1")).thenReturn(getRatingDtos);

        List<PackageDto> actualResult = packageServiceImpl.getAllPackages();

        assertEquals(1, actualResult.stream().count());

        verify(repo).findAll();
        verify(ratingService).getRatingsByPackage("id1");
    }

    @Test
    void getAllPackagesWithoutActive_ShouldReturnNoPackages()
    {
        List<PackageDto> expectedResult = new ArrayList<>();
        List<Package> packages = new ArrayList<>();

        when(repo.findAll()).thenReturn(packages);

        List<PackageDto> actualResult = packageServiceImpl.getAllPackages();

        assertEquals(actualResult, expectedResult);
    }

    @Test
    void updatePackage_ShouldReturnPackageNotFoundException() {
        String packageID = "id";
        CreateUpdatePackageDto dto = CreateUpdatePackageDto.builder().build();

        when(repo.findById(packageID)).thenReturn(Optional.empty());

        PackageNotFoundException exception = assertThrows(PackageNotFoundException.class, () -> packageServiceImpl.updatePackage(packageID, dto));
        String expectedMessage = "404 NOT_FOUND \"Package not found\"";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(repo).findById(packageID);
    }

    @Test
    void updatePackage_ShouldReturnInvalidUserException() {
        String packageID = "id";
        String userID = "userID";
        CreateUpdatePackageDto dto = CreateUpdatePackageDto.builder().creatorId(userID).build();

        Package p = Package.builder().id("id").build();
        when(repo.findById(packageID)).thenReturn(Optional.of(p));

        when(userRepository.findById(userID)).thenReturn(Optional.empty());

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> packageServiceImpl.updatePackage(packageID, dto));
        String expectedMessage = "400 BAD_REQUEST \"USER_INVALID\"";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(repo).findById(packageID);
    }

    @Test
    void updatePackage_ShouldReturnUpdatedPackage()
    {
        String packageID = "id";
        String userID = "userID";
        String categoryID = "categoryID";
        String title = "title";
        String description = "description";
        String image = "Image";

        List<GetRatingDto> ratingDtos = new ArrayList<>();

        User user = User.builder().id("id").build();
        Category category = Category.builder().id(categoryID).build();
        CreateUpdatePackageDto dto = CreateUpdatePackageDto.builder().creatorId(userID).categoryId(categoryID).description(description).image(image).title(title).build();
        Package p = Package.builder().id("id").image(image).contentCreator(user).category(category).isActive(true).title(title).description(description).build();

        ratingDtos.add(GetRatingDto.builder().id("id1").build());
        ratingDtos.add(GetRatingDto.builder().id("id2").build());

        when(repo.findById(packageID)).thenReturn(Optional.of(p));
        when(userRepository.findById(userID)).thenReturn(Optional.of(user));
        when(categoryService.getCategoryById(categoryID)).thenReturn(category);
        when(repo.save(p)).thenReturn(p);

        PackageDto expectedResult = PackageDtoConverter.convertToDto(p, ratingDtos);

        PackageDto actualResult = packageServiceImpl.updatePackage(packageID, dto);

        assertEquals(expectedResult.getId(), actualResult.getId());

        verify(repo).findById(packageID);
        verify(userRepository).findById(userID);
        verify(categoryService).getCategoryById(categoryID);
        verify(repo).save(p);

    }

    @Test
    void deletePackage_ShouldReturnInvalidPackageException()
    {
        String id = "id";
        Package p = Package.builder().id("id").isActive(false).build();

        when(repo.findById(id)).thenReturn(Optional.empty());

        InvalidPackageException exception = assertThrows(InvalidPackageException.class, () -> packageServiceImpl.deletePackage(id));
        String expectedMessage = "400 BAD_REQUEST \"PACKAGE_INVALID\"";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(repo).findById(id);
    }

    @Test
    void deletePackage_ShouldReturnTrue()
    {
        String id = "id";
        Package p = Package.builder().id("id").isActive(true).build();

        when(repo.findById(id)).thenReturn(Optional.of(p));
        when(repo.save(p)).thenReturn(p);

        boolean actualResult = packageServiceImpl.deletePackage(id);

        assertTrue(actualResult);

        verify(repo).save(p);
        verify(repo).findById(id);
    }

    @Test
    void getUploadPackageByUser_ShouldInvokeInvalidUserException()
    {
        String userID = "userID";

        when(userRepository.findById(userID)).thenReturn(Optional.empty());

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> packageServiceImpl.getUploadedPackagesByUser(userID));
        String expectedMessage = "400 BAD_REQUEST \"USER_INVALID\"";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository).findById(userID);
    }

    @Test
    void getUploadPackageByUser_ShouldReturnUploadedPackages()
    {
        String packageID = "id";
        String userID = "userID";
        String categoryID = "categoryID";
        String title = "title";
        String description = "description";
        String image = "Image";

        List<GetRatingDto> ratingDtos = new ArrayList<>();
        List<Package> packages = new ArrayList<>();

        User user = User.builder().id("id").build();
        Category category = Category.builder().id(categoryID).build();
        CreateUpdatePackageDto dto = CreateUpdatePackageDto.builder().creatorId(userID).categoryId(categoryID).description(description).image(image).title(title).build();
        packages.add(Package.builder().id("id").image(image).contentCreator(user).category(category).isActive(true).title(title).description(description).build());

        ratingDtos.add(GetRatingDto.builder().id("id1").build());
        ratingDtos.add(GetRatingDto.builder().id("id2").build());

        when(userRepository.findById(userID)).thenReturn(Optional.of(user));
        when(repo.findPackageByContentCreator(user)).thenReturn(packages);
        when(ratingService.getRatingsByPackage("id")).thenReturn(ratingDtos);


        List<PackageDto> expectedResult = new ArrayList<>();

        List<PackageDto> actualResult = packageServiceImpl.getUploadedPackagesByUser(userID);

        assertEquals(1, actualResult.stream().count());
        verify(userRepository).findById(userID);
        verify(repo).findPackageByContentCreator(user);
        verify(ratingService).getRatingsByPackage("id");
    }

    @Test
    void activatePackage_shouldReturnTrue()
    {
        String id = "id";
        Package p = Package.builder().id("id").isActive(false).build();

        when(repo.findById(id)).thenReturn(Optional.of(p));
        when(repo.save(p)).thenReturn(p);

        boolean actualResult = packageServiceImpl.activatePackage(id);

        assertTrue(actualResult);
        verify(repo).findById(id);
        verify(repo).save(p);
    }

    @Test
    void activatePackage_shouldInvokeInvalidPackageException()
    {
        String id = "id";
        Package p = Package.builder().id("id").isActive(false).build();

        when(repo.findById(id)).thenReturn(Optional.empty());

        InvalidPackageException exception = assertThrows(InvalidPackageException.class, () -> packageServiceImpl.activatePackage(id));
        String expectedMessage = "400 BAD_REQUEST \"PACKAGE_INVALID\"";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(repo).findById(id);
    }

    @Test
    void getPackageByCategory_ShouldReturnAllPackagesByCategory()
    {
        List<Package> packages = new ArrayList<>();
        String categoryID = "categoryID";
        Category category = Category.builder().id(categoryID).build();
        packages.add(Package.builder().title("title1").category(category).build());
        packages.add(Package.builder().title("title2").category(category).build());
        packages.add(Package.builder().title("title3").category(category).build());

        when(repo.findAll()).thenReturn(packages);

        List<Package> expectedResult = packages;
        List<Package> actualResult = packageServiceImpl.getPackagesByCategory(categoryID);

        assertEquals(expectedResult, actualResult);

        verify(repo).findAll();
    }



}
