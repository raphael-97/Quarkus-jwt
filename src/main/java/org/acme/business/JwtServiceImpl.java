package org.acme.business;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Set;

@ApplicationScoped
@NoArgsConstructor
public class JwtServiceImpl implements JwtService {

    @ConfigProperty(name = "jwt.expiration.time")
    private int expiration;

    @ConfigProperty(name = "jwt.issuer.name")
    private String issuer;
    @Override
    public String generateJwtToken(String username, Set<String> roles) {
        return Jwt.issuer(issuer)
                .subject(username)
                .groups(roles)
                .expiresIn(expiration)
                .sign();
    }
}
