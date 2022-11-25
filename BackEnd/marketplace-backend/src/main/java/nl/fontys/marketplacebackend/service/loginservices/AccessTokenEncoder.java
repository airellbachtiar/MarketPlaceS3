package nl.fontys.marketplacebackend.service.loginservices;

import nl.fontys.marketplacebackend.dto.logindtos.AccessTokenDTO;

public interface AccessTokenEncoder {
    String encode(AccessTokenDTO accessToken);
}
