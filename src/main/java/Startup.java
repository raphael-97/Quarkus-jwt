import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;
import lombok.NoArgsConstructor;
import org.acme.model.User;
import org.acme.persistance.UserRepository;

import java.util.Set;

@Singleton
@NoArgsConstructor
public class Startup {

    private UserRepository userRepository;

    @Inject
    public Startup(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {

        User admin = User.builder()
                .username("admin")
                .password("password")
                .roles(Set.of("admin"))
                .build();

        User user = User.builder()
                .username("user")
                .password("password")
                .roles(Set.of("user"))
                .build();

        User multi = User.builder()
                .username("both")
                .password("password")
                .roles(Set.of("user", "admin"))
                .build();


        userRepository.persist(admin);
        userRepository.persist(user);
        userRepository.persist(multi);
    }
}