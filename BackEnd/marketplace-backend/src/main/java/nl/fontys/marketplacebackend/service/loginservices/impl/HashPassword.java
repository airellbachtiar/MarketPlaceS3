package nl.fontys.marketplacebackend.service.loginservices.impl;

import lombok.experimental.UtilityClass;
import nl.fontys.marketplacebackend.service.exception.InvalidUserException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@UtilityClass
public final class HashPassword {
    public static String hashPassword(String input) {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            BigInteger number = new BigInteger(1, hash);

            StringBuilder hashedString = new StringBuilder(number.toString(16));

            while (hashedString.length() < 32)
            {
                hashedString.insert(0, '0');
            }
            return hashedString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new InvalidUserException();
        }
    }
}
