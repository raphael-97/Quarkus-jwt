package org.acme.business;

import java.util.Set;

public interface JwtService {

    String generateJwtToken(String username, Set<String> roles);
}
