package nl.fontys.marketplacebackend.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
