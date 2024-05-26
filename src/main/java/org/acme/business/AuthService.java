package org.acme.business;

import org.acme.dto.RegistrationRequest;


public interface AuthService {
    void registerUser(RegistrationRequest registrationRequest);
}
