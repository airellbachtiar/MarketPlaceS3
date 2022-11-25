package nl.fontys.marketplacebackend.service;

import lombok.experimental.UtilityClass;
import nl.fontys.marketplacebackend.dto.UserDto;
import nl.fontys.marketplacebackend.model.User;

@UtilityClass
public final class UserDtoConverter {

    public static UserDto convertToDto(User user)
    {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
