package org.acme.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameAlreadyExistsException extends RuntimeException {
    private int status;

    public UsernameAlreadyExistsException(int status, String message) {
        super(message);
        this.status = status;
    }
}
