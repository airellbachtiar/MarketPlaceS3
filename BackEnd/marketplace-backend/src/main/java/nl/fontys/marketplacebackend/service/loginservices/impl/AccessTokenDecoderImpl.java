package nl.fontys.marketplacebackend.service.loginservices.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import nl.fontys.marketplacebackend.dto.logindtos.AccessTokenDTO;
import nl.fontys.marketplacebackend.service.loginservices.AccessTokenDecoder;
import nl.fontys.marketplacebackend.service.loginservices.exception.InvalidAccessTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class AccessTokenDecoderImpl implements AccessTokenDecoder {

    private final Key key;

    public AccessTokenDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public AccessTokenDTO decode(String accessToken) {
        try {
            Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(accessToken);
            Claims claims = (Claims) jwt.getBody();

            return AccessTokenDTO.builder()
                    .userID(claims.get("userId", String.class))
                    .build();
        } catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }
    }
}
