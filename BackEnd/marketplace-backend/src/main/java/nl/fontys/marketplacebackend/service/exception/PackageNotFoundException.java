package nl.fontys.marketplacebackend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PackageNotFoundException extends ResponseStatusException {

    public PackageNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Package not found");
    }
}
