package nl.fontys.marketplacebackend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCategoryException extends ResponseStatusException {

    public InvalidCategoryException() {
        super(HttpStatus.NOT_FOUND, "Invalid Category");
    }
}
