package org.acme.persistance;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.User;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public Optional<User> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }

    @Override
    public void persist(User user) {
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        PanacheRepository.super.persist(user);
    }
}
