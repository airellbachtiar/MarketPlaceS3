package nl.fontys.marketplacebackend.service.impl;

import lombok.RequiredArgsConstructor;
import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.service.PackageService;
import nl.fontys.marketplacebackend.service.RecommendedAlgorithm;
import nl.fontys.marketplacebackend.service.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class RecommendedAlgorithmImpl implements RecommendedAlgorithm {
    private final UserService userService;
    private final PackageService packageService;

    @Override
    public List<PackageDto> GetRecommendedPackages(String userID){
        System.out.println("Getting recommended...");
        List<PackageDto> recommendedPackages = new LinkedList<>();
        List<PackageDto> allPackages = packageService.getAllPackages();

        System.out.println("Getting downloaded...");
        List<PackageDto> downloadedPackages = userService.getDownloadedPackagesPerUser(userID);

        //make a list of all downloaded categories
        List<String> categories = downloadedPackages.stream()
                .map(p -> p.getCategory()).toList();


        //make 2 lists one with the amount and one with the category
        List<Integer> categoryAmount = new LinkedList<>();
        List<String> uniqueCategories = new LinkedList<>();

        for (String category : categories) {
            if(!uniqueCategories.contains(category)){
                categoryAmount.add(Collections.frequency(categories, category));
                uniqueCategories.add(category);
            }
        }


        int totalAmount = categoryAmount.stream()
                .mapToInt(a -> a)
                .sum();
        System.out.println("total downloaded: " + totalAmount);

        double result;
        double packageAmount;
        int index = 0;
        for (Integer amount : categoryAmount){
            //calculate how many of the 30 has to be of each category
            result = (double) amount / totalAmount;
            packageAmount = 30 * result;

            //shuffle to get random packages each time
            Collections.shuffle(allPackages);

            //loop over all packages and get correct amount of packages for each category
            int addedAmount = 0;
            for (PackageDto packageDto : allPackages){
                if (packageDto.getCategory().equals(uniqueCategories.get(index))) {
                    if (packageAmount >= addedAmount) {
                        if (!downloadedPackages.contains(packageDto)) {
                            recommendedPackages.add(packageDto);
                            addedAmount++;
                        }
                    }
                }
            }
            index++;
        }
        System.out.println("recommended: " + recommendedPackages.size());
        //shuffle to put in random order
        Collections.shuffle(recommendedPackages);
        return recommendedPackages;
    }
}
