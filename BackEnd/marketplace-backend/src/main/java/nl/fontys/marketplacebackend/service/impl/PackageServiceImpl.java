package nl.fontys.marketplacebackend.service.impl;

import lombok.RequiredArgsConstructor;
import nl.fontys.marketplacebackend.dto.CreatePackageDTO;
import nl.fontys.marketplacebackend.dto.CreateUpdatePackageDto;
import nl.fontys.marketplacebackend.dto.GetRatingDto;
import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.model.Category;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.User;
import nl.fontys.marketplacebackend.persistence.PackageRepository;
import nl.fontys.marketplacebackend.persistence.UserRepository;
import nl.fontys.marketplacebackend.service.*;
import nl.fontys.marketplacebackend.service.exception.InvalidCategoryException;
import nl.fontys.marketplacebackend.service.exception.InvalidPackageException;
import nl.fontys.marketplacebackend.service.exception.InvalidUserException;
import nl.fontys.marketplacebackend.service.exception.PackageNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageRepository repo;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final RatingService ratingService;

    @Override
    public Package addPackage(CreatePackageDTO createPackageDTO) {

        Category category = categoryService.getCategoryById(createPackageDTO.getCategoryId());
        if (category == null) {
            throw new InvalidCategoryException();
        }
        Optional<User> user = userRepository.findById(createPackageDTO.getCreatorId());
        if(user.isEmpty())
        {
            throw new InvalidUserException();
        }

        Package packageToAdd = new Package(createPackageDTO.getImage(), createPackageDTO.getTitle(),
                createPackageDTO.getDescription(),
                category,
                user.get());
        return repo.save(packageToAdd);
    }

    @Override
    public PackageDto getPackageById(String id) {

        Package p = repo.findById(id).orElse(null);
        if (p == null) {
            throw new PackageNotFoundException();
        }

        List<GetRatingDto> ratings = ratingService.getRatingsByPackage(p.getId());
        return PackageDtoConverter.convertToDto(p, ratings);
    }

    @Override
    public List<PackageDto> getAllPackages() {
        List<PackageDto> packages = new ArrayList<>();
        for (Package p : repo.findAll()) {
            if(p.isActive())
            {
                List<GetRatingDto> ratings = ratingService.getRatingsByPackage(p.getId());
                packages.add(PackageDtoConverter.convertToDto(p, ratings));
            }
        }
        return packages;
    }

    //unused
    @Override
    public List<Package> getPackagesByCategory(String categoryId) {
        List<Package> packages = this.repo.findAll();
        List<Package> returnPackages = new ArrayList<>();
        for (Package p : packages) {
            if (p.getCategory().getId().equals(categoryId)) {
                returnPackages.add(p);
            }
        }
        return returnPackages;
    }

    @Override
    public PackageDto updatePackage(String id, CreateUpdatePackageDto createUpdatePackageDto) {
        //new code
        Package p = repo.findById(id).orElse(null);
        if (p == null)
        {
            throw new PackageNotFoundException();
        }

        //old code
//        Package p = repo.findById(id).isPresent() ? repo.findById(id).get() : null;
//
//        if (p == null) {
//            throw new PackageNotFoundException();
//        }

        Optional<User> user = userRepository.findById(createUpdatePackageDto.getCreatorId());
        if(user.isEmpty())
        {
            throw new InvalidUserException();
        }

        p.setTitle(createUpdatePackageDto.getTitle());
        p.setDescription(createUpdatePackageDto.getDescription());
        p.setImage(createUpdatePackageDto.getImage());
        p.setCategory(categoryService.getCategoryById(createUpdatePackageDto.getCategoryId()));
        p.setContentCreator(user.get());

        Package updatedPackage = repo.save(p);
        List<GetRatingDto> ratings = ratingService.getRatingsByPackage(updatedPackage.getId());

        return PackageDtoConverter.convertToDto(p, ratings);

    }


    @Override
    public boolean deletePackage(String id) {
        Optional<Package> packageToRemove = repo.findById(id);
        if(packageToRemove.isEmpty())
        {
            throw new InvalidPackageException();
        }
        packageToRemove.get().setActive(false);

        Package inactivePackage = repo.save(packageToRemove.get());

        return !inactivePackage.isActive();
    }

    @Override
    public List<PackageDto> getUploadedPackagesByUser(String userID)
    {
        Optional<User> user = userRepository.findById(userID);
        if(user.isEmpty())
        {
            throw new InvalidUserException();
        }
        List<Package> uploadedPackages = repo.findPackageByContentCreator(user.get());
        List<PackageDto> uploadedPackageDTOs = new ArrayList<>();

        for (Package p : uploadedPackages) {
            List<GetRatingDto> ratings = ratingService.getRatingsByPackage(p.getId());

            uploadedPackageDTOs.add(PackageDtoConverter.convertToDto(p, ratings));
        }
        return uploadedPackageDTOs;
    }

    @Override
    public boolean activatePackage(String id) {
        Optional<Package> p = repo.findById(id);
        if(p.isEmpty())
        {
            throw new InvalidPackageException();
        }
        p.get().setActive(true);
        Package activatedPackage = repo.save(p.get());

        return activatedPackage.isActive();
    }
}
