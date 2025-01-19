package net.nemisolv.identity.security;


import lombok.*;
import net.nemisolv.identity.entity.Role;
import net.nemisolv.identity.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal  implements UserDetails, OAuth2User   {
    private Long id;
    private String email;
    private String password;
    private String imgUrl;
    private boolean active;
    private boolean emailVerified;
    @Setter
    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;

    @Getter
    private Role role;



    public static UserPrincipal create(User user) {

        List<SimpleGrantedAuthority> authorities = buildAuthorities(user.getRole());

        return UserPrincipal.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .emailVerified(user.isEmailVerified())
                .active(user.isEnabled())
                .authorities(authorities)
                .role(user.getRole())
                .build();
    }

    private static List<SimpleGrantedAuthority> buildAuthorities(Role role) {
        if(role == null) {
            return List.of();
        }
        var authorities = new java.util.ArrayList<>(role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName().name()))
                .toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

        return authorities;
    }

    public static UserPrincipal create(User user, Map<String,Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);

        return userPrincipal;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }


    @Override
    public String getName() {
        return String.valueOf(id);
    }


}