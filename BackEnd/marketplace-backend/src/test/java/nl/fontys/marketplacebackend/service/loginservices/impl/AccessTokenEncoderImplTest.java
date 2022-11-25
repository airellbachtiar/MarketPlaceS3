package nl.fontys.marketplacebackend.service.loginservices.impl;

import nl.fontys.marketplacebackend.dto.logindtos.AccessTokenDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccessTokenEncoderImplTest {

    private AccessTokenEncoderImpl accessTokenEncoder = new AccessTokenEncoderImpl("123HIOFNEOHF9NY893CYFNO22C3289YR8932092CNY32Y3892C8902");

    @Test
    void encode()
    {
        AccessTokenDTO accessTokenDTO = AccessTokenDTO.builder()
                .userID("123")
                .build();

        String accessToken = accessTokenEncoder.encode(accessTokenDTO);
        assertNotNull(accessToken);
    }
}