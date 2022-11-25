package nl.fontys.marketplacebackend.service.loginservices;

import nl.fontys.marketplacebackend.dto.logindtos.AccessTokenDTO;

public interface AccessTokenDecoder {
    AccessTokenDTO decode (String accessToken);
}
