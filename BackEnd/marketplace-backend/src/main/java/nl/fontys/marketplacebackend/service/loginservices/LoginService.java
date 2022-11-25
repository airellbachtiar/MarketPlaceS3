package nl.fontys.marketplacebackend.service.loginservices;

import nl.fontys.marketplacebackend.dto.logindtos.LoginRequestDTO;
import nl.fontys.marketplacebackend.dto.logindtos.LoginResponseDTO;

public interface LoginService {

    LoginResponseDTO login(LoginRequestDTO request);
}
