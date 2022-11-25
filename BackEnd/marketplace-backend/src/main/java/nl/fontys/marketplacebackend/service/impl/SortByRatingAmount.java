package nl.fontys.marketplacebackend.service.impl;

import nl.fontys.marketplacebackend.dto.PackageDto;

import java.util.Comparator;

public class SortByRatingAmount implements Comparator<PackageDto> {

    public int compare(PackageDto a, PackageDto b){
        return b.getRatingsAmount() - a.getRatingsAmount();
    }
}
