package org.acme.presentation;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
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
    @PermitAll
    public Response getUsers() {
        List<UserResponse> listOfUsers = userService.getUsers();
        return Response.ok(listOfUsers).build();
    }

    @GET
    @Path("{id}")
    public Response getUser(@PathParam("id") Long id) {
        UserResponse user = userService.getUserById(id);
        return Response.ok(user).build();
    }

    @POST
    public Response createUser(UserRequest userRequest) {
        UserResponse user = userService.createUser(userRequest);
        return Response.created(URI.create("/users/" + user.getId())).entity(user).build();
    }

    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") Long id, UserRequest userRequest) {
        UserResponse user = userService.updateUser(id, userRequest);
        return Response.ok(user).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteUser(id);
        return Response.ok().build();
    }
}