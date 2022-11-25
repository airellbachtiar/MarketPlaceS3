package nl.fontys.marketplacebackend.service.impl;

import lombok.RequiredArgsConstructor;
import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.service.PackageService;
import nl.fontys.marketplacebackend.service.TopRatedAlgorithm;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class TopRatedAlgorithmImpl implements TopRatedAlgorithm {
    private final PackageService packageService;

    @Override
    public List<PackageDto> GetTopRated(){
        List<PackageDto> topRatedPackages = new LinkedList<>();

        List<PackageDto> packages = packageService.getAllPackages();
        PackageDto[] packageArray = new PackageDto[packages.size()];
        packages.toArray(packageArray);

        Arrays.sort(packageArray, new SortByRatingAmount());

        for(PackageDto packageDto : packageArray){
            if(packageDto.getAverageStarRating() >= 3){
                topRatedPackages.add(packageDto);
            }
        }
        boolean moreToAdd = true;
        while(topRatedPackages.size() < 30 && moreToAdd){
            for(PackageDto packageDto : packageArray){
                if(!topRatedPackages.contains(packageDto)){
                    topRatedPackages.add(packageDto);
                }
            }
            moreToAdd = false;
        }

        return topRatedPackages;


    }
}
