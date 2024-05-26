package org.acme.business;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import org.acme.dto.UserRequest;
import org.acme.dto.UserResponse;
import org.acme.mapper.UserMapper;
import org.acme.model.User;
import org.acme.persistance.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(UserMapper.MAPPER::userToUserResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        Optional<User> userOpt =  userRepository.findByIdOptional(id);
        User foundUser = userOpt.orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", id)));
        return UserMapper.MAPPER.userToUserResponse(foundUser);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        User user = UserMapper.MAPPER.UserRequestToUser(userRequest);
        userRepository.persist(user);
        return UserMapper.MAPPER.userToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findByIdOptional(id).orElseThrow(
                () -> new NotFoundException(String.format("User with id %s not found", id)));

        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setRoles(userRequest.getRoles());
        userRepository.persist(user);
        return UserMapper.MAPPER.userToUserResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findByIdOptional(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s not found", id)));

        userRepository.delete(user);
    }
}
