package nl.fontys.marketplacebackend.service.loginservices.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import nl.fontys.marketplacebackend.dto.logindtos.AccessTokenDTO;
import nl.fontys.marketplacebackend.service.loginservices.AccessTokenEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenEncoderImpl implements AccessTokenEncoder {

    private final Key key;

    public AccessTokenEncoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessTokenDTO accessTokenDTO) {

        Map<String, Object> claimsMap = new HashMap<>();
        if (accessTokenDTO.getUserID() != null) {
            claimsMap.put("userId", accessTokenDTO.getUserID());
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(60, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }
}
