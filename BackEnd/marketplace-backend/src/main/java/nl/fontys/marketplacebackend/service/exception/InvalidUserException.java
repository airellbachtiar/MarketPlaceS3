package nl.fontys.marketplacebackend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUserException extends ResponseStatusException {
    public InvalidUserException()
    {
        super(HttpStatus.BAD_REQUEST, "USER_INVALID");
    }
}
