package nl.fontys.marketplacebackend.controller;

import lombok.RequiredArgsConstructor;
import nl.fontys.marketplacebackend.config.security.isauthenticated.IsAuthenticated;
import nl.fontys.marketplacebackend.dto.CreatePackageDTO;
import nl.fontys.marketplacebackend.dto.CreateUpdatePackageDto;
import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.service.*;
import nl.fontys.marketplacebackend.service.RecommendedAlgorithm;
import nl.fontys.marketplacebackend.service.TopRatedAlgorithm;
import nl.fontys.marketplacebackend.service.impl.PackageServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/packages")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PackageController {
    private final PackageServiceImpl packageService;
    private final TopRatedAlgorithm topratedService;
    private final RecommendedAlgorithm recommendedAlgorithm;

    // Get all packages
    // GET /packages
    @GetMapping
    public ResponseEntity<List<PackageDto>> getAllPackages() {

        return ResponseEntity.ok(this.packageService.getAllPackages());
    }

    // Get specific package
    // GET /packages/{id}
    @GetMapping("{id}")
    public ResponseEntity<PackageDto> getPackageById(@PathVariable("id") String id) {
        return ResponseEntity.ok(packageService.getPackageById(id));
    }

    @PostMapping
    @IsAuthenticated
    public ResponseEntity<String> addPackage(
            @RequestBody @Valid CreatePackageDTO createPackageDTO) {
        Package p = packageService.addPackage(createPackageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(p.getId());
    }


    @PutMapping("{id}")
    @IsAuthenticated
    public ResponseEntity<PackageDto> updatePackage(@PathVariable("id") String id, @RequestBody CreateUpdatePackageDto packageRequest) {
        return ResponseEntity.ok(packageService.updatePackage(id, packageRequest));
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activatePackage(@PathVariable("id") String id) {
        boolean isActivated = packageService.activatePackage(id);

        if (isActivated) {
            return ResponseEntity.ok().body("Successfully activated package: " + id);
        } else {
            String entity = "Failed to activate package";
            return new ResponseEntity<>(entity, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    @IsAuthenticated
    public ResponseEntity<Void> deletePackage(@PathVariable String id) {
        boolean isPackageRemoved = this.packageService.deletePackage(id);

        if (isPackageRemoved) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/topRated")
    public ResponseEntity<List<PackageDto>> getTopRated() {

        return ResponseEntity.ok(this.topratedService.GetTopRated());
    }

    @GetMapping("{userID}/recommended")
    public ResponseEntity<List<PackageDto>> getRecommended(@PathVariable String userID){
        return ResponseEntity.ok(recommendedAlgorithm.GetRecommendedPackages(userID));
    }

}
