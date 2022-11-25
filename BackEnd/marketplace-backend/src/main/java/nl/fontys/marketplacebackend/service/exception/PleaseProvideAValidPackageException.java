package nl.fontys.marketplacebackend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PleaseProvideAValidPackageException extends ResponseStatusException {
    public PleaseProvideAValidPackageException()
    {
        super(HttpStatus.BAD_REQUEST, "Please provide a valid package.");
    }
}
