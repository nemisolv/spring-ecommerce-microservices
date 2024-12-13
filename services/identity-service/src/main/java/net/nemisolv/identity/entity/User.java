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
    private String name;
    private String password;
    private String email;
    private String imgUrl;
    private String address;
    private String phoneNumber;
    private boolean emailVerified;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String providerId;

    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}