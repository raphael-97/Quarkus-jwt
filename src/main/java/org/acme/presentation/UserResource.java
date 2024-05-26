package org.acme.presentation;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import org.acme.business.UserService;
import org.acme.dto.UserRequest;
import org.acme.dto.UserResponse;

import java.net.URI;
import java.util.List;

@Path("/api/users")
@NoArgsConstructor
public class UserResource {

    private UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<UserResponse> listOfUsers = userService.getUsers();
        return Response.ok(listOfUsers).build();
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id) {
        UserResponse user = userService.getUserById(id);
        return Response.ok(user).build();
    }

    @POST
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserRequest userRequest) {
        UserResponse user = userService.createUser(userRequest);
        return Response.created(URI.create("/users/" + user.getId())).entity(user).build();
    }

    @PUT
    @Path("{id}")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, UserRequest userRequest) {
        UserResponse user = userService.updateUser(id, userRequest);
        return Response.ok(user).build();
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteUser(id);
        return Response.ok().build();
    }
}
