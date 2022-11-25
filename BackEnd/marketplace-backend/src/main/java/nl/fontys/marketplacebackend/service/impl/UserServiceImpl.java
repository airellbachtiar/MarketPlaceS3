package nl.fontys.marketplacebackend.service.impl;

import lombok.RequiredArgsConstructor;
import nl.fontys.marketplacebackend.dto.GetRatingDto;
import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.dto.UserDto;
import nl.fontys.marketplacebackend.dto.userdtos.CreateUserRequestDTO;
import nl.fontys.marketplacebackend.dto.userdtos.UpdateUserRequestDTO;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.Rating;
import nl.fontys.marketplacebackend.model.User;
import nl.fontys.marketplacebackend.persistence.PackageRepository;
import nl.fontys.marketplacebackend.persistence.UserRepository;
import nl.fontys.marketplacebackend.service.*;
import nl.fontys.marketplacebackend.service.exception.InvalidPackageException;
import nl.fontys.marketplacebackend.service.exception.InvalidUserException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RatingService ratingService;

    private final PackageService packageService;

    private final PackageRepository packageRepository;

    public UserDto getUserById(String userId)
    {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
        {
            throw new InvalidUserException();
        }
        return UserDtoConverter.convertToDto(user.get());
    }

    public boolean addUser(CreateUserRequestDTO request)
    {
        if(userRepository.findUserByEmail(request.getEmail()) != null
                || !StringUtils.hasText(request.getEmail())
                || !StringUtils.hasText(request.getFirstName())
                || !StringUtils.hasText(request.getLastName())
                || !StringUtils.hasText(request.getPassword())
                )
        {
            return false;
        }
        String userID = UUID.randomUUID().toString();
        User user = User.builder()
                .id(userID)
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        this.userRepository.save(user);
        return true;
    }

    @Override
    public void updateUser(UpdateUserRequestDTO request)
    {
        Optional<User> user = userRepository.findById(request.getUserID());
        if(user.isEmpty() || !Objects.equals(user.get().getEmail(), request.getEmail())
                && userRepository.findUserByEmail(request.getEmail()) != null)
        {
            throw new InvalidUserException();
        }

        User updateUser = user.get();
        updateUser.setEmail(request.getEmail());
        updateUser.setFirstName(request.getFirstName());
        updateUser.setLastName(request.getLastName());

        userRepository.save(updateUser);
    }

    public List<UserDto> getAllUsers()
    {
        return userRepository.findAll().stream().map(UserDtoConverter::convertToDto).toList();
    }

    public boolean addDownloadedPackage(String userID, String packageID) {

        Optional<User> user = userRepository.findById(userID);
        if(user.isEmpty())
        {
            throw new InvalidUserException();
        }
        Optional<Package> packageModel = packageRepository.findById(packageID);
        if(packageModel.isEmpty())
        {
            throw new InvalidPackageException();
        }
        // Return false if the package is already downloaded
        if(getDownloadedPackagePerUser(user.get().getId(), packageModel.get().getId()) != null) {
            return false;
        }

        List<Package> downloadedPackages = user.get().getDownloadedPackages();
        if(downloadedPackages == null) {
            downloadedPackages = new ArrayList<>();
        }
        downloadedPackages.add(packageModel.get());
        user.get().setDownloadedPackages(downloadedPackages);
        userRepository.save(user.get());
        return true;
    }

    public boolean removeDownloadedPackage(String userID, String packageID) {
        Optional<User> user = userRepository.findById(userID);
        if(user.isEmpty())
        {
            throw new InvalidUserException();
        }
        Optional<Package> packageModel = packageRepository.findById(packageID);
        if(packageModel.isEmpty())
        {
            throw new InvalidPackageException();
        }
        // Return false if the package is not downloaded
        if(getDownloadedPackagePerUser(user.get().getId(), packageModel.get().getId()) == null) {
            return false;
        }

        // Remove a rating for the package if the user had one
        Rating rating = ratingService.getRatingByRatingPackageAndUser(packageModel.get(), user.get());
        if(rating != null) {
            ratingService.deleteRating(rating.getId());
        }

        List<Package> downloadedPackages = user.get().getDownloadedPackages();
        if(downloadedPackages == null) {
            downloadedPackages = new ArrayList<>();
        }
        downloadedPackages.removeIf(dp -> dp.getId().equals(packageModel.get().getId()));
        user.get().setDownloadedPackages(downloadedPackages);
        userRepository.save(user.get());
        return true;
    }


    public List<PackageDto> getDownloadedPackagesPerUser(String userID) {
        Optional<User> optionalUser = userRepository.findById(userID);
        if(optionalUser.isEmpty())
        {
            throw new InvalidUserException();
        }
        User user = optionalUser.get();
        List<Package> packages = user.getDownloadedPackages();
        if(packages == null)
        {
            throw new InvalidPackageException();
        }

        List<PackageDto> packageDtos = new ArrayList<>();
        for(Package p : packages)
        {
            List<GetRatingDto> ratings = ratingService.getRatingsByPackage(p.getId());
            packageDtos.add(PackageDtoConverter.convertToDto(p, ratings));
        }

        return packageDtos;
    }


    public PackageDto getDownloadedPackagePerUser(String userID, String packageID) {
        Optional<User> user = userRepository.findById(userID);
        if(user.isEmpty()) {
            throw new InvalidUserException();
        }
        PackageDto packageDto = packageService.getPackageById(packageID);

        List<Package> downloadedPackages = user.get().getDownloadedPackages();
        if(downloadedPackages == null) {
            downloadedPackages = new ArrayList<>();
        }
        Package downloadedPackage = null;
        for(Package p : downloadedPackages) {
            if(p.getId().equals(packageDto.getId())) {
                downloadedPackage = p;
            }
        }
        if(downloadedPackage == null) {
            return null;
        }

        List<GetRatingDto> ratings = ratingService.getRatingsByPackage(packageID);

        return PackageDtoConverter.convertToDto(downloadedPackage, ratings);
    }
}
