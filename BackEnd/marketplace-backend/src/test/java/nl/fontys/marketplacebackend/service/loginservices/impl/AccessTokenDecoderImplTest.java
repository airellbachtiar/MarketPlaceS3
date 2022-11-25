package nl.fontys.marketplacebackend.service.loginservices.impl;

import nl.fontys.marketplacebackend.dto.logindtos.AccessTokenDTO;
import nl.fontys.marketplacebackend.service.loginservices.exception.InvalidAccessTokenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccessTokenDecoderImplTest {

    private final String key = "123HIOFNEOHF9NY893CYFNO22C3289YR8932092CNY32Y3892C8902";
    private final AccessTokenEncoderImpl accessTokenEncoder = new AccessTokenEncoderImpl(key);
    private final AccessTokenDecoderImpl accessTokenDecoder = new AccessTokenDecoderImpl(key);

    @Test
    void decode()
    {
        AccessTokenDTO expectedAccessToken = AccessTokenDTO.builder()
                .userID("123")
                .build();

        String accessToken = accessTokenEncoder.encode(expectedAccessToken);
        AccessTokenDTO actualAccessToken = accessTokenDecoder.decode(accessToken);
        assertEquals(expectedAccessToken, actualAccessToken);
    }

    @Test
    void decode_ReturnException()
    {
        assertThrows(InvalidAccessTokenException.class, ()-> accessTokenDecoder.decode("accessToken.sadasd.asdasda"));
    }
}