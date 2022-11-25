package nl.fontys.marketplacebackend.service;

import nl.fontys.marketplacebackend.dto.CategoryDto;
import nl.fontys.marketplacebackend.dto.CreatePackageDTO;
import nl.fontys.marketplacebackend.dto.CreateUpdatePackageDto;
import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.User;

import java.util.List;

public interface PackageService {
    Package addPackage(CreatePackageDTO createPackageDTO);
    PackageDto getPackageById(String id);
    List<PackageDto> getAllPackages();
    List<Package> getPackagesByCategory(String categoryId);
    PackageDto updatePackage(String id, CreateUpdatePackageDto createUpdatePackageDto);
    boolean deletePackage(String id);
    List<PackageDto> getUploadedPackagesByUser(String userID);
    boolean activatePackage(String id);
}
