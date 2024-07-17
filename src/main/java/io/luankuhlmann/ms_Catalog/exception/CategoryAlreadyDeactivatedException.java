package io.luankuhlmann.ms_Catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryAlreadyDeactivatedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CategoryAlreadyDeactivatedException(String ex) {
        super(ex);
    }
}
