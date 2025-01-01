package net.nemisolv.identity.payload.profile;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.nemisolv.identity.entity.Role;
import net.nemisolv.identity.payload.user.RoleResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    boolean emailVerified;
    RoleResponse role;
}