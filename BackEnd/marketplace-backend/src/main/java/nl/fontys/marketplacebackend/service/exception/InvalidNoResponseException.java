package nl.fontys.marketplacebackend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidNoResponseException extends ResponseStatusException {
    public InvalidNoResponseException()
    {
        super(HttpStatus.BAD_REQUEST, "NO_RESPONSE");
    }
}
