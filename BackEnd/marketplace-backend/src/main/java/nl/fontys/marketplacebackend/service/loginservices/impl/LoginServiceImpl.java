package nl.fontys.marketplacebackend.service.loginservices.impl;

import lombok.RequiredArgsConstructor;
import nl.fontys.marketplacebackend.dto.logindtos.AccessTokenDTO;
import nl.fontys.marketplacebackend.dto.logindtos.LoginRequestDTO;
import nl.fontys.marketplacebackend.dto.logindtos.LoginResponseDTO;
import nl.fontys.marketplacebackend.model.User;
import nl.fontys.marketplacebackend.persistence.UserRepository;
import nl.fontys.marketplacebackend.service.loginservices.AccessTokenEncoder;
import nl.fontys.marketplacebackend.service.loginservices.LoginService;
import nl.fontys.marketplacebackend.service.loginservices.exception.InvalidLoginException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findUserByEmail(request.getEmail());
        if(user == null)
        {
            throw new InvalidLoginException();
        }
        if(!isPasswordCorrect(request.getPassword(), user.getPassword()))
        {
            throw new InvalidLoginException();
        }

        String accessToken = generateAccessToken(user);
        return LoginResponseDTO.builder().accessToken(accessToken).build();
    }

    private boolean isPasswordCorrect(String insertedPassword, String realPassword)
    {
        String hashedInsertedPassword = HashPassword.hashPassword(insertedPassword);
        return hashedInsertedPassword.equals(realPassword);
    }

    private String generateAccessToken(User user)
    {
        AccessTokenDTO accessTokenDTO = AccessTokenDTO.builder()
                .userID(user.getId())
                .build();

        return accessTokenEncoder.encode(accessTokenDTO);
    }
}
