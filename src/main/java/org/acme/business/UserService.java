package org.acme.business;

import org.acme.dto.UserRequest;
import org.acme.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();

    UserResponse getUserById(Long id);

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);
}
