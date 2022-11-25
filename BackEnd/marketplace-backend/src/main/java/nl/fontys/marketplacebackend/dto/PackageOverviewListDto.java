package nl.fontys.marketplacebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.fontys.marketplacebackend.model.Category;
import nl.fontys.marketplacebackend.model.User;

// DTO used for displaying a package in an overview list.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackageOverviewListDto {
    private String id;
    private String title;
    private Category category;
    private String Description;
    private User contentCreator;

    //the category and user should be changed to their respective DTOs, userdto currently does not work
}
