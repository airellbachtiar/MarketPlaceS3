package nl.fontys.marketplacebackend.controller;

import lombok.RequiredArgsConstructor;
import nl.fontys.marketplacebackend.config.security.isauthenticated.IsAuthenticated;
import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.dto.UserDto;
import nl.fontys.marketplacebackend.dto.userdtos.CreateUserRequestDTO;
import nl.fontys.marketplacebackend.dto.userdtos.UpdateUserRequestDTO;
import nl.fontys.marketplacebackend.service.PackageService;
import nl.fontys.marketplacebackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PackageService packageService;


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by id
    // GET /users/{id}
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<Boolean> addUser(@RequestBody CreateUserRequestDTO request) {
        return ResponseEntity.ok().body(userService.addUser(request));
    }

    @IsAuthenticated
    @PutMapping("{userID}")
    public ResponseEntity<Void> updateUser(@PathVariable String userID, @RequestBody UpdateUserRequestDTO request) {
        request.setUserID(userID);
        userService.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    // Get package a user has downloaded
    // GET /users/{id}/downloadedPackages/{packageId}
    @GetMapping("{id}/downloadedPackages/{packageId}")
    public ResponseEntity<PackageDto> getDownloadedPackageByUser(@PathVariable("id") String id, @PathVariable("packageId") String packageId) {
        return ResponseEntity.ok().body(userService.getDownloadedPackagePerUser(id, packageId));
    }

    // Get packages a user has downloaded
    // GET /users/{id}/downloadedPackages
    @GetMapping("{id}/downloadedPackages")
    public ResponseEntity<List<PackageDto>> getDownloadedPackagesByUser(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(userService.getDownloadedPackagesPerUser(id));
    }

    @GetMapping("{id}/uploads")
    public ResponseEntity<List<PackageDto>> getUploadedPackagesByUser(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(packageService.getUploadedPackagesByUser(id));
    }

    // Add a new package to the downloaded packages of a user
    // POST /users/{id}/downloadedPackages
    @PostMapping("{id}/downloadedPackages")
    @IsAuthenticated
    public ResponseEntity<String> addDownloadedPackageByUser(@PathVariable("id") String id, @RequestBody String packageId) {
        if (!userService.addDownloadedPackage(id, packageId)) {
            String entity = "This user has already downloaded package with id: " + packageId + ".";
            return new ResponseEntity<>(entity, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Remove a package from the downloaded packages of a user
    // DELETE /users/{id}/downloadedPackages
    @DeleteMapping("{id}/downloadedPackages/{packageId}")
    @IsAuthenticated
    public ResponseEntity<String> removeDownloadedPackageByUser(@PathVariable("id") String id, @PathVariable("packageId") String packageId) {

        if (!userService.removeDownloadedPackage(id, packageId)) {
            return new ResponseEntity<>("This user does not have this package in their downloads.", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().build();
    }
}
