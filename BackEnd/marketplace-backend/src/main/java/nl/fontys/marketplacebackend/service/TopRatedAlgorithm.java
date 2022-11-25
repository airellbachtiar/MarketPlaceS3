package nl.fontys.marketplacebackend.service;

import nl.fontys.marketplacebackend.dto.PackageDto;

import java.util.List;

public interface TopRatedAlgorithm {
    List<PackageDto> GetTopRated();
}
