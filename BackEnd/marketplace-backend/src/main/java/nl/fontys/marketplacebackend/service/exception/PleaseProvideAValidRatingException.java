package nl.fontys.marketplacebackend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PleaseProvideAValidRatingException extends ResponseStatusException {
    public PleaseProvideAValidRatingException()
    {
        super(HttpStatus.BAD_REQUEST, "Please provide a valid rating.");
    }
}
