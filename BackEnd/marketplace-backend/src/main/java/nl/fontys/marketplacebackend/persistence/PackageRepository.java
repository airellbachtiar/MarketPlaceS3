package nl.fontys.marketplacebackend.persistence;

import nl.fontys.marketplacebackend.dto.CategoryDto;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface PackageRepository extends MongoRepository<Package, String> {
    List<Package> findPackageByContentCreator(User user);
}
