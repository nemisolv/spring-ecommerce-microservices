package net.nemisolv.identity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.nemisolv.lib.core._enum.AuthProvider;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@SuperBuilder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    private boolean emailVerified;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column(columnDefinition = "TEXT")
    private String providerId;

    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}