package nl.fontys.marketplacebackend.service;

import nl.fontys.marketplacebackend.dto.PackageDto;

import java.util.List;

public interface RecommendedAlgorithm {
    List<PackageDto> GetRecommendedPackages(String userID);
}
