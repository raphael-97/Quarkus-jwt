package org.acme.model;

import io.quarkus.security.jpa.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@UserDefinition
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Username
    @Column(unique = true)
    private String username;

    @Password(value = PasswordType.MCF)
    private String password;

    @Roles
    private Set<String> roles;
}
