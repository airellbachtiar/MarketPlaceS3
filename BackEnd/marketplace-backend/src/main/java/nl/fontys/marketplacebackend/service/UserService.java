package nl.fontys.marketplacebackend.service;

import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.dto.UserDto;
import nl.fontys.marketplacebackend.dto.userdtos.CreateUserRequestDTO;
import nl.fontys.marketplacebackend.dto.userdtos.UpdateUserRequestDTO;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.User;

import java.util.List;

public interface UserService {
    List<PackageDto> getDownloadedPackagesPerUser(String userID);
    PackageDto getDownloadedPackagePerUser(String userID, String packageID);
    UserDto getUserById(String userId);
    boolean addUser(CreateUserRequestDTO request);
    void updateUser(UpdateUserRequestDTO request);
    List<UserDto> getAllUsers();
    boolean addDownloadedPackage(String userID, String packageID);
    boolean removeDownloadedPackage(String userID, String packageID);
}
