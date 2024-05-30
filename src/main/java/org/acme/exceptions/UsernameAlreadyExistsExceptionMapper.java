package org.acme.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.dto.ErrorMessage;

@Provider
public class UsernameAlreadyExistsExceptionMapper implements ExceptionMapper<UsernameAlreadyExistsException> {
    @Override
    public Response toResponse(UsernameAlreadyExistsException e) {
        ErrorMessage error = ErrorMessage.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();
        return Response.status(e.getStatus()).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}
