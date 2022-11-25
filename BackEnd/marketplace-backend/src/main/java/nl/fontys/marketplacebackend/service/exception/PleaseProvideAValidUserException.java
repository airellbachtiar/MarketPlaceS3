package nl.fontys.marketplacebackend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PleaseProvideAValidUserException extends ResponseStatusException {
    public PleaseProvideAValidUserException()
    {
        super(HttpStatus.BAD_REQUEST, "Please provide a valid user.");
    }
}
