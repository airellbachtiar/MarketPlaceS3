package nl.fontys.marketplacebackend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPackageException extends ResponseStatusException {
    public InvalidPackageException()
    {
        super(HttpStatus.BAD_REQUEST, "PACKAGE_INVALID");
    }
}
