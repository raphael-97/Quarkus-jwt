package org.acme.presentation;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.*;
import lombok.NoArgsConstructor;
import org.acme.business.AuthService;
import org.acme.business.JwtService;
import org.acme.business.UserService;
import org.acme.dto.RegistrationRequest;

import java.util.Set;

@Path("/api/auth")
@NoArgsConstructor
public class AuthResource {

    private AuthService authService;

    private UserService userService;

    private JwtService jwtService;

    @Inject
    public AuthResource(AuthService authService, UserService userService, JwtService jwtService) {
        this.authService = authService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @POST
    @Path("/register")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(RegistrationRequest registrationRequest) {
        try {
            authService.registerUser(registrationRequest);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(400).entity("Username already in use").build();
        }
    }

    @POST
    @Path("/login")
    @RolesAllowed({"admin", "user"})
    public Response loginUser(@Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        Set<String> roles = userService.getRoles(username);

        String jwtToken = jwtService.generateJwtToken(username, roles);

        // Deprecated...
        NewCookie jwtCookie = new NewCookie("jwt", jwtToken, "/", null, 2, null,
                3600, null, true, true, NewCookie.SameSite.STRICT);
        return Response.ok().cookie(jwtCookie).build();
    }
}
