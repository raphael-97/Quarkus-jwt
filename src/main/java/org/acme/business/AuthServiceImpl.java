package org.acme.business;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.acme.dto.RegistrationRequest;
import org.acme.mapper.UserMapper;
import org.acme.model.User;
import org.acme.persistance.UserRepository;

import java.util.Set;

@ApplicationScoped
@NoArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    @Inject
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void registerUser(RegistrationRequest registrationRequest) {
        User user = UserMapper.MAPPER.RegistrationRequestToUser(registrationRequest);
        user.setRoles(Set.of("user"));
        userRepository.persist(user);
    }
}
